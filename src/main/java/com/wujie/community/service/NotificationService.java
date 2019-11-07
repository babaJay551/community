package com.wujie.community.service;

import com.wujie.community.dto.NotificationDto;
import com.wujie.community.dto.PaginationDto;
import com.wujie.community.dto.QuesionDto;
import com.wujie.community.enums.NotificationStatusEnum;
import com.wujie.community.enums.NotificationTypeEnum;
import com.wujie.community.exception.CustomException;
import com.wujie.community.exception.CustomizeErrorCode;
import com.wujie.community.mapper.NotificationMapper;
import com.wujie.community.model.Notification;
import com.wujie.community.model.NotificationExample;
import com.wujie.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDto list(Long id, Integer currentPage, Integer pageSize) {
        //总页数
        Integer totalPage;
        PaginationDto<NotificationDto> paginationDto =new PaginationDto();
        //查询question总记录数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);

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

        //查询所有的notify
        NotificationExample example = new NotificationExample();
        example.setOrderByClause("gmt_create desc");
        example.createCriteria().andReceiverEqualTo(id);
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,pageSize));

        if (notificationList.size()==0){
            return paginationDto;
        }

        //所有被回复的通知内容
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        //拿到我们的每一条通知 并获取到它是评论回复还是问题回复
        for (Notification notification : notificationList) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDtoList.add(notificationDto);
        }

        paginationDto.setData(notificationDtoList);

        return paginationDto;
    }

    //查询所有的回复数
    public Long unreadCount(Long userId) {

        //统计当前传递过啦的用户的所有通知数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        notificationExample.setOrderByClause("gmt_create desc");
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDto read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification==null){
            throw new CustomException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification,notificationDto);
        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDto;

    }
}
