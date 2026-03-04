-- Bảng tin nhắn / bình luận trên từng bài đăng (item)
-- Chạy trong database Project_PRJ301_DB

USE Project_PRJ301_DB;
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Item_Messages')
BEGIN
    CREATE TABLE Item_Messages (
        message_id INT PRIMARY KEY IDENTITY(1,1),
        item_id INT NOT NULL,
        user_id INT NOT NULL,
        message_text NVARCHAR(500) NOT NULL,
        created_at DATETIME DEFAULT GETDATE(),

        CONSTRAINT FK_ItemMessages_Items
            FOREIGN KEY (item_id) REFERENCES Items(item_id) ON DELETE CASCADE,
        CONSTRAINT FK_ItemMessages_Users
            FOREIGN KEY (user_id) REFERENCES Users(user_id)
    );

    CREATE INDEX idx_item_messages_item ON Item_Messages(item_id);
    CREATE INDEX idx_item_messages_created ON Item_Messages(created_at);
END
GO
