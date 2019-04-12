package com.osuskin.cloud.service;

import com.osuskin.cloud.pojo.bo.StandardResult;

public interface FileStorageService {
    StandardResult<?> uploadSkin();
    StandardResult<?> downloadSkin();

}
