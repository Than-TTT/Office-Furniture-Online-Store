package cnpm.ergo.controller.Admin.Marketing.Voucher;

import cnpm.ergo.entity.VoucherByPrice;
import cnpm.ergo.service.implement.IVoucherByPriceServiceImpl;
import cnpm.ergo.service.interfaces.IVoucherByPriceService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = "/admin/voucher/editPrice")
public class UpdateVoucherPrice extends HttpServlet {

    private IVoucherByPriceService voucherByPrice;

    @Override
    public void init() throws ServletException {
        voucherByPrice = new IVoucherByPriceServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = safeTrim(request.getParameter("voucherId"));
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
            return;
        }

        int voucherId;
        try {
            voucherId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
            return;
        }

        VoucherByPrice voucher = voucherByPrice.findById(voucherId);
        if (voucher == null) {
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = voucher.getDateStart() != null ? sdf.format(voucher.getDateStart()) : "";
        String dateEnd = voucher.getDateEnd() != null ? sdf.format(voucher.getDateEnd()) : "";

        request.setAttribute("editvoucherId", voucher.getVoucherId());
        request.setAttribute("editVoucherCodePrice", voucher.getCode());
        request.setAttribute("editVoucherDiscountPrice", voucher.getDiscount());
        request.setAttribute("editVoucherDateStartPrice", dateStart);
        request.setAttribute("editVoucherDateEndPrice", dateEnd);
        // adjust this attribute name if your JSP expects different name for lowerbound
        request.setAttribute("editVoucherLowerbound", voucher.getLowerbound());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/views/editVoucherPrice.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // safe parsing and null checks
        try {
            request.setCharacterEncoding("UTF-8");

            String idParam = safeTrim(request.getParameter("voucherId"));
            if (idParam == null) {
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
                return;
            }
            int voucherID = Integer.parseInt(idParam);

            VoucherByPrice voucher = voucherByPrice.findById(voucherID);
            if (voucher == null) {
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
                return;
            }

            String code = safeTrim(request.getParameter("editVoucherCodePrice"));
            String discountStr = safeTrim(request.getParameter("editVoucherDiscountPrice"));
            String dateStartStr = safeTrim(request.getParameter("editVoucherDateStartPrice"));
            String dateEndStr = safeTrim(request.getParameter("editVoucherDateEndPrice"));
            String lowerboundStr = safeTrim(request.getParameter("editVoucherLowerbound"));

            if (code != null) voucher.setCode(code);

            if (discountStr != null && !discountStr.isEmpty()) {
                try { voucher.setDiscount(Double.parseDouble(discountStr)); } catch (NumberFormatException ignored) {}
            }

            Date ds = parseDate(dateStartStr);
            Date de = parseDate(dateEndStr);
            voucher.setDateStart(ds);
            voucher.setDateEnd(de);

            if (lowerboundStr != null && !lowerboundStr.isEmpty()) {
                try { voucher.setLowerbound(Double.parseDouble(lowerboundStr)); } catch (NumberFormatException ignored) {}
            }

            voucherByPrice.update(voucher);
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to edit the voucher. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }
}