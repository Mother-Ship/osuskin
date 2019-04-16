package com.osuskin.cloud.pojo.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sc_skin_metadata")
public class ScSkinMetadata implements  CommonDto{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sc_generator")
    @TableGenerator(name = "sc_generator",
            table = "sc_sequence",
            pkColumnName = "table_name",
            valueColumnName = "table_sequence",
            pkColumnValue = "sc_skin_metadata",
            allocationSize = 10)
    private Long id;
    @Column(name = "is_deleted")
    private Boolean deleted;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModifyed;
    private Long skinId;
    private String filePath;
    private String fileMd5Url;

}
