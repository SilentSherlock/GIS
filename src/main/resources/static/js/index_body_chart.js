//所有图表组件
let chart_components = {
    c1: {
        cname: "株高和叶绿素",
        cid: "chart-chAndChl",
        curl: "chart_chAndChl",
        cinit: "chart_chAndChl_init()"
    },
    c2: {
        cname: "体积含水量",
        cid: "chart-vwc",
        curl: "chart_vwc",
        cinit: "chart_vwc_init()"
    },
    c3: {
        cname: "叶面积指数",
        cid: "chart-lai",
        curl: "chart_lai",
        cinit: "chart_lai_init()"
    },
    c4: {
        cname: "叶面积仪数据",
        cid: "chart-lam",
        curl: "chart_lam",
        cinit: "chart_lam_init()"
    },
    c5: {
        cname: "降雨量和灌溉量",
        cid: "chart-preAndIrr",
        curl: "chart_preAndIrr",
        cinit: "chart_preAndIrr_init()"
    },
    c6: {
        cname: "气孔阻力",
        cid: "chart-sr",
        curl: "chart_sr",
        cinit: "chart_sr_init()"
    },
    c7: {
        cname: "土壤含水量",
        cid: "chart-swc",
        curl: "chart_swc",
        cinit: "chart_swc_init()"
    },
    c8: {
        cname: "标准气象站",
        cid: "chart-sws",
        curl: "chart_sws",
        cinit: "chart_sws_init()"
    },
    c9: {
        cname: "产量",
        cid: "chart-yield",
        curl: "chart_yield",
        cinit: "chart_yield_init()"
    }
};

/**
 * 作者: lwh
 * 时间: 2020.5.20
 * 描述: 图表展示主页初始化
 */
function index_body_chart_init() {
    //加载侧边导航栏
    loadSidebar();
    //注册侧边栏监听函数
    registListenerForSidebar();
    //展示图标首页
    loadChartIndex();
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 加载侧边导航栏
 */
function loadSidebar() {
    //是否已经加载
    if (!isLoaded("index-body-sidebar")) {
        $.ajax({
            url: "index_body_sidebar",
            type: "get",
            async: false,
            dataType: "html",
            success: function (data) {
                $("#index-body-chart").append(data);
                //初始化侧边导航栏
                initSidebar();
            },
            error: function (error) {
                alert("----ajax请求加载侧边栏执行出错！错误信息如下：----\n" + error.responseText);
            }
        });
    }
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 加载图表首页
 */
function loadChartIndex() {
    //遍历组件列表加载所有组件
    $.each(chart_components, function (key, value) {
        loadSingleComponent(value, "#index-body-chart #index-body-chart-container");
    });
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
            //更新图表
            chart_chAndChl_generate(newDOY, newTRT);
        }
    });
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

    //用于更改图标大小以适应屏幕的resize函数
    function resize() {
        cornHandChChart1.resize();
        cornHandChChart2.resize();
        cornHandChChart3.resize();
    }
    //隐藏/展开侧边栏的监听函数注册
    addSidebarClickEventHandlerFunction(resize);
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
            //更新图表
            chart_lam_generate(clickedItemValue);
        }
    });
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

    function resize() {
        chart.resize();
    }

    addSidebarClickEventHandlerFunction(resize);
}

/**
 * 作者: lwh
 * 时间: 2020.6.2
 * 描述: 产量图表初始化
 */
