<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
  <section class="vbox">
    <section class="scrollable">
      <header class="header b-b b-light" style="min-height: 100px;">
        <ul class="breadcrumb no-margin-bottom no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> <li><a href="#"><s:property value="getTitleText('fms.menu.management')" /></a></li> <li class="active"><s:property value="getTitleText('fms.menu.report.driver')"/></li> </ul>
        <div class="m-b-md"><h3 class="m-b-none"><s:property value="item.name" /> <small>(<s:property value="item.id" />)</small></h3></div>
      </header>

      <section class="hbox stretch">
        <aside class="aside-lg b-r">
          <section class="panel panel-default m-b-none">
            <header class="panel-heading bg-light no-border"><div class="clearfix text-center"> <div class="thumb-lg"> <img src="/assets/img/user.png" class=""> </div> </div> </header>
            <div class="list-group no-radius alt">
            </div>
          </section>
          <div class="wrapper">
            <div>
              <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.name')" /></small><p><s:property value="item.name" /></p>
              <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.phone')"/></small><p><s:property value="item.phone" /></p>
              <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.company.position')" /> </small><p><s:property value="item.licenceKey" /></p>
              <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.company')" />: </small><p><code><a href="/manage/company/detail.action?id=<s:property value="item.company.id"/>" ><s:property value="item.company.name" /></a></code></p>
              <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.note')" /> </small><p><s:property value="item.note" /></p>
            </div>
            <sec:authorize access="hasAnyRole('ROLE_DRIVER_EDIT')">
              <div class="line"></div>
              <p><a href="/manage/contact/edit.action?driverId=<s:property value="item.id" />&companyId=<s:property value="item.company.id"/>" class="btn btn-default btn-block"><i class="fa fa-pencil pull-left"></i><s:property value="getTitleText('fms.button.edit')"/></a></p>
            </sec:authorize>
          </div>
        </aside>
        <aside class="bg-light lt b-r">
          <div class="wrapper">

            <section class="panel panel-info">
              <div class="panel-body">
                <div class="btn-group"> <button class="btn btn-warning"><s:property value="getTitleText('fms.menu.report')"/></button> <button class="btn btn-warning dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button> <ul class="dropdown-menu">

                </ul></div>

              </div>
            </section>

            <section class="panel panel-default">
              <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.report.activity')"/>
                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
              </header>
              <section class="panel-body">

              </section>
            </section>
          </div>
        </aside>
      </section>
    </section>
    <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
  </section>
</section>
