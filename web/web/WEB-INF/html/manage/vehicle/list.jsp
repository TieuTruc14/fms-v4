<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="#"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
                <li><a href="/manage/vehicle/vehicle.list.action"><s:property value="getTitleText('fms.menu.vehicle')" /></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.management.vehicle')" /></h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" /> <s:if test="page.getRowCount()>0">: <code><s:property value="page.getRowCount()" /></code> <s:property value="getTitleText('fms.menu.vehicle')" /></s:if></header>
                <div class="text-sm wrapper bg-light lt">
                    <s:form cssClass="form-inline padder" role="form" theme="simple">
                        <div class="form-group"><s:textfield name="vehicleId" placeholder="%{getTitleText('fms.utility.search.by.vehicle')}" cssClass="input-sm form-control"/></div>
                        <button type="submit" class="btn btn-default btn-sm" formaction="vehicle.list.action"><s:property value="getTitleText('fms.utility.search')"/></button>

                    </s:form>
                </div>
                <div class="table-responsive table-overflow-x-fix">
                    <table id="tblVehicle" class="table table-striped table-bordered m-b-none text-sm display">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col">#</th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.menu.vehicle')"/></th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.menu.transport.type')"/></th>
                            <th class="box-shadow-inner small_col hidden-xs"><s:property value="getTitleText('fms.menu.company')"/></th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.date.active')"/></th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.date.expire')"/></th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:set var="requestDate" value="date"/>
                        <s:iterator var="item" value="page.items" status="stat">
                            <tr>
                                <td></td>
                                <td style="display:none;"><s:property value="#item.id"/></td>
                                <td><a href="/manage/vehicle/vehicle.detail.action?vehicleId=<s:property value="#item.id" />"><s:property value="#item.id"/></a></td>
                                <td><s:property value="#item.typeName" /></td>
                                <td class="hidden-xs"><a href="/manage/company/detail.action?id=<s:property value="#item.company.id" />"><s:property value="#item.company.name"/></a></td>
                                <td><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateStart,'yyyy/MM/dd')" /></td>
                                <td><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateEnd,'yyyy/MM/dd')" /></td>
                                <td>
                                    <sec:authorize access="hasAnyRole('ROLE_VEHICLE_EDIT')">
                                        <div class="btn-group">
                                            <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                            <ul class="dropdown-menu pull-right">
                                                <li><a href="/manage/vehicle/vehicle.edit.action?vehicleId=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
                                                <li> <a class="editVehicleNote "
                                                        data-toggle="modal" data-target="#editVehicleNote"
                                                        data-device.id="<s:property value="#item.id"/>"
                                                        data-device.note="<s:property value="#item.note"/>">
                                                    <i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.report.title.note')" /></a>
                                                </li>
                                            </ul>
                                        </div>
                                    </sec:authorize>
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
                                    <li><a href="/manage/vehicle/vehicle.list.action?page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                </s:if>
                                <s:iterator var="item" value="page.pageList" status="stat">
                                    <li><a href="/manage/vehicle/vehicle.list.action?page.pageNumber=<s:property value="#item" />">
                                        <s:property value="#item" /></a></li>
                                </s:iterator>
                                <s:if test="%{page.pageNumber < page.getPageCount()}">
                                    <li><a href="/manage/vehicle/vehicle.list.action?page.pageNumber=<s:property value="%{page.pageNumber + 2 }" />">»</a></li>
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

<div class="modal fade"  id="editVehicleNote"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;">
        <div class="modal-content"style="max-width: 700px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLable">Ghi chú</h5>
            </div>
            <s:form id="filter" method="POST"  action="note.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Biển số xe:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                      <span class="input-group-addon">
                                        <i class="fa fa-fw fa-barcode"></i>
                                    </span>
                                <s:textfield name="abc"  placeholder="Biển số xe" disabled="true" cssClass="form-control information_edit_id" />
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
                            <s:textarea name="item.note"  placeholder="Ghi chú" cssClass="form-control information_edit_note" />
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

<style>
    td.details-control {
        background: url('/assets/images/details_open.png') no-repeat center center;
        cursor: pointer;
    }
    tr.shown td.details-control {
        background: url('/assets/images/details_close.png') no-repeat center center;
    }
</style>

<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<script src="/assets/js/jquery-1.12.3.js"></script>
<script src="/assets/js/jquery.dataTables.min.js"></script>

<script>
    var tableString ="";
    function format ( d ) {
        $.getJSON("/manage/vehicle/vehicle.device.json.action?vehicleId=" + d[1], function (data) {
            tableString ='<table class="table table-striped b-t b-light table-bordered m-b-none text-sm table-hover"  style="width:100%;">';
            tableString +='<thead>';
            tableString +='<tr>';
            tableString +='<th class="box-shadow-inner small_col text-center" rowspan="2">Ma</th><th class="box-shadow-inner small_col text-center" rowspan="2">Loai</th><th class="box-shadow-inner small_col text-center" >Ngày bắt đầu</th><th class="box-shadow-inner small_col text-center" >Ngày kích hoạt</th><th class="box-shadow-inner small_col text-center">Ngày kết thúc</th>';
            tableString +='</tr>';
            tableString +='<thead>';
            $.each(data, function (key, val) {
                var dateStart=new Date(val[1]);
                var dateActive=new Date(val[2]);
                var dateEnd=new Date(val[3]);
                tableString+='<tr>';
                tableString+='<td class="text-center"><a href="/store/device/activity.action?deviceId='+val[0]+'">'+val[0]+'</a></td>';
                tableString+='<td class="text-center">'+val[4]+'</td>';
                tableString+='<td class="text-center">'+dateStart.toLocaleDateString()+'</td>';
                tableString+='<td class="text-center">'+dateActive.toLocaleDateString()+'</td>';
                tableString+='<td class="text-center">'+dateEnd.toLocaleDateString()+'</td>';
                tableString+='</tr>';
            });
            tableString+='</table>';
        });
        return tableString;
    };

    $(document).ready(function() {
        $.ajaxSetup({
            async: false
        });

        var table = $('#tblVehicle').DataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType":"full_numbers",
            "aoColumns": [
                { "sClass": "details-control",  "orderable":false,"data": null, "defaultContent": '' },
                { "sClass": "text-right", "sType": "num-html" },
                { "sClass": "text-right", "sType": "num-html" },
                { "sClass": "text-right", "sType": "num-html" },
                { "sClass": "text-right", "sType": "num-html" },
                { "sClass": "text-right", "sType": "num-html" },
                { "sClass": "text-right", "sType": "num-html" },
                { "sClass": "text-right", "sType": "num-html" }
            ],
            "order": [[1, 'asc']]
        });

        $('#tblVehicle tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = table.row(tr);

            if ( row.child.isShown() ) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                // Open this row
                row.child( format(row.data()) ).show();
                tr.addClass('shown');
            }
        } );

        $(".editVehicleNote").click(function(){
            var id=$(this).data('device.id');
            var note=$(this).data('device.note');

            $(".information_edit_id").val(id);
            $(".information_edit_note").val(note);
        });
    });
</script>