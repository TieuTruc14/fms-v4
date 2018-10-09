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
                <li><a href="/store/batch/list.action">lô sản phẩm</a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none">Lô sản phẩm</h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>&nbsp;<s:property value="getTitleText('fms.header.data')" /> <s:if test="page.getRowCount()>0">: <code><s:property value="page.getRowCount()" /></code> <s:property value="getTitleText('fms.menu.batch')" /></s:if>
                    <div class="text-sm wrapper bg-light lt">
                        <s:form cssClass="form-inline padder" role="form" theme="simple">
                            <div class="form-group"><s:textfield name="code" placeholder="%{getTitleText('fms.utility.search.by.code')}" cssClass="input-sm form-control"/></div>
                            <div class="form-group"><s:textfield name="name" placeholder="%{getTitleText('fms.utility.search.by.name')}" cssClass="input-sm form-control"/></div>
                            <button type="submit" class="btn btn-default btn-sm" formaction="list.action"><s:property value="getTitleText('fms.utility.search')"/></button>
                            <sec:authorize access="hasAnyRole('ROLE_BATCH_ADD')">
                            <a class="btn btn-sm btn-primary pull-right" href="/store/batch/addnew.action"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
                            </sec:authorize>
                        </s:form>
                    </div>
                </header>
                <div class="table-responsive">
                    <table id="tblCompany" class="table table-striped table-bordered m-b-none text-sm table-hover">
                        <thead>
                            <tr>
                                <th class="box-shadow-inner small_col text-center">#</th>
                                <th class="box-shadow-inner small_col text-center">Mã</th>
                                <th class="box-shadow-inner small_col text-center">Tên</th>
                                <th class="box-shadow-inner small_col text-center">Số lượng</th>
                                <th class="box-shadow-inner small_col text-center">Model</th>
                                <th class="box-shadow-inner small_col text-center">Ngày bắt đầu</th>
                                <th class="box-shadow-inner small_col text-center">Ngày hết hạn</th>
                                <th class="box-shadow-inner small_col text-center">Mô tả</th>
                                <th class="box-shadow-inner small_col text-center">Trạng thái</th>
                                <th class="box-shadow-inner small_col text-center "><s:property value="getTitleText('fms.report.title.functional')"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        <s:set var="requestDate" value="date"/>
                        <s:iterator var="item" value="page.items" status="stat">
                            <tr>
                                <td class="text-left" ><s:property value="#stat.count" /></td>
                                <td class="text-left"><a href="/store/batch/batch.detail.action?id=<s:property value="#item.id" />"><s:property value="#item.id"/></a></td>
                                <td class="text-left"><a href="/store/batch/batch.detail.action?id=<s:property value="#item.id" />"><s:property value="#item.name"/></a></td>
                                <td class="text-left"><a href="/store/batch/batch.detail.action?id=<s:property value="#item.id" />"><s:property value="#item.deviceCount"/></a></td>
                                <td class="text-left"><a href="/catalog/model.list.action"><s:property value="#item.model.name"/></a></td>
                                <td class="text-left"><a href="/store/batch/batch.detail.action?id=<s:property value="#item.id" />"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateStart,'dd/MM/yyyy')" /></a></td>
                                <td class="text-left"><a href="/store/batch/batch.detail.action?id=<s:property value="#item.id" />"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateEnd,'dd/MM/yyyy')" /></a></td>
                                <td class="text-left"><a href="/store/batch/batch.detail.action?id=<s:property value="#item.id" />"><s:property value="#item.description"/></a></td>
                                <td class="text-left">
                                    <s:if test="#item.locked">Đã khóa</s:if><s:else>Chưa khóa</s:else>
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                        <ul class="dropdown-menu pull-right">
                                            <sec:authorize access="hasAnyRole('ROLE_BATCH_VIEW')">
                                            <li><a href="/store/batch/batch.detail.action?id=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.menu.report.detail')" /></a></li>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_BATCH_EDIT')">
                                            <li><a href="/store/batch/edit.action?id=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
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
                        <div class="col-sm-4 hidden-xs"></div>
                        <div class="col-sm-4 text-center"><small class="text-muted inline m-t-sm m-b-sm"></small></div>
                        <div class="col-sm-12 text-right text-center-xs">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <s:if test="%{page.pageNumber > 1}">
                                    <li><a href="/store/batch/list.action?page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                                </s:if>
                                <s:iterator var="item" value="page.pageList" status="stat">
                                    <li><a href="/store/batch/list.action?page.pageNumber=<s:property value="#item" />"><s:property value="#item"/></a></li>
                                </s:iterator>
                                <s:if test="%{page.pageNumber < page.getPageCount()}">
                                    <li><a href="/store/batch/list.action?page.pageNumber=<s:property value="%{page.pageNumber + 2}" />">»</a></li>
                                </s:if>
                            </ul>
                        </div>
                    </div>
                </footer>
            </section>

        </section>
        <%--<footer class="footer bg-white b-t b-light"></footer>--%>
    </section>
</section>

<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<script>
    $(document).ready(function() {

        $('#tblCompany').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType":"full_numbers"
        });
    });
</script>
