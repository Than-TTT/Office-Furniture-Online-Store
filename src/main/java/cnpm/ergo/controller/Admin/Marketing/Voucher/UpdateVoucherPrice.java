package cnpm.ergo.controller.Admin.Marketing.Voucher;

import cnpm.ergo.entity.VoucherByPrice;
import cnpm.ergo.entity.VoucherByProduct;
import cnpm.ergo.service.implement.IVoucherByPriceServiceImpl;
import cnpm.ergo.service.implement.IVoucherByProductServiceImpl;
import cnpm.ergo.service.interfaces.IVoucherByPriceService;
import cnpm.ergo.service.interfaces.IVoucherByProductService;
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

@WebServlet(urlPatterns = {"/admin/voucher/editPrice", "/admin/views/voucher/editPrice"})
public class UpdateVoucherPrice extends HttpServlet {

    IVoucherByProductService voucherByProduct;
    IVoucherByPriceService voucherByPrice;

    @Override
    public void init() throws ServletException {
        // Initialize the service implementation
        voucherByPrice = new IVoucherByPriceServiceImpl();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy giá trị từ tham số của form
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        String code = request.getParameter("voucherCode");
        double discount = Double.parseDouble(request.getParameter("voucherDiscount"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = parseDate(request.getParameter("voucherDateStart"));
        Date dateEnd = parseDate(request.getParameter("voucherDateEnd"));
        String dateStartFormated = sdf.format(dateStart);
        String dateEndFormated = sdf.format(dateEnd);
        // Gửi giá trị vào trang editVoucherPrice.jsp
        request.setAttribute("editvoucherId", voucherId);
        request.setAttribute("editVoucherCodePrice", code);
        request.setAttribute("editVoucherDiscountPrice", discount);
        request.setAttribute("editVoucherDateStartPrice", dateStartFormated);
        request.setAttribute("editVoucherDateEndPrice", dateEndFormated);

        // Chuyển hướng đến trang editVoucherPrice
//        RequestDispatcher dispatcher = request.getRequestDispatcher("views/editVoucherPrice.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/views/editVoucherPrice.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/login");
//            return;
//        }
        int voucherID = Integer.parseInt(request.getParameter("voucherId"));
        System.out.println("testID" + voucherID);

        String code = request.getParameter("editVoucherCodePrice");
        System.out.println("Voucher Code: " + code);

        double discount = Double.parseDouble(request.getParameter("editVoucherDiscountPrice"));
        System.out.println("Discount: " + discount);

        Date dateStart = parseDate(request.getParameter("editVoucherDateStartPrice"));
        System.out.println("Start Date: " + dateStart);

        Date dateEnd = parseDate(request.getParameter("editVoucherDateEndPrice"));
        System.out.println("End Date: " + dateEnd);

        double lowerbound = Double.parseDouble(request.getParameter("editVoucherLowerbound"));
        System.out.println("Minimum Order Value: " + lowerbound);

// In ra các giá trị của các biến

        try {
            // Retrieve form data from the request
            // Validate input fields (optional, add your validation logic here)
                    VoucherByPrice voucher = voucherByPrice.findById(voucherID);
                    if (voucher == null) {
                        response.sendRedirect(request.getContextPath() + "/admin/marketing");
                        return;
                        }
                    voucher.setCode(code);
                    voucher.setDiscount(discount);
                    voucher.setDateStart(dateStart);
                    voucher.setDateEnd(dateEnd);
                    voucher.setLowerbound(lowerbound);

//                     Thêm vào cơ sở dữ liệu
                    voucherByPrice.update(voucher);


                // Redirect to the employee management page upon success
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
        }catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to edit the voucher. Please try again.");
            request.getRequestDispatcher("/test").forward(request, response);
        }

    }
    private Date parseDate (String dateStr){
        try {
            // Sử dụng định dạng yyyy-MM-dd
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Trả về null nếu parse thất bại
        }
    }
}
