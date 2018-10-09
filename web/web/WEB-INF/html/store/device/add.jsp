<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>



<section id="content">
  <section class="vbox">
    <section class="scrollable padder">
      <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
        <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
        <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
        <li><a href="/store/device/list.action"><s:property value="getTitleText('fms.menu.device')"/></a></li>
        <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.button.edit')" /></a></li>
      </ul>
      <div class="m-b-md">
          <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.device.addnew')" /></h3>
      </div>
      <section class="panel panel-default">
        <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.device')" /></header>
        <div class="panel-body">
          <s:form method="post" action="addnew.save" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
            <div class="form-group">
              <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.menu.device.code')" />(*)</label>
              <div class="col-sm-10">
                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                  <s:textfield name="item.id" maxlength="5" placeholder="%{getTitleText('fms.menu.device.code')}" cssClass="form-control" />
                </div>
                <s:fielderror cssStyle="color: red" fieldName="item.id"/>
              </div>
            </div>

            <div class="line line-dashed line-lg pull-in"></div>

            <div class="form-group">
              <label class="col-sm-2 control-label">Model sản phẩm</label>
              <div class="col-sm-10">
                <s:select theme="simple"
                          cssClass="selectpicker span2"
                          label="Model sản phẩm"
                          list="ListModelInterceptor_models"
                          listKey="id"
                          listValue="id"
                          name="model"
                          headerKey=""
                          headerValue="Model sản phẩm"
                          data-width="auto"
                          cssStyle="width:250px;height: 30px"
                        />
                <s:fielderror cssStyle="color: red" fieldName="modelId"/>
              </div>

            </div>

            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
              <label class="col-sm-2 control-label">Lô sản phẩm(*)</label>
              <div class="col-sm-10">
                <div class="input-group m-b">
                  <span class="input-group-addon"><i class="fa fa-fw"></i> </span>
                  <s:textfield name="batch" maxlength="3" placeholder="Lô sản phẩm" cssClass="form-control" />
                </div>
                <small> <code>Mã lô sản phẩm phải tồn tại trong model sản phẩm rồi</code></small>
                <s:fielderror cssStyle="color: red" fieldName="batch"/>
              </div>
            </div>
            <div class="line line-dashed line-lg pull-in"></div>

            <div class="form-group">
              <label class="col-sm-2 control-label">Firmware</label>
              <div class="col-sm-10">
                <s:select theme="simple"
                          cssClass="selectpicker span2"
                          label="Firmware"
                          list="ListFirmwaresInterceptor_firmwares"
                          listKey="id"
                          listValue="id"
                          name="item.fwv.id"
                          headerKey=""
                          headerValue="Firmware"
                          data-width="auto"
                          cssStyle="width:250px;height: 30px"
                        />
                <s:fielderror cssStyle="color: red" fieldName="item.fwv.id"/>
              </div>

            </div>
            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
              <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.utility.date.of.production')"/></label>
              <div class="col-sm-10">
                <div class="input-daterange">
                  <s:textfield id="strDateStart" name="strDateStart" placeholder="%{getTitleText('fms.utility.date.of.production')}" theme="simple" cssClass="input-small input-sm input-s-sm form-control inline"/>
                </div>
                <s:fielderror cssStyle="color: red" fieldName="strDateStart"/>
              </div>
            </div>

            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
              <div class="col-sm-4 col-sm-offset-2">
                <a href="/store/device/list.action" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
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
    $("#addnew_save_modelId").select2();
    $("#addnew_save_item_fwv_id").select2();

//        $("#strDateStart").change(function (ev) {
//            var a = new Date($('#strDateStart').val().substr(6,4),$('#strDateStart').val().substr(3,2),$('#strDateStart').val().substr(0,2));
//            var newDate= new Date(a.getFullYear()+1, a.getMonth()-1, a.getDate());
//            $('#strDateEnd').datepicker('setDate',newDate);
//        });
  });
</script>