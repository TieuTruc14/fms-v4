<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<s:set var="update" value="%{'edit'}" />
<s:set var="formAction" value="%{'driver.edit.save'}" />
<s:if test="(editMode == 'addnew.save') || (item == null) || (item.id == null) || (item.id == '')">
    <s:set var="update" value="%{'addnew'}" />
    <s:set var="formAction" value="%{'driver.addnew.save'}" />
</s:if>

<sec:authorize access="hasAnyRole('ROLE_DRIVER_EDIT')">
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> <li><a href="#"><s:property value="getTitleText('fms.menu.management')" /></a></li> <li><a href="#"><s:property value="getTitleText('fms.menu.report.driver')"/></a></li></ul>
            <s:if test="#update == 'addnew'">
                <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.button.addnew')" /> <s:property value="getTitleText('fms.management.driver.license')" /></h3></div>
            </s:if>
            <s:else>
                <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.button.update')" /> <s:property value="getTitleText('fms.management.driver.license')" /> <small>(<s:property value="item.id" />)</small></h3></div>
            </s:else>

            <s:if test="actionErrors.contains('action.error.dupplicate')">
                <div class="alert alert-danger"> <i class="fa fa-info-sign"></i><s:property value="getTitleText('fms.driver.add.existed')" /> .</div>
            </s:if>
            <s:else>
                <s:if test="#update == 'edit'">
                    <s:set var="driverDisplay" value="item" />
                </s:if>

                <div>
                    <s:if test="hasActionMessages()">
                        <div class="alert alert-success">
                            <s:actionmessage/>
                        </div>
                    </s:if>
                </div>

                <div class="alert alert-info"> <button type="button" class="close" data-dismiss="alert">×</button> <i class="fa fa-info-sign"></i><s:property value="getTitleText('fms.please.complete.infor')"/> </div>

                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> Form <s:property value="getTitleText('fms.button.update')" /></header>
                    <div class="panel-body">
                        <s:form method="post" action="%{#formAction}" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
                            <s:hidden name="companyId" />
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.management.driver.id')" /></label>
                                <div class="col-sm-10">
                                    <s:if test="#update == 'addnew'">
                                        <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-barcode"></i></span> <s:textfield name="item.id" maxlength="20" placeholder="Mã thẻ Lái xe" cssStyle="width:100%;max-width: 250px;" cssClass="form-control" /> </div>
                                        <s:fielderror cssStyle="color: red" fieldName="item.id"/>
                                    </s:if>
                                    <s:else>
                                        <code><s:property value="item.id" /></code>
                                        <s:hidden name="item.id" />
                                    </s:else>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.management.driver.name')" /> (*)</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-crop"></i></span>
                                        <s:textfield name="item.name" maxlength="64" placeholder="Họ Tên" cssStyle="width:100%;max-width: 250px;" cssClass="form-control" />
                                        <s:fielderror fieldName="item.name" cssStyle="color: red"></s:fielderror>
                                    </div>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.phone')" /></label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-crop"></i></span>
                                        <s:textfield name="item.phone" maxlength="13" cssStyle="width:100%;max-width: 250px;" placeholder="Số Điện thoại" cssClass="form-control" />
                                        <s:fielderror fieldName="item.phone" cssStyle="color: red"></s:fielderror>
                                    </div>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.management.driver.license')" />(*)</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-crop"></i></span>
                                        <s:textfield name="item.licenceKey" maxlength="20" cssStyle="width:100%;max-width: 250px;" placeholder="Số giấy phép lái xe" cssClass="form-control" />
                                        <s:fielderror fieldName="item.licenceKey" cssStyle="color: red"></s:fielderror>
                                    </div>
                                </div>
                            </div>

                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.management.driver.license.day')" /></label>
                                <div class="col-sm-10">
                                    <div class="input-daterange">
                                        <s:textfield key="strlicenseDay" placeholder="Ngày cấp" cssStyle="width:100%;max-width: 293px;"
                                                     theme="simple"
                                                     cssClass="input-small input-sm input-s-sm form-control inline"/>
                                    </div>
                                </div>
                            </div>

                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.date.expire')"/></label>
                                <div class="col-sm-10">
                                    <div class="input-daterange">
                                        <s:textfield key="strlicenseExp" placeholder="Ngày hết hạn" cssStyle="width:100%;max-width: 293px;"
                                                     theme="simple"
                                                     cssClass="input-small input-sm input-s-sm form-control inline"/>
                                    </div>
                                </div>
                            </div>

                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a href="/manage/company/detail.action?id=<s:property value="companyId"/>" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
                                    <button type="submit" data-loading-text="..." class="btn btn-primary"><s:property value="getTitleText('fms.button.update')" /></button>
                                </div>
                            </div>
                        </s:form>
                    </div>
                </section>

            </s:else>

        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<link rel="stylesheet" href="/assets/js/eternicode-bootstrap-datepicker/css/datepicker.css" type="text/css"/>
<script src="/assets/js/eternicode-bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<script src="/assets/js/select2/select2.min.js"></script>
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
        $("#driver_edit_save_item_defaultVehicle").select2();
        $("#driver_addnew_save_item_defaultVehicle").select2();
    });
</script>
    </sec:authorize>