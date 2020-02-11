package com.wujie.community.controller;

import cn.hutool.core.date.DateUtil;
import com.wujie.community.dto.ResultDto;
import com.wujie.community.enums.ResultCodeEnum;
import com.wujie.community.model.User;
import com.wujie.community.service.UserService;
import com.wujie.community.utils.AppFileUtils;
import com.wujie.community.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toRegister")
    public String toRegister(){
            return "/register.html";
    }

    /**
     * 注册
     * @param user
     * @return
     */

    @PostMapping("/addUser")
    @ResponseBody
    public ResultDto<User> register(@RequestBody User user,HttpServletRequest request){
        //如果传过来的图片是以_temp结尾的话，截取_temp再将数据存入数据库
        if (user.getAvatarUrl()!=null && user.getAvatarUrl().endsWith("_temp")){
            //进行加密，页面传过来的不是明文，是一个哈希值，对哈希再加密
            String s=user.getPassword();
            String smi= MD5Util.MD5(s);
            user.setPassword(smi);
            String avatarUrl = AppFileUtils.rename(user.getAvatarUrl());
            user.setAvatarUrl(avatarUrl);
        }
        try {
            userService.createOrUpdate(user);
            return ResultDto.okOf();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.errorOf(ResultCodeEnum.UNKNOWN_REASON.getCode(),ResultCodeEnum.UNKNOWN_REASON.getMessage());
        }
    }

    /**
     *修改用户信息
     */

    @GetMapping("/editUser")
    public String edit(HttpServletRequest request){
        //拿到session中的用户对象，重新赋值
        User user = (User) request.getSession().getAttribute("user");
        Long id = user.getId();
        HttpSession session = request.getSession();
        User user1 = userService.selectOne(id);
        String password = user1.getPassword();
        String jm = MD5Util.JM(password);
        user1.setPassword(jm);
        session.setAttribute("user",user1);
        return "redirect:/register/toRegister";
    }


    /**
     * 文件上传
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(MultipartFile mf) {
        //1、得到文件名
        String oldName = mf.getOriginalFilename();
        //2、根据文件名生成新文件名
        String newName = AppFileUtils.createNewFileName(oldName);
        //3,得到当前日期的字符串
        String dirName = DateUtil.format(new Date(), "yyyy-MM-dd");
        //4、构造文件夹
        File dirFile = new File(AppFileUtils.UPLOAD_PATH, dirName);
        //5,判断当前文件夹是否存在
        if (!dirFile.exists()) {
            dirFile.mkdirs();//创建新的文件夹
        }
        //6,构造文件对象
        File file=new File(dirFile, newName+"_temp");
        //7,把mf里面的图片信息写入file
        try {
            mf.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("path", dirName+"/"+newName+"_temp");
        return map;
    }

    /**
     * 图片展示
     */
    @RequestMapping("/showImage")
    public ResponseEntity<Object> showImage(String path){

        return AppFileUtils.createResponseEntity(path);
    }

}
