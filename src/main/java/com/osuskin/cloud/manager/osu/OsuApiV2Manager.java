package com.osuskin.cloud.manager.osu;

import com.osuskin.cloud.pojo.bo.osuapi.OsuApiToken;
import com.osuskin.cloud.pojo.bo.osuapi.OsuUser;
import com.osuskin.cloud.util.JsonUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.apache.commons.codec.Charsets;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Data
@ConfigurationProperties(prefix = "osuapi")
@Log4j2
public class OsuApiV2Manager {
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    private String OSU_API_TOKEN_URL = "https://osu.ppy.sh/oauth/token";
    private String OSU_API_USER_INFO_URL = "https://osu.ppy.sh/api/v2/me";

    private static final OkHttpClient CLIENT = new OkHttpClient();

    public OsuUser getTokenAndUser(String authorizationCode) {
        FormBody.Builder builder = new FormBody.Builder(Charsets.UTF_8);
        builder.add("grant_type", "authorization_code\n" +
                "&client_id={" + clientId + "}\n" +
                "&client_secret={" + clientSecret + "}\n" +
                "&redirect_uri={" + redirectUri + "}\n" +
                "&code={" + authorizationCode + "}");
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(OSU_API_TOKEN_URL).post(requestBody).build();
        try (Response tokenResponse = CLIENT.newCall(request).execute();) {
            OsuApiToken token = JsonUtil.decode(Objects.requireNonNull(tokenResponse.body()).string(), OsuApiToken.class);
            request = new Request.Builder().url(OSU_API_USER_INFO_URL)
                    .addHeader("Authorization", Objects.requireNonNull(token).getTokenType() + " " + token.getAccesssToken())
                    .build();
            try (Response userResponse = CLIENT.newCall(request).execute()) {
                return JsonUtil.decode(Objects.requireNonNull(userResponse.body()).string(),OsuUser.class);
            }
        } catch (IOException | NullPointerException e) {
            log.error("error in get token and userinfo, authz code:{},error:{}", authorizationCode, e);
            return null;
        }
    }
}
