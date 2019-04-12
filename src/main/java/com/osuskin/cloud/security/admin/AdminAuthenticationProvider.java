package com.osuskin.cloud.security.admin;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        String password = (String) authentication.getCredentials();
//        if (CheckUtils.isEmpty(password)) {
//            throw new BadCredentialsException("密码不能为空");
//        }
//        Safety safety = safetyService.load(null, username, null);
//        if (null == safety) {
//            throw new BadCredentialsException("用户不存在");
//        }
//        User user = userService.load(safety.getUid());
//        if (null == user) {
//            throw new BadCredentialsException("用户不存在");
//        }
//        if (password.length() != 32) {
//            password = DigestUtils.md5Hex(password);
//        }
//        if (!password.equals(safety.getPassword())) {
//            throw new BadCredentialsException("用户名或密码不正确");
//        }
//        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(username, password, listUserGrantedAuthorities(user.getId()));
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(username, password);
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

//    private Set<GrantedAuthority> listUserGrantedAuthorities(Long uid) {
//        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        if (CheckUtils.isEmpty(uid)) {
//            return authorities;
//        }
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        return authorities;
//    }

}
