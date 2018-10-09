<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<s:set var="update" value="%{'edit'}" />
<s:set var="formAction" value="%{'edit.save'}" />
<s:if test="(editMode == 'addnew.save') || (item == null) || (item.id == null) || (item.id == '')">
    <s:set var="update" value="%{'addnew'}" />
    <s:set var="formAction" value="%{'addnew.save'}" />
</s:if>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                <li class="active"><a href="/store/device/list.action"><s:property value="getTitleText('fms.menu.device')"/></a></li>
                <li class="active"><a href="/store/device/stock.out.action"><s:property value="getTitleText('fms.admin.management.delivery.note')" /></a></li>
            <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.button.addnew')" /> <s:property value="getTitleText('fms.admin.management.delivery.note')" /></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.button.addnew')" /> <s:property value="getTitleText('fms.admin.management.delivery.note')" /></h3> </div>

                <div class="alert alert-info"> <button type="button" class="close" data-dismiss="alert">×</button> <i class="fa fa-info-sign"></i><s:property value="getTitleText('fms.please.complete.infor')"/>  </div>
            <s:if test="actionErrors.contains('action.error.permission')">
                <div>
                    <div class="alert alert-danger">
                        <s:property value="getTitleText('fms.failed.authority')"/>
                    </div>
                </div>
            </s:if>
                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.button.addnew')" /> <s:property value="getTitleText('fms.admin.management.delivery.note')" /></header>
                    <div class="panel-body">
                        <s:form method="post" action="stock.out.add.save.action" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">

                            <div class="form-group">
                                <label class="col-sm-2 control-label"> <s:property value="getTitleText('fms.admin.management.stock.name')" /> (*)</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-tag"></i></span>
                                        <s:textfield name="item.name" cssClass="form-control" maxlength="128" placeholder="%{getTitleText(manage)}"/>
                                    </div>
                                    <s:fielderror cssStyle="color: red" fieldName="item.name"/>
                                </div>
                            </div>

                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.note')"/></label>
                                <div class="col-sm-10">
                                    <s:textarea name="item.note" cssClass="form-control" placeholder="%{getTitleText('fms.report.title.note.content')}" />
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a href="/store/device/stock.out.action" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
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
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<script src="/assets/js/select2/select2.min.js"></script>
<script>
    $(document).ready(function() {
        $("#stock_out_add_save_item_companyDes_id").select2();
    });

</script>