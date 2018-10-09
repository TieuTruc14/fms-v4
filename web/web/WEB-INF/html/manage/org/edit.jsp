<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<s:set var="update" value="%{'addnew'}" />
<s:set var="formAction" value="%{'addnew'}" />
<s:if test="(editMode == 'addnew.save') || (item == null) || (item.id == null) || (item.id == '')|| (code == '')">
    <s:set var="update" value="%{'addnew'}" />
    <s:set var="formAction" value="%{'addnew'}" />
</s:if>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                <li><a href="/manage/org/list.action"><s:property value="getTitleText('fms.menu.company')"/></a></li></ul>

                <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.button.update')" />: <s:property value="item.name" /> <small>(<s:property value="item.id" />)</small></h3></div>

            <s:if test="actionErrors.contains('action.error.permission')">
                <div class="alert alert-info"> <i class="fa fa-info-sign"></i>
                    <code><s:property value="getTitleText('fms.failed.authority')"/></code>
                </div>
            </s:if>
            <s:else>
                <s:if test="actionErrors.contains('action.error.space')">
                    <div class="alert alert-info"> <i class="fa fa-info-sign"></i><code><h4>Lỗi: <s:property value="getTitleText('fms.please.complete.infor')"/> </h4></code>
                    </div>
                </s:if>
                <s:if test="hasActionMessages()">
                    <div class="alert alert-success">
                        <s:actionmessage/>
                    </div>
                </s:if>
                <div class="alert alert-info"> <button type="button" class="close" data-dismiss="alert">×</button> <i class="fa fa-info-sign"></i><s:property value="getTitleText('fms.please.complete.infor')"/>  </div>

                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.button.update')"/></header>
                    <div class="panel-body">
                        <s:form id="company" method="post" action="edit.save" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
                            <s:hidden name="item.id" />
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.customer.kind.of')"/><code>(*)</code></label>
                                <div class="col-sm-10">
                                    <div class="form-group">
                                        <div class="col-sm-10">
                                            <s:select theme="simple"
                                                      cssClass="selectpicker span2"
                                                      label="%{getTitleText('fms.customer.kind.of')}"
                                                      list="ListCustomerTypeInterceptor_customerTypes"
                                                      listKey="id"
                                                      listValue="name"
                                                      name="item.customerType.id"
                                                      headerKey=""
                                                      value="item.customerType.id"
                                                      headerValue="%{getTitleText('fms.customer.kind.of')}"
                                                      data-width="auto"
                                                      cssStyle="width:293px;height: 30px"
                                                    />
                                        </div>
                                    </div>
                                    <s:fielderror cssStyle="color: red" fieldName="item.customerType.id"/>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.menu.company.name')" /> <code>(*)</code></label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-tag"></i></span> <s:textfield name="item.name" cssClass="form-control" cssStyle="width:250px;" maxlength="128" placeholder="%{getTitleText('fms.menu.company.name')}"/></div>
                                    <s:fielderror cssStyle="color: red" fieldName="item.name"/>
                                    <small><s:property value="getTitleText('fms.company.add.name.description')" /></small>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">MST/CMT <code>(*)</code></label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-tag"></i></span> <s:textfield name="item.code" cssClass="form-control" cssStyle="width:250px;"  placeholder="MST/CMT"  /></div>
                                    <small><s:property value="getTitleText('fms.company.add.code.description')" /></small>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.address')"/><code>(*)</code></label>
                                <div class="col-sm-10">
                                    <div class="col-sm-3" style="padding-left: 0px;">
                                        <s:select theme="simple"
                                                  cssClass="selectpicker span2"
                                                  label="%{getTitleText('fms.report.choice.department')}"
                                                  list="ListProvinceInterceptor_provinces"
                                                  listKey="id"
                                                  listValue="fullName"
                                                  name="item.address.province.id"
                                                  id="provinceId"
                                                  headerKey=""
                                                  headerValue="%{getTitleText('fms.report.choice.department')}"
                                                  data-width="auto"
                                                  cssStyle="width:100%;height: 30px"
                                                />
                                    </div>
                                    <div class="col-sm-3">
                                        <s:select theme="simple"
                                                  cssClass="selectpicker span2"
                                                  label="%{getTitleText('fms.company.address.district')}"
                                                  list="districts"
                                                  listKey="id"
                                                  listValue="fullName"
                                                  name="item.address.district.id"
                                                  id="districtId"
                                                  headerKey=""
                                                  headerValue="%{getTitleText('fms.company.address.district')}"
                                                  data-width="auto"
                                                  cssStyle="width:100%;height: 30px"
                                                />
                                    </div>
                                    <div class="col-sm-3">
                                        <s:select theme="simple"
                                                  cssClass="selectpicker span2"
                                                  label="%{getTitleText('fms.company.address.commue')}"
                                                  list="communes"
                                                  listKey="id"
                                                  listValue="fullName"
                                                  name="item.address.commune.id"
                                                  id="communeId"
                                                  headerKey=""
                                                  headerValue="%{getTitleText('fms.company.address.commue')}"
                                                  data-width="auto"
                                                  cssStyle="width:100%;height: 30px"
                                                />
                                    </div>
                                    <div class="col-sm-3">
                                        <s:textfield name="item.address.no" cssClass="form-control" cssStyle="width:100%;height:27px;" placeholder="%{getTitleText('fms.company.address.house')}"  />
                                    </div>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.phone')"/><code>(*)</code></label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-phone"></i></span>
                                        <s:textfield name="item.phone" cssClass="form-control" cssStyle="width:250px;" placeholder="Phone" />
                                    </div>
                                    <s:fielderror cssStyle="color: red" fieldName="item.phone"/>
                                    <small><s:property value="getTitleText('fms.company.add.phone.description')"/></small>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Email</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-envelope-o"></i></span> <s:textfield name="item.email" cssClass="form-control" cssStyle="width:250px;" placeholder="Email" /></div>
                                    <s:fielderror cssStyle="color: red" fieldName="item.email"/>
                                </div>
                            </div>


                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Web</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-globe"></i></span> <s:textfield name="item.url" cssClass="form-control" cssStyle="width:250px;" placeholder="http://eposi.vn" /></div>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.menu.manager')"/><code>(*)</code></label>
                                <div class="col-sm-10">
                                    <s:select theme="simple"
                                              cssClass="selectpicker span2"
                                              label="%{getTitleText('fms.menu.choice.manager')}"
                                              list="ListOrganizationByUserInterceptor_orgazations"
                                              listKey="id"
                                              listValue="name"
                                              name="item.parent.id"
                                              headerKey=""
                                              headerValue="%{getTitleText('fms.menu.choice.manager')}"
                                              data-width="auto"
                                              cssStyle="width:293px;height: 30px"
                                            />
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
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.company.add.technician')"/>: </label>
                                <div class="col-sm-10">
                                    <div >
                                        <label class="checkbox-inline">
                                            <s:checkbox name="item.technician" fieldValue="true" label="%{getTitleText('fms.company.add.technician')}"/>
                                        </label>
                                    </div>
                                    <small><s:property value="getTitleText('fms.company.add.tecgnician.description')"/></small>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a href="/manage/org/org.list.action" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
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
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<script src="/assets/js/select2/select2.min.js"></script>
<script>
    $(document).ready(function() {
        $("#company_item_province_id").select2();
        $("#company_item_parent_id").select2();
        $("#company_item_customerType_id").select2();

        $("#provinceId").select2();
        $("#districtId").select2();
        $("#communeId").select2();

        $('#phoneId').change(function() {
            var phone=$('#phoneId').val();

            $.getJSON("/manage/company/company.check.phone.json.action?phone=" + phone,function (data) {

                if(data.length){
                    $('#errorPhone').text('Lưu ý: SĐT này đã trùng với khách hàng trên hệ thống!');
                    $('#showthongbao').text('');
                }else{
                    $('#errorPhone').text('');
                    $('#showthongbao').text('SĐT khả dụng!');
                }
            });

        });


        $('#provinceId').change(function() {
            var provinceId=$('#provinceId').val();
            $.getJSON("/utility/district/list.by.province.action?provinceId=" + provinceId,function (data) {
                if(data.length){
                    $('#districtId').text("");
                    $('#districtId').select2();
                    for (var i = 0; i < data.length; i++) {
                        $('#districtId').append('<option value="' + data[i][0] + '">' + data[i][1] + '</option>');
                    }

                }
            });

        });

        $('#districtId').change(function() {
            var districtId=$('#districtId').val();
            $.getJSON("/utility/commune/list.by.district.action?districtId=" + districtId,function (data) {
                if(data.length){
                    $('#communeId').text("");
                    $('#communeId').select2();
                    for (var i = 0; i < data.length; i++) {
                        $('#communeId').append('<option value="' + data[i][0] + '">' + data[i][1] + '</option>');
                    }
                }
            });

        });


    });

    $('#code1 :input').change(function() {
        <s:set var="item" value="%{null}" />
        <s:set var="update" value="%{'addnew'}" />
        <s:set var="formAction" value="%{'addnew'}" />
        $('#company').submit();
    });

</script>