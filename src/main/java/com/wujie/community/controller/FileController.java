package com.wujie.community.controller;

import com.wujie.community.dto.FileDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {

    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDto upload(){
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(1);
        fileDto.setUrl("/images/fm.png");
        return fileDto;
    }
}