<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<s:set var="drivers" value="%{getDriver(item.id)}" />

<section id="content">
    <section class="vbox">
        <section class="scrollable">
            <header class="header b-b b-light" style="min-height: 100px;">
                <ul class="breadcrumb no-margin-bottom no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> <li><a href="#"><s:property value="getTitleText('fms.menu.management')" /></a></li> <li><a href="/manage/company/list.action"><s:property value="getTitleText('fms.menu.company')"/></a></li></ul>
                <div class="m-b-md"> <h3 class="m-b-none"><s:property value="item.name" /> <small>(<s:property value="item.id" />)</small></h3></div>
            </header>

            <section class="hbox stretch">
                <aside class="aside-lg b-r">
                    <section class="panel panel-default m-b-none">
                        <header class="panel-heading bg-light no-border"><div class="clearfix text-center"> <div class="thumb-lg"> <img src="/assets/img/user.png" class=""> </div> </div> </header>
                        <div class="list-group no-radius alt">
                            <a class="list-group-item" href="#"> <span class="badge bg-success"><s:property value="items.size" /></span> <i class="fa fa-fw fa-truck icon-muted"></i> <s:property value="getTitleText('fms.menu.company')"/> </a>
                            <a class="list-group-item" href="/manage/vehicle/vehicle.list.action?companyId=<s:property value="item.id"/>"> <span class="badge bg-info"><s:property value="vehicles.size" /></span> <i class="fa fa-fw fa-user icon-muted"></i> <s:property value="getTitleText('fms.menu.vehicle')"/> </a>
                        </div>
                    </section>
                    <div class="wrapper">
                        <div>
                            <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.company.code')"/></small><code><s:property value="item.id" /></code></p>
                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.fullname')"/></small><p><s:property value="item.name" /></p>
                            <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.province.name')"/></small>
                            <s:if test="%{item.province.name != null}"><s:property value="item.province.name" /></s:if><s:else><s:property value="getTitleText('fms.title.updating')"/></s:else></p>

                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.address')"/></small>
                            <p><s:property value="item.address" /></p>

                            <small class="text-uc text-xs text-muted">Email</small>
                            <s:if test="%{item.email != null}"><p><s:property value="item.email" /></p></s:if>
                            <s:else><p><s:property value="getTitleText('fms.title.updating')"/></p></s:else>

                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.phone')"/></small>
                            <s:if test="%{item.phone != null}"><p><s:property value="item.phone" /></p></s:if>
                            <s:else><p><s:property value="getTitleText('fms.title.updating')"/></p></s:else>

                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.note')"/></small><p><s:if test="%{item.note != null}"><s:property value="item.note" /></s:if></p>
                            <div class="line"></div>
                            <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.date.created')"/></small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateCreated,'yyyy/MM/dd-HH:mm:ss')" /></p>
                            <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.update')"/></small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateUpdated,'yyyy/MM/dd-HH:mm:ss')" /></p>
                        </div>

                        <sec:authorize access="hasAnyRole('ROLE_ORG_EDIT')">
                            <div class="line"></div>
                            <p><a href="/manage/org/edit.action?id=<s:property value="item.id" />" class="btn btn-default btn-block"><i class="fa fa-pencil pull-left"></i><s:property value="getTitleText('fms.button.edit')"/></a></p>
                        </sec:authorize>
                    </div>
                </aside>
                <aside class="bg-light lt b-r">
                    <div class="wrapper">
                        <sec:authorize access="hasAnyRole('ROLE_COMPANY_ADD')">
                            <section class="panel panel-info">
                                <div class="panel-body">
                                    <div class="btn-group m-l-sm"> <button class="btn btn-info"><s:property value="getTitleText('fms.menu.management')"/></button> <button class="btn btn-info dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
                                        <ul class="dropdown-menu">
                                            <li><a href="/store/device/stock.recovery.action?companyId=<s:property value="item.id" />"><s:property value="getTitleText('fms.admin.management.reclamation.device')"/></a></li>
                                        </ul>
                                    </div>
                                </div>
                            </section>
                        </sec:authorize>
                        <section class="panel panel-default">
                            <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.account')"/><span class="label bg-dark"><s:property value="#drivers.size" /></span>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                                <sec:authorize access="hasAnyRole('ROLE_ORG_ADD,ROLE_USER_ADD')">
                                    <a class="btn btn-xs btn-primary pull-right" href="/utility/user/addnew.action?companyId=<s:property value="item.id" />"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
                                </sec:authorize>
                            </header>
                            <section class="panel-body" data-height="230px">
                                <s:iterator var="itemUser" value="users" status="stat">
                                    <sec:authorize access="hasAnyRole('ROLE_USER_EDIT')">
                                    <div class="btn-group pull-right relative" style="margin-top:25px!important;margin-right:10px;">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random "></i></button>
                                        <ul class="dropdown-menu pull-right topIndex">
                                            <li><a href="/utility/user/edit.action?username=<s:property value="#itemUser.username" />&companyId=<s:property value="#itemUser.company.id"/>"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
                                            <li><a href="/utility/user/authority.action?username=<s:property value="#itemUser.username" />&companyId=<s:property value="#itemUser.company.id"/>"><i class="fa fa-pencil-square-o"></i><s:property value="getTitleText('fms.admin.user.grants')" /></a></li>
                                        </ul>
                                    </div>
                                    </sec:authorize>
                                    <article class="media">
                                        <span class="pull-left thumb-sm"><i class="fa fa-user fa-3x icon-muted"></i></span>
                                        <div class="media-body">
                                            <a href="/utility/user/edit.action?username=<s:property value="#itemUser.username" />&companyId=<s:property value="#itemUser.company.id" />" class="h4"><s:property value="#itemUser.username" /></a>
                                            <a href="/utility/user/edit.action?username=<s:property value="#itemUser.username" />&companyId=<s:property value="#itemUser.company.id" />"><small class="block"><s:property value="#itemUser.password" /></small></a>
                                        </div>
                                    </article>
                                    <div class="line pull-in"></div>
                                </s:iterator>
                            </section>
                        </section>

                        <section class="panel panel-default">
                            <header class="panel-heading">
                                <i class="fa fa-caret-square-o-right"></i><s:property value="getTitleText('fms.company.list')"/>  <code><s:property value="items.size" /></code>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                                <a class="btn btn-xs btn-primary pull-right" href="/manage/org/addnew.action?companyParentId=<s:property value="item.id" />"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.company.add.agency')"/></a>
                                <a class="btn btn-xs btn-primary pull-right" style="margin-right:10px;" href="/manage/company/addnew.action?companyParentId=<s:property value="item.id" />"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.company.add.customer')"/> </a>
                            </header>
                            <section class="panel-body" >
                                <div class="table-responsive">
                                    <table id="tblCompany" class="table table-striped table-bordered m-b-none text-sm table-hover">
                                        <thead>
                                            <tr>
                                                <th class="box-shadow-inner small_col text-center">#</th>
                                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.code')"/></th>
                                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.name')"/></th>
                                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.phone')"/></th>
                                                <th class="box-shadow-inner small_col text-center hidden-xs"><s:property value="getTitleText('fms.menu.manager')"/></th>
                                                <th class="box-shadow-inner small_col text-center hidden-xs"><s:property value="getTitleText('fms.report.department')"/></th>
                                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.vehicle.count')"/></th>
                                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.report.driver.count')"/></th>
                                                <th class="box-shadow-inner small_col text-center hidden-xs"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <s:set var="requestDate" value="date"/>
                                            <s:iterator var="item" value="page.items" status="stat">
                                                <tr>
                                                    <td class="text-left" ><s:property value="#stat.count" /></td>
                                                    <td class="text-left"><a href="/manage/company/detail.action?id=<s:property value="#item.id" />"><s:property value="#item.id"/></a></td>
                                                    <td class="text-left"><a href="/manage/company/detail.action?id=<s:property value="#item.id" />"><s:property value="#item.name"/></a></td>

                                                    <td class="text-left"><s:property value="#item.phone"/></td>
                                                    <td class="text-left hidden-xs"><s:property value="#item.parent.name"/></td>
                                                    <td class="text-left hidden-xs"><a href="/utility/province/detail.action?id=<s:property value="#item.province.id"/>"><s:property value="#item.province.fullName"/></a></td>
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
                            </section>
                            <footer class="footer bg-white b-t b-light">
                                <div class="row">
                                    <div class="col-sm-4">
                                    </div>
                                    <div class="col-sm-4 text-center">
                                        <small class="text-muted inline m-t-sm m-b-sm"></small>
                                    </div>
                                    <div class="col-sm-12 text-right text-center-xs">
                                        <ul class="pagination pagination-sm m-t-none m-b-none">
                                            <s:if test="%{page.pageNumber > 1}">
                                                <li><a href="manage/org/detail.action?id=<s:property value="id"/>&page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                            </s:if>
                                            <s:iterator var="item" value="page.pageList" status="stat">
                                                <li><a href="manage/org/detail.action?id=<s:property value="id"/>&page.pageNumber=<s:property value="#item" />">
                                                    <s:property value="#item" /></a></li>
                                            </s:iterator>
                                            <s:if test="%{page.pageNumber < page.getPageCount()}">
                                                <li><a href="manage/org/detail.action?id=<s:property value="id"/>&page.pageNumber=<s:property value="%{page.pageNumber + 2 }" />">»</a></li>
                                            </s:if>
                                        </ul>
                                    </div>
                                </div>
                            </footer>
                        </section>
                    </div>
                </aside>
            </section>
        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
</section>
