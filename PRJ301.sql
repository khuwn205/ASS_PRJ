-- =============================================
-- Database: Project_PRJ301
-- Mô tả: Hệ thống quản lý thất lạc đồ trong trường học
-- =============================================

USE master;
GO

-- Tạo database nếu chưa tồn tại
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'Project_PRJ301_DB')
BEGIN
    CREATE DATABASE Project_PRJ301_DB;
END
GO

USE Project_PRJ301_DB;
GO

--=========================
-- 1. Bảng Users (Người dùng)
--=========================
CREATE TABLE Users (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name NVARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    role NVARCHAR(20) NOT NULL DEFAULT 'student',
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),

    CONSTRAINT CK_Users_Role CHECK (role IN ('student','staff','admin'))
);

CREATE INDEX idx_role ON Users(role);
CREATE INDEX idx_email ON Users(email);


--=========================
-- 2. Bảng Categories (Danh mục đồ vật)
--=========================
CREATE TABLE Categories (
    category_id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(50) UNIQUE NOT NULL,
    description NVARCHAR(255) NOT NULL
);


--=========================
-- 3. Bảng Locations (Khu vực trong trường)
--=========================

CREATE TABLE Locations (
    location_id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL UNIQUE
);

-- Index hỗ trợ tìm kiếm theo tên
CREATE INDEX idx_location_name ON Locations(name);


--=========================
-- 4. Bảng Items (Đồ vật)
--=========================
CREATE TABLE Items (
    item_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    location_id INT NOT NULL,
    title NVARCHAR(150) NOT NULL,
    description NVARCHAR(255) NOT NULL,
    type NVARCHAR(100) NOT NULL,
    status NVARCHAR(50) NOT NULL DEFAULT 'processing',
    date_incident DATETIME NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),

    CONSTRAINT CK_Items_Type 
        CHECK (type IN ('lost','found')),

    CONSTRAINT CK_Items_Status 
        CHECK (status IN ('processing','completed','rejected')),

    CONSTRAINT FK_Items_Users 
        FOREIGN KEY (user_id) REFERENCES Users(user_id),

    CONSTRAINT FK_Items_Categories 
        FOREIGN KEY (category_id) REFERENCES Categories(category_id),

    CONSTRAINT FK_Items_Locations 
        FOREIGN KEY (location_id) REFERENCES Locations(location_id)
);

CREATE INDEX idx_status_type ON Items(status, type);
CREATE INDEX idx_user ON Items(user_id);
CREATE INDEX idx_location ON Items(location_id);
CREATE INDEX idx_created ON Items(created_at);


--=========================
-- 5. Bảng Item_Images (Hình ảnh chi tiết)
--=========================
CREATE TABLE Item_Images (
    image_id INT PRIMARY KEY IDENTITY(1,1),
    item_id INT NOT NULL,
    image_url NVARCHAR(255) NOT NULL,
    display_order INT DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE(),

    CONSTRAINT FK_ItemImages_Items
        FOREIGN KEY (item_id) REFERENCES Items(item_id) ON DELETE CASCADE
);

CREATE INDEX idx_item_order ON Item_Images(item_id, display_order);


--=========================
-- 6. Bảng Claims (Yêu cầu nhận lại đồ)
--=========================
CREATE TABLE Claims (
    claim_id INT PRIMARY KEY IDENTITY(1,1),
    item_id INT NOT NULL,
    claimer_id INT NOT NULL,
    status NVARCHAR(50) DEFAULT 'pending'
        CHECK (status IN ('pending','approved','rejected')),
    proof_description NVARCHAR(255) NOT NULL,
    response_message NVARCHAR(255) NULL,
    responded_by INT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),

    CONSTRAINT FK_Claims_Items FOREIGN KEY (item_id) REFERENCES Items(item_id),
    CONSTRAINT FK_Claims_Claimer FOREIGN KEY (claimer_id) REFERENCES Users(user_id),
    CONSTRAINT FK_Claims_RespondedBy FOREIGN KEY (responded_by) REFERENCES Users(user_id)
);

CREATE INDEX idx_item_status ON Claims(item_id, status);
CREATE INDEX idx_claimer ON Claims(claimer_id);

/* Chặn 1 user claim 1 item nhiều lần */
CREATE UNIQUE INDEX ux_claim_item_claimer
ON Claims(item_id, claimer_id);

/* Mỗi item chỉ được có 1 claim approved */
CREATE UNIQUE INDEX ux_claim_item_approved
ON Claims(item_id)
WHERE status = 'approved';


--=========================
-- 7. Bảng Notifications (Thông báo)
--=========================
CREATE TABLE Notifications (
    notification_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT NOT NULL,
    notification_type NVARCHAR(50) NOT NULL DEFAULT 'system',
    title NVARCHAR(150),
    message NVARCHAR(255),
    is_read BIT DEFAULT 0,
    related_item_id INT NULL,
    created_at DATETIME DEFAULT GETDATE(),

    CONSTRAINT CK_Notifications_Type
        CHECK (notification_type IN ('claim','system','match')),

    CONSTRAINT FK_Notifications_Users
        FOREIGN KEY (user_id) REFERENCES Users(user_id),

    CONSTRAINT FK_Notifications_Items
        FOREIGN KEY (related_item_id) REFERENCES Items(item_id) ON DELETE SET NULL
);

CREATE INDEX idx_user_read ON Notifications(user_id, is_read);
CREATE INDEX idx_created_notification ON Notifications(created_at);

