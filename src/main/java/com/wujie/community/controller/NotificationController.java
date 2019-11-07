package com.wujie.community.controller;

import com.wujie.community.dto.NotificationDto;
import com.wujie.community.enums.NotificationTypeEnum;
import com.wujie.community.model.User;
import com.wujie.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id")Long id) {

        //获取session中的用户
        User user = (User) request.getSession().getAttribute("user");

        //未登录直接回首页
        if (user == null) {
            return "redirect:/";
        }

        NotificationDto notificationDto = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDto.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDto.getType()) {

            return "redirect:/question/" + notificationDto.getOuterid();
        }
        return "redirect:/";
    }

}
