<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li class="active"><a href="/index.action"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
                <li class="active"><a href="/utility/view/list.action"><s:property value="getTitleText('fms.menu.view')" /></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.view.content')" /></h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" /></header>
                <div class="table-responsive table-overflow-x-fix">
                    <table id="tblProvince" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Key</th>
                            <th class="box-shadow-inner small_col text-center">Tiếng Anh</th>
                            <th class="box-shadow-inner small_col text-center">Tiếng việt</th>
                            <th class="box-shadow-inner small_col text-center">Taxi</th>
                            <th class="box-shadow-inner small_col text-center">Xe máy</th>
                            <th class="box-shadow-inner small_col text-center">Tàu thủy</th>
                            <th class="box-shadow-inner small_col text-center">Phà</th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                        </tr>
                        </thead>
                        <tbody>

                        <s:iterator var="item" value="items" status="stat">
                            <tr>
                                <td class="text-right"><s:property value="#stat.count"/></td>
                                <td class="text-left"><s:property value="#item.id"/></td>
                                <td class="text-left"><s:property value="#item.nameE"/></td>
                                <td class="text-left"><s:property value="#item.nameVN"/></td>
                                <td class="text-left"><s:property value="#item.nameTaxi"/></td>
                                <td class="text-left"><s:property value="#item.nameBike"/></td>
                                <td class="text-left"><s:property value="#item.nameShip"/></td>
                                <td class="text-left"><s:property value="#item.nameFerry"/></td>
                                <td>
                                    <div class="btn-group">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                        <sec:authorize access="hasAnyRole('ROLE_TEXT_EDIT')">
                                        <ul class="dropdown-menu pull-right">
                                            <li><a href="/utility/view/edit.action?id=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
                                        </ul>
                                       </sec:authorize>
                                    </div>
                                </td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>
                <footer class="panel-footer"></footer>
            </section>
        </section>
        <%--<footer class="footer bg-white b-t b-light"></footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<script>
    $(document).ready(function() {

        $('#tblProvince').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType":"full_numbers"
        });
    });
</script>