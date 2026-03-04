/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ItemDAO;
import dal.ItemImagesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Item_Images;
import model.Items;

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
        doGet(request, response);
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
