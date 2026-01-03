package controller;

import service.IReviewService;
import service.ReviewServiceImpl;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/customer/add-review")
public class ReviewController extends HttpServlet {

    private IReviewService reviewService = new ReviewServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        User customer = (User) session.getAttribute("customer");

        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");
            
            boolean success = reviewService.addReview(
                customer.getUserId(), 
                productId, 
                rating, 
                comment
            );

            if (success) {
                response.sendRedirect(request.getContextPath() + 
                    "/customer/order-history?success=review_added");
            } else {
                response.sendRedirect(request.getContextPath() + 
                    "/customer/order-history?error=review_failed");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + 
                "/customer/order-history?error=invalid_data");
        }
    }
}