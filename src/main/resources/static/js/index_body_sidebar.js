/**
 * 作者: lwh
 * 时间: 2020.5.20
 * 描述: 侧边导航栏初始化
 */
function initSidebar() {
    $(".sidebar .btn-sidebar").click(sidebar);
    $(".sidebar .sidebar-prompt").click(sidebar);

    $(".sidebar ul li ul li").click(function () {
        $(".sidebar ul li ul li.active").removeClass("active");
        $(this).addClass("active");
    });
}

/**
 * 作者: lwh
 * 时间: 2020.5.20
 * 描述: 侧边导航栏隐藏、展示动画所需的点击事件处理
 */
function sidebar() {
    $(".sidebar .btn-sidebar").toggleClass("btn-side");
    $(".sidebar").toggleClass("side");
    $(".sidebar > div").toggleClass("sidebar-prompt");
    /*更改sidebar的兄弟节点在侧边栏展开和收起时的宽度*/
    if ($(".sidebar").hasClass("side")) {
        $(".sidebar").prev().css("width", "calc( 100% - 225px)");
    } else {
        $(".sidebar").prev().css("width", "calc( 100% - 25px)");
    }
}

/**
 * 作者: lwh
 * 时间: 2020.5.31
 * 描述: 为导航栏的显示和隐藏添加监听函数
 */
function addSidebarClickEventHandlerFunction(resize) {
    $(".sidebar:eq(0)").click(function () {
        setTimeout(resize, 500);
    });
    $(window).resize(resize);
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 侧边导航栏点击监听处理函数
 */
function registListenerForSidebar() {
    $("#index-body-chart #index-body-sidebar ul li ul li").click(function () {
        loadSingleChart($(this).children().text());
    });
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 侧边导航栏点击监听处理函数
 */
function loadSingleChart(cname) {
    //根据名称查找图表并加载
    $.each(chart_components, function (key, value) {
        if (value.cname === cname) {
            //清空图表显示区域
            $("#index-body-chart #index-body-chart-container").empty();
            loadSingleComponent(value, "#index-body-chart #index-body-chart-container");
            //return false;——跳出所有循环；相当于 javascript 中的 break 效果。
            //return true;——跳出当前循环，进入下一个循环；相当于 javascript 中的 continue 效果
            return false;
        }
    });
}