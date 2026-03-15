# Gym Manager Microservices - Guías para Agentes de IA

## Resumen de Arquitectura
Este es un sistema de microservicios Spring Cloud para gestión de gimnasios con los siguientes componentes:
- **Servicios de Infraestructura**: Eureka (descubrimiento), Config Server (configuración centralizada), API Gateway (enrutamiento)
- **Servicios de Negocio**: auth-service, class-service, member-service, notification-service, payment-service, promotion-service, reservation-service, trainer-service
- **Datos**: MongoDB (bases de datos separadas por servicio, ej., `gymdb` para la mayoría, `gym_auth_db` para auth)
- **Mensajería**: Kafka para comunicación basada en eventos (ej., PaymentCreatedEvent de payment-service a member-service)
- **Autenticación**: Basada en JWT con roles (MEMBER, TRAINER, ADMIN)

## Estructura de Servicios
Cada servicio sigue las convenciones de Spring Boot:
- Paquete: `com.gym.{service_name}` (con guion bajo en el nombre, ej., `com.gym.auth_service`)
- Capas: controller/, service/, repository/, models/, dto/
- Dependencias: Spring Web, Data MongoDB, Validation, Eureka Client, Actuator, Lombok
- Configuración: Centralizada vía Config Server (archivos en `gym-config-repo/`), respaldo a local `application.yml`

## Enrutamiento de API
API Gateway enruta las solicitudes:
- `/api/classes/**` → CLASS-SERVICE
- `/api/members/**` → MEMBER-SERVICE
- `/api/auth/**` → AUTH-SERVICE (nota: auth no enrutado vía gateway en config, acceso directo?)
- Similar para otros: notifications, payments, promotions, reservations, trainers

## Configuración
- URL de Config Server: `http://localhost:8888`
- URL de Eureka: `http://localhost:8761/eureka/`
- Puertos: Gateway 8080, Config 8888, Eureka 8761, Auth 8081, Class 8083, Member 8089, etc.
- MongoDB: `mongodb://localhost:27017/{db_name}`
- Secreto JWT compartido entre servicios (codificado en configs)

## Flujo de Construcción y Ejecución
1. Iniciar infraestructura: `./mvnw spring-boot:run` en `gym-config-server`, luego `gym-eureka-server`
2. Iniciar servicios: Ejecutar `./mvnw spring-boot:run` en cada servicio de negocio (el orden no importa debido a Eureka)
3. Iniciar gateway: `./mvnw spring-boot:run` en `gym-api-gateway`
- Usar `mvnw.cmd` en Windows
- Para desarrollo, ejecutar servicios individualmente; para sistema completo, usar el orden anterior
- Verificar el dashboard de Eureka en `http://localhost:8761` para servicios registrados

## Patrones Clave
- **Eventos**: Usar consumidores Kafka en servicios (ej., member-service escucha `PaymentCreatedEvent`)
- **DTOs**: DTOs separados para solicitud/respuesta en paquete `dto/`
- **Validación**: Usar `@Valid` en controladores, anotaciones de Bean Validation
- **Seguridad**: JWT en headers para endpoints protegidos; auth-service genera tokens
- **Logging**: Nivel INFO por defecto; usar endpoints de Actuator para health/métricas
- **Modelos**: MongoDB `@Document` con Lombok `@Data @Builder`

## Tareas Comunes
- Agregar nuevo endpoint: Crear DTO, agregar a controller, implementar en service, actualizar repository si es necesario
- Agregar evento: Definir clase de evento, publicar en servicio productor, consumir en servicio consumidor
- Cambios de config: Editar `gym-config-repo/{service}.yml`, reiniciar servicio
- Base de datos: Cada servicio tiene su propia DB/colección; usar MongoDB Compass para inspección

## Notas
- Auth-service carece de archivo de config; usa local `application.yml`
- Nombres de servicios en Eureka son mayúsculas (ej., CLASS-SERVICE) pero configs minúsculas
- Versiones de Spring Boot varían (3.3.5-3.5.7); Spring Cloud 2025.0.x
- No hay POM padre; cada servicio independiente

## Notas sobre Skills para Agentes de IA
Cada skill requiere su propio archivo SKILL.md con nombre y descripción, pero pueden agruparse en un solo repo para facilitar la gestión y distribución.
