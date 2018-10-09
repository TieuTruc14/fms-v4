<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<section id="content" style="width: 100%!important;">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in"> <li><a href="index.html"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li> </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.welcome')" /></h3> </div>
            <p><s:property value="getTitleText('fms.welcome.content')" /></p>

        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/assets/js/bootstrap3-dialog/bootstrap-dialog.min.css"/>
<script type="text/javascript" src="/assets/js/bootstrap3-dialog/bootstrap-dialog.min.js"></script>
<script type="text/javascript">

    var timerNotifyStates;
    $(document).ready(function() {
        if (timerNotifyStates) {
            clearTimeout(timerNotifyStates)
        }
        notify('#warning_msg')
    });

    function notify(elem) {
        var url = "/tracking/notify.json.action";
        $.getJSON(url, function(data) {
            var content  = "";
            var title = "";
            if(data){
                $.each( data, function( idx, val) {
                    title+=val[0];
                    content+= val[1];
                    content+='<br/>';
                });

                if(content.length>0){
                    BootstrapDialog.show({ title: title,
                        message:content,
                        buttons: [
                            {
                                label: 'Đóng', cssClass: 'btn-sm btn-warning text-dark',
                                action: function (dialogRef) {
                                    dialogRef.close();
                                }
                            }
                        ]
                    });
                }
            }
        })
    };

</script>​
<style>
    .font-tb{font-size:16px!important;}
    .bootstrap-dialog-title{font-size:18px!important;}
</style>
