<%-- 
    Document   : edit_item
    Created on : Mar 4, 2026, 10:54:36 AM
    Author     : HungKNHE194779
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật thông tin báo mất đồ</title>
</head>
<body>
    <h2>Cập nhật thông tin báo mất đồ</h2>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form action="edit_item" method="POST">
        <!-- Ẩn id bài đăng -->
        <input type="hidden" name="itemId" value="${editItem.itemId}" />

        <div>
            <label for="title">Tên đồ vật (*):</label>
            <input type="text" id="title" name="title"
                   value="${editItem.title}" required />
        </div>

        <div>
            <label for="categoryId">Loại đồ vật (*):</label>
            <select id="categoryId" name="categoryId" required>
                <option value="1" ${editItem.categoryId == 1 ? 'selected' : ''}>Điện tử</option>
                <option value="2" ${editItem.categoryId == 2 ? 'selected' : ''}>Giấy tờ tùy thân</option>
                <option value="3" ${editItem.categoryId == 3 ? 'selected' : ''}>Đồ dùng học tập</option>
                <option value="4" ${editItem.categoryId == 4 ? 'selected' : ''}>Khác</option>
            </select>
        </div>

        <div>
            <label for="locationId">Khu vực mất (*):</label>
            <select id="locationId" name="locationId" required>
                <option value="1" ${editItem.locationId == 1 ? 'selected' : ''}>Tòa nhà Alpha</option>
                <option value="2" ${editItem.locationId == 2 ? 'selected' : ''}>Thư viện</option>
                <option value="3" ${editItem.locationId == 3 ? 'selected' : ''}>Sân bóng</option>
                <option value="4" ${editItem.locationId == 4 ? 'selected' : ''}>Căn tin</option>
            </select>
        </div>

        <div>
            <label for="dateIncident">Thời gian mất (*):</label>

            <%-- Định dạng sẵn thành chuỗi phù hợp với input datetime-local --%>
            <fmt:formatDate value="${editItem.dateIncident}"
                            pattern="yyyy-MM-dd'T'HH:mm"
                            var="dateIncidentStr" />

            <input type="datetime-local" id="dateIncident" name="dateIncident"
                   value="${dateIncidentStr}"
                   required />
        </div>

        <div>
            <label for="description">Mô tả chi tiết (*):</label>
            <textarea id="description" name="description" rows="4" required>${editItem.description}</textarea>
        </div>

        <button type="submit">Cập nhật</button>
    </form>
</body>
</html>