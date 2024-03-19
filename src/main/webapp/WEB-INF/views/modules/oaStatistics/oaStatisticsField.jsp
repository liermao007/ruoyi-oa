<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2018/1/8
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>修改配置</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<script type="text/javascript">
   function aaa(i) {
       var b=$("#td3"+i).html();
       var id=$("#id"+i).val();
       var a=$("#td4"+i).html();
       $("#tr"+i).remove();
       $("#tab1").append("<tr id='selectTr"+i+"' name='selected'>" +
           "<input type='hidden' name='saveId' id='id"+i+"' value='"+id+"'>" +
           "<td class='td1'><span id='td1"+i+"'>"+"<input type='text' name='rank' class='rank'></span></td>" +
           "<td class='td2'><span id='td2"+i+"'>"+b+"</td>"+
           "<td class='td6'><span id='td6"+i+"'>"+a+"</td>"+
           "<td class='delete'></span><input type='button' onclick='bbb(\""+i+"\")' id='but"+i+"' value='删除'></td>" +
           "</tr>");
       sortNumber();
   }

   function bbb(i) {
       var a=$("#td2"+i).html();
       var id=$("#id"+i).val();
       var b=$("#td6"+i).html();
       $("#selectTr"+i).remove();
       $("#tab2").append("<tr name='optional' id='tr"+i+"'>" +
           "<input type='hidden' id='id"+i+"' value='"+id+"'>" +
           "<td class='tdCheck'><input id='check"+i+"' onclick='bbb(\""+i+"\")' type='checkbox'></td>" +
           "<td class='td3'><span id='td3"+i+"'>"+a+"</span></td>" +
           "<td class='td4'><span id='td4"+i+"'>"+b+"</span></td>" +
           "</tr>");
       sortNumber();
   }

//   序号编排方法
   function sortNumber() {
       var allInput = $("#tab1").find("input[name='rank']");
       if(allInput.length != 0){
           for(var i=0;i<allInput.length;i++){
               var tempInput = allInput[i];
               tempInput.value = i + 1;
           }
       }
   }

   //
   $(document).ready(function(){
       $("tab1").css("w")
   });
</script>


<%------------------------------------------------------------------%>
<sys:message content="${message}"/>
<form id="form" action="${ctx}/OaStatistics/oaStatistics/updateField" method="post">
<table id="contentTable" class="table table-striped table-bordered table-condensed" style="">
        <input type="hidden" name="TJId" value="${oaStatisticsTable.statisticsId}">
        <input type="hidden" name="tableId" value="${oaStatisticsTable.id}">
    <tbody>
    <tr>
        <td width="50%">已有字段</td>
        <td width="50%">可选字段</td>
    </tr>
    <tr>
        <td style="vertical-align:top" >
            <table id="tab1">
                <tr>
                    <td>排序</td>
                    <td>字段名称</td>
                    <td>数据列</td>
                    <td>操作</td>
                </tr>
                <c:forEach items="${list}" var="oaStatisticsField">
                    <c:if test="${oaStatisticsField.flag == 0}">
                        <tr id="selectTr${oaStatisticsField.id}" name="selected">
                            <input type="hidden" name="saveId" id="id${oaStatisticsField.id}" value="${oaStatisticsField.id}">
                            <td class="td1"><span id="td1${oaStatisticsField.id}"><input type="text" name="rank" value="${oaStatisticsField.serial}" class="rank" placeholder="请输入数字" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"></span></td>
                            <td class="td2"><span id="td2${oaStatisticsField.id}">${oaStatisticsField.remarks}</span></td>
                            <td class="td6"><span id="td6${oaStatisticsField.id}">${oaStatisticsField.fieldName}</span></td>
                            <td class="delete"><input type="button" onclick="bbb('${oaStatisticsField.id}')" class="but1" id="but${oaStatisticsField.id}" value="删除"></td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </td>
        <td style="vertical-align:top" >

            <table id="tab2">
                <tr>
                    <td></td>
                    <td>字段名称</td>
                    <td>数据列</td>
                </tr>
                <c:forEach items="${list}" var="oaStatisticsField">
                    <c:if test="${oaStatisticsField.flag == 1}">
                        <c:if test="${oaStatisticsField.fieldName != 'id' && oaStatisticsField.fieldName != 'create_by' && oaStatisticsField.fieldName != 'create_date' && oaStatisticsField.fieldName == 'update_by' == oaStatisticsField.fieldName != 'update_date' && oaStatisticsField.fieldName != 'remarks' && oaStatisticsField.fieldName != 'del_flag' && oaStatisticsField.fieldName != 'proc_ins_id' && oaStatisticsField.fieldName != 'proc_def_id'}">
                        <tr name="optional" id="tr${oaStatisticsField.id}">
                            <input type="hidden" id="id${oaStatisticsField.id}" value="${oaStatisticsField.id}">
                            <td class="tdCheck"><input id="check${oaStatisticsField.id}" onclick="aaa('${oaStatisticsField.id}')" type="checkbox"></td>
                            <td class="td3"><span id="td3${oaStatisticsField.id}">${oaStatisticsField.remarks}</span></td>
                            <td class="td4"><span id="td4${oaStatisticsField.id}">${oaStatisticsField.fieldName}</span></td>
                        </tr>
                        </c:if>
                    </c:if>
                </c:forEach>
            </table>
        </td>
    </tr>
    <tr>
        <td style="margin-bottom: 100px" colspan="2">
                <input type="submit" class="btn btn-primary" value="保存">
            <input type="button" id="getBack" class="btn" onclick="history.go(-1)" value="返回">
        </td>
    </tr>
    <tr style="height: 100px">
    </tr>
    </tbody>
</table>
</form>
</body>
</html>
