<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<section id="content">
  <section class="vbox">
    <section class="scrollable padder">
      <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
        <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
        <li><a href="#"><s:property value="getTitleText('fms.menu.admin')" /></a></li>
        <li><a href="javascript:void(0)">Nhóm quyền</a></li>
      </ul>
      <div class="m-b-md"> <h3 class="m-b-none">Danh sách nhóm quyền</h3> </div>

      <section class="panel panel-default">
        <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" />
        <sec:authorize access="hasAnyRole('ROLE_GROUP_EDIT')">
          <div class="btn-group pull-right">
            <a class=" addGrant btn-sm btn-primary pull-right" data-toggle="modal" data-target="#addGrant"><i class="fa fa-plus"></i> <s:property value="getTitleText('fms.button.addnew')" /></a>
          </div>
        </sec:authorize>
        </header>

        <div class="table-responsive table-overflow-x-fix">
          <table id="tblGroup" class="table table-striped table-bordered m-b-none text-sm">
            <thead>
            <tr>
              <th class="box-shadow-inner small_col text-center">#</th>
              <th class="box-shadow-inner small_col text-center">Mã</th>
              <th class="box-shadow-inner small_col text-center">Nhóm quyền</th>
              <th class="box-shadow-inner small_col text-center">Mô tả</th>
              <sec:authorize access="hasAnyRole('ROLE_GROUP_EDIT')">
              <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                </sec:authorize>
            </tr>
            </thead>
            <tbody>

            <s:iterator var="item" value="page.items" status="stat">
              <tr>
                <td><s:property value="#stat.count"/></td>
                <td><s:property value="#item.id"/></td>
                <td><s:property value="#item.name"/></td>
                <td><s:property value="#item.description"/></td>
                <sec:authorize access="hasAnyRole('ROLE_GROUP_EDIT')">
                <td>
                  <div class="btn-group">
                    <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                    <ul class="dropdown-menu pull-right">
                      <li>
                        <a class="editGrant"
                           data-toggle="modal" data-target="#editGrant"
                           data-grant.id="<s:property value="#item.id"/>"
                           data-grant.name="<s:property value="#item.name"/>"
                           data-grant.description="<s:property value="#item.description"/>"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a>
                      </li>
                      <li><a href="/utility/grant/group.grant.action?groupId=<s:property value="#item.id" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.admin.user.grants')" /></a></li>
                    </ul>
                  </div>
                </td>
              </tr>
                </sec:authorize>
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
                  <li><a href="/utility/user/list.action?page.pageNumber=<s:property value="%{page.pageNumber}" />&filterUserName=<s:property value="filterUserName"/>">«</a></li>
                </s:if>
                <s:iterator var="item" value="page.pageList" status="stat">
                  <li><a href="/utility/user/list.action?page.pageNumber=<s:property value="#item" />&filterUserName=<s:property value="filterUserName"/>"><s:property value="#item" /></a></li>
                </s:iterator>
                <s:if test="%{page.pageNumber < page.getPageCount()}">
                  <li><a href="/utility/user/list.action?page.pageNumber=<s:property value="%{page.pageNumber + 2 }" />&&filterUserName=<s:property value="filterUserName"/>">»</a></li>
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


<div class="modal fade"  id="addGrant"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
  <div class="modal-dialog" style="max-width: 500px;">
    <div class="modal-content"style="max-width: 500px;">
      <div class="modal-header" style="padding: 7px;">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h5 class="modal-title" id="myModalAdd">Thêm nhóm quyền</h5>
      </div>
      <s:form id="filter" method="POST"  action="addnew.save.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
        <div class="modal-body"  style="padding: 10px;">
          <div class="form-group">
            <label class="col-sm-3 control-label">Mã nhóm:</label>
            <div class="col-sm-9">
              <div class="input-group m-b"> <span class="input-group-addon"> <i class="fa fa-fw fa-barcode"></i> </span>
                <s:textfield name="item.id"  placeholder="Mã Nhóm quyền" cssClass="form-control " />
              </div>
            </div>
          </div>
          <div class="line line-dashed line-lg pull-in"></div>
          <div class="form-group">
            <label class="col-sm-3 control-label">Tên nhóm:</label>
            <div class="col-sm-9">
              <div class="input-group m-b"><span class="input-group-addon"> <i class="fa fa-fw fa-file-text"></i> </span>
                  <s:textfield name="item.name" placeholder="Tên" cssStyle="text-align:left" theme="simple" cssClass="input-small input-sm input-s-sm form-control "/>
              </div>
            </div>
          </div>
          <div class="line line-dashed line-lg pull-in"></div>
          <div class="form-group">
            <label class="col-sm-3 control-label">Mô tả:</label>
            <div class="col-sm-9">
              <div class="input-group m-b"><span class="input-group-addon"> <i class="fa fa-fw fa-file-text"></i> </span>
                <s:textfield name="item.description" placeholder="Mô tả" cssStyle="text-align:left" theme="simple" cssClass="input-small input-sm input-s-sm form-control "/>
              </div>
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


  <div class="modal fade"  id="editGrant"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 500px;">
      <div class="modal-content"style="max-width: 500px;">
        <div class="modal-header" style="padding: 7px;">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h5 class="modal-title" id="myModalEdit">Sửa nhóm quyền</h5>
        </div>
        <s:form id="filter" method="POST"  action="edit.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
        <div class="modal-body"  style="padding: 10px;">
          <div class="form-group">
            <label class="col-sm-3 control-label">Mã nhóm:</label>
            <div class="col-sm-9">
              <div class="input-group m-b"> <span class="input-group-addon"> <i class="fa fa-fw fa-barcode"></i> </span>
                <s:textfield name="abc"  placeholder="Mã" disabled="true" cssClass="form-control information_edit_id" />
                <s:textfield name="item.id"  type="hidden" cssClass="form-control information_edit_id" />
              </div>
            </div>
          </div>
          <div class="line line-dashed line-lg pull-in"></div>
          <div class="form-group">
            <label class="col-sm-3 control-label">Tên nhóm:</label>
            <div class="col-sm-9">
              <div class="input-group m-b"><span class="input-group-addon"> <i class="fa fa-fw fa-file-text"></i> </span>
                <s:textfield name="item.name" placeholder="Tên" cssStyle="text-align:left" theme="simple" cssClass="input-small input-sm input-s-sm form-control information_edit_name"/>
              </div>
            </div>
          </div>
          <div class="line line-dashed line-lg pull-in"></div>
          <div class="form-group">
            <label class="col-sm-3 control-label">Mô tả:</label>
            <div class="col-sm-9">
              <div class="input-group m-b"><span class="input-group-addon"> <i class="fa fa-fw fa-file-text"></i> </span>
                <s:textfield name="item.description" placeholder="Mô tả" cssStyle="text-align:left" theme="simple" cssClass="input-small input-sm input-s-sm form-control information_edit_description"/>
              </div>
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
  </div>


<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<script>
  $(document).ready(function() {

    $('#tblGroup').dataTable({
      "bFilter": false,
      "bPaginate": false,
      "bAutoWidth": false,
      "sPaginationType":"full_numbers"
    });

    $(".editGrant").click(function(){
      var id=$(this).data('grant.id');
      var name=$(this).data('grant.name');
      var description=$(this).data('grant.description');

      $(".information_edit_id").val(id);
      $(".information_edit_name").val(name);
      $(".information_edit_description").val(description);
    });

  });
</script>
