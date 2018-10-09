<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<s:set var="vehicles" value="%{getVehicle(item.id)}" />
<s:set var="drivers" value="%{getDriver(item.id)}" />

<section id="content">
    <section class="vbox">
        <section class="scrollable">
            <header class="header b-b b-light" style="min-height: 100px;">
                <ul class="breadcrumb no-margin-bottom no-border no-radius b-b b-light pull-in"> <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> <li><a href="#"><s:property value="getTitleText('fms.menu.management')" /></a></li> <li class="active"><s:property value="getTitleText('fms.menu.vehicle')"/></li><li class="active"> <s:property value="vehicleId" /></li></ul>
                <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.menu.detail.vehicle')" /> <code><s:property value="vehicleId" /></code></h3> </div>
            </header>
            <section class="hbox stretch">
                <aside class="aside-lg b-r">
                    <section class="panel panel-default m-b-none">
                        <header class="panel-heading bg-light no-border"><div class="clearfix text-center"> <div class="thumb-lg"> <img src="/assets/images/vehicle_default_avatar.png" class=""> </div> </div> </header>
                        <div class="list-group no-radius alt">
                        </div>
                    </section>
                    <div class="wrapper">
                        <s:if test="%{item == null}">
                            <div class="alert alert-warning alert-block"> <h4><i class="fa fa-bell-alt"></i><s:property value="getTitleText('fms.report.title.note')"/>!</h4> <p><s:property value="getTitleText('fms.vehicle.not.exists')" /></p> </div>
                        </s:if>
                        <s:else>
                            <div>
                                <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.vehicle')"/>:</small>  <code><s:property value="item.id" /></code></p>
                                <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.transport.type')"/>:</small> <s:property value="item.typeName" /> (<code><s:property value="item.type.id" /></code>)</p>
                                <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.vehicle.weight.seats')"/>:</small> <s:property value="item.capacity" /></p>
                                <div class="line"></div>
                                <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.utility.date.created')"/> :</small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateCreated,'dd/MM/yyyy')" /></p>
                                <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.utility.date.update')"/>: </small> <s:property value="@com.eposi.common.util.FormatUtil@formatDate(item.dateUpdated,'dd/MM/yyyy')" /></p>
                                <div class="line"></div>
                                <s:if test="%{item.disable}">
                                    <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.status')"/> :</small><code> <s:property value="getTitleText('fms.vehicle.lock')"/></code></p>
                                </s:if>
                                <s:else>
                                    <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.status')"/> :</small><code> <s:property value="getTitleText('fms.menu.report.activity')"/></code></p>
                                </s:else>
                                <div class="line"></div>
                                    <s:if test="%{item.note != ''}">
                                <p><small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.note')"/> :</small> <s:property value="item.note" /></p>
                                    </s:if>
                            </div>

                            <div class="line"></div>
                            <p><small class="text-uc text-xs text-muted"><b><s:property value="getTitleText('fms.menu.company.info')"/></b></small></p>
                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.company.code')" /></small><p><a href="/manage/company/detail.action?id=<s:property value="item.company.id" />"><code><s:property value="item.company.id" /></code></a></p>
                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.menu.company.name')" /></small><p><a href="/manage/company/detail.action?id=<s:property value="item.company.id" />"><s:property value="item.company.name" /></a></p>
                            <small class="text-uc text-xs text-muted"><s:property value="getTitleText('fms.report.title.address')"/></small><p><s:property value="item.company.address" /></p>
                            <small class="text-uc text-xs text-muted">Phone</small><p><s:property value="item.company.phone" /></p>
                            <sec:authorize access="hasAnyRole('ROLE_VEHICLE_EDIT')">
                            <div class="line"></div>
                                <p><a href="/manage/vehicle/vehicle.edit.action?vehicleId=<s:property value="item.id" />" class="btn btn-default btn-block"><i class="fa fa-pencil pull-left"></i><s:property value="getTitleText('fms.button.edit')"/></a></p>
                            </sec:authorize>

                        </s:else>
                    </div>
                </aside>
                <aside class="bg-light lt b-r">
                    <div class="wrapper">

                        <s:if test='messeage!=""'>
                            <section class="panel panel-default">
                                <div class="panel-body">
                                    <h4><i class="fa fa-bell-alt"></i><code><s:property value="messeage"/></code></h4>
                                </div>
                            </section>
                        </s:if>

                        <section class="panel panel-default">
                            <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.management.device')"/> <code > <s:property value="lstDevice.size" /></code>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                                <sec:authorize access="hasRole('ROLE_VEHICLE_DEVICE_ADD')">
                                <button class="addDeviceVehicle btn btn-sm btn-primary pull-right" style="margin-top:-8px" data-toggle="modal" data-target="#addDeviceVehicle"><i class="fa fa-plus pull-left"></i><s:property value="getTitleText('fms.menu.device.addnew')"/></button>
                                </sec:authorize>
                            </header>
                            <section class="panel-body" data-height="230px">
                                <div class="table-responsive">
                                    <table  class="table table-striped b-t b-light table-bordered m-b-none text-sm table-hover"  >
                                        <thead>
                                        <tr>
                                            <th class="box-shadow-inner small_col text-center">#</th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.code')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.date.active')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.date.expire')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.menu.product.type')"/></th>
                                            <th class="box-shadow-inner small_col text-center"  style="max-width:200px;"><s:property value="getTitleText('fms.report.title.note')"/></th>
                                            <th class="box-shadow-inner small_col text-center"><s:property value="getTitleText('fms.report.title.functional')"/></th>
                                        </tr>

                                        </thead>
                                        <tbody>

                                        <s:iterator var="item" value="lstDevice" status="stat">
                                            <tr>
                                                <td class=" text-center"><s:property value="#stat.count" /></td>
                                                <td class=" text-center"><s:property value="#item.id" /></td>
                                                <td class=" text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateActive,'dd/MM/yyyy')" /></td>
                                                <td class=" text-center"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.dateEnd,'dd/MM/yyyy')" /></td>
                                                <td class=" text-center"><s:property value="#item.batch.model.productType.name" /></td>
                                                <td class=" text-center"  style="max-width:200px;"><s:property value="#item.note" /></td>
                                                <td class=" text-center">
                                                    <div class="btn-group">
                                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i class="fa fa-random"></i></button>
                                                        <ul class="dropdown-menu pull-right">
                                                            <sec:authorize access="hasRole('ROLE_VEHICLE_DEVICE_DELETE')">
                                                            <li> <a class="removeDevice"
                                                                    data-toggle="modal" data-target="#removeDevice"
                                                                    data-device.id="<s:property value="#item.id"/>"><i class="fa fa-pencil-square-o"></i><s:property value="getTitleText('fms.vehicle.uninstall.device')"/> </a>
                                                            </li>
                                                            </sec:authorize>
                                                        </ul>
                                                    </div>
                                                </td>
                                            </tr>
                                        </s:iterator>

                                        </tbody>

                                    </table>
                                </div>
                            </section>
                        </section>

                        <section class="panel panel-default m-b-none" style="margin-top:20px;">
                            <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.menu.report.activity')"/>
                                <ul class="nav nav-pills pull-right"><li><a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a></li></ul>
                            </header>
                            <section class="panel-body">
                                <div class="timeline">
                                    <article class="timeline-item active">
                                        <div class="timeline-caption">
                                            <div class="panel bg-primary lter no-borders">
                                                <div class="panel-body">
                                                    <span class="timeline-icon"><i class="fa fa-info time-icon bg-primary"></i></span>
                                                    <span class="timeline-date"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(vehicleActivity.vehicleState.time,'yyyy/MM/dd HH:mm:ss')" /></span>
                                                    <h5><span><s:property value="vehicleActivity.vehicleState.address" /></span></h5>
                                                    <div class="m-t-sm timeline-action"><span class="h3 pull-left m-r-sm"><s:property value="vehicleActivity.vehicleState.speed" /> kmh</span></div>
                                                </div>
                                            </div>
                                        </div>
                                    </article>
                                    <s:iterator var="item" value="vehicleActivity.items" status="stat">
                                        <article class="timeline-item <s:if test="#stat.odd == false">alt</s:if>">
                                            <div class="timeline-caption">
                                                <div class="panel panel-default">
                                                    <div class="panel-body">
                                                        <span class="arrow <s:if test="#stat.odd == false">right</s:if><s:else>left</s:else>"></span>
                                                        <span class="timeline-icon"><s:property escape="false" value="#item.icon" /></span>
                                                        <span class="timeline-date"><s:property value="@com.eposi.common.util.FormatUtil@formatDate(#item.date,'yyyy/MM/dd HH:mm:ss')" /></span>
                                                        <h5><span><s:property value="#item.actionName" /></span></h5>
                                                        <p><s:property value="#item.context" /></p>
                                                    </div>
                                                </div>
                                            </div>
                                        </article>
                                    </s:iterator>
                                    <div class="timeline-footer"><a href="#"><i class="fa fa-plus time-icon inline-block bg-dark"></i></a></div>
                                </div>

                            </section>
                        </section>

                    </div>
                </aside>
            </section>
        </section>
        <%--<footer class="footer bg-white b-t b-light"><p>This is a footer</p></footer>--%>
    </section>
</section>

<div class="modal fade"  id="addDeviceVehicle"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;">
        <div class="modal-content"style="max-width: 700px;max-height: 500px">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalg"> <s:property value="getTitleText('fms.menu.choice.device')"/> </h5>
            </div>
            <s:form id="filter" method="POST"  action="vehicle.add.device.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="table-responsive" style="width:100%!important;">
                    <s:textfield name="id" value="%{item.id}" type="hidden" cssClass="form-control" />
                    <table  class="table table-striped b-t b-light tablle100" style="width:100%!important;" id="datatables">
                        <thead>
                        <tr>
                            <th > #</th>
                            <th class="text-center">
                                <label  class="checkbox m-n i-checks"><input style="width:20px;height:17px;margin-top:-7px;" type="checkbox"  />  <i></i></label>
                            </th>
                            <th  class="text-center"><s:property value="getTitleText('fms.menu.device.code')"/></th>
                            <th class="text-center" ><s:property value="getTitleText('fms.menu.product.type')"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator var="item" value="deviceList" status="stat">
                            <s:set var="idx" value="#stat.count - 1" />
                            <input hidden="true" name="devices[<s:property value='#idx'/>].id" value="<s:property value="#item.id"/>">
                            <tr>
                                <td><s:property value="#stat.count" /></td>
                                <td class="text-center"><label  class="checkbox-inline">
                                        <%--<s:checkbox name="devices[<s:property value='#idx'/>].checkbox" fieldValue="true" label="manager"/>--%>
                                    <input style="width:20px;height:17px;margin-top:-7px;" type="checkbox" name="devices[<s:property value="#idx"/>].checkbox">
                                    <i></i></label>
                                </td>
                                <td class="text-center"><label class=""><s:property value="#item.id"/></label></td>
                                <td class="text-center"><label class=""><s:property value="#item.model.productType.name"/></label></td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-sm btn-primary"><s:property value="getTitleText('fms.button.update')"/></button>
                </div>
            </s:form>
        </div>
    </div>
</div>

<div class="modal fade"  id="removeDevice" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 500px;max-height: 300px">
        <div class="modal-content"style="max-width: 500px;max-height: 300px">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLabel"><s:property value="getTitleText('fms.vehicle.uninstall.device')"/> </h5>
            </div>
            <s:form id="filter" method="POST"  action="vehicle.remove.device.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <h4><s:property value="getTitleText('fms.vehicle.uninstall.device')"/>  <code><span class="information_id_view"></span></code> --- <code><s:property value="vehicleId" /></code>?</h4>
                    <div class="form-group">
                        <div class="col-sm-9">
                            <s:textfield class="form-control " type="hidden" name="vehicleId" />
                            <input class="form-control information_id" type="hidden" name="id">
                        </div>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-sm btn-danger"> <s:property value="getTitleText('fms.vehicle.uninstall.device')"/>  </button>
                </div>
            </s:form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {

        $('#datatables').each(function() {
            var oTable = $(this).dataTable( {
                "bSort": false,
                "bProcessing": false,
                /* "sAjaxSource": "js/datatables/datatable.json",*/
                "iDisplayLength": -1,
                "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
                "sPaginationType": "full_numbers",
                "sScrollY": 240,
                "bPaginate": false

            } );
        });
        $('.dataTables_scrollHeadInner').css('width','100%');
        $('.tablle100').css('width','100%');
        $('.dataTables_filter').css('padding','2px');
        $('.table').css('margin-bottom','-2px');

        $(".removeDevice").click(function(){
            var iddevice = $(this).data('device.id');
            $(".information_id").val(iddevice);
            $('.information_id_view').text(iddevice);
        });
    });
</script>