package com.osuskin.cloud.pojo.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sc_user_skin")
public class ScUserSkin implements CommonDto {
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sc_generator")
    @TableGenerator(name = "sc_generator",
            table = "sc_sequence",
            pkColumnName = "table_name",
            valueColumnName = "table_sequence",
            pkColumnValue = "sc_user_skin",
            allocationSize = 10)
    @Id
    private Long id;
    @Column(name = "is_deleted")
    private Boolean deleted;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModifyed;
    private Long userId;
    private Long skinId;
    private Integer version;

}