function chart_yield_init() {
    chart_yield_generate();
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

    function resize() {
        chart.resize();
    }

    addSidebarClickEventHandlerFunction(resize);

    //动态生成表格
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
 * 作者: lwh
 * 时间: 2020.7.6
 * 描述: 体积含水量图表初始化
 */
function chart_vwc_init() {
    chart_vwc_generate();
    //样区选择按钮监听事件注册
    $("#chart-vwc .nav-pills .dropdown-menu li a").click(function () {
        //直接使用this并不能获得触发事件的对象，需要使用$(this)
        let clickedItemValue = $(this).text();
        //获得属性选择中第二个span
        let clickedBtn = $(this).parent().parent().prev().children();
        let preAttrValue = $(clickedBtn[1]).text();
        //是否改变了样区
        if (preAttrValue !== clickedItemValue) {
            //使用$表明这是一个jquery对象,防止使用方法时冲突
            $(clickedBtn[1]).text(clickedItemValue);
            //更新图表
            chart_vwc_generate(clickedItemValue);
        }
    });
}

/**
 * 作者: lwh
 * 时间: 2020.7.6
 * 描述: 体积含水量图表绘制
 */
function chart_vwc_generate(TRT = "1") {
    //从后端取数据
    let chartData = getChartData("field/fwh", null, 0);

    //获取指定样区的数据
    let plotData = screenDataByAttr(chartData, "tRT", TRT);
    let plot1Data = screenDataByAttr(plotData, "nUM_1", TRT + "-1");
    let plot2Data = screenDataByAttr(plotData, "nUM_1", TRT + "-2");
    let plot3Data = screenDataByAttr(plotData, "nUM_1", TRT + "-3");

    let chartOption1 = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plot1Data
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "ContentWeight",
                encode: {
                    x: 3,
                    y: 0
                }
            },
            {
                type: "line",
                name: "massWaterContent",
                encode: {
                    x: 3,
                    y: 1
                }
            },
            {
                type: "line",
                name: "volumeWaterContent",
                encode: {
                    x: 3,
                    y: 5
                }
            }
        ]
    };

    let chartOption2 = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plot2Data
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "ContentWeight",
                encode: {
                    x: 3,
                    y: 0
                }
            },
            {
                type: "line",
                name: "massWaterContent",
                encode: {
                    x: 3,
                    y: 1
                }
            },
            {
                type: "line",
                name: "volumeWaterContent",
                encode: {
                    x: 3,
                    y: 5
                }
            }
        ]
    };

    let chartOption3 = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plot3Data
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "ContentWeight",
                encode: {
                    x: 3,
                    y: 0
                }
            },
            {
                type: "line",
                name: "massWaterContent",
                encode: {
                    x: 3,
                    y: 1
                }
            },
            {
                type: "line",
                name: "volumeWaterContent",
                encode: {
                    x: 3,
                    y: 5
                }
            }
        ]
    };

    let chart1 = getAndInitChart("chart-vwc-1");
    let chart2 = getAndInitChart("chart-vwc-2");
    let chart3 = getAndInitChart("chart-vwc-3");

    chart1.setOption(chartOption1);
    chart2.setOption(chartOption2);
    chart3.setOption(chartOption3);

    function resize() {
        chart1.resize();
        chart2.resize();
        chart3.resize();
    }

    addSidebarClickEventHandlerFunction(resize);
}

/**
 * 作者: lwh
 * 时间: 2020.7.6
 * 描述: 叶面积指数图表初始化
 */
function chart_lai_init() {
    chart_lai_generate();
    //样区选择按钮监听事件注册
    $("#chart-lai .nav-pills .dropdown-menu li a").click(function () {
        //直接使用this并不能获得触发事件的对象，需要使用$(this)
        let clickedItemValue = $(this).text();
        //获得属性选择中第二个span
        let clickedBtn = $(this).parent().parent().prev().children();
        let preAttrValue = $(clickedBtn[1]).text();
        //是否改变了样区
        if (preAttrValue !== clickedItemValue) {
            //使用$表明这是一个jquery对象,防止使用方法时冲突
            $(clickedBtn[1]).text(clickedItemValue);
            let newDOY = $("#chart-lai .nav-pills .dropdown button span:eq(1)").text();
            let newTRT = $("#chart-lai .nav-pills .dropdown button span:eq(4)").text();
            //更新图表
            chart_lai_generate(newDOY, newTRT);
        }
    });
}

/**
 * 作者: lwh
 * 时间: 2020.7.6
 * 描述: 体积含水量图表绘制
 */
