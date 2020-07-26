/**
 * 作者: lwh
 * 时间: 2020.4.13
 * 描述: 添加管理员页面初始化----------
 */
function index_body_addAdmin_init() {
    //重置按钮的点击事件监听函数注册
    $("#addAdminResetButton").click(function () {
        $("#addAdmin-form").data("bootstrapValidator").resetForm(true);
        //更改添加按钮的状态
        $("#addAdminButton").button("reset");
    });

    //开启bootstrapValidator进行表单验证
    $("#addAdmin-form").bootstrapValidator({
        message: "*输入不合法",
        feedbackIcons: {
            valid: "glyphicon glyphicon-ok",
            invalid: "glyphicon glyphicon-remove",
            validating: "glyphicon glyphicon-refresh"
        },
        fields: {
            newUserName: {
                message: "*用户名不合法",
                validators: {
                    notEmpty: {
                        message: "*用户名不能为空"
                    },
                    stringLength: {
                        min: 6,
                        max: 10,
                        message: "*用户名长度为6-10（包含）"
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_.]+$/,
                        message: "*用户名只能包含字母、数字、下划线和点"
                    },
                    //重定向校验器，向后端发送ajax请求
                    //后端返回一个包含验证结果和消息的json字符串-------------------
                    remote: {
                        url: "",
                        type: "post",
                        message: "该用户名已存在"
                    }
                }
            },
            newUserPassword: {
                message: "*密码不合法",
                validators: {
                    notEmpty: {
                        message: "*密码不能为空"
                    },
                    stringLength: {
                        min: 6,
                        max: 6,
                        message: "*密码必须为6位"
                    },
                    //不能和指定输入域值相同
                    different: {
                        field: "newUserName",
                        message: "*密码不能和用户名相同"
                    }
                }
            },
            newUserPasswordConfirm: {
                message: "*密码确认失败",
                validators: {
                    notEmpty: {
                        message: "*请再次输入密码"
                    },
                    //必须与指定输入域的值相同
                    identical: {
                        field: "newUserPassword",
                        message: "*两次输入的密码不相同"
                    }
                }
            },
            newUserEmail: {
                message: "*邮箱不合法",
                validators: {
                    notEmpty: {
                        message: "*邮箱不能为空"
                    },
                    //邮箱地址合法性验证
                    emailAddress: {
                        message: "*请输入合法的邮箱地址"
                    }
                }
            }
        }
    }).on("success.form.bv", function (e) {
        //注册表单被提交后且验证成功的事件的监听函数以使用ajax提交表单数据
        //阻止正常提交表单
        e.preventDefault();
        //更改添加按钮的登陆状态
        $("#addAdminButton").button("loading");
        //获取信息
        let newUserName = $("input[name='newUserName']").val();
        let newUserPassword = $("input[name='newUserPassword']").val();
        let newUserEmail = $("input[name='newUserEmail']").val();
        let jsonStr = JSON.stringify({
            newUserName: newUserName,
            newUserPassword: newUserPassword,
            newUserEmail: newUserEmail
        });
        //使用ajax提交表单进行注册
        $.ajax({
            url: "addAdmin",
            type: "post",
            data: jsonStr,
            contentType: "application/json;charset=utf-8",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data === "0")
                    myAlert("啊欧，添加管理员失败了(x_x)!");
                else
                    myAlert("管理员添加成功(∩_∩)!");
                //更改添加按钮的修改状态
                $("#addAdminButton").button("reset");
                //初始化表单
                $("#addAdmin-form").data("bootstrapValidator").resetForm(true);
            },
            error: function (error) {
                alert("----ajax请求添加管理员执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    });
}