<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="#"><s:property value="getTitleText('fms.menu.admin')"/></a></li> <li><a href="javascript:void(0)">Cập nhật tài khoản</a></li>
            </ul>
            <div class="m-b-md">
                    <h3 class="m-b-none">Thêm mới tài khoản : "<s:property value="company.name" />"</h3>
            </div>
            <s:if test="actionErrors.contains('action.error.permission')">
                <div class="alert"> <i class="fa fa-info-sign"></i>
                    <code><s:property value="getTitleText('fms.failed.authority')"/></code>
                </div>
            </s:if>
                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>Cập nhật tài khoản</header>
                    <div class="panel-body">
                        <s:form method="post" action="addnew.save.action" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
                            <s:hidden name="companyId"/>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Tên tài khoản</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.username" maxlength="25" cssStyle="width:100%;max-width: 250px;" placeholder="Tên tài khoản" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Mật khẩu</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:password name="item.password" type="password" maxlength="50" cssStyle="width:100%;max-width: 250px;" placeholder="Mật khẩu" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Xác nhận mật khẩu</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:password name="confirmPassword" maxlength="50" cssStyle="width:100%;max-width: 250px;" placeholder="Xác nhận mật khẩu" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>

                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Kiểu hiển thị</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-cog"></i></span>
                                        <s:select key="item.view" label="Loại xe" inputPrepend="" cssClass="form-control"
                                                  required="true" placeholder="Loại hình"
                                                  list="#{'0':'Tiếng Việt','1':'Tiếng Anh','2':'Taxi','3':'Xe máy','4':'Tàu thuyền','5':'Phà'}"
                                                  value="item.view"
                                                  style="width:100%;max-width: 250px;">
                                        </s:select>
                                        <s:fielderror cssStyle="color: red" fieldName="item.view"/>
                                    </div>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.name')"/></label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.name" maxlength="100" cssStyle="width:100%;max-width: 250px;" placeholder="Họ và tên" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Số điện thoại</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.phone" maxlength="15" cssStyle="width:100%;max-width: 250px;" placeholder="Số điện thoại" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Tài khoản chính</label>
                                <div class="col-sm-10">
                                    <div >
                                        <label class="checkbox-inline">
                                            <s:checkbox name="item.supperUser" fieldValue="true" value="true" label="supper"/>Supper User
                                        </label>
                                    </div>
                                    <small>Tài khoản tổng tức xem được toàn bộ xe và các tài khoản khác của công ty.Bỏ check nếu là tài khoản phụ.</small>
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
            format: "dd/mm/yyyy",
            startDate: "2012/01/01",
            endDate: "",
            todayBtn: "linked",
            calendarWeeks: false,
            autoclose: true,
            todayHighlight: true
        });
    });
</script>