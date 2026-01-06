package cnpm.ergo.controller.Admin.Marketing.Voucher;

import cnpm.ergo.entity.Voucher;
import cnpm.ergo.entity.VoucherByPrice;
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
import java.sql.Date;

@WebServlet(urlPatterns = "/admin/voucher/add")
public class AddVoucherController extends HttpServlet {
    private IVoucherByPriceService voucherByPriceService = new IVoucherByPriceServiceImpl();
    private IVoucherByProductService voucherByProductService = new IVoucherByProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            String voucherType = request.getParameter("voucherType");
            String code = request.getParameter("code");
            String discountStr = request.getParameter("discount");
            String dateStartStr = request.getParameter("dateStart");
            String dateEndStr = request.getParameter("dateEnd");

            double discount = 0.0;
            try { discount = Double.parseDouble(discountStr); } catch (Exception ignored) {}

            Date dateStart = null;
            Date dateEnd = null;
            try { if (dateStartStr != null && !dateStartStr.isEmpty()) dateStart = Date.valueOf(dateStartStr); } catch (Exception ignored) {}
            try { if (dateEndStr != null && !dateEndStr.isEmpty()) dateEnd = Date.valueOf(dateEndStr); } catch (Exception ignored) {}

            if ("byPrice".equalsIgnoreCase(voucherType)) {
                VoucherByPrice v = new VoucherByPrice();
                v.setCode(code);
                v.setDiscount(discount);
                v.setDateStart(dateStart);
                v.setDateEnd(dateEnd);
                String lowerboundStr = request.getParameter("lowerbound");
                try { v.setLowerbound(Double.parseDouble(lowerboundStr)); } catch (Exception ignored) {}
                v.setDelete(false);
                v.setMarketingCampaign(null);
                voucherByPriceService.insert(v);
            } else {
                // byProduct (default)
                VoucherByProduct v = new VoucherByProduct();
                v.setCode(code);
                v.setDiscount(discount);
                v.setDateStart(dateStart);
                v.setDateEnd(dateEnd);
                v.setDelete(false);
                v.setMarketingCampaign(null);
                // productTypes param may be an array of ids; associate later if needed
                voucherByProductService.insert(v);
            }

            response.sendRedirect(request.getContextPath() + "/admin/marketing");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to add voucher: " + e.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}