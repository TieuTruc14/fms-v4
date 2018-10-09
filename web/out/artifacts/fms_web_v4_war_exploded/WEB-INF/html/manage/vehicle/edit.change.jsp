<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<s:set var="formAction" value="%{'vehicle.change.edit.save'}" />

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> <li><a href="#"><s:property value="getTitleText('fms.menu.management')" /></a></li> <li><a href="#">Đổi thiết bị</a></li></ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.admin.management.vehicle.change')" /></h3></div>
                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.admin.management.vehicle.change')" /></header>
                    <s:if test="actionErrors.contains('action.error.permission')">
                        <div class="alert"> <i class="fa fa-info-sign"></i>
                            <h4><code><s:property value="getTitleText('fms.failed.authority')"/></code></h4>
                        </div>
                    </s:if>
                    <div class="panel-body">
                        <s:form method="post" action="%{#formAction}" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
                            <s:hidden name="companyId"/>
                            <s:hidden name="item.company.id"/>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.management.vehicle.old')" /></label>
                                <div class="col-sm-10">
                                        <code><s:property value="item.id" /></code>
                                        <s:hidden name="item.id" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.management.vehicle.new')" /> (*)</label>
                                <div class="col-sm-10">
                                        <div class="input-group m-b">
                                            <span class="input-group-addon"><i class="fa fa-fw fa-truck"></i></span>
                                            <s:textfield name="vehicleId" maxlength="13" placeholder="%{getTitleText(manage)}" cssClass="form-control" />
                                        </div>
                                        <s:fielderror cssStyle="color: red" fieldName="vehicleId"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.management.reason')" />(*)</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon"><i class="fa fa-fw fa-truck"></i></span>
                                        <s:textfield name="note"  placeholder="lý do đổi" cssClass="form-control" />
                                    </div>
                                    <s:fielderror cssStyle="color: red" fieldName="note"/>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a href="/manage/company/detail.action?id=<s:property value="item.company.id" />" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
                                    <button type="submit" data-loading-text="..." class="btn btn-primary"><s:property value="getTitleText('fms.button.update')" /></button>
                                </div>
                            </div>
                        </s:form>
                    </div>
                </section>
        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>
