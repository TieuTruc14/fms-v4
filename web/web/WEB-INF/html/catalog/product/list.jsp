<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
  <section class="vbox">
<sec:authorize access="hasAnyRole('ROLE_PRODUCT_TYPE_VIEW')">
    <section class="scrollable padder">
      <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
        <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
        <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.catalog')" /></a></li>
        <li><a href="/catalog/model.list.action">Hàng hóa</a></li>
      </ul>
      <div class="m-b-md"> <h3 class="m-b-none">Hàng hóa</h3> </div>

      <section class="panel panel-default">
        <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" />
          <div class="btn-group pull-right">
            <sec:authorize access="hasAnyRole('ROLE_PRODUCT_TYPE_ADD')">
              <a class=" btn-sm btn-primary pull-right" href="/catalog/product.addnew.action"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
            </sec:authorize>
          </div>
        </header>
        <div class="table-responsive">
          <table id="tblModel" class="table table-striped table-bordered m-b-none text-sm">
            <thead>
            <tr>
              <th class="box-shadow-inner small_col">#</th>
              <th class="box-shadow-inner small_col">Mã </th>
              <th class="box-shadow-inner small_col">Tên</th>
              <th class="box-shadow-inner small_col">Nơi sản xuất</th>
              <th class="box-shadow-inner small_col">Thương hiệu</th>
              <th class="box-shadow-inner small_col">Ngành hàng</th>
              <th class="box-shadow-inner small_col">Nhóm hàng</th>
              <th class="box-shadow-inner small_col">Loại</th>
              <th class="box-shadow-inner small_col">Ghi chú</th>
              <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.functional')"/></th>
            </tr>
            </thead>
            <tbody>

            <s:set var="requestDate" value="date"/>
            <s:iterator var="item" value="page.items" status="stat">
              <tr>
                <td><s:property value="#stat.count" /></td>
                <td><s:property value="#item.id" /></td>
                <td><s:property value="#item.name" /></td>
                <td><s:property value="#item.location" /></td>
                <td><s:property value="#item.brand" /></td>
                <td><s:property value="#item.branch" /></td>
                <td><s:property value="#item.groupId" /></td>
                <s:if test="#item.type==0">
                  <td>GSHT</td>
                </s:if><s:elseif test="#item.type==1">
                  <td>Sim</td>
              </s:elseif><s:else>
                <td>Loại khác</td>
              </s:else>
                <td><s:property value="#item.note" /></td>
                <td>
                  <div class="btn-group">
                    <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                    <sec:authorize access="hasAnyRole('ROLE_PRODUCT_TYPE_EDIT')">
                    <ul class="dropdown-menu pull-right">
                      <li><a href="/catalog/product.edit.action?id=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
                    </ul>
                    </sec:authorize>
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
                  <li><a href="/catalog/product.list.action?page.pageNumber=<s:property value="%{page.pageNumber}" />">«</a></li>
                </s:if>
                <s:iterator var="item" value="page.pageList" status="stat">
                  <li><a href="/catalog/product.list.action?page.pageNumber=<s:property value="#item" />">
                    <s:property value="#item" /></a></li>
                </s:iterator>
                <s:if test="%{page.pageNumber < page.getPageCount()}">
                  <li><a href="/catalog/product.list.action?page.pageNumber=<s:property value="%{page.pageNumber + 2 }" />">»</a></li>
                </s:if>
              </ul>
            </div>
          </div>
        </footer>
      </section>

    </section>
</sec:authorize>
    <%--<footer class="footer bg-white b-t b-light">--%>
    <%--</footer>--%>
  </section>
  <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<script>
  $(document).ready(function() {

    $('#tblModel').dataTable({
      "bFilter": false,
      "bPaginate": false,
      "bAutoWidth": false,
      "sPaginationType":"full_numbers"
    });
  });
</script>