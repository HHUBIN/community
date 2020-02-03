package com.example.community.service;

import com.example.community.Mapper.QuestionMapper;
import com.example.community.Mapper.UserMapper;
import com.example.community.Model.Question;
import com.example.community.Model.User;
import com.example.community.dto.PageDTO;
import com.example.community.dto.QuestionDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;

    public PageDTO getPageDTO(Integer page, Integer size) {
        Integer totle = questionMapper.findTotle();
        Integer totlePage;
        if(totle % size == 0){
            totlePage = totle/size;
        }else{
            totlePage = totle/size+1;
        }
        page = page<1?1:page;
        page = page>totlePage?totlePage:page;
        Integer offset = (page-1)*10;
        if(totle == 0)offset=0;
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : questions){
            User user = userMapper.findByid(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PageDTO pageDTO = new PageDTO(page,size,totle,questionDTOList,totlePage);
        return pageDTO;
    }

    public PageDTO findByUserId(Integer page, Integer size,User user) {
        Integer totle = questionMapper.findByUserTotle(user);
        Integer totlePage;
        if(totle % size == 0){
            totlePage = totle/size;
        }else{
            totlePage = totle/size+1;
        }
        page = page<1?1:page;
        page = page>totlePage?totlePage:page;
        Integer offset = (page-1)*10;
        List<Question> questions = questionMapper.listUser(offset,size,user.getId());
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : questions){
            //User user = userMapper.findByid(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PageDTO pageDTO = new PageDTO(page,size,totle,questionDTOList,totlePage);
        return pageDTO;
    }

    public QuestionDTO findById(Integer id) {
        Question question = questionMapper.findById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findByid(question.getCreator());
        questionDTO.setUser(user);
        return  questionDTO;
    }

    public void editOrInsert(Question question, Integer id) {
        if(id == null){
            questionMapper.create(question);
        }else{
            question.setId(id);
            questionMapper.update(question);
        }
    }
}
