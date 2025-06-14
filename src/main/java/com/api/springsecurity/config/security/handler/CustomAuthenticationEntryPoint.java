package com.api.springsecurity.config.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * Maneja errores de autenticación delegando al AccessDeniedHandler.
     *
     * Esta implementación trata los errores de autenticación (401) como
     * errores de acceso denegado (403) para simplificar el manejo de errores
     * y mantener un formato de respuesta consistente.
     *
     * @param request Petición HTTP que falló en autenticación
     * @param response Respuesta HTTP a personalizar
     * @param authException Excepción de autenticación que ocurrió
     * @throws IOException Si hay problemas escribiendo la respuesta
     * @throws ServletException Si hay errores en el servlet
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        accessDeniedHandler.handle(request, response, new AccessDeniedException("Access Denied"));

    }
}
