package cnpm.ergo.controller.Customer;

import java.io.IOException;
import java.util.List;

import cnpm.ergo.entity.Product;
import cnpm.ergo.entity.User; // Giả sử bạn có entity User
import cnpm.ergo.service.implement.CustomerServiceImpl;
import cnpm.ergo.service.implement.WishlistServiceImpl;
import cnpm.ergo.service.interfaces.ICustomerService;
import cnpm.ergo.service.interfaces.IWishlistService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/customer/wishlist", "/customer/wishlist/delete"})
public class WishListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public IWishlistService wishlistService = new WishlistServiceImpl();
    public ICustomerService customerService = new CustomerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        
        // 1. Lấy thông tin user từ session (người dùng đã đăng nhập)
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account"); // "account" hoặc "user" tùy bạn đặt lúc login
        
        if (user == null) {
            // Nếu chưa đăng nhập thì redirect về trang login
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        int userId = user.getUserId();

        if (url.contains("/customer/wishlist/delete")) {
            deleteWishlist(req, resp, userId);
        } else if (url.contains("/customer/wishlist")) {
            showWishlist(req, resp, userId);
        }
    }

    private void showWishlist(HttpServletRequest req, HttpServletResponse resp, int userId) 
            throws ServletException, IOException {
        // Lưu ý: Interface trả về List<Product>, phải khai báo đúng kiểu
        List<Product> list = wishlistService.getAllWishlistByUserId(userId);
        
        req.setAttribute("wishlist", list);
        req.getRequestDispatcher("/customer/views/wishlist.jsp").forward(req, resp);
    }

    private void deleteWishlist(HttpServletRequest req, HttpServletResponse resp, int userId) 
            throws IOException {
        try {
            // Lấy productId từ parameter trên URL (?id=...)
            int productId = Integer.parseInt(req.getParameter("id"));
            
            // Gọi đúng hàm trong Interface: deleteFromWishlist(int userId, int productId)
            wishlistService.deleteFromWishlist(userId, productId);
            
            // Sau khi xóa xong, nên Redirect (chuyển hướng) để tránh lỗi lặp lại hành động khi F5
            resp.sendRedirect(req.getContextPath() + "/customer/wishlist");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/customer/wishlist?error=1");
        }
    }
}