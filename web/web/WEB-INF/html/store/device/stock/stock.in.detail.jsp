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
                    <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                    <li class="active"><a href="/store/device/list.action"><s:property value="getTitleText('fms.menu.device')"/></a></li>
                    <li class="active"><a href="/store/device/stock.in.action">Phiếu nhập kho</a></li>
                     <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.report.detail')" /></a></li>
                </ul>
                <div class="m-b-md">
                    <s:if test="%{item.status==1}">
                        <sec:authorize access="hasRole('ROLE_STOCK_IN_VIEW')">
                        <div class="col-xs-6">
                            <h3 class="ReceiveStock btn btn-sm btn-facebook pull-left"  data-toggle="modal" data-target="#ReceiveStock"><i class="fa fa-sign-out pull-left"></i>Nhận phiếu </h3>
                        </div>
                        <div class="col-xs-6">
                            <h3 class="UnReceiveStock btn btn-sm btn-danger pull-right"  data-toggle="modal" data-target="#UnReceiveStock">
                            <i class="fa fa-plus pull-left"></i>Không nhận phiếu</h3>

                        </div>
                        </sec:authorize>
                    </s:if>
                    <s:hidden id="stockId" name="stockId" value="%{item.id}"/>
                </div>
            </header>

            <section class="hbox stretch">
                <aside class="aside-lg b-r">
                    <section class="panel panel-default m-b-none">
                        <header class="panel-heading bg-light no-border">Chi tiết phiếu nhập</header>
                    </section>
                    <div class="wrapper">
                        <div>
                            <p><small class="text-uc text-xs text-muted"> <s:property value="getTitleText('fms.admin.management.stock.name')" />: </small><s:property value="item.name" /></p>
                            <s:if test="%{item.status}">
                                <p><small class="text-uc text-xs text-muted">Công ty xuất: </small>
                                        <s:if test="%{item.companySource != null}"><s:property value="item.companySource.name" /></s:if>
                                <p><small class="text-uc text-xs text-muted">Công ty nhận: </small>
                                    <s:if test="%{item.companyDes != null}"><s:property value="item.companyDes.name" /></s:if><s:else><s:property value="getTitleText('fms.title.updating')"/></s:else></p>
                            </s:if>
                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.status')" />:</small>
                            <s:if test="%{item.status==0}">
                                <p>Chưa xuất</p>
                            </s:if>
                            <s:elseif  test="%{item.status==1}">
                                <p>Đã xuất-Chưa nhận</p>
                            </s:elseif>
                            <s:elseif  test="%{item.status==2}">
                                <p>Đã nhận</p>
                            </s:elseif>
                            <s:elseif  test="%{item.status==3}">
                                <p>Không nhận phiếu</p>
                            </s:elseif>
                            <s:elseif  test="%{item.status==4}">
                                <p>Đã hủy</p>
                            </s:elseif>
                        </div>
                        <div class="line"></div>
                        <s:if test="%{item.status==3}">
                                <p class="deleteStock btn btn-sm btn-danger pull-left"  data-toggle="modal" data-target="#deleteStock"><i class="fa fa-sign-out pull-left"></i>Hủy phiếu </p>
                        </s:if>
                    </div>
                </aside>
                <aside class="bg-light lt b-r">

                    <section class="panel panel-default m-b-none">
                        <header class="panel-heading bg-light no-border">Thiết bị trong phiếu</header>
                    </section>
                    <div class="wrapper">
                        <section class="panel panel-default">
                            <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> Danh sách <s:property value="#mapItem.productType.name"/> <code > <s:property value="#mapItem.deviceList.size" /></code>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                            </header>
                            <section class="panel-body" data-height="230px">
                                <div class="table-responsive">
                                    <table  class="table table-striped b-t b-light table-bordered m-b-none text-sm table-hover"  >
                                        <thead>
                                        <tr>
                                            <th  class="box-shadow-inner small_col text-center">#</th>
                                            <th class="box-shadow-inner small_col text-center">Mã</th>
                                            <th  class="box-shadow-inner small_col text-center">Model</th>
                                            <th  class="box-shadow-inner small_col text-center">Lô</th>
                                            <th  class="box-shadow-inner small_col text-center">Ngày SX</th>

                                        </tr>

                                        </thead>
                                        <tbody>
                                        <s:iterator var="mapItem" value="lstMap" status="stat">
                                            <tr class="group text-">
                                                <td colspan="5" ><strong><s:property value="#mapItem.productType.name"/></strong> <code > <s:property value="#mapItem.deviceList.size" /></code></td>
                                            </tr>
                                            <s:iterator var="item" value="#mapItem.deviceList" status="stat">
                                                <tr>
                                                    <td class=" text-center"><s:property value="#stat.count" /></td>
                                                    <td class=" text-center"><s:property value="#item.id" /></td>
                                                    <td class=" text-center"><s:property value="#item.batch.model.id" /></td>
                                                    <td class=" text-center"><s:property value="#item.batch.id" /></td>
                                                    <td class=" text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateStart,'yyyy/MM/dd')" /></td>

                                                </tr>
                                            </s:iterator>
                                        </s:iterator>
                                        </tbody>
                                    </table>
                                </div>
                            </section>
                        </section>

                    </div>
                </aside>

            </section>
        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
</section>
<div class="modal fade"  id="deleteStock" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 500px;max-height: 300px">
        <div class="modal-content"style="max-width: 500px;max-height: 300px">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLabe1l">Hủy phiếu</h5>
            </div>
            <s:form id="filter" method="POST"  action="stock.delete.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <h4>Bạn xác nhận hủy bỏ phiếu này?</h4>
                    <div class="form-group">
                        <div class="col-sm-9">
                            <s:textfield class="form-control " type="hidden" name="id" />
                        </div>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-sm btn-danger"><s:property value="getTitleText('fms.button.delete')"/></button>
                </div>
            </s:form>
        </div>
    </div>
</div>

<div class="modal fade"  id="ReceiveStock" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 500px;max-height: 300px">
        <div class="modal-content"style="max-width: 500px;max-height: 300px">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLabel">Xác nhận phiếu!</h5>
            </div>
            <s:form id="filter" method="POST"  action="stock.in.receive.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <h4 class="text-center">Việc xác nhận phiếu tức các thiết bị bàn giao trong phiếu đã nhận đủ!</h4>
                    <div class="form-group">
                        <div class="col-sm-9">
                            <s:textfield class="form-control " type="hidden" name="id" />
                        </div>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-sm btn-facebook">Xác nhận</button>
                </div>
            </s:form>
        </div>
    </div>
</div>

<div class="modal fade"  id="UnReceiveStock" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 500px;max-height: 300px">
        <div class="modal-content"style="max-width: 500px;max-height: 300px">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLabel21">Không nhận phiếu!</h5>
            </div>
            <s:form id="filter" method="POST"  action="stock.in.unreceive.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="col-sm-3 control-label pull-left">Lý do:</label>
                        <div class="col-sm-9">
                            <s:textarea name="note"  placeholder="Lý do không nhận" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-9">
                            <s:textfield class="form-control " type="hidden" name="id" />
                        </div>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-sm btn-primary">Hủy phiếu</button>
                </div>
            </s:form>
        </div>
    </div>
</div>

<div class="modal fade"  id="confirmStockOutPartner"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;max-height: 300px">
        <div class="modal-content"style="max-width: 700px;max-height: 300px">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLabel1">Chọn đối tác</h5>
            </div>
            <s:form id="filter" method="POST"  action="stock.out.confirm.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">Mã:</label>
                        <div class="col-sm-8">
                            <s:textfield name="companyCode"  placeholder="Mã đối tác" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">---Hoặc---</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">Tên:</label>
                        <div class="col-sm-8">
                            <s:select theme="simple"
                                      cssClass="selectpicker span2 input-group"
                                      headerKey=""
                                      headerValue="%{getTitleText(manage)}"
                                      list="ListCompanyInterceptor_companies"
                                      listKey="id"
                                      listValue="name"
                                      name="companyId"
                                      data-width="auto"
                                      cssStyle="width:290px"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-9">
                            <input class="form-control skill_stockId" type="hidden" name="stockId">
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


<div class="modal fade"  id="AddDeviceStockOut"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 800px;">
        <div class="modal-content"style="max-width: 800px;max-height: 500px">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalg">Chọn thiết bị</h5>
            </div>
            <s:form id="filter" method="POST"  action="stock.out.add.device.save.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="table-responsive" style="width:100%!important;">
                    <s:textfield name="id" value="%{item.id}" type="hidden" cssClass="form-control" />
                    <table  class="table table-striped b-t b-light tablle100" style="width:100%!important;" id="datatables">
                        <thead>
                        <tr>
                            <th > #</th>
                            <th >
                                Chọn
                            </th>
                            <th><s:property value="getTitleText('fms.menu.device.code')"/></th>
                            <th class="text-center" >Loại hàng hóa</th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator var="item" value="deviceList" status="stat">
                            <s:set var="idx" value="#stat.count - 1" />
                            <input hidden="true" name="devices[<s:property value='#idx'/>].id" value="<s:property value="#item.id"/>">
                            <tr>
                                <td><s:property value="#stat.count" /></td>
                                <td class="text-center"><label  class="checkbox-inline">
                                    <input style="width:20px;height:17px;margin-top:-7px;" type="checkbox" name="devices[<s:property value="#idx"/>].checkbox">
                                    <i></i></label>
                                </td>
                                <td class="text-center"><label class=""><s:property value="#item.id"/></label></td>
                                <td class="text-center"><label class=""><s:property value="#item.batch.model.productType.name"/></label></td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer" style="padding: 10px;" >
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
            <s:form id="filter" method="POST"  action="stock.out.add.device.from.excel.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <s:textfield name="id" value="%{item.id}" type="hidden" cssClass="form-control" />
                <div class="form-group">
                    <label class="col-sm-3 control-label">Mẫu nhập liệu</label>
                    <div class="col-sm-9 input-group m-b">
                        <a href="/assets/upload/stock.out.device.xls" class="media list-group-item">
                              <span class="pull-left thumb-sm">
                                 <i class="fa fa-file fa-3x"></i>
                              </span>
                              <span class="media-body block m-b-none">
                                Download mẫu nhập liệu
                              </span>
                        </a>
                    </div>
                </div>
                <div class="modal-body"  style="padding: 10px;">
                    <div class="line line-dashed line-lg pull-in"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Chọn File</label>
                        <div class="col-sm-10">
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


<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<script src="/assets/js/select2/select2.min.js"></script>


<script type="text/javascript">
    $(document).ready(function () {
        var itemId= $('#stockId').val();
        $(".deleteStockDetail").click(function(){
            var idDevice = $(this).data('device.id');
            $(".skill_id").val(idDevice);
            $(".deviceIdDelete").text(idDevice);

        });

        $("#filter_companyId").select2();
        $(".confirmStockOutPartner").click(function(e){
            $(".skill_stockId").val(itemId);
        });

        $('#datatables').each(function() {
            var oTable = $(this).dataTable( {
                "bSort": false,
                "bProcessing": false,
                "iDisplayLength": -1,
                "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
                "sPaginationType": "full_numbers",
                "sScrollY": 240,
                "bPaginate": false

            } );
        });
        $('.dataTables_scrollHeadInner').css('width','100%');
        $('.tablle100').css('width','100%');
        $('.dataTables_filter').css('padding','2px');
        $('.table').css('margin-bottom','-2px');
    });
</script>
