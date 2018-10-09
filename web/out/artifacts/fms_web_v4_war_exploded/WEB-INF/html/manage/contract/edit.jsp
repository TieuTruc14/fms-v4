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
                <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                <li><a href="/manage/contract/list.action"><s:property value="getTitleText('fms.menu.contract')"/></a></li></ul>
            </ul>
                <s:if test="#update == 'addnew'">
                    <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.contract.addnew')"/></h3> </div>
                </s:if>
                <s:else>
                    <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.contract.update')"/></h3></div>
                </s:else>

                <div class="m-b-md"> <h3 class="m-b-none"></h3> </div>
                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.contract')"/></header>
                    <div class="panel-body">
                        <s:form method="post" action="%{#formAction}" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
                            <div>
                                <s:if test="actionErrors.contains('action.error.permission')">
                                    <div class="alert"> <i class="fa fa-info-sign"></i>
                                        <h4><code><s:property value="getTitleText('fms.failed.authority')"/></code></h4>
                                    </div>
                                </s:if>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.menu.vehicle')"/>(*)</label>
                                <div class="col-sm-10">
                                    <s:if test="#update == 'addnew'">
                                        <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-barcode"></i></span>
                                            <s:textfield name="item.vehicle.id" maxlength="15" placeholder="%{getTitleText('fms.menu.vehicle.license')}" cssStyle="width:100%;max-width: 250px;" cssClass="form-control" />
                                        </div>
                                        <s:fielderror cssStyle="color: red" fieldName="item.vehicle.id"/>
                                    </s:if>
                                    <s:else>
                                        <code><s:property value="item.vehicle.id" /></code>
                                        <s:hidden name="item.vehicle.id" />
                                        <s:hidden name="item.id" />
                                    </s:else>
                                </div>
                            </div>

                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.date.effect')"/>(*)</label>
                                <div class="col-sm-10">
                                    <div class="input-daterange">
                                        <s:textfield name="strDateStart" id="strDateStart" cssStyle="width:100%;max-width: 293px;" placeholder="%{getTitleText('fms.report.title.date.effect')}" theme="simple" cssClass="input-small input-sm input-s-sm form-control inline"/>
                                    </div>
                                    <s:fielderror cssStyle="color: red" fieldName="strDateStart"/>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.date.expire')"/>(*)</label>
                                <div class="col-sm-10">
                                    <div class="input-daterange" >
                                        <s:textfield id="strDateEnd" name="strDateEnd" cssStyle="width:100%;max-width: 293px;" placeholder="%{getTitleText('fms.report.title.date.expire')}" theme="simple" cssClass="input-small input-sm input-s-sm form-control inline"/>
                                    </div>
                                    <s:fielderror cssStyle="color: red" fieldName="strDateEnd"/>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.note')"/></label>
                                <div class="col-sm-10">
                                    <s:textarea name="item.note" cssClass="form-control"  placeholder="%{getTitleText('fms.report.title.note.content')}" />
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a href="/manage/contract/list.action" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
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

<link rel="stylesheet" href="/assets/js/eternicode-bootstrap-datepicker/css/datepicker.css" type="text/css"/>
<script src="/assets/js/eternicode-bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script>
    $(document).ready(function () {
        $('.input-daterange').datepicker({
            format: "dd/mm/yyyy",
            startDate: "01/01/2012",
            endDate: "",
            todayBtn: "linked",
            calendarWeeks: false,
            autoclose: true,
            todayHighlight: true
        });
        $("#strDateStart").change(function (ev) {
            var a = new Date($('#strDateStart').val().substr(6,4),$('#strDateStart').val().substr(3,2),$('#strDateStart').val().substr(0,2));
            var newDate= new Date(a.getFullYear()+1, a.getMonth()-1, a.getDate());
            $('#strDateEnd').datepicker('setDate',newDate);
        });

    });

</script>