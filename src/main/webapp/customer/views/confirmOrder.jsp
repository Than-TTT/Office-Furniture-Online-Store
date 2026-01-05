<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cảm ơn</title>
    <style>
        /* Cấu hình chung cho trang */
        body {
            font-family: 'Helvetica Neue', Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            box-sizing: border-box;
        }

        /* Định dạng cho phần nội dung */
        .content {
            text-align: center;
            max-width: 600px;
            width: 100%;
            margin: 0 auto;
            padding: 40px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        /* Tiêu đề */
        h1 {
            font-size: 3em;
            color: #111111;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 2px;
            margin-bottom: 20px;
            text-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        /* Thông báo */
        p {
            font-size: 1.3em;
            color: #666666;
            margin-bottom: 40px;
            font-weight: 300;
        }

        /* Nút Mua Ngay */
        .buy-button {
            display: block;
            width: 100%;
            padding: 12px 20px;
            font-size: 1rem;
            font-weight: bold;
            color: #fff;
            background: #333;
            border: none;
            border-radius: 8px;
            text-align: center;
            cursor: pointer;
            transition: background 0.3s ease, transform 0.2s ease;
        }

        /* Hiệu ứng khi hover và nhấn */
        .buy-button:hover {
            background: #000;
            transform: scale(1.05);
        }

        .buy-button:active {
            background: #555;
        }
    </style>
</head>
<body>

    <!-- Nội dung chính -->
    <div class="content" style="text-align: center; padding: 20px; background-color: #fff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
        <!-- Tiêu đề -->
        <h1 style="font-size: 2rem; color: #333;">Cảm ơn bạn đã đặt hàng!</h1>

        <!-- Dòng thông báo -->
        <p style="font-size: 1rem; color: #555;">Đơn hàng của bạn đã được xác nhận. Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất.</p>

        <!-- Nút Quay về trang chủ -->
        <button type="submit" 
                style="display: inline-block; padding: 12px 20px; font-size: 1rem; font-weight: bold; color: #fff; background: #333; border: none; border-radius: 8px; text-align: center; cursor: pointer; margin-top: 20px; transition: background 0.3s ease, transform 0.2s ease; width: auto; max-width: 250px;" onclick="window.location.href='${pageContext.request.contextPath}/customer/home';">
            Quay về trang chủ
        </button>
    </div>

</body>
</html>


