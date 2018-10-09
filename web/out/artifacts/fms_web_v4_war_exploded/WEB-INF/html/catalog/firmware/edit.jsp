<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<s:set var="update" value="%{'edit'}" />
<s:set var="formAction" value="%{'firmware.edit.save'}" />
<s:if test="(item == null) || (item.id == null) || (item.id == '')">
    <s:set var="update" value="%{'addnew'}" />
    <s:set var="formAction" value="%{'firmware.addnew.save'}" />
</s:if>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.catalog')" /></a></li>
                <li><a href="/catalog/firmware.list.action">Firmware</a></li>
            </ul>
            <div class="m-b-md">
                <s:if test="#update == 'addnew'">
                    <h3 class="m-b-none"><s:property value="getTitleText('fms.button.addnew')" /></h3>
                </s:if>
                <s:else>
                    <h3 class="m-b-none"><s:property value="getTitleText('fms.button.update')" /></h3>
                </s:else>
            </div>
            <s:if test="actionErrors.contains('action.error.permission')">
                <div class="alert"> <i class="fa fa-info-sign"></i>
                    <code><s:property value="getTitleText('fms.failed.authority')"/></code>
                </div>
            </s:if>
                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>
                        <s:if test="#update == 'addnew'">
                           <s:property value="getTitleText('fms.button.addnew')" />
                        </s:if>
                        <s:else>
                            <s:property value="getTitleText('fms.button.update')" />
                        </s:else>
                    </header>
                    <div class="panel-body">
                        <s:form method="post" action="%{#formAction}" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
                            <s:if test="#update!='addnew'">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">ID (*)</label>
                                    <div class="col-sm-10">
                                        <code><s:property value="item.id" /></code>
                                        <s:hidden name="item.id" />
                                    </div>
                                </div>
                            </s:if><s:else>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.name')"/>(*)</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.id" maxlength="100" cssStyle="width:100%;max-width:250px;" placeholder="" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                        </s:else>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Dota</label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                <span class="input-group-addon">
                                    <i class="fa fa-fw fa-pencil"></i>
                                </span>
                                    <s:textarea name="item.dota" rows="1"  placeholder="Dota" cssClass="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Noi dung</label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                <span class="input-group-addon">
                                    <i class="fa fa-fw fa-pencil"></i>
                                </span>
                                    <s:textarea name="item.content" rows="12"  placeholder="Noi dung Firmware" cssClass="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Liên quan</label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                            <span class="input-group-addon">
                                <i class="fa fa-fw fa-pencil"></i>
                            </span>
                                    <s:textarea name="item.relative" placeholder="Danh sách các Firmware liên quan" cssClass="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.note')"/></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                    <span class="input-group-addon">
                                        <i class="fa fa-fw fa-pencil"></i>
                                    </span>
                                    <s:textarea name="item.note" placeholder="%{getTitleText('fms.report.title.note')}" cssClass="form-control" />
                                </div>
                            </div>
                        </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a href="/catalog/firmware.list.action" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
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
