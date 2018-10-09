<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="#"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                <li><a href="javascript:void(0)">Cập nhật tài khoản</a></li>
            </ul>
            <div class="m-b-md">
                    <h3 class="m-b-none">Cập nhật tài khoản</h3>
            </div>
                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>Cập nhật tài khoản</header>
                    <div class="panel-body">
                        <s:form method="post" action="edit.save.action" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Tên tài khoản(*)</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.username" maxlength="25" placeholder="Tên tài khoản" disabled="true" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Nhóm quyền</label>
                                <div class="col-sm-10">
                                    <div class="table-responsive">
                                        <table id="tblUser" class="table table-striped table-bordered m-b-none text-sm">
                                            <thead>
                                            <tr>
                                                <th class="box-shadow-inner small_col">#</th>
                                                <th class="box-shadow-inner small_col">Quyền</th>
                                                <th class="box-shadow-inner small_col"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <s:iterator value="item.grantedAuths" status="stat">
                                                <tr>
                                                    <td><s:property value="#stat.count"/></td>
                                                    <td><s:property value="authority"/></td>
                                                    <td>
                                                        <div class="btn-group">
                                                            <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                                            <ul class="dropdown-menu pull-right">
                                                                <li><a href="admin/user/edit.remove.authen.action?username=<s:property value="username" />&authority=<s:property value="authority" />"><i class="fa fa-pencil-square-o"></i>Xóa</a></li>
                                                            </ul>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </s:iterator>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a href="/store/device/list.action" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
                                    <button type="submit" data-loading-text="..." class="btn btn-primary"><s:property value="getTitleText('fms.button.update')" /></button>
                                </div>
                            </div>
                        </s:form>
                    </div>
                </section>
        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<link rel="stylesheet" href="/assets/js/eternicode-bootstrap-datepicker/css/datepicker.css" type="text/css"/>
<script src="/assets/js/eternicode-bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script>
    $(document).ready(function () {
        $('.input-daterange').datepicker({
            format: "yyyy/mm/dd",
            startDate: "2012/01/01",
            endDate: "",
            todayBtn: "linked",
            calendarWeeks: false,
            autoclose: true,
            todayHighlight: true
        });
    });
</script>