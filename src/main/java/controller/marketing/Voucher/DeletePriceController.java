package controller.marketing.Voucher;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.VoucherByPrice;
import service.IVoucherByPriceService;
import service.IVoucherByPriceServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/voucher/deletePrice")
public class DeletePriceController extends HttpServlet {
    IVoucherByPriceService voucherByPrice;

    @Override
    public void init() throws ServletException {
        voucherByPrice = new IVoucherByPriceServiceImpl();
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

            System.out.println("Deleting voucher: " + voucherIdParam);
            int voucherId = Integer.parseInt(voucherIdParam);

            VoucherByPrice voucher = voucherByPrice.findById(voucherId);
            if (voucher == null) {
                System.out.println("Voucher not found with id: " + voucherId);
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
                return;
            }

            voucher.setDelete(true);
            voucherByPrice.update(voucher);
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
