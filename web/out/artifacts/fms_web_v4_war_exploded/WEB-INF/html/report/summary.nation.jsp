<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
                <li><a href="/report/index.action"><s:property value="getTitleText('fms.menu.report')" /></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.report.summary.nation')" /></h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" /></header>
                <div class="table-responsive">
                    <table id="tblCompany" class="table table-striped table-bordered m-b-none text-sm table-hover">
                        <thead>
                            <tr>
                                <th class="box-shadow-inner small_col text-center">#</th>
                                <th class="box-shadow-inner small_col text-center" data-toggle="tooltip" data-container="body" data-placement="top" title="Hiện lên ở đây"><s:property value="getTitleText('fms.report.title.name')"/></th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.department')"/></th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.vehicle.count')"/></th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.report.driver.count')"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        <s:set var="requestDate" value="date"/>
                        <s:iterator var="item" value="page.items" status="stat">
                            <tr>
                                <td class="text-left" ><s:property value="#stat.count" /></td>
                                <td class="text-left"><a href="/manage/company/detail.action?id=<s:property value="#item.id" />"><s:property value="#item.name"/></a></td>
                                <td class="text-left"><a href="/utility/province/detail.action?id=<s:property value="#item.province.id"/>"><s:property value="#item.province.fullName"/></a></td>
                                <td class="text-left"><s:property value="#item.vehicleCount"/></td>
                                <td class="text-left"><s:property value="#item.driverCount"/></td>
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
                                    <li><a href="/report/summary.nation.action?page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                </s:if>
                                <s:iterator var="item" value="page.pageList" status="stat">
                                    <li><a href="/report/summary.nation.action?page.pageNumber=<s:property value="#item" />"><s:property value="#item"/></a></li>
                                </s:iterator>
                                <s:if test="%{page.pageNumber < page.getPageCount()}">
                                    <li><a href="/report/summary.nation.action?page.pageNumber=<s:property value="%{page.pageNumber + 2}" />">»</a></li>
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

<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<script>
    $(document).ready(function() {
        $('#tblCompany').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType":"full_numbers"
        });
    });
</script>
