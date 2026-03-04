<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tình Trạng Yêu Cầu Nhận Đồ</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #f4f4f4; }
        /* CSS cho các trạng thái */
        .badge { padding: 5px 10px; border-radius: 15px; font-size: 14px; font-weight: bold; color: white; }
        .bg-pending { background-color: #ffc107; color: #000; } /* Màu vàng */
        .bg-approved { background-color: #28a745; } /* Màu xanh lá */
        .bg-rejected { background-color: #dc3545; } /* Màu đỏ */
        
        .alert-info { color: #31708f; background-color: #d9edf7; padding: 15px; border: 1px solid #bce8f1; margin-bottom: 20px;}
        .empty-msg { text-align: center; font-style: italic; color: #777; padding: 20px;}
    </style>
</head>
<body>
    <h2>Tình trạng các yêu cầu nhận lại đồ của bạn</h2>
    
    <div class="alert-info">
        Đây là danh sách các món đồ bạn đã gửi yêu cầu xác nhận là của mình. Hãy theo dõi trạng thái tại đây.
    </div>

    <table>
        <thead>
            <tr>
                <th>STT</th>
                <th>Tên món đồ</th>
                <th>Bằng chứng bạn cung cấp</th>
                <th>Ngày gửi yêu cầu</th>
                <th>Trạng thái</th>
                <th>Phản hồi từ người đăng</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty requestScope.myClaimList}">
                    <tr>
                        <td colspan="6" class="empty-msg">Bạn chưa có yêu cầu nhận lại đồ nào.</td>
                    </tr>
                </c:when>
                
                <c:otherwise>
                    <c:forEach items="${requestScope.myClaimList}" var="claim" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td><strong>${claim.itemTitle}</strong></td>
                            <td>${claim.proofDescription}</td>
                            <td>
                                <fmt:formatDate value="${claim.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${claim.status == 'pending'}">
                                        <span class="badge bg-pending">Đang chờ duyệt</span>
                                    </c:when>
                                    <c:when test="${claim.status == 'approved'}">
                                        <span class="badge bg-approved">Đã được duyệt</span>
                                    </c:when>
                                    <c:when test="${claim.status == 'rejected'}">
                                        <span class="badge bg-rejected">Bị từ chối</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge">${claim.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty claim.responseMessage}">
                                        <em>"${claim.responseMessage}"</em>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #ccc;">-</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
    
    <br>
    <a href="home.jsp" style="text-decoration: none; color: #007bff;">&larr; Quay lại trang chủ</a>
</body>
</html>