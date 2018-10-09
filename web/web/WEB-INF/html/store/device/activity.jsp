<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable">
            <header class="header b-b b-light" style="min-height: 100px;">
                <ul class="breadcrumb no-margin-bottom no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                    <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                    <li class="active"><a href="/store/device/list.action"><s:property value="getTitleText('fms.menu.device')"/></a></li>
                    <li class="active"><s:property value="getTitleText('fms.menu.report.detail')"/></li>
                </ul>
                <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.detail.device')"/>: <code><s:property value="deviceId" /></code></h3> </div>
            </header>

            <section class="hbox stretch">
                <aside class="aside-lg b-r">
                    <section class="panel panel-default m-b-none">
                        <header class="panel-heading bg-light no-border"><div class="clearfix text-center"> <div class="thumb-lg"> <img src="/assets/images/vehicle_default_avatar.png" class=""> </div> </div> </header>
                        <div class="list-group no-radius alt">
                        </div>
                    </section>
                    <div class="wrapper">
                        <s:if test="%{item == null}">
                            <div class="alert alert-warning alert-block"> <h4><i class="fa fa-bell-alt"></i>Chú ý!</h4> <p>Thiết bị chưa được lắp đặt...</p> </div>
                        </s:if>
                        <s:else>
                            <div>
                                <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.device')"/></small>  <code><s:property value="item.id" /></code></p>
                                <div class="line"></div>
                                <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.date.created')"/></small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateCreated,'yyyy/MM/dd-HH:mm:ss')" /></p>
                                <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.update')"/></small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateUpdated,'yyyy/MM/dd-HH:mm:ss')" /></p>
                                <div class="line"></div>
                                <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.utility.date.of.production')"/></small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateStart,'yyyy/MM/dd')" /></p>
                                <p><small class="text-uc text-xs text-muted"><s:property value="fms.report.title.date.expire')"/></small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateEnd,'yyyy/MM/dd')" /></p>
                                <div class="line"></div>
                                <p><small class="text-uc text-xs text-muted"><b><s:property value="getTitleText('fms.menu.company.info')"/></b></small></p>
                                <s:if test="%{item.vehicle != null}">
                                    <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.vehicle')"/></small>  <code><s:property value="item.vehicle.id" /></code></p>
                                </s:if>
                                <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.company.name')" /></small><p><s:property value="item.company.name" /></p>
                                <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.address')"/></small><p><s:property value="item.company.address" /></p>
                                <small class="text-uc text-xs text-muted">Email</small><p><s:property value="item.company.email" /></p>
                                <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.phone')"/></small><p><s:property value="item.company.phone" /></p>
                            </div>
                        </s:else>
                    </div>
                </aside>
                <aside class="bg-light lt b-r">
                    <div class="wrapper">
                        <section class="panel panel-default">
                            <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.report.activity')"/>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                            </header>
                            <section class="panel-body">
                                <div class="timeline">
                                    <article class="timeline-item active">
                                        <div class="timeline-caption">
                                            <div class="panel bg-primary lter no-borders">
                                                <div class="panel-body">
                                                    <span class="timeline-icon"><i class="fa fa-info time-icon bg-primary"></i></span>
                                                    <span class="timeline-date"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(vehicleState.time,'yyyy/MM/dd HH:mm:ss')" /></span>
                                                    <h5><span><s:property value="vehicleState.address" /></span></h5>
                                                    <div class="m-t-sm timeline-action"><span class="h3 pull-left m-r-sm"><s:property value="vehicleState.speed" /> kmh</span></div>
                                                </div>
                                            </div>
                                        </div>
                                    </article>
                                    <s:iterator var="item" value="activitys" status="stat">
                                        <article class="timeline-item <s:if test="#stat.odd == false">alt</s:if>">
                                            <div class="timeline-caption">
                                                <div class="panel panel-default">
                                                    <div class="panel-body">
                                                        <span class="arrow <s:if test="#stat.odd == false">right</s:if><s:else>left</s:else>"></span>
                                                        <span class="timeline-icon"><s:property escape="false" value="#item.icon" /></span>
                                                        <span class="timeline-date"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.date,'yyyy/MM/dd HH:mm:ss')" /></span>
                                                        <h5><span><s:property value="#item.actionName" /></span></h5>
                                                        <p><s:property value="#item.context" /></p>
                                                    </div>
                                                </div>
                                            </div>
                                        </article>
                                    </s:iterator>
                                    <div class="timeline-footer"><a href="#"><i class="fa fa-plus time-icon inline-block bg-dark"></i></a></div>
                                </div>

                            </section>
                        </section>
                    </div>
                </aside>
            </section>
        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
</section>