package com.api.springsecurity.config.security.authorization;

import com.api.springsecurity.exception.ObjectNotFoundException;
import com.api.springsecurity.persistence.entity.security.Operation;
import com.api.springsecurity.persistence.entity.security.User;
import com.api.springsecurity.persistence.repository.security.OperationRepository;
import com.api.springsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private UserService userService;
    /**
     * Metodo principal que determina si se autoriza o deniega el acceso a un recurso.
     *
     * @param authentication Supplier que proporciona la información de autenticación del usuario
     * @param requestContext Contexto de la petición HTTP que incluye información sobre la URL y metodo
     * @return AuthorizationDecision con true si se permite el acceso, false en caso contrario
     */
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext requestContext) {

        HttpServletRequest request = requestContext.getRequest();

        String url = extractUrl(request);
        String httpMethod = request.getMethod();

        boolean isPublic = isPublic(url, httpMethod);
        if(isPublic){
            return new AuthorizationDecision(true);
        }

        boolean isGranted = isGranted(url, httpMethod, authentication.get());

        return new AuthorizationDecision(isGranted);
    }

    /**
     * Verifica si un usuario autenticado tiene permisos para acceder a un endpoint específico.
     *
     * @param url URL del endpoint solicitado
     * @param httpMethod Metodo HTTP de la petición (GET, POST, PUT, DELETE, etc.)
     * @param authentication Objeto de autenticación del usuario
     * @return true si el usuario tiene permisos, false en caso contrario
     * @throws AuthenticationCredentialsNotFoundException si el usuario no está autenticado correctamente
     */
    private boolean isGranted(String url, String httpMethod, Authentication authentication) {

        if( authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)){
            throw new AuthenticationCredentialsNotFoundException("User not logged in");
        }

        List<Operation> operations = obtainOperations(authentication);

        boolean isGranted = operations.stream().anyMatch(getOperationPredicate(url, httpMethod));

        System.out.println("IS GRANTED: " + isGranted);
        return isGranted;
    }

    /**
     * Crea un predicado que verifica si una operación coincide con la URL y metodo HTTP solicitados.
     * Utiliza expresiones regulares para hacer coincidir patrones de URL.
     *
     * @param url URL solicitada
     * @param httpMethod Metodo HTTP solicitado
     * @return Predicate que evalúa si una Operation coincide con los parámetros dados
     */
    private static Predicate<Operation> getOperationPredicate(String url, String httpMethod) {
        return operation -> {

            String basePath = operation.getModule().getBasePath();

            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
            Matcher matcher = pattern.matcher(url);

            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        };
    }

    /**
     * Obtiene todas las operaciones que un usuario puede realizar basándose en sus permisos de rol.
     *
     * @param authentication Objeto de autenticación que contiene el username
     * @return Lista de operaciones permitidas para el usuario
     * @throws ObjectNotFoundException si el usuario no se encuentra en la base de datos
     */
    private List<Operation> obtainOperations(Authentication authentication) {

        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) authToken.getPrincipal();
        User user = userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));

        return user.getRole().getPermissions().stream()
                .map(grantedPermission -> grantedPermission.getOperation())
                .collect(Collectors.toList());

    }

    /**
     * Verifica si un endpoint es de acceso público (no requiere autenticación).
     *
     * @param url URL del endpoint
     * @param httpMethod Metodo HTTP de la petición
     * @return true si el endpoint es público, false si requiere autenticación
     */
    private boolean isPublic(String url, String httpMethod) {

        List<Operation> publicAccessEndpoints = operationRepository
                .findByPubliccAcces();

        boolean isPublic = publicAccessEndpoints.stream().anyMatch(getOperationPredicate(url, httpMethod));


        System.out.println("IS PUBLIC: " + isPublic);

        return isPublic;
    }

    /**
     * Extrae la URL limpia de una petición HTTP, removiendo el contexto de la aplicación.
     *
     * @param request Petición HTTP
     * @return URL sin el contexto de la aplicación
     */
    private String extractUrl(HttpServletRequest request) {

        String contextPath = request.getContextPath();
        String url = request.getRequestURI();
        url = url.replace(contextPath, "");
        System.out.println(url);

        return url;
    }
}
