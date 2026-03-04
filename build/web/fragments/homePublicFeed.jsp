<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<b>Tin báo mất đồ mới nhất</b>
<table border="1" width="100%">
    <tr>
        <th>Tên đồ vật</th>
        <th>Ngày báo</th>
        <th>Hành động</th>
    </tr>
    <c:forEach var="it" items="${recentLostItems}">
        <tr>
            <td>${it.title}</td>
            <td><fmt:formatDate value="${it.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
            <td><a href="${pageContext.request.contextPath}/item_detail?id=${it.itemId}">Xem &amp; Nhắn tin</a></td>
        </tr>
    </c:forEach>
    <c:if test="${empty recentLostItems}">
        <tr><td colspan="3">Chưa có tin báo mất nào.</td></tr>
    </c:if>
</table>
<p><a href="${pageContext.request.contextPath}/view_all_lost">Xem tất cả tin báo mất</a></p>

<br/>

<b>Tin báo nhặt được đồ mới nhất</b>
<table border="1" width="100%">
    <tr>
        <th>Tên đồ vật</th>
        <th>Ngày báo</th>
        <th>Hành động</th>
    </tr>
    <c:forEach var="it" items="${recentFoundItems}">
        <tr>
            <td>${it.title}</td>
            <td><fmt:formatDate value="${it.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
            <td><a href="${pageContext.request.contextPath}/item_detail?id=${it.itemId}">Xem &amp; Nhắn tin</a></td>
        </tr>
    </c:forEach>
    <c:if test="${empty recentFoundItems}">
        <tr><td colspan="3">Chưa có tin báo nhặt được nào.</td></tr>
    </c:if>
</table>
<p><a href="${pageContext.request.contextPath}/view_all_found">Xem tất cả tin báo nhặt được</a></p>
