package com.wujie.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDto {

    //封装QuesionDto 的集合
    private List<QuesionDto> quesionDtoList;
    //是否显示上一页
    private boolean showPrevious;
    //是否显示第一页
    private boolean showFirstPage;
    //是否显示下一页
    private boolean showNext;
    //是否显示末页
    private boolean showEndPage;
    //当前页数
    private Integer currentPage;
    //显示的页数码集合
    private List<Integer> pages = new ArrayList<>();
    //总页数
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer currentPage, Integer pageSize) {

        if (totalCount % pageSize == 0){
            totalPage=totalCount/pageSize;
        }else{
            totalPage=totalCount/pageSize+1;
        }

        if (currentPage<1){
            currentPage=1;
        }
        if (currentPage>totalPage){
            currentPage=totalPage;
        }
        this.currentPage=currentPage;

        //将页码添加到集合中
        pages.add(currentPage);
        for (int i=1;i<=3;i++){
            if (currentPage-i>0){
                pages.add(0,currentPage-i);
            }
            if (currentPage+i<=totalPage){
                pages.add(currentPage+i);
            }
        }


        //判断是否显示上一页
        if (currentPage==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }

        //判断是否显示下一页
        if (currentPage==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }

        //判断是否显示首页
        if (pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }

        //判断是否显示末页
        if (pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
