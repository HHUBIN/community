package com.example.community.controller;

import com.example.community.dto.CommentDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.model.User;
import com.example.community.service.CommentService;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model,
                           HttpServletRequest request){

        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("questionDTO",questionDTO);
        User user = (User)request.getSession().getAttribute("user");
        model.addAttribute("currentUser",user);

        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("comments",comments);

        questionService.inView(id);
        return "question";
    }
}
