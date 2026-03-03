package model;

import java.util.Date;

public class Item_Images {

    private int imageId;
    private int itemId;
    private String imageUrl;
    private int displayOrder;
    private Date createdAt;

    public Item_Images() {
    }

    public Item_Images(int imageId, int itemId, String imageUrl, int displayOrder, Date createdAt) {
        this.imageId = imageId;
        this.itemId = itemId;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Item_Images{" + "imageId=" + imageId + ", itemId=" + itemId + ", imageUrl=" + imageUrl + ", displayOrder=" + displayOrder + ", createdAt=" + createdAt + '}';
    }
}