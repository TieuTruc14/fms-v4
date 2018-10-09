<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
                <li><a href="/report/index.action"><s:property value="getTitleText('fms.menu.report')" /></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.report.system.activity')" /></h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.utility.choice')" />
                    <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                </header>
                <section class="panel-body">
                    <s:form id="filter" label="Tìm kiếm " action="system.activity.action" method="get" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
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
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" /></header>
                <div class="table-responsive">
                    <table id="tblSystem" class="table table-striped table-bordered m-b-none text-sm table-hover">
                        <thead>
                            <tr>
                                <th class="box-shadow-inner small_col text-center">#</th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.time')"/></th>
                                <th class="box-shadow-inner small_col text-center" ><s:property value="getTitleText('fms.report.title.name')"/></th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.report.activity')"/></th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.object')"/></th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.utility.content')"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        <s:set var="requestDate" value="date"/>
                        <s:iterator var="item" value="activitys" status="stat">
                            <tr>
                                <td class="text-center" ><s:property value="#stat.count" /></td>
                                <td class="text-center"><s:property  value="@com.eposi.common.util.FormatUtil@formatDate(#item.date,'yyyy/MM/dd HH:mm:ss')"/></td>
                                <td class="text-center"><s:property  value="#item.actorName"/></td>
                                <td class="text-center"><s:property  value="#item.actionName"/></td>
                                <td class="text-center"><a href="<s:property  value="#item.indirectObjectName"/>"><s:property  value="#item.objectId"/></a></td>
                                <td class="text-center"><s:property  value="#item.context"/></td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>

                <footer class="panel-footer">
                    <div class="row">
                        <div class="col-sm-4 hidden-xs"></div>
                        <div class="col-sm-4 text-center"><small class="text-muted inline m-t-sm m-b-sm"></small></div>
                        <div class="col-sm-12 text-right text-center-xs">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <s:if test="%{page.pageNumber > 1}">
                                    <li><a href="/report/system.activity.action?page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                </s:if>
                                <s:iterator var="item" value="page.pageList" status="stat">
                                    <li><a href="/report/system.activity.action?page.pageNumber=<s:property value="#item" />"><s:property value="#item"/></a></li>
                                </s:iterator>
                                <s:if test="%{page.pageNumber < page.getPageCount()}">
                                    <li><a href="/report/system.activity.action?page.pageNumber=<s:property value="%{page.pageNumber + 2}" />">»</a></li>
                                </s:if>
                            </ul>
                        </div>
                    </div>
                </footer>
            </section>

        </section>
        <%--<footer class="footer bg-white b-t b-light"></footer>--%>
    </section>
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
            todayBtn: "linked",
            autoclose: true,
            todayHighlight: true
        });

        $('#tblSystem').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType":"full_numbers"
        });
    });
</script>
