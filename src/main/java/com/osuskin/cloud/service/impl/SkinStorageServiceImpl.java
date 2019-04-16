package com.osuskin.cloud.service.impl;

import com.osuskin.cloud.dao.ScSkinMetadataRepository;
import com.osuskin.cloud.dao.ScSkinRepository;
import com.osuskin.cloud.dao.ScUserSkinRepository;
import com.osuskin.cloud.manager.oss.OssManager;
import com.osuskin.cloud.pojo.bo.StandardResult;
import com.osuskin.cloud.pojo.bo.request.UploadSkinRequest;
import com.osuskin.cloud.pojo.dto.ScSkin;
import com.osuskin.cloud.pojo.dto.ScSkinMetadata;
import com.osuskin.cloud.pojo.dto.ScUserSkin;
import com.osuskin.cloud.security.ScUserDetails;
import com.osuskin.cloud.service.SkinStorageService;
import com.osuskin.cloud.util.CurrentUserUtil;
import com.osuskin.cloud.util.DtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class SkinStorageServiceImpl implements SkinStorageService {
    private OssManager ossManager;
    private ScSkinRepository scSkinRepository;
    private ScSkinMetadataRepository scSkinMetadataRepository;
    private ScUserSkinRepository scUserSkinRepository;

    @Autowired
    public void setOssManager(OssManager ossManager) {
        this.ossManager = ossManager;
    }

    @Autowired
    public void setScSkinRepository(ScSkinRepository scSkinRepository) {
        this.scSkinRepository = scSkinRepository;
    }

    @Autowired
    public void setScSkinMetadataRepository(ScSkinMetadataRepository scSkinMetadataRepository) {
        this.scSkinMetadataRepository = scSkinMetadataRepository;
    }

    @Autowired
    public void setScUserSkinRepository(ScUserSkinRepository scUserSkinRepository) {
        this.scUserSkinRepository = scUserSkinRepository;
    }

    @Override
    @Transactional
    public StandardResult<?> uploadSkin(UploadSkinRequest request) {
        StandardResult result = StandardResult.create();
        ScUserDetails user = CurrentUserUtil.getUserDetails();

        ScSkin skin = new ScSkin();
        DtoUtil.createCommon(skin);

        skin.setAuthorId(request.getAuthorId());
        skin.setDownloadCount(0L);
        skin.setName(request.getName());
        skin.setMode(request.getMode().getCode());
        scSkinRepository.save(skin);

        ScUserSkin scUserSkin = new ScUserSkin();
        DtoUtil.createCommon(scUserSkin);
        scUserSkin.setUserId(user.getId());
        scUserSkin.setSkinId(skin.getId());

        ScUserSkin lastUserSkin = scUserSkinRepository.findFirstByUserIdAndDeletedIsFalseOrderByVersionDesc(user.getId());
        //如果该用户还没有皮肤，版本默认为0
        int version = 0;
        if (lastUserSkin != null) {
            switch (lastUserSkin.getVersion()) {
                case 2:
                    //处理用户已经有三个版本的皮肤的情况，原有的1 2被修改为0 1，原有的0逻辑删除，写定时任务物理删除
                    lastUserSkin.setVersion(1);

                    ScUserSkin version1UserSkin = scUserSkinRepository.findByUserIdAndVersion(user.getId(), 1);
                    version1UserSkin.setVersion(0);

                    ScUserSkin version0UserSkin = scUserSkinRepository.findByUserIdAndVersion(user.getId(), 0);
                    scUserSkinRepository.delete(version0UserSkin);

                    scUserSkinRepository.save(version1UserSkin);
                    scUserSkinRepository.save(lastUserSkin);
                    version = 2;
                    break;
                case 1:
                    //用户只有两个版本皮肤的情况，新皮肤直接设为版本2
                    version = 2;
                    break;
                case 0:
                    //用户只有一个版本皮肤的情况，新皮肤为版本1
                    version = 1;
                    break;
            }
        }
        scUserSkin.setVersion(version);

        ZipEntry entry;
        //可能需要new ZipInputStream(new CheckedInputStream(is, new CRC32()))
        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(request.getData()))) {
            while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
                ScSkinMetadata metadata = new ScSkinMetadata();
                DtoUtil.createCommon(metadata);

                String filename = entry.getName();
                metadata.setFilePath(filename);
                metadata.setSkinId(skin.getId());

                byte[] data = new byte[(int) entry.getSize()];
                //输入流read后 下次read会从上次read结束的地方开始
                zis.read(data, 0, (int) entry.getSize());
                String md5 = ossManager.addSkinComponent(data);
                metadata.setFileMd5Url(md5);
                scSkinMetadataRepository.save(metadata);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.setSuccess(true);
    }

    @Override
    public StandardResult<?> downloadSkin() {
        return null;
    }
}
