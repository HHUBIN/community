package com.example.community.controller;

import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.dto.QuestionDTO;
import com.example.community.model.User;
import com.example.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute(("id"),id);
        return "publish";
    }


    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Long id,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title == null || title == "") {
            model.addAttribute("titleerror","标题不能为空");
            return "publish";
        }
        if(description == null || description == "") {
            model.addAttribute("descriptionerror","内容不能为空");
            return "publish";
        }

        if(tag == null || tag == "") {
            model.addAttribute("tagerror","标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("usererror","用户未登陆");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        questionService.editOrInsert(question);

        if(id == null){
            return "redirect:/";
        }else {
            return "redirect:question/"+id;
        }



    }
}
