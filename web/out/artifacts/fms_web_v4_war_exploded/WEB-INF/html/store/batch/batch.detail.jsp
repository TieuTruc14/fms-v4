<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
    .datepicker{
        z-index: 9999!important;
    }
</style>
<section id="content">
    <section class="vbox">
        <section class="scrollable">
            <header class="header b-b b-light" style="min-height: 100px;">
                <ul class="breadcrumb no-margin-bottom no-border no-radius b-b b-light pull-in">
                    <li><a href="index.html"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                    <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                    <li class="active"><a href="/store/batch/list.action">Lô</a></li>
                    <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.report.detail')" /></a></li>
                </ul>
                <div class="m-b-md">
                    <div class="col-xs-6"><h3 class="m-b-none"><s:property value="getTitleText('fms.menu.report.detail')" /> </h3></div>
                    <s:if test="%{!item.locked}">
                        <div class="col-xs-6"><h3 class=" btn btn-sm btn-warning pull-right"  data-toggle="modal" data-target="#lockBatch"><i class="fa fa-lock pull-left"></i>Khóa lô </h3></div>
                    </s:if>
                    <s:hidden id="stockId" name="stockId" value="%{item.id}"/>
                </div>
            </header>

            <section class="hbox stretch">
                <aside class="aside-lg b-r">
                    <section class="panel panel-default m-b-none">
                        <header class="panel-heading bg-light no-border">Thông tin chi tiết</header>
                    </section>
                    <div class="wrapper">
                        <div>
                            <p><small class="text-uc text-xs text-muted"> Mã lô: </small><s:property value="item.id" /></p>
                            <p><small class="text-uc text-xs text-muted">Tên: </small> <s:property value="item.name" /></p>
                            <p><small class="text-uc text-xs text-muted">Số lượng thiết bị: </small><s:property value="page.rowCount" /></p>
                            <p><small class="text-uc text-xs text-muted">Model: </small><s:property value="item.model.name" /></p>
                            <p><small class="box-shadow-inner small_col text-muted">Ngày tạo</small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateCreated,'HH:mm-dd/MM/yyyy')" /></p>
                            <p><small class="box-shadow-inner small_col text-muted">Trạng thái</small>
                                <s:if test="item.locked">
                                    <code>Đã khóa</code>
                                </s:if><s:else>Chưa khóa</s:else>
                            </p>
                        </div>
                            <div class="line"></div>
                        <s:if test="%{!item.locked}">
                            <p><a href="/store/batch/edit.action?id=<s:property value="item.id" />" class="btn btn-default btn-block"><i class="fa fa-pencil pull-left"></i><s:property value="getTitleText('fms.button.edit')"/></a></p>
                        </s:if>
                    </div>
                </aside>
                <aside class="bg-light lt b-r">
                    <s:if test='%{message!=""}'>
                        <%--<section class="panel panel-default">--%>
                            <%--<div class="panel-heading" style=" font-size: 14px;">--%>
                                <%--<a><i class="fa fa-bell-alt"></i><code><s:property value="message"/></code></a>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <section id="collapseOne" class="panel-collapse in" >
                            <div class="panel-body text-sm" style=" font-size: 14px;color:#c7254e;"><s:property value="message"/> </div>
                        </section>
                    </s:if>
                    <s:if test="actionErrors.contains('action.error.permission')">
                        <section class="panel panel-default">
                            <div class="panel-body">
                                <h4><code><s:property value="getTitleText('fms.failed.authority')"/></code></h4>
                            </div>
                        </section>
                    </s:if>
                        <section class="panel panel-default">
                            <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.devices')" /><span class="label bg-dark"><s:property value="#stockDetailList.size" /></span>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"></a></li></ul>
                                <sec:authorize access="hasAnyRole('ROLE_DEVICE_ADD')">
                                    <s:if test="%{!item.locked}">
                                        <button style="margin-top:-7px" class="addDeviceNew btn btn-sm btn-primary pull-right" data-toggle="modal" data-target="#addDeviceNew"><i class="fa fa-plus"></i> Nhập thiết bị</button> &nbsp;&nbsp;
                                        <button style="margin-top:-7px;margin-right:10px;" class="addDeviceNew btn btn-sm btn-primary pull-right" data-toggle="modal" data-target="#addDeviceFromExcel"><i class="fa fa-file"></i> Nhập từ Excel</button>
                                    </s:if>
                                </sec:authorize>
                            </header>
                            <%--<section class="panel-body" >--%>
                                <div class="table-responsive">
                                    <%--<table  class="table table-striped b-t b-light"  id="datatables">--%>
                                    <table  class="table table-striped table-bordered m-b-none text-sm table-hover"  id="datatables">
                                        <thead>
                                        <tr>
                                            <th class="box-shadow-inner small_col text-center">#</th>
                                            <th  class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.device.code')"/></th>
                                            <th  class="box-shadow-inner small_col text-center">Ngày nhập/sản xuất</th>
                                            <th class="box-shadow-inner small_col text-center">Ghi chú</th>
                                            <th  class="box-shadow-inner small_col text-center">Ngày tạo</th>
                                            <th  class="box-shadow-inner small_col text-center">Người tạo</th>
                                            <s:if test="%{!item.locked}">
                                            <th rowspan="2" class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                                             </s:if>
                                        </tr>

                                        </thead>
                                        <tbody>
                                        <s:iterator var="item" value="page.items" status="stat">
                                            <tr>
                                                <td class=" text-center"><s:property value="#stat.count" /></td>
                                                <td class=" text-center"><s:property value="#item.id" /></td>
                                                <td class=" text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateStart,'dd/MM/yyyy')"/></td>
                                                <td class=" text-center"><s:property value="#item.description" /></td>
                                                <td class=" text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateCreated,'dd/MM/yyyy')"/></td>
                                                <td class=" text-center"><s:property value="#item.userCreated"/></td>
                                                <s:if test="%{!item.locked}">
                                                    <td>
                                                        <div class="btn-group">
                                                            <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                                            <ul class="dropdown-menu pull-right">
                                                                <li> <a class="editDevice "
                                                                        data-toggle="modal" data-target="#editDevice"
                                                                        data-device.id="<s:property value="#item.id"/>"
                                                                        data-device.note="<s:property value="#item.note"/>"
                                                                        data-device.start="<s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateStart,'dd/MM/yyyy')" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </td>
                                                </s:if>

                                            </tr>
                                        </s:iterator>
                                        </tbody>
                                    </table>
                                </div>

                                    <footer class="panel-footer">
                                        <div class="row">
                                            <div class="col-sm-4 hidden-xs"></div>
                                            <div class="col-sm-4 text-center"><small class="text-muted inline m-t-sm m-b-sm"></small></div>
                                            <div class="col-sm-12 text-right text-center-xs">
                                                <ul class="pagination pagination-sm m-t-none m-b-none">
                                                    <s:if test="%{page.pageNumber > 1}">
                                                        <li><a href="/store/batch/batch.detail.action?id=<s:property value="%{item.id}"/>&page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                                    </s:if>
                                                    <s:iterator var="item" value="page.pageList" status="stat">
                                                        <li><a href="/store/batch/batch.detail.action?id=<s:property value="%{item.id}"/>&page.pageNumber=<s:property value="#item" />"><s:property value="#item"/></a></li>
                                                    </s:iterator>
                                                    <s:if test="%{page.pageNumber < page.getPageCount()}">
                                                        <li><a href="/store/batch/batch.detail.action?id=<s:property value="%{item.id}"/>&page.pageNumber=<s:property value="%{page.pageNumber + 2}" />">»</a></li>
                                                    </s:if>
                                                </ul>
                                            </div>
                                        </div>
                                    </footer>

                            <%--</section>--%>
                        </section>

                </aside>
            </section>
        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
</section>

<div class="modal fade"  id="addDeviceNew"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;">
        <div class="modal-content"style="max-width: 700px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLabel1">Thêm thiết bị</h5>
            </div>
            <s:form id="filter" method="POST"  action="add.device.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body" >
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Mã thiết bị:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                    <span class="input-group-addon">
                                        <i class="fa fa-fw fa-barcode"></i>
                                    </span>
                                 <s:textfield name="device.id"  placeholder="Mã thiết bị" cssClass="form-control" />
                                 <s:textfield name="batchId" value="%{item.id}" type="hidden" cssClass="form-control" />

                            </div>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Ngày SX:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                     <span class="input-group-addon">
                                        <i class="fa fa-fw fa-calendar"></i>
                                    </span>
                                <div class="input-daterange">
                                <s:textfield id="strDateStartForm" name="strDateStartForm" placeholder="Ngày nhập/sản xuất" theme="simple" cssStyle="text-align:left" cssClass="input-small input-sm input-s-sm form-control "/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Ghi chú:</label>

                        <div class="col-sm-9 input-group m-b">
                                    <span class="input-group-addon">
                                        <i class="fa fa-fw fa-pencil"></i>
                                    </span>
                            <s:textarea name="device.note"  placeholder="Ghi chú" cssClass="form-control" />
                        </div>
                    </div>


                </div>
                <div class="modal-footer" >
                    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-sm btn-primary"><s:property value="getTitleText('fms.button.update')"/></button>
                </div>
            </s:form>
        </div>
    </div>
</div>

<div class="modal fade"  id="addDeviceFromExcel"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;">
        <div class="modal-content"style="max-width: 700px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">Thêm thiết bị từ Excel</h5>
            </div>
            <s:form id="filter" method="POST"  action="add.device.from.excel.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <s:textfield name="batchId" value="%{item.id}" type="hidden" cssClass="form-control" />
                <div class="form-group">
                    <label class="col-sm-3 control-label">Mẫu nhập liệu</label>
                    <div class="col-sm-9 ">
                        <a href="/assets/upload/Batch.device.xls" class="media list-group-item">
                              <span class="pull-left thumb-sm">
                                 <i class="fa fa-file fa-3x"></i>
                              </span>
                              <span class="media-body block m-b-none">
                                Download mẫu nhập liệu
                              </span>
                        </a>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="line line-dashed line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Chọn File</label>
                        <div class="col-sm-9">
                            <input type="file" name="file" class="filestyle" data-icon="false" data-classButton="btn btn-default" data-classInput="form-control inline input-s">
                        </div>
                    </div>
                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-sm btn-primary"><s:property value="getTitleText('fms.button.update')"/></button>
                </div>
            </s:form>
        </div>
    </div>
</div>


<div class="modal fade"  id="editDevice"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;">
        <div class="modal-content"style="max-width: 700px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModal">Sửa thiết bị</h5>
            </div>
            <s:form id="filter" method="POST"  action="edit.device.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Mã thiết bị:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                      <span class="input-group-addon">
                                        <i class="fa fa-fw fa-barcode"></i>
                                    </span>
                                <s:textfield name="abc"  placeholder="Mã thiết bị" disabled="true" cssClass="form-control information_edit_id" />
                                <s:textfield name="device.id"  type="hidden" cssClass="form-control information_edit_id" />
                                <s:textfield name="batchId" value="%{item.id}" type="hidden" cssClass="form-control" />
                            </div>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Ngày nhập/sản xuất:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                    <span class="input-group-addon">
                                        <i class="fa fa-fw fa-calendar"></i>
                                    </span>
                                <div class="input-daterange">
                                    <s:textfield id="strDateStart" name="strDateStart" placeholder="Ngày nhập/sản xuất" cssStyle="text-align:left" theme="simple" cssClass="input-small input-sm input-s-sm form-control information_edit_start"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Ghi chú:</label>
                        <div class="col-sm-9 input-group m-b">
                                    <span class="input-group-addon">
                                        <i class="fa fa-fw fa-pencil"></i>
                                    </span>
                            <s:textarea name="device.note"  placeholder="Ghi chú" cssClass="form-control information_edit_note" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer" >
                    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-sm btn-primary"><s:property value="getTitleText('fms.button.update')"/></button>
                </div>
            </s:form>
        </div>
    </div>
</div>


<div class="modal fade"  id="lockBatch" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 400px;max-height: 200px">
        <div class="modal-content"style="max-width: 400px;max-height: 200px">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLabel">Khóa lô</h5>
            </div>
            <s:form id="filter" method="POST"  action="edit.save.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <h4>Bạn thực sự muốn khóa lô này?</h4>
                    <div class="form-group">
                        <div class="col-sm-9">
                            <s:textfield class="form-control " type="hidden" name="item.id" value="%{item.id}"/>
                            <s:textfield class="form-control " type="hidden" name="lock" value="lock" />
                        </div>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-sm btn-danger"> Khóa </button>
                </div>
            </s:form>
        </div>
    </div>
</div>

<link rel="stylesheet" href="/assets/js/eternicode-bootstrap-datepicker/css/datepicker.css" type="text/css"/>
<script src="/assets/js/eternicode-bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<script src="/assets/js/select2/select2.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('.input-daterange').datepicker({
            format: "dd/mm/yyyy",
            startDate: "01/01/2014",
            endDate: "",
            todayBtn: "linked",
            calendarWeeks: false,
            autoclose: true,
            todayHighlight: true
        });

        $("#filter_companyId").select2();

        $(".editDevice").click(function(){
            var id=$(this).data('device.id');
            var note=$(this).data('device.note');
            var start=$(this).data('device.start');

            $(".information_edit_id").val(id);
            $(".information_edit_note").val(note);
            $(".information_edit_start").val(start);
        });
    });
</script>
