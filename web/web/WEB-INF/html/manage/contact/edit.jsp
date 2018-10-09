<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
  <section class="vbox">
    <section class="scrollable padder">
      <ul class="breadcrumb no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> <li><a href="#"><s:property value="getTitleText('fms.menu.management')" /></a></li> <li><a href="#"><s:property value="getTitleText('fms.menu.report.driver')"/></a></li></ul>
      <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.button.addnew')" /> <s:property value="getTitleText('fms.company.represent')" /></h3></div>

      <div>
        <s:if test="hasActionMessages()">
          <div class="alert alert-success">
            <s:actionmessage/>
          </div>
        </s:if>
      </div>

      <div class="alert alert-info"> <button type="button" class="close" data-dismiss="alert">×</button> <i class="fa fa-info-sign"></i><s:property value="getTitleText('fms.please.complete.infor')"/> </div>

      <section class="panel panel-default">
        <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> Form <s:property value="getTitleText('fms.button.update')" /></header>
        <div class="panel-body">
          <s:form method="post" action="edit.save" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
            <s:hidden name="companyId" />
            <s:hidden name="contactId" />

            <div class="form-group">
              <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.name')" /> (*)</label>
              <div class="col-sm-10">
                <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-crop"></i></span>
                  <s:textfield name="item.name" maxlength="64" placeholder="%{getTitleText('fms.report.title.name')}" cssStyle="width:100%;max-width: 250px;" cssClass="form-control" />
                </div>
              </div>
            </div>
            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
              <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.phone')" /> (*)</label>
              <div class="col-sm-10">
                <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-crop"></i></span>
                  <s:textfield name="item.phone" cssStyle="width:100%;max-width: 250px;" placeholder="%{getTitleText('fms.report.title.phone')}" cssClass="form-control" />
                </div>
              </div>
            </div>
            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
              <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.company.position')" /> </label>
              <div class="col-sm-10">
                <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-crop"></i></span>
                  <s:textfield name="item.position"  cssStyle="width:100%;max-width: 250px;" placeholder="%{getTitleText('fms.company.position')}" cssClass="form-control" />
                </div>
              </div>
            </div>
            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
              <label class="col-sm-2 control-label">Email</label>
              <div class="col-sm-10">
                <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-crop"></i></span>
                  <s:textfield name="item.email" cssStyle="width:100%;max-width: 250px;" placeholder="Email" cssClass="form-control" />
                </div>
              </div>
            </div>
            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
              <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.note')" /></label>
              <div class="col-sm-10">
                <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-crop"></i></span>
                  <s:textarea name="item.note"  cssStyle="width:100%;max-width: 250px;" placeholder="%{getTitleText('fms.report.title.note')}" cssClass="form-control" />
                </div>
              </div>
            </div>


            <div class="line line-dashed line-lg pull-in"></div>
            <div class="form-group">
              <div class="col-sm-4 col-sm-offset-2">
                <a href="/manage/company/detail.action?id=<s:property value="companyId"/>" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
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
