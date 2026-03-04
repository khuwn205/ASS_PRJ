package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Item_Images;

public class ItemImagesDAO extends DBContext {

    public List<Item_Images> getImagesByItemId(int itemId) {
        List<Item_Images> list = new ArrayList<>();
        String sql = "SELECT * FROM Item_Images WHERE item_id = ? ORDER BY display_order ASC, created_at ASC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, itemId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Item_Images img = new Item_Images();
                img.setImageId(rs.getInt("image_id"));
                img.setItemId(rs.getInt("item_id"));
                img.setImageUrl(rs.getString("image_url"));
                img.setDisplayOrder(rs.getInt("display_order"));
                img.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(img);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getImagesByItemId: " + e.getMessage());
        }
        return list;
    }
    public boolean insertImageForItem(int itemId, String imageUrl, int displayOrder) {
    String sql = "INSERT INTO Item_Images (item_id, image_url, display_order) VALUES (?, ?, ?)";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, itemId);
        st.setString(2, imageUrl);
        st.setInt(3, displayOrder);
        int rows = st.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        System.out.println("Lỗi insertImageForItem: " + e.getMessage());
    }
    return false;
}
}