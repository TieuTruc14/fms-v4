<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
                <li><a href="/manage/contract/list.action"><s:property value="getTitleText('fms.menu.contract')" /></a></li></ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.management.contract')" /></h3> </div>
            <sec:authorize access="hasAnyRole('ROLE_CONTRACT_TYPE_ADD')">
                <div class="m-b-md"> <h3 class="m-b-none"> <a class="btn btn-sm btn-facebook pull-right" href="/manage/contract/list.censored.action"><s:property value="getTitleText('fms.contract.need.confirmation')" /></a></h3> </div>
            </sec:authorize>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" /> <s:if test="page.getRowCount()>0">: <code><s:property value="page.getRowCount()" /></code> <s:property value="getTitleText('fms.menu.contract')" /></s:if></header>
                <div class="text-sm wrapper bg-light lt">
                    <s:form cssClass="form-inline padder" role="form" theme="simple">
                        <div class="form-group"><s:textfield name="vehicleId" placeholder="%{getTitleText('fms.utility.search.by.vehicle')}" cssClass="input-sm form-control"/></div>
                        <button type="submit" class="btn btn-default btn-sm" formaction="list.action"><s:property value="getTitleText('fms.utility.search')"/></button>
                        <sec:authorize access="hasAnyRole('ROLE_CONTRACT_ADD')">
                            <a class="btn btn-sm btn-primary pull-right" href="/manage/contract/addnew.action"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
                        </sec:authorize>
                    </s:form>
                </div>
                <div class="table-responsive">
                    <table id="tblContract" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.vehicle')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.utility.date.created')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.date.effect')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.date.expire')"/></th>
                            <th class="box-shadow-inner small_col text-center hidden-xs"><s:property value="getTitleText('fms.menu.company')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.status')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.note')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:set var="requestDate" value="date"/>
                        <s:iterator var="item" value="page.items" status="stat">
                            <tr>
                                <td class="text-center"><s:property value="#stat.count" /></td>
                                <td class="text-center"><a href="/manage/vehicle/vehicle.detail.action?vehicleId=<s:property value="#item.vehicle.id" />"><s:property value="#item.vehicle.id" /></a></td>
                                <td class="text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateCreated,'dd/MM/yyyy')"/></td>
                                <td class="text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateStart,'dd/MM/yyyy')"/></td>
                                <td class="text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateEnd,'dd/MM/yyyy')"/></td>
                                <td class="text-left hidden-xs"><a href="/manage/company/detail.action?id=<s:property value="#item.company.id" />"><s:property value="#item.company.name" /></a></td>
                                <s:if test="#item.censored">
                                    <td class="text-center"><s:property value="getTitleText('fms.contract.confirmation.ok')"/></td>
                                </s:if>
                                <s:else>
                                    <td class="text-center"><s:property value="getTitleText('fms.contract.confirmation.no.ok')"/></td>
                                 </s:else>

                                <td class="text-center"><s:property value="#item.note"/></td>
                                <td>
                                    <div class="btn-group">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                        <ul class="dropdown-menu pull-right">
                                            <li><a href="edit.action?id=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
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
                        <div class="col-sm-4 hidden-xs"></div>
                        <div class="col-sm-4 text-center"><small class="text-muted inline m-t-sm m-b-sm"></small></div>
                        <div class="col-sm-12 text-right text-center-xs">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <s:if test="%{page.pageNumber > 1}">
                                    <li><a href="/manage/contract/list.action?page.pageNumber=<s:property value="%{page.pageNumber}" />&vehicleId=<s:property value="vehicleId" />">«</a></li>
                                </s:if>
                                <s:iterator var="item" value="page.pageList" status="stat">
                                    <li><a href="/manage/contract/list.action?page.pageNumber=<s:property value="%{page.pageNumber}" />&vehicleId=<s:property value="vehicleId" />"><s:property value="#item"/></a></li>
                                </s:iterator>
                                <s:if test="%{page.pageNumber < page.getPageCount()}">
                                    <li><a href="/manage/contract/list.action?page.pageNumber=<s:property value="%{page.pageNumber + 2}" />&vehicleId=<s:property value="vehicleId" />">»</a></li>
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
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<script src="/assets/js/select2/select2.min.js"></script>
<script>
    $(document).ready(function() {
        $("#list_companyId").select2();
    });

    $('#tblContract').dataTable({
        "bFilter": false,
        "bPaginate": false,
        "bAutoWidth": false,
        "sPaginationType":"full_numbers"
    });
</script>