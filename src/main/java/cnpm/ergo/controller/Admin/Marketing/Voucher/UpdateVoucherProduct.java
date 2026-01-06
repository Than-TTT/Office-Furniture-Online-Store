package cnpm.ergo.controller.Admin.Marketing.Voucher;

import cnpm.ergo.entity.ProductType;
import cnpm.ergo.entity.VoucherByPrice;
import cnpm.ergo.entity.VoucherByProduct;
import cnpm.ergo.service.implement.IVoucherByPriceServiceImpl;
import cnpm.ergo.service.implement.IVoucherByProductServiceImpl;
import cnpm.ergo.service.implement.ProductTypeServiceImpl;
import cnpm.ergo.service.interfaces.IProductTypeService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/voucher/editProduct"})
public class UpdateVoucherProduct extends HttpServlet {

    IVoucherByProductService voucherByProductService;
    IProductTypeService productTypeService;

    @Override
    public void init() throws ServletException {
        voucherByProductService = new IVoucherByProductServiceImpl();
        productTypeService = new ProductTypeServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lấy dữ liệu theo đúng "name" trong Modal JSP
        String idStr = request.getParameter("voucherId");
        String code = request.getParameter("editVoucherCode");
        String discountStr = request.getParameter("editVoucherDiscount");
        String dateStartStr = request.getParameter("editVoucherDateStart");
        String dateEndStr = request.getParameter("editVoucherDateEnd");
        String[] selectedProductTypes = request.getParameterValues("productTypes");

        try {
            int voucherID = Integer.parseInt(idStr);
            double discount = (discountStr != null && !discountStr.isEmpty()) ? Double.parseDouble(discountStr) : 0;
            Date dateStart = parseDate(dateStartStr);
            Date dateEnd = parseDate(dateEndStr);

            // 2. Xử lý danh sách sản phẩm áp dụng
            List<ProductType> productTypeList = new ArrayList<>();
            if (selectedProductTypes != null) {
                for (String typeId : selectedProductTypes) {
                    ProductType type = productTypeService.getProductTypeById(Integer.parseInt(typeId));
                    if (type != null) productTypeList.add(type);
                }
            }

            // 3. Cập nhật đối tượng
            VoucherByProduct voucher = voucherByProductService.findById(voucherID);
            if (voucher != null) {
                voucher.setCode(code.toUpperCase());
                voucher.setDiscount(discount);
                voucher.setDateStart(dateStart);
                voucher.setDateEnd(dateEnd);
                voucher.setProductTypes(productTypeList);

                voucherByProductService.update(voucher);
            }

            // 4. Redirect về trang marketing, mở tab voucher
            response.sendRedirect(request.getContextPath() + "/admin/marketing?tab=voucher");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/marketing?error=1");
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
}