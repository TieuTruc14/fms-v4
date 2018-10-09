<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!-- /.aside -->
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="index.html"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')"/></a></li>
                <li class="active">Dashboard</li>
            </ul>
            <div class="m-b-md"><h3 class="m-b-none">Dashboard</h3>
                <small>Hệ thống giám sát hành trình.</small>
            </div>
            <section class="panel panel-default">
                <div class="row m-l-none m-r-none bg-light lter">
                    <div class="col-sm-6 col-md-3 padder-v b-r b-light">
                                    <span
                                            class="fa-stack fa-2x pull-left m-r-sm"> <i
                                            class="fa fa-circle fa-stack-2x text-info"></i> <i
                                            class="fa fa-shopping-cart fa-stack-1x text-white"></i>
                                    </span>
                        <a class="clear" href="/manage/org/org.list.action">
                            <span class="h3 block m-t-xs"><strong><s:property value="item.branchs"/></strong></span>
                            <small class="text-muted text-uc">Đại lý</small>
                        </a>
                    </div>
                    <div class="col-sm-6 col-md-3 padder-v b-r b-light">
                                    <span
                                            class="fa-stack fa-2x pull-left m-r-sm"> <i
                                            class="fa fa-circle fa-stack-2x text-info"></i> <i
                                            class="fa fa-flag fa-stack-1x text-white"></i>
                                    </span>
                        <a class="clear" href="/manage/company/list.action">
                            <span class="h3 block m-t-xs"><strong><s:property value="item.companys"/></strong></span>
                            <small class="text-muted text-uc">Công ty</small>
                        </a>
                    </div>
                    <div class="col-sm-6 col-md-3 padder-v b-r b-light">
                                    <span
                                            class="fa-stack fa-2x pull-left m-r-sm"> <i
                                            class="fa fa-circle fa-stack-2x text-info"></i> <i
                                            class="fa fa-truck fa-stack-1x text-white"></i>
                                    </span>
                        <a class="clear" href="/manage/vehicle/vehicle.list.action">
                            <span class="h3 block m-t-xs"><strong><s:property value="item.vehicles"/></strong></span>
                            <small class="text-muted text-uc">Phương tiện</small>
                        </a>
                    </div>
                    <div class="col-sm-6 col-md-3 padder-v b-r b-light">
                                    <span
                                            class="fa-stack fa-2x pull-left m-r-sm"> <i
                                            class="fa fa-circle fa-stack-2x text-info"></i> <i
                                            class="fa fa-male fa-stack-1x text-white"></i>
                                    </span>
                        <a class="clear" href="/manage/driver/driver.list.action">
                            <span class="h3 block m-t-xs"><strong><s:property value="item.drivers"/></strong></span>
                            <small class="text-muted text-uc">Lái xe</small>
                        </a>
                    </div>
                </div>
            </section>
            <div class="row">
                <div class="col-md-8">
                    <section class="panel panel-default">
                        <header class="panel-heading font-bold">Biểu đồ lắp thiết bị năm <s:property value="year"/></header>
                        <div class="panel-body">
                            <div id="flot-1ine" style="height:350px"></div>
                        </div>
                        <footer class="panel-footer bg-white no-padder">
                            <div class="row text-center no-gutter">
                                <div class="col-xs-3 b-r b-light"><span
                                        class="h4 font-bold m-t block"><s:property value="dayCount"/></span>
                                    <small class="text-muted m-b block">Hôm nay</small>
                                </div>
                                <div class="col-xs-3 b-r b-light"><span class="h4 font-bold m-t block"><s:property value="weekCount"/></span>
                                    <small class="text-muted m-b block">Tuần này</small>
                                </div>
                                <div class="col-xs-3 b-r b-light"><span class="h4 font-bold m-t block"><s:property value="monthCount"/></span>
                                    <small class="text-muted m-b block">Tháng này</small>
                                </div>
                                <div class="col-xs-3"><span class="h4 font-bold m-t block"><s:property value="yearCount"/></span>
                                    <small class="text-muted m-b block">Năm</small>
                                </div>
                            </div>
                        </footer>
                    </section>
                </div>
                <div class="col-md-4">
                    <section class="panel panel-default">
                        <header class="panel-heading font-bold">
                            Biểu đồ tỷ lệ phương tiện
                        </header>
                        <div class="panel-body">
                            <div id="flot-pie1" style="height:240px">
                            </div>
                        </div>
                    </section>
                </div>
            </div>

        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<!-- Bootstrap --> <!-- App -->
<script src="/assets/js/charts/sparkline/jquery.sparkline.min.js" cache="false"></script>
<script src="/assets/js/charts/easypiechart/jquery.easy-pie-chart.js" cache="false"></script>
<script src="/assets/js/highcharts/highcharts.js" cache="false"></script>
<script src="/assets/js/highcharts/data.js" cache="false"></script>
<script src="/assets/js/highcharts/drilldown.js" cache="false"></script>
<%--flot-pie1--%>

<script>
    var pieData = [
        <s:iterator value="pies" status="stat">
        {
           name: '<s:property value="name" />',
           y: <s:property value="value" />
        }<s:if test="!#stat.last">,</s:if>
        </s:iterator>
    ];

    var monthData = [
        <s:iterator value="months" status="stat">
            {
                name: '<s:property value="name" />',
                y: <s:property value="value" />,
                drilldown:'<s:property value="name" />'
            }<s:if test="!#stat.last">,</s:if>
        </s:iterator>
    ];

    var drilldownData = [
        <s:iterator value="itemDowns" status="stat">
        {
            name: '<s:property value="name" />',
            id: '<s:property value="name" />',
            data:[
                <s:iterator value="items" status="stat">
                    [
                         '<s:property value="name" />',
                          <s:property value="value" />
                    ]
                    <s:if test="!#stat.last">,</s:if>
                </s:iterator>
            ]
        }<s:if test="!#stat.last">,</s:if>
        </s:iterator>
    ];

    $(document).ready(function(){
        $(function () {
            $(document).ready(function () {
                // Build the chart
                $('#flot-pie1').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        type: 'pie'
                    },
                    title: {
                        text: null
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true
                        }
                    },
                    series: [{
                        name: 'Brands',
                        colorByPoint: true,
                        data: pieData
                    }]
                });
            });

            $('#flot-1ine').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Biểu đồ lắp đặt  thiết bị năm <s:property value="year" />'
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    title: {
                        text: 'Số lượng thiết bị'
                    }

                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    series: {
                        borderWidth: 0
                    }
                },

                tooltip: {
                    headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b><br/>'
                },

                series: [{
                    name: 'Month',
                    colorByPoint: false,
                    data: monthData
                }],
                drilldown: {
                    series: drilldownData
                }
            });
        });
    });
</script>