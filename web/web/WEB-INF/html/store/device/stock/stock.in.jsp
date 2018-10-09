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
                <li class="active"><a href="/store/device/stock.in.action"><s:property value="getTitleText('fms.admin.management.receit.note')" /></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.admin.management.receit.note')" /></h3> </div>
            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.list')" />
                    <div class="text-sm wrapper bg-light lt">
                        <s:form cssClass="form-inline padder" role="form" theme="simple">
                                <div class="form-group">
                                    <s:select theme="simple"
                                              cssClass="selectpicker span2 input-group input-s-sm"
                                              headerKey=""
                                              headerValue="Đối tác"
                                              list="ListCompanyInterceptor_companies"
                                              listKey="id"
                                              listValue="name"
                                              name="companyId"
                                              data-width="auto"
                                              cssStyle="width:290px"/>
                                </div>
                                <button type="submit" class="btn btn-default btn-sm " formaction="stock.in.action"><s:property value="getTitleText('fms.utility.search')"/></button>
                        </s:form>
                    </div>

                </header>

                <div class="table-responsive">
                    <table id="tblReportData" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col">#</th>
                            <th class="box-shadow-inner small_col">Tên phiếu</th>
                            <th class="box-shadow-inner small_col">Nguồn</th>
                            <th class="box-shadow-inner small_col">Người lập</th>
                            <th class="box-shadow-inner small_col">Thời gian gửi</th>
                            <th class="box-shadow-inner small_col">Mô tả</th>
                            <th class="box-shadow-inner small_col">Trạng thái</th>

                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator var="item" value="page.items" status="stat">
                            <tr id="row<s:property value="#stat.count" />">
                                <td><s:property value="#stat.count" /></td>
                                <td><a href="/store/device/stock.in.detail.action?id=<s:property value="#item.id" />"><s:property value="#item.name" /></a></td>
                                <td><s:property value="#item.companySource.name" /></td>
                                <td><s:property value="#item.userCreated" /></td>
                                <td><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateCreated,'dd/MM/yyyy')" /></td>
                                <td><s:property value="#item.note" /></td>
                                <td>
                                    <s:if test="%{#item.status==1}">
                                        Chưa nhận
                                    </s:if>
                                    <s:elseif  test="%{#item.status==2}">
                                        Đã nhận
                                    </s:elseif>
                                    <s:elseif  test="%{#item.status==3}">
                                         Không nhận
                                    </s:elseif>
                                    <s:elseif  test="%{#item.status==4}">
                                         Đã hủy
                                    </s:elseif>
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
                                    <li><a href="/store/device/stock.in.action?page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                </s:if>
                                <s:iterator var="item" value="page.pageList" status="stat">
                                    <li><a href="/store/device/stock.in.action?page.pageNumber=<s:property value="#item" />">
                                        <s:property value="#item" /></a></li>
                                </s:iterator>
                                <s:if test="%{page.pageNumber < page.getPageCount()}">
                                    <li><a href="/store/device/stock.in.action?page.pageNumber=<s:property value="%{page.pageNumber + 2 }" />">»</a></li>
                                </s:if>
                            </ul>
                        </div>
                    </div>
                </footer>
            </section>

        </section>
        <%--<footer class="footer bg-white b-t b-light"></footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<link rel="stylesheet" type="text/css" href="/assets/js/eternicode-bootstrap-datepicker/css/datepicker.css" />
<script src="/assets/js/eternicode-bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<script src="/assets/js/select2/select2.min.js"></script>

<script>
    $(document).ready(function() {
        $("#stock_in_companyId").select2();
        $('.input-daterange').datepicker({
            format: "dd/mm/yyyy",
            startDate: "01-01-2014",
            endDate: "new Date()",
            todayBtn: "linked",
            calendarWeeks: false,
            autoclose: true,
            todayHighlight: true
        });
    });
</script>