package org.example.expense_tracker_application.configuration;

import org.example.expense_tracker_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// it indicates that this class contains bean configuration for our security settings in our spring boot application
@EnableWebSecurity // a Spring Security annotation used to enable web security in a Spring application.
public class SecurityConfiguration {
    @Bean
    // Annotates this method as a bean that Spring should manage
    public UserDetailsService userDetailsService(UserService userService) {
        return userService;
        // Return the UserService (which implements UserDetailService) as the UserDetailsService

    }
    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //allows us to define the chain of security filters
        // This method configures the HttpSecurity object, which allows customization of security settings for HTTP requests.

        http

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // Permit all requests to the home page and static resources
                        .requestMatchers("/users/new", "/login", "/users", "/verify", "/assign-admin").permitAll()
                        .requestMatchers("/currency-converter").hasRole("ADMIN")
                        // Require authentication for any other request
                        .anyRequest().authenticated()

                )
                .formLogin(formLogin -> formLogin // first page is to be the login page

                        .loginPage("/login") // Specifies the login page URL

                        // Redirect to the home page ("/") after successful login, always redirecting even if the user was previously navigating elsewhere
                        .defaultSuccessUrl("/", true)
                        .permitAll() // Allow everyone to see the login page
                )

                .logout(logout -> logout
                        .logoutUrl("/logout") // Specifies the logout page URL
                        // Redirect to the login page with a logout parameter after a successful logout
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONNID") // Deletes the JSESSIONNID cookie upon logout
                        .invalidateHttpSession(true) // Invalidates the HTTP session after logout to prevent session fixation attacks.
                        .permitAll()
                );
        // Builds and returns the SecurityFilterChain
        return http.build();

    }

    @Autowired
    // Configures the global authentication manager
    public void configureGlobal(
            AuthenticationManagerBuilder auth, // Builder for creating an AuthenticationManager
            UserDetailsService userDetailsService // Service to load user-specific data
    ) throws Exception {
        // This method can throw various exceptions, such as:
        // - ConfigurationException: If there's an error configuring the AuthenticationManagerBuilder
        // - UsernameNotFoundException: If the UserDetailsService cannot find a user by the given username

        auth
                .userDetailsService(userDetailsService) // Uses the provided UserDetailsService to load user details
                .passwordEncoder(new BCryptPasswordEncoder()); // Sets the password encoder to BCrypt for encoding and matching passwords

    }
}



/*
    @Bean // implicates that the method password Encoder returns a new instance of the password encoder class
    A bean in Spring is an object created, configured, and managed by the Spring IoC container.
    Inversion of Control (IoC) is a design principle in software engineering where the control of object creation
    and dependency management is transferred from the application code to a container or framework.
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity httpSecurity)  {}
    protected meaning it can be accessible only within the package
    or subclasses even if the subclasses aren't in the package.
*/
