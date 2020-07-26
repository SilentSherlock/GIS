/**
 * 作者: lwh
 * 时间: 2020.4.13
 * 描述: 导航栏初始化----------
 */
function index_navbar_init() {
    //更新导航栏已登录管理员信息
    $("#index-navbar-adminName").text("您好，" + loggedAdminInfo.adminName);
    //搜索框输入验证
    $("#index-navbar-searchFrom").bootstrapValidator({
        //提示信息放到指定区域
        container: "#errors",
        feedbackIcons: {
            valid: "glyphicon glyphicon-ok",
            invalid: "glyphicon glyphicon-remove",
            validating: "glyphicon glyphicon-refresh"
        },
        fields: {
            searchContentInput: {
                validators: {
                    notEmpty: {
                        message: "请输入任何关键字以进行搜索！"
                    }
                }
            }
        }
    }).on("success.form.bv", function (e) {
        //注册表单被提交后且验证成功的事件的监听函数以使用ajax提交表单数据
        //阻止正常提交表单
        e.preventDefault();
        //更改搜索按钮的状态
        $("#index-navbar-searchFrom :submit").button("loading");
        //--------------------------------------
        myAlert("前方正在施工!");
    });

    //为导航标签添加监听函数
    navbarTabListenerRegist();
}

/**
 * 作者: lwh
 * 时间: 2020.7.8
 * 描述: 导航栏标签监听函数注册
 */
function navbarTabListenerRegist() {
    //导航栏主要标签
    let navbar_navul = $("#index-navbar ul:eq(0)");
    //设置监听器
    $(navbar_navul).children().click(function () {
        //是否点击已经激活的菜单项
        if ($(this).hasClass("active"))
            return;
        //更改网页标题
        let cname = $(this).children().text();
        $("title").text( cname+ "—玉米的生长及环境数据管理系统");
        //取消当前激活菜单项
        navbar_navul.children("li.active").removeClass("active");
        //如果菜单项数等于4则要进行删除
        if (navbar_navul.children().length === 4)
            //移除多余菜单项
            navbar_navul.children("li:eq(3)").remove();
        //激活点击项
        $(this).addClass("active");
        //根据名称查找组件并加载
        findComponentInfoAndLoad(cname);
    });

    //导航栏管理员信息操作标签
    $("#index-navbar ul.dropdown-menu:eq(0) li.needComponent").click(function () {
        //更改网页标题(去除空格)
        let cname = $(this).children().text().replace(/\s/g, "");
        $("title").text( cname+ "—玉米的生长及环境数据管理系统");
        //取消当前激活的菜单项
        navbar_navul.children("li.active").removeClass("active");
        //如果菜单项数等于4则要进行删除
        if (navbar_navul.children().length === 4)
            //移除多余菜单项
            navbar_navul.children("li:eq(3)").remove();
        //导航栏标签上添加当前正在访问的功能
        let html = "<li class='active'><a href='javascript:void(0)'>" + cname + "</a></li>";
        navbar_navul.append(html);
        //根据名称查找组件并加载
        findComponentInfoAndLoad(cname);
    });

    function findComponentInfoAndLoad(cname) {
        //根据名称查找组件并加载
        let i = 0;
        $.each(systemComponents, function (key, value) {
            if (value.cname === cname) {
                //清空主体区域
                $("#index-body-container").empty();
                //加载对应组件
                loadSingleComponent(value, "#index-body-container");
                //更新当前用户所处界面
                currentComponent = i;
                let jsonObj = {cno: currentComponent};
                saveData2Ses(jsonObj);
                //return false;——跳出所有循环；相当于 javascript 中的 break 效果。
                //return true;——跳出当前循环，进入下一个循环；相当于 javascript 中的 continue 效果
                return false;
            }
            i++;
        });
    }
}

/**
 * 作者: lwh
 * 时间: 2020.4.12
 * 描述: 退出登录
 */
function logout() {
    $(window).attr("location", "/gis");
}