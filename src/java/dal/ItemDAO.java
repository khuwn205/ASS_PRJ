/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Items;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

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
public List<Items> getItemsByUserId(int userId) {
        List<Items> list = new ArrayList<>();
        String sql = "SELECT * FROM Items WHERE user_id = ? ORDER BY created_at DESC";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Items item = new Items();
                item.setItemId(rs.getInt("item_id"));
                item.setUserId(rs.getInt("user_id"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setLocationId(rs.getInt("location_id"));
                item.setTitle(rs.getString("title"));
                item.setDescription(rs.getString("description"));
                item.setType(rs.getString("type"));
                item.setStatus(rs.getString("status"));
                item.setDateIncident(rs.getTimestamp("date_incident"));
                item.setCreatedAt(rs.getTimestamp("created_at"));
                item.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                list.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách Item: " + e.getMessage());
        }
        return list;
    }
public List<Items> getLostItemsByUserId(int userId) {
    List<Items> list = new ArrayList<>();
    String sql = "SELECT * FROM Items WHERE user_id = ? AND type = 'lost' ORDER BY created_at DESC";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, userId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Items item = new Items();
            item.setItemId(rs.getInt("item_id"));
            item.setUserId(rs.getInt("user_id"));
            item.setCategoryId(rs.getInt("category_id"));
            item.setLocationId(rs.getInt("location_id"));
            item.setTitle(rs.getString("title"));
            item.setDescription(rs.getString("description"));
            item.setType(rs.getString("type"));
            item.setStatus(rs.getString("status"));
            item.setDateIncident(rs.getTimestamp("date_incident"));
            item.setCreatedAt(rs.getTimestamp("created_at"));
            item.setUpdatedAt(rs.getTimestamp("updated_at"));
            list.add(item);
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi lấy lost items: " + e.getMessage());
    }
    return list;
}

public List<Items> getFoundItemsByUserId(int userId) {
    List<Items> list = new ArrayList<>();
    String sql = "SELECT * FROM Items WHERE user_id = ? AND type = 'found' ORDER BY created_at DESC";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, userId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Items item = new Items();
            item.setItemId(rs.getInt("item_id"));
            item.setUserId(rs.getInt("user_id"));
            item.setCategoryId(rs.getInt("category_id"));
            item.setLocationId(rs.getInt("location_id"));
            item.setTitle(rs.getString("title"));
            item.setDescription(rs.getString("description"));
            item.setType(rs.getString("type"));
            item.setStatus(rs.getString("status"));
            item.setDateIncident(rs.getTimestamp("date_incident"));
            item.setCreatedAt(rs.getTimestamp("created_at"));
            item.setUpdatedAt(rs.getTimestamp("updated_at"));
            list.add(item);
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi lấy found items: " + e.getMessage());
    }
    return list;
}
public boolean deleteItemByIdAndUser(int itemId, int userId) {
    try {
        // 1. Xóa mọi claim liên quan tới item này (nếu có)
        String sqlDeleteClaims = "DELETE FROM Claims WHERE item_id = ?";
        PreparedStatement stClaim = connection.prepareStatement(sqlDeleteClaims);
        stClaim.setInt(1, itemId);
        stClaim.executeUpdate();  // không cần quan tâm xóa bao nhiêu dòng

        // 2. Xóa bản ghi trong Items (chỉ cho phép xóa bài thuộc user hiện tại)
        //    Item_Images có ON DELETE CASCADE nên khi xóa Items, ảnh sẽ bị xóa tự động.
        String sqlDeleteItem = "DELETE FROM Items WHERE item_id = ? AND user_id = ?";
        PreparedStatement stItem = connection.prepareStatement(sqlDeleteItem);
        stItem.setInt(1, itemId);
        stItem.setInt(2, userId);

        int rows = stItem.executeUpdate();
        return rows > 0;   // true nếu xóa được ít nhất 1 dòng

    } catch (SQLException e) {
        System.out.println("Lỗi khi xóa Item (kèm claims): " + e.getMessage());
    }
    return false;
}
// Lấy thông tin 1 item theo id
public Items getItemById(int itemId) {
    String sql = "SELECT * FROM Items WHERE item_id = ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, itemId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            Items item = new Items();
            item.setItemId(rs.getInt("item_id"));
            item.setUserId(rs.getInt("user_id"));
            item.setCategoryId(rs.getInt("category_id"));
            item.setLocationId(rs.getInt("location_id"));
            item.setTitle(rs.getString("title"));
            item.setDescription(rs.getString("description"));
            item.setType(rs.getString("type"));
            item.setStatus(rs.getString("status"));
            item.setDateIncident(rs.getTimestamp("date_incident"));
            item.setCreatedAt(rs.getTimestamp("created_at"));
            item.setUpdatedAt(rs.getTimestamp("updated_at"));
            return item;
        }
    } catch (SQLException e) {
        System.out.println("Lỗi getItemById: " + e.getMessage());
    }
    return null;
}

// Cập nhật thông tin item (không đổi user_id, type,... nếu không cần)
public boolean updateItem(Items item) {
    String sql = "UPDATE Items SET category_id = ?, location_id = ?, title = ?, "
               + "description = ?, date_incident = ?, updated_at = GETDATE() "
               + "WHERE item_id = ? AND user_id = ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, item.getCategoryId());
        st.setInt(2, item.getLocationId());
        st.setString(3, item.getTitle());
        st.setString(4, item.getDescription());
        st.setTimestamp(5, new java.sql.Timestamp(item.getDateIncident().getTime()));
        st.setInt(6, item.getItemId());
        st.setInt(7, item.getUserId());
        int rows = st.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        System.out.println("Lỗi updateItem: " + e.getMessage());
    }
    return false;
}
public int insertFoundItemReturnId(Items item) {
    String sql = "INSERT INTO Items (user_id, category_id, location_id, title, description, type, status, date_incident) "
               + "VALUES (?, ?, ?, ?, ?, 'found', 'processing', ?)";
    try {
        PreparedStatement st = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, item.getUserId());
        st.setInt(2, item.getCategoryId());
        st.setInt(3, item.getLocationId());
        st.setString(4, item.getTitle());
        st.setString(5, item.getDescription());
        st.setTimestamp(6, new java.sql.Timestamp(item.getDateIncident().getTime()));

        int affected = st.executeUpdate();
        if (affected > 0) {
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    } catch (SQLException e) {
        System.out.println("Lỗi insertFoundItemReturnId: " + e.getMessage());
    }
    return -1;
}
}
