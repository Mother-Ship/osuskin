package com.osuskin.cloud.service;

import com.osuskin.cloud.pojo.bo.StandardResult;
import com.osuskin.cloud.pojo.bo.request.UploadSkinRequest;

public interface SkinStorageService {
    StandardResult<?> uploadSkin(UploadSkinRequest request);
    StandardResult<?> downloadSkin();

}
