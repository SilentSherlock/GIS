/**
 * 作者: lwh
 * 时间: 2020.2.26
 * 描述: 管理员登陆界面的相关初始化js
 */
$(document).ready(function () {
    //用来保存账号密码的cookie名称变量
    let cname = "savedAccInfo";

    //查询cookie是否存在记住的账号密码
    let base64_accInfo = getCookie(window.btoa(cname));
    if (base64_accInfo !== null){
        //存在记住的账号密码
        let arr = base64_accInfo.split("_");
        //解码获取账号密码
        let userName = window.atob(arr[0]);
        let password = window.atob(arr[1]);
        //自动填充
        $("input[name='userNameInput']").val(userName);
        $("input[name='passwordInput']").val(password);
    }

    //开启bootstrap工具提示插件
    $("[data-toggle='tooltip']").tooltip();
    //开启bootstrapValidator进行表单验证
    $("#loginForm").bootstrapValidator({
        message: "*输入不合法",
        feedbackIcons: {
            valid: "glyphicon glyphicon-ok",
            invalid: "glyphicon glyphicon-remove",
            validating: "glyphicon glyphicon-refresh"
        },
        fields:{
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
                message: "密码不合法",
                validators: {
                    notEmpty: {
                        message: "*密码不能为空"
                    },
                    stringLength: {
                        min: 6,
                        max: 6,
                        message: "*密码必须为6位"
                    },
                    different: {
                        field: "userNameInput",
                        message: "*密码不能和用户名相同"
                    }
                }
            }
        }
    }).on("success.form.bv", function(e) {
        //注册表单被提交后且验证成功的事件的监听函数以使用ajax提交表单数据
        //阻止正常提交表单
        e.preventDefault();
        //获取表单实例
        let $form = $(e.target);
        //获取校验器实例
        let bv = $form.data("bootstrapValidator");
        //用base64加密用户名和密码
        let base64_userName = window.btoa($form.userNameInput);
        let base64_password = window.btoa($form.passwordInput);
        alert($form.userNameInput);
        let model = {
                adminName:base64_userName,
                password: base64_password
            };
        let jsonObject = JSON.stringify(model);
        alert("fuck");
        //使用ajax提交表单验证用户名密码
        $.ajax({
            url: "adminValidate",
            type: "post",
            data: jsonObject,
            dataType: "text",
            contentType:"application/json;charset=UTF-8",
            async:false,
            success: function (data) {
                alert("----ajax请求执行成功！----");
                let checkResult = data.toString();
                if (checkResult === "1"){
                    //登陆成功
                    if($("input[name='rememberCheckbox']").prop("checked")) {
                        //需要记住密码,设置cookie
                        let base64_cname = window.btoa(cname);
                        let base64_cvalue = base64_userName + "_" + base64_password;
                        setCookie(base64_cname, base64_cvalue, -1);
                    }else{
                        let base64_cname = window.btoa(cname);
                        //删除cookie
                        setCookie(base64_cname, "", 0);
                    }
                    alert("登陆成功！");
                    window.location.href = "index.html";
                }else if(checkResult === "0"){
                    //验证失败，加载并显示提示用户验证失败的模态框
                    loadModals();
                    $("#loginErrorModal").modal();
                    //更改用户名和密码的验证状态
                    $("#loginErrorModal").on("hide.bs.modal", function ( e) {
                        bv.updateStatus("userNameInput", "INVALID");
                        bv.updateStatus("passwordInput", "INVALID");
                    });
                    alert("用户名或密码错误!");
                }else{
                    alert("登录校验结果出错！");
                }
            },
            error: function (error) {
                alert("----ajax请求校验账号密码执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    });

    //重置按钮的点击事件监听函数注册
    $("#resetButton").click(function () {
        $("#loginForm").data("bootstrapValidator").resetForm(true);
        //取消记住密码的选择，因为记住密码的checkbox未添加bootstrapValidator验证
        $("input[name='rememberCheckbox']").prop("checked", false);
    });
});
/**
 * 作者: lwh
 * 时间: 2020.2.26
 * 描述: 设置或者删除cookie
 */
function setCookie(cname, cvalue, exhours) {
    /* ----------Cookie属性项说明----------
     * NAME=VALUE	键值对，可以设置要保存的 Key/Value，注意这里的 NAME 不能和其他属性项的名字一样
     * Expires	    过期时间，在设置的某个时间点(ms)后该 Cookie 就会失效（不指定该属性值或者属性值
     *              小于0时，cookie生命周期为会话周期；指定为0时，cookie无效，代表立即删除该cookie）
     * Domain	    生成该 Cookie 的域名，如 domain="www.baidu.com"
     * Path	        该 Cookie 是在当前的哪个路径下生成的，如 path=/wp-admin/
     * Secure	    如果设置了这个属性，那么只会在 SSH 连接时才会回传该 Cookie
     *
     * 读取cookie时cookie的字符串结构为“name1=value1; name2=value2”
     */
    let cookieStr = cname + "=" + cvalue;
    //当hours>0时，该cookie存在指定时间；等于0时代表立即删除该cookie；小于0时该cookie会存在至会话结束
    if (exhours === 0){
        cookieStr += "; expires=0";
    }else if (exhours < 0){
        cookieStr += "; expires=-1";
    }else {
        //设置到期时间
        let expires = new Date();
        expires.setTime(expires.getTime() + exhours * 60 * 60 * 1000);
        cookieStr += "; expires=" + expires.toUTCString();
    }
    //设置cookie
    document.cookie = cookieStr;
}
/**
 * 作者: lwh
 * 时间: 2020.2.26
 * 描述: 获取指定名称的cookie的value值,失败返回null
 */
function getCookie(cname) {
    let reg = new RegExp("^| " + cname + "=(.*);|$");
    let cookieStr = document.cookie;
    if (cookieStr !== ""){
        let arr = cookieStr.match(reg);
        if (arr != null){
            return unescape(arr[1]);
        }
    }
    return null;
}
/**
 * 作者: lwh
 * 时间: 2020.2.27
 * 描述: 加载模态框
 */
function loadModals() {
    //加载模态框
    $.ajax({
        url: "adminLogin_modals.html",
        type: "get",
        dataType: "html",
        success: function (data) {
            $(".container-fluid").append(data);
        },
        error: function (error) {
            alert("----ajax请求加载模态框执行出错！错误信息如下：----\n" + error.responseText);
        }
    });
}
