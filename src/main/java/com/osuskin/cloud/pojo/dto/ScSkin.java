package com.osuskin.cloud.pojo.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
@Data
@Entity
public class ScSkin {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Boolean deleted;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModifyed;
    private String name;
    private Long authorId;
    private Integer mode;
    private String previewImageUrl;
    private Long downloadCount;


}
