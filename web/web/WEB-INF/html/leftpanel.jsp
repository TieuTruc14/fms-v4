<%@ page import="org.apache.struts2.ServletActionContext" %>
<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%!
    public boolean isActive(String navPath) {
        String namespace = ServletActionContext.getActionMapping().getNamespace();
        String action = ServletActionContext.getActionMapping().getName();
        String activeUri = namespace + "/" + action + ".action";
        if (activeUri.startsWith(navPath)) {
            return true;
        }

        return false;
    }
%>

<aside class="bg-light lter b-r aside-md hidden-print" id="nav">
    <section class="vbox">
        <header class="header bg-primary lter text-center clearfix">
            <div class="btn-group">
                <button type="button" class="btn btn-sm btn-dark btn-icon"><i class="fa fa-envelope"></i></button>
                <div class="btn-group hidden-nav-xs">
                    <a href="/feedback/form.action" class="btn btn-sm btn-primary " ><s:property value="getTitleText('fms.feedback')" /> </a>
                </div>
            </div>
        </header>
        <section class="w-f scrollable">
            <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="5px" data-color="#333333">
                <nav class="nav-primary hidden-xs">
                    <ul class="nav">
                        <sec:authorize access="hasRole('ROLE_DASH_VIEW')">
                        <li class="<%= isActive("/") ? "active" : "" %>"><a href="/dashboard/dashboard.action">
                            <i class="fa fa-dashboard icon">
                                <b class="bg-danger"></b>
                            </i><span>Dashboard</span></a>
                        </li>
                        </sec:authorize>

                         <li class="<%= isActive("/manage/") ? "active" : "" %>"><a href="#" class="<%= isActive("/manage/") ? "active" : "" %>"> <i class="fa fa-align-justify icon"> <b class="bg-info"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span><s:property value="getTitleText('fms.menu.management')" /></span> </a>
                            <ul class="nav lt">
                                <sec:authorize access="hasAnyRole('ROLE_ORG_VIEW')">
                                <li class="<%= isActive("/manage/org/org.list.action") ? "active" : "" %>"><a href="/manage/org/org.list.action"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.organization')" /></span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_COMPANY_VIEW')">
                                <li class="<%= isActive("/manage/company/list.action") ? "active" : "" %>"><a href="/manage/company/list.action"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.company')" /></span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_VEHICLE_VIEW')">
                                <li class="<%= isActive("/manage/vehicle/vehicle.list.action") ? "active" : "" %>"><a href="/manage/vehicle/vehicle.list.action"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.vehicle')" /></span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_CONTRACT_VIEW')">
                                <li class="<%= isActive("/manage/contract/list.action") ? "active" : "" %>"><a href="/manage/contract/list.action"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.contract')" /></span> </a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                        <li class="<%= isActive("/store/") ? "active" : "" %>"><a href="#" class="<%= isActive("/store/") ? "active" : "" %>"> <i class="fa fa-home icon"> <b class="bg-info"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>Kho</span> </a>
                            <ul class="nav lt">
                                <sec:authorize access="hasAnyRole('ROLE_BATCH_VIEW')">
                                    <li class="<%= isActive("/store/batch/list.action") ? "active" : "" %>"><a href="/store/batch/list.action"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.batch')" /></span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_DEVICE_VIEW')">
                                    <li class="<%= isActive("/store/device/list.action") ? "active" : "" %>"><a href="/store/device/list.action"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.device')" /></span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_DEVICE_VIEW')">
                                    <li class="<%= isActive("/store/device/stock.inventory.action") ? "active" : "" %>"><a href="/store/device/stock.inventory.action"> <i class="fa fa-angle-right"></i> <span>Tồn kho</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_STOCK_OUT_VIEW')">
                                    <li class="<%= isActive("/store/device/stock.out.action") ? "active" : "" %>"><a href="/store/device/stock.out.action"> <i class="fa fa-angle-right"></i> <span>Phiếu xuất kho</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_STOCK_IN_VIEW')">
                                    <li class="<%= isActive("/store/device/stock.in.action") ? "active" : "" %>"><a href="/store/device/stock.in.action"> <i class="fa fa-angle-right"></i> <span>Phiếu nhập kho</span> </a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                        <li class="<%= isActive("/log/") ? "active" : "" %>"><a href="#" class="<%= isActive("/log/") ? "active" : "" %>"> <i class="fa fa-archive icon"> <b class="bg-info"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>LOG</span> </a>
                            <ul class="nav lt">
                                <li class="<%= isActive("/log/list.action") ? "active" : "" %>"><a href="/log/list.action" class="<%= isActive("/log") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.log.vew')" /></span> </a></li>
                            </ul>
                        </li>
                        <li class="<%= isActive("/report/") ? "active" : "" %>"><a href="#" class="<%= isActive("/report/") ? "active" : "" %>"> <i class="fa fa-file-text icon"> <b class="bg-info"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span><s:property value="getTitleText('fms.menu.report')" /></span> </a>
                            <ul class="nav lt">
                                <li class="<%= isActive("/report/summary.nation.action") ? "active" : "" %>"><a href="/report/summary.nation.action"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.report.summary.nation')" /></span> </a> </li>
                                <li class="<%= isActive("/report/batch.xmit.action") ? "active" : "" %>"><a href="/report/batch.xmit.action"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.report.batch.send.data')" /></span> </a> </li>
                                <li class="<%= isActive("/report/system.activity.action") ? "active" : "" %>"><a href="/report/system.activity.action"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.report.system.activity')" /></span> </a> </li>
                            </ul>
                        </li>
                         <li class="<%= isActive("/utility/") ? "active" : "" %>"><a href="#" class="<%= isActive("/utility/") ? "active" : "" %>"> <i class="fa fa-flask icon"> <b class="bg-info"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span><s:property value="getTitleText('fms.menu.utility')" /></span> </a>
                            <ul class="nav lt">
                                <sec:authorize access="hasAnyRole('ROLE_USER_VIEW')">
                                <li class="<%= isActive("/utility/user/list.action") ? "active" : "" %>"><a href="/utility/user/list.action" class="<%= isActive("/utility/user/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.account')" /></span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_PROVINCE_VIEW')">
                                <li class="<%= isActive("/utility/province/list.action") ? "active" : "" %>"><a href="/utility/province/list.action" class="<%= isActive("/utility/province/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.provinces')" /></span> </a></li>
                                </sec:authorize>
                                <li class="<%= isActive("/utility/feedback/list.action") ? "active" : "" %>"><a href="/utility/feedback/list.action" class="<%= isActive("/utility/feedback/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.feedback')" /></span> </a></li>
                                <sec:authorize access="hasAnyRole('ROLE_TEXT_VIEW')">
                                <li class="<%= isActive("/utility/view/list.action") ? "active" : "" %>"><a href="/utility/view/list.action" class="<%= isActive("/utility/view/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.view')" /></span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_GROUP_VIEW')">
                                    <li class="<%= isActive("/utility/grant/list.action") ? "active" : "" %>"><a href="/utility/grant/list.action" class="<%= isActive("/utility/grant/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span>Quyền</span> </a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                        <li class="<%= isActive("/setting/") ? "active" : "" %>"><a href="#" class="<%= isActive("/setting/") ? "active" : "" %>"> <i class="fa fa-wrench icon"> <b class="bg-info"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>Cấu hình</span> </a>

                            <ul class="nav lt">
                                <li class="<%= isActive("/setting/list.action") ? "active" : "" %>"><a href="/setting/list.action" class="<%= isActive("/setting/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span>Cấu hình hệ thống</span> </a></li>
                            </ul>
                        </li>
                        <li class="<%= isActive("/catalog/") ? "active" : "" %>"><a href="#" class="<%= isActive("/catalog/") ? "active" : "" %>"> <i class="fa fa-folder-open icon"> <b class="bg-info"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span><s:property value="getTitleText('fms.menu.catalog')" /></span> </a>
                            <ul class="nav lt">
                                <sec:authorize access="hasAnyRole('ROLE_CUSTOMER_TYPE_VIEW')">
                                <li class="<%= isActive("/catalog/customer.type.list.action") ? "active" : "" %>"><a href="/catalog/customer.type.list.action" class="<%= isActive("/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.customer.type')" /></span> <b class="badge pull-right">0</b></a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_CONTRACT_TYPE_VIEW')">
                                    <li class="<%= isActive("/catalog/contract.list.action") ? "active" : "" %>"><a href="/catalog/contract.list.action" class="<%= isActive("/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.contract')" /></span> <b class="badge pull-right">0</b></a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_PRODUCT_TYPE_VIEW')">
                                <li class="<%= isActive("/catalog/product.list.action") ? "active" : "" %>"><a href="/catalog/product.list.action" class="<%= isActive("/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.product.type')" /></span> <b class="badge pull-right">0</b></a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_MODEL_VIEW')">
                                <li class="<%= isActive("/catalog/model.list.action") ? "active" : "" %>"><a href="/catalog/model.list.action" class="<%= isActive("/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.model.type')" /></span> <b class="badge pull-right">0</b></a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_TRANSPORT_VIEW')">
                                <li class="<%= isActive("/catalog/transport.list.action") ? "active" : "" %>"><a href="/catalog/transport.list.action" class="<%= isActive("/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.transport.type')" /></span> <b class="badge pull-right">0</b></a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_FUEL_VIEW')">
                                <li class="<%= isActive("/catalog/fuel.list.action") ? "active" : "" %>"><a href="/catalog/fuel.list.action" class="<%= isActive("/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.menu.fuel')" /></span> <b class="badge pull-right">0</b></a></li>
                                </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_FIRMWARE_VIEW')">
                                <li class="<%= isActive("/catalog/firmware.list.action") ? "active" : "" %>"><a href="/catalog/firmware.list.action" class="<%= isActive("/") ? "active" : "" %>"> <i class="fa fa-angle-right"></i> <span><s:property value="getTitleText('fms.catalog.firmware')" /></span> <b class="badge pull-right">0</b></a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </section>
        <footer class="footer lt hidden-xs b-light">
            <a href="#nav" data-toggle="class:nav-xs" class="pull-right btn btn-sm btn-default btn-icon"> <i class="fa fa-angle-left text"></i><i class="fa fa-angle-right text-active"></i> </a>
        </footer>
    </section>
</aside>