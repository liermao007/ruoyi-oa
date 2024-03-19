<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>离职人员提醒</title>
    <meta name="decorator" content="default"/>
    <style>
        .table-striped.table-bordered.table-condensed  th{
            text-align: center;
        }

        table th,table td{
            text-align: center;
        }
        </style>
    <script>
        function updateLeave(key,id,loginName){
            var hrmFlay=document.getElementById("ccid_"+loginName+"_"+key+"Id").value;
            $.ajax({
                type: "post",
                url: "${ctx}/sys/user/updateLeave?key="+key+"&id="+id+"&loginName="+loginName+"&hrmFlay="+hrmFlay,
                success: function(data){
                }
            });
        }
        function sel(a){
            o=document.getElementsByName(a)
            for(i=0;i<o.length;i++)
                o[i].checked=event.srcElement.checked
            var checked = false;
        }
        function updateLeave(key,id,loginName,d) {
            var checked = false;
            var ids = document.getElementsByName(d);
            var chestr = "";
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                    chestr += "'"+ids[i].value +"'"+ ",";
                }
            }
            chestr=chestr.substring(0,chestr.length-1)
            var hrmFlay=document.getElementById("ccid_"+loginName+"_"+key+"Id").value;
            $.ajax({
                type: "post",
                url: "${ctx}/sys/user/updateLeave1?key="+key+"&id="+id+"&loginName="+loginName+"&hrmFlay="+hrmFlay+"&ids="+chestr,
                success: function(data){
                }
            });
        }
    </script>
</head>
<body>
<sys:message content="${message}"/>
<table id="contentTable" >
    <c:forEach items="${map}" var="entry">
        <fieldset>
            <table align="center" width="100%">
                <c:if test="${entry.key == '2'}">
                    <legend>直属领导</legend>
                    <thead class="table table-striped table-bordered table-condensed"><tr><th><input width="2%" type="checkbox" onclick=sel('ccid_${entry.key}')  id="ccid_${entry.key}"/></th><th  width="20%">姓名</th><th width="20%">直属领导</th><th width="20%">所在部门</th><th width="20%">职务替代人员姓名</th><th width="18%">操作</th></tr></thead>
                </c:if>
                <c:if test="${entry.key == '3'}">
                    <legend>部门领导</legend>
                    <thead class="table table-striped table-bordered table-condensed"><tr><th><input width="2%" type="checkbox" onclick=sel('ccid_${entry.key}')  id="ccid_${entry.key}"/></th><th width="20%">姓名</th><th width="20%">部门领导</th><th wwidth="20%">所在部门</th><th width="20%">职务替代人员姓名</th><th width="18%">操作</th></tr></thead>
                </c:if>
                <c:if test="${entry.key == '4'}">
                    <legend>一级领导</legend>
                    <thead class="table table-striped table-bordered table-condensed"><tr><th><input width="2%" type="checkbox" onclick=sel('ccid_${entry.key}')  id="ccid_${entry.key}"/></th><th width="20%">姓名</th><th width="20%">一级领导</th><th width="20%">所在部门</th><th width="20%">职务替代人员姓名</th><th width="18%">操作</th></tr></thead>
                </c:if>
                <tbody>
                <c:forEach items="${entry.value }" var="user" varStatus="stuts">
                    <tr>
                        <td width="2%"><input type="checkbox" name="ccid_${entry.key}" value="${user.id}"></td>
                        <td width="20%"><label>${user.name}</label></td>
                        <td width="20%"><label>${name}</label></td>
                        <td width="20%"><label>${user.officeName}</label></td>
                        <c:if test="${stuts.index==0}">
                        <td  width="20%" >
                            <sys:treeselect id="ccid_${user.loginName}_${entry.key}" name="hrmFlay"
                                            value="${mailInfo.ccId}" labelName="name" labelValue="${mailInfo.ccNames}"
                                            title="用户" url="/sys/office/treeData?type=3"  cssStyle="width:35%"/>
                        <td width="15%">
                            <%--<a href=""  onclick="updateLeave(${entry.key},${user.id},${user.loginName})">保存</a>--%>
                            <input type="submit" class="btn btn-success" value="保存" onclick="updateLeave('${entry.key}','${user.id}','${user.loginName}','ccid_${entry.key}')">　
                        </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </fieldset>
        <br /><br />
    </c:forEach>
    <tr >
     <td >
     <a href="javascript:"   onclick="history.go(-1);" class="btn" style="float: right;margin: 0 500px 0 10px;">返回上一页</a> &nbsp;
     </td>
    </tr>

</table>
</body>
</html>