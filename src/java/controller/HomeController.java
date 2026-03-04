package controller;

import dal.ItemDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Items;

/**
 * Trang chủ: hiển thị tin báo mất / báo nhặt mới nhất cho mọi người.
 */
public class HomeController extends HttpServlet {

    private static final int RECENT_LIMIT = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemDAO itemDao = new ItemDAO();
        List<Items> recentLost = itemDao.getRecentLostItems(RECENT_LIMIT);
        List<Items> recentFound = itemDao.getRecentFoundItems(RECENT_LIMIT);

        request.setAttribute("recentLostItems", recentLost);
        request.setAttribute("recentFoundItems", recentFound);

        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
