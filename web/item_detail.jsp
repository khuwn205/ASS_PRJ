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
        .msg-box { border: 1px solid #ddd; padding: 10px; margin: 10px 0; }
    </style>
</head>
<body>
    <h2>Chi tiết bài đăng</h2>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <c:if test="${not empty item}">
        <p><span class="field-label">Tiêu đề:</span> ${item.title}</p>
        <p><span class="field-label">Loại:</span> ${item.type}</p>
        <p><span class="field-label">Trạng thái:</span> ${item.status}</p>
        <p><span class="field-label">Ngày xảy ra:</span>
            <fmt:formatDate value="${item.dateIncident}" pattern="dd/MM/yyyy HH:mm" />
        </p>
        <p><span class="field-label">Ngày đăng:</span>
            <fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yyyy HH:mm" />
        </p>
        <p><span class="field-label">Chỉnh sửa cuối:</span>
            <fmt:formatDate value="${item.updatedAt}" pattern="dd/MM/yyyy HH:mm" />
        </p>
        <p><span class="field-label">Mô tả:</span><br/> ${item.description}</p>

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

        <h3>Gửi tin nhắn cho chủ bài</h3>
        <c:if test="${not empty sessionScope.currentUser}">
            <form action="item_detail" method="POST">
                <input type="hidden" name="id" value="${item.itemId}" />
                <textarea name="message" rows="3" cols="60" required placeholder="Nhập nội dung..."></textarea><br/>
                <button type="submit">Gửi</button>
            </form>
        </c:if>
        <c:if test="${empty sessionScope.currentUser}">
            <p><i>Đăng nhập để gửi tin nhắn.</i></p>
        </c:if>

        <!-- Chỉ CHỦ BÀI mới thấy danh sách tin nhắn đã nhận (lưu trong Notifications) -->
        <c:if test="${not empty itemMessages}">
            <h3>Tin nhắn đã nhận (chỉ chủ bài xem)</h3>
            <div class="msg-box">
                <c:forEach var="n" items="${itemMessages}">
                    <p style="margin:6px 0;">
                        <fmt:formatDate value="${n.createdAt}" pattern="dd/MM/yyyy HH:mm" /> -
                        <strong>${n.title}</strong><br/>
                        ${n.message}
                    </p>
                </c:forEach>
            </div>
        </c:if>

    </c:if>

    <p>
        <a href="home.jsp">Trang chủ</a> |
        <a href="my-items">Danh sách bài đăng của tôi</a>
    </p>
</body>
</html>
