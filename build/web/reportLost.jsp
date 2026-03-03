<%-- 
    Document   : reportLost
    Created on : Mar 4, 2026, 12:27:30 AM
    Author     : HungKNHE194779
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Báo Mất Đồ</title>
    <style>
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold;}
        .form-group input, .form-group select, .form-group textarea { width: 100%; padding: 8px; }
        .message { color: green; font-weight: bold; }
        .error { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h2>Đăng Tin Báo Mất Đồ</h2>

    <c:if test="${not empty message}">
        <p class="message">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form action="reportLost" method="POST">
        <div class="form-group">
            <label for="title">Tên đồ vật bị mất (*):</label>
            <input type="text" id="title" name="title" required placeholder="Ví dụ: Ví da màu đen">
        </div>

        <div class="form-group">
            <label for="categoryId">Loại đồ vật (*):</label>
            <select id="categoryId" name="categoryId" required>
                <option value="1">Điện tử (Điện thoại, Laptop...)</option>
                <option value="2">Giấy tờ tùy thân</option>
                <option value="3">Đồ dùng học tập</option>
                <option value="4">Khác</option>
            </select>
        </div>

        <div class="form-group">
            <label for="locationId">Khu vực mất (Dự đoán) (*):</label>
            <select id="locationId" name="locationId" required>
                <option value="1">Tòa nhà Alpha</option>
                <option value="2">Thư viện</option>
                <option value="3">Sân bóng</option>
                <option value="4">Căn tin</option>
            </select>
        </div>

        <div class="form-group">
            <label for="dateIncident">Thời gian mất (Ước lượng) (*):</label>
            <input type="datetime-local" id="dateIncident" name="dateIncident" required>
        </div>

        <div class="form-group">
            <label for="description">Mô tả chi tiết (*):</label>
            <textarea id="description" name="description" rows="4" required 
                      placeholder="Mô tả màu sắc, đặc điểm nhận dạng, nhãn hiệu..."></textarea>
        </div>

        <button type="submit" style="padding: 10px 20px; cursor: pointer;">Đăng Tin</button>
    </form>
</body>
</html>
