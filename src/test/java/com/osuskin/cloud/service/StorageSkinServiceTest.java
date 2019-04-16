package com.osuskin.cloud.service;

import com.osuskin.cloud.enums.OsuGameModeEnum;
import com.osuskin.cloud.pojo.bo.request.UploadSkinRequest;
import com.osuskin.cloud.security.ScUserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
@RunWith(SpringRunner.class)
@SpringBootTest

public class StorageSkinServiceTest {
    private SkinStorageService skinStorageService;

    @Autowired
    public void setSkinStorageService(SkinStorageService skinStorageService) {
        this.skinStorageService = skinStorageService;
    }

    @Test
    public void testUpload() throws FileNotFoundException {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(new ScUserDetails(2545898L,"Mother Ship",true));
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        UploadSkinRequest request = new UploadSkinRequest();
        FileInputStream is = new FileInputStream("C:\\Users\\QHS\\Downloads\\Cookiezi 36 2018-11-23 Rafis Edit.osk");
        request.setMode(OsuGameModeEnum.STD);
        request.setName("Cookiezi 36 2018-11-23 Rafis Edit");
        request.setData(is);
        skinStorageService.uploadSkin(request);
    }
}
