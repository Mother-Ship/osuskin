package com.osuskin.cloud.security.osuoauth;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 改造自UsernamePasswordFilter，从HttpRequest中获取参数并且做基础的判空
 */
public class OsuOAuthAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_ERROR_KEY = "error";
    public static final String SPRING_SECURITY_FORM_STATE_KEY = "state";
    public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";

    private String errorParameter = SPRING_SECURITY_FORM_ERROR_KEY;
    private String stateParameter = SPRING_SECURITY_FORM_STATE_KEY;
    private String codeParameter = SPRING_SECURITY_FORM_CODE_KEY;
    private boolean postOnly = true;


    public OsuOAuthAuthenticationFilter() {
        super(new AntPathRequestMatcher("/oauth_callback", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String error = obtainError(request);
        String state = obtainState(request);
        String code = obtainCode(request);

        if (error == null) {
            error = "";
        }

        if (state == null) {
            state = "";
        }
        if (code == null) {
            code = "";
        }

        error = error.trim();
        state = state.trim();
        code = code.trim();
        //原有UsernamePasswordToken也需要改造
        OsuOAuthAuthenticationToken authRequest = new OsuOAuthAuthenticationToken(
                error, state, code);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    protected String obtainError(HttpServletRequest request) {
        return request.getParameter(errorParameter);
    }

    protected String obtainState(HttpServletRequest request) {
        return request.getParameter(stateParameter);
    }

    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(codeParameter);
    }

    protected void setDetails(HttpServletRequest request,
                              OsuOAuthAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
