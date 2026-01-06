package cnpm.ergo.controller.Customer.Customer;

import cnpm.ergo.entity.*;
import cnpm.ergo.service.implement.ProductServiceImpl;
import cnpm.ergo.service.implement.ReviewServiceImpl;
import cnpm.ergo.service.interfaces.IOrderService;
import cnpm.ergo.service.interfaces.IProductService;
import cnpm.ergo.service.interfaces.IReviewService;
import cnpm.ergo.service.implement.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "ReviewController", value = "/review/add")
public class ReviewController extends HttpServlet {
    private final IReviewService reviewService = new ReviewServiceImpl();
    private final IProductService productService = new ProductServiceImpl();
    private final IOrderService orderService = new OrderServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            // Lấy dữ liệu từ form
//            int productId = Integer.parseInt(request.getParameter("productId"));
            //test
            int productId =2;
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String content = request.getParameter("content");
            int rating = Integer.parseInt(request.getParameter("rating"));

            // Hiển thị debug
            System.out.println("Product ID: " + productId);
            System.out.println("Order ID: " + orderId);
            System.out.println("Content: " + content);
            System.out.println("Rating: " + rating);
            // Xây dựng đối tượng review
            Product product = productService.getProductById(productId);
            Customer customer = (Customer) request.getSession().getAttribute("customer");
            Order order = orderService.findById(orderId);
            if (product != null && customer != null) {
                Review review = new Review();
                review.setContent(content);
                review.setRating(rating);
                review.setCreateAt(LocalDateTime.now());
                review.setProduct(product);
                review.setCustomer(customer);
                review.setOrder(order); 

                // Lưu review
                reviewService.insert(review);
                // Redirect về `order-history` với thông báo thành công
                response.sendRedirect(request.getContextPath() + "/customer/order-history?reviewStatus=success");
            } else {
                // Redirect về `order-history` với thông báo lỗi nếu dữ liệu không hợp lệ
                response.sendRedirect(request.getContextPath() + "/customer/order-history?reviewStatus=error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect về `order-history` với thông báo lỗi
            response.sendRedirect(request.getContextPath() + "/customer/order-history?reviewStatus=error");
        }
    }
}
