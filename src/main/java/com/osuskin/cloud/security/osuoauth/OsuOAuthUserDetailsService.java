package com.osuskin.cloud.security.osuoauth;

import com.osuskin.cloud.pojo.bo.osuapi.OsuUser;
import com.osuskin.cloud.manager.osu.OsuApiV2Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OsuOAuthUserDetailsService  {
    private OsuApiV2Manager osuApiV2Manager;
    @Autowired
    public void setOsuApiV2Manager(OsuApiV2Manager osuApiV2Manager){
        this.osuApiV2Manager = osuApiV2Manager;
    }

    /**
     * 类似于loadUserByUsername的方法，由于和UserDetailsService差异过大 不进行继承
     * 此处实现为请求osu接口，用auth code换auth token，成功了视为登陆成功
     */
    public OsuOAuthUserDetails checkByCode(String error, String code, String state){
        if (Objects.equals("access_denied", error)) {
            throw new OsuOAuthLoginException("用户拒绝了授权");
        }
        OsuUser user = osuApiV2Manager.getTokenAndUser(code);
        if(user== null){
            throw new OsuOAuthLoginException("授权异常");
        }
        return new OsuOAuthUserDetails(user.getId(),user.getUsername());
//        return new OsuOAuthUserDetails(2545898,"Mother Ship");
    }
}
