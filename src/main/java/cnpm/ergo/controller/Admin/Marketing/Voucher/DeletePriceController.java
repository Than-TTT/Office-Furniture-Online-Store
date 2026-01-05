package cnpm.ergo.controller.Admin.Marketing.Voucher;

import cnpm.ergo.service.implement.IVoucherByPriceServiceImpl;
import cnpm.ergo.service.interfaces.IVoucherByPriceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/voucher/deletePrice")
public class DeletePriceController extends HttpServlet {
    IVoucherByPriceService voucherByPrice;

    @Override
    public void init() throws ServletException {
        // Initialize the service implementation
        voucherByPrice = new IVoucherByPriceServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/login");
//            return;
//        }
        try {
            System.out.println("xoa gia");
            System.out.println(request.getParameter("voucherId").isBlank());
            int voucherID = Integer.parseInt(request.getParameter("voucherId"));
            System.out.println(voucherID);

            voucherByPrice.delete(voucherByPrice.findById(voucherID));

            response.sendRedirect(request.getContextPath() + "/admin/marketing");


        } catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to delete. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }


}
