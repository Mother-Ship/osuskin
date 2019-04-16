package com.osuskin.cloud.dao;

import com.osuskin.cloud.pojo.dto.ScUserSkin;


public interface ScUserSkinRepository extends SoftDeleteCrudRepository<ScUserSkin, Long> {
    ScUserSkin findFirstByUserIdAndDeletedIsFalseOrderByVersionDesc(Long userId);
    ScUserSkin findByUserIdAndVersion(Long userId,Integer version);
}
