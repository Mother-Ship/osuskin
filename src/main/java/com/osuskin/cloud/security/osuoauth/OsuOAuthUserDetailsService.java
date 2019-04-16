package com.osuskin.cloud.security.osuoauth;

import com.osuskin.cloud.manager.osu.OsuApiV2Manager;
import com.osuskin.cloud.pojo.bo.osuapi.OsuUser;
import com.osuskin.cloud.security.ScUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@ConfigurationProperties(prefix = "admin")
public class OsuOAuthUserDetailsService {
    private List<Integer> list = new ArrayList<>();
    private OsuApiV2Manager osuApiV2Manager;

    @Autowired
    public void setOsuApiV2Manager(OsuApiV2Manager osuApiV2Manager) {
        this.osuApiV2Manager = osuApiV2Manager;
    }

    /**
     * 类似于loadUserByUsername的方法，由于和UserDetailsService差异过大 不进行继承
     * 此处实现为请求osu接口，用auth code换auth token，成功了视为登陆成功
     */
    public ScUserDetails checkByCode(String error, String code, String state) {
        if (Objects.equals("access_denied", error)) {
            throw new OsuOAuthLoginException("用户拒绝了授权");
        }
        OsuUser user = osuApiV2Manager.getTokenAndUser(code);
        if (user == null) {
            throw new OsuOAuthLoginException("授权异常");
        }
        boolean isAdmin = list.contains(user.getId());
        return new ScUserDetails(user.getId(), user.getUsername(), isAdmin);

    }
}
