<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
                <li><a href="/store/device/list.action"><s:property value="getTitleText('fms.menu.device')" /></a></li></ul>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.management.device')" /></h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" /> <s:if test="page.getRowCount()>0">: <code><s:property value="page.getRowCount()" /></code> <s:property value="getTitleText('fms.menu.device')" /></s:if>
                    <div class="text-sm wrapper bg-light lt">
                        <s:form cssClass="form-inline padder" role="form" theme="simple">
                            <div class="form-group">
                                <s:textfield name="id" placeholder="%{getTitleText('fms.utility.search.by.code')}" cssClass="input-sm form-control"/>

                            </div>
                            <div class="form-group">
                                <s:select key="type"
                                          label="Trạng thái thiết bị" inputPrepend=""
                                          required="true"
                                          placeholder="Trạng thái thiết bị"
                                          list="#{'0':'Tất cả','1':'Đã lắp đặt','2':'Chưa lắp đặt','3':'Kho của tôi'}"
                                          value="type"
                                          data-width="auto"
                                          cssStyle="width:160px;">
                                </s:select>
                            </div>
                            <div class="form-group">
                                <s:select theme="simple"
                                          cssClass="selectpicker span2"
                                          label="Chọn loại hàng hóa"
                                          list="ListProductTypeInterceptor_models"
                                          listKey="id"
                                          listValue="name"
                                          name="productTypeId"
                                          headerKey=""
                                          headerValue="Chọn loại hàng hóa"
                                          data-width="auto"
                                          cssStyle="width:250px;height: 30px;margin-top:4px;"
                                        />
                                <s:fielderror cssStyle="color: red" fieldName="item.parent.id"/>
                            </div>
                            <button type="submit" class="btn btn-default btn-sm" formaction="list.action"><s:property value="getTitleText('fms.utility.search')"/></button>

                            <%--<sec:authorize access="hasAnyRole('ROLE_ADMIN')">--%>
                                <%--<a class="btn btn-sm btn-primary pull-right" href="/store/device/addnew.action"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>--%>
                            <%--</sec:authorize>--%>
                        </s:form>
                    </div>
                </header>
                <div class="table-responsive">
                    <table id="tblDevice" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col">#</th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.code')"/> thiết bị</th>
                            <th class="box-shadow-inner small_col">Mã sản phẩm</th>
                            <th class="box-shadow-inner small_col">Loại hàng hóa</th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.utility.date.of.production')"/></th>
                            <th class="box-shadow-inner small_col">Ngày kích hoạt</th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.date.expire')"/></th>
                            <th class="box-shadow-inner small_col hidden-xs"><s:property value="getTitleText('fms.menu.owner')"/></th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.status')"/></th>
                            <th class="box-shadow-inner small_col">Xe</th>
                            <th class="box-shadow-inner small_col">Ghi chú</th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                        </tr>
                        </thead>
                        <tbody>

                        <s:set var="requestDate" value="date"/>
                        <s:iterator var="item" value="page.items" status="stat">
                            <tr id="row<s:property value="#stat.count" />">
                                <td><s:property value="#stat.count" /></td>
                                <td><a href="/store/device/activity.action?deviceId=<s:property value="#item.id" />"><s:property value="#item.id" /></a></td>
                                <td><s:property value="#item.product_key" /></td>
                                <td><a href="/catalog/product.list.action"><s:property value="#item.batch.model.productType.name" /></a></td>
                                <td><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateStart,'dd/MM/yyyy')" /></td>
                                <td><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateActive,'dd/MM/yyyy')" /></td>
                                <td><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateEnd,'dd/MM/yyyy')" /></td>
                                <td class="hidden-xs"><a href="/manage/company/detail.action?id=<s:property value="#item.company.id" />"><s:property value="#item.company.name" /></a></td>
                                <td><s:property value="#item.unitName" /></td>
                                <td><a href="/manage/vehicle/vehicle.detail.action?vehicleId=<s:property value="#item.vehicle.id" />"><s:property value="#item.vehicle.id" /></a></td>
                                <td><s:property value="#item.description" /></td>
                                <td>
                                    <div class="btn-group">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                        <ul class="dropdown-menu pull-right">
                                            <sec:authorize access="hasRole('ROLE_DEVICE_EDIT')">
                                            <li> <a class="editDevice "
                                                    data-toggle="modal" data-target="#editDevice"
                                                    data-device.id="<s:property value="#item.id"/>"
                                                    data-device.note="<s:property value="#item.description"/>"
                                                    data-device.active="<s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateActive,'dd/MM/yyyy')" />"
                                                    data-device.end="<s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateEnd,'dd/MM/yyyy')" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a>
                                            </li>
                                            </sec:authorize>
                                            <sec:authorize access="hasRole('ROLE_DEVICE_NOTE')">
                                            <li> <a class="editDeviceNote "
                                                    data-toggle="modal" data-target="#editDeviceNote"
                                                    data-device.id="<s:property value="#item.id"/>"
                                                    data-device.note="<s:property value="#item.description"/>">
                                                <i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.report.title.note')" /></a>
                                            </li>
                                            </sec:authorize>
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
                        <div class="col-sm-4 hidden-xs">
                        </div>
                        <div class="col-sm-4 text-center">
                            <small class="text-muted inline m-t-sm m-b-sm"></small>
                        </div>
                        <div class="col-sm-12 text-right text-center-xs">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <s:if test="%{page.pageNumber > 1}">
                                    <li><a href="/store/device/list.action?page.pageNumber=<s:property value="%{page.pageNumber}" />&type=<s:property value="type"/>&id=<s:property value="id"/>&productTypeId=<s:property value="productTypeId"/> ">«</a></li>
                                </s:if>
                                <s:iterator var="item" value="page.pageList" status="stat">
                                    <li><a href="/store/device/list.action?page.pageNumber=<s:property value="#item" />&type=<s:property value="type"/>&id=<s:property value="id"/>&productTypeId=<s:property value="productTypeId"/>">
                                        <s:property value="#item" /></a></li>
                                </s:iterator>
                                <s:if test="%{page.pageNumber < page.getPageCount()}">
                                    <li><a href="/store/device/list.action?page.pageNumber=<s:property value="%{page.pageNumber + 2 }" />&type=<s:property value="type"/>&id=<s:property value="id"/>&productTypeId=<s:property value="productTypeId"/>">»</a></li>
                                </s:if>
                            </ul>
                        </div>
                    </div>
                </footer>
            </section>

        </section>
        <%--<footer class="footer bg-white b-t b-light">--%>
        <%--</footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<div class="modal fade"  id="editDevice"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;">
        <div class="modal-content"style="max-width: 700px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModal">Sửa thiết bị</h5>
            </div>
            <s:form id="filter" method="POST"  action="edit.save.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Mã thiết bị:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                      <span class="input-group-addon">
                                        <i class="fa fa-fw fa-barcode"></i>
                                    </span>
                                <s:textfield name="abc"  placeholder="Mã thiết bị" disabled="true" cssClass="form-control information_edit_id" />
                                <s:textfield name="item.id"  type="hidden" cssClass="form-control information_edit_id" />
                            </div>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Ngày kích hoạt:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                    <span class="input-group-addon">
                                        <i class="fa fa-fw fa-calendar"></i>
                                    </span>
                                <div class="input-daterange">
                                    <s:textfield id="strDateActive" name="strDateActive" placeholder="Ngày kích hoạt" cssStyle="text-align:left" theme="simple" cssClass="input-small input-sm input-s-sm form-control information_edit_active"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Ngày hết hạn:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                    <span class="input-group-addon">
                                        <i class="fa fa-fw fa-calendar"></i>
                                    </span>
                                <div class="input-daterange">
                                    <s:textfield id="strDateEnd" name="strDateEnd" placeholder="Ngày hết hạn" cssStyle="text-align:left" theme="simple" cssClass="input-small input-sm input-s-sm form-control information_edit_end"/>
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
                            <s:textarea name="item.description"  placeholder="Ghi chú" cssClass="form-control information_edit_note" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-primary"><s:property value="getTitleText('fms.button.update')"/></button>
                </div>
            </s:form>
        </div>
    </div>
</div>

<div class="modal fade"  id="editDeviceNote"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;">
        <div class="modal-content"style="max-width: 700px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLable">Ghi chú</h5>
            </div>
            <s:form id="filter" method="POST"  action="note.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Mã thiết bị:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                      <span class="input-group-addon">
                                        <i class="fa fa-fw fa-barcode"></i>
                                    </span>
                                <s:textfield name="abc"  placeholder="Mã thiết bị" disabled="true" cssClass="form-control information_edit_id" />
                                <s:textfield name="item.id"  type="hidden" cssClass="form-control information_edit_id" />
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
                            <s:textarea name="item.description"  placeholder="Ghi chú" cssClass="form-control information_edit_note" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-danger"><s:property value="getTitleText('fms.button.update')"/></button>
                </div>
            </s:form>
        </div>
    </div>
</div>


<link rel="stylesheet" href="/assets/js/eternicode-bootstrap-datepicker/css/datepicker.css" type="text/css"/>
<script src="/assets/js/eternicode-bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<script src="/assets/js/select2/select2.min.js"></script>

<script>
    $(document).ready(function() {
        $('.input-daterange').datepicker({
            format: "dd/mm/yyyy",
            startDate: "01/01/2014",
            endDate: "",
            todayBtn: "linked",
            calendarWeeks: false,
            autoclose: true,
            todayHighlight: true
        });

         $("#list_companyId").select2();
         $("#list_type").select2();
         $("#list_productTypeId").select2();

        $(".editDevice").click(function(){
            var id=$(this).data('device.id');
            var note=$(this).data('device.note');
            var active=$(this).data('device.active');
            var end=$(this).data('device.end');

            $(".information_edit_id").val(id);
            $(".information_edit_note").val(note);
            $(".information_edit_active").val(active);
            $(".information_edit_end").val(end);
        });
        $(".editDeviceNote").click(function(){
            var id=$(this).data('device.id');
            var note=$(this).data('device.note');

            $(".information_edit_id").val(id);
            $(".information_edit_note").val(note);
        });

        $('#tblDevice').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType":"full_numbers"
        });
    });



</script>