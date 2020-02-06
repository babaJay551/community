package com.wujie.community.controller;

import com.wujie.community.utils.VerifyCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/verifyCode")
public class CodeController {

        @RequestMapping(value = "/getImg", method = {RequestMethod.POST, RequestMethod.GET})
        protected void createImg(HttpServletRequest req, HttpServletResponse res) throws IOException {
            //1.生成随机的验证码及图片
            Object[] objs = VerifyCodeUtil.createImage();
            //2.将验证码存入session
            String imgcode = (String) objs[0];
            HttpSession session = req.getSession();
            session.setAttribute("imgcode", imgcode);
            //3.将图片输出给浏览器
            BufferedImage img = (BufferedImage) objs[1];
            res.setContentType("image/png");
            //服务器自动创建输出流，目标指向浏览器
            OutputStream os = res.getOutputStream();
            ImageIO.write(img, "png", os);
            os.close();
        }
    }
