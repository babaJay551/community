package com.wujie.community.controller;

import com.wujie.community.dto.PaginationDto;
import com.wujie.community.model.User;
import com.wujie.community.service.NotificationService;
import com.wujie.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationSevice;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {

        //获取session中的用户
        User user = (User) request.getSession().getAttribute("user");

        /*
         * 这里的user是需要进行判断是否为空,由于我要先把页面功能完成就注释掉了
         * 等完成页面功能再将它注释删掉
         * */
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectioName", "我的提问");
             /*
                这里31是需要用user.getId();替换的 由于登录出现未知问题还需调试
                所以先手动修改，让页面功能显示做完再改回来
             */
            PaginationDto paginationDto = questionService.findListByUserId(user.getId(), currentPage, pageSize);
            model.addAttribute("pagination", paginationDto);
        } else if ("replies".equals(action)) {
            PaginationDto paginationDto=notificationSevice.list(user.getId(),currentPage,pageSize);

            model.addAttribute("section", "replies");
            model.addAttribute("pagination", paginationDto);
            model.addAttribute("sectioName", "最新回复");
        }
        return "profile";

    }
}
