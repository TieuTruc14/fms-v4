<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li class="active"><a href="/index.action"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
                <li class="active"><a href="/log/list.action"><s:property value="getTitleText('fms.menu.log.vew')" /></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.log.vew')" /></h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.utility.choice')" />
                    <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                </header>
                <section class="panel-body">
                    <s:form id="filter" label="Tìm kiếm " action="detail.action" method="get" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.menu.vehicle')" />:</label>
                            <div class="col-sm-10">
                                <s:textfield cssClass="input-small input-sm input-s-sm form-control inline"
                                             label="Nhập biến số xe"
                                             name="vehicleId"
                                             headerValue="Nhập biến số xe"
                                             data-width="auto"
                                             cssStyle="width:150px"
                                        />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.utility.date')" /></label>
                            <div class="col-sm-10">
                                <div class="input-daterange">
                                    <s:textfield key="day" placeholder="Ngày" theme="simple" cssClass="input-small input-sm input-s-sm form-control inline"/>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group"> <div class="col-sm-4 col-sm-offset-2"> <button type="submit" class="btn btn-primary"><s:property value="getTitleText('fms.button.view')" /></button> </div> </div>
                    </s:form>
                </section>
            </section>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" />
                    <div class="btn-group pull-right">
                        <button data-toggle="dropdown" class="btn btn-xs dropdown-toggle" style=""><i class="fa fa-download"></i> <s:property value="getTitleText('fms.button.export')" /> <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li class="disabled"><a href="#">Acrobat PDF</a></li>
                            <li><a href="detail.door.action?vehicleId=<s:property value="vehicleId" />&start=<s:property value="start" />&end=<s:property value="end" />&format=xlsx">Excel XLSX</a></li>
                            <li class="divider"></li>
                            <li class="disabled"><a href="#">Text CSV</a></li>
                            <li class="disabled"><a href="#">Text XML</a></li>
                        </ul>
                    </div>
                </header>

                <div class="table-responsive">
                <s:iterator var="item" value="items" status="stat">
                    [
                    d:"<s:property value="#item.devive" />",
                    t:"<s:property value="@org.apache.commons.lang3.time.DateFormatUtils@format(#item.unixTime,'yyyy/MM/dd-HH:mm:ss')" />",
                    x:<s:property value="#item.x" />,
                    y:<s:property value="#item.y" />,
                    s:<s:property value="#item.speed" />,
                    dr:"<s:property value="#item.driver" />",
                    i0:<s:property value="#item.i0" />,
                    i1:<s:property value="#item.i1" />,
                    i2:<s:property value="#item.i2" />,
                    i3:<s:property value="#item.i3" />,
                    i4:<s:property value="#item.i4" />,
                    i5:<s:property value="#item.i5" />
                    ]<s:if test="!#stat.last">,</s:if>
                    <br/>
                </s:iterator>
                </div>
            </section>

        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<link rel="stylesheet" href="/assets/js/eternicode-bootstrap-datepicker/css/datepicker.css" type="text/css"/>
<script src="/assets/js/eternicode-bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script>
    $(document).ready(function () {
        $('.input-daterange').datepicker({
            format: "yyyy/mm/dd",
            startDate: "2012/01/01",
            endDate: "",
            todayBtn: "linked",
            calendarWeeks: false,
            autoclose: true,
            todayHighlight: true
        });
    });
</script>









