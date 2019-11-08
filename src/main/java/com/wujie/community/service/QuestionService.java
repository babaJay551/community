package com.wujie.community.service;

import com.wujie.community.dto.PaginationDto;
import com.wujie.community.dto.QuesionDto;
import com.wujie.community.dto.QuestionQueryDto;
import com.wujie.community.exception.CustomException;
import com.wujie.community.exception.CustomizeErrorCode;
import com.wujie.community.mapper.QuestionExtMapper;
import com.wujie.community.mapper.QuestionMapper;
import com.wujie.community.mapper.UserMapper;
import com.wujie.community.model.Question;
import com.wujie.community.model.QuestionExample;
import com.wujie.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;


    //分页查询业务方法
    public PaginationDto list(String search,Integer currentPage, Integer pageSize){

        //获取用户查询的关键字内容
        if (StringUtils.isNotBlank(search)){
            //根据的空格进行拆分关键字
            String[] searchChats = StringUtils.split(search," ");
            search = Arrays.stream(searchChats).collect(Collectors.joining("|"));
        }

        //总页数
        Integer totalPage;
        PaginationDto paginationDto =new PaginationDto();
        //查询question总记录数
        QuestionQueryDto questionQueryDto = new QuestionQueryDto();
        questionQueryDto.setSearch(search);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDto);

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
        //需要一个非业务层的方法来 获取想要的参数 例如：totalPage总页数
        paginationDto.setPagination(currentPage,totalPage);

        //通过页面传递过来的 当前页码 与页大小
        Integer offset = (currentPage-1)*pageSize;
        if (offset<1){
            offset=1;
        }

        //查询所有的question
        QuestionQueryDto questionQuery = new QuestionQueryDto();
        questionQuery.setSearch(search);
        questionQuery.setOffset(offset);
        questionQuery.setPageSize(pageSize);
        List<Question> questionList = questionExtMapper.selectBySearchWithRowbounds(questionQuery);
        List<QuesionDto> quesionDtoList = new ArrayList<>();

        for (Question question : questionList) {
            //通过question表关联的creator查询user
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuesionDto quesionDto = new QuesionDto();
            //将左边的字段全部赋值到右边字段表
            BeanUtils.copyProperties(question,quesionDto);
            quesionDto.setUser(user);
            quesionDtoList.add(quesionDto);
        }
        paginationDto.setData(quesionDtoList);

        return paginationDto;
    }

    /*
    个人显示页面业务方法
    */
    public PaginationDto findListByUserId(Long userId, Integer currentPage, Integer pageSize) {

        //总页数
        Integer totalPage;
        PaginationDto paginationDto =new PaginationDto();
        //查询question总记录数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);

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

        //需要一个非业务层的方法来 获取想要的参数 例如：totalPage总页数
        paginationDto.setPagination(currentPage,totalPage);

        //通过页面传递过来的 当前页码 与页大小
        Integer offset = (currentPage-1)*pageSize;

        //查询所有的question
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,pageSize));
        List<QuesionDto> quesionDtoList = new ArrayList<>();

        for (Question question : questionList) {
            //通过question表关联的creator查询user
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuesionDto quesionDto = new QuesionDto();
            //将左边的字段全部赋值到右边字段表
            BeanUtils.copyProperties(question,quesionDto);
            quesionDto.setUser(user);
            quesionDtoList.add(quesionDto);
        }
        paginationDto.setData(quesionDtoList);

        return paginationDto;
    }

    /*
    * 通过id查询question
    * */
    public QuesionDto findQusById(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuesionDto quesionDto = new QuesionDto();
        BeanUtils.copyProperties(question,quesionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        quesionDto.setUser(user);
        return quesionDto;
    }

    /*
    * 创建或修改方法
    * */
    public void createOrUpdate(Question question) {

        //判断question的唯一标识id 是否为空
        if (question.getId()==null){
            //如果为空则进行数据添加操作
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            //添加
            questionMapper.insert(question);
        }else{
            //如果不为空 则进行更新操作
            question.setGmtModified(System.currentTimeMillis());
            question.setTitle(question.getTitle());
            question.setDescription(question.getDescription());
            question.setTag(question.getTag());
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int rows = questionMapper.updateByExampleSelective(question, example);
            if (rows!=1){
                throw new CustomException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /*
    * 累加阅读数
    * */
    public void incView(Long id){
        Question question =new Question();
        //每次点击查看question都加1
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    /*
    * 查询相关问题
    * */
    public List<QuesionDto> selectRelated(QuesionDto queryDto) {
        if (StringUtils.isBlank(queryDto.getTag())){
            return new ArrayList<>();
        }

        //获取每一个tag
        String[] tags = StringUtils.split(queryDto.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        //封装参数
        Question question = new Question();
        question.setId(queryDto.getId());
        question.setTag(regexpTag);
        //查询tag
        List<Question> questions = questionExtMapper.selectByRelated(question);

        List<QuesionDto> quesionDTOS = questions.stream().map(q -> {
            QuesionDto quesionDto = new QuesionDto();
            BeanUtils.copyProperties(q, quesionDto);
            return quesionDto;
        }).collect(Collectors.toList());

        return quesionDTOS;
    }
}
