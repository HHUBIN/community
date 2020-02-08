package com.example.community.service;

import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.QuestionExtMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.dto.PageDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.model.QuestionExample;
import com.example.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    @Resource
    private QuestionExtMapper questionextMapper;

    public PageDTO getPageDTO(Integer page, Integer size) {
        Integer totle = (int) questionMapper.countByExample(new QuestionExample());
        Integer totlePage;
        if(totle % size == 0) {
            totlePage = totle / size;
        } else {
            totlePage = totle / size + 1;
        }
        page = page < 1 ? 1 : page;
        page = page > totlePage ? totlePage : page;
        Integer offset = (page - 1) * 10;
        if(totle == 0) offset = 0;
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PageDTO pageDTO = new PageDTO(page, size, totle, questionDTOList, totlePage);
        return pageDTO;
    }

    public PageDTO findByUserId(Integer page, Integer size, User user) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(user.getId());

        Integer totle = (int) questionMapper.countByExample(questionExample);
        Integer totlePage;
        if(totle % size == 0) {
            totlePage = totle / size;
        } else {
            totlePage = totle / size + 1;
        }
        page = page < 1 ? 1 : page;
        page = page > totlePage ? totlePage : page;
        Integer offset = (page - 1) * 10;
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(user.getId());
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : questions) {
            //User user = userMapper.findByid(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PageDTO pageDTO = new PageDTO(page, size, totle, questionDTOList, totlePage);
        return pageDTO;
    }

    public QuestionDTO findById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void editOrInsert(Question question) {
        if(question.getId() == null) {
            //插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        } else {
            //更新
            Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());
            dbQuestion.setTitle(question.getTitle());
            dbQuestion.setDescription(question.getDescription());
            dbQuestion.setTag(question.getTag());
            dbQuestion.setCreator(question.getCreator());
            dbQuestion.setGmtModified(System.currentTimeMillis());
            int update = questionMapper.updateByPrimaryKey(dbQuestion);

            if(update != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void inView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionextMapper.incView(question);

    }
}
