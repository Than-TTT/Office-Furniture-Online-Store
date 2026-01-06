-- =============================================
-- Database Setup Script for Office Furniture Online Store
-- =============================================

-- Tạo database
CREATE DATABASE IF NOT EXISTS cnpm;
USE cnpm;

-- =============================================
-- 1. BẢNG ROLE (Không có foreign key)
-- =============================================
CREATE TABLE IF NOT EXISTS role (
    roleId INT AUTO_INCREMENT PRIMARY KEY,
    roleName VARCHAR(100)
);

-- =============================================
-- 2. BẢNG USER (Parent table - JOINED Inheritance)
-- =============================================
CREATE TABLE IF NOT EXISTS user (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE,
    email VARCHAR(100),
    password VARCHAR(100),
    phone VARCHAR(100),
    address VARCHAR(100),
    gender VARCHAR(100),
    status VARCHAR(100),
    isDelete BIT,
    roleId INT,
    FOREIGN KEY (roleId) REFERENCES role(roleId)
);

-- =============================================
-- 3. BẢNG CUSTOMER (Kế thừa từ User)
-- =============================================
CREATE TABLE IF NOT EXISTS customer (
    customerId INT PRIMARY KEY,
    FOREIGN KEY (customerId) REFERENCES user(userId)
);

-- =============================================
-- 4. BẢNG EMPLOYEE (Kế thừa từ User)
-- =============================================
CREATE TABLE IF NOT EXISTS employee (
    employeeId INT PRIMARY KEY,
    FOREIGN KEY (employeeId) REFERENCES user(userId)
);

-- =============================================
-- 5. BẢNG ADMINISTRATOR (Kế thừa từ User)
-- =============================================
CREATE TABLE IF NOT EXISTS administrator (
    administratorId INT PRIMARY KEY,
    FOREIGN KEY (administratorId) REFERENCES user(userId)
);

-- =============================================
-- 6. BẢNG LOG
-- =============================================
CREATE TABLE IF NOT EXISTS log (
    logId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    dateLog DATETIME,
    content TEXT,
    FOREIGN KEY (userId) REFERENCES user(userId)
);

-- =============================================
-- 7. BẢNG CATEGORY
-- =============================================
CREATE TABLE IF NOT EXISTS category (
    categoryId INT AUTO_INCREMENT PRIMARY KEY,
    categoryName NVARCHAR(200) NOT NULL
);

-- =============================================
-- 8. BẢNG PRODUCT
-- =============================================
CREATE TABLE IF NOT EXISTS product (
    productId INT AUTO_INCREMENT PRIMARY KEY,
    name NVARCHAR(200) NOT NULL,
    descript TEXT,
    isDelete BIT,
    categoryId INT,
    FOREIGN KEY (categoryId) REFERENCES category(categoryId)
);

-- =============================================
-- 9. BẢNG PRODUCT_TYPE
-- =============================================
CREATE TABLE IF NOT EXISTS product_type (
    typeId INT AUTO_INCREMENT PRIMARY KEY,
    productId INT NOT NULL,
    color NVARCHAR(50),
    length DOUBLE,
    width DOUBLE,
    height DOUBLE,
    weight DOUBLE,
    material NVARCHAR(100),
    price DOUBLE,
    quantity INT,
    FOREIGN KEY (productId) REFERENCES product(productId)
);

-- =============================================
-- 10. BẢNG PRODUCT_IMAGE
-- =============================================
CREATE TABLE IF NOT EXISTS product_image (
    productImageId INT AUTO_INCREMENT PRIMARY KEY,
    productImage NVARCHAR(500) NOT NULL,
    productId INT NOT NULL,
    FOREIGN KEY (productId) REFERENCES product(productId)
);

-- =============================================
-- 11. BẢNG VOUCHER (Parent table - JOINED Inheritance)
-- =============================================
CREATE TABLE IF NOT EXISTS voucher (
    voucherId INT AUTO_INCREMENT PRIMARY KEY,
    dateStart DATE,
    dateEnd DATE,
    code VARCHAR(255),
    discount DOUBLE,
    isDelete BIT
);

-- =============================================
-- 12. BẢNG VOUCHER_BY_PRICE (Kế thừa từ Voucher)
-- =============================================
CREATE TABLE IF NOT EXISTS voucherbyprice (
    voucherByPriceId INT PRIMARY KEY,
    lowerbound DOUBLE,
    FOREIGN KEY (voucherByPriceId) REFERENCES voucher(voucherId)
);

-- =============================================
-- 13. BẢNG VOUCHER_BY_PRODUCT (Kế thừa từ Voucher)
-- =============================================
CREATE TABLE IF NOT EXISTS voucherbyproduct (
    voucherByProductId INT PRIMARY KEY,
    FOREIGN KEY (voucherByProductId) REFERENCES voucher(voucherId)
);

