
<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.feedback')"/> </a></li></ul>
            <div class="m-b-md"> </div>
            <section class="panel panel-body">
                <h3 class="m-b-none"><s:property value="getTitleText('fms.feedback.thank')"/></h3><br/>
                <label><s:property value="getTitleText('fms.feedback.content1')"/></label><br/>
                <label><s:property value="getTitleText('fms.feedback.content2')"/></label><br/><br/><br/>
                <label class="bold" style="color: #000000"><s:property value="getTitleText('fms.feedback.team')"/></label>
            </section>

        </section>
        <footer class="footer bg-white b-t b-light"></footer>
    </section>
</section>
