package com.example.community.controller;

import com.example.community.dto.QuestionDTO;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model,
                           HttpServletRequest request){

        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("currentUser",request.getSession().getAttribute("user"));
        return "question";
    }
}
