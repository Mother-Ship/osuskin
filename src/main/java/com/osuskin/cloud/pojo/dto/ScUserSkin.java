package com.osuskin.cloud.pojo.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.TableGenerator;
import java.time.LocalDateTime;

@Data

public class ScUserSkin implements CommonDto {
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sc_generator")
    @TableGenerator(name = "sc_generator",
            table = "sc_sequence",
            pkColumnName = "table_sequence",
            valueColumnName = "table_name",
            pkColumnValue = "sc_user_skin",
            allocationSize = 10)
    private Long id;
    private Boolean deleted;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModifyed;
    private Long userId;
    private Long skinId;
    private Integer version;

}
