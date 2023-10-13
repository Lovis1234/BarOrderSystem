package nl.belastingdienst.barordersystem.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilter {


    JwtRequestFilter jwtRequestFilter;

    public SecurityFilter(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/authenticate").permitAll()

                .requestMatchers("/barkeeper/**").hasRole("STAFF")

                .requestMatchers("/customer/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/drink/**").hasRole("CUSTOMER")
                .requestMatchers("/drink/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/ingredient").hasRole("CUSTOMER")
                .requestMatchers("/ingredient/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.POST, "/order").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.GET, "/order/status/{id}").hasRole("CUSTOMER")
                .requestMatchers("/order/**").hasRole("STAFF")

                .requestMatchers("/db/drinkimage/{id}").hasRole("CUSTOMER")
                .requestMatchers("/getInvoices/{id}").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.POST, "/db").hasRole("STAFF")
                .requestMatchers(HttpMethod.GET, "/download/{filename}").permitAll()
                .requestMatchers("/download/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers("/users/**").hasRole("STAFF")


//                .antMatchers("/**").denyAll()
                .and()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
