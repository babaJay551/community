package com.wujie.community.interceptor;

import com.wujie.community.mapper.UserMapper;
import com.wujie.community.model.User;
import com.wujie.community.model.UserExample;
import com.wujie.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationSevice;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie
        Cookie[] cookies = request.getCookies();
        //判断cookie中是否存在对象 (判断对象是否登录过)
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                //如果cookie中有值 证明用户登录过
                if (cookie.getName().equals("uid")) {
                    String id = cookie.getValue();
                    //通过用户名去查找对象
                    UserExample personExample = new UserExample();
                    personExample.createCriteria().andIdEqualTo(Long.parseLong(id));
                    List<User> userList = userMapper.selectByExample(personExample);
                    if (userList.size() != 0) {
                        //放入session域中  方便前端页面显示
                        request.getSession().setAttribute("user", userList.get(0));
                        //通知数
                        Long unreadCount = notificationSevice.unreadCount(userList.get(0).getId());
                        request.getSession().setAttribute("unreadCount",unreadCount);
                    }
                    break;
                }
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
