package com.api.springsecurity.config.security.filter;


import com.api.springsecurity.exception.ObjectNotFoundException;
import com.api.springsecurity.persistence.entity.security.JwtToken;
import com.api.springsecurity.persistence.entity.security.User;
import com.api.springsecurity.persistence.repository.security.JwtTokenRepository;
import com.api.springsecurity.service.UserService;
import com.api.springsecurity.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtTokenRepository jwtRepository;

    @Autowired
    private UserService userService;


    /**
     * Metodo principal del filtro que se ejecuta para cada petición HTTP.
     *
     * Proceso de filtrado:
     * 1. Extrae el JWT de la petición
     * 2. Valida el token contra la base de datos
     * 3. Si es válido, establece el contexto de autenticación
     * 4. Continúa con la cadena de filtros
     *
     * @param request Petición HTTP entrante
     * @param response Respuesta HTTP saliente
     * @param filterChain Cadena de filtros para continuar el procesamiento
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de E/S
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtService.extractJwtFromRequest(request);
        if(jwt == null || !StringUtils.hasText(jwt)){
            filterChain.doFilter(request, response);
            return;
        }

        Optional<JwtToken> token = jwtRepository.findByToken(jwt);
        boolean isValid = validateToken(token);

        if(!isValid){
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.extractUsername(jwt);
        User user = userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            username, null, user.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    /**
     * Valida un token JWT verificando su existencia, estado y fecha de expiración.
     *
     * @param optionalJwtToken Optional que puede contener el token JWT
     * @return true si el token es válido, false en caso contrario
     */
    private boolean validateToken(Optional<JwtToken> optionalJwtToken) {

        if(!optionalJwtToken.isPresent()){
            System.out.println("Token no existe o no fue generado en nuestro sistema");
            return false;
        }

        JwtToken token = optionalJwtToken.get();
        Date now = new Date(System.currentTimeMillis());
        boolean isValid = token.isValid() && token.getExpiration().after(now);

        if(!isValid){
            System.out.println("Token inválido");
            updateTokenStatus(token);
        }

        return isValid;
    }

    /**
     * Actualiza el estado de un token JWT marcándolo como inválido en la base de datos.
     *
     * Esto es útil para:
     * - Invalidar tokens expirados
     * - Llevar un registro de tokens que ya no deben ser aceptados
     * - Implementar funcionalidades como logout o revocación de tokens
     *
     * @param token Token JWT a marcar como inválido
     */
    private void updateTokenStatus(JwtToken token) {
        token.setValid(false);
        jwtRepository.save(token);
    }


}
