package com.osuskin.cloud.util;

import com.osuskin.cloud.pojo.dto.CommonDto;

import java.time.LocalDateTime;

public class DtoUtil {
    public static void createCommon(CommonDto dto){
        dto.setDeleted(false);
        dto.setGmtCreated(LocalDateTime.now());
        dto.setGmtModifyed(LocalDateTime.now());
    }
}