function chart_lai_generate(DOY = "177", TRT = "1") {
    //从后端取数据
    let chartData = getChartData("corn/cornLAIDOY", JSON.stringify({DOY: DOY}), 1);

    //获取指定样区的数据
    let plotData = screenDataByAttr(chartData, "tRT", TRT);
    let plot1Data = screenDataByAttr(plotData, "nUM_1", TRT + "-1");
    let plot2Data = screenDataByAttr(plotData, "nUM_1", TRT + "-2");
    let plot3Data = screenDataByAttr(plotData, "nUM_1", TRT + "-3");

    let chartOption1 = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plot1Data
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "LAI",
                encode: {
                    x: 4,
                    y: 1
                }
            }
        ]
    };

    let chartOption2 = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plot2Data
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "LAI",
                encode: {
                    x: 4,
                    y: 1
                }
            }
        ]
    };

    let chartOption3 = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plot3Data
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "LAI",
                encode: {
                    x: 4,
                    y: 1
                }
            }
        ]
    };

    let chart1 = getAndInitChart("chart-lai-1");
    let chart2 = getAndInitChart("chart-lai-2");
    let chart3 = getAndInitChart("chart-lai-3");

    chart1.setOption(chartOption1);
    chart2.setOption(chartOption2);
    chart3.setOption(chartOption3);

    function resize() {
        chart1.resize();
        chart2.resize();
        chart3.resize();
    }

    addSidebarClickEventHandlerFunction(resize);
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 降雨量和灌溉量图表初始化
 */
function chart_preAndIrr_init() {
    chart_preAndIrr_generate();
    //样区选择按钮监听事件注册
    $("#chart-preAndIrr .nav-pills .dropdown-menu li a").click(function () {
        //直接使用this并不能获得触发事件的对象，需要使用$(this)
        let clickedItemValue = $(this).text();
        //获得属性选择中第二个span
        let clickedBtn = $(this).parent().parent().prev().children();
        let preAttrValue = $(clickedBtn[1]).text();
        //是否改变了样区
        if (preAttrValue !== clickedItemValue) {
            //使用$表明这是一个jquery对象,防止使用方法时冲突
            $(clickedBtn[1]).text(clickedItemValue);
            let newTRT = $("#chart-preAndIrr .nav-pills .dropdown button span:eq(1)").text();
            //更新图表
            chart_preAndIrr_generate(newTRT);
        }
    });
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 降雨量和灌溉量图表绘制
 */
function chart_preAndIrr_generate(TRT = "1") {
    //从后端取数据
    let chartData = getChartData("climatic/fieldpai", null, 0);
    //获取指定样区的数据
    let plotData = screenDataByAttr(chartData, "tRT", TRT);

    //处理时间格式
    $.each(plotData, function (key, value) {
        value.recordDate = timestampToTime(value.recordDate).split(" ")[0];
    });

    let chartOption = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plotData
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "Precipitation",
                encode: {
                    x: 3,
                    y: 2
                }
            },
            {
                type: "line",
                name: "Irrigation",
                encode: {
                    x: 3,
                    y: 1
                }
            }
        ]
    };

    let chart = getAndInitChart("chart-preAndIrr-1");

    chart.setOption(chartOption);

    function resize() {
        chart.resize();
    }

    addSidebarClickEventHandlerFunction(resize);
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 土壤含水量图表初始化
 */
function chart_swc_init() {
    chart_swc_generate();
    //样区选择按钮监听事件注册
    $("#chart-swc .nav-pills .dropdown-menu li a").click(function () {
        //直接使用this并不能获得触发事件的对象，需要使用$(this)
        let clickedItemValue = $(this).text();
        //获得属性选择中第二个span
        let clickedBtn = $(this).parent().parent().prev().children();
        let preAttrValue = $(clickedBtn[1]).text();
        //是否改变了样区
        if (preAttrValue !== clickedItemValue) {
            //使用$表明这是一个jquery对象,防止使用方法时冲突
            $(clickedBtn[1]).text(clickedItemValue);
            let newDOY = $("#chart-swc .nav-pills .dropdown button span:eq(1)").text();
            let newTRT = $("#chart-swc .nav-pills .dropdown button span:eq(4)").text();
            //更新图表
            chart_swc_generate(newDOY, newTRT);
        }
    });
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 土壤含水量图表初绘制
 */
function chart_swc_generate(DOY = "177", TRT = "1") {
    //从后端取数据
    let chartData = getChartData("field/swc", null, 0);

    //获取指定样区的数据
    let plotData = screenDataByAttr(chartData, "dOY", DOY);
    let plot1Data = screenDataByAttr(plotData, "nUM_1", TRT + "-1");
    let plot2Data = screenDataByAttr(plotData, "nUM_1", TRT + "-2");
    let plot3Data = screenDataByAttr(plotData, "nUM_1", TRT + "-3");

    let chartOption1 = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plot1Data
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "SWC",
                encode: {
                    x: 6,
                    y: 8
                }
            }
        ]
    };

    let chartOption2 = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plot2Data
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "SWC",
                encode: {
                    x: 6,
                    y: 8
                }
            }
        ]
    };

    let chartOption3 = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plot3Data
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "SWC",
                encode: {
                    x: 6,
                    y: 8
                }
            }
        ]
    };

    let chart1 = getAndInitChart("chart-swc-1");
    let chart2 = getAndInitChart("chart-swc-2");
    let chart3 = getAndInitChart("chart-swc-3");

    chart1.setOption(chartOption1);
    chart2.setOption(chartOption2);
    chart3.setOption(chartOption3);

    function resize() {
        chart1.resize();
        chart2.resize();
        chart3.resize();
    }

    addSidebarClickEventHandlerFunction(resize);
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 标准气象站图表初始化
 */
