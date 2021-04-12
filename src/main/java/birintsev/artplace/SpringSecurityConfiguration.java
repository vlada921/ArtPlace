package birintsev.artplace;

import birintsev.artplace.model.db.Authority;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final AuthenticationProvider authenticationProvider;

    public SpringSecurityConfiguration(
        @Qualifier("UserRepo")
            UserDetailsService userDetailsService,
        AuthenticationProvider authenticationProvider
    ) {
        this.userDetailsService = userDetailsService;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**")
                .hasAuthority(Authority.ADMIN.getName())
            .antMatchers("/registration/**")
                .not()
                .authenticated()
                .and()
            .formLogin()
                .permitAll()
                .and()
            .logout()
                .permitAll();

        http.userDetailsService(userDetailsService);
        http.authenticationProvider(authenticationProvider);
    }
}
