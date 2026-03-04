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
import java.text.SimpleDateFormat;
import model.Items;
import model.Users;

/**
 *
 * @author HungKNHE194779
 */
public class EditItemController extends HttpServlet {
   
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
            out.println("<title>Servlet EditItemController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditItemController at " + request.getContextPath () + "</h1>");
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
        HttpSession session = request.getSession();
        Users loggedInUser = (Users) session.getAttribute("currentUser");
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idRaw = request.getParameter("id");
        try {
            int itemId = Integer.parseInt(idRaw);
            ItemDAO dao = new ItemDAO();
            Items item = dao.getItemById(itemId);

            // Kiểm tra quyền sở hữu
            if (item == null || item.getUserId() != loggedInUser.getUserId()) {
                session.setAttribute("successMessage", "Bạn không có quyền sửa bài này.");
                response.sendRedirect("my-items");
                return;
            }

            request.setAttribute("editItem", item);
            request.getRequestDispatcher("edit_item.jsp").forward(request, response);

        } catch (Exception e) {
            session.setAttribute("successMessage", "Lỗi load bài: " + e.getMessage());
            response.sendRedirect("my-items");
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
        HttpSession session = request.getSession();
        Users loggedInUser = (Users) session.getAttribute("currentUser");
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            String dateIncidentStr = request.getParameter("dateIncident");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            java.util.Date dateIncident = sdf.parse(dateIncidentStr);

            Items item = new Items();
            item.setItemId(itemId);
            item.setUserId(loggedInUser.getUserId());
            item.setTitle(title);
            item.setDescription(description);
            item.setCategoryId(categoryId);
            item.setLocationId(locationId);
            item.setDateIncident(dateIncident);

            ItemDAO dao = new ItemDAO();
            boolean ok = dao.updateItem(item);

            if (ok) {
                session.setAttribute("successMessage", "Cập nhật bài đăng thành công.");
            } else {
                session.setAttribute("successMessage", "Không cập nhật được bài đăng.");
            }

        } catch (Exception e) {
            session.setAttribute("successMessage", "Lỗi cập nhật: " + e.getMessage());
        }

        response.sendRedirect("my-items");
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