function chart_sws_init() {
    chart_sws_generate();
    //样区选择按钮监听事件注册
    $("#chart-sws .nav-pills .dropdown-menu li a").click(function () {
        //直接使用this并不能获得触发事件的对象，需要使用$(this)
        let clickedItemValue = $(this).text();
        //获得属性选择中第二个span
        let clickedBtn = $(this).parent().parent().prev().children();
        let preAttrValue = $(clickedBtn[1]).text();
        //是否改变了样区
        if (preAttrValue !== clickedItemValue) {
            //使用$表明这是一个jquery对象,防止使用方法时冲突
            $(clickedBtn[1]).text(clickedItemValue);
            let newDate = $("#chart-sws .nav-pills .dropdown button span:eq(1)").text();
            //更新图表
            chart_sws_generate(newDate);
        }
    });
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 标准气象站图表绘制
 */
function chart_sws_generate(Date = "2018-06-23") {
    //从后端取数据
    let chartData = getChartData("climatic/station", null, 0);

    let plotData = [];

    //处理时间格式
    $.each(chartData, function (key, value) {
        value.recordDate = timestampToTime(value.recordDate);
        if (value.recordDate.split(" ")[0] === Date) {
            plotData.push(value);
        }
    });

    let chartOption = {
        legend: {},
        tooltip: {},
        dataset: {
            source: plotData
        },
        xAxis: {
            type: "category"
        },
        yAxis: {},
        series: [
            {
                type: "line",
                name: "AirTemperature",
                encode: {
                    x: 5,
                    y: 1
                }
            },
            {
                type: "line",
                name: "AirHummidity",
                encode: {
                    x: 5,
                    y: 0
                }
            },
            {
                type: "line",
                name: "WindSpeed",
                encode: {
                    x: 5,
                    y: 6
                }
            },
            {
                type: "line",
                name: "RainFall",
                encode: {
                    x: 5,
                    y: 4
                }
            },
            {
                type: "line",
                name: "RadiationAmount",
                encode: {
                    x: 5,
                    y: 3
                }
            }
        ]
    };

    let chart = getAndInitChart("chart-sws-1");

    chart.setOption(chartOption);

    function resize() {
        chart.resize();
    }

    addSidebarClickEventHandlerFunction(resize);
}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 气孔阻力图表初始化--------------------
 */
function chart_sr_init() {
    chart_sr_generate();

}

/**
 * 作者: lwh
 * 时间: 2020.7.7
 * 描述: 气孔阻力图表绘制----------------------
 */
function chart_sr_generate() {

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
        //hasOwnProperty(propertyName) 判断json对象是否有某个属性
        if (value.hasOwnProperty(colName))
            //往json对象数组中添加json对象
            colData.push(value[colName]);
    });
    return colData;
}

/**
 * 作者: SilentSherlock
 * 描述：从JSON对象数组中根据某个属性值筛选部分对象
 */
function screenDataByAttr(chartData, attrName, attrValue) {
    //attrValue传递进来的是String要转换为Number
    let result = [];
    $.each(chartData, function (key, value) {
        //value[attrName]和attrValue的数据类型不能确定，所以不能用全等----------------
        if (value[attrName] == attrValue)
            result.push(value);
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