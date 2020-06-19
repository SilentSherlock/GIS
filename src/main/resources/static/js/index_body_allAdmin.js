/**
 * 作者: lwh
 * 时间: 2020.6.2
 * 描述: 管理员列表页面初始化----------
 */
function initAllAdmin() {
    $.ajax({
        url: "getAllAdmins",
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            if (data === "0")
                alert("获取管理员列表失败!");
            else {
                let html = "";
                $.each(data, function (key, value) {
                    html += "<tr>" +
                        "<th scope='row'>" + value.adminId + "</th>" +
                        "<td>" + value.adminName + "</td>" +
                        "<td>" + value.password + "</td>" +
                        "<td>" + value.email + "</td>" +
                        "<td><a href='javascript:void(0)' onclick='deleteAdmin(this)'>删除</a></td>" +
                        "</tr>";
                });
                $("#allAdmin-table tbody tr").remove();
                $("#allAdmin-table tbody").append(html);
            }
        },
        error: function (error) {
            alert("----ajax请求加载显示管理员信息执行出错！错误信息如下：----\n" + error.responseText);
        }
    });
}

/**
 * 作者: lwh
 * 时间: 2020.6.2
 * 描述: 删除管理员
 */
function deleteAdmin(p) {
    let tds = $(p).parent().parent().children();
    let adminName = $(tds[1]).text();
    let password = $(tds[2]).text();
    let jsonStr = JSON.stringify({
        adminName: adminName,
        password: password
    });
    $.ajax({
        url: "deleteAdmin",
        type: "post",
        data: jsonStr,
        contentType: "application/json;charset=utf-8",
        async: true,
        dataType: "json",
        success: function (data) {
            if (data === "0")
                alert("删除失败!");
            else {
                alert("删除成功!");
                initAllAdmin();
            }
        },
        error: function (error) {
            alert("----ajax请求加载显示管理员信息执行出错！错误信息如下：----\n" + error.responseText);
        }
    });
}