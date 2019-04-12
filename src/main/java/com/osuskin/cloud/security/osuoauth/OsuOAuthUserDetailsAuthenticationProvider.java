package com.osuskin.cloud.security.osuoauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 调用UserDetailsService进行实际密码对比逻辑的地方
 */
@Component
public class OsuOAuthUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private OsuOAuthUserDetailsService osuOAuthUserDetailsService;

    @Autowired
    public void setOsuOAuthUserDetailsService(OsuOAuthUserDetailsService osuOAuthUserDetailsService) {
        this.osuOAuthUserDetailsService = osuOAuthUserDetailsService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }
    @Override
    public boolean supports(Class<?> authentication) {
        return (OsuOAuthAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        OsuOAuthAuthenticationToken auth = (OsuOAuthAuthenticationToken) authentication;
        //本意是在此方法中调用UserDetailsService，根据用户输入用户名在数据库中获取用户，并且从authentication中获取用户输入的密码进行比对，视情况抛出异常
        return osuOAuthUserDetailsService.checkByCode(auth.getError(), auth.getCode(), auth.getState());
    }
}
