package com.osuskin.cloud.manager.oss;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OssManagerTest {
    @Autowired
    private OssManager ossManager;

    @Test
    public void testSetValue(){
        System.out.println(ossManager);
    }
}