//模态框的加载以系统组件为单位，同一组件的模态框同时加载和删除
//该系统组件用到的所有模态框请求url
let adminInfoModalsUrl = "index_body_adminInfo_modal";
//存储用户信息管理页面所有模态框的加载函数和名称、模态框id、初始化函数
let adminInfoModals = {
    "m0": {
        "minit": "adminInfoModal0init()",          //初始化函数
        "mn": "修改密码模态框",                     //模态框名称
        "mid": "adminInfo-resetPassword-modal"     //模态框id
    },
    "m1": {
        "minit": "adminInfoModal1init()",
        "mn": "修改邮箱模态框",
        "mid": "adminInfo-resetEmail-modal"
    }
};

/**
 * 作者: lwh
 * 时间: 2020.4.12
 * 描述：显示登录管理员信息页面的初始化
 */
function initAdminInfo() {
    //管理员信息显示页面的初始化相关工作
    $("#adminInfo-hello-adminName").text("您好，" + loggedAdminInfo.adminName + "!");
    $("#adminInfo-hello-email").text(loggedAdminInfo.email);
}

/**
 * 作者: lwh
 * 时间: 2020.4.25
 * 描述：修改用户信息
 */
function resetAdminInfo(type) {
    //加载该组件用到的所有模态框
    loadAdminInfoModals();
    //根据要更改的信息类别调用对应的模态框
    if (type === 0)
        //激活模态框
        $("#" + adminInfoModals.m0.mid).modal();
    else if (type === 1)
        $("#" + adminInfoModals.m1.mid).modal();
    else
        alert("更改的用户信息类别出错!");
}

/**
 * 作者: lwh
 * 时间: 2020.4.26
 * 描述：修改用户信息模态框加载
 */
function loadAdminInfoModals() {
    //判断是否已经加载模态框
    if (!isLoaded(adminInfoModals.m0.mid)) {
        //请求
        $.ajax({
            url: adminInfoModalsUrl,
            type: "get",
            async: false,
            dataType: "html",
            success: function (data) {
                //加载
                $("body").append(data);
                //初始化各个模态框
                $.each(adminInfoModals, function (key, value) {
                    eval(value.minit);
                    //更改验证状态，对模态框的生命周期时间进行监听，当模态框消失时触发操作
                    $("#" + value.mid).on("hide.bs.modal", function (e) {
                        $("#" + value.mid + "-form").data("bootstrapValidator").resetForm(true);
                    });
                });
            },
            error: function (error) {
                alert("----ajax请求管理员信息组件下的模态框执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    }
}

/**
 * 作者: lwh
 * 时间: 2020.4.26
 * 描述：初始化修改密码的模态框------------
 */
function adminInfoModal0init() {
    //位更改新密码的表单添加验证条件
    $("#adminInfo-resetPassword-modal-form").bootstrapValidator({
        message: "*输入不合法",
        feedbackIcons: {
            valid: "glyphicon glyphicon-ok",
            invalid: "glyphicon glyphicon-remove",
            validating: "glyphicon glyphicon-refresh"
        },
        fields: {
            "adminInfo-newPasswordInput": {
                message: "*密码不合法",
                validators: {
                    notEmpty: {
                        message: "*请输入新密码"
                    },
                    stringLength: {
                        min: 6,
                        max: 6,
                        message: "*密码必须为6位"
                    },
                    //自定义校验器(新密码不能和旧密码相同且不能和用户名相同)
                    callback: {
                        callback: function (value, validator, $field) {
                            //保存验证结果的json字符串
                            let checkResult = {
                                valid: true,
                                message: "*校验成功"
                            };
                            if (value === loggedAdminInfo.password) {
                                checkResult.valid = false;
                                checkResult.message = "*新密码不能和旧密码相同";
                            }
                            if (value === loggedAdminInfo.adminName) {
                                checkResult.valid = false;
                                checkResult.message = "*密码不能和用户名相同";
                            }
                            return checkResult;
                        }
                    }
                }
            },
            "adminInfo-newPasswordConfirm": {
                message: "*密码确认失败",
                validators: {
                    notEmpty: {
                        message: "*请再次输入新密码"
                    },
                    //必须与指定输入域的值相同
                    identical: {
                        field: "adminInfo-newPasswordInput",
                        message: "*两次输入的密码不相同"
                    }
                }
            }
        }
    }).on("success.form.bv", function (e) {
        //注册表单被提交后且验证成功的事件的监听函数以使用ajax提交表单数据
        //阻止正常提交表单
        e.preventDefault();
        //获取新密码
        let newPassword = $("#adminInfo-newPasswordInput").val();
        //更改修改按钮的修改状态
        $("#adminInfo-resetPassword-modal-form :submit").button("loading");
        //发送请求更改新密码---------------------
        $.ajax({
            url: "",
            type: "post",
            data: {
                newPassword: newPassword
            },
            contentType: "application/json;charset=utf-8",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data === "1") {
                    myAlert("alert", "alert", "密码修改成功，请重新登陆(∩_∩)!");
                    logout();
                } else {
                    myAlert("alert", "alert", "啊欧，密码修改失败(x_x)！");
                    //更改修改按钮的修改状态
                    $("#adminInfo-resetPassword-modal-form :submit").button("reset");
                }
            },
            error: function (error) {
                alert("----ajax请求更改密码执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    });
}

/**
 * 作者: lwh
 * 时间: 2020.4.26
 * 描述：初始化修改邮箱的模态框--------------
 */
function adminInfoModal1init() {
    //位更改新密码的表单添加验证条件
    $("#adminInfo-resetEmail-modal-form").bootstrapValidator({
        message: "*输入不合法",
        feedbackIcons: {
            valid: "glyphicon glyphicon-ok",
            invalid: "glyphicon glyphicon-remove",
            validating: "glyphicon glyphicon-refresh"
        },
        fields: {
            "adminInfo-newEmailInput": {
                message: "*邮箱不合法",
                validators: {
                    notEmpty: {
                        message: "*请输入新邮箱地址"
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
        //获取新邮箱
        let newEmail = $("#adminInfo-newEmailInput").val();
        //更改修改按钮的修改状态
        $("#adminInfo-resetEmail-modal-form :submit").button("loading");
        //发送请求更改新邮箱--------------------
        $.ajax({
            url: "",
            type: "post",
            data: {
                newEmail: newEmail
            },
            contentType: "application/json;charset=utf-8",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data === "1") {
                    myAlert("alert", "alert", "邮箱修改成功(∩_∩)!");
                    //重新加载管理员信息
                    eval(systemComponents.c5.clfn);
                } else {
                    myAlert("alert", "alert", "啊欧，邮箱修改失败(x_x)！");
                    //更改修改按钮的修改状态
                    $("#adminInfo-resetEmail-modal-form :submit").button("reset");
                }
            },
            error: function (error) {
                alert("----ajax请求更改邮箱执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    });
}