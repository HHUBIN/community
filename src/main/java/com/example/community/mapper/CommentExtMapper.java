package com.example.community.mapper;

import com.example.community.model.Comment;
import com.example.community.model.Question;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}