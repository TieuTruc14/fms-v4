<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<section id="content">
    <section class="vbox">
        <section class="scrollable">
            <header class="header b-b b-light" style="min-height: 100px;">
                <ul class="breadcrumb no-margin-bottom no-border no-radius b-b b-light pull-in">
                    <li><a href="index.html"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                    <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                    <li><a href="/manage/contract/list.action"><s:property value="getTitleText('fms.menu.contract')"/></a></li></ul>
                </ul>
                <div class="m-b-md"> <h3 class="m-b-none"> <small><s:property value="getTitleText('fms.menu.contract.code')"/>:<code> <s:property value="item.code" /></code></small></h3></div>
            </header>

            <section class="hbox stretch">
                <aside class="aside-lg b-r">
                    <section class="panel panel-default m-b-none">
                        <header class="panel-heading bg-light no-border"><div class="clearfix text-center"></div> </header>
                        <div class="list-group no-radius alt">
                            <a class="list-group-item" href="#"> <span class="badge bg-success"><s:property value="contractDetails.size" /></span> <i class="fa fa-fw fa-truck icon-muted"></i> <s:property value="getTitleText('fms.contract.total')"/> </a>
                        </div>
                    </section>
                    <div class="wrapper">
                        <div>
                            <p><small class="text-uc text-xs text-muted"> : </small><br/>
                                <s:if test="%{item.company.name != null}"><s:property value="item.company.name" /></s:if><s:else><s:property value="getTitleText('fms.title.updating')"/></s:else></p>

                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.contract.date.sign')"/> : </small><br/>
                            <s:if test="%{item.dateSign != null}"><p><s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateSign,'dd/MM/yyyy-HH:mm:ss')" /></p></s:if>
                            <s:else><p><s:property value="getTitleText('fms.title.updating')"/></p></s:else>
                            <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.utility.date.update')"/> : </small><br/> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateNote,'dd/MM/yyyy-HH:mm:ss')" /></p>
                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.note')"/> : </small><br/><p><s:if test="%{item.note != null}"><s:property value="item.note" /></s:if></p>
                            <div class="line"></div>
                        </div>

                        <sec:authorize access="hasAnyRole('ROLE_NATION','ROLE_ADMIN','ROLE_PROVINCE')">
                            <div class="line"></div>
                            <p><a href="/manage/contract/edit.action?id=<s:property value="item.id" />" class="btn btn-default btn-block"><i class="fa fa-pencil pull-left"></i><s:property value="getTitleText('fms.button.edit')"/></a></p>
                        </sec:authorize>
                    </div>
                </aside>
                <aside class="bg-light lt b-r">
                    <div class="wrapper">
                        <section class="panel panel-default">
                            <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.contract.list')"/><span class="label bg-dark"><s:property value="#drivers.size" /></span>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                                <sec:authorize access="hasAnyRole('ROLE_NATION','ROLE_ADMIN')">
                                    <a class="btn btn-xs btn-primary pull-right" href="/manage/contract/sub.add.action?contractId=<s:property value="item.id" />"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
                                </sec:authorize>
                            </header>
                            <section class="panel-body" >

                                <div class="table-responsive">
                                    <table id="tblCompany" class="table table-striped table-bordered m-b-none text-sm">
                                        <thead>
                                        <tr>
                                            <th class="box-shadow-inner small_col text-center">#</th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.vehicle')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.date.effect')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.date.expire')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.utility.date.update')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.contract.time')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.note')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <s:iterator var="itemDetail" value="contractDetails" status="stat">
                                            <tr>
                                                <td class="text-center"><s:property value="#stat.count" /></td>
                                                <td class="text-center"><s:property value="#itemDetail.vehicle.id"/></td>
                                                <td class="text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#itemDetail.dateBegin,'dd/MM/yyyy')"/></td>
                                                <td class="text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#itemDetail.dateEnd,'dd/MM/yyyy')"/></td>
                                                <td class="text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#itemDetail.dateUpdate,'dd/MM/yyyy')"/></td>
                                                <td class="text-center"><s:property value="#itemDetail.low"/></td>
                                                <td class="text-center"><s:property value="#itemDetail.note"/></td>
                                                <td>
                                                    <div class="btn-group">
                                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                                        <ul class="dropdown-menu pull-right">
                                                            <li><a href="sub.edit.action?id=<s:property value="#itemDetail.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
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

                                    </div>
                                </footer>

                            </section>
                        </section>

                    </div>
                </aside>
            </section>
        </section>
    </section>
</section>
