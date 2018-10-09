<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
  .table-bordered > thead > tr > th, .table-bordered > thead > tr > td{
    border-bottom-width: 1px;
  }
</style>
<section id="content">
  <section class="vbox">
      <section class="scrollable padder">
        <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
          <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
          <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
          <li class="active"><a href="/store/device/list.action"><s:property value="getTitleText('fms.menu.device')"/></a></li>
          <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.admin.management.reclamation.device')"/></a></li>
        </ul>
        <div class="m-b-md"> <h3 class="m-b-none">Phân quyền nhóm " <s:property value="groupId"/> "</h3> </div>
        <div class="line line-dashed line-lg pull-in"></div>

        <s:form class="form-horizontal" id="formStock" cssClass="form-inline padder" method="get" action="group.grant.save.action">
        <s:hidden name="groupId"/>
        <section class="panel panel-default">
          <header class="panel-heading">Phần quyền cho nhóm quyền người dùng "<s:property value="groupId"/>", Chọn quyền phù hợp.
            <button style="margin-top: -5px;" name="xacnhan" type="submit" class="btn btn-primary pull-right btn-sm" >Xác nhận</button>
            <a style="margin-right: 10px;margin-top: -5px;" href="/utility/grant/list.action" class="btn btn-default pull-right btn-sm" ><s:property value="getTitleText('fms.button.cancel')"/></a>
          </header>
          <div class="table-responsive">
            <table  class="table table-striped table-bordered m-b-none text-sm table-hover" id="datatables" >
              <thead>
              <tr>
                <th class="text-center">#</th>
                <th class="text-center">Nhóm chức năng</th>
                <th class="text-center">Mô tả nhóm</th>
                <th class="text-center">Xem thông tin</th>
                <th class="text-center">Thêm mới</th>
                <th  class="text-center">Sửa thông tin</th>
                <th  class="text-center">Xóa</th>
              </tr>

              </thead>
              <tbody>
              <s:iterator var="item" value="lstGrants" status="stat">
                <s:set var="idx" value="#stat.count - 1" />
                <input hidden="true" name="lstGrants[<s:property value="#idx"/>].id" value="<s:property value="#item.id"/>">
                <input hidden="true" name="lstGrants[<s:property value="#idx"/>].name" value="<s:property value="#item.name"/>">
                <tr>
                  <td class=" text-center"><s:property value="#stat.count" /></td>
                  <td class=" text-left"><s:property value="#item.name" /></td>
                  <td class=" text-left"><s:property value="#item.description" /></td>
                  <%--<td class=" text-center">--%>
                      <%--<s:checkbox name="#item.view"  cssClass="checkbox m-n i-checks" cssStyle="height:20px;color:white;background-color: white;" fieldValue="true" />--%>
                  <%--</td>--%>

                  <td class=" text-center">
                    <s:if test="#item.view">
                      <label  class="checkbox m-n i-checks"><input style="width:20px;" checked type="checkbox" name="lstGrants[<s:property value="#idx"/>].view"><i></i></label>
                    </s:if><s:else>
                      <label  class="checkbox m-n i-checks"><input style="width:20px;"  type="checkbox" name="lstGrants[<s:property value="#idx"/>].view"><i></i></label>
                    </s:else>
                  </td>
                  <td class=" text-center">
                    <s:if test="#item.add">
                      <label  class="checkbox m-n i-checks"><input style="width:20px;" checked type="checkbox" name="lstGrants[<s:property value="#idx"/>].add"><i></i></label>
                    </s:if><s:else>
                    <label  class="checkbox m-n i-checks"><input style="width:20px;"  type="checkbox" name="lstGrants[<s:property value="#idx"/>].add"><i></i></label>
                  </s:else>
                  </td>
                  <td class=" text-center">
                    <s:if test="#item.edit">
                      <label  class="checkbox m-n i-checks"><input style="width:20px;" checked type="checkbox" name="lstGrants[<s:property value="#idx"/>].edit"><i></i></label>
                    </s:if><s:else>
                    <label  class="checkbox m-n i-checks"><input style="width:20px;"  type="checkbox" name="lstGrants[<s:property value="#idx"/>].edit"><i></i></label>
                  </s:else>
                  </td>
                  <td class=" text-center">
                    <s:if test="#item.delete">
                      <label  class="checkbox m-n i-checks"><input style="width:20px;" checked type="checkbox" name="lstGrants[<s:property value="#idx"/>].delete"><i></i></label>
                    </s:if><s:else>
                    <label  class="checkbox m-n i-checks"><input style="width:20px;"  type="checkbox" name="lstGrants[<s:property value="#idx"/>].delete"><i></i></label>
                  </s:else>
                  </td>

                  <%--<td class=" text-center">--%>
                   <%--<s:checkbox name="#item.add" cssStyle="height:20px;" fieldValue="true" />--%>
                  <%--</td>--%>
              </s:iterator>
              </tbody>
            </table>
          </div>

          </s:form>

          <footer class="panel-footer">
          </footer>
        </section>

      </section>

  </section>

</section>
<link rel="stylesheet" type="text/css" href="/assets/js/bootstrap3-dialog/bootstrap-dialog.min.css"/>
<script type="text/javascript" src="/assets/js/bootstrap3-dialog/bootstrap-dialog.min.js"></script>
<script>
  $(document).ready(function() {
    $('#datatables').each(function() {
      var oTable = $(this).dataTable( {
        "bSort": true,
        "bProcessing": false,
        /* "sAjaxSource": "js/datatables/datatable.json",*/
        "iDisplayLength": 100,
        "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
        "sPaginationType": "full_numbers"
      });
    });
  } );

</script>

