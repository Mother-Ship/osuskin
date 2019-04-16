package com.osuskin.cloud.pojo.bo.request;

import com.osuskin.cloud.enums.OsuGameModeEnum;
import lombok.Data;

import java.io.InputStream;

@Data
public class UploadSkinRequest {
    private InputStream data;
    private Long authorId;
    private OsuGameModeEnum mode;
    private String name;
}
