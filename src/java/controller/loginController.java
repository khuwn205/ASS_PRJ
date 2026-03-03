/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Users;
import dal.UserDAO;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author HungKNHE194779
 */
public class loginController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet loginController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            HttpSession session = request.getSession(false);

    // Nếu đã đăng nhập (session còn và có currentUser) thì cho vào thẳng home
    if (session != null && session.getAttribute("currentUser") != null) {
        response.sendRedirect("home");
        return;
    }

    // Chưa đăng nhập thì hiện trang login
        request.getRequestDispatcher("login.jsp").forward(request, response);

    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        Users loggedInUser = dao.checkLogin(username, password);

        if (loggedInUser != null) {
            // 1. Tạo hoặc lấy Session hiện tại
            HttpSession session = request.getSession();

            // 2. Lưu toàn bộ thông tin user vào Session
            session.setAttribute("currentUser", loggedInUser);

            // 3. Lưu riêng role để dùng nhanh trên giao diện
            session.setAttribute("userRole", loggedInUser.getRole());

            // 4. Chuyển hướng về trang chung sau đăng nhập
            response.sendRedirect("home.jsp");
        } else {
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }


    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
