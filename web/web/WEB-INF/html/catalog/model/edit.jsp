<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.catalog')" /></a></li>
                <li><a href="/catalog/model.list.action"><s:property value="getTitleText('fms.menu.catalog.model')"/></a></li></ul>
            <div class="m-b-md">
                    <h3 class="m-b-none"><s:property value="getTitleText('fms.button.update')" /></h3>
            </div>
            <s:if test="actionErrors.contains('action.error.permission')">
                <div class="alert"> <i class="fa fa-info-sign"></i>
                    <code><s:property value="getTitleText('fms.failed.authority')"/></code>
                </div>
            </s:if>
                <section class="panel panel-default">
                    <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>
                            <s:property value="getTitleText('fms.button.update')" />
                    </header>
                    <div class="panel-body">
                        <s:form method="post" action="model.edit.save" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
                            <s:hidden name="goodsId" />
                            <div class="form-group">
                                <label class="col-sm-2 control-label">ID (*)</label>
                                <div class="col-sm-10">
                                    <code><s:property value="item.id" /></code>
                                    <s:hidden name="item.id" />

                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Tên model:</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-crop"></i></span>
                                        <s:textfield name="item.name"  cssStyle="width:100%;max-width:250px;" placeholder="Tên" cssClass="form-control" />
                                    </div>

                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Loại hàng hóa</label>
                                <div class="col-sm-10">
                                    <s:select theme="simple"
                                              cssClass="selectpicker span2"
                                              label="Chọn loại hàng hóa"
                                              list="ListProductTypeInterceptor_models"
                                              listKey="id"
                                              listValue="name"
                                              name="item.productType.id"
                                              headerKey=""
                                              headerValue="Chọn loại hàng hóa"
                                              data-width="auto"
                                              cssStyle="width:293px;height: 30px"
                                            />
                                    <s:fielderror cssStyle="color: red" fieldName="item.parent.id"/>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.note')"/></label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-fw fa-pencil"></i>
                                        </span>
                                        <s:textarea name="item.note" placeholder="%{getTitleText('fms.report.title.note')}" cssClass="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="line line-dashed line-lg pull-in"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <a href="/catalog/model.list.action?goodsId=<s:property value="goodsId"/>" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
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
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css" />
<script src="/assets/js/select2/select2.min.js"></script>
<script>
    $(document).ready(function() {
        $("#model_edit_save_item_productType_id").select2();
    });

</script>