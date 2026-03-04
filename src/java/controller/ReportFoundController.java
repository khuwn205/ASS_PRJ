/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ItemDAO;
import dal.ItemImagesDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import model.Items;
import model.Users;

/**
 *
 * @author HungKNHE194779
 */
@WebServlet(name="ReportFoundController", urlPatterns={"/report_found"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1MB
    maxFileSize = 5 * 1024 * 1024,        // 5MB mỗi file
    maxRequestSize = 25 * 1024 * 1024     // 25MB tổng request
)
public class ReportFoundController extends HttpServlet {
   
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
            out.println("<title>Servlet ReportFoundController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportFoundController at " + request.getContextPath () + "</h1>");
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
        request.getRequestDispatcher("reportFound.jsp").forward(request, response);
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

        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String categoryIdRaw = request.getParameter("categoryId");
        String locationIdRaw = request.getParameter("locationId");
        String dateIncidentStr = request.getParameter("dateIncident");

        try {
            int categoryId = Integer.parseInt(categoryIdRaw);
            int locationId = Integer.parseInt(locationIdRaw);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date dateIncident = sdf.parse(dateIncidentStr);

            // 1. Tạo item kiểu FOUND
            Items item = new Items();
            item.setUserId(loggedInUser.getUserId());
            item.setCategoryId(categoryId);
            item.setLocationId(locationId);
            item.setTitle(title);
            item.setDescription(description);
            item.setType("found");
            item.setStatus("processing");
            item.setDateIncident(dateIncident);

            ItemDAO itemDao = new ItemDAO();
            int newItemId = itemDao.insertFoundItemReturnId(item);

            if (newItemId <= 0) {
                request.setAttribute("error", "Không tạo được bài báo nhặt đồ.");
                request.getRequestDispatcher("reportFound.jsp").forward(request, response);
                return;
            }

            // 2. Lưu file ảnh (nếu có)
            String uploadFolder = getServletContext().getRealPath("/") + "uploads";
            File uploadDir = new File(uploadFolder);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            Collection<Part> parts = request.getParts();
            ItemImagesDAO imgDao = new ItemImagesDAO();
            int displayOrder = 0;

            for (Part part : parts) {
                if (!"images".equals(part.getName())) continue;
                if (part.getSize() <= 0) continue;

                String submittedFileName = extractFileName(part.getSubmittedFileName());
                if (submittedFileName == null || submittedFileName.isEmpty()) continue;

                String timePrefix = String.valueOf(System.currentTimeMillis());
                String fileName = timePrefix + "_" + submittedFileName;

                String filePath = uploadFolder + File.separator + fileName;
                part.write(filePath);

                String imageUrl = "uploads/" + fileName; // sẽ dùng ${pageContext.request.contextPath}/uploads/...

                imgDao.insertImageForItem(newItemId, imageUrl, displayOrder++);
            }

            request.setAttribute("message", "Đăng tin báo nhặt đồ thành công!");
            request.getRequestDispatcher("reportFound.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("reportFound.jsp").forward(request, response);
        }
    }

    private String extractFileName(String submitted) {
        if (submitted == null) return null;
        int slash = Math.max(submitted.lastIndexOf('/'), submitted.lastIndexOf('\\'));
        return (slash >= 0) ? submitted.substring(slash + 1) : submitted;
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
