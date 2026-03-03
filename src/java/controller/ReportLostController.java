/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.ItemDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Items;
import model.Users; // Giả sử bạn có model Users

@WebServlet(name = "ReportLostController", urlPatterns = {"/reportLost"})
public class ReportLostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // (Tùy chọn) Tại đây bạn có thể gọi CategoryDAO và LocationDAO 
        // để đẩy danh sách categories và locations sang trang JSP.
        request.getRequestDispatcher("reportLost.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Lấy thông tin user đang đăng nhập từ Session
        HttpSession session = request.getSession();
        Users loggedInUser = (Users) session.getAttribute("currentUser");
        
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp"); // Bắt buộc đăng nhập
            return;
        }

        // 2. Lấy dữ liệu từ Form gửi lên
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        String dateIncidentStr = request.getParameter("dateIncident"); // Dạng yyyy-MM-dd'T'HH:mm

        try {
            // Chuyển đổi chuỗi thời gian từ HTML input (datetime-local) sang java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date dateIncident = sdf.parse(dateIncidentStr);

            // 3. Tạo đối tượng Items và set dữ liệu
            Items newItem = new Items();
            newItem.setUserId(loggedInUser.getUserId());
            newItem.setTitle(title);
            newItem.setDescription(description);
            newItem.setCategoryId(categoryId);
            newItem.setLocationId(locationId);
            newItem.setDateIncident(dateIncident);
            // Các trường type, status, created_at sẽ được xử lý ở DAO và Database

            // 4. Gọi DAO để lưu vào DB
            ItemDAO itemDAO = new ItemDAO();
            boolean isSuccess = itemDAO.insertLostItem(newItem);

            if (isSuccess) {
                request.setAttribute("message", "Báo mất đồ thành công!");
            } else {
                request.setAttribute("error", "Có lỗi xảy ra, vui lòng thử lại.");
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Dữ liệu nhập không hợp lệ: " + e.getMessage());
        }

        // 5. Quay lại trang form cùng thông báo
        request.getRequestDispatcher("reportLost.jsp").forward(request, response);
    }
}
