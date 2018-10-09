<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="/index.action"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
                <li><a href="#"><s:property value="getTitleText('fms.menu.report')" /></a></li> </ul>
            <div class="m-b-md">
            <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.report.summary.data')" /></h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.utility.choice')" />
                    <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                </header>
                <section class="panel-body">
                    <s:form id="filter" label="Tìm kiếm " action="summary.xmit.nation.action" method="get" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
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
                <footer class="panel-footer bg-light lter">
                    <s:if test="hasActionErrors()">
                        <s:iterator value="actionErrors">
                            <div class="alert alert-danger"> <s:property escape="true" /> </div>
                        </s:iterator>
                    </s:if>
                </footer>
            </section>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" />
                    <div class="btn-group pull-right">
                        <button data-toggle="dropdown" class="btn btn-xs dropdown-toggle" style=""><i class="fa fa-download"></i> <s:property value="getTitleText('fms.button.export')" /> <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li class="disabled"><a href="#">Acrobat PDF</a></li>
                            <li><a href="summary.xmit.nation.action?start=<s:property value="start" />&end=<s:property value="end" />&format=xlsx">Excel XLSX</a></li>
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
                            <th class="box-shadow-inner small_col text-center" rowspan="2">#</th>
                            <th class="box-shadow-inner small_col text-center" rowspan="2"><s:property value="getTitleText('fms.menu.company')"/></th>
                            <th class="box-shadow-inner small_col text-center" rowspan="2"><s:property value="getTitleText('fms.menu.vehicle.count')"/></th>
                            <th class="box-shadow-inner small_col text-center" rowspan="2"><s:property value="getTitleText('fms.utility.msg')"/></th>
                            <th class="box-shadow-inner small_col text-center" colspan="2"><s:property value="getTitleText('fms.utility.gps.signal.lost')"/></th>
                            <th class="box-shadow-inner small_col text-center" colspan="2"><s:property value="getTitleText('fms.utility.data.lost')"/></th>
                        </tr>
                        <tr>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.times')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.time')"/></th>

                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.times')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.time')"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:set var="totalVehicleCount" value="%{0}" />
                        <s:set var="totalMsgCount" value="%{0}" />
                        <s:set var="totalGPSCount" value="%{0}" />
                        <s:set var="totalGPSDuration" value="%{0}" />
                        <s:set var="totalDataCount" value="%{0}" />
                        <s:set var="totalDataDuration" value="%{0}" />

                        <s:iterator var="item" value="items" status="stat">
                            <s:set var="totalVehicleCount" value="%{#item.company.vehicleCount + #attr.totalVehicleCount}" />
                            <s:set var="totalMsgCount"     value="%{#item.msgCount + #attr.totalMsgCount}" />
                            <s:set var="totalGPSCount"     value="%{#item.countGPSNoSignal + #attr.totalGPSCount}" />
                            <s:set var="totalGPSDuration"  value="%{#item.gpsNoSignalDuration + #attr.totalGPSDuration}" />
                            <s:set var="totalDataCount"    value="%{#item.countDataNoSignal + #attr.totalDataCount}" />
                            <s:set var="totalDataDuration" value="%{#item.dataNoSignalDuration + #attr.totalDataDuration}" />

                            <tr>
                                <td><s:property value="#stat.count" /></td>
                                <td>
                                    <div class="dropdown">
                                        <a data-toggle="dropdown" href="#"><s:property value="#item.company.name" /> <b class="caret"></b></a>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a href="#">Thông tin</a></li>
                                            <li><a href="summary.xmit.company.action?companyId=<s:property value="#item.company.id" />&start=<s:property value="start" />&end=<s:property value="end" />"><s:property value="getTitleText('fms.menu.report.detail')" /></a></li>
                                        </ul>
                                    </div>
                                </td>
                                <td class="text-center"><s:property value="#item.company.vehicleCount"/></td>
                                <td class="text-center"><s:property value="#item.msgCount"/></td>

                                <td class="text-center"><a href="summary.xmit.company.action?companyId=<s:property value="#item.company.id" />&start=<s:property value="start" />&end=<s:property value="end" />"><s:property value="#item.countGPSNoSignal" /></a></td>
                                <td class="text-center"><a href="summary.xmit.company.action?companyId=<s:property value="#item.company.id" />&start=<s:property value="start" />&end=<s:property value="end" />"><s:property value="@org.apache.commons.lang3.time.DurationFormatUtils@formatDuration(#item.gpsNoSignalDuration* 1000, 'HH:mm:ss')" /></a></td>

                                <td class="text-center"><a href="summary.xmit.company.action?companyId=<s:property value="#item.company.id" />&start=<s:property value="start" />&end=<s:property value="end" />"><s:property value="#item.countDataNoSignal" /></a></td>
                                <td class="text-center"><a href="summary.xmit.company.action?companyId=<s:property value="#item.company.id" />&start=<s:property value="start" />&end=<s:property value="end" />"><s:property value="@org.apache.commons.lang3.time.DurationFormatUtils@formatDuration(#item.dataNoSignalDuration* 1000, 'HH:mm:ss')" /></a></td>
                            </tr>
                        </s:iterator>
                        </tbody>
                        <tfoot>
                        <tr>
                            <th class="text-center" colspan="2"><s:property value="getTitleText('fms.report.sum')" /></th>
                            <th class="text-center"><s:property value="#totalVehicleCount" /></th>
                            <th class="text-center"><s:property value="#totalMsgCount" /></th>
                            <th class="text-center"><s:property value="#totalGPSCount" /></th>
                            <th class="text-center"><s:property value="@org.apache.commons.lang3.time.DurationFormatUtils@formatDuration(#totalGPSDuration* 1000, 'HH:mm:ss')" /></th>
                            <th class="text-center"><s:property value="#totalDataCount"/></th>
                            <th class="text-center"><s:property value="@org.apache.commons.lang3.time.DurationFormatUtils@formatDuration(#totalDataDuration* 1000, 'HH:mm:ss')" /></th>
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

<link   href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<link rel="stylesheet" type="text/css" href="/assets/js/eternicode-bootstrap-datepicker/css/datepicker.css" />
<script src="/assets/js/eternicode-bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="/assets/js/select2/select2.min.js"></script>
<script>
    $(document).ready(function() {
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
            "sPaginationType":"full_numbers",
            "aoColumns": [
                { "sClass": "text-right", "sType": "num-html" },
                null,
                { "sClass": "text-center", "sType": "num-html" },
                { "sClass": "text-center", "sType": "num-html" },
                { "sClass": "text-center", "sType": "num-html" },
                { "sClass": "text-center", "sType": "num-html" },
                { "sClass": "text-center", "sType": "num-html" },
                { "sClass": "text-center", "sType": "num-html" }]
        });
    });
</script>