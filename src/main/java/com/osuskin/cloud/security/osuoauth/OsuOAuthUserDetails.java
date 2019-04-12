package com.osuskin.cloud.security.osuoauth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 本应是从数据库取出的User对象，在此处设计为对接osu接口后获取的uid
 */
@Data
@AllArgsConstructor
public class OsuOAuthUserDetails implements UserDetails {
    private Integer id;
    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
    //默认生成的Override方法，用户是锁定+过期+密码过期+禁用的，需要修改
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
