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
                <li><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.account')" /></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.admin.account')" /></h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.header.data')" />
                    <div class="btn-group pull-right">
                        <button data-toggle="dropdown" class="btn btn-xs dropdown-toggle" style=""><i class="fa fa-download"></i> <s:property value="getTitleText('fms.button.export')" /> <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li class="disabled"><a href="#">Acrobat PDF</a></li>
                            <li><a href="list.action?format=xlsx">Excel XLSX</a></li>
                            <li class="divider"></li>
                            <li class="disabled"><a href="#">Text CSV</a></li>
                            <li class="disabled"><a href="#">Text XML</a></li>
                        </ul>
                    </div>
                </header>
                <div class="text-sm wrapper bg-light lt">
                    <s:form cssClass="form-inline padder" role="form" theme="simple">
                        <div class="form-group"><s:textfield name="filterUserName" placeholder="Username" cssClass="input-sm form-control"/></div>
                        <button type="submit" class="btn btn-default btn-sm" formaction="list.action"><s:property value="getTitleText('fms.utility.search')"/></button>
                    </s:form>
                </div>
                <div class="table-responsive table-overflow-x-fix">
                    <table id="tblUser" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Username</th>
                            <th class="box-shadow-inner small_col text-center">PassWord</th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.name')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.phone')"/></th>
                            <th class="box-shadow-inner small_col text-center hidden-xs"><s:property value="getTitleText('fms.menu.company')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.department')"/></th>
                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                        </tr>
                        </thead>
                        <tbody>

                        <s:iterator var="item" value="page.items" status="stat">
                            <tr>
                                <td><s:property value="#stat.count"/></td>
                                <td><a href="/utility/user/activity.action?username=<s:property value="#item.username" />"><s:property value="#item.username"/></a></td>
                                <td><a href="/utility/user/activity.action?username=<s:property value="#item.username" />"><s:property value="#item.password"/></a></td>
                                <td><a href="/utility/user/activity.action?username=<s:property value="#item.username" />"><s:property value="#item.name"/></a></td>
                                <td><s:property value="#item.phone"/></td>
                                <td class="hidden-xs"><a href="/manage/company/detail.action?id=<s:property value="#item.company.id" />"><s:property value="#item.company.name" /></a></td>
                                <td><a href="/utility/province/detail.action?id=<s:property value="#item.province.id" />"><s:property value="#item.province.name"/></a></td>
                                <td>
                                    <div class="btn-group">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                        <ul class="dropdown-menu pull-right">
                                            <li><a href="/utility/user/edit.action?username=<s:property value="#item.username" />"><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.button.edit')" /></a></li>
                                            <li><a href="/utility/user/change.password.action?username=<s:property value="#item.username" />"><i class="fa fa-pencil-square-o"></i> Reset Password</a></li>
                                            <li><a href="/utility/user/authority.action?username=<s:property value="#item.username" />&companyId=<s:property value="#item.company.id"/> "><i class="fa fa-pencil-square-o"></i> <s:property value="getTitleText('fms.admin.user.grants')" /></a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
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

<link href="/assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<script src="/assets/bootstrap-select/bootstrap-select.min.js"></script>
<script>
    $(document).ready(function() {

        $('#tblUser').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType":"full_numbers"
        });
    });
</script>
