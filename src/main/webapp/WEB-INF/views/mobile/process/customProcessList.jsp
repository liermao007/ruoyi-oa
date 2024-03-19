<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<html>
<head>
    <title>自由流程信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
    <style>
        .modal.fade.in {
            top: 5%;
        }
        .ul-form td a {
            display: block;
            width: 100%;
            height: 100%;
            text-align: -webkit-center;
            padding: 0;
            line-height: 27px;
            text-decoration: none;
        }

        .ul-form td a.active {
            background: #45aeea;
            color: #fff;
        }

        .ul-form td a:hover {
            background: #45aeea;
            color: #fff;
            border-radius: 5px;
        }

        select{
            width:75%;
        }
        .process{
            display: block;
            color:#45aeea;
            width: 100%;
            height: 100%;
            text-align: -webkit-center;
            padding: 0;
            line-height: 27px;
            text-decoration: none;
            cursor: pointer;
        }
        .process:hover{
            background: #45aeea;
            color: #fff;
            border-radius: 5px;
        }
        .modal.fade{
            top:-80%
        }

    </style>
    <style>
        .borderWidth2{
            -webkit-border-width:1px;
            -moz-border-width:1px;
            -ms-border-width:1px;
            -o-border-width:1px;
            border-width:1px;
            border-width:1px\0;
        }
    </style>
    <style rel="stylesheet">
        input {
            /*background: none;*/
            width: 100%;
            height: 100%;
            /*outline:none;*/
            /*border: 0px;*/
            text-align: center;
        }

        input[readonly]{
            background: none;
            width: 100%;
            height: 100%;
            outline: none;
            border: 0px;
            text-align: center;
        }
        #contentTable tbody td {
            padding: 0 4px 0 4px;
            height: 40px;
        }

        .entry_item > td > div > a {
            cursor: pointer;
        }
        .entry_item > td{
            text-align:center;
        }
        .modal{
            position: fixed !important;
        }
    </style>
</head>
<body>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" width="800px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel" align="center">
                    自由节点设定
                </h4>
            </div>
            <div class="modal-body" width="800px" style="max-height: 205px">
                <div class="input-group">
                    <div class="form-group">
                        <table id="contentTables" class="table table-bordered table-striped mg-t datatable ">
                            <thead>
                            <tr style="background: white">
                                <th style="width: 15px;text-align: center" >节点名称</th>
                                <th style="width: 30px;text-align: center">节点类型</th>
                                <th style="width:20px;text-align: center">审批人</th>
                                <th style="width:50px;text-align: center">操作</th>
                            </tr>
                            </thead>
                            <c:if test="${empty customProcess.id}">
                                <tr>
                                    <td style="width: 7%"><input readonly value="创建"/></td>
                                    <td style="width: 13%"><input readonly value="开始"/></td>
                                    <td style="width:15%"><input readonly value="${fns:getUser().name}"/></td>
                                    <td style="width:10%"><input type="hidden" value="${fns:getUser().id}"></td>
                                    <td style="width:25%;display: none" ><input type="hidden" value="start">
                                    <td style="width:25%;display: none"><input type="hidden" value="0">
                                    </td>
                                </tr>
                                <%--<form id="contentForm" action="${ctx}/process/transferSet/save">--%>
                                <tbody id="contentTableTbody">

                                </tbody>
                                <%--</form>--%>
                                <tr>
                                    <td style="width: 7%"><input readonly value="归档"/></td>
                                    <td style="width: 5%"><input readonly value="归档"/></td>
                                    <td style="width: 15%"><input  id="nodeId_show_1000" value="${fns:getUser().name}"  onclick="handlePerson(1000)"/></td>
                                    <td style="width:10%"><input id="nodeId_1000" type="hidden" value="${fns:getUser().id}" ></td>
                                    <td style="width:25%;display: none" ><input type="hidden" value="apply_end"></td>
                                    <td style="width:25%;display: none"><input type="hidden" value="0"></td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty customProcess.id}">
                                <tbody id="contentTableTbody">

                                </tbody>

                            </c:if>
                        </table>
                    </div>
                </div>
            </div>
            <span style="color:red;">&nbsp;&nbsp;&nbsp;&nbsp;节点类型：会签 如果有多人，则多人都同意才进行下一步，否则返回到申请人处。<br></span>
            <span style="color:red;">&nbsp;&nbsp;&nbsp;&nbsp;节点类型：非会签 如果有多人，则其中有一个同意即可进行下一步，否则返回到申请人处。<br></span>
            <span style="color:red;">&nbsp;&nbsp;&nbsp;&nbsp;会签与非会签需要所有的审批人审批，不管是同意还是不同意都需要审批。<br><br></span>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <input id="btnSubmit" class="btn btn-primary" type="button" onclick="save1()" value="保存">
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" >
    <div class="modal-dialog" width="800px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel1" align="center">
                    审批人设定
                </h4>
            </div>
            <div class="modal-body" width="800px">
                <div class="input-group">
                    <div class="form-group">
                        <table id="contentTable1" class="table table-bordered table-striped mg-t datatable ">
                            <tr>
                                <input type="hidden" id="table">
                                <td style="width: 5%">
                                    <sys:treeselect id="handlePerson" name="handlePerson"
                                                    value="${TransferSet.handlePerson}" labelName="name"
                                                    labelValue="${TransferSet.handlePersonName}"
                                                    title="用户" url="/sys/office/treeData?type=3"
                                                    cssClass="input-xxlarge required" notAllowSelectParent="true"
                                                    checked="true"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <input id="btnSubmit1" onclick="saveHandlePerson($('#table').val())" class="btn btn-primary"
                       type="button" value="保存">
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<form:form id="searchForm" modelAttribute="customProcess"  action="" method="post" class="breadcrumb form-search">
    <ul class="ul-form" style="align:right">
        <table align="right" border="0px solid black" style="margin-right: 100px; ">
            <tr>
                <td style="text-align: center"><font size="3" color="#2fa4e7"><b>缩放比例：</b></font>
                    <select id="printSelect" style="width:75px;">
                        <option value="27">30%</option>
                        <option value="45">50%</option>
                        <option value="54">60%</option>
                        <option value="63">70%</option>
                        <option value="72">80%</option>
                        <option value="76">85%</option>
                        <option value="81">90%</option>
                        <option value="85">95%</option>
                        <option value="90" selected="true">100%</option>
                        <option value="112">125%</option>
                        <option value="135">150%</option>
                        <option value="180">200%</option>
                    </select>&nbsp;&nbsp;&nbsp;</td>

                <td width="50px" style="text-align: center;font-weight: bold"><span class="process" onclick="preview()"><font size="3">打印</font></span>
                </td>
                <c:if test="${!(fn:contains(customProcess.type,'flowView'))}">
                    <td width="80px" style="text-align: center;font-weight: bold"><span class="process" onclick="toProcess()"><font
                            size="3">流转设定</font></span></td>
                </c:if>
            </tr>
        </table>
    </ul>
</form:form>

<table border="0" cellpadding="0" cellspacing="0" height="183" width="100%">
    <tbody>
    <tr>
        <td height="38" width="4%">
            &nbsp;</td>
        <td height="38" width="4%">
            &nbsp;</td>
        <td height="38" width="84%">
            <img src="/static/images/logo.png" style="float: left;height: 40px;"/></td>
        <td height="38" width="4%">
            &nbsp;</td>
        <td height="38" width="4%">
            &nbsp;</td>
    </tr>
    <tr>
        <td height="38" width="4%">
            &nbsp;</td>
        <td height="38" width="4%">
            &nbsp;</td>
        <td height="38" width="84%">
            <p align="center">
                <b><font size="6">自由流程</font></b></p>
        </td>
        <td height="38" width="4%">
            &nbsp;</td>
        <td height="38" width="4%">
            &nbsp;</td>
    </tr>
    <tr>
        <td height="26" width="4%">
            &nbsp;</td>
        <td height="26" width="4%">
            &nbsp;</td>
        <td height="26" width="84%">
            <table border="0" cellpadding="0" cellspacing="0" height="22" width="100%">
                <tbody>
                <tr>
                    <td align="right" height="22" style="width: 108px;" width="16%">
                        <font size="3"></font></td>
                    <td align="left" height="22" style="width: 214px;" width="16%">
                        <font size="3"></font></td>
                    <td align="center" height="22" width="17%">
                        &nbsp;</td>
                    <td align="center" height="22" width="17%">
                        &nbsp;</td>
                    <td align="center" height="21" style="width: 204px;" width="17%">
                        <b><font size="3"></font></b></td>
                    <td align="center" height="21" style="width: 141px;" width="19%">
                        &nbsp;</td>
                </tr>
                </tbody>
            </table>
        </td>
        <td height="26" width="4%">
            &nbsp;</td>
        <td height="26" width="4%">
            &nbsp;</td>
    </tr>
    <tr>
        <td height="101" width="4%">
            &nbsp;</td>
        <td height="101" width="4%">
            &nbsp;</td>
        <td height="35" width="84%">
            <form:form id="inputForm" modelAttribute="customProcess" action="${ctx}/process/customProcess/save"
                       method="post" class="form-horizontal">
                <input type="hidden" value="${fns:getUser().name}" id="userName">
                <input type="hidden" name="FJLJ" id="FJLJ" value="${customProcess.FJLJ}"/>
                <input type="hidden" name="FJMC" id="FJMC" value="${customProcess.FJMC}"/>
                <c:if test="${not empty customProcess.id and !(fn:contains(customComment.taskDefKey,'start'))}">
                    <input type="hidden" id="procInstId" htmlEscape="false" maxlength="100" readonly="false"
                           class="input-xlarge  required" value="${customProcess.procInstId}"/>
                    <input type="hidden" path="flag" id="flag" htmlEscape="false" maxlength="100" readonly="false"/>
                    <input type="hidden" id="tbodyHtml"/>
                    <table border="2" bordercolor="#000000" cellpadding="0" cellspacing="0" height="" width="100%" id="cavas" style="background-color: #fff" >
                        <tbody>
                        <tr>
                            <td align="center" height="50px" width="14%">
                                <font size="3"><b>姓  名</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="center" height="" width="14%">
                                <font size="3">${customProcess.name}</font>
                            </td>
                            <td align="center" height="50px" width="14%">
                                <font size="3"><b>部 门</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="center" height="" width="14%">
                                <font size="3">${customProcess.deptId}</font></td>
                        </tr>
                        <tr>
                            <td align="center" height="50px" width="14%">
                                <font size="3"><b>标 题</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="left" height="" width="14%" colspan="3">
                                <font size="3">${customProcess.title}</font></td>
                        </tr>
                        <tr>
                            <td align="center" height="400px" width="14%">
                                <font size="3"><b>内 容</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="left" height="" width="14%" colspan="3">
                                <font size="3">${customProcess.content}</font></td>
                        </tr>
                        <tr>
                            <td align="center" height="100" width="14%">
                                <font size="3"><b>附  件</b></font>&nbsp;&nbsp;</td>
                            <td height="" width="14%" colspan="3" id="hhh">
                                <font size="3"></font></td>
                        </tr>

                        <c:forEach items="${fns:findTableComment(customProcess.procInstId)}" var="customHiActinst">
                            <tr>
                                <td align="center" style="height: 110px"><font size="3"><b>${customHiActinst.name}</b></font></td>
                                <td align="center" colspan="3" style="height: 110px">${customHiActinst.comment}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </c:if>
                <c:if test="${fn:contains(customComment.taskDefKey,'start') and not empty customProcess.id}">
                    <input type="hidden" id="procInstId" htmlEscape="false" maxlength="100" readonly="false"
                           class="input-xlarge  required" value="${customProcess.procInstId}"/>
                    <input type="hidden" path="flag" id="flag" htmlEscape="false" maxlength="100" readonly="false"/>
                    <form:hidden path="id"></form:hidden>
                    <table border="1" bordercolor="#000000" cellpadding="0" cellspacing="0" height="" width="100%">
                        <tbody>
                        <tr>
                            <td align="center" height="50px" width="14%">
                                <font size="3"><b>姓 名</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="center" height="" width="14%">
                                <font size="3">${fns:getUser().name}</font>
                            </td>
                            <td align="center" height="50px" width="14%">
                                <font size="3"><b>部 门</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="center" height="" width="14%">
                                <font size="3">${customProcess.deptId}</font></td>
                        </tr>
                        <tr>
                            <td align="center" height="50px" width="14%">
                                <font size="3"><b>标 题</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="left" height="" width="14%" colspan="3">
                                <font size="3"><form:input path="title" htmlEscape="false" id="title"
                                                           cssStyle="width: 98%;height:100px;text-align: left" maxlength="1000"/></font></td>
                        </tr>
                        <tr>
                            <td align="center" height="400px" width="14%">
                                <font size="3"><b>内 容</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="left" height="" width="14%" colspan="3">
                                <font size="3"><form:textarea path="content" htmlEscape="false" id="content"
                                                              cssStyle="width: 98%;height: 400px" maxlength="1000"
                                                              class="input-xlarge required"/></font></td>
                        </tr>
                        <tr>
                            <td align="center" height="100" width="14%">
                                <font size="3"><b>附  件</b></font>&nbsp;&nbsp;</td>
                            <td height="" width="14%" colspan="3" id="hhh">
                                <font size="3"></font></td>
                        </tr>

                        <c:forEach items="${fns:findTableComment(customProcess.procInstId)}" var="customHiActinst">
                            <tr>
                                <td align="center" style="height: 110px"><font size="3"><b>${customHiActinst.name}</b></font></td>
                                <td align="center" colspan="3">${customHiActinst.comment}</td>
                            </tr>
                        </c:forEach>



                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty customProcess.id}">
                    <form:hidden path="procInstId" id="procInstId" htmlEscape="false" maxlength="100" readonly="false"
                                 class="input-xlarge  required" value="${customProcess.procInstId}"/>
                    <table border="1" bordercolor="#000000" cellpadding="0" cellspacing="0" height="" width="100%">
                        <tbody>
                        <tr>
                            <td align="center" height="50px" width="14%">
                                <font size="3"><b>姓 名</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="center" height="" width="14%">
                                <font size="3"><form:input path="name" cssStyle="height: 100%;width: 93%;text-align: left" htmlEscape="false" maxlength="100" readonly="true"
                                                           class="input-xlarge  required" value="${fns:getUser().name}"/>

                                </font>
                            </td>
                            <td align="center" height="50px" width="14%">
                                <font size="3"><b>部 门</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="center" height="" width="14%">
                                <font size="3"><form:input path="deptId" cssStyle="height: 100%;width: 93%" htmlEscape="false" maxlength="64"
                                                           class="input-xlarge " readonly="true"
                                                           value="${fns:getOffice()}"/></font></td>
                        </tr>
                        <tr>
                            <td align="center" height="50px" width="14%">
                                <font size="3"><b>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="left" height="" width="14%" colspan="3">
                                <font size="3"><form:input path="title" htmlEscape="false" id="title"
                                                           cssStyle="width: 98%;height:50px" maxlength="1000"/></font></td>
                        </tr>
                        <tr>
                            <td align="center" height="400px" width="14%">
                                <font size="3"><b>内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容</b></font>&nbsp;&nbsp;<font color="red">*</font></td>
                            <td align="center" height="" width="14%" colspan="3">
                                <font size="3"><form:textarea path="content" htmlEscape="false" id="content"
                                                              cssStyle="width: 98%;height: 400px" maxlength="1000"
                                                              class="input-xlarge required"/></font></td>
                        </tr>
                        <tr>
                            <td align="center" height="100" width="14%">
                                <font size="3"><b>附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件</b></font>&nbsp;&nbsp;</td>
                            <td height="" width="14%" colspan="3" id="hhh">
                                <font size="3"></font></td>
                        </tr>
                        <tr>
                            <td align="center" height="100" width="14%">
                                <font size="3"><b>是否上传附件：</b></font>&nbsp;&nbsp;</td>
                            <td align="center" height="" width="14%" colspan="3">
                                <font size="3"><iframe id="iframe2" scrolling="no" src="/static/stream/stream.html" style="border: 0px currentColor; width: 90%; height: 265px;"></iframe>
                                </font></td>
                        </tr>
                        </tbody>
                    </table>
                </c:if>
            </form:form>

        </td>
        <td height="101" width="4%">
            &nbsp;</td>
        <td height="101" width="4%">
            &nbsp;</td>
    </tr>
    </tbody>
</table>
<br>
<table border="0" cellpadding="0" cellspacing="0" style="margin-left: 9% ;margin-right: 9%"  width="83%" class="table-form">
    <c:if test="${not empty customProcess.id and !(fn:contains(customComment.taskDefKey,'start')) and !(fn:contains(customProcess.type,'flowView')) }">
        <tr><td height="50px" style="font-size: 18px;font-weight: bold">您的意见：</td></tr>
        <input type="hidden" id="custRuskTaskinstId" value="${customComment.id}"/>
        <input type="hidden" id="taskDefKey" value="${customComment.taskDefKey}"/>
        <input type="hidden" id="procInstId" value="${customProcess.procInstId}"/>
        <input type="hidden" id="flag" htmlEscape="false" maxlength="100" readonly="false" value="2"/>
        <tr>
            <td>
                <font size="3"><textarea  id="comment" htmlEscape="false"
                                          style="width: 98%;height: 100px" maxlength="1000"></textarea></font>
            </td>
        </tr>
    </c:if>
</table>
<table  border="0" cellpadding="0" cellspacing="0" style="margin-left: 9% ;margin-right: 9%;margin-bottom: 10px"  width="50%">
    <tr>
            <c:if test="${empty customProcess.id}">
        <td style="line-height: 40px;" >
            <input  class="btn btn-primary" type="button" value="提交申请" onclick="save();"/></td>
        </c:if>
        <c:if test="${not empty customProcess.id}">
            <input type="hidden" value="${customProcess.id}" id="emptyId"/>
            <c:if test="${fn:contains(customComment.taskDefKey,'apply_cs')}">
                <td style="line-height: 40px"><input  class="btn btn-primary" type="button" value="已 阅" onclick="applyCS()"></td>
            </c:if>
            <c:if test="${!(fn:contains(customComment.taskDefKey,'apply_cs')) and !(fn:contains(customComment.taskDefKey,'start'))  and !(fn:contains(customProcess.type,'flowView'))}">
                <c:if test="${!fn:contains(customComment.taskDefKey,'start') and !(fn:contains(customComment.taskDefKey,'apply_'))}">
                   <td style="line-height: 40px"> <input  class="btn btn-primary" type="button" value="同 意" onclick="return agreeAudit();"/>&nbsp;
                    <input  class="btn btn-primary" type="button" value="驳 回" onclick="return reject();"/>&nbsp;</td>
                    <%--<td width="50px" style="text-align: center;font-weight: bold"><span class="process" onclick="agreeAudit()"><font size="3">同意</font></span></td>--%>
                    <%--<td width="50px" style="text-align: center;font-weight: bold"><span class="process"  onclick="reject()"><font size="3">驳回</font></span></td>--%>
                </c:if>
                <c:if test="${(fn:contains(customComment.taskDefKey,'apply_end')) and !(fn:contains(customComment.taskDefKey,'start'))}">
                    <td style="line-height: 40px"><input  class="btn btn-primary" type="button" value="归 档" onclick="agree()"></td>
                </c:if>
                <c:if test="${((fn:contains(customComment.taskDefKey,'apply_audit')) and !(fn:contains(customComment.taskDefKey,'start'))and !(fn:contains(customComment.taskDefKey,'apply_end')))}">
                    <td style="line-height: 40px"><input  class="btn btn-primary" type="button" value="同 意" onclick="apply_agree()">&nbsp;
                    <input  class="btn btn-primary" type="button" value="不同意"  onclick="apply_reject()"></td>
                </c:if>
                <c:if test="${((fn:contains(customComment.taskDefKey,'apply_no_audit')))}">
                    <td style="line-height: 40px"><input  class="btn btn-primary" type="button" value="同 意" onclick="apply_no_agree()">&nbsp;
                    <input  class="btn btn-primary" type="button" value="不同意"  onclick="apply_no_reject()"></td>
                </c:if>
            </c:if>
            <c:if test="${fn:contains(customComment.taskDefKey,'start')}">
                <td style="line-height: 40px"> <input  class="btn btn-primary" type="button" value="提交申请" onclick="save();"/>
                             <input  class="btn btn-primary" type="button" value="销毁申请" onclick="destory();"/></td>
            </c:if>
        </c:if>
    </tr>
</table>
<br>
<br>
<c:if test="${not empty customProcess.id}">
    <font size="5"  color="#00bfff"><b>流转信息</b></font>
    <br>
    <table border="1" class="table table-striped table-bordered" width="83%" style="margin-bottom: 50px;">
        <thead>
        <th height="30px">执行环节</th>
        <th>执行人</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>提交意见</th>
        </thead>

        <c:forEach items="${fns:findComment(customProcess.procInstId)}" var="customHiActinst">
            <tr>
                <c:if test="${(empty customHiActinst.endTime) and(fn:contains(customHiActinst.taskDefKey,'start')) and (fn:contains(customHiActinst.name,'创建'))}">
                    <td hidden="hidden"><input type="hidden" id="destory" value="${customHiActinst.id}"></td>
                </c:if>
                <td align="center" height="20px">${customHiActinst.name}</td>
                <td align="center">${fns:getUserNameByCardNo(customHiActinst.assignee)}</td>
                <td align="center"><fmt:formatDate value="${customHiActinst.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td align="center"><fmt:formatDate value="${customHiActinst.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td align="center">${customHiActinst.comment}</td>
            </tr>

        </c:forEach>

    </table>
</c:if>
<script type="application/javascript">
    //多附件上传，非申请页面附件路径和名称不为空时，切割文件名称和文件路径，显示
    var  fjlj1= $("#FJLJ").val();
    var  fjmc1= $("#FJMC").val();
    if(fjlj1 != "" && fjmc1 != ""){
        var mc=fjmc1.split(",")
        var lj=fjlj1.split(",")

        $("#hhh").html("");
        for(var i=0;i<mc.length-1;i++){
            $("#hhh").append("附件"+[i+1]+"：<a href='" + lj[i] + "' download=''>"+mc[i]+"</a><br>");
        }
    }
    $(function(){
        $("table").each(function(i,e){
            if($(this).attr("border")=="1"){
                if($(this).find("table").length==0){
                    $(this).attr("border","1");
                    $(this).find("td").addClass("border","1px solid #333");
                }
            }
        });
    });

    //打印功能开始
    function preview() {
        bdhtml=window.document.body.innerHTML;
        zoom(parseInt($("#printSelect").val()));
        console.log($('table').eq(3).html());
        window.document.body.innerHTML=$('table').eq(3).html();
        setTimeout('yscz()',500);
    }
    function zoom(level)
    {
        var i = parseInt(document.body.style.zoom);
        if (isNaN(i)) i=100;
        newZoom= i * level / 100;
        document.body.style.zoom=newZoom+'%';
    }

    function yscz(){
        window.print();
        document.body.style.width = "100%";
        document.body.style.height = "100%";
        window.document.body.innerHTML=bdhtml;
        location.reload();
    }
    //打印功能结束

    //抄送功能开始
    function applyCS(){
        param = {
            procInstId: $("#procInstId").val(),
            flag: "4",
            comment: $("#comment").val(),
            id:$("#custRuskTaskinstId").val(),
            taskDefKey:$("#taskDefKey").val()
        }
        $.ajax({
            type: 'get',
            url: '${ctx}/process/customProcess/saveCsAudit',
            data: param,
            success: function (data) {
                if(data.code == "success"){
                    window.location="${ctx}/process/customProcess/listAudit?flag=2"
                }
            }
        })
    }
    //抄送功能结束

    //销毁申请开始
    function destory(){
        param = {
            procInstId: $("#procInstId").val(),
            flag: "3",
            userId :$("#destory").val()
        }
        $.ajax({
            type: 'get',
            url: '${ctx}/process/customProcess/destoryAudit',
            data: param,
            success: function (data) {
                if(data.code == "success"){
                    window.location="${ctx}/process/customProcess/listAudit?flag=3"
                }
            }
        })
//        alert("销毁申请成功");
        //再销毁申请的时候，流程直接结束。
    }
    //销毁申请结束

    //保存自由流程信息
    var i = 1;
    //提交流程信息执行的保存
    function save() {
        //首先先判断当前的实例id在流转设定中是否有值，
        // 如果有值则可提交，否者不可提交
        var procInstId = $("#procInstId").val();
        var content = $("#content").val();
        if(content != "" && content != null){
            $.ajax({
                type: 'get',
                url: '${ctx}/process/transferSet/listAllView?procInstId='+procInstId,
                success: function (data) {
                    if(data && data.length>0){
                        $("#inputForm").submit();
                    }else{
                        $('#myModal').modal('show');
                        alert("没有设定流转信息，请设定！")
                    }
                }
            })
        }else{
            alert("没有内容不能提交自由流程！")
        }

    }
    //同意审批流程信息执行的保存
    function agreeAudit(){
        param = {
            procInstId: $("#procInstId").val(),
            flag: "2",
            comment: $("#comment").val(),
            id:$("#custRuskTaskinstId").val(),
            taskDefKey:$("#taskDefKey").val()
        }
        $.ajax({
            type: 'post',
            url: '${ctx}/process/customProcess/saveAudit',
            data: param,
            success: function (data) {
                if(data.code == "success"){
                    window.location="${ctx}/process/customProcess/listAudit?flag=1"
                }
            }
        })
    }


    //归档同意审批流程信息执行的保存
    function agree(){
        html2canvas(document.getElementById('cavas'),{
            allowTaint: true,
            taintTest: false,
            onrendered: function(canvas) {
                canvas.id = "mycanvas";
                //document.body.appendChild(canvas);
                //生成base64图片数据
                var dataUrl = canvas.toDataURL();
                console.log(dataUrl);
                $("#tbodyHtml").val(dataUrl);
                param = {
                    procInstId: $("#procInstId").val(),
                    flag: "2",
                    comment: $("#comment").val(),
                    id:$("#custRuskTaskinstId").val(),
                    taskDefKey:$("#taskDefKey").val(),
                    tbodyHtml:$("#tbodyHtml").val()
                }
                $.ajax({
                    type: 'post',
                    url: '${ctx}/process/customProcess/saveAudit',
                    data: param,
                    success: function (data) {
                        if(data.code == "success"){
                            window.location="${ctx}/process/customProcess/listAudit?flag=1"
                        }
                    }
                })
                return true;
            }
        });
    }

    //驳回流程信息执行的保存
    function reject(){
        param = {
            procInstId: $("#procInstId").val(),
            flag: "0",
            comment: $("#comment").val(),
            id:$("#custRuskTaskinstId").val(),
            taskDefKey:$("#taskDefKey").val()
        }
        $.ajax({
            type: 'get',
            url: '${ctx}/process/customProcess/saveRejectAudit',
            data: param,
            success: function (data) {
                if(data.code == "success"){
                    window.location="${ctx}/process/customProcess/listAudit?flag=0"
                }
            }
        })
    }
    //同意流程信息执行的保存
    function apply_agree(){
        param = {
            procInstId: $("#procInstId").val(),
            flag: "2",
            comment: $("#comment").val(),
            id:$("#custRuskTaskinstId").val(),
            taskDefKey:$("#taskDefKey").val()
        }
        $.ajax({
            type: 'get',
            url: '${ctx}/process/customProcess/saveApplyAudit',
            data: param,
            success: function (data) {
                if(data.code == "success"){
                    window.location="${ctx}/process/customProcess/listAudit?flag=1"
                }
            }
        })
    }

    //不同意流程信息执行的保存
    function apply_reject(){
        param = {
            procInstId: $("#procInstId").val(),
            flag: "3",
            comment: $("#comment").val(),
            id:$("#custRuskTaskinstId").val(),
            taskDefKey:$("#taskDefKey").val()
        }
        $.ajax({
            type: 'get',
            url: '${ctx}/process/customProcess/saveApplyAudit',
            data: param,
            success: function (data) {
                if(data.code == "success"){
                    window.location="${ctx}/process/customProcess/listAudit?flag=0"
                }
            }
        })
    }


    //非会签流程信息执行的保存
    function apply_no_agree(){
        param = {
            procInstId: $("#procInstId").val(),
            flag: "2",
            comment: $("#comment").val(),
            id:$("#custRuskTaskinstId").val(),
            taskDefKey:$("#taskDefKey").val()
        }
        $.ajax({
            type: 'get',
            url: '${ctx}/process/customProcess/saveApplyNoAudit',
            data: param,
            success: function (data) {
                if(data.code == "success"){
                    window.location="${ctx}/process/customProcess/listAudit?flag=1"
                }
            }
        })
    }

    //非会签不同意流程信息执行的保存
    function apply_no_reject(){
        param = {
            procInstId: $("#procInstId").val(),
            flag: "3",
            comment: $("#comment").val(),
            id:$("#custRuskTaskinstId").val(),
            taskDefKey:$("#taskDefKey").val()
        }
        $.ajax({
            type: 'get',
            url: '${ctx}/process/customProcess/saveApplyNoAudit',
            data: param,
            success: function (data) {
                if(data.code == "success"){
                    window.location="${ctx}/process/customProcess/listAudit?flag=1"
                }
            }
        })
    }

    //为动态行每一行数据初始化一个空对象
    var obj = {
        nodeName: '',
        nodeType: '',
        handlePerson: '',
        handlePersonId: '',
        taskDefKey:'',
        flag:'',
        procInstId: ''
    }
    //为更新列表定义参数
    var params = {}

    //初始化页面
    listView();

    //后台查询列表
    function listView(params) {
        $("#s").val(2)
        var id = $("#emptyId").val();
        if(id != null && id !=""){
            $.ajax({
                type:'post',
                data:params,
                url:'${ctx}/process/transferSet/listAllView?procInstId='+$("#procInstId").val(),
                success:function(data){
                    if(data && data.length > 0){
                        for(var i=0; i < data.length;i++){
                            if(data[i].flag == "1"){
                                addEntity(data[i]);
                            }else{
                                addEntity(data[i]);
                            }

                        }
                    }else{
                        addEntity(obj);
                    }
                }
            })
        }else{
            $.ajax({
                type:'post',
                data:params,
                url:'${ctx}/process/transferSet/listView?procInstId='+$("#procInstId").val(),
                success:function(data){
                    if(data && data.length > 0){
                        for(var i=0; i < data.length;i++){
                            if(!(data[i].nodeType == "归档")){
                                addEntity(data[i]);
                            }
                        }
                    }else{
                        addEntity(obj);
                    }
                }
            })
        }
    }
    var count = 0;
    //动态添加行以及在下一步人时回显流转信息
    function addEntity(o, b) {
        var nodeName = o.nodeName == "" ? "节点名称"+i : o.nodeName
        var nodeType = o.nodeType == 0 ? 1 : o.nodeType
        var flag = o.flag == 0 ? 0 : o.flag
        var handlePerson = o.handlePerson == 0 ? "" : o.handlePerson
        var userName = $("#userName").val();
        if(flag == "1" && nodeType == "开始"){
            var tbody = ['<tr class="entry_item">',
                '<td><input style="width: 100%;height: 100%" readonly  class="nodeName"   value="' + nodeName + '"/></td> ',
                '<td><span id="node_type_1">开始</span><input class="nodeType" id="node_type_hidden_1" type="hidden" value="'+nodeType+'"></td>',
                '<td><input readonly class="handlePerson" id="nodeId_show_1" value="'+handlePerson+'"></td>',
                '<td hidden=""><input id="nodeId_1" type="hidden" value="'+ o.handlePersonId+'"></td>',
                '<td hidden=""><input id="task_def_key_1" type="hidden" value="'+ o.taskDefKey+'"></td>',
                '<td hidden=""><input id="flag_1" type="hidden" value="'+ flag+'"/></td>',
                '<td ></td>',
                "</tr>"].join("")
        }else if(flag == 1 && nodeType != "开始"){
            var tbody = ['<tr class="entry_item">',
                '<td><input readonly  class="nodeName"   value="' + nodeName + '"/></td> ',
                '<td><select disabled="disabled" id="node_type_1" onchange="changeInfo(' + (i + 1) + ')"><option value ="1" onChange="changeInfo(' + (i + 1) + ')">审批</option><option value ="2" onChange="changeInfo(' + (i + 1) + ')">会签</option><option value="3"  onChange="changeInfo(' + (i + 1) + ')">非会签</option><option value="4"  onChange="changeInfo(' + (i + 1) + ')">抄送</option></select><input class="nodeType" id="node_type_hidden_1" type="hidden" value="'+nodeType+'"></td>',
                '<td><input readonly class="handlePerson" id="nodeId_show_1" value="'+handlePerson+'"></td>',
                '<td hidden=""><input id="nodeId_1" type="hidden" value="'+ o.handlePersonId+'"></td>',
                '<td hidden=""><input id="task_def_key_1" type="hidden" value="'+ o.taskDefKey+'"></td>',
                '<td hidden=""><input id="flag_1" type="hidden" value="'+ flag+'"/></td>',
                '<td ></td>',
                "</tr>"].join("")
        }else  if(flag == "0" && nodeType != "开始" && handlePerson != null && handlePerson.indexOf(userName)>=0){
            var tbody = ['<tr class="entry_item">',
                '<td><input readonly  class="nodeName"   value="' + nodeName + '"/></td> ',
                '<td><select disabled="disabled" id="node_type_1" onchange="changeInfo(' + (i + 1) + ')"><option value ="1" onChange="changeInfo(' + (i + 1) + ')">审批</option><option value ="2" onChange="changeInfo(' + (i + 1) + ')">会签</option><option value="3"  onChange="changeInfo(' + (i + 1) + ')">非会签</option><option value="4"  onChange="changeInfo(' + (i + 1) + ')">抄送</option></select><input class="nodeType" id="node_type_hidden_1" type="hidden" value="'+nodeType+'"></td>',
                '<td><input readonly class="handlePerson" id="nodeId_show_1" value="'+handlePerson+'"></td>',
                '<td hidden=""><input id="nodeId_1" type="hidden" value="'+ o.handlePersonId+'"></td>',
                '<td hidden=""><input id="task_def_key_1" type="hidden" value="'+ o.taskDefKey+'"></td>',
                '<td hidden=""><input id="flag_1" type="hidden" value="'+ flag+'"/></td>',
                '<td ></td>',
                "</tr>"].join("")
        }else if(nodeType == "归档"){
            var tbody = ['<tr class="entry_item">',
                '<td><input readonly  class="nodeName"   value="' + nodeName + '"/></td> ',
                '<td><span id="node_type_1">归档</span><input class="nodeType" id="node_type_hidden_1" type="hidden" value="'+nodeType+'"></td>',
                '<td><input readonly class="handlePerson" id="nodeId_show_1" value="'+handlePerson+'"></td>',
                '<td hidden=""><input id="nodeId_1" type="hidden" value="'+ o.handlePersonId+'"></td>',
                '<td hidden=""><input id="task_def_key_1" type="hidden" value="'+ o.taskDefKey+'"></td>',
                '<td hidden=""><input id="flag_1" type="hidden" value="'+ flag+'"/></td>',
                '<td ></td>',
                "</tr>"].join("")
        }else  if(flag == "0" && nodeType == "开始"){
            var tbody = ['<tr class="entry_item">',
                '<td><input style="width: 100%;height: 100%" readonly  class="nodeName"   value="' + nodeName + '"/></td> ',
                '<td><span id="node_type_1">开始</span><input class="nodeType" id="node_type_hidden_1" type="hidden" value="'+nodeType+'"></td>',
                '<td><input readonly class="handlePerson" id="nodeId_show_1" value="'+handlePerson+'"></td>',
                '<td hidden=""><input id="nodeId_1" type="hidden" value="'+ o.handlePersonId+'"></td>',
                '<td hidden=""><input id="task_def_key_1" type="hidden" value="'+ o.taskDefKey+'"></td>',
                '<td hidden=""><input id="flag_1" type="hidden" value="'+ flag+'"/></td>',
                '<td ><a href="#"  onclick="addEntity(obj,this)">添加</a><a href="#" onclick="removeEntity(this)">删除</a></td>',
                "</tr>"].join("")
        }else{
            var tbody = ['<tr class="entry_item">',
                '<td><input  class="nodeName"   value="' + nodeName + '"/></td> ',
                '<td><select id="node_type_1" onchange="changeInfo(' + (i + 1) + ')"><option value ="1" onChange="changeInfo(' + (i + 1) + ')">审批</option><option value ="2" onChange="changeInfo(' + (i + 1) + ')">会签</option><option value="3"  onChange="changeInfo(' + (i + 1) + ')">非会签</option><option value="4"  onChange="changeInfo(' + (i + 1) + ')">抄送</option></select><input class="nodeType" id="node_type_hidden_1" type="hidden" value="'+nodeType+'"></td>',
                '<td><input class="handlePerson" onclick="handlePerson(' + (i + 1) + ')" id="nodeId_show_1" value="'+handlePerson+'"></td>',
                '<td hidden=""><input id="nodeId_1" type="hidden" value="'+ o.handlePersonId+'"></td>',
                '<td hidden=""><input id="task_def_key_1" type="hidden" value="'+ o.taskDefKey+'"></td>',
                '<td hidden=""><input id="flag_1" type="hidden" value="'+ flag+'"/></td>',
                '<td ><a href="#"  onclick="addEntity(obj,this)">添加</a>&nbsp;<a href="#" onclick="removeEntity(this)">删除</a></td>',
                "</tr>"].join("")
        }
        b ? $(b).parents('.entry_item').after(tbody) : $("#contentTableTbody").append(tbody);
        $("#node_type_1").val(nodeType)

        document.getElementById("nodeId_1").id = "nodeId_" + (i + 1);
        document.getElementById("flag_1").id = "flag_" + (i + 1);
        document.getElementById("task_def_key_1").id = "task_def_key_" + (i + 1);
        document.getElementById("node_type_1").id = "node_type_" + (i + 1);
        document.getElementById("node_type_hidden_1").id = "node_type_hidden_" + (i + 1);
        document.getElementById("nodeId_show_1").id = "nodeId_show_" + (i + 1);
        i = i + 1;
    }

    //显示处理人的树形结构
    function handlePerson(u) {
        $("#table").val(u);//saveHandlePerson(u)方法中的u既是取得这个值
        $("#handlePersonId").val($("#nodeId_" + u).val())
        $("#handlePersonName").val($("#nodeId_show_" + u).val())
        $('#myModal1').modal('show');
    }

    //点击处理人填写完成后，点击保存按钮，将处理人的id和姓名保存在table表格中
    function saveHandlePerson(u) {
        var nodeType = $("#node_type_hidden_"+u).val()
        if(nodeType == "1"){
            $("#nodeId_" + u).val($("#handlePersonId").val())
            var nodeId = $("#nodeId_"+u).val().split(",");
            if(nodeId.length > 1){
                alert("当节点类型为'审批'时,只能设置一个审批人");
                return false;
            }
        }
        $("#nodeId_" + u).val($("#handlePersonId").val())
        $("#nodeId_show_" + u).val($("#handlePersonName").val())
        if($("#node_type_hidden_" + u).val() == "1"){
            $("#task_def_key_" + u).val("audit"+u);
        }
        $('#myModal1').modal('hide');

    }

    function changeInfo(u) {
        $("#node_type_" + u).val();
        $("#node_type_hidden_" + u).val($("#node_type_" + u).val());
        if($("#node_type_hidden_" + u).val() == "1"){
            $("#task_def_key_" + u).val("audit"+u);
        }
        if($("#node_type_hidden_" + u).val() == "2"){
            $("#task_def_key_" + u).val("apply_audit"+u);
        }
        if($("#node_type_hidden_" + u).val() == "3"){
            $("#task_def_key_" + u).val("apply_no_audit"+u);
        }
        if($("#node_type_hidden_" + u).val() == "4"){
            $("#task_def_key_" + u).val("apply_cs"+u);
        }
    }

    //删除一条节点记录，并将该行移除
    function removeEntity(o) {
        var ids = $(o).parents('tr').children('td').eq(7).children('input').val();
        $(o).parents('tr').remove();
        keepOne();
    }

    //始终保证页面有一行
    function keepOne() {
        if ($(" .entry_item").length < 1)
            addEntity(obj);
    }

    //保存流转设定数据信息
    function save1() {
        var procInstId = $("#procInstId").val();
        var x = document.getElementById('contentTables').rows;//获得行数(包括thead)
        var data = [];
        for (var i = 1; i < x.length; i++) {
            var obj = new Object();
            obj.nodeName = x[i].cells[0].getElementsByTagName("input")[0].value;
            obj.nodeType = x[i].cells[1].getElementsByTagName("input")[0].value;
            obj.handlePerson = x[i].cells[2].getElementsByTagName("input")[0].value;
            obj.handlePersonId = x[i].cells[3].getElementsByTagName("input")[0].value;
            obj.taskDefKey = x[i].cells[4].getElementsByTagName("input")[0].value;
            obj.flag = x[i].cells[5].getElementsByTagName("input")[0].value;
            obj.procInstId = procInstId;
            data.push(obj);
            if(obj.handlePerson == "" || obj.handlePerson == null){
                alert("处理人不能为空，请设定后再保存！")
                return;
            }
        }
        console.log(data);
        ///格式化数据
        var obj = JSON.stringify(data);//这一行很关键
        $.ajax({
            url: "${ctx}/process/transferSet/save",
            type: "post",
            data: {'param': obj},//,//参数
            datatype: "json",
            success: function (data) {
                if (data.code == "success") {
                    $('#myModal').modal('hide');
                    alert("流转信息设定成功！")
                }
            }
        });
    }

    function toProcess(e) {
//        e.preventDefault;
//        console.log($("#right")[]);
//        alert(11111);
        $('#myModal').modal('show');
    }

</script>
</body>
</html>