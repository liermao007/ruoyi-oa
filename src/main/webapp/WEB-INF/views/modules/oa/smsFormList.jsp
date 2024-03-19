<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
    <span style="padding-left: 20%; text-align:left; font-size: 15px;color: #317EAC;font-weight: bold; width: 50%;">
        是否发送短信：
        <input type="radio" value="1" name="isSend" id="yes" <c:if test="${smsSet.isSend eq 1}">checked="checked"</c:if>/>是
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="radio" value="0" name="isSend" id="no" <c:if test="${smsSet.isSend eq 0}">checked="checked"</c:if>/>否
    </span>
        <p style="padding-left: 20%;font-size: 15px;color: #317EAC;font-weight: bold; width: 20%; list-style: none">默认不发短信流程</p>
        <c:forEach items="${list}" var="dish"  varStatus="status">
            <li align="left" hight="100" valign="middle" style=" list-style: none; padding-left: 20%">
                <c:choose>
                    <c:when test="${dish.isSend eq 0}">
                         <input type="checkbox" id="tableName" name="tableName" checked="checked" value="${dish.tableComment}-_-${dish.tableName}"/>${dish.tableComment}(${dish.tableName})
                    </c:when>
                    <c:otherwise>
                        <input type="checkbox"  name="tableName" value="${dish.tableComment}-_-${dish.tableName}"/>${dish.tableComment}(${dish.tableName})
                    </c:otherwise>
                </c:choose>
            </li>
        </c:forEach>
