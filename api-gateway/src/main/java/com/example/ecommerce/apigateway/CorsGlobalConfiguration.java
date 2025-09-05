package com.example.ecommerce.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CorsGlobalConfiguration {
    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();

                // ✅ AUTORISER LES DEUX PORTS : 5173 ET 5174
                String origin = request.getHeaders().getOrigin();
                if (origin != null && (
                        origin.equals("http://localhost:5173") ||
                                origin.equals("http://localhost:5174") ||
                                origin.equals("http://127.0.0.1:5173") ||
                                origin.equals("http://127.0.0.1:5174")
                )) {
                    headers.add("Access-Control-Allow-Origin", origin);
                } else {
                    // ✅ FALLBACK POUR DÉVELOPPEMENT
                    headers.add("Access-Control-Allow-Origin", "http://localhost:5173");
                }

                headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS, PATCH");
                // ✅ AJOUTER PLUS DE HEADERS AUTORISÉS
                headers.add("Access-Control-Allow-Headers",
                        "Content-Type, Authorization, X-Requested-With, Accept, Origin, Access-Control-Request-Method, Access-Control-Request-Headers");
                headers.set("Access-Control-Allow-Credentials", "true");
                headers.set("Access-Control-Max-Age", "3600");

                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }
}