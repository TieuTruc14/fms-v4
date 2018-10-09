<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li><li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.catalog')" /></a></li>
                <li><a href="/utility/view/list.action"> Danh mục hợp đồng</a></li></ul>
            <div class="m-b-md">
                    <h3 class="m-b-none">Danh mục Hợp đồng</h3>
            </div>
                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>
                      Cập nhật thông tin hiển thị
                    </header>
                    <div class="panel-body">
                        <s:form method="post" action="edit.save.action" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Key (*)</label>
                                <div class="col-sm-10">
                                    <code><s:property value="item.id" /></code>
                                    <s:hidden name="item.id" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Tiếng Anh </label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.nameE" maxlength="100" cssStyle="width:100%;max-width: 250px;" placeholder="Tên danh mục hợp đồng" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Tiếng Việt </label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.nameVN" maxlength="100" cssStyle="width:100%;max-width: 250px;" placeholder="Tên danh mục hợp đồng" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Taxi</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.nameTaxi" maxlength="100" cssStyle="width:100%;max-width: 250px;" placeholder="Tên danh mục hợp đồng" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Xe máy</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.nameBike" maxlength="100" cssStyle="width:100%;max-width: 250px;" placeholder="Tên danh mục hợp đồng" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Tàu thủy</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.nameShip" maxlength="100" cssStyle="width:100%;max-width: 250px;" placeholder="Tên hiển thị tàu" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">Phà</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw"></i>
                                        </span>
                                        <s:textfield name="item.nameFerry" maxlength="100" cssStyle="width:100%;max-width: 250px;" placeholder="Tên hiển thị Phà" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>

                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a href="/utility/view/list.action" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
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
