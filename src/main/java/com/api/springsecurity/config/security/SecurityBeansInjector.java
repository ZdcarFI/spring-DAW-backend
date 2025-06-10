package com.api.springsecurity.config.security;

import com.api.springsecurity.exception.ObjectNotFoundException;
import com.api.springsecurity.persistence.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {

    @Autowired
    private UserRepository userRepository;


    /**
     * Bean del gestor de autenticación principal.

     * El AuthenticationManager es el componente central que coordina todo el proceso de autenticación en Spring Security.

     *
     * @param authenticationConfiguration Configuración de autenticación de Spring
     * @return AuthenticationManager configurado
     * @throws Exception Si hay errores en la configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean del proveedor de autenticación .
     *
     * DaoAuthenticationProvider es una implementación que:
     * 1. Usa UserDetailsService para cargar datos del usuario
     * 2. Usa PasswordEncoder para verificar contraseñas
     * 3. Maneja la lógica de autenticación completa
     *
     * @return AuthenticationProvider configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder( passwordEncoder() );
        authenticationStrategy.setUserDetailsService( userDetailsService() );

        return authenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean del servicio de detalles de usuario.
     *
     * UserDetailsService es una interfaz funcional que define cómo
     * Spring Security debe cargar la información de un usuario dado su username.
     *
     * Esta implementación:
     * 1. Busca el usuario en la base de datos por username
     * 2. Si lo encuentra, lo retorna (el User debe implementar UserDetails)
     * 3. Si no lo encuentra, lanza una excepción
     *
     * @return UserDetailsService que busca en la base de datos
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new ObjectNotFoundException("User not found with username " + username));
        };
    }

}
