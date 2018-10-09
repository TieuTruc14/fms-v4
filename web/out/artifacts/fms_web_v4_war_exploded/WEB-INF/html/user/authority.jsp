<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style type="text/css">
    select {
        width: 200px;
        float: left;
    }
    .controls {
        width: 60px;
        float: left;
        margin: 10px;
        margin-top:40px;
    }

</style>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href="#"><s:property value="getTitleText('fms.menu.admin')"/></a></li> <li><a href="javascript:void(0)">Cập nhật tài khoản </a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none"><s:property value="getTitleText('fms.admin.user.grants')"/> <s:property value="getTitleText('fms.menu.account')"/><code> "<s:property value="item.username" />" </code>!</h3>
            </div>
            <s:if test="actionErrors.contains('action.error.permission')">
                <div class="alert"> <i class="fa fa-info-sign"></i>
                    <code><s:property value="getTitleText('fms.failed.authority')"/></code>
                </div>
            </s:if>
            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i><s:property value="getTitleText('fms.button.update')"/> </header>
                <div class="panel-body">
                    <s:form method="post" action="authority.save.action" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true" onSubmit="return selectAll()">
                        <s:hidden name="item.username" />
                        <s:hidden name="companyId"/>
                        <s:hidden name="username"/>

                        <div class="form-group" >
                            <div class="form-group">
                                <label class="col-lg-2 col-sm-0 col-md-1 control-label"></label>
                                <div class="col-lg-10 col-sm-12 col-md-11">
                                    <div class="col-sm-5 col-xs-5 col-lg-3-5 col-md-4-5">
                                    <label class="col-xs-12 control-label" style="text-align:left;">Danh sách quyền:</label>
                                        <s:select id        = "from"
                                                  cssClass="col-xs-12"
                                                  size      = "10"
                                                  name      = "lstAllAuthor"
                                                  cssStyle  = "margin-top:10px; margin-bottom:10px;"
                                                  list      = "lstAllAuthor"
                                                  listKey   = "id"
                                                  listValue = "name"
                                                  multiple  = "true"
                                               />
                                    </div>
                                    <div class="controls col-sm-2 col-xs-2">
                                        <a href="javascript:moveAll('from', 'to')" class="btn btn-default text-center" style="margin-bottom: 10px;"><i class="fa fa-chevron-right"></i><i class="fa fa-chevron-right"></i></a>
                                        <a href="javascript:moveSelected('from', 'to')" class="btn btn-default" style="margin-bottom: 10px;">&nbsp;<i class="fa fa-chevron-right"></i>&nbsp; </a>
                                        <a href="javascript:moveSelected('to', 'from')" class="btn btn-default" style="margin-bottom: 10px;">&nbsp;<i class="fa fa-chevron-left"></i>&nbsp; </a>
                                        <a href="javascript:moveAll('to', 'from')" class="btn btn-default" style="margin-bottom: 10px;"><i class="fa fa-chevron-left"></i><i class="fa fa-chevron-left"></i></a>
                                    </div>
                                    <div class="col-sm-5 col-xs-5 col-lg-3-5 col-md-4-5">
                                        <label class="col-xs-12 control-label" style="text-align:left;">Quyền đã cấp:</label>
                                    <s:select id        = "to"
                                              cssClass="col-xs-12"
                                              size      = "10"
                                              name      = "lstChoose"
                                              cssStyle  = "margin-top:10px; margin-bottom:10px;"
                                              list      = "lstChoose"
                                              listKey   = "id"
                                              listValue = "name"
                                              multiple  = "true"
                                            />
                                    </div>

                                </div>
                            </div>

                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group" style="margin-top: 10px;">
                            <div class="col-sm-4 col-sm-offset-2">
                                <a href="/manage/company/detail.action?id=<s:property value="companyId"/>" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')"/> </a>
                                <button type="submit" data-loading-text="..." class="btn btn-primary"><s:property value="getTitleText('fms.button.update')"/></button>
                            </div>
                        </div>
                    </s:form>
                </div>
            </section>
        </section>
        <%--<footer class="footer bg-white b-t b-light"><p></p></footer>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js">
</script>
<script>
    function moveAll(from, to) {
        $('#'+from+' option').remove().appendTo('#'+to);
    }

    function moveSelected(from, to) {
        $('#'+from+' option:selected').remove().appendTo('#'+to);
    }
    function selectAll() {
        $("select option").attr("selected","selected");
    }
</script>