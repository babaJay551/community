/*$().ready(function() {
    $("#login_form").validate({
        rules: {
            username: "required",
            password: {
                required: true,
                minlength: 5
            },
        },
        messages: {
            username: "请输入姓名",
            password: {
                required: "请输入密码",
                minlength: jQuery.format("密码不能小于{0}个字 符")
            },
        }
    });
    $("#register_form").validate({
        rules: {
            username: "required",
            password: {
                required: true,
                minlength: 5
            },
            rpassword: {
                equalTo: "#register_password"
            },
            email: {
                required: true,
                email: true
            }
        },
        messages: {
            username: "请输入姓名",
            password: {
                required: "请输入密码",
                minlength: jQuery.format("密码不能小于{0}个字 符")
            },
            rpassword: {
                equalTo: "两次密码不一样"
            },
            email: {
                required: "请输入邮箱",
                email: "请输入有效邮箱"
            }
        }
    });
});
$(function() {
    $("#register_btn").click(function() {
        $("#register_form").css("display", "block");
        $("#login_form").css("display", "none");
    });
    $("#back_btn").click(function() {
        $("#register_form").css("display", "none");
        $("#login_form").css("display", "block");
    });
});*/

function login() {

    var username = $("#username").val();
    var password = $("#password").val();
    var code = $("#verify_code").val();

/*
    var formData = new FormData();
    formData.append(username);
    formData.append(password);
    formData.append(code);
*/

    $.ajax({
        url:'/login',
        contentType: "application/json",
        dataType:'json',
        data:{
            'username':username,
            'password':password,
            'code':code
        }/*,
        success:function (res) {
            if (res){
                alert("登录成功!")
            }else{
                alert("登录失败!")
            }
        }*/
    })


}