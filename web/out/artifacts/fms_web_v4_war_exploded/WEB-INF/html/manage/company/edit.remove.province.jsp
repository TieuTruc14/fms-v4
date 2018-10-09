<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<section id="content">
    <section class="vbox">
        <section class="scrollable">
            <header class="header b-b b-light" style="min-height: 100px;">
                <ul class="breadcrumb no-margin-bottom no-border no-radius b-b b-light pull-in"> <li><a href="index.html"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                    <li><a href="/manage/company/list.action"><s:property value="getTitleText('fms.menu.company')"/></a></li></ul>
                <div class="m-b-md"> <h3 class="m-b-none">Gỡ bỏ thuộc quản lý của Sở GTVT</h3></div>
            </header>
            <div class="panel-body">
                <s:if test="%{item != null}">
                    <div class="alert alert-info"> <i class="fa fa-info-sign"></i> Thực hiện gỡ bỏ thuộc quản lý của Sở GTVT <s:property value="province.name"/> cho Doanh nghiệp <s:property value="item.name"/> (<s:property value="item.id"/>) thành công.</div>
                </s:if>
                <s:else>
                    <div class="alert alert-error"> <i class="fa fa-info-sign"></i> Lỗi xảy ra trong quá trình xử lý ở máy chủ hoặc không tìm thấy Doanh nghiệp này!</div>
                </s:else>
            </div>
        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
</section>