<%-- 
    Document   : myItem
    Created on : Mar 4, 2026, 1:03:46 AM
    Author     : HungKNHE194779
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Các Bài Đăng Của Tôi</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                padding: 20px;
            }
            h2 {
                margin-top: 30px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f4f4f4;
            }
            .alert-success {
                color: green;
                background-color: #e6ffe6;
                padding: 10px;
                border: 1px solid green;
                margin-bottom: 20px;
            }
            .status-processing {
                color: orange;
                font-weight: bold;
            }
            .status-completed {
                color: green;
                font-weight: bold;
            }
            .status-rejected {
                color: red;
                font-weight: bold;
            }
            .btn {
                padding: 8px 15px;
                text-decoration: none;
                background-color: #007bff;
                color: white;
                border-radius: 4px;
                display: inline-block;
                margin-top: 10px;
            }
            .link-action {
                text-decoration: none;
                color: #007bff;
            }
        </style>
    </head>
    <body>
        <h1>Các bài đăng của tôi</h1>

        <c:if test="${not empty sessionScope.successMessage}">
            <div class="alert-success">
                ${sessionScope.successMessage}
            </div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>

        <a href="report-lost" class="btn">+ Đăng tin báo mất mới</a>

        <!-- BẢNG 1: Đồ tôi đã báo mất -->
        <h2>Danh sách đồ vật tôi đã báo mất</h2>

        <table>
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Tên đồ vật</th>
                    <th>Mô tả</th>
                    <th>Ngày mất (ước lượng)</th>
                    <th>Ngày đăng</th>
                    <th>Chỉnh sửa cuối</th>
                    <th>Trạng thái</th>
                    <th>Xem chi tiết</th>
                    <th>Sửa bài đăng</th>
                    <th>Xóa bài</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty requestScope.myLostItemsList}">
                        <tr>
                            <td colspan="9" style="text-align: center;">Bạn chưa đăng báo cáo mất đồ nào.</td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <c:forEach items="${requestScope.myLostItemsList}" var="item" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${item.title}</td>
                                <td>${item.description}</td>
                                <td>
                                    <fmt:formatDate value="${item.dateIncident}" pattern="dd/MM/yyyy HH:mm" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${item.updatedAt}" pattern="dd/MM/yyyy HH:mm" />
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.status == 'processing'}">
                                            <span class="status-processing">Đang tìm kiếm</span>
                                        </c:when>
                                        <c:when test="${item.status == 'completed'}">
                                            <span class="status-completed">Đã tìm thấy</span>
                                        </c:when>
                                        <c:when test="${item.status == 'rejected'}">
                                            <span class="status-rejected">Bị hủy</span>
                                        </c:when>
                                        <c:otherwise>
                                            ${item.status}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <!-- Đổi URL theo controller chi tiết của bạn -->
                                    <a class="link-action" href="item_detail?id=${item.itemId}">Xem</a>
                                </td>
                                <td>
                                    <!-- Đổi URL theo controller sửa bài của bạn -->
                                    <a class="link-action" href="edit_item?id=${item.itemId}">Sửa</a>
                                </td>
                                <td>
                                    <a class="link-action" href="delete_item?id=${item.itemId}"
                                       onclick="return confirm('Bạn chắc chắn muốn xóa bài này?');">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <!-- BẢNG 2: Đồ tôi đã báo nhặt được -->
        <h2>Danh sách đồ vật tôi đã báo nhặt được</h2>

        <table>
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Tên đồ vật</th>
                    <th>Mô tả</th>
                    <th>Ngày nhặt (ước lượng)</th>
                    <th>Ngày đăng</th>
                    <th>Chỉnh sửa cuối</th>
                    <th>Trạng thái</th>
                    <th>Xem chi tiết</th>
                    <th>Sửa bài đăng</th>
                    <th>Xóa bài</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty requestScope.myFoundItemsList}">
                        <tr>
                            <td colspan="9" style="text-align: center;">Bạn chưa đăng báo nhặt được đồ nào.</td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <c:forEach items="${requestScope.myFoundItemsList}" var="item" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${item.title}</td>
                                <td>${item.description}</td>
                                <td>
                                    <fmt:formatDate value="${item.dateIncident}" pattern="dd/MM/yyyy HH:mm" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${item.updatedAt}" pattern="dd/MM/yyyy HH:mm" />
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.status == 'processing'}">
                                            <span class="status-processing">Đang xử lý</span>
                                        </c:when>
                                        <c:when test="${item.status == 'completed'}">
                                            <span class="status-completed">Đã trả</span>
                                        </c:when>
                                        <c:when test="${item.status == 'rejected'}">
                                            <span class="status-rejected">Bị hủy</span>
                                        </c:when>
                                        <c:otherwise>
                                            ${item.status}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <!-- Đổi URL theo controller chi tiết của bạn -->
                                    <a class="link-action" href="item_detail?id=${item.itemId}">Xem</a>
                                </td>
                                <td>
                                    <!-- Đổi URL theo controller sửa bài của bạn -->
                                    <a class="link-action" href="edit_item?id=${item.itemId}">Sửa</a>
                                </td>
                                <td>
                                    <a class="link-action" href="delete_item?id=${item.itemId}"
                                       onclick="return confirm('Bạn chắc chắn muốn xóa bài này?');">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </body>
</html>