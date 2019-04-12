package com.osuskin.cloud.security.osuoauth;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义登录异常
 */
public class OsuOAuthLoginException extends AuthenticationException {
    public OsuOAuthLoginException(String msg, Throwable t) {
        super(msg, t);
    }

    public OsuOAuthLoginException(String msg) {
        super(msg);
    }
}
