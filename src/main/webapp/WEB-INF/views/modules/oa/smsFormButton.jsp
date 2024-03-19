<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<span>是否发送短信：</span>
<input name="isSend" class="btn btn-primary" type="radio" value="1"  style="display: initial;" <c:if test="${isSend eq 1}"> checked="checked"</c:if>/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input name="isSend" class="btn btn-primary" type="radio" value="0"  style="display: initial;" <c:if test="${isSend ne 1}"> checked="checked"</c:if>/>否