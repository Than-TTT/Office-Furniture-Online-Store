package cnpm.ergo.controller.Admin.Marketing.Voucher;

import cnpm.ergo.entity.VoucherByProduct;

import cnpm.ergo.service.implement.IVoucherByProductServiceImpl;
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

        voucherByProduct = new IVoucherByProductServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String voucherIdParam = safeTrim(request.getParameter("voucherId"));
            if (voucherIdParam == null || voucherIdParam.isEmpty()) {
                System.out.println("voucherId is null or empty");
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
                return;
            }

            System.out.println("Deleting voucher by product: " + voucherIdParam);
            int voucherId = Integer.parseInt(voucherIdParam);

            VoucherByProduct voucher = voucherByProduct.findById(voucherId);
            if (voucher == null) {
                System.out.println("Voucher not found with id: " + voucherId);
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
                return;
            }

            voucher.setDelete(true);
            voucherByProduct.update(voucher);
            System.out.println("Voucher deleted successfully: " + voucherId);

            response.sendRedirect(request.getContextPath() + "/admin/marketing");
        } catch (NumberFormatException e) {
            System.err.println("Invalid voucherId format: " + e.getMessage());
            request.setAttribute("errorMessage", "Invalid voucher ID format.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to delete voucher. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }
}
