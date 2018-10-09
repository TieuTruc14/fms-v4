<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico" />
  <title>EPOSI.FMS LOGIN</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

  <!-- CSS -->
  <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
  <link rel="stylesheet" href="/assets/note/css/app.v2.css" type="text/css"/>
  <link rel="stylesheet" href="/assets/note/css/font.css" type="text/css" cache="false"/>
  <link rel="stylesheet" href="/assets/login/css/style.css">

</head>
<%--<style>--%>

  <%--html,--%>
  <%--body {--%>
    <%--height: 100%;--%>
    <%--/* The html and body elements cannot have any padding or margin. */--%>
  <%--}--%>

  <%--/* Wrapper for page content to push down footer */--%>
  <%--#wrap {--%>
    <%--min-height: 100%;--%>
    <%--height: auto !important;--%>
    <%--height: 100%;--%>
    <%--/* Negative indent footer by its height */--%>
    <%--margin: 0 auto -60px;--%>
    <%--/* Pad bottom by footer height */--%>
    <%--padding: 0 0 60px;--%>
  <%--}--%>

  <%--/* Set the fixed height of the footer here */--%>
  <%--#footer {--%>
    <%--height: 60px;--%>
    <%--background-color: #f5f5f5;--%>
  <%--}--%>


  <%--/* Custom page CSS--%>
  <%---------------------------------------------------- */--%>
  <%--/* Not required for template or sticky footer method. */--%>

  <%--.container {--%>
    <%--width: auto;--%>
    <%--max-width: 680px;--%>
    <%--padding: 0 15px;--%>
  <%--}--%>
  <%--.container .credit {--%>
    <%--margin: 20px 0;--%>
  <%--}--%>
<%--</style>--%>
<body style="max-width: 1600px!important;min-width: 960px!important;margin: 0 auto;">
<header class="text-white header navbar navbar-fixed-top-xs menu-header-login" style="max-width: 1600px;min-width: 900px!important;margin: 0 auto;">
  <div class="logo-menu-login" style="max-width: 1000px;min-width: 950px!important;margin: 0 auto;" >
  </div>
  <div class=" clr "></div>
</header>
<div class=" clr "></div>
<div class="col-xs-12"  style="max-width: 1600px;;margin: 0 auto;">
  <%--<div class="col-xs-12"  style="max-width: 1600px;margin: 0 auto;">--%>
    <%--<div class="col-xs-1  ">--%>
    <%--</div>--%>
    <%--<div class="col-xs-3  ">--%>

      <%--<div class="image-login1"></div>--%>
    <%--</div>--%>
    <%--<div class=" col-xs-4 form-login bottom-login" >--%>
      <%--<form action="<s:url value='/j_spring_security_check'/>" method="post" class="form-group ">--%>
                            <%--<span style="text-transform: lowercase" type='text'>--%>
                                <%--<br/>--%>
                                <%--<div class="input-group text-sm">--%>
                                  <%--<div class="input-group-btn">--%>
                                        <%--<span  class="btn btn-bg btn-default">--%>
                                            <%--<span class="fa fa-user"></span>--%>
                                        <%--</span>--%>
                                  <%--</div>--%>
                                  <%--<input type="text"  id="username" name="username" class="input-sm input-login text-login text-dark" placeholder="Tên đăng nhập">--%>
                                <%--</div>--%>
                                <%--<div class="input-group text-sm">--%>
                                  <%--<div class="input-group-btn">--%>
                                        <%--<span  class="btn btn-md btn-default">--%>
                                            <%--<span class="fa fa-lock"></span>--%>
                                        <%--</span>--%>
                                  <%--</div>--%>
                                  <%--<input type="password" id="password" name="password" class="input-sm input-login text-login text-dark" placeholder="Mật khẩu">--%>
                                <%--</div>--%>
                            <%--</span>--%>
        <%--<input type="checkbox" > <label class="text-dark font-normal text-login">Ghi nhớ mật khẩu</label></br>--%>
        <%--<button type="submit" class="btn-success btn-xs btn btn-login">Đăng nhập</button>--%>
        <%--<div class="error"><span></span></div>--%>
      <%--</form>--%>
    <%--</div>--%>

    <%--<div class="col-xs-3 ">--%>
      <%--<div class="image-login2"></div>--%>
    <%--</div>--%>
  <%--</div>--%>
    <div id="wrap">
      <!-- Begin page content -->
      <div class="container">
        <div class="page-header">
          <h1>Kích hoạt email thành công!</h1>
        </div>
        <p class="lead">Xác nhận email thành công! Hệ thống giám sát hành trình EPOSI sẽ gửi các thông tin hoạt động thiết bị tới quý khách.</p>

      </div>
    </div>
  <div class="line-dashed line line-xs clr "></div>
  <div class="col-xs-12 ">
    <div class="col-xs-1"></div>
    <div class="col-xs-10">
      <div class="col-xs-1 "></div>
      <div class="col-xs-3 ">
        <h5 class="text-support footer-h ">Hỗ trợ kỹ thuật:</h5>
        <nav class="">
          <ul class="nav">
            <li class="active" ><h6 href="#" class="footer-h "><span class="pull-right font-thin "> </span> Cố định </h6>
              <ul class="nav lt nav_login">
                <li class="active text-dark font-normal text-login" ><span href=""> <i class="fa fa-angle-right"></i> <span>04.3355 4779--Máy lẻ 213,248,249</span> </span> </li>
              </ul>
            </li>
            <li class="active" ><h6 href="#" class="footer-h "><span class="pull-right font-thin "> </span> Di động </h6>
              <ul class="nav lt nav_login">
                <li class="active text-dark font-normal text-login" ><span href=""> <i class="fa fa-angle-right"></i> <span>0163 9989 001 (Mr.Danh Mạnh)</span> </span> </li>
                <li class="active text-dark font-normal text-login" ><span href=""> <i class="fa fa-angle-right"></i> <span>0163 9989 002 (Mr.Đông)</span> </span> </li>
                <li class="active text-dark font-normal text-login" ><span href=""> <i class="fa fa-angle-right"></i> <span>0163 9989 005 (Mr.Thắng)</span> </span> </li>
              </ul>
            </li>
          </ul>
        </nav>
      </div>
      <div class="col-xs-3 ">
        <h5 class=" text-support footer-h ">Chăm sóc khách hàng:</h5>
        <nav class="">
          <ul class="nav">
            <li class="active" ><h6 href="#" class="footer-h "><span class="pull-right font-thin"> </span> Cố định </h6>
              <ul class="nav lt nav_login">
                <li class="active text-dark font-normal text-login" ><span href=""> <i class="fa fa-angle-right"></i> <span>04.3355 4779--Máy lẻ 201,210</span> </span> </li>
              </ul>
            </li>
            <li class="active"><h6 href="#" class="footer-h "><span class="pull-right font-thin"> </span> Di động </h6>
              <ul class="nav lt nav_login">
                <li class="active text-dark font-normal text-login"><span href=""><i class="fa fa-angle-right"></i><span>0968 699 983 (Mr. Phạm Mạnh)</span> </span> </li>

              </ul>
            </li>
          </ul>
        </nav>
      </div>
      <div class="col-xs-3 ">
        <h5 class=" text-support footer-h ">Hỗ trợ kinh doanh:</h5>
        <nav class=" nav">
          <ul class="nav ">
            <li class="active " ><h6 href="#" class="footer-h "><span class="pull-right font-thin"> </span> Cố định </h6>
              <ul class="nav lt nav_login">
                <li class="active text-dark font-normal text-login" ><span href=""> <i class="fa fa-angle-right"></i> <span>04.3355 4779--Máy lẻ 204,205,206</span> </span> </li>
              </ul>
            </li>
            <li class="active" ><h6 href="#" class="footer-h "><span class="pull-right font-thin"> </span> Di động </h6>
              <ul class="nav lt">
                <li class="active text-dark font-normal text-login" ><span> <i class="fa fa-angle-right"></i> <span>0163 9988 995 (Mr.Cường)</span> </span> </li>
              </ul>
            </li>
          </ul>
        </nav>

      </div>
    </div>
    <div class="col-xs-1"></div>

  </div>
</div>
</div>
</div>


<!-- Javascript -->
<script src="/assets/note/js/app.v2.js"></script>

</body>

</html>

