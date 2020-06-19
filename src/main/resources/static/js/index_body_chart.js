//显示图标页面需要用到的组件
let chart_components = {
    c0: {
        clfn: "loadSidebar()",
        cname: "侧边导航栏",
        cid: "index-body-sidebar",
        curl: "index_body_sidebar"
    },
    c1: {
        clfn: "loadChAndChlChart()",
        cname: "株高和叶绿素",
        cid: "chart-chAndChl",
        curl: "chart_chAndChl"
    }
};

/*
let chart_components = {
    c0: {
        clfn: "loadSidebar()",
        cname: "侧边导航栏",
        cid: "index-body-sidebar",
        curl: "index_body_sidebar"
    },
    c1: {
        clfn: "loadChAndChlChart()",
        cname: "株高和叶绿素",
        cid: "chart-chAndChl",
        curl: "chart_chAndChl"
    },
    c2: {
        clfn: "loadVWCChart()",
        cname: "体积含水量",
        cid: "chart-vwc",
        curl: "chart_vwc"
    },
    c3: {
        clfn: "loadLAIChart()",
        cname: "叶面积指数",
        cid: "chart-lai",
        curl: "chart_lai"
    },
    c4: {
        clfn: "loadLAMChart()",
        cname: "叶面积仪数据",
        cid: "chart-lam",
        curl: "chart_lam"
    },
    c5: {
        clfn: "loadPreAndIrrChart()",
        cname: "降雨量和灌溉量",
        cid: "chart-preAndIrr",
        curl: "chart_preAndIrr"
    },
    c6: {
        clfn: "loadSRChart()",
        cname: "气孔阻力",
        cid: "chart-sr",
        curl: "chart_sr"
    },
    c7: {
        clfn: "loadSWCChart()",
        cname: "土壤含水量",
        cid: "chart-swc",
        curl: "chart_swc"
    },
    c8: {
        clfn: "loadSWSChart()",
        cname: "标准气象站",
        cid: "chart-sws",
        curl: "chart_sws"
    },
    c9: {
        clfn: "loadYieldChart()",
        cname: "产量",
        cid: "chart-yield",
        curl: "chart_yield"
    }
};
*/
/**
 * 作者: lwh
 * 时间: 2020.5.20
 * 描述: 图表展示主页初始化
 */
function indexBodyChartInit() {
    //加载图表
    $.each(chart_components, function (key, value) {
        eval(value.clfn);
    });

}

/**
 * 作者: lwh
 * 时间: 2020.5.20
 * 描述: 加载侧边导航栏
 */
function loadSidebar() {
    if (!isLoaded(chart_components.c0.cid)) {
        $.ajax({
            url: chart_components.c0.curl,
            type: "get",
            async: false,
            dataType: "html",
            success: function (data) {
                $("#index-body-chart").append(data);
                //初始化侧边栏
                initSidebar();
            },
            error: function (error) {
                alert("----ajax请求加载侧边导航栏执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    }
}

/**
 * 作者: lwh
 * 时间: 2020.5.28
 * 描述: 加载株高和叶绿素的图表
 */
function loadChAndChlChart() {
    if (!isLoaded(chart_components.c1.cid)) {
        $.ajax({
            url: chart_components.c1.curl,
            type: "get",
            async: true,
            dataType: "html",
            success: function (data) {
                $("#index-body-chart #index-body-chart-container").append(data);
                //初始化株高和叶绿素含量图表
                chart_chAndChl_init();
                //初始化叶面积仪数据表
                chart_lam_init();
                //初始化叶面积仪数据表
                chart_yield_init();
            },
            error: function (error) {
                alert("----ajax请求加载株高和叶绿素图表执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    }
}

/**
 * 作者: SilentSherlock
 * 描述：玉米株高和叶绿素图表初始化
 */
function chart_chAndChl_init() {
    //加载默认样区的数据
    chart_chAndChl_generate();
    //样区选择按钮监听事件注册
    $("#chart-chAndChl .nav-pills .dropdown-menu li a").click(function () {
        //直接使用this并不能获得触发事件的对象，需要使用$(this)
        let clickedItemValue = $(this).text();
        //获得属性选择中第二个span
        let clickedBtn = $(this).parent().parent().prev().children();
        let preAttrValue = $(clickedBtn[1]).text();
        //是否改变了样区
        if (preAttrValue !== clickedItemValue) {
            //使用$表明这是一个jquery对象,防止使用方法时冲突
            $(clickedBtn[1]).text(clickedItemValue);
            let newDOY = $("#chart-chAndChl .nav-pills .dropdown button span:eq(1)").text();
            let newTRT = $("#chart-chAndChl .nav-pills .dropdown button span:eq(4)").text();
            console.log("DOY: " + newDOY + "\nTRT: " + newTRT);
            //更新图表
            chart_chAndChl_generate(newDOY, newTRT);
        }
    });
}

/**
 * 作者: SilentSherlock
 * 描述：玉米叶面积仪数据图表初始化
 */
function chart_lam_init() {
    //加载默认样区的数据
    chart_lam_generate();
    //样区选择按钮监听事件注册
    $("#chart-lam .nav-pills .dropdown-menu li a").click(function () {
        //直接使用this并不能获得触发事件的对象，需要使用$(this)
        let clickedItemValue = $(this).text();
        //获得属性选择中第二个span
        let clickedBtn = $(this).parent().parent().prev().children();
        let preAttrValue = $(clickedBtn[1]).text();
        //是否改变了样区
        if (preAttrValue !== clickedItemValue) {
            //使用$表明这是一个jquery对象,防止使用方法时冲突
            $(clickedBtn[1]).text(clickedItemValue);
            console.log("DOY: " + clickedItemValue);
            //更新图表
            chart_lam_generate(clickedItemValue);
        }
    });
}

/**
 * 作者: SilentSherlock
 * 描述：玉米产量图表初始化
 */
function chart_yield_init() {
    chart_yield_generate();
}

/**
 * 作者: SilentSherlock
 * 描述：画玉米株高和叶绿素图
 * 函数根据DOY属性一次获得多条数据
 * 画图时则根据TRT属性,确定一个扇形地块,分为三部分,画三个图
 */
function chart_chAndChl_generate(DOY = "177", TRT = "1") {
//从后端取数据
    let chartData = getChartData("corn/cornHeightAndChloDOY", JSON.stringify({DOY: DOY}), 1);
    //提取出所需地块
    let screenChartData = screenDataByAttr(chartData, "tRT", TRT);
    //提取数据NUM_3列数据作为x轴
    let xAxisData = extractCol(screenDataByAttr(screenChartData, "nUM_1", TRT + ".1"), "nUM_3");
    //提取第一部分
    let partData1 = screenDataByAttr(screenChartData, "nUM_1", TRT + ".1");
    //提取第二部分
    let partData2 = screenDataByAttr(screenChartData, "nUM_1", TRT + ".2");
    //提取第三部分
    let partData3 = screenDataByAttr(screenChartData, "nUM_1", TRT + ".3");

    let partOption1 = {
        legend: {},
        tooltip: {},
        xAxis: {
            type: "category",
            data: xAxisData,
            axisLabel: {
                formatter: "第{value}次"
            }
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "Height",
                data: extractCol(partData1, "height")
            },
            {
                type: "line",
                name: "Chlorophyll",
                data: extractCol(partData1, "chlorophyll")
            }
        ]
    };
    let partOption2 = {
        legend: {},
        tooltip: {},
        xAxis: {
            type: "category",
            data: xAxisData,
            axisLabel: {
                formatter: "第{value}次"
            }
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "Height",
                data: extractCol(partData2, "height")
            },
            {
                type: "line",
                name: "Chlorophyll",
                data: extractCol(partData2, "chlorophyll")
            }
        ]
    };
    let partOption3 = {
        legend: {},
        tooltip: {},
        xAxis: {
            type: "category",
            data: xAxisData,
            axisLabel: {
                formatter: "第{value}次"
            }
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "Height",
                data: extractCol(partData3, "height")
            },
            {
                type: "line",
                name: "Chlorophyll",
                data: extractCol(partData3, "chlorophyll")
            }
        ]
    };

    let cornHandChChart1 = getAndInitChart("chart-chAndChl-1");
    let cornHandChChart2 = getAndInitChart("chart-chAndChl-2");
    let cornHandChChart3 = getAndInitChart("chart-chAndChl-3");

    cornHandChChart1.setOption(partOption1);
    cornHandChChart2.setOption(partOption2);
    cornHandChChart3.setOption(partOption3);

    addSidebarClickEventHandlerFunction(function () {
        setTimeout(resize, 500);
    });
    $(window).resize(resize);

    function resize() {
        cornHandChChart1.resize();
        cornHandChChart2.resize();
        cornHandChChart3.resize();
    }
}

/**
 * 作者: SilentSherlock
 * 描述：画玉米叶面积仪相关数据
 */
function chart_lam_generate(DOY = "177") {
    //从后端取数据
    let chartData = getChartData("corn/cornLeafDOY", JSON.stringify({DOY: DOY}), 1);

    let chartOption = {
        legend: {},
        tooltip: {},
        dataset: {
            source: chartData
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "LeafPerimeter",
                encode: {
                    x: 5,
                    y: 3
                }
            },
            {
                type: "line",
                name: "LeafArea",
                encode: {
                    x: 5,
                    y: 1
                }
            },
            {
                type: "line",
                name: "LeafNumber",
                encode: {
                    x: 5,
                    y: 2
                }
            }
        ]
    };

    let chart = getAndInitChart("chart-lam-1");

    chart.setOption(chartOption);

    addSidebarClickEventHandlerFunction(function () {
        setTimeout(resize, 500);
    });
    $(window).resize(resize);

    function resize() {
        chart.resize();
    }
}

/**
 * 作者: SilentSherlock
 * 描述：画玉米产量相关数据
 */
function chart_yield_generate(DOY = "177") {
    //从后端取数据
    let chartData = getChartData("corn/cornYield", null, 0);

    let chartOption = {
        legend: {},
        tooltip: {},
        dataset: {
            source: chartData
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "产量（湿重）kg/hm2",
                encode: {
                    x: 3,
                    y: 6
                }
            },
            {
                type: "line",
                name: "产量（干重）kg/hm2",
                encode: {
                    x: 3,
                    y: 4
                }
            }
        ]
    };

    let chart = getAndInitChart("chart-yield-1");

    chart.setOption(chartOption);

    addSidebarClickEventHandlerFunction(function () {
        setTimeout(resize, 500);
    });
    $(window).resize(resize);

    function resize() {
        chart.resize();
    }

    let html =
        "<table class='table table-hover'>" +
        "<thead>" +
        "<tr>" +
        "<th>编号</th>" +
        "<th>产量（湿重）kg/hm2</th>" +
        "<th>盒重g</th>" +
        "<th>湿重g</th>" +
        "<th>干重g</th>" +
        "<th>含水率</th>" +
        "<th>产量（干重）kg/hm2</th>" +
        "</tr>" +
        "</thead>" +
        "<tbody>";

    $.each(chartData, function (key, value) {
        html += "<tr>" +
            "<th scope='row'>" + value.cornFieldId + "</th>" +
            "<td>" + value.moistureContent + "</td>" +
            "<td>" + value.boxWeight + "</td>" +
            "<td>" + value.beforeDehydration + "</td>" +
            "<td>" + value.afterDehydration + "</td>" +
            "<td>" + value.moistureContent + "</td>" +
            "<td>" + value.dryYield + "</td>" +
            "</tr>";
    });

    html += "</tbody></table>";

    $("#chart-yield-2").append(html);
}

/**
 * 作者: SilentSherlock
 * 描述：向后台发送请求获取数据,默认带请求数据
 */
function getChartData(requestUrl, jsonStr, flag = 1) {
    let chartData = null;
    if (requestUrl != null) {
        //图表数据获取成功回调函数
        function callback(data) {
            if (data === "0")
                alert("获取图表数据失败");
            else
                chartData = data;
        }

        //根据请求类别发送请求
        if (flag === 1) {
            $.ajax({
                url: requestUrl,
                type: "post",
                data: jsonStr,
                async: false,
                contentType: "application/json;charset=UTF-8",
                dataType: "json",   //dataType为json时得到的数据格式为json对象，但是后端发送数据时需发送json字符串
                success: callback,
                error: function (error) {
                    alert("----ajax请求获取图表数据失败！错误信息如下：----\n" + error.responseText);
                }
            });
        } else if (flag === 0) {
            $.ajax({
                url: requestUrl,
                type: "get",
                async: false,
                dataType: "json",
                success: callback,
                error: function (error) {
                    alert("----ajax请求获取图表数据失败！错误信息如下：----\n" + error.responseText);
                }
            });
        } else
            alert("图表数据请求类型出错");
    } else
        alert("图表数据请求url不能为空");

    return chartData;
}

/**
 * 作者: SilentSherlock
 * 描述：提取JSON对象数组中的某一列数据
 */
function extractCol(chartData, colName) {
    let colData = [];
    $.each(chartData, function (key, value) {
        if (value.hasOwnProperty(colName))
            colData.push(value[colName]);
    });
    return colData;
}

/**
 * 作者: SilentSherlock
 * 描述：提取JSON对象数组中所有数据
 */
function extractAll(chartData) {
    let allData = [];
    $.each(chartData, function (key, value) {

    });
    for (let row = 0; row < chartData.length; row++) {
        let rowData = [];
        for (let x in chartData[row]) {
            rowData.push(chartData[row][x]);
        }
        allData.push(rowData);
    }
    return allData;
}

/**
 * 作者: SilentSherlock
 * 描述：从JSON对象数组中根据某个属性值筛选部分对象
 */
function screenDataByAttr(chartData, attrName, attrValue) {
    //attrValue传递进来的是String要转换为Number
    let result = [];
    let i = 0;
    $.each(chartData, function (key, value) {
        if (value[attrName] === Number(attrValue)) {
            result.push(value);
            i++
        }
    });
    return result;
}

/**
 * 作者: SilentSherlock
 * 描述：初始化图表节点
 */
function getAndInitChart(chartDomId, theme = "purple") {
    let chartDom = document.getElementById(chartDomId);
    return echarts.init(chartDom, theme)
}