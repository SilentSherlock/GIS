//全局变量
//存储当前已登录的用户信息，方便函数调用
let loggedAdminInfo;
//存储系统各组件的加载函数和组件名称、组件id和组件请求url
let systemComponents = {
    "c0": {
        "clfn": "loadNavbar()",      //加载组件函数名
        "cn": "导航栏",              //组件名称
        "cid": "index-navbar",       //组件id
        "curl": "index_navbar"       //组件请求url
    },
    "c1": {
        "clfn": "loadFoot()",
        "cn": "页脚",
        "cid": "index-foot",
        "curl": "index_foot"
    },
    "c2": {
        "clfn": "showBodyIndex()",
        "cn": "首页",
        "cid": "index-body-index",
        "curl": "index_body_index"
    },
    "c3": {
        "clfn": "showMap()",
        "cn": "地图",
        "cid": "index-body-map",
        "curl": "index_body_map"
    },
    "c4": {
        "clfn": "showChart()",
        "cn": "图表",
        "cid": "index-body-chart",
        "curl": "index_body_chart"
    },
    "c5": {
        "clfn": "showAdminInfo()",
        "cn": "我的账号",
        "cid": "index-body-adminInfo",
        "curl": "index_body_adminInfo"
    },
    "c6": {
        "clfn": "showAddAdmin()",
        "cn": "添加管理员",
        "cid": "index-body-addAdmin",
        "curl": "index_body_addAdmin"
    },
    "c7": {
        "clfn": "showSearchResult()",
        "cn": "搜索结果",
        "cid": "index-body-searchResult",
        "curl": "index_body_searchResult"
    }
    ,
    "c8": {
        "clfn": "showAllAdmin()",
        "cn": "管理员列表",
        "cid": "index-body-allAdmin",
        "curl": "index_body_allAdmin"
    }
};
//记录当前用户处于的界面便于刷新
let currentComponent = systemComponents.c2;

/**
 * 作者: lwh
 * 时间: 2020.4.10
 * 描述: 管理系统主页初始化js
 */
$(document).ready(function () {
    //判断是否存在登录用户-------------------
    //isLogged();

    //初始化全局变量
    getGlobalVars();

    //加载并初始化导航栏
    eval(systemComponents.c0.clfn);

    //根据保存的当前操作加载对应内容
    eval(currentComponent.clfn);

    //加载并初始化页脚
    eval(systemComponents.c1.clfn);
});

/**
 * 作者: lwh
 * 时间: 2020.4.14
 * 描述: 判断是否存在登录用户---------
 */
function isLogged() {
    $.ajax({
        url: "",
        type: "get",
        async: false,
        dataType: "json",
        sussess: function (data) {
            if (data === "0")
                logout();
        },
        error: function (error) {
            alert("----ajax请求验证是否存在登录用户执行出错！错误信息如下：----\n" + error.responseText);
        }
    });
}

/**
 * 作者: lwh
 * 时间: 2020.4.13
 * 描述: 获取管理员信息和刷新网页前的操作信息
 */
function getGlobalVars() {
    //获取管理员信息
    loggedAdminInfo = {
        adminId: window.sessionStorage.getItem("adminId"),
        adminName: window.atob(window.sessionStorage.getItem("adminName")),
        password: window.atob(window.sessionStorage.getItem("password")),
        email: window.atob(window.sessionStorage.getItem("email"))
    };
    //获取网页加载前进行的操作
    let cclfn = window.sessionStorage.getItem("clfn");
    //如果为空加载首页，如果不为空说明刷新网页前处于非首页状态
    if (cclfn !== null) {
        currentComponent = {
            "clfn": cclfn,
            "cn": window.sessionStorage.getItem("cn"),
            "cid": window.sessionStorage.getItem("cid"),
            "curl": window.sessionStorage.getItem("curl")
        }
    }
}

/**
 * 作者: lwh
 * 时间: 2020.4.10
 * 描述: 加载导航栏
 */
function loadNavbar() {
    //获取
    $.ajax({
        url: systemComponents.c0.curl,
        type: "get",
        async: false,
        dataType: "html",
        success: function (data) {
            //加载
            $("#index-navbar-container").append(data);
            //初始化导航栏
            initIndexNavbar();
        },
        error: function (error) {
            alert("----ajax请求加载导航栏执行出错！错误信息如下：----\n" + error.responseText);
        }
    });
}

/**
 * 作者: lwh
 * 时间: 2020.4.13
 * 描述: 加载页脚
 */
function loadFoot() {
    //获取
    $.ajax({
        url: systemComponents.c1.curl,
        type: "get",
        async: false,
        dataType: "html",
        success: function (data) {
            //加载
            $("#index-foot-container").append(data);
        },
        error: function (error) {
            alert("----ajax请求加载页脚执行出错！错误信息如下：----\n" + error.responseText);
        }
    });
}
