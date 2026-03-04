/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ItemDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Items;

/**
 *
 * @author HungKNHE194779
 */
public class MyItemsController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyItemsController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyItemsController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// 1. Xác định User ID đang đăng nhập (Tạm thời gán cứng là 1 để test giống bước trước)
        // Khi nào làm xong Login thì lấy từ Session nhé
        HttpSession session = request.getSession();

        // LƯU Ý QUAN TRỌNG: Chữ "user" trong getAttribute phải KHỚP 
        // với chữ bạn dùng lúc login thành công (ví dụ: session.setAttribute("user", account))
        model.Users loggedInUser = (model.Users) session.getAttribute("currentUser");

        // Nếu chưa đăng nhập, bắt quay về trang login
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Lấy chính xác ID của người đang đăng nhập
        int userId = loggedInUser.getUserId();

        // 3. Gọi DAO lấy 2 danh sách
        dal.ItemDAO itemDAO = new dal.ItemDAO();
        List<model.Items> lostList = itemDAO.getLostItemsByUserId(userId);
        List<model.Items> foundList = itemDAO.getFoundItemsByUserId(userId);

// 4. Gửi sang JSP
        request.setAttribute("myLostItemsList", lostList);
        request.setAttribute("myFoundItemsList", foundList);
        request.getRequestDispatcher("myItem.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
