<%@ page import="org.apache.struts2.ServletActionContext" %>
<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header class="bg-dark dk header navbar navbar-fixed-top-xs">
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico" />
    <div class="navbar-header aside-md ">
        <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
            <i class="fa fa-bars"></i>
        </a>
        <a href="/index.action" class="navbar-brand" data-toggle="fullscreen1111"><img src="/assets/images/logo.Eposi.gif" class="m-r-sm logo_fms"></a>
        <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".nav-user">
            <i class="fa fa-cog"></i>
        </a>
    </div>

    <div class="nav navbar-nav navbar-left hidden-xs"  style="max-width: 700px!important;">
        <marquee align="center" direction="left" height="30" onmouseout="this.start()" onmouseover="this.stop()" scrollamount="5" width="100%">
            <s:property value="getTitleText('fms.system.notify')" />
        </marquee>
    </div>

    <ul class="nav navbar-nav navbar-right hidden-xs nav-user " >
        <li class="hidden-xs">
            <a href="#" class="dropdown-toggle dk" data-toggle="dropdown">
                <s:property value="getTitleText('fms.help')" />
            </a>
            <section class="dropdown-menu aside-xl">
                <section class="panel bg-white">
                    <header class="panel-heading b-light bg-light">
                        <strong>Chọn tài liệu</strong>
                    </header>
                    <div class=" list-group-alt animated fadeInRight">
                            <a href="/assets/FMS-HDSD-Admin.pdf" class="media list-group-item">
                              <span class="pull-left thumb-sm">
                                 <i class="fa fa-book fa-3x"></i>
                              </span>
                              <span class="media-body block m-b-none">
                                <s:property value="getTitleText('fms.guide.using')" />
                              </span>
                            </a>
                    </div>

                </section>
            </section>
        </li>

        <li class="dropdown">
            <a href="#" class=" dropdown-toggle "  data-toggle="dropdown" > <span class="thumb-sm avatar pull-left"> <img src="/assets/img/user.png"> </span> <sec:authentication property="principal.name" /> <b class="caret"></b> </a>
            <ul class="dropdown-menu animated fadeInRight "><span class="arrow top"></span>
                <li><a href="#"><s:property value="getTitleText('fms.user.profile')" /></a></li>
                <li class="changeMyPassword" data-toggle="modal" data-target="#changeMyPassword"><a href="#">Đổi mật khẩu</a></li>
                <li class="divider"></li>
                <li><a href="/j_spring_security_logout"><s:property value="getTitleText('fms.log.out')" /></a></li>
            </ul>
        </li>

    </ul>
</header>
<div class="modal fade"  id="changeMyPassword"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 700px;max-height: 300px">
        <div class="modal-content"style="max-width: 700px;max-height: 300px">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalChangePass">ĐỔI MẬT KHẨU</h5>
            </div>
            <s:form id="filter" method="POST"  action="/utility/user/change.mypassword.action" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">Mật khẩu cũ:</label>
                        <div class="col-sm-8">
                            <s:textfield name="oldPass" type="password"  placeholder="Mật khẩu cũ" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">Mật khẩu mới:</label>
                        <div class="col-sm-8">
                            <s:textfield name="newPassword" type="password" placeholder="Mật khẩu mới" cssClass="form-control" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">Mật khẩu mới(nhập lại):</label>
                        <div class="col-sm-8">
                            <s:textfield name="confirmPass" type="password" placeholder="Mật khẩu mới" cssClass="form-control" />
                        </div>
                    </div>
                    <s:set var="username">
                        <sec:authentication property="principal.username" />
                    </s:set>
                    <s:textfield name="username" value="%{username}" type="hidden" cssClass="form-control" />
                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><s:property value="getTitleText('fms.button.cancel')"/></button>
                    <button type="submit" class="btn btn-sm btn-primary"><s:property value="getTitleText('fms.button.update')"/></button>
                </div>
            </s:form>
        </div>
    </div>
</div>