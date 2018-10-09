<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasAnyRole('ROLE_COMPANY_VIEW')">
    <section id="content">
        <section class="vbox">
            <section class="scrollable padder">
                <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                    <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                    <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
                    <li><a href="/manage/company/list.action"><s:property value="getTitleText('fms.menu.company')" /></a></li>
                </ul>
                <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.company')" /></h3> </div>

                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>&nbsp;<s:property value="getTitleText('fms.header.data')" /> <s:if test="page.getRowCount()>0">: <code><s:property value="page.getRowCount()" /></code> <s:property value="getTitleText('fms.menu.company')" /></s:if></header>
                    <div class="text-sm wrapper bg-light lt">
                        <s:form cssClass="form-inline padder" role="form" theme="simple">
                            <div class="form-group"><s:textfield name="id" placeholder="%{getTitleText('fms.utility.search.by.code')}" cssClass="input-sm form-control"/></div>
                            <div class="form-group"><s:textfield name="name" placeholder="%{getTitleText('fms.utility.search.by.name')}" cssClass="input-sm form-control"/></div>
                            <div class="form-group"><s:textfield name="owner" type="number" placeholder="Tìm theo owner" cssClass="input-sm form-control"/></div>
                            <button type="submit" class="btn btn-default btn-sm" formaction="list.action"><s:property value="getTitleText('fms.utility.search')"/></button>
                            <sec:authorize access="hasAnyRole('ROLE_COMPANY_ADD')">
                                <a class="btn btn-sm btn-primary pull-right" href="/manage/company/addnew.action"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
                            </sec:authorize>
                        </s:form>
                    </div>
                    <div class="table-responsive">
                        <table id="tblCompany" class="table table-striped table-bordered m-b-none text-sm table-hover">
                            <thead>
                            <tr>
                                <th rowspan="2" class="box-shadow-inner small_col text-center">#</th>
                                <th rowspan="2" class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.code')"/></th>
                                <th rowspan="2" class="box-shadow-inner small_col text-center">Owner V2</th>
                                <th rowspan="2" class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.name')"/></th>
                                <th colspan="4" class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.address')"/></th>
                                <th rowspan="2" class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.phone')"/></th>
                                <th rowspan="2" class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.manager')"/></th>
                                <th rowspan="2" class="box-shadow-inner small_col text-center">ĐTKH</th>
                                <th rowspan="2" class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.department')"/></th>
                                <th rowspan="2" class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.vehicle.count')"/></th>
                                <th rowspan="2" class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.report.driver.count')"/></th>
                                <th rowspan="2" class="box-shadow-inner small_col text-center"></th>
                            </tr>
                            <tr>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.company.address.house')"/></th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.company.address.commue')"/></th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.company.address.district')"/></th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.company.address.province')"/></th>
                            </tr>
                            </thead>
                            <tbody>

                            <s:iterator var="item" value="page.items" status="stat">
                                <tr>
                                    <td class="text-left" ><s:property value="#stat.count" /></td>
                                    <td class="text-left"><a href="/manage/company/detail.action?id=<s:property value="#item.id" />"><s:property value="#item.id"/></a></td>
                                    <td class="text-left"><s:property value="#item.owner"/></td>
                                    <td class="text-left"><a href="/manage/company/detail.action?id=<s:property value="#item.id" />"><s:property value="#item.name"/></a></td>
                                    <td class="text-left"><s:property value="#item.address.no"/></td>
                                    <td class="text-left"><s:property value="#item.address.commune.name"/></td>
                                    <td class="text-left"><s:property value="#item.address.district.name"/></td>
                                    <td class="text-left"><s:property value="#item.address.province.name"/></td>
                                    <td class="text-left"><s:property value="#item.phone"/></td>
                                    <td class="text-left"><a href="/manage/org/detail.action?id=<s:property value="#item.parent.id"/>"><s:property value="#item.parent.name"/></a></td>
                                    <td class="text-left"><a href="/catalog/customer.type.list.action"><s:property value="#item.customerType.name"/></a></td>
                                    <td class="text-left"><a href="/utility/province/detail.action?id=<s:property value="#item.province.id"/>"><s:property value="#item.province.fullName"/></a></td>
                                    <td class="text-left"><s:property value="#item.vehicleCount"/></td>
                                    <td class="text-left"><s:property value="#item.driverCount"/></td>
                                    <td>
                                        <div class="btn-group">
                                            <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                            <sec:authorize access="hasAnyRole('ROLE_COMPANY_EDIT')">
                                                <ul class="dropdown-menu pull-right">
                                                    <li><a href="/manage/company/edit.action?id=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
                                                </ul>
                                            </sec:authorize>
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
                                        <li><a href="/manage/company/list.action?page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                    </s:if>
                                    <s:iterator var="item" value="page.pageList" status="stat">
                                        <li><a href="/manage/company/list.action?page.pageNumber=<s:property value="#item" />"><s:property value="#item"/></a></li>
                                    </s:iterator>
                                    <s:if test="%{page.pageNumber < page.getPageCount()}">
                                        <li><a href="/manage/company/list.action?page.pageNumber=<s:property value="%{page.pageNumber + 2}" />">»</a></li>
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
</sec:authorize>