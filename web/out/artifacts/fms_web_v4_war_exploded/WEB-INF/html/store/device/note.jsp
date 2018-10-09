<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/index.action"><i class="fa fa-home"></i>&nbsp;<s:property value="getTitleText('fms.header.home')" /></a></li>
                <li><a href=""><s:property value="getTitleText('fms.menu.admin')"/></a></li>
                <li><a href=""><s:property value="getTitleText('fms.menu.device')"/></a></li>
                <li><a href=""><s:property value="getTitleText('fms.report.title.note')"/></a></li>
            </ul>
            <div class="m-b-md"> <h3 class="m-b-none"><s:property value="getTitleText('fms.button.addnew')"/> <s:property value="getTitleText('fms.report.title.note')"/> TB <code><s:property value="%{id}"/> </code></h3> </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> <s:property value="getTitleText('fms.report.title.note.content')"/></header>
                <div class="text-sm wrapper bg-light lt">
                    <s:form cssClass="form-inline padder" role="form" theme="simple">
                        <div class="col-sm-12">
                            <div class="btn-toolbar m-b-sm btn-editor" data-role="editor-toolbar" data-target="#editor">
                                <div class="btn-group">
                                    <a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" title="Font">
                                        <i class="fa fa-font"></i><b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu"></ul>
                                </div>
                                <div class="btn-group"><a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" title="Font Size">
                                    <i class="fa fa-text-height"></i>&nbsp;<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a data-edit="fontSize 5"><font size="5">Huge</font></a></li>
                                        <li><a data-edit="fontSize 3"><font size="3">Normal</font></a></li>
                                        <li><a data-edit="fontSize 1"><font size="1">Small</font></a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <a class="btn btn-default btn-sm" data-edit="bold" title="Bold (Ctrl/Cmd+B)">
                                        <i class="fa fa-bold"></i>
                                    </a>
                                    <a class="btn btn-default btn-sm" data-edit="italic"title="Italic (Ctrl/Cmd+I)">
                                        <i class="fa fa-italic"></i>
                                    </a>
                                    <a class="btn btn-default btn-sm" data-edit="strikethrough" title="Strikethrough">
                                        <i class="fa fa-strikethrough"></i>
                                    </a>
                                    <a class="btn btn-default btn-sm" data-edit="underline" title="Underline (Ctrl/Cmd+U)">
                                        <i class="fa fa-underline"></i>
                                    </a>
                                </div>
                                <div class="btn-group">
                                    <a class="btn btn-default btn-sm" data-edit="insertunorderedlist" title="Bullet list">
                                        <i class="fa fa-list-ul"></i>
                                    </a>
                                    <a class="btn btn-default btn-sm" data-edit="insertorderedlist" title="Number list">
                                        <i class="fa fa-list-ol"></i>
                                    </a>
                                    <a class="btn btn-default btn-sm" data-edit="outdent" title="Reduce indent (Shift+Tab)">
                                        <i class="fa fa-dedent"></i>
                                    </a>
                                    <a class="btn btn-default btn-sm"data-edit="indent" title="Indent (Tab)">
                                        <i class="fa fa-indent"></i>
                                    </a>
                                </div>

                                <div class="btn-group">
                                    <a class="btn btn-default btn-sm" data-edit="justifyleft" title="Align Left (Ctrl/Cmd+L)"><i
                                        class="fa fa-align-left"></i>
                                    </a>
                                    <a class="btn btn-default btn-sm" data-edit="justifycenter" title="Center (Ctrl/Cmd+E)">
                                        <i class="fa fa-align-center"></i>
                                    </a>
                                    <a class="btn btn-default btn-sm" data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)">
                                        <i class="fa fa-align-right"></i>
                                    </a>
                                    <a class="btn btn-default btn-sm" data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)">
                                        <i class="fa fa-align-justify"></i>
                                    </a>
                                </div>
                                <div class="btn-group"><a class="btn btn-default btn-sm dropdown-toggle"
                                                          data-toggle="dropdown" title="Hyperlink"><i
                                        class="fa fa-link"></i></a>

                                    <div class="dropdown-menu">
                                        <div class="input-group m-l-xs m-r-xs"><input
                                                class="form-control input-sm" placeholder="URL"
                                                type="text" data-edit="createLink"/>

                                            <div class="input-group-btn">
                                                <button class="btn btn-default btn-sm" type="button">
                                                    Add
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <a class="btn btn-default btn-sm" data-edit="unlink" title="Remove Hyperlink">
                                        <i class="fa fa-cut"></i>
                                    </a>
                                </div>
                                <div class="btn-group">
                                    <a class="btn btn-default btn-sm" data-edit="undo" title="Undo (Ctrl/Cmd+Z)">
                                        <i class="fa fa-undo"></i>
                                    </a>
                                    <a class="btn btn-default btn-sm" data-edit="redo" title="Redo (Ctrl/Cmd+Y)">
                                        <i class="fa fa-repeat"></i>
                                    </a>
                                </div>
                                <input type="text" class="form-control-trans pull-left"  data-edit="inserttext" id="voiceBtn" x-webkit-speech="" style="width:25px;height:28px;">
                            </div>
                            <div id="editor" class="form-control" style="overflow:scroll;height:200px;max-height:250px"></div>

                            <textarea hidden="true" name="contentText" id="contentText"></textarea>

                            <div class="line line-dashed line-lg pull-in"></div>
                            <div >
                                <a class="btn  btn-default " href="/store/device/list.action"><i class="fa fa-reply"></i><s:property value="getTitleText('fms.button.cancel')" /></a>
                                <button id="btnSubmit" type="submit" formaction="form.success.action" class="btn btn-primary "><s:property value="getTitleText('fms.button.addnew')" /></button>
                            </div>
                        </div>

                    </s:form>
                </div>
            </section>

        </section>
        <%--<footer class="footer bg-white b-t b-light"></footer>--%>
    </section>
</section>
<!-- wysiwyg -->
<script src="/assets/note/js/wysiwyg/jquery.hotkeys.js" cache="false"></script>
<script src="/assets/note/js/wysiwyg/bootstrap-wysiwyg.js" cache="false"></script>
<script src="/assets/note/js/wysiwyg/demo.js" cache="false"></script>

<script>
    $(document).ready(function() {
        $('#editor').wysiwyg();
        $('#btnSubmit').bind('click', function () {
            $("#contentText").val($("#editor").html());
        });
    });
</script>