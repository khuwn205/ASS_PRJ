package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Notification;

public class NotificationDAO extends DBContext {

    // Gửi 1 notification (dùng cho message vào bài đăng)
    public boolean insertMessageToItemOwner(int ownerUserId, int relatedItemId, String title, String message) {
        String sql = "INSERT INTO Notifications (user_id, notification_type, title, message, is_read, related_item_id) "
                   + "VALUES (?, 'system', ?, ?, 0, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ownerUserId);
            st.setString(2, title);
            st.setString(3, message);
            st.setInt(4, relatedItemId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi NotificationDAO.insertMessageToItemOwner: " + e.getMessage());
        }
        return false;
    }

    // Lấy “message” của 1 bài đăng (chỉ lấy loại system + related_item_id)
    // Dùng để chủ bài xem lại các tin nhắn gửi vào bài của mình
    public List<Notification> getMessagesByItemForOwner(int ownerUserId, int relatedItemId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT notification_id, user_id, notification_type, title, message, is_read, related_item_id, created_at "
                   + "FROM Notifications "
                   + "WHERE user_id = ? AND related_item_id = ? AND notification_type = 'system' "
                   + "ORDER BY created_at ASC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ownerUserId);
            st.setInt(2, relatedItemId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setUserId(rs.getInt("user_id"));
                n.setNotificationType(rs.getString("notification_type"));
                n.setTitle(rs.getString("title"));
                n.setMessage(rs.getString("message"));
                n.setIsRead(rs.getBoolean("is_read"));
                int rid = rs.getInt("related_item_id");
                n.setRelatedItemId(rs.wasNull() ? null : rid);
                n.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(n);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi NotificationDAO.getMessagesByItemForOwner: " + e.getMessage());
        }
        return list;
    }
}