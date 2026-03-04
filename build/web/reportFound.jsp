<%-- 
    Document   : reportFound
    Created on : Mar 4, 2026, 11:24:47 AM
    Author     : HungKNHE194779
--%>

<%-- 
    Document   : reportFound
    Mô tả      : Trang báo nhặt được đồ (Found) + upload hình ảnh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Báo Nhặt Được Đồ</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold;}
        .form-group input, .form-group select, .form-group textarea {
            width: 100%; padding: 8px; box-sizing: border-box;
        }
        .message { color: green; font-weight: bold; }
        .error { color: red; font-weight: bold; }
        button { padding: 10px 20px; cursor: pointer; }
    </style>
</head>
<body>
    <h2>Đăng Tin Báo Nhặt Được Đồ</h2>

    <c:if test="${not empty message}">
        <p class="message">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <%-- 
        action="report_found" → bạn sẽ viết 1 servlet map tới URL này,
        trong servlet đó dùng Multipart để đọc file upload.
    --%>
    <form action="report_found" method="POST" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">Tên đồ vật nhặt được (*):</label>
            <input type="text" id="title" name="title" required
                   placeholder="Ví dụ: Ví da màu đen, điện thoại iPhone...">
        </div>

        <div class="form-group">
            <label for="categoryId">Loại đồ vật (*):</label>
            <select id="categoryId" name="categoryId" required>
                <option value="">-- Chọn loại --</option>
                <option value="1">Điện tử (Điện thoại, Laptop...)</option>
                <option value="2">Giấy tờ tùy thân</option>
                <option value="3">Đồ dùng học tập</option>
                <option value="4">Khác</option>
            </select>
        </div>

        <div class="form-group">
            <label for="locationId">Khu vực nhặt được (*):</label>
            <select id="locationId" name="locationId" required>
                <option value="">-- Chọn khu vực --</option>
                <option value="1">Tòa nhà Alpha</option>
                <option value="2">Thư viện</option>
                <option value="3">Sân bóng</option>
                <option value="4">Căn tin</option>
            </select>
        </div>

        <div class="form-group">
            <label for="dateIncident">Thời gian nhặt được (ước lượng) (*):</label>
            <input type="datetime-local" id="dateIncident" name="dateIncident" required>
        </div>

        <div class="form-group">
            <label for="description">Mô tả chi tiết (*):</label>
            <textarea id="description" name="description" rows="4" required
                      placeholder="Mô tả màu sắc, đặc điểm nhận dạng, nhãn hiệu, vị trí bỏ quên..."></textarea>
        </div>

        <div class="form-group">
            <label for="images">Hình ảnh minh họa (có thể chọn nhiều ảnh):</label>
            <input type="file" id="images" name="images" accept="image/*" multiple>
            <%-- backend đọc bằng request.getParts() với name="images" --%>
        </div>

        <button type="submit">Đăng Tin Báo Nhặt</button>
    </form>
</body>
</html>
