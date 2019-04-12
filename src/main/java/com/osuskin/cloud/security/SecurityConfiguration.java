package com.osuskin.cloud.security;


import com.osuskin.cloud.security.admin.AdminAuthenticationFilter;
import com.osuskin.cloud.security.admin.AdminAuthenticationProvider;
import com.osuskin.cloud.security.osuoauth.OsuOAuthAuthenticationFilter;
import com.osuskin.cloud.security.osuoauth.OsuOAuthUserDetailsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private OsuOAuthUserDetailsAuthenticationProvider osuOAuthUserDetailsAuthenticationProvider;

    @Autowired
    public void setOsuOAuthUserDetailsAuthenticationProvider(OsuOAuthUserDetailsAuthenticationProvider osuOAuthUserDetailsAuthenticationProvider) {
        this.osuOAuthUserDetailsAuthenticationProvider = osuOAuthUserDetailsAuthenticationProvider;
    }


    private AdminAuthenticationProvider adminAuthenticationProvider;

    @Autowired
    public void setAdminAuthenticationProvider(AdminAuthenticationProvider adminAuthenticationProvider) {
        this.adminAuthenticationProvider = adminAuthenticationProvider;
    }


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(osuOAuthUserDetailsAuthenticationProvider)
                .authenticationProvider(adminAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //加入对osu api回调的校验
                .addFilterBefore(osuOAuthAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(adminAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/css/**", "/", "/loginpage","/oauth_callback","/login.json").permitAll()

                .antMatchers("/**").authenticated()
                .and()
                .formLogin().loginPage("/loginpage")
                .and()
                .logout()
                .logoutUrl("/logout");
    }


    public OsuOAuthAuthenticationFilter osuOAuthAuthenticationFilter() throws Exception {
        OsuOAuthAuthenticationFilter filter = new OsuOAuthAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    public AdminAuthenticationFilter adminAuthenticationFilter() throws Exception {
        AdminAuthenticationFilter filter = new AdminAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

}