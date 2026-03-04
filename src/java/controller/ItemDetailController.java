/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ItemDAO;
import dal.ItemImagesDAO;
import dal.NotificationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Item_Images;
import model.Items;
import model.Notification;
import model.Users;

/**
 *
 * @author HungKNHE194779
 */
public class ItemDetailController extends HttpServlet {
   
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
            out.println("<title>Servlet ItemDetailController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ItemDetailController at " + request.getContextPath () + "</h1>");
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

        String idRaw = request.getParameter("id");
        try {
            int itemId = Integer.parseInt(idRaw);

            ItemDAO itemDao = new ItemDAO();
            Items item = itemDao.getItemById(itemId);
            if (item == null) {
                request.setAttribute("error", "Không tìm thấy bài đăng.");
                request.getRequestDispatcher("item_detail.jsp").forward(request, response);
                return;
            }

            ItemImagesDAO imgDao = new ItemImagesDAO();
            List<Item_Images> images = imgDao.getImagesByItemId(itemId);

            request.setAttribute("item", item);
            request.setAttribute("images", images);

            // Nếu đang đăng nhập và là CHỦ BÀI thì load message đã lưu trong Notifications
            HttpSession session = request.getSession(false);
            Users currentUser = (session == null) ? null : (Users) session.getAttribute("currentUser");
            if (currentUser != null && currentUser.getUserId() == item.getUserId()) {
                NotificationDAO nDao = new NotificationDAO();
                List<Notification> itemMessages = nDao.getMessagesByItemForOwner(currentUser.getUserId(), itemId);
                request.setAttribute("itemMessages", itemMessages);
            }

            request.getRequestDispatcher("item_detail.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Lỗi xem chi tiết: " + e.getMessage());
            request.getRequestDispatcher("item_detail.jsp").forward(request, response);
        }
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
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Users sender = (Users) session.getAttribute("currentUser");
        if (sender == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idRaw = request.getParameter("id");
        String messageText = request.getParameter("message");

        try {
            int itemId = Integer.parseInt(idRaw);

            if (messageText == null || messageText.trim().isEmpty()) {
                response.sendRedirect("item_detail?id=" + itemId);
                return;
            }

            ItemDAO itemDao = new ItemDAO();
            Items item = itemDao.getItemById(itemId);
            if (item == null) {
                response.sendRedirect("home.jsp");
                return;
            }

            int ownerUserId = item.getUserId();
            String title = "Tin nhắn mới";
            String msg = sender.getFullName() + ": " + messageText.trim();

            NotificationDAO nDao = new NotificationDAO();
            nDao.insertMessageToItemOwner(ownerUserId, itemId, title, msg);

            response.sendRedirect("item_detail?id=" + itemId);

        } catch (Exception e) {
            response.sendRedirect("home.jsp");
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
