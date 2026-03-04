package controller;

import dal.ItemDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Items;

@WebServlet(name = "ViewAllFoundController", urlPatterns = {"/view_all_found"})
public class ViewAllFoundController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemDAO itemDao = new ItemDAO();
        List<Items> list = itemDao.getAllFoundItems();
        request.setAttribute("allFoundItems", list);
        request.getRequestDispatcher("viewAllFound.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
