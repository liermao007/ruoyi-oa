<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色流程对应管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

            $(function(){
                var boxObj = $("input:checkbox[name='choice']"); //获取所有的复选框值
                var expresslist = '${certDlStatusList}'; //用el表达式获取在控制层存放的复选框的值为字符串类型
                var express = expresslist.split(',');
                $.each(express, function(index, expressId){
                    boxObj.each(function () {
                        if($(this).val() == expressId) {
                            $(this).attr("checked",true);
                        }
                    });
                });
            })
		});
	</script>

    <script>



        //点击全选，子复选框被选中
        function demo(){
            var allcheck=document.getElementById("allcheck");
            var choice=document.getElementsByName("choice");
            for(var i=0;i<choice.length;i++){
                choice[i].checked=allcheck.checked;
            }
        }

        //点击子复选框,全选框 选中、取消
        function setAll(){
            if(!$(".checknum").checked){
                $("#allcheck").prop("checked",false); // 子复选框某个不选择，全选也被取消
            }
            var choicelength=$("input[type='checkbox'][class='checknum']").length;
            var choiceselect=$("input[type='checkbox'][class='checknum']:checked").length;

            if(choicelength==choiceselect){
                $("#allcheck").prop("checked",true);   // 子复选框全部部被选择，全选也被选择；1.对于HTML元素我们自己自定义的DOM属性，在处理时，使用attr方法；2.对于HTML元素本身就带有的固有属性，在处理时，使用prop方法。
            }

        }
        function deleteBy() {
            var checked = false;
            var allcheck=document.getElementById("allcheck");
            var ids = document.getElementsByName("choice");
            var chestr = "";
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                    ids[i].checked=allcheck.checked;
                    chestr += ids[i].value + ",";
                }
            }
            chestr=chestr.substring(0,chestr.length-1)
            if(chestr.length == 0){
                window.location.href = "${ctx}/statistics_act_role/actRole/save?roleId=${actRole.roleId}&ids";
            }else{

                window.location.href = "${ctx}/statistics_act_role/actRole/save?roleId=${actRole.roleId}&ids=" + chestr;
            }
        }



    </script>
</head>
<body>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <span style="color: #2fa4e7">${actRole.remarks}/${actRole.roleName}</span>
    <a href="javascript:" onclick="history.go(-1);" class="btn" style="float: right;margin: 0 20px 0 10px;">返回上一页</a> &nbsp;
    <input align="right" type="button" value="确定" class="btn btn-success ;" onclick="deleteBy()" style="float: right;">&nbsp;

    <tr>
        <th> <input type="checkbox" id="allcheck"  onclick="demo()" />表单名称</th>
        <th>机构</th>
        <th>类型</th>
    </tr>
    </thead>&nbsp;&nbsp;
    <tbody>
    <c:forEach items="${list}" var="oaFormMaster" >
        <tr>
            <td> <input type="checkbox" name="choice" class="checknum"  value="${oaFormMaster.formNo}" onclick="setAll()"> ${oaFormMaster.title}</td>
            <td>
                    ${fns:getDictLabel(oaFormMaster.office.id,'act_category','无机构')}
            </td>
            <td>
                    ${fns:getDictLabel(oaFormMaster.formType,'act_type','无分类')}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>