package com.example.community.controller;

import com.example.community.dto.PageDTO;
import com.example.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 */
@Controller
public class IndexController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "10") Integer size){
        PageDTO pageDTO = questionService.getPageDTO(page,size);
        model.addAttribute("page",pageDTO);
        return "index";
    }
    @GetMapping("/outLogin")
    public String outLogin(HttpServletRequest request,
                           HttpServletResponse response){
        request.getSession().removeAttribute("user");
        response.addCookie((new Cookie("token","0")));
        return "redirect:/";
    }
}
