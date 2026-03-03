/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Items;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemDAO extends DBContext {

    // Hàm thêm một báo cáo mất đồ mới vào database
    public boolean insertLostItem(Items item) {
        // Cố định type là 'lost' và status là 'processing' theo yêu cầu của database
        String sql = "INSERT INTO Items (user_id, category_id, location_id, title, description, type, status, date_incident) "
                   + "VALUES (?, ?, ?, ?, ?, 'lost', 'processing', ?)";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, item.getUserId());
            st.setInt(2, item.getCategoryId());
            st.setInt(3, item.getLocationId());
            st.setString(4, item.getTitle());
            st.setString(5, item.getDescription());
            // Sử dụng java.sql.Timestamp để lưu cả ngày và giờ
            st.setTimestamp(6, new java.sql.Timestamp(item.getDateIncident().getTime()));
            
            int result = st.executeUpdate();
            return result > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm Item: " + e.getMessage());
        }
        return false;
    }
}
