package com.example.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class PageDTO {
    private Integer currentPage;
    private Integer totle;
    private Integer totlePage;
    private boolean fristPage;
    private boolean prePage;
    private boolean lastPage;
    private boolean nextPage;
    List<QuestionDTO> questionDTOList;
    List<Integer> pages = new ArrayList<>();

    public PageDTO(Integer page,Integer size,Integer totle,List<QuestionDTO> questionDTOList,Integer totlePage){
        currentPage = page;
        this.totlePage = totlePage;
        page = page<4?4 :page;
        page = page>totlePage-3?totlePage-3:page;
        page = page-3;
        this.totle = totle;
        this.questionDTOList = questionDTOList;
        if(totlePage<7){
            for(int i = 0; i < totlePage; i++) {
                pages.add(i+1);
            }
        }else {
            for(int i = 0; i < 7; i++) {
                pages.add(page+i);
            }
        }




        fristPage = currentPage == 1 ? false : true;
        lastPage = currentPage == totlePage ? false : true;
        prePage = currentPage == 1 ? false : true;
        nextPage = currentPage == totlePage ? false : true;


    }
}
