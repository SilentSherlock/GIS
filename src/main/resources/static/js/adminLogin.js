/**
 * 作者: lwh
 * 时间: 2020.2.26
 * 描述: 管理员登陆界面的相关初始化js
 */
$(document).ready(function () {
    //用来保存账号密码的cookie名称变量
    let cname = "savedAccInfo";
    //用来判断是否已设置cookie
    let isSaved = false;
    let base64_savedUserName = "";

    //查询cookie是否存在记住的账号密码
    let base64_accInfo = getCookie(window.btoa(cname));
    if (base64_accInfo !== null) {
        //存在记住的账号密码
        let arr = base64_accInfo.split("_");
        //解码获取账号密码
        let userName = window.atob(arr[0]);
        let password = window.atob(arr[1]);
        //自动填充
        $("input[name='userNameInput']").val(userName);
        $("input[name='passwordInput']").val(password);
        isSaved = true;
        base64_savedUserName = arr[0];
    }

    //开启bootstrap工具提示插件
    $("[data-toggle='tooltip']").tooltip();

    //重置按钮的点击事件监听函数注册
    $("#resetButton").click(function () {
        $("#loginForm").data("bootstrapValidator").resetForm(true);
        //取消记住密码的选择，因为记住密码的checkbox未添加bootstrapValidator验证
        $("input[name='rememberCheckbox']").prop("checked", false);
        //更改登录按钮的登陆状态
        $("#loginButton").button("reset");
    });

    //开启bootstrapValidator进行表单验证
    $("#loginForm").bootstrapValidator({
        message: "*输入不合法",
        feedbackIcons: {
            valid: "glyphicon glyphicon-ok",
            invalid: "glyphicon glyphicon-remove",
            validating: "glyphicon glyphicon-refresh"
        },
        fields: {
            userNameInput: {
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
                    }
                }
            },
            passwordInput: {
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
                        field: "userNameInput",
                        message: "*密码不能和用户名相同"
                    }
                }
            }
        }
    }).on("success.form.bv", function (e) {
        //注册表单被提交后且验证成功的事件的监听函数以使用ajax提交表单数据
        //阻止正常提交表单
        e.preventDefault();
        //更改登录按钮的登陆状态
        $("#loginButton").button("loading");
        //获取bootstrapValidator实例
        //let bv = $(e.target).data("bootstrapValidator");
        //用base64加密用户名和密码
        let base64_userName = window.btoa($("input[name='userNameInput']").val());
        let base64_password = window.btoa($("input[name='passwordInput']").val());
        let jsonStr = JSON.stringify({
            adminName: base64_userName,
            password: base64_password
        });
        //使用ajax提交表单验证用户名密码
        $.ajax({
            url: "adminValidate",
            type: "post",
            data: jsonStr,
            contentType: "application/json;charset=utf-8",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data === "0") {
                    //验证失败，提示用户验证失败
                    myAlert("用户名或密码错误(x_x)！");
                    /*更改用户名密码为未验证状态，对模态框的生命周期时间进行监听，当模态框消失时触发操作
                    loginErrorModal.on("hide.bs.modal", function (e) {
                        bv.updateStatus("userNameInput", "NOT_VALIDATED");
                        bv.updateStatus("passwordInput", "NOT_VALIDATED");
                    });
                    */
                    //更改登录按钮的登陆状态
                    $("#loginButton").button("reset");
                } else {
                    //将用户信息保存到sessionStorage中
                    let loggedAdminInfo = data.adminName + "_" + data.password + "_" + data.email;
                    let jsonObj = {
                        loggedAdminInfo: loggedAdminInfo
                    };
                    saveData2Ses(jsonObj);
                    //登陆成功
                    if ($("input[name='rememberCheckbox']").prop("checked")) {
                        //需要记住密码,判断是否已经设置cookie，有的话不必设置cookie
                        if (!isSaved) {
                            //没有已保存到cookie中的账户信息，设置cookie
                            let base64_cname = window.btoa(cname);
                            let base64_cvalue = base64_userName + "_" + base64_password;
                            setCookie(base64_cname, base64_cvalue, -1);
                        } else if (base64_userName !== base64_savedUserName) {
                            //当前输入的账户信息与已保存到cookie中的账户信息不符，重新设置cookie保存新的账户信息
                            let base64_cname = window.btoa(cname);
                            let base64_cvalue = base64_userName + "_" + base64_password;
                            setCookie(base64_cname, base64_cvalue, -1);
                        } else {
                            //当前输入的账户信息与已保存到cookie中的账户信息相同，不必进行保存
                        }
                    }
                    //登陆成功，跳转到主页
                    $(window).attr("location", "index");
                }
            },
            error: function (error) {
                alert("----ajax请求校验账号密码执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    });
});