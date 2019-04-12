package com.osuskin.cloud.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Log4j2
@Controller
public class UploadSkinController {



    @RequestMapping(value = "/uploadFile.json", method = RequestMethod.POST)
    public ModelAndView uploadFileAction(@RequestParam("uploadFile") MultipartFile uploadFile, @RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploadAndDownload");

        try (InputStream fis = uploadFile.getInputStream();
             OutputStream outputStream = new FileOutputStream("G:\\uploadfile\\" + uploadFile.getOriginalFilename());) {
            IOUtils.copy(fis, outputStream);
            modelAndView.addObject("sucess", "上传成功");
            return modelAndView;
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelAndView.addObject("sucess", "上传失败!");
        return modelAndView;
    }


    @RequestMapping("/downloadFile.json")
    public void downloadFileAction(HttpServletRequest request, HttpServletResponse response) {

        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        File file = new File("G:\\config.ini");
        try (FileInputStream fis = new FileInputStream(file);) {
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {

        }
    }

}

