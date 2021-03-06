<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="/report/index.action"><s:property value="getTitleText('fms.menu.report')" /></a></li>
                <li><a href="#"><s:property value="getTitleText('fms.menu.report.detail')" /></a></li>
                <li class="active"><s:property value="getTitleText('fms.menu.report.door')" /></li> </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.report.detail.door')" /></h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.utility.choice')" />
                    <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                </header>
                <section class="panel-body">
                    <s:form id="filter" label="Tìm kiếm " action="detail.door.action" method="get" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.menu.vehicle')"/></label>
                            <div class="col-sm-10">
                                <s:select theme="simple"
                                          cssClass="selectpicker span2"
                                          label="%{getTitleText('fms.report.title.choice.vehicle')}"
                                          list="ListVehicleOfCompanyInterceptor_vehicles"
                                          listKey="id"
                                          listValue="id"
                                          name="vehicleId"
                                          headerKey=""
                                          value="vehicleId"
                                          headerValue="%{getTitleText('fms.report.title.choice.vehicle')}"
                                          data-width="auto"
                                          cssStyle="width:300px"
                                        />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.utility.date')" /></label>
                            <div class="col-sm-10">
                                <div class="input-daterange">
                                    <s:textfield key="start" placeholder="Ngày" theme="simple" cssClass="input-small input-sm input-s-sm form-control inline"/>
                                    <span class="add-on">-</span>
                                    <s:textfield key="end" placeholder="Ngày" theme="simple" cssClass="input-small input-sm input-s-sm form-control inline"/>
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

                <div class="table-responsive table-overflow-x-fix">
                    <table id="tblReportData" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col" rowspan="2">#</th>
                            <th class="box-shadow-inner small_col text-center" colspan="3"><s:property value="getTitleText('fms.report.title.time')"/></th>
                            <th class="box-shadow-inner small_col text-center" rowspan="2"><s:property value="getTitleText('fms.report.title.location')"/></th>
                            <th class="box-shadow-inner small_col text-center" rowspan="2"><s:property value="getTitleText('fms.menu.report.driver')"/></th>
                            <th class="box-shadow-inner small_col" rowspan="2"></th>
                        </tr>
                        <tr>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.start')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.end')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.time')"/></th>
                        </tr>
                        </thead>
                        <s:set var="totalDuration" value="%{0}" />
                        <tbody>
                        <s:iterator var="item" value="items" status="stat">
                            <s:set var="duration" value="%{#item.endTime.time - #item.beginTime.time}" />
                            <s:set var="totalDuration" value="%{#duration + #attr.totalDuration}" />
                            <tr id="row<s:property value="#stat.count" />">
                                <td><s:property value="#stat.count" /></td>
                                <td><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.beginTime,'yyyy/MM/dd HH:mm:ss')" /></td>
                                <td><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.endTime,'yyyy/MM/dd HH:mm:ss')" /></td>
                                <td class="text-center"><s:property value="@org.apache.commons.lang3.time.DurationFormatUtils@formatDuration(#duration, 'H:mm:ss')" /></td>
                                <td><s:property value="#item.address" /> ( <s:property value="@com.eposi.common.util.FormatUtil@formatDecimal(#item.x,'#0.######')" /> <s:property value="@com.eposi.common.util.FormatUtil@formatDecimal(#item.y,'#0.######')" /> )</td>
                                <td></td>
                                <td>
                                    <div class="btn-group pull-right">
                                        <button class="btn btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-external-link-square"></i></button>
                                        <ul class="dropdown-menu">
                                            <li><a href="/tracking/history.action?vehicleId=<s:property value="vehicleId" />&start=<s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.beginTime,'dd/MM/yyyy-HH:mm:ss')" />&end=<s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.endTime,'dd/MM/yyyy-HH:mm:ss')" />"><i class="fa fa-map-marker"></i> Bản đồ</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </s:iterator>
                        </tbody>
                        <tfoot>
                        <tr>
                            <th class="text-center" colspan="3"><s:property value="getTitleText('fms.report.sum')" /></th>
                            <th class="text-center"><s:property value="@org.apache.commons.lang3.time.DurationFormatUtils@formatDuration(#totalDuration, 'HH:mm:ss')" /></th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </section>

        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="/assets/js/eternicode-bootstrap-datepicker/css/datepicker.css" type="text/css" />
<script src="/assets/js/eternicode-bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" href="/assets/js/select2/select2.css" type="text/css" />
<script src="/assets/js/select2/select2.min.js"></script>
<script>
$(document).ready(function() {
    $("#filter_provinceId").select2();
    $("#filter_companyId").select2();
    $("#filter_vehicleId").select2();

    $("#filter_provinceId").on("change", function() {
        $( "#filter" ).submit();
    });
    $("#filter_companyId").on("change", function() {
        $( "#filter" ).submit();
    });

    $('.input-daterange').datepicker({
        format: "dd/mm/yyyy",
        startDate: "01-01-2014",
        endDate: "new Date()",
        todayBtn: "linked",
        calendarWeeks: false,
        autoclose: true,
        todayHighlight: true
    });

    $('#tblReportData').dataTable({
        "bFilter": false,
        "bPaginate": false,
        "bAutoWidth": false,
        "sPaginationType":"full_numbers"
    });
});
</script>









