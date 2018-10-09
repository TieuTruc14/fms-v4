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
                <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                <li class="active"><a href="/store/device/list.action"><s:property value="getTitleText('fms.menu.device')"/></a></li>
                <li class="active"><a href="/store/device/stock.inventory.action">Báo cáo tồn kho</a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none">Báo cáo tồn kho</h3> </div>
            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" /></header>
                <div class="table-responsive">
                    <table id="tblCompany" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                            <tr>
                                <th class="box-shadow-inner small_col text-center">#</th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.company')"/></th>
                                <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.admin.management.stock.device.uninstall')"/></th>

                            </tr>
                        </thead>
                        <tbody>
                        <s:set var="requestDate" value="date"/>
                        <s:iterator var="item" value="lstCompanyItem" status="stat">
                            <tr>
                                <td class="text-left"><s:property value="#stat.count" /></td>
                                <td class="text-left"><a href="/store/device/stock.inventory.detail.action?companyId=<s:property value="#item.id" />"><s:property value="#item.name"/></a></td>
                                <td class="text-left"><s:property value="#item.deviceUnused"/></td>

                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>
            </section>

        </section>
        <%--<footer class="footer bg-white b-t b-light"></footer>--%>
    </section>
</section>
