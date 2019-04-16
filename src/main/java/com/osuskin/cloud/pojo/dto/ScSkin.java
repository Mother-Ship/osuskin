package com.osuskin.cloud.pojo.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sc_skin")
public class ScSkin implements CommonDto {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sc_generator")
    @TableGenerator(name = "sc_generator",
            table = "sc_sequence",
            pkColumnName = "table_name",
            valueColumnName = "table_sequence",
            pkColumnValue = "sc_skin",
            allocationSize = 10)
    private Long id;
    @Column(name = "is_deleted")
    private Boolean deleted;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModifyed;
    private String name;
    private Long authorId;
    private Integer mode;
    private String previewImageUrl;
    private Long downloadCount;

}
