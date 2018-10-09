<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
    .table-bordered > thead > tr > th, .table-bordered > thead > tr > td{
        border-bottom-width: 1px;
    }
</style>
<section id="content">
    <section class="vbox">
        <sec:authorize access="hasAnyRole('ROLE_COMPANY_ADD')">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                <li class="active"><a href="/store/device/list.action"><s:property value="getTitleText('fms.menu.device')"/></a></li>
                <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.admin.management.reclamation.device')"/></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.admin.management.reclamation.device')"/> " <s:property value="company.name"/> "</h3> </div>
            <div class="line line-dashed line-lg pull-in"></div>

            <s:form class="form-horizontal" id="formStock" cssClass="form-inline padder" method="get" action="stock.recovery.save.action">
             <s:hidden name="companyId"/>
            <section class="panel panel-default">
                <header class="panel-heading"><s:property value="getTitleText('fms.admin.management.reclamation.device')"/>
                    <button style="margin-top: -5px;" name="xacnhan" type="submit" class="btn btn-primary pull-right btn-sm" ><s:property value="getTitleText('fms.admin.management.reclamation.device')"/></button>
                    <a style="margin-right: 10px;margin-top: -5px;" href="/manage/company/detail.action?id=<s:property value="companyId"/>" class="btn btn-default pull-right btn-sm" ><s:property value="getTitleText('fms.button.cancel')"/></a>
                </header>
                <div class="wrapper">
                    Chọn thiết bị cần thu hồi
                </div>
                <div class="table-responsive">
                    <table  class="table table-striped table-bordered m-b-none text-sm table-hover" id="datatables" >
                        <thead>
                        <tr>
                            <th rowspan="2" class="text-center">#</th>
                            <th rowspan="2" class="text-center"> <label  class="checkbox m-n i-checks"> <input  style="width:20px" type="checkbox"  /></label></th>
                            <th rowspan="2" class="text-center"><s:property value="getTitleText('fms.menu.device.code')"/></th>
                            <th colspan="2" class="text-center"><s:property value="getTitleText('fms.admin.management.guarantee.period')"/></th>

                        </tr>
                        <tr>
                            <th class="text-center"><s:property value="getTitleText('fms.report.title.start')"/></th>
                            <th class="text-center"><s:property value="getTitleText('fms.report.title.end')"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator var="item" value="deviceList" status="stat">
                            <s:set var="idx" value="#stat.count - 1" />
                            <input hidden="true" name="devices[<s:property value="#idx"/>].id" value="<s:property value="#item.id"/>">
                            <tr>
                                <td class=" text-center"><s:property value="#stat.count" /></td>
                                <td class=" text-center">
                                    <label  class="checkbox m-n i-checks"><input style="width:20px;"  type="checkbox" name="devices[<s:property value="#idx"/>].checkbox"><i></i></label>
                                </td>
                                <td class=" text-center"><s:property value="#item.id" /></td>
                                <td class=" text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateActive,'dd/MM/yyyy')" /></td>
                                <td class=" text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateEnd,'dd/MM/yyyy')" /></td>

                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>

                </s:form>

                <footer class="panel-footer">
                </footer>
            </section>

        </section>
</sec:authorize>
    </section>

</section>
<link rel="stylesheet" type="text/css" href="/assets/js/bootstrap3-dialog/bootstrap-dialog.min.css"/>
<script type="text/javascript" src="/assets/js/bootstrap3-dialog/bootstrap-dialog.min.js"></script>
<script>
    $(document).ready(function() {
        $('#datatables').each(function() {
            var oTable = $(this).dataTable( {
                "bSort": true,
                "bProcessing": false,
                /* "sAjaxSource": "js/datatables/datatable.json",*/
                "iDisplayLength": 100,
                "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
                "sPaginationType": "full_numbers"
            });
        });
    } );

</script>

