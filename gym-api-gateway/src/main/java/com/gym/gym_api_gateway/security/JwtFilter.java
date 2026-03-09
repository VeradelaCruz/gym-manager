package com.gym.gym_api_gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//JwtFilter intercepta todas las requests que llegan al gateway
//Hace lo siguiente:
//Extrae el Authorization header
//Saca el token
//Llama a jwtService.validateToken(token)
//Comprueba firma con secret
//Comprueba expiración
@Component
@RequiredArgsConstructor
//GatewayFilter → interfaz de Spring Cloud Gateway que permite
// interceptar todas las requests antes de enviarlas a los microservicios.
public class JwtFilter implements GatewayFilter {

    private final JwtService jwtService;

    @Override
    //ServerWebExchange exchange → representa la petición y la respuesta HTTP
    //GatewayFilterChain chain → representa la cadena de filtros que la request
    // seguirá si pasa la validación
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtService.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }
}