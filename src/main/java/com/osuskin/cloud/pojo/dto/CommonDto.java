package com.osuskin.cloud.pojo.dto;

import java.time.LocalDateTime;

public interface CommonDto {
    Long getId();

    void setDeleted(Boolean deleted) ;

    void setGmtCreated(LocalDateTime gmtCreated) ;

    void setGmtModifyed(LocalDateTime gmtModifyed);

}
