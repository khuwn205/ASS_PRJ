<%-- 
    Document   : item_detail
    Created on : Mar 4, 2026, 11:00:22 AM
    Author     : HungKNHE194779
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết bài đăng</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .field-label { font-weight: bold; }
        .images img { max-width: 200px; margin: 5px; border: 1px solid #ccc; }
    </style>
</head>
<body>
    <h2>Chi tiết bài đăng</h2>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <c:if test="${not empty item}">
        <p>
            <span class="field-label">Tiêu đề:</span>
            ${item.title}
        </p>
        <p>
            <span class="field-label">Loại:</span>
            ${item.type}
        </p>
        <p>
            <span class="field-label">Trạng thái xử lý:</span>
            ${item.status}
        </p>
        <p>
            <span class="field-label">Ngày xảy ra:</span>
            <fmt:formatDate value="${item.dateIncident}" pattern="dd/MM/yyyy HH:mm" />
        </p>
        <p>
            <span class="field-label">Ngày đăng:</span>
            <fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yyyy HH:mm" />
        </p>
        <p>
            <span class="field-label">Chỉnh sửa cuối:</span>
            <fmt:formatDate value="${item.updatedAt}" pattern="dd/MM/yyyy HH:mm" />
        </p>
        <p>
            <span class="field-label">Mô tả chi tiết:</span><br/>
            ${item.description}
        </p>

        <!-- Ảnh: đặc biệt hữu ích với bài báo nhặt được -->
        <c:if test="${not empty images}">
            <h3>Hình ảnh đính kèm</h3>
            <div class="images">
                <c:forEach var="img" items="${images}">
                    <img src="${pageContext.request.contextPath}/${img.imageUrl}" alt="Ảnh minh họa"/>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty images}">
            <p><i>Không có hình ảnh minh họa.</i></p>
        </c:if>
    </c:if>

    <p>
        <a href="my-items">Quay lại danh sách bài đăng của tôi</a>
    </p>
</body>
</html>
