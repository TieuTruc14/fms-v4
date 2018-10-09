<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;
                    <s:property value="getTitleText('fms.header.home')"/></a></li>
                <li><a href="#"><s:property value="getTitleText('fms.menu.management')"/></a></li>
                <li><a href="#"><s:property value="getTitleText('fms.menu.vehicle')"/></a></li>
            </ul>

            <div class="m-b-md"><h3 class="m-b-none"><s:property value="getTitleText('fms.menu.vehicle.edit')"/></h3>
            </div>
            <s:if test="actionErrors.contains('action.error.company.permission')">
                <div class="alert"> <i class="fa fa-info-sign"></i>
                    <h4><code><s:property value="getTitleText('fms.company.dont.have.authority')"/> !</code></h4>
                </div>
            </s:if>
            <s:if test='%{message!=""}'>
                <section id="collapseOne" class="panel-collapse in" >
                    <div class="panel-body text-sm" style=" font-size: 16px;color:#c7254e;"><h3  class="text-center"><s:property value="message"/> </h3></div>
                 </section>
            </s:if>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> Form <s:property value="getTitleText('fms.menu.vehicle.update')"/></header>
                <div class="panel-body">
                    <s:form method="post" action="vehicle.edit.save" theme="simple"  enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="true">
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.menu.vehicle')"/> (*)</label>
                            <div class="col-sm-10">
                                <s:hidden name="item.company.id" />
                                <s:hidden name="companyId" />
                                <div class="input-group m-b">
                                   <code><s:property value="item.id"/> </code>
                                    <s:hidden name="item.id"/>
                                </div>

                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property
                                    value="getTitleText('fms.menu.transport.type')"/> (*)</label>

                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                    <s:select theme="simple"
                                              cssClass=" selectpicker span2"
                                              label="%{getTitleText('fms.menu.transport.type')}"
                                              list="ListTansportTypesInterceptor_transportTypes"
                                              listKey="id"
                                              listValue="name"
                                              name="item.type.id"
                                              headerKey=""
                                              value="item.type.id"
                                              headerValue="%{getTitleText('fms.menu.transport.type')}"
                                              data-width="auto"
                                              cssStyle="width:293px;height: 28px;"
                                            />
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property
                                    value="getTitleText('fms.menu.fuel.type')"/> (*)</label>

                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                    <s:select theme="simple"
                                              cssClass=" selectpicker span2"
                                              label="%{getTitleText('fms.menu.fuel.type')}"
                                              list="ListFuelTypesInterceptor_fuelTypes"
                                              listKey="id"
                                              listValue="name"
                                              name="item.fuelType.id"
                                              headerKey=""
                                              value="item.fuelType.id"
                                              headerValue="%{getTitleText('fms.menu.fuel.type')}"
                                              data-width="auto"
                                              cssStyle="width:293px;height: 28px;"
                                            />
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Lái xe mặc định</label>
                            <div class="col-sm-10">
                                <s:select theme="simple"
                                          cssClass="selectpicker span2"
                                          label="%{getTitleText('fms.utility.choice.all')}"
                                          list="drivers"
                                          listKey="id"
                                          listValue="name"
                                          name="item.driver.id"
                                          headerKey=""
                                          value="item.driver.id"
                                          headerValue="Chọn lái xe mặc định"
                                          data-width="auto"
                                          cssStyle="width:293px;height: 28px;"
                                        />
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.menu.vehicle.weight.seats')"/></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b"> <span class="input-group-addon"><i class="fa fa-fw fa-crop"></i></span>
                                    <s:textfield name="item.capacity" type="number" cssStyle="width:100%;max-width:250px;"  placeholder="Trọng tải (xe tải) hoặc số ghế (xe khách)" cssClass="form-control" />
                                </div>
                                <small><s:property value="getTitleText('fms.vehicle.capacity.description')"/></small>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.vehicle.sensor')"/></label>
                            <div class="col-sm-10">
                                <div>
                                    <label class="checkbox-inline">
                                        <s:checkbox name="item.sensor" fieldValue="true" label="sensor"/>Sensor
                                    </label>
                                </div>
                                <small><s:property value="getTitleText('fms.vehicle.sensor.description')"/> !</small>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.menu.report.send.data')"/></label>
                            <div class="col-sm-10">
                                <div>
                                    <label class="checkbox-inline">
                                        <s:checkbox name="item.bgt" fieldValue="true" label="bộ giao thông"/><s:property value="getTitleText('fms.menu.report.send.data')"/>
                                    </label>
                                </div>
                                <small><s:property value="getTitleText('fms.vehicle.send.data.description')"/> !</small>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.config')"/></label>
                            <div class="col-sm-10">
                                <div>
                                    <label class="checkbox-inline">
                                        <s:checkbox name="item.configI0" fieldValue="true" label="I0"/>I0
                                    </label>
                                    <label class="checkbox-inline">
                                        <s:checkbox name="item.configI1" fieldValue="true" label="I1"/>I1
                                    </label>
                                    <label class="checkbox-inline">
                                        <s:checkbox name="item.configI2" fieldValue="true" label="I2"/>I2
                                    </label>

                                </div>
                                <small><s:property value="getTitleText('fms.vehicle.config.contrary.direction')"/> !</small>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.vehicle.work')"/></label>
                            <div class="col-sm-10">
                                <div>
                                    <label class="checkbox-inline">
                                        <s:checkbox name="item.configI5" fieldValue="true" label="I5"/><s:property value="getTitleText('fms.vehicle.work')"/>
                                    </label></br></br>
                                    <s:select label="Chân tính xe công trình"
                                              headerKey="-1" headerValue="Chân tính xe công trình"
                                              list="#{'i1':'i1', 'i2':'i2','i3':'i3','i5':'i5'}"
                                              name="item.workVehicleMapping"
                                              value="item.workVehicleMapping"
                                              cssStyle="width:293px;height: 28px;"/><br/>
                                </div>
                                <small><s:property value="getTitleText('fms.vehicle.work.description')"/></small>
                            </div>
                        </div>

                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.vehicle.on.off.machine')"/></label>
                            <div class="col-sm-10">
                                <div >
                                    <label class="checkbox-inline">
                                        <s:checkbox name="item.onFilter" fieldValue="true" label="I0"/>onFilter
                                    </label>
                                </div>
                                <small><s:property value="getTitleText('fms.vehicle.on.off.machine.description')"/> </small>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.vehicle.lock')"/></label>
                            <div class="col-sm-10">
                                <div >
                                    <label class="checkbox-inline">
                                        <s:checkbox name="item.disable" fieldValue="true"  label="Khóa"/><s:property value="getTitleText('fms.vehicle.lock')"/>
                                    </label>
                                </div>
                                <small><s:property value="getTitleText('fms.vehicle.lock.description')"/> </small>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><s:property value="getTitleText('fms.report.title.note')"/>  (*)</label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                    <span class="input-group-addon"><i class="fa fa-fw fa-text-width"></i></span>
                                    <s:textarea name="item.note"  placeholder="%{getTitleText('fms.report.title.note')}" cssClass="form-control" rows="8"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                        <label class="col-sm-2 control-label"><s:actionmessage value="actionMessage"/> </label>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <a href="/manage/vehicle/vehicle.list.action" class="btn btn-default"><s:property value="getTitleText('fms.button.cancel')" /></a>
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
<link rel="stylesheet" type="text/css" href="/assets/js/select2/select2.css"/>
<script src="/assets/js/select2/select2.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $("#vehicle_edit_save_item_type_id").select2();
        $("#vehicle_edit_save_item_fuelType_id").select2();
        $("#vehicle_edit_save_item_driver_id").select2();
        $("#vehicle_edit_save_item_workVehicleMapping").select2();
    });
</script>
