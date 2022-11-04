package nl.belastingdienst.barordersystem.Security;

import nl.belastingdienst.barordersystem.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()

                .antMatchers("/barkeeper/**").hasRole("STAFF")

                .antMatchers("/customer/**").hasRole("STAFF")

                .antMatchers("/drink").hasRole("CUSTOMER")
                .antMatchers("/drink/price/{id}").hasRole("CUSTOMER")
                .antMatchers("/drink/{id}").hasRole("CUSTOMER")
                .antMatchers("/drink/createCustom").hasRole("CUSTOMER")
                .antMatchers("/drink/**").hasRole("STAFF")

                .antMatchers("/ingredient").hasRole("CUSTOMER")
                .antMatchers("/ingredient/**").hasRole("STAFF")

                .antMatchers("/order/create").hasRole("CUSTOMER")
                .antMatchers("/order/status/{id}").hasRole("CUSTOMER")
                .antMatchers("/order/**").hasRole("STAFF")

                .antMatchers("/drinkimage/{id}").hasRole("CUSTOMER")
                .antMatchers("/getInvoices/{id}").hasRole("CUSTOMER")
                .antMatchers("/upload").hasRole("STAFF")
                .antMatchers("/download/{filename}").permitAll()
                .antMatchers("/multiple/upload/db").hasRole("STAFF")
                .antMatchers("/getAll").hasRole("STAFF")

                .antMatchers("/users/create").permitAll()
                .antMatchers("/users/**").hasRole("STAFF")


                .antMatchers("/**").denyAll()
                .and()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
