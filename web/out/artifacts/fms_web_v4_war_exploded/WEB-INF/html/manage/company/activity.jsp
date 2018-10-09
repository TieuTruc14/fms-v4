<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable">
            <header class="header b-b b-light" style="min-height: 100px;">
                <ul class="breadcrumb no-margin-bottom no-border no-radius b-b b-light pull-in"> <li><a href="index.html"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                    <li><a href="/manage/company/list.action"><s:property value="getTitleText('fms.menu.company')"/></a></li><li><a href="/manage/company/activity.action?companyId=<s:property value="companyId"/> "><s:property value="getTitleText('fms.menu.report.activity')"/></a></li></ul>
                <div class="m-b-md"> <h3 class="m-b-none"><s:property value="item.name" /> <small>(<s:property value="item.id" />)</small></h3></div>
            </header>

            <section class="hbox stretch">
                <aside class="aside-lg b-r">
                    <section class="panel panel-default m-b-none">
                        <header class="panel-heading bg-light no-border"><div class="clearfix text-center"> <div class="thumb-lg"> <img src="/assets/img/user.png" class=""> </div> </div> </header>
                        <div class="list-group no-radius alt">
                            <a class="list-group-item" href="#"> <span class="badge bg-success"><s:property value="vehicles.size" /></span> <i class="fa fa-fw fa-truck icon-muted"></i> <s:property value="getTitleText('fms.menu.vehicle')"/> </a>
                            <a class="list-group-item" href="#"> <span class="badge bg-info"><s:property value="drivers.size" /></span> <i class="fa fa-fw fa-user icon-muted"></i> <s:property value="getTitleText('fms.menu.report.driver')"/> </a>
                        </div>
                    </section>
                    <div class="wrapper">
                        <div>
                            <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.company.code')"/></small><code><a href="/manage/company/detail.action?id=<s:property value="#item.id" />"><s:property value="item.id" /></a></code></p>
                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.fullname')"/></small><p><a href="/manage/company/detail.action?id=<s:property value="#item.id" />"><s:property value="item.name" /></a></p>
                            <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.province.name')"/></small>
                                <s:if test="%{item.province.name != null}"><s:property value="item.province.name" /></s:if><s:else><s:property value="getTitleText('fms.title.updating')"/></s:else></p>

                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.address')"/></small>
                            <s:if test="%{item.address != null}"><p><s:property value="item.address" /></p></s:if>
                            <s:else><p><s:property value="getTitleText('fms.title.updating')"/></p></s:else>

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

                        <sec:authorize access="hasAnyRole('ROLE_NATION','ROLE_ADMIN','ROLE_PROVINCE')">
                            <div class="line"></div>
                            <p><a href="/manage/company/edit.action?id=<s:property value="item.id" />" class="btn btn-default btn-block"><i class="fa fa-pencil pull-left"></i><s:property value="getTitleText('fms.button.edit')"/></a></p>
                        </sec:authorize>
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
                                    <s:iterator var="item" value="activitys" status="stat">
                                        <article class="timeline-item <s:if test="#stat.odd == false">alt</s:if>">
                                            <div class="timeline-caption">
                                                <div class="panel panel-default">
                                                    <div class="panel-body">
                                                        <span class="arrow <s:if test="#stat.odd == false">right</s:if><s:else>left</s:else>"></span>
                                                        <span class="timeline-icon"><s:property escape="false" value="#item.icon" /></span>
                                                        <span class="timeline-date"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.date,'yyyy/MM/dd HH:mm:ss')" /></span>
                                                        <h5><span><s:property value="#item.actionName" /></span></h5>
                                                        <h5><span><s:property value="#item.actorName" /></span></h5>
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
