//提交回复
function post() {

    //提问id
    var questionId = $("#question_id").val();
    //评论内容
    var content = $("#comment_content").val();
    //调用评论的方法
    comment2target(questionId, 1, content);

}

/*
* 公共的一二级评论
* */
function comment2target(targetId, type, content) {
//服务器端进行回复内容校验
    if (!content) {
        alert("输入的回复为空哦~");
        return;
    }

    //异步请求
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/comment",
        //将从页面接收过来的字符串转换成json字符串
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            //如何响应成功
            if (response.code == 200) {
                //点击回复按钮重新刷新页面
                window.location.reload();
            } else {
                //如果没有登录就评论，则提示用户先登录
                if (response.code = 2003) {
                    var isAccepted = window.confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=e09fab738cc3cb2c6538&redirect_uri=http://localhost:8087/callback&scope=user&state=1");
                        //在请求时设置一个隐式传递
                        localStorage.setItem("closeable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

/*
* 提交二级评论
* */
function comment(e) {
    //获取到二级评论文本框的id
    var commentId = e.getAttribute("data-id");
    //评论的内容
    var content = $("#input-" + commentId).val();

    //调用评论方法
    comment2target(commentId, 2, content);

    var  n = $("#commentCount").val();
    alert(n);
}


/**
 * 展开二级评论
 * 通过icon的评论id 查找对应的显示二级评论内容的div
 */
function collapseComments(e) {
    //获取到评论icon的id
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //获取二级评论展开的状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active")
    } else {
        var subCommentContainer = $("#comment-" + id);

        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开的状态
            e.setAttribute("data-collapse", "in");
            //高亮显示
            e.classList.add("active");
        } else {
            //获取响应过来的json数据,进行页面封装
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class": "mediaLeft"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading"
                    }).append($("<span/>",{
                        "html": comment.user.name
                    }))).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);

                    //评论框
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });

                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开的状态
                e.setAttribute("data-collapse", "in");
                //高亮显示
                e.classList.add("active");
            });
        }

    }
}

//判断标签是否选中
function selectTag(e) {
    var value=e.getAttribute("data-tag");

    var previous=$("#tag").val();
    
    if (previous.indexOf(value)==-1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}

//是否展示标签栏
function showSelectTag() {
    $("#select-tag").show()
}
