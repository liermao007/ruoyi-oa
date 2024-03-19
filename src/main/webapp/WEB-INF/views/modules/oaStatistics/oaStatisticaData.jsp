<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2018/1/19
  Time: 8:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>统计表数据</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<script type="text/javascript">
    var fields=$('td [name=td1]').children().length;
    var width=1/fields;
    $("td").css("width",width)
</script>



   <ul class="breadcrumb">
       <li class="active"><a href="${ctx}/oa/leaveApplication/">请假单统计列表</a></li>
       <li><a href="${ctx}/oa/leaveApplication/form">请假单统计添加</a></li>
   </ul>
   <form:form id="searchForm" modelAttribute="leaveApplication" action="${ctx}/oa/leaveApplication?tableName=${tableName}&type=${type}" method="post" class="breadcrumb form-search">
       <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
       <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
       <ul class="ul-form">
               <%--<li><label>请假类型：</label>--%>
               <%--<form:select path="qjlx" class="input-xlarge">--%>
               <%--<form:option value="" label="请选择"/>--%>
               <%--<form:options items="${fns:getDictList('oa_leave_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
               <%--</form:select>--%>
               <%--</li>--%>


           <li><label>开始日期：</label>
               <input name="qjksrq" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                      value="<fmt:formatDate value="${leaveApplication.qjksrq}" pattern="yyyy-MM-dd"/>"
                      onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
           </li>
           <li><label>结束日期：</label>
               <input name="qjjsrq" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                      value="<fmt:formatDate value="${leaveApplication.qjjsrq}" pattern="yyyy-MM-dd"/>"
                      onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
           </li>
           <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
           <li class="btns"> <input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
           <li class="clearfix"></li>
       </ul>
   </form:form>
   <sys:message content="${message}"/>
   <table id="contentTable" class="table table-striped table-bordered table-condensed">
       <thead>
       <tr>
           <c:forEach items="${fields}" var="field">
               <th>${field.remarks}</th>
           </c:forEach>
           <th style="min-width: 100px; ">操作</th>
       </tr>
       </thead>
       <tbody>
       <c:forEach items="${list}" var="data"  begin="0" end="19">
           <tr>
               <c:forEach items="${fields}" var="zd" varStatus="i">
               <c:set var='zd' value="${zd.fieldName}" scope="page"/>
               <td>
                       ${data[zd]}
               </td>
               </c:forEach>
               <td>
                   <a href="">查看详情</a>
               </td>
           </tr>
       </c:forEach>
       </tbody>
   </table>
   <div class="pagination">
       <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
   </div>
</body>
</html>
