<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tất cả tin báo nhặt được đồ</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f4f4f4; }
    </style>
</head>
<body>
    <h2>Tất cả tin báo nhặt được đồ</h2>
    <p><a href="${pageContext.request.contextPath}/home">Về trang chủ</a></p>

    <table>
        <tr>
            <th>STT</th>
            <th>Tên đồ vật</th>
            <th>Mô tả ngắn</th>
            <th>Ngày báo</th>
            <th>Hành động</th>
        </tr>
        <c:forEach var="it" items="${allFoundItems}" varStatus="st">
            <tr>
                <td>${st.index + 1}</td>
                <td>${it.title}</td>
                <td>${it.description}</td>
                <td><fmt:formatDate value="${it.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
                <td><a href="${pageContext.request.contextPath}/item_detail?id=${it.itemId}">Xem chi tiết &amp; Nhắn tin</a></td>
            </tr>
        </c:forEach>
        <c:if test="${empty allFoundItems}">
            <tr><td colspan="5">Chưa có tin báo nhặt được nào.</td></tr>
        </c:if>
    </table>
</body>
</html>
