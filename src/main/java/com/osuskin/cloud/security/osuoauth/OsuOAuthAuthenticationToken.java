package com.osuskin.cloud.security.osuoauth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 用于存储用户输入字段的类
 */
public class OsuOAuthAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String error;
    private String state;
    private String code;

    /**
     * 仿造UsernamePasswordToken提供两个构造器，带authorities的设为已校验，应当被AuthenticationManager或AuthenticationProvider用来产生已经通过校验的Token
     */
    public OsuOAuthAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String error, String state, String code) {
        super(null,null,authorities);
        this.error = error;
        this.state = state;
        this.code = code;
        super.setAuthenticated(true);
    }

    public OsuOAuthAuthenticationToken(String error, String state, String code) {
        super(null,null,null);
        this.error = error;
        this.state = state;
        this.code = code;
        setAuthenticated(false);
    }

    public String getError() {
        return error;
    }

    public String getState() {
        return state;
    }

    public String getCode() {
        return code;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    /**
     * 追踪父类源码，只有在Credentials对象为CredentialsContainer类时才会起作用，典型用法是SBoot自带的User清空Password
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
