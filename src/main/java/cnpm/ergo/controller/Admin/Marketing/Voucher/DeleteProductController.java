package cnpm.ergo.controller.Admin.Marketing.Voucher;

import cnpm.ergo.entity.VoucherByProduct;
import cnpm.ergo.service.implement.IVoucherByPriceServiceImpl;
import cnpm.ergo.service.implement.IVoucherByProductServiceImpl;
import cnpm.ergo.service.interfaces.IVoucherByPriceService;
import cnpm.ergo.service.interfaces.IVoucherByProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/voucher/deleteProduct")
public class DeleteProductController extends HttpServlet {
    IVoucherByProductService voucherByProduct;

    @Override
    public void init() throws ServletException {
        // Initialize the service implementation
        voucherByProduct = new IVoucherByProductServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/login");
//            return;
//        }
        try {
            System.out.println("xoa san pham");
            System.out.println(request.getParameter("voucherId").isBlank());
            System.out.println(request.getParameter("voucherId"));
            int voucherID = Integer.parseInt(request.getParameter("voucherId"));
            VoucherByProduct product = voucherByProduct.findById(voucherID);
            product.setDelete(true);
            voucherByProduct.update(product);
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
        } catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to delete. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }


}

