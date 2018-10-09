<%--
  Created by IntelliJ IDEA.
  User: TieuTruc
  Date: 5/6/2015
  Time: 3:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li class="active"><a href="javascript:void(0)"><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                <li class="active"><a href="/store/device/list.action"><s:property value="getTitleText('fms.menu.device')"/></a></li>
                 <li class="active"><a href="/store/device/stock.out.action"><s:property value="getTitleText('fms.admin.management.delivery.note')" /></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.admin.management.stock.addnew.device.on.stock')" /></h3> </div>

            <div class="line line-dashed line-lg pull-in"></div>

            <s:form class="form-horizontal" cssClass="form-inline padder" method="post" action="stock.out.add.device.save.action">
                <s:hidden name="id"/>
                <section class="panel panel-default">
                    <header class="panel-heading"><s:property value="getTitleText('fms.admin.management.stock.addnew.device.on.stock')" />
                        <button style="margin-top: -5px;" type="submit" class="btn btn-primary pull-right btn-sm" ><s:property value="getTitleText('fms.button.update')" /></button>
                        <a style="margin-right: 10px;margin-top: -5px;" href="/store/device/stock.out.detail.action?id=<s:property value="%{id}"/>" class="btn btn-default pull-right btn-sm" ><s:property value="getTitleText('fms.button.cancel')" /></a>
                    </header>
                    <div class="wrapper">
                        <p><s:property value="getTitleText('fms.admin.management.stock.device.select')" />
                        </p>
                    </div>
                    <div class="table-responsive">
                        <table  class="table table-striped b-t b-light"  id="datatables">
                            <thead>
                            <tr>
                                <th style="width:50px;"> #</th>
                                <th style="width:200px;" class="text-center">
                                    <label  class="checkbox m-n i-checks"><input  style="width:20px;" type="checkbox"  />  <i></i></label>
                                </th>
                                <th ><s:property value="getTitleText('fms.menu.device.code')"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator var="item" value="deviceList" status="stat">
                                <s:set var="idx" value="#stat.count - 1" />
                                <input hidden="true" name="devices[<s:property value="#idx"/>].id" value="<s:property value="#item.id"/>">
                                <tr>
                                    <td><s:property value="#stat.count" /></td>
                                    <td class="text-center"><label  class="checkbox m-n i-checks">
                                        <input style="width:20px;" type="checkbox" name="devices[<s:property value="#idx"/>].checkbox">
                                        <i></i></label>
                                    </td>
                                    <td><label   class=""><s:property value="#item.id"/></label></td>
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
</section>

</section>

<script>
    $(document).ready(function() {

        // datatable
        $('#datatables').each(function() {
            var oTable = $(this).dataTable( {
                "bSort": true,
                "bProcessing": false,
                /* "sAjaxSource": "js/datatables/datatable.json",*/
                "iDisplayLength": 1000,
                "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
                "sPaginationType": "full_numbers"
            } );
        });

    } );

</script>
