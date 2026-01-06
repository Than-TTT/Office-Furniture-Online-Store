package cnpm.ergo.controller.Admin.Marketing.Voucher;

import cnpm.ergo.entity.VoucherByPrice;
import cnpm.ergo.service.implement.IVoucherByPriceServiceImpl;
import cnpm.ergo.service.interfaces.IVoucherByPriceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = {"/admin/voucher/editPrice"})
public class UpdateVoucherPrice extends HttpServlet {

    IVoucherByPriceService voucherByPrice;

    @Override
    public void init() throws ServletException {
        voucherByPrice = new IVoucherByPriceServiceImpl();
    }

    // doGet bây giờ không còn cần thiết nếu bạn đã dùng Modal tích hợp trong marketing.jsp
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/admin/marketing");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Lấy dữ liệu từ Request (Lưu ý: Tên tham số phải khớp với thẻ <input name="..."> trong JSP)
        String idStr = request.getParameter("voucherId");
        String code = request.getParameter("editVoucherCode"); // Đổi từ editVoucherCodePrice -> editVoucherCode
        String discountStr = request.getParameter("editVoucherDiscount"); // Đổi từ editVoucherDiscountPrice -> editVoucherDiscount
        String dateStartStr = request.getParameter("editVoucherDateStart"); // Đổi từ editVoucherDateStartPrice -> editVoucherDateStart
        String dateEndStr = request.getParameter("editVoucherDateEnd"); // Đổi từ editVoucherDateEndPrice -> editVoucherDateEnd
        String lowerboundStr = request.getParameter("editLowerbound"); // Đã khớp với JSP

        try {
            // 2. Ép kiểu và kiểm tra Null để tránh sập server
            int voucherID = Integer.parseInt(idStr);
            double discount = (discountStr != null) ? Double.parseDouble(discountStr) : 0;
            double lowerbound = (lowerboundStr != null) ? Double.parseDouble(lowerboundStr) : 0;
            Date dateStart = parseDate(dateStartStr);
            Date dateEnd = parseDate(dateEndStr);

            // 3. Xử lý Update
            VoucherByPrice voucher = voucherByPrice.findById(voucherID);
            if (voucher != null) {
                voucher.setCode(code);
                voucher.setDiscount(discount);
                voucher.setDateStart(dateStart);
                voucher.setDateEnd(dateEnd);
                voucher.setLowerbound(lowerbound);

                voucherByPrice.update(voucher);
            }

            // 4. Quay lại trang marketing với tham số tab để mở lại tab voucher
            response.sendRedirect(request.getContextPath() + "/admin/marketing?tab=voucher");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi cập nhật Voucher: " + e.getMessage());
            // Nếu lỗi, quay lại trang quản lý thay vì trang /test
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
        }
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}