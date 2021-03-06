package api.clicks;

import api.clicks.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class ClicksApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClicksApplication.class, args);
    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception{
            http.cors().and().csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(getApplicationContext()), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/login/**").permitAll()
                    .antMatchers("/logout/**").authenticated()
                    .antMatchers("/players/**").hasAnyRole("ADMIN", "GUEST")
                    .antMatchers("/player/**").hasRole("ADMIN")
                    .antMatchers("/teams/**").hasAnyRole("ADMIN", "GUEST")
                    .antMatchers("/team/**").hasRole("ADMIN")
                    .antMatchers("/localities/**").hasAnyRole("ADMIN", "GUEST")
                    .antMatchers("/locality/**").hasRole("ADMIN")
                    .antMatchers("/provinces/**").hasAnyRole("ADMIN", "GUEST")
                    .antMatchers("/province/**").hasRole("ADMIN")
                    .antMatchers("/countries/**").hasAnyRole("ADMIN", "GUEST")
                    .antMatchers("/country/**").hasRole("ADMIN")
                    .antMatchers("/").permitAll();
        }

        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }


//TODO: genera token pero tambien entra a los datos sin el, una vez genero el token no me sirve
}