-- =============================================
-- 14. BẢNG LIÊN KẾT VOUCHER_PRODUCT_TYPE (Many-to-Many)
-- =============================================
CREATE TABLE IF NOT EXISTS voucher_product_type (
    voucherByProductId INT,
    typeId INT,
    PRIMARY KEY (voucherByProductId, typeId),
    FOREIGN KEY (voucherByProductId) REFERENCES voucherbyproduct(voucherByProductId),
    FOREIGN KEY (typeId) REFERENCES product_type(typeId)
);

-- =============================================
-- 15. BẢNG ORDERS
-- =============================================
CREATE TABLE IF NOT EXISTS orders (
    orderId INT AUTO_INCREMENT PRIMARY KEY,
    orderDate DATE NOT NULL,
    status NVARCHAR(30),
    cityOfProvince NVARCHAR(50),
    district NVARCHAR(50),
    ward NVARCHAR(50),
    streetNumber NVARCHAR(50),
    phone VARCHAR(10) NOT NULL,
    totalCost DOUBLE NOT NULL,
    discount DOUBLE NOT NULL,
    actualCost DOUBLE NOT NULL,
    customerId INT NOT NULL,
    voucherId INT,
    FOREIGN KEY (customerId) REFERENCES user(userId),
    FOREIGN KEY (voucherId) REFERENCES voucher(voucherId)
);

-- =============================================
-- 16. BẢNG ORDER_ITEM (Composite Primary Key)
-- =============================================
CREATE TABLE IF NOT EXISTS order_item (
    orderId INT NOT NULL,
    typeId INT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    PRIMARY KEY (orderId, typeId),
    FOREIGN KEY (orderId) REFERENCES orders(orderId),
    FOREIGN KEY (typeId) REFERENCES product_type(typeId)
);

-- =============================================
-- 17. BẢNG CART
-- =============================================
CREATE TABLE IF NOT EXISTS cart (
    cartId INT AUTO_INCREMENT PRIMARY KEY,
    customerId INT,
    FOREIGN KEY (customerId) REFERENCES customer(customerId)
);

-- =============================================
-- 18. BẢNG CARTITEM
-- =============================================
CREATE TABLE IF NOT EXISTS cartitem (
    cartItemId INT AUTO_INCREMENT PRIMARY KEY,
    cartId INT NOT NULL,
    typeId INT,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    FOREIGN KEY (cartId) REFERENCES cart(cartId),
    FOREIGN KEY (typeId) REFERENCES product_type(typeId)
);

-- =============================================
-- 19. BẢNG WISHLIST
-- =============================================
CREATE TABLE IF NOT EXISTS wishlist (
    wishlistId INT AUTO_INCREMENT PRIMARY KEY,
    customerId INT,
    isDelete BIT NOT NULL,
    FOREIGN KEY (customerId) REFERENCES customer(customerId)
);

-- =============================================
-- 20. BẢNG LIÊN KẾT WISHLIST_PRODUCT (Many-to-Many)
-- =============================================
CREATE TABLE IF NOT EXISTS wishlist_product (
    wishlistId INT,
    productId INT,
    PRIMARY KEY (wishlistId, productId),
    FOREIGN KEY (wishlistId) REFERENCES wishlist(wishlistId),
    FOREIGN KEY (productId) REFERENCES product(productId)
);

-- =============================================
-- 21. BẢNG BLOG
-- =============================================
CREATE TABLE IF NOT EXISTS blog (
    blogId INT AUTO_INCREMENT PRIMARY KEY,
    postingDate DATE NOT NULL,
    blogTitle NVARCHAR(200) NOT NULL,
    content TEXT,
    approval BIT
);

-- =============================================
-- 22. BẢNG MARKETING_CAMPAIGN
-- =============================================
CREATE TABLE IF NOT EXISTS marketingcampaign (
    campaign_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    voucher_id INT UNIQUE,
    is_delete BIT NOT NULL,
    FOREIGN KEY (voucher_id) REFERENCES voucher(voucherId)
);

-- =============================================
-- 23. BẢNG CAMPAIGN_IMAGE
-- =============================================
CREATE TABLE IF NOT EXISTS campaignimage (
    image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    campaign_id BIGINT,
    image_path VARCHAR(255) NOT NULL,
    FOREIGN KEY (campaign_id) REFERENCES marketingcampaign(campaign_id)
);

-- =============================================
-- 24. BẢNG CONVERSATION
-- =============================================
CREATE TABLE IF NOT EXISTS conversation (
    conversationId INT AUTO_INCREMENT PRIMARY KEY,
    employeeId INT,
    customerId INT,
    FOREIGN KEY (employeeId) REFERENCES employee(employeeId),
    FOREIGN KEY (customerId) REFERENCES customer(customerId)
);

-- =============================================
-- 25. BẢNG MESSAGE
-- =============================================
CREATE TABLE IF NOT EXISTS message (
    messsageId INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT,
    timestamp DATETIME,
    userId INT,
    conversationId INT,
    FOREIGN KEY (userId) REFERENCES user(userId),
    FOREIGN KEY (conversationId) REFERENCES conversation(conversationId)
);

-- =============================================
-- 26. BẢNG QUESTION
-- =============================================
CREATE TABLE IF NOT EXISTS question (
    questionId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    content TEXT,
    timestamp DATETIME,
    isPending BIT,
    FOREIGN KEY (userId) REFERENCES customer(customerId)
);

-- =============================================
-- 27. BẢNG REVIEW
-- =============================================
CREATE TABLE IF NOT EXISTS review (
    reviewId INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255),
    createAt DATETIME NOT NULL,
    rating DOUBLE NOT NULL,
    productId INT NOT NULL,
    customerId INT NOT NULL,
    FOREIGN KEY (productId) REFERENCES product(productId),
    FOREIGN KEY (customerId) REFERENCES customer(customerId)
);

-- =============================================
-- 28. BẢNG PAYMENT_ACCOUNT
-- =============================================
CREATE TABLE IF NOT EXISTS payment_account (
    bankId INT AUTO_INCREMENT PRIMARY KEY,
    type NVARCHAR(100) NOT NULL,
    serialNumber NVARCHAR(50) NOT NULL,
    bank NVARCHAR(100) NOT NULL
);

-- =============================================
-- 29. BẢNG LIÊN KẾT PRODUCT_WISHLISTS (Many-to-Many từ Product)
-- =============================================
CREATE TABLE IF NOT EXISTS product_wishlists (
    product_productId INT,
    wishlists_wishlistId INT,
    PRIMARY KEY (product_productId, wishlists_wishlistId),
    FOREIGN KEY (product_productId) REFERENCES product(productId),
    FOREIGN KEY (wishlists_wishlistId) REFERENCES wishlist(wishlistId)
);

-- =============================================
-- INSERT DỮ LIỆU MẪU
-- =============================================

-- Insert Roles
INSERT INTO role (roleName) VALUES ('Admin'), ('Customer'), ('Employee');

-- Insert Admin User
INSERT INTO user (name, email, password, phone, address, gender, status, isDelete, roleId)
VALUES ('admin', 'admin@ergo.com', 'admin123', '0123456789', 'HCM City', 'Male', 'Active', 0, 1);

INSERT INTO administrator (administratorId) VALUES (1);

-- Insert Sample Employee
INSERT INTO user (name, email, password, phone, address, gender, status, isDelete, roleId)
VALUES ('employee1', 'employee1@ergo.com', 'emp123', '0123456788', 'HCM City', 'Female', 'Active', 0, 3);

INSERT INTO employee (employeeId) VALUES (2);

-- Insert Sample Customer
INSERT INTO user (name, email, password, phone, address, gender, status, isDelete, roleId)
VALUES ('customer1', 'customer1@gmail.com', 'cust123', '0123456787', 'Ha Noi', 'Male', 'Active', 0, 2);

INSERT INTO customer (customerId) VALUES (3);

-- Insert Sample Categories
INSERT INTO category (categoryName) VALUES 
('Bàn làm việc'),
('Ghế văn phòng'),
('Tủ hồ sơ'),
('Kệ sách');

-- Insert Sample Products
INSERT INTO product (name, descript, isDelete, categoryId) VALUES 
('Bàn làm việc Ergonomic', 'Bàn làm việc có thể điều chỉnh độ cao', 0, 1),
('Ghế công thái học', 'Ghế văn phòng hỗ trợ lưng tốt', 0, 2),
('Tủ hồ sơ 3 ngăn', 'Tủ đựng tài liệu văn phòng', 0, 3);

-- Insert Sample Product Types
INSERT INTO product_type (productId, color, length, width, height, weight, material, price, quantity) VALUES 
(1, 'Trắng', 120, 60, 75, 25, 'Gỗ MDF', 2500000, 50),
(1, 'Đen', 120, 60, 75, 25, 'Gỗ MDF', 2600000, 30),
(2, 'Đen', 65, 65, 120, 15, 'Da PU', 3500000, 40),
(3, 'Xám', 80, 45, 100, 30, 'Thép sơn tĩnh điện', 1800000, 25);

-- Insert Sample Product Images
INSERT INTO product_image (productImage, productId) VALUES 
('desk_white.jpg', 1),
('desk_black.jpg', 1),
('chair_black.jpg', 2),
('cabinet_gray.jpg', 3);

-- Insert Sample Blog
INSERT INTO blog (postingDate, blogTitle, content, approval) VALUES 
(CURDATE(), 'Cách chọn ghế văn phòng phù hợp', 'Nội dung bài viết về cách chọn ghế...', 1);

-- Insert Cart for Customer
INSERT INTO cart (customerId) VALUES (3);

-- Insert Wishlist for Customer  
INSERT INTO wishlist (customerId, isDelete) VALUES (3, 0);

SELECT 'Database setup completed successfully!' AS Status;
