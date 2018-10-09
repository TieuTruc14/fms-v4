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
                <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                <li class="active"><a href="/store/device/list.action"><s:property value="getTitleText('fms.menu.device')"/></a></li>
                <li class="active"><a href="/store/device/stock.out.action"><s:property value="getTitleText('fms.admin.management.delivery.note')" /></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.admin.management.delivery.note')" /></h3> </div>

            <section class="panel panel-default">

                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.list')" /> <s:property value="getTitleText('fms.admin.management.delivery.note')" /> <s:if test="page.getRowCount()>0">: <code><s:property value="page.getRowCount()" /></code> </s:if>
                    <div class="text-sm wrapper bg-light lt">
                        <sec:authorize access="hasAnyRole('ROLE_STOCK_OUT_ADD')">
                            <a><button class="addStockOut btn btn-sm btn-primary pull-right" style="margin-top:-25px;"  data-toggle="modal" data-target="#addStockOut"><i class="fa fa-plus"></i><s:property value="getTitleText('fms.button.addnew')" /></button></a>
                        </sec:authorize>
                        <s:form cssClass="form-inline padder" role="form" theme="simple">
                            <div class="input-daterange">
                                <s:textfield key="start" placeholder="%{getTitleText('fms.utility.date')}" theme="simple" cssClass="input-small input-sm input-s-sm form-control inline"/>
                                <span class="add-on">-Tới-</span>
                                <s:textfield key="end" placeholder="%{getTitleText('fms.utility.date')}" theme="simple" cssClass="input-small input-sm input-s-sm form-control inline"/>
                                    <button type="submit" class="btn btn-default btn-sm" formaction="stock.out.action"><s:property value="getTitleText('fms.utility.search')"/></button>
                            </div>

                        </s:form>
                    </div>

                </header>

                <div class="table-responsive">
                    <table id="tblReportData" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col">#</th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.admin.management.stock.name')" /> </th>
                            <th class="box-shadow-inner small_col">Đối tác</th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.admin.management.stock.created.date')" /></th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.admin.management.stock.created.person')" /></th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.status')"/></th>
                            <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator var="item" value="page.items" status="stat">
                            <tr id="row<s:property value="#stat.count" />">
                                <td><s:property value="#stat.count" /></td>
                                <td><a href="/store/device/stock.out.detail.action?id=<s:property value="#item.id" />"><s:property value="#item.name" /></a></td>
                                <td><a href="/manage/company/detail.action?id=<s:property value="#item.companyDes.id" />"><s:property value="#item.companyDes.name" /></a></td>
                                <td class="text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateCreated,'dd/MM/yyyy')"/></td>
                                <td><s:property value="#item.userCreated" /></td>
                                <s:if test="%{#item.status==0}">
                                    <td>Chưa xuất</td>
                                </s:if>
                                <s:elseif  test="%{#item.status==1}">
                                    <td>Đã xuất- Chưa nhận</td>
                                </s:elseif>
                                <s:elseif  test="%{#item.status==2}">
                                    <td>Đã nhận</td>
                                </s:elseif>
                                <s:elseif  test="%{#item.status==3}">
                                    <td>Không nhận</td>
                                </s:elseif>
                                <s:else>
                                    <td>Đã hủy</td>
                                </s:else>

                                <td>
                                    <div class="btn-group">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                        <ul class="dropdown-menu pull-right">
                                            <sec:authorize access="hasAnyRole('ROLE_STOCK_OUT_VIEW')">
                                            <li><a href="/store/device/stock.out.detail.action?id=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i><s:property value="getTitleText('fms.menu.report.detail')"/></a></li>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_STOCK_OUT_EDIT')">
                                                <s:if test="%{#item.status==0}">
                                                    <li> <a class="editStockOut "
                                                            data-toggle="modal" data-target="#editStockOut"
                                                            data-stockout.id="<s:property value="#item.id"/>"
                                                            data-stockout.name="<s:property value="#item.name"/>"
                                                            data-stockout.note="<s:property value="#item.note"/>"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a>
                                                    </li>
                                                </s:if>
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
                                    <li><a href="/store/device/stock.out.action?page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                </s:if>
                                <s:iterator var="item" value="page.pageList" status="stat">
                                    <li><a href="/store/device/stock.out.action?page.pageNumber=<s:property value="#item" />">
                                        <s:property value="#item" /></a></li>
                                </s:iterator>
                                <s:if test="%{page.pageNumber < page.getPageCount()}">
                                    <li><a href="/store/device/stock.out.action?page.pageNumber=<s:property value="%{page.pageNumber + 2 }" />">»</a></li>
                                </s:if>
                            </ul>
                        </div>
                    </div>
                </footer>
                <%--</s:form>--%>
            </section>

        </section>
        <%--<footer class="footer bg-white b-t b-light"></footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>
<sec:authorize access="hasAnyRole('ROLE_STOCK_OUT_ADD')">
<div class="modal fade"  id="addStockOut"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;">
        <div class="modal-content"style="max-width: 700px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLable">Ghi chú</h5>
            </div>
            <s:form id="filter" method="POST"  action="stock.out.add.save.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Tên phiếu:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                      <span class="input-group-addon">
                                        <i class="fa fa-fw fa-barcode"></i>
                                    </span>
                                <s:textfield name="item.name"  placeholder="Tên phiếu"  cssClass="form-control" />
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
                            <s:textarea name="item.note"  placeholder="Ghi chú" cssClass="form-control" />
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
</sec:authorize>

<sec:authorize access="hasAnyRole('ROLE_STOCK_OUT_EDIT')">
<div class="modal fade"  id="editStockOut"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;">
        <div class="modal-content"style="max-width: 700px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModal">Ghi chú</h5>
            </div>
            <s:form id="filter" method="POST"  action="stock.out.edit.save.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Tên phiếu:</label>
                        <div class="col-sm-9">
                            <div class="input-group m-b">
                                      <span class="input-group-addon">
                                        <i class="fa fa-fw fa-barcode"></i>
                                    </span>
                                <s:textfield name="item.name"  placeholder="Tên phiếu"  cssClass="form-control information_edit_name" />
                                <s:textfield name="item.id"  placeholder="Tên phiếu" type="hidden" cssClass="form-control information_edit_id" />
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
                    <button type="submit" class="btn btn-primary"><s:property value="getTitleText('fms.button.update')"/></button>
                </div>
            </s:form>
        </div>
    </div>
</div>
</sec:authorize>

<link rel="stylesheet" type="text/css" href="/assets/js/eternicode-bootstrap-datepicker/css/datepicker.css" />
<script src="/assets/js/eternicode-bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<script src="/assets/js/select2/select2.min.js"></script>

<script>
    $(document).ready(function() {
        $("#stock_out_companyId").select2();
        $('.input-daterange').datepicker({
            format: "dd/mm/yyyy",
            startDate: "01-01-2014",
            endDate: "new Date()",
            todayBtn: "linked",
            calendarWeeks: false,
            autoclose: true,
            todayHighlight: true
        });

        $(".editStockOut").click(function(){
            var id=$(this).data('stockout.id');
            var note=$(this).data('stockout.note');
            var name=$(this).data('stockout.name');

            $(".information_edit_id").val(id);
            $(".information_edit_note").val(note);
            $(".information_edit_name").val(name);
        });
    });
</script>