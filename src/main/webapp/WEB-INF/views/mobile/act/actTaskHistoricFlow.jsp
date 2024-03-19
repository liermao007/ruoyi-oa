<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style>
    .actTask-class {
        font-size: 8px;
    }
</style>
<table class="table table-striped table-bordered table-condensed">
	<tr><th>执行环节</th><th>执行人</th><th>开始时间</th><th>结束时间</th><th>提交意见</th><th>任务历时</th></tr>
	<c:forEach items="${histoicFlowList}" var="act">
		<tr>
			<td class="actTask-class" style="font-size: 8px;">${act.histIns.activityName}</td>
			<td class="actTask-class" style="font-size: 8px;">${act.assigneeName}</td>
			<td class="actTask-class"><fmt:formatDate value="${act.histIns.startTime}" type="both"/></td>
			<td class="actTask-class"><fmt:formatDate value="${act.histIns.endTime}" type="both"/></td>
			<td class="actTask-class" style="word-wrap:break-word;word-break:break-all;">${act.comment}</td>
			<td class="actTask-class">${act.durationTime}</td>
		</tr>
	</c:forEach>
</table>