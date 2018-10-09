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
        <li class="active"><a href="/index.action"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
        <li class="active"><a href="/utility/district/list.action"><s:property value="getTitleText('fms.menu.provinces')" /></a></li>
      </ul>
      <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.provinces')" /></h3> </div>

      <section class="panel panel-default">
        <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" /></header>
        <div class="table-responsive table-overflow-x-fix">
          <table id="tblDistrict" class="table table-striped table-bordered m-b-none text-sm">
            <thead>
            <tr>
              <th class="box-shadow-inner small_col text-center">#</th>
              <th class="box-shadow-inner small_col text-center">Tên</th>
              <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.company.count')"/></th>
              <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.vehicle.count')"/></th>
              <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.functional')"/></th>
            </tr>
            </thead>
            <tbody>

            <s:iterator var="item" value="items" status="stat">
              <tr>
                <td class="text-right"><s:property value="#stat.count"/></td>
                <td><a href="/utility/district/detail.action?id=<s:property value="#item.id"/>"><s:property value="#item.fullName"/></a></td>
                <td class="text-right"><s:property value="#item.companyCount"/></td>
                <td class="text-right"><s:property value="#item.vehicleCount"/></td>
                <td>
                  <div class="btn-group">
                    <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                    <ul class="dropdown-menu pull-right">
                      <li><a href="/utility/province/detail.action?id=<s:property value="#item.id"/>"><s:property value="getTitleText('fms.menu.report.detail')" /></a></li>
                    </ul>
                  </div>
                </td>
              </tr>
            </s:iterator>
            </tbody>
          </table>
        </div>
        <footer class="panel-footer"></footer>
      </section>
    </section>
    <%--<footer class="footer bg-white b-t b-light"></footer>--%>
  </section>
  <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<script>
  $(document).ready(function() {

    $('#tblDistrict').dataTable({
      "bFilter": false,
      "bPaginate": false,
      "bAutoWidth": false,
      "sPaginationType":"full_numbers"
    });
  });
</script>