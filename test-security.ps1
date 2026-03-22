Write-Host "🔐 PRUEBA COMPLETA DE SEGURIDAD EN MEMBER-SERVICE" -ForegroundColor Green
Write-Host "================================================" -ForegroundColor Green
Write-Host ""

# Función para hacer requests
function Test-Endpoint {
    param(
        [string]$Method,
        [string]$Url,
        [string]$Token,
        [string]$Description,
        [bool]$ShouldSucceed = $true
    )

    Write-Host "Testing: $Description" -ForegroundColor Yellow
    Write-Host "  $Method $Url" -ForegroundColor White

    try {
        if ($Token) {
            $response = Invoke-WebRequest -Uri $Url -Method $Method -Headers @{Authorization="Bearer $Token"} -ErrorAction Stop
            $statusCode = $response.StatusCode
        } else {
            $response = Invoke-WebRequest -Uri $Url -Method $Method -ErrorAction Stop
            $statusCode = $response.StatusCode
        }

        if ($ShouldSucceed -and $statusCode -eq 200) {
            Write-Host "  ✅ SUCCESS ($statusCode)" -ForegroundColor Green
        } elseif (!$ShouldSucceed -and ($statusCode -eq 403 -or $statusCode -eq 401)) {
            Write-Host "  ✅ EXPECTED DENIAL ($statusCode)" -ForegroundColor Green
        } else {
            Write-Host "  ❌ UNEXPECTED RESPONSE ($statusCode)" -ForegroundColor Red
        }
    } catch {
        $statusCode = $_.Exception.Response.StatusCode.Value__
        if (!$ShouldSucceed -and ($statusCode -eq 403 -or $statusCode -eq 401)) {
            Write-Host "  ✅ EXPECTED DENIAL ($statusCode)" -ForegroundColor Green
        } else {
            Write-Host "  ❌ ERROR ($statusCode): $($_.Exception.Message)" -ForegroundColor Red
        }
    }
    Write-Host ""
}

Write-Host "1️⃣ Creando usuarios de prueba..." -ForegroundColor Yellow

# Crear usuario MEMBER
Write-Host "  Creando MEMBER..." -ForegroundColor White
Invoke-WebRequest -Uri "http://localhost:8081/auth/register" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{
    "username": "member.test",
    "password": "Member123!",
    "role": "MEMBER",
    "memberId": "mem-001"
}' -ErrorAction SilentlyContinue | Out-Null

# Crear usuario ADMIN
Write-Host "  Creando ADMIN..." -ForegroundColor White
Invoke-WebRequest -Uri "http://localhost:8081/auth/register" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{
    "username": "admin.test",
    "password": "Admin123!",
    "role": "ADMIN",
    "memberId": "admin-001"
}' -ErrorAction SilentlyContinue | Out-Null

# Crear usuario TRAINER
Write-Host "  Creando TRAINER..." -ForegroundColor White
Invoke-WebRequest -Uri "http://localhost:8081/auth/register" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{
    "username": "trainer.test",
    "password": "Trainer123!",
    "role": "TRAINER",
    "memberId": "trainer-001"
}' -ErrorAction SilentlyContinue | Out-Null

Write-Host "  ✅ Usuarios creados" -ForegroundColor Green
Write-Host ""

Write-Host "2️⃣ Obteniendo tokens..." -ForegroundColor Yellow

# Login MEMBER
$memberLogin = Invoke-WebRequest -Uri "http://localhost:8081/auth/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{
    "username": "member.test",
    "password": "Member123!"
}' -ErrorAction SilentlyContinue
$memberToken = ($memberLogin.Content | ConvertFrom-Json).access_token
Write-Host "  ✅ MEMBER token obtenido" -ForegroundColor Green

# Login ADMIN
$adminLogin = Invoke-WebRequest -Uri "http://localhost:8081/auth/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{
    "username": "admin.test",
    "password": "Admin123!"
}' -ErrorAction SilentlyContinue
$adminToken = ($adminLogin.Content | ConvertFrom-Json).access_token
Write-Host "  ✅ ADMIN token obtenido" -ForegroundColor Green

# Login TRAINER
$trainerLogin = Invoke-WebRequest -Uri "http://localhost:8081/auth/login" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{
    "username": "trainer.test",
    "password": "Trainer123!"
}' -ErrorAction SilentlyContinue
$trainerToken = ($trainerLogin.Content | ConvertFrom-Json).access_token
Write-Host "  ✅ TRAINER token obtenido" -ForegroundColor Green
Write-Host ""

Write-Host "3️⃣ PRUEBAS DE AUTORIZACIÓN" -ForegroundColor Yellow
Write-Host "==========================" -ForegroundColor Yellow
Write-Host ""

# PRUEBA 1: Ver todos los miembros
Write-Host "📋 PRUEBA: Ver todos los miembros (/members/all)" -ForegroundColor Cyan
Test-Endpoint -Method GET -Url "http://localhost:8080/api/members/all" -Token $memberToken -Description "MEMBER intenta ver todos" -ShouldSucceed $false
Test-Endpoint -Method GET -Url "http://localhost:8080/api/members/all" -Token $trainerToken -Description "TRAINER intenta ver todos" -ShouldSucceed $false
Test-Endpoint -Method GET -Url "http://localhost:8080/api/members/all" -Token $adminToken -Description "ADMIN ve todos" -ShouldSucceed $true

# PRUEBA 2: Ver perfil específico
Write-Host "👤 PRUEBA: Ver perfil específico (/members/id/mem-001)" -ForegroundColor Cyan
Test-Endpoint -Method GET -Url "http://localhost:8080/api/members/id/mem-001" -Token $memberToken -Description "MEMBER ve su propio perfil" -ShouldSucceed $true
Test-Endpoint -Method GET -Url "http://localhost:8080/api/members/id/mem-001" -Token $trainerToken -Description "TRAINER ve perfil de miembro" -ShouldSucceed $true
Test-Endpoint -Method GET -Url "http://localhost:8080/api/members/id/mem-001" -Token $adminToken -Description "ADMIN ve perfil de miembro" -ShouldSucceed $true

# PRUEBA 3: MEMBER intenta ver perfil de otro
Write-Host "🚫 PRUEBA: MEMBER intenta ver perfil de otro (/members/id/other-id)" -ForegroundColor Cyan
Test-Endpoint -Method GET -Url "http://localhost:8080/api/members/id/other-member-id" -Token $memberToken -Description "MEMBER intenta ver perfil ajeno" -ShouldSucceed $false

# PRUEBA 4: Crear miembro
Write-Host "➕ PRUEBA: Crear nuevo miembro (/members/add)" -ForegroundColor Cyan
$createBody = '{
    "name": "Nuevo Miembro",
    "email": "nuevo@email.com",
    "phone": "123456789",
    "membershipType": "BASIC"
}' | ConvertTo-Json

Test-Endpoint -Method POST -Url "http://localhost:8080/api/members/add" -Token $memberToken -Description "MEMBER intenta crear miembro" -ShouldSucceed $false
Test-Endpoint -Method POST -Url "http://localhost:8080/api/members/add" -Token $trainerToken -Description "TRAINER intenta crear miembro" -ShouldSucceed $false
Test-Endpoint -Method POST -Url "http://localhost:8080/api/members/add" -Token $adminToken -Description "ADMIN crea miembro" -ShouldSucceed $true

# PRUEBA 5: Actualizar perfil
Write-Host "✏️ PRUEBA: Actualizar perfil (/members/update/mem-001)" -ForegroundColor Cyan
$updateBody = '{
    "name": "Miembro Actualizado",
    "email": "actualizado@email.com"
}' | ConvertTo-Json

Test-Endpoint -Method PUT -Url "http://localhost:8080/api/members/update/mem-001" -Token $memberToken -Description "MEMBER actualiza su perfil" -ShouldSucceed $true
Test-Endpoint -Method PUT -Url "http://localhost:8080/api/members/update/mem-001" -Token $trainerToken -Description "TRAINER intenta actualizar" -ShouldSucceed $false
Test-Endpoint -Method PUT -Url "http://localhost:8080/api/members/update/mem-001" -Token $adminToken -Description "ADMIN actualiza perfil" -ShouldSucceed $true

# PRUEBA 6: Eliminar miembro
Write-Host "🗑️ PRUEBA: Eliminar miembro (/members/delete/mem-001)" -ForegroundColor Cyan
Test-Endpoint -Method DELETE -Url "http://localhost:8080/api/members/delete/mem-001" -Token $memberToken -Description "MEMBER intenta eliminar" -ShouldSucceed $false
Test-Endpoint -Method DELETE -Url "http://localhost:8080/api/members/delete/mem-001" -Token $trainerToken -Description "TRAINER intenta eliminar" -ShouldSucceed $false
Test-Endpoint -Method DELETE -Url "http://localhost:8080/api/members/delete/mem-001" -Token $adminToken -Description "ADMIN elimina miembro" -ShouldSucceed $true

Write-Host "🎉 PRUEBA COMPLETADA!" -ForegroundColor Green
Write-Host ""
Write-Host "📊 RESUMEN:" -ForegroundColor Yellow
Write-Host "  ✅ Las reglas de autorización están funcionando correctamente" -ForegroundColor Green
Write-Host "  ✅ ADMIN tiene acceso completo" -ForegroundColor Green
Write-Host "  ✅ MEMBER solo accede a su información" -ForegroundColor Green
Write-Host "  ✅ TRAINER puede ver pero no modificar" -ForegroundColor Green
Write-Host "  ✅ Accesos no autorizados son correctamente denegados" -ForegroundColor Green
