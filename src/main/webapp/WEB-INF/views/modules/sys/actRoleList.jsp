<%--
]
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>角色管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
            var data = ${fns:toJson(list)}, rootId = "${not empty office.parentId ? office.parentId : '0'}";
            addRow("#treeTableList", tpl, data, rootId, true);
            $("#treeTable").treeTable({expandLevel: 5});
        });

        function addRow(list, tpl, data, pid, root) {
            for (var i = 0; i < data.length; i++) {
                var row = data[i];
                if ((${fns:jsGetVal('row.parentId')}) == pid) {
                    $(list).append(Mustache.render(tpl, {
                        dict: {
                            type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)
                        }, pid: (root ? 0 : pid), row: row
                    }));

                    addRow(list, tpl, data, row.id);
                }
            }
        }

    </script>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<shiro:hasPermission name="sys:role:edit">
    <div class="breadcrumb">
        <a href="${ctx}/sys/role/form" role="button" class="btn btn-primary">添加职务</a>
        <a href="${ctx}/sys/role/saveRole" role="button" class="btn btn-primary">同步职务</a>
        <a href="${ctx}/sys/role/saveRolePeop" role="button" class="btn btn-primary">同步角色对应人员表</a>
    </div>
</shiro:hasPermission>
<sys:message content="${message}"/>
<div>

    <div>
        <table id="treeTable" class="table table-striped table-bordered table-condensed"
               style="width:60% ;float: left;">
            <thead>
            <tr>
                <th>部门名称</th>
                <th>部门类型</th>
            </tr>
            </thead>
            <tbody id="treeTableList"></tbody>
        </table>
        <script type="text/template" id="treeTableTpl">

            <tr id="{{row.id}}" pId="{{pid}}">

                &lt;%&ndash;<td onclick="selectRole('{{row.id}}',this.parentNode.parentNode)">&lt;%&ndash;<a href="${ctx}/sys/role/findRoleByDept?id=">&ndash;%&gt;{{row.name}}&lt;%&ndash;</a>&ndash;%&gt;</td>&ndash;%&gt;
                <td onclick="selectRole('{{row.id}}',this)">&lt;%&ndash;<a href="${ctx}/sys/role/findRoleByDept?id=">&ndash;%&gt;{{row.name}}&lt;%&ndash;</a>&ndash;%&gt;</td>
                &lt;%&ndash;<td>{{row.area.name}}</td>&ndash;%&gt;
                &lt;%&ndash;<td>{{row.code}}</td>&ndash;%&gt;
                <td>{{dict.type}}</td>
                &lt;%&ndash;<td>{{row.remarks}}</td>&ndash;%&gt;
                &lt;%&ndash; <shiro:hasPermission name="sys:office:edit"><td>
                     <a href="${ctx}/sys/office/form?id={{row.id}}">修改</a>
                     <a href="${ctx}/sys/office/delete?id={{row.id}}" onclick="return confirmx('要删除该部门及所有子部门项吗？', this.href)">删除</a>
                         &lt;%&ndash;<a href="${ctx}/sys/office/form?parent.id={{row.id}}">添加下级部门</a>&ndash;%&gt;
                 </td></shiro:hasPermission>&ndash;%&gt;
            </tr>
        </script>
    </div>
    <div>
        <table id="treeTable1" class="table table-striped table-bordered table-condensed" style="width:40%;">
            <thead>
            <tr>
                <th>角色名称</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tog">


            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    function selectRole(id, ele) {
//        $("#"+id)[0].style.backgroundColor = "red";
        $.ajax({
            url: "${ctx}/sys/role/findRoleByDept?id=" + id,
            type: "get",
            success: function (data) {
                $("#tog").html("");
                var id;
                for (var i = 0; i < data.length; i++) {
                    if (data != null) {
                        id = data[i].id;
                        $("#tog").append("<tr><td>" + data[i].name + "</td><td><a href=${ctx}/sys/role/assign?id=" + id + ">分配</a>&nbsp;<a href=${ctx}/sys/role/form?id=" + id + ">修改</a>&nbsp;<a href=${ctx}/sys/role/delete?id=" + id + " onclick='return confirmx('确认要删除该角色吗？', this.href)'>删除</a></td></tr>");
                    }else{

                    }
                }
            }
        });
        var table = document.getElementById("treeTable")
        //var table = document.getElementById("t1").rows[0].cells[0];
        var rowObj = null;
        var cellObj = null;

        // 引用rows
        for (var i = 0; i < table.rows.length; i++) {
            rowObj = table.rows[i];
            for (var j = 0; j < rowObj.cells.length; j++) {
                rowObj.cells[j].style.color = "#555";
            }

        }

//        var rows = document.all.treeTable.rows;
//        for (var i=0; i < rows.length; i++) {
//            rows[i].style.color = "#555";
////            if(i % 2 == 0){//偶数行背景色为红色  
////                rows[i].style.backgroundColor = 'yellow';
////            }//基数行背景色为蓝色  
////            else rows[i].style.backgroundColor = "blue";
//        }
        $(ele).css("color", "red");
//        $("#"+id).css('background','red');

    }
</script>
</body>
</html>--%>


<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>角色管理</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr><th>职务名称</th><th>英文名称</th><th>归属公司</th><th>数据范围</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
    <c:forEach items="${list}" var="role">
        <tr>
            <td><a href="${ctx}/statistics_act_role/actRole/form?roleId=${role.id}&roleName=${role.name}">${role.name}</a></td>
            <td><a href="${ctx}/statistics_act_role/actRole/form?roleId=${role.id}&roleName=${role.name}">${role.enname}</a></td>
            <td>${role.office.name}</td>
            <td>${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</td>
            <shiro:hasPermission name="sys:role:edit"><td>
                <c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
                    <a href="${ctx}/statistics_act_role/actRole/form?roleId=${role.id}&roleName=${role.name}">修改</a>
                </c:if>

            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
</table>
</body>
</html>
