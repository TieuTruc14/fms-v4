<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en" class="bg-dark">
<head>
    <meta charset="utf-8" />
    <title> ADMIN.QC31.VN | Hệ Thống Quản Lý Khách Hàng</title>
    <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <link rel="stylesheet" href="/assets/note/css/bootstrap.css" type="text/css" />
    <link rel="stylesheet" href="/assets/note/css/animate.css" type="text/css" />
    <link rel="stylesheet" href="/assets/note/css/font-awesome.min.css" type="text/css" />
    <link rel="stylesheet" href="/assets/note/css/font.css" type="text/css" />
    <link rel="stylesheet" href="/assets/note/css/app.css" type="text/css" />
    <!--[if lt IE 9]>
    <script src="/assets/note/js/ie/html5shiv.js"></script>
    <script src="/assets/note/js/ie/respond.min.js"></script>
    <script src="/assets/note/js/ie/excanvas.js"></script>
    <![endif]-->
</head>
<body class="">
<style>
    .navbar-brand img{max-height:50px;}
</style>
<section id="content" class="m-t-lg wrapper-md animated fadeInUp">
    <div class="container aside-xxl">
        <a class="navbar-brand block" href="index.html"><img src="/assets/images/logo.Eposi.gif" class="img-rounded"></a>
        <section class="panel panel-default bg-white m-t-lg">
            <header class="panel-heading text-center">
                <strong>Đăng Nhập</strong>
            </header>
            <form action="<s:url value='/j_spring_security_check'/>" class="panel-body wrapper-lg" method="post" class="form-group ">
                <div class="form-group">
                    <label class="control-label">Tài khoản</label>
                    <input id="username" name="username" placeholder="username" class="form-control input-lg">
                </div>
                <div class="form-group">
                    <label class="control-label">Mật khẩu</label>
                    <input type="password" id="password" name="password" placeholder="password" class="form-control input-lg">
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox"> Ghi nhớ mật khẩu
                    </label>
                </div>
                <button type="submit" class="btn btn-primary">Đăng nhập</button>
                <div class="line line-dashed"></div>
                <p class="text-primary">HỖ TRỢ KỸ THUẬT</p>
                <p class="text-muted"><small>Cố định: 04.3783 5200--Máy lẻ : 108, 210</small></p>
                <p class="text-muted"><small>Di động: 0968 699 983 (Mr. Phạm Mạnh)</small></p>
            </form>
        </section>
    </div>
</section>
<!-- footer -->
<footer id="footer">
    <div class="text-center padder">
        <p>
            <small>Hệ Thống Giám Sát Hành Trình<br>&copy; 2016</small>
        </p>
    </div>
</footer>
<!-- / footer -->
<script src="/assets/note/js/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="/assets/note/js/bootstrap.js"></script>
<!-- App -->
<script src="/assets/note/js/app.js"></script>
<script src="/assets/note/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/assets/note/js/app.plugin.js"></script>
</body>
</html>