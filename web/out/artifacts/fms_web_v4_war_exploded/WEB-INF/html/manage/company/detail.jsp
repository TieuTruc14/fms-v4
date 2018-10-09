<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasAnyRole('ROLE_COMPANY_VIEW')">
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
                            <a class="list-group-item" href="/manage/vehicle/vehicle.list.action?companyId=<s:property value="item.id"/>"> <span class="badge bg-success"><s:property value="vehicles.size" /></span> <i class="fa fa-fw fa-truck icon-muted"></i> <s:property value="getTitleText('fms.menu.vehicle')"/> </a>
                            <a class="list-group-item" href="#"> <span class="badge bg-info"><s:property value="drivers.size" /></span> <i class="fa fa-fw fa-user icon-muted"></i> <s:property value="getTitleText('fms.menu.report.driver')"/> </a>
                        </div>
                    </section>
                    <div class="wrapper">
                        <div>
                            <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.company.code')"/></small><code><s:property value="item.id" /></code></p>
                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.fullname')"/></small><p><s:property value="item.name" /></p>
                            <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.province.name')"/></small>
                            <s:if test="%{item.province != null}"><s:property value="item.province.name" /></s:if><s:else><s:property value="getTitleText('fms.title.updating')"/></s:else></p>

                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.address')"/></small>
                            <s:if test="%{item.address != null}"><p><s:property value="item.address" /></p></s:if>
                            <s:else><p><s:property value="getTitleText('fms.title.updating')"/></p></s:else>

                            <small class="text-uc text-xs text-muted">Email</small>
                            <s:if test="%{item.email != null}"><p><s:property value="item.email" /></p></s:if>
                            <s:else><p><s:property value="getTitleText('fms.title.updating')"/></p></s:else>

                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.phone')"/></small>
                            <s:if test="%{item.phone != null}"><p><s:property value="item.phone" /></p></s:if>
                            <s:else><p><s:property value="getTitleText('fms.title.updating')"/></p></s:else>

                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.customer.kind.of')"/></small>
                            <s:if test="%{item.customerType != null}"><p><s:property value="item.customerType.name" /></p></s:if>
                            <s:else><p><s:property value="getTitleText('fms.title.updating')"/></p></s:else>

                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.note')"/></small><p><s:if test="%{item.note != null}"><s:property value="item.note" /></s:if></p>
                            <div class="line"></div>
                            <p><small class="text-uc text-xs text-muted">Ngày tạo</small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateCreated,'yyyy/MM/dd-HH:mm:ss')" /></p>
                            <p><small class="text-uc text-xs text-muted">Cập nhập</small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateUpdated,'yyyy/MM/dd-HH:mm:ss')" /></p>
                        </div>

                        <sec:authorize access="hasAnyRole('ROLE_COMPANY_EDIT')">
                            <div class="line"></div>
                            <p><a href="/manage/company/edit.action?id=<s:property value="item.id" />" class="btn btn-default btn-block"><i class="fa fa-pencil pull-left"></i><s:property value="getTitleText('fms.button.edit')"/></a></p>
                        </sec:authorize>
                    </div>
                </aside>
                <aside class="bg-light lt b-r">
                    <div class="wrapper">

                        <section class="panel panel-info">
                            <div class="panel-body">
                                <div class="btn-group m-l-sm"> <button class="btn btn-info"><s:property value="getTitleText('fms.menu.management')"/></button> <button class="btn btn-info dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
                                    <ul class="dropdown-menu">
                                        <sec:authorize access="hasAnyRole('ROLE_COMPANY_EDIT')">
                                            <li><a href="/store/device/stock.recovery.action?companyId=<s:property value="item.id" />"><s:property value="getTitleText('fms.admin.management.reclamation.device')"/></a></li>
                                            <li><a href="/manage/company/activity.action?companyId=<s:property value="item.id" />"><s:property value="getTitleText('fms.menu.report.activity')"/></a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_ORG_DELETE')">
                                           <s:if test="item.inforLocked">
                                               <li  class="unlockInforCompany" data-toggle="modal" data-target="#unlockInforCompany" data-company.id="<s:property value="item.id"/>" data-company.name="<s:property value="item.name" />" ><a >Bỏ khóa thông tin KH</a></li>
                                           </s:if><s:else>
                                            <li  class="lockInforCompany" data-toggle="modal" data-target="#lockInforCompany" data-company.id="<s:property value="item.id"/>" data-company.name="<s:property value="item.name" />" ><a >Khóa thông tin KH</a></li>
                                            </s:else>
                                        </sec:authorize>
                                    </ul>
                                </div>
                            </div>
                        </section>

                        <%--Danh sách ng đại diện--%>
                        <section class="panel panel-default">
                            <header class="panel-heading">
                                <i class="fa fa-caret-square-o-right"></i><s:property value="getTitleText('fms.company.represent')" />
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                                <sec:authorize access="hasAnyRole('ROLE_COMPANY_ADD')">
                                    <a class="btn btn-xs btn-primary pull-right" href="/manage/contact/addnew.action?companyId=<s:property value="item.id" />"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
                                </sec:authorize>
                            </header>
                            <section class="panel-body" >
                                <div class="table-responsive">
                                    <table id="tblContact" class="table table-striped table-bordered m-b-none text-sm table-hover">
                                        <thead>
                                        <tr>
                                            <th class="box-shadow-inner small_col text-center">#</th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.name')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.company.position')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.phone')"/></th>
                                            <th class="box-shadow-inner small_col text-center hidden-xs">Email</th>
                                            <th class="box-shadow-inner small_col text-center hidden-xs"><s:property value="getTitleText('fms.report.title.note')"/></th>
                                            <th class="box-shadow-inner small_col text-center hidden-xs"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <s:iterator var="item" value="contacts" status="stat">
                                            <tr>
                                                <td class="text-left" ><s:property value="#stat.count" /></td>
                                                <td class="text-left"><a href="/manage/contact/detail.action?contactId=<s:property value="#item.id" />"><s:property value="#item.name"/></a></td>
                                                <td class="text-left"><s:property value="#item.position"/></td>
                                                <td class="text-left"><s:property value="#item.phone"/></td>
                                                <td class="text-left hidden-xs"><s:property value="#item.email"/></td>
                                                <td class="text-left hidden-xs"><s:property value="#item.note"/></td>

                                                <td>
                                                    <div class="btn-group">
                                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                                        <sec:authorize access="hasAnyRole('ROLE_COMPANY_EDIT')">
                                                            <ul class="dropdown-menu pull-right">
                                                                <li><a href="/manage/contact/edit.action?contactId=<s:property value="#item.id" />&companyId=<s:property value="item.id"/>"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
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
                        </section>


                        <section class="panel panel-default">
                            <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.account')"/><span class="label bg-dark"><s:property value="#drivers.size" /></span>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                                <sec:authorize access="hasAnyRole('ROLE_COMPANY_ADD,ROLE_USER_ADD')">
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

                        <s:if test="item.companyLead==null">
                            <section class="panel panel-default">
                                <header class="panel-heading">
                                    <i class="fa fa-caret-square-o-right"></i>Danh sách công ty trực thuộc  <code><s:property value="childs.size" /></code>
                                    <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                                    <sec:authorize access="hasAnyRole('ROLE_COMPANY_ADD')">
                                        <a class="btn btn-xs btn-primary pull-right" href="/manage/company/addnew.action?companyLead=<s:property value="item.id" />"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
                                    </sec:authorize>
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
                                                    <li><a href="manage/company/detail.action?id=<s:property value="id"/>&page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                                </s:if>
                                                <s:iterator var="item" value="page.pageList" status="stat">
                                                    <li><a href="manage/company/detail.action?id=<s:property value="id"/>&page.pageNumber=<s:property value="#item" />">
                                                        <s:property value="#item" /></a></li>
                                                </s:iterator>
                                                <s:if test="%{page.pageNumber < page.getPageCount()}">
                                                    <li><a href="manage/company/detail.action?id=<s:property value="id"/>&page.pageNumber=<s:property value="%{page.pageNumber + 2 }" />">»</a></li>
                                                </s:if>
                                            </ul>
                                        </div>
                                    </div>
                                </footer>
                            </section>
                        </s:if>



                        <section class="panel panel-default">
                            <header class="panel-heading">
                                <i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.vehicles')"/> <span class="label bg-dark"><s:property value="#vehicles.size" /></span>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                                <sec:authorize access="hasAnyRole('ROLE_VEHICLE_ADD')">
                                    <a class="btn btn-xs btn-primary pull-right" href="/manage/vehicle/vehicle.addnew.action?companyId=<s:property value="item.id" />"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
                                </sec:authorize>
                            </header>
                            <section class="panel-body" >
                                <s:iterator var="itemVehicle" value="vehicles" status="stat" >

                                    <div class="btn-group pull-right relative" style="margin-top:25px!important;margin-right:10px;">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random "></i></button>
                                        <ul class="dropdown-menu pull-right topIndex">
                                             <sec:authorize access="hasAnyRole('ROLE_VEHICLE_EDIT')">
                                                <li><a href="/manage/vehicle/vehicle.edit.action?companyId=<s:property value="#itemVehicle.company.id" />&vehicleId=<s:property value="#itemVehicle.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
                                                <li><a href="/manage/vehicle/vehicle.change.edit.action?vehicleId=<s:property value="#itemVehicle.id" />"><i class="fa fa-pencil-square-o"></i><s:property value="getTitleText('fms.admin.management.vehicle.change')" /></a></li>
                                                <li class="changeToPartnerAnother" data-toggle="modal" data-target="#changeToPartnerAnother" data-vehicle.id="<s:property value="#itemVehicle.id"/>" ><a><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.vehicle.change.customer')" /></a></li>

                                            </sec:authorize>

                                        </ul>
                                    </div>
                                    <article class="media ">
                                        <span class="pull-left thumb-sm"><i class="fa fa-truck fa-3x icon-muted"></i></span>
                                        <div class="media-body botIndex" style="padding-left: 5px;">
                                            <a href="/manage/vehicle/vehicle.detail.action?companyId=<s:property value="item.id" />&vehicleId=<s:property value="id" />" class="h4"><s:property value="#itemVehicle.id.toUpperCase()" /></a>
                                            <small class="block"><s:property value="getTitleText('fms.menu.report.driver')"/>: <s:property value="#itemVehicle.driver.name" /></small>
                                            <s:if test="#itemVehicle.disable">
                                                <small class="block"><s:property value="getTitleText('fms.report.title.status')"/> : <code> <s:property value="getTitleText('fms.vehicle.lock')"/></code></small>
                                            </s:if>
                                            <s:else>
                                                <small class="block"><s:property value="getTitleText('fms.report.title.status')"/> : <code> <s:property value="getTitleText('fms.menu.report.activity')"/></code></small>
                                            </s:else>
                                        </div>
                                    </article>
                                    <div class="line pull-in"></div>
                                </s:iterator>
                            </section>
                        </section>

                        <section class="panel panel-default">
                            <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.report.driver')"/> <span class="label bg-dark"><s:property value="#drivers.size" /></span>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                                <sec:authorize access="hasAnyRole('ROLE_DRIVER_ADD')">
                                    <a class="btn btn-xs btn-primary pull-right" href="/manage/driver/driver.addnew.action?companyId=<s:property value="item.id" />"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
                                </sec:authorize>
                            </header>
                            <section class="panel-body" data-height="230px">
                                <s:iterator var="itemDriver" value="drivers" status="stat">
                                    <div class="btn-group pull-right relative" style="margin-top:25px!important;margin-right:10px;">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random "></i></button>
                                        <ul class="dropdown-menu pull-right topIndex">
                                            <sec:authorize access="hasAnyRole('ROLE_DRIVER_EDIT')">
                                                <li><a href="/manage/driver/driver.edit.action?companyId=<s:property value="#itemDriver.company.id" />&driverId=<s:property value="#itemDriver.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
                                                <li class="changeDriverToPartnerAnother" data-toggle="modal" data-target="#changeDriverToPartnerAnother" data-driver.id="<s:property value="#itemDriver.id"/>" data-driver.name="<s:property value="#itemDriver.name"/>" ><a><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.vehicle.change.customer')" /></a></li>

                                            </sec:authorize>

                                        </ul>
                                    </div>
                                    <article class="media">
                                        <span class="pull-left thumb-sm"><i class="fa fa-user fa-3x icon-muted"></i></span>
                                        <div class="media-body">
                                            <a href="/manage/driver/driver.detail.action?driverId=<s:property value="#itemDriver.id" />" class="h4"><s:property value="#itemDriver.name" /></a>
                                            <a href="/manage/driver/driver.detail.action?driverId=<s:property value="#itemDriver.id" />" ><small class="block"><s:property value="#itemDriver.id.toUpperCase()" /></small></a>
                                        </div>
                                    </article>
                                    <div class="line pull-in"></div>
                                </s:iterator>
                            </section>
                        </section>
                    </div>
                </aside>
            </section>
        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
</section>

    <div class="modal fade"  id="lockInforCompany" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;max-height: 300px">
            <div class="modal-content"style="max-width: 500px;max-height: 300px">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title" id="lockInfor">Khóa thông tin khách hàng: <span class="companyname"></span>!</h5>
                </div>
                <s:form id="filter" method="POST"  action="lock.infor.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class=" control-label" style="text-align:center">Việc xác nhận khóa thông tin sẽ không cho sửa thông tin KH này trong tương lai! Chỉ người có quyền sửa khách hàng đã khóa mới có thể sửa.</label>
                            <input class="form-control skill_id" type="hidden" name="id">
                            <%--<s:textfield class="form-control" type="hidden" name="id" />--%>
                        </div>
                    </div>
                    <div class="modal-footer" style="padding: 10px;" >
                        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                        <button type="submit" class="btn btn-sm btn-danger">Xác nhận khóa</button>
                    </div>
                </s:form>
            </div>
        </div>
    </div>

    <div class="modal fade"  id="unlockInforCompany" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;max-height: 300px">
            <div class="modal-content"style="max-width: 500px;max-height: 300px">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title" id="unlockInfor">Bỏ khóa thông tin khách hàng: <span class="companyname"></span>!</h5>
                </div>
                <s:form id="filter" method="POST"  action="unlock.infor.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class=" control-label" style="text-align:center">Việc xác nhận hủy khóa thông tin sẽ cho sửa thông tin KH này trong tương lai bởi tất cả các đại lý, chi nhánh và cha quản lý!</label>
                            <input class="form-control skill_id" type="hidden" name="id">
                                <%--<s:textfield class="form-control" type="hidden" name="id" />--%>
                        </div>
                    </div>
                    <div class="modal-footer" style="padding: 10px;" >
                        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                        <button type="submit" class="btn btn-sm btn-danger">Xác nhận mở khóa</button>
                    </div>
                </s:form>
            </div>
        </div>
    </div>

    <div class="modal fade"  id="changeToPartnerAnother" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;max-height: 300px">
            <div class="modal-content"style="max-width: 500px;max-height: 300px">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title" id="myModalLabel54"><s:property value="getTitleText('fms.vehicle.change.customer')" />: <span class="vehicleIdDelete"></span>!</h5>
                </div>
                <s:form id="filter" method="POST"  action="vehicle.change.company.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                    <div class="modal-body"  style="padding: 10px;">

                        <div class="form-group">
                            <label class="col-sm-4 control-label"><s:property value="getTitleText('fms.menu.company.code')" />:</label>
                            <div class="col-sm-8">
                                <s:textfield name="companyDesCode"  placeholder="Mã đối tác" cssClass="form-control" />
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-9">
                                <input class="form-control skill_id" type="hidden" name="vehicleId">
                                <s:textfield class="form-control " type="hidden" name="id" />
                            </div>
                        </div>

                    </div>
                    <div class="modal-footer" style="padding: 10px;" >
                        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                        <button type="submit" class="btn btn-sm btn-danger">Xác nhận</button>
                    </div>
                </s:form>
            </div>
        </div>
    </div>

    <div class="modal fade"  id="changeDriverToPartnerAnother" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;max-height: 300px">
            <div class="modal-content"style="max-width: 500px;max-height: 300px">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title" id="myModalLabel"><s:property value="getTitleText('fms.vehicle.change.customer')" />: <span class="driver_name"></span>!</h5>
                </div>
                <s:form id="filter" method="POST"  action="driver.change.company.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                    <div class="modal-body"  style="padding: 10px;">

                        <div class="form-group">
                            <label class="col-sm-4 control-label"><s:property value="getTitleText('fms.menu.company.code')" />:</label>
                            <div class="col-sm-8">
                                <s:textfield name="companyDesCodeDriver"  placeholder="Mã đối tác" cssClass="form-control" />
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-9">
                                <input class="form-control skill_id" type="hidden" name="driverId">
                                <s:textfield class="form-control " type="hidden" name="id" />
                            </div>
                        </div>

                    </div>
                    <div class="modal-footer" style="padding: 10px;" >
                        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                        <button type="submit" class="btn btn-sm btn-danger">Xác nhận</button>
                    </div>
                </s:form>
            </div>
        </div>
    </div>

    <link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
    <script src="/assets/js/select2/select2.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".changeToPartnerAnother").click(function(){
                var idvehicle = $(this).data('vehicle.id');
                $(".skill_id").val(idvehicle);
                $(".vehicleIdDelete").text(idvehicle);

            });

            $(".changeDriverToPartnerAnother").click(function(){
                var iddriver = $(this).data('driver.id');
                var drivername = $(this).data('driver.name');
                $(".skill_id").val(iddriver);
                $(".driver_name").text(drivername);

            });
            $(".lockInforCompany").click(function(){
                var id = $(this).data('company.id');
                var companyname = $(this).data('company.name');
                $(".skill_id").val(id);
                $(".companyname").text(companyname);

            });
            $(".unlockInforCompany").click(function(){
                var id = $(this).data('company.id');
                var companyname = $(this).data('company.name');
                $(".skill_id").val(id);
                $(".companyname").text(companyname);

            });
        });
    </script>
</sec:authorize>
