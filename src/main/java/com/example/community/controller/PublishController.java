package com.example.community.controller;

import com.example.community.Mapper.QuestionMapper;
import com.example.community.Mapper.UserMapper;
import com.example.community.Model.Question;
import com.example.community.Model.User;
import com.example.community.dto.QuestionDTO;
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
    public String edit(@PathVariable(name = "id") Integer id,
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
            @RequestParam("id") Integer id,
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

        Cookie[] cookies = request.getCookies();
        User user = null;
        if(cookies != null){
            for(Cookie cookie : cookies)
            {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user != null){
                        request. getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if(user == null){
            model.addAttribute("usererror","用户未登陆");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionService.editOrInsert(question,id);

        return "redirect:/";


    }
}
