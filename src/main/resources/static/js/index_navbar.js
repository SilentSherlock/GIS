/**
 * 作者: lwh
 * 时间: 2020.4.13
 * 描述: 导航栏初始化----------
 */
function initIndexNavbar() {
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
        console.log("搜索处理");
    });
}

/**
 * 作者: lwh
 * 时间: 2020.4.13
 * 描述: 加载网页主体首页
 */
function showBodyIndex() {
    if (!isLoaded(systemComponents.c2.cid)) {
        //获取
        $.ajax({
            url: systemComponents.c2.curl,
            type: "get",
            async: true,
            dataType: "html",
            success: function (data) {
                //移除之前的组件
                $("#" + currentComponent.cid).remove();
                //加载
                $("#index-body-container").append(data);
                //更新当前操作和导航栏
                saveCurrentOpt2SesAndUpdateNavbar(systemComponents.c2);
            },
            error: function (error) {
                alert("----ajax请求加载首页执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    }
}

/**
 * 作者: lwh
 * 时间: 2020.4.24
 * 描述: 显示地图信息-------
 */
function showMap() {
    console.log("showMap");
}

/**
 * 作者: lwh
 * 时间: 2020.4.24
 * 描述: 显示图表信息------
 */
function showChart() {
    if (!isLoaded(systemComponents.c4.cid)) {
        //获取
        $.ajax({
            url: systemComponents.c4.curl,
            type: "get",
            async: true,
            dataType: "html",
            success: function (data) {
                //移除之前的组件
                $("#" + currentComponent.cid).remove();
                //加载
                $("#index-body-container").append(data);
                //更新当前操作并激活对应导航栏菜单项
                saveCurrentOpt2SesAndUpdateNavbar(systemComponents.c4);
                //初始化图表界面
                indexBodyChartInit();
            },
            error: function (error) {
                alert("----ajax请求加载图表展示网页执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    }
}

/**
 * 作者: lwh
 * 时间: 2020.4.12
 * 描述：显示登录管理员的信息
 */
function showAdminInfo() {
    if (!isLoaded(systemComponents.c5.cid)) {
        //获取
        $.ajax({
            url: systemComponents.c5.curl,
            type: "get",
            async: true,
            dataType: "html",
            success: function (data) {
                //移除之前的组件
                $("#" + currentComponent.cid).remove();
                //加载
                $("#index-body-container").append(data);
                //更新当前操作并激活对应导航栏菜单项
                saveCurrentOpt2SesAndUpdateNavbar(systemComponents.c5);
                //初始化管理员信息页面
                initAdminInfo();
            },
            error: function (error) {
                alert("----ajax请求加载显示管理员信息执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    }
}

/**
 * 作者: lwh
 * 时间: 2020.4.12
 * 描述: 添加管理员---------
 */
function showAddAdmin() {
    if (!isLoaded(systemComponents.c6.cid)) {
        //获取
        $.ajax({
            url: systemComponents.c6.curl,
            type: "get",
            async: true,
            dataType: "html",
            success: function (data) {
                //移除之前的组件
                $("#" + currentComponent.cid).remove();
                //加载
                $("#index-body-container").append(data);
                //更新当前操作并激活对应导航栏菜单项
                saveCurrentOpt2SesAndUpdateNavbar(systemComponents.c6);
                //初始化管理员信息页面
                initAddAdmin();
            },
            error: function (error) {
                alert("----ajax请求加载显示管理员信息执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    }
}

/**
 * 作者: lwh
 * 时间: 2020.6.2
 * 描述: 管理员列表---------
 */
function showAllAdmin() {
    if (!isLoaded(systemComponents.c8.cid)) {
        //获取
        $.ajax({
            url: systemComponents.c8.curl,
            type: "get",
            async: true,
            dataType: "html",
            success: function (data) {
                //移除之前的组件
                $("#" + currentComponent.cid).remove();
                //加载
                $("#index-body-container").append(data);
                //更新当前操作并激活对应导航栏菜单项
                saveCurrentOpt2SesAndUpdateNavbar(systemComponents.c8);
                //初始化管理员信息页面
                initAllAdmin();
            },
            error: function (error) {
                alert("----ajax请求加载显示管理员信息执行出错！错误信息如下：----\n" + error.responseText);
            }
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

/**
 * 作者: lwh
 * 时间: 2020.4.24
 * 描述: 更新sessionstorage中存储的当前操作并更新导航栏
 */
function saveCurrentOpt2SesAndUpdateNavbar(cid) {
    //更新当前操作
    currentComponent = cid;
    //保存当前操作
    saveData2Ses(currentComponent);
    //更新导航栏
    updateCurrentNavMenu(cid);
}

/**
 * 作者: lwh
 * 时间: 2020.4.24
 * 描述: 根据用户当前的操作更新导航栏当前激活的菜单栏
 */
function updateCurrentNavMenu(cid) {
    //更改网页标题
    $("title").text(cid.cn + "—玉米的生长及环境数据管理系统");
    //获取导航栏菜单ul
    //注意eq选择器的使用
    let navMenu = $("#navbar-collapse-1 ul:eq(0)");
    //取消当前激活菜单项
    navMenu.children(".active").removeClass("active");
    //如果菜单项数等于4说明进入了非主要的3个菜单项之外的其他菜单项
    if (navMenu.children().length === 4)
        //移除正在浏览的其他菜单项
        navMenu.children("li:eq(3)").remove();
    switch (cid.clfn) {
        case systemComponents.c2.clfn:
            navMenu.children("li:eq(0)").addClass("active");
            break;
        case systemComponents.c3.clfn:
            navMenu.children("li:eq(1)").addClass("active");
            break;
        case systemComponents.c4.clfn:
            navMenu.children("li:eq(2)").addClass("active");
            break;
        default:
            let html = "<li class='active'><a href='javascript:void(0)' onclick='" + cid.clfn + "'>" + cid.cn + "</a></li>";
            navMenu.append(html);
            break;
    }
}