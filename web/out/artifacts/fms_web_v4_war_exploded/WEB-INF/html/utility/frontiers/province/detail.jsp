<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<section id="content">
    <section class="vbox">
        <section class="scrollable">
            <header class="header b-b b-light" style="min-height: 100px;">
                <ul class="breadcrumb no-margin-bottom no-border no-radius b-b b-light pull-in"> <li><a href="index.html"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                    <li class="active"><a href="/index.action"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                    <li class="active"><a href="/province/list.action">Danh sách Tỉnh</a></li>
                </ul>
                <div class="m-b-md"> <h3 class="m-b-none"><s:property value="item.name" /> <small>(<s:property value="item.id" />)</small></h3></div>
            </header>

            <section class="hbox stretch">
                <aside class="aside-lg b-r">
                    <section class="panel panel-default m-b-none">
                        <header class="panel-heading bg-light no-border"><div id="map" style="height: 200px"></div></header>
                        <div class="list-group no-radius alt">
                            <a class="list-group-item" href="#"> <span class="badge bg-success"><s:property value="item.companyCount" /></span> <i class="fa fa-fw fa-truck icon-muted"></i><s:property value="getTitleText('fms.menu.company')"/> </a>
                            <a class="list-group-item" href="#"> <span class="badge bg-info"><s:property value="item.vehicleCount" /></span> <i class="fa fa-fw fa-truck icon-muted"></i> <s:property value="getTitleText('fms.menu.vehicle')"/> </a>
                        </div>
                    </section>
                    <div class="wrapper">
                        <div>
                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.utility.province.code')"/></small><p><code><s:property value="item.id" /></code></p>
                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.province.name')"/></small><p><s:property value="item.name" /></p>
                        </div>
                        <div class="line"></div>
                    </div>
                </aside>
                <aside class="bg-light lt b-r">
                    <div class="wrapper">
                        <section class="panel panel-default">
                            <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.list')"/> <s:property value="getTitleText('fms.menu.company')"/></header>

                            <div class="table-responsive">
                                <table id="tblCompany" class="table table-striped table-bordered m-b-none text-sm">
                                    <thead>
                                    <tr>
                                        <th class="box-shadow-inner small_col text-center">#</th>
                                        <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.name')"/></th>
                                        <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.address')"/></th>
                                        <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.phone')"/></th>
                                        <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.vehicle.count')"/></th>
                                        <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.report.driver.count')"/></th>
                                        <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <s:set var="requestDate" value="date"/>
                                    <s:iterator var="item" value="page.items" status="stat">
                                        <tr>
                                            <td class="text-left"><s:property value="#stat.count" /></td>
                                            <td class="text-left"><a href="/manage/company/detail.action?id=<s:property value="#item.id" />"><s:property value="#item.name"/></a></td>
                                            <td class="text-left"><s:property value="#item.address"/></td>
                                            <td class="text-left"><s:property value="#item.phone"/></td>
                                            <td class="text-left"><s:property value="#item.vehicleCount"/></td>
                                            <td class="text-left"><s:property value="#item.driverCount"/></td>
                                            <td>
                                                <div class="btn-group">
                                                    <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                                    <ul class="dropdown-menu pull-right">
                                                        <li><a href="/manage/company/edit.action?id=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
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
                                                <li><a href="/utility/province/detail.action?page.pageNumber=<s:property value="%{page.pageNumber}" />&id=<s:property value="id" />">«</a></li>
                                            </s:if>
                                            <s:iterator var="item" value="page.pageList" status="stat">
                                                <li><a href="/utility/province/detail.action?page.pageNumber=<s:property value="#item" />&id=<s:property value="id" />"><s:property value="#item"/></a></li>
                                            </s:iterator>
                                            <s:if test="%{page.pageNumber < page.getPageCount()}">
                                                <li><a href="/utility/province/detail.action?page.pageNumber=<s:property value="%{page.pageNumber + 2}" />&id=<s:property value="id" />">»</a></li>
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

<link rel="stylesheet" type="text/css" href="/assets/js/leaflet-0.7.2/awesome-markers/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/assets/js/leaflet-0.7.2/awesome-markers/leaflet.awesome-markers.css">
<link rel="stylesheet" type="text/css" href="/assets/js/leaflet-0.7.2/leaflet.css" />
<!--[if lte IE 8]>
<link rel="stylesheet" type="text/css" href="/assets/js/leaflet-0.7.2/awesome-markers/css/font-awesome-ie7.min.css">
<![endif]-->

<script type="text/javascript" charset="utf-8" language="javascript" src="/assets/js/leaflet-0.7.2/leaflet.js"></script>
<script type="text/javascript" charset="utf-8" language="javascript" src="/assets/js/leaflet-0.7.2/awesome-markers/leaflet.awesome-markers.min.js"></script>

<script>
    var map;

    $(document).ready(function() {
        map = L.map('map').setView([ <s:property value="item.y" />, <s:property value="item.x" /> ], 7);

        arrowIcon = L.icon({
            iconUrl: '/assets/js/leaflet-0.7.2/images/marker-icon1.png',
            iconSize: [24, 35]
        });


        // Google map
        var ggMap = L.tileLayer('http://mt1.googleapis.com/vt?lyrs=m@216024762&src=apiv3&hl=vi&x={x}&y={y}&z={z}&s=Gali&style=37%7Csmartmaps,59', {
            minZoom: 0,
            maxZoom: 20,
            attribution: 'Map data &copy; <a href="http://maps.google.com">GOOGLE</a>.'
        }).addTo(map);
        L.control.layers( { "GOOGLE" : ggMap } ).addTo(map);

        L.marker([<s:property value="item.y" />, <s:property value="item.x" />],{icon:arrowIcon}).addTo(map);

    });
</script>