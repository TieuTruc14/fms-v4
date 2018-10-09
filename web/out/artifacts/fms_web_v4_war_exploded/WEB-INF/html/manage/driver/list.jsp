<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="index.html"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="#"><s:property value="getTitleText('fms.menu.management')" /></a></li>
                <li><a href="#"><s:property value="getTitleText('fms.menu.report.driver')" /></a></li></ul>
            <div class="m-b-md">
                <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.report.driver.management')"/></h3>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" />
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                        <a class="btn btn-success btn-sm pull-right" href="/manage/driver/driver.addnew.action"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
                    </sec:authorize>
                </header>
                <div class="table-responsive">
                    <table id="tblDriver" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                            <tr>
                                <th class="box-shadow-inner small_col">#</th>
                                <th class="box-shadow-inner mide"><s:property value="getTitleText('fms.menu.card.code')"/></th>
                                <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.menu.fullname')"/></th>
                                <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.phone')"/></th>
                                <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.driver.licence')"/></th>
                                <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.date.effect')"/></th>
                                <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.date.expire')"/></th>
                                <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator var="item" value="page.items" status="stat">
                                <tr>
                                    <td><s:property value="#stat.count" /></td>
                                    <td><s:property value="#item.id"/></td>
                                    <td><a href="/manage/driver/driver.detail.action?driverId=<s:property value="#item.id" />"><s:property value="#item.name"/></a></td>
                                    <td><s:property value="#item.phone"/></td>
                                    <td><s:property value="#item.licenceKey"/></td>
                                    <td><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.licenceDay,'yyyy/MM/dd')"/></td>
                                    <td><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.licenceDay,'yyyy/MM/dd')"/></td>
                                    <td>
                                        <div class="btn-group">
                                            <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                            <ul class="dropdown-menu pull-right">
                                                <li><a href="/manage/driver/driver.edit.action?driverId=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
                                            </ul>
                                        </div>
                                    </td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                </div>
                <footer class="panel-footer">
                    <div class="row">
                        <div class="col-sm-4 hidden-xs">
                        </div>
                        <div class="col-sm-4 text-center">
                            <small class="text-muted inline m-t-sm m-b-sm"></small>
                        </div>
                        <div class="col-sm-12 text-right text-center-xs">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <s:if test="%{page.pageNumber > 1}">
                                    <li><a href="/manage/driver/driver.list.action?page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                </s:if>
                                <s:iterator var="item" value="page.pageList" status="stat">
                                    <li><a href="/manage/driver/driver.list.action?page.pageNumber=<s:property value="#item" />"><s:property value="#item"/></a></li>
                                </s:iterator>
                                <s:if test="%{page.pageNumber < page.getPageCount()}">
                                    <li><a href="/manage/driver/driver.list.action?page.pageNumber=<s:property value="%{page.pageNumber + 2 }" />">»</a></li>
                                </s:if>
                            </ul>
                        </div>
                    </div>
                </footer>
            </section>

        </section>
        <%--<footer class="footer bg-white b-t b-light">--%>
        <%--</footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>
<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<script>
    $(document).ready(function() {

        $('#tblDriver').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType":"full_numbers"
        });
    });
</script>