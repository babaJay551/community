package com.wujie.community.controller;

import com.wujie.community.cache.TagCache;
import com.wujie.community.dto.QuesionDto;
import com.wujie.community.model.Question;
import com.wujie.community.model.User;
import com.wujie.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    /*
    * 更新问题
    * */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Long id,
                       Model model){
        QuesionDto quesion = questionService.findQusById(id);
        model.addAttribute("title",quesion.getTitle());
        model.addAttribute("description",quesion.getDescription());
        model.addAttribute("tag",quesion.getTag());
        model.addAttribute("id",quesion.getId());
        model.addAttribute("tags",TagCache.get());

        return "publish";
    }

    //更新页面
    @GetMapping("/publish")
    public String toPublish(Model model) {
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }

    //发布提问
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value = "id",required = false) Long id,
                            HttpServletRequest request,
                            Model model) {

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",TagCache.get());

        //判断必填文本框
        if (title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null||description==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if (tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入非法标签"+invalid);
            return "publish";
        }

        //将user对象存入session域中
        User user = (User) request.getSession().getAttribute("user");

        //用户未登录回到publish页面
        if (user == null) {
            model.addAttribute("error", "用户未登录！");
            return "publish";
        }

        Question question =new Question();

        //封装参数
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setTag(tag);
        question.setId(id);

        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
