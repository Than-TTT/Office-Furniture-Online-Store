package controller.Marketing.Voucher;

import model.ProductType;
import model.VoucherByPrice;
import model.VoucherByProduct;
import service.VoucherByPriceServiceImpl;
import service.VoucherByProductServiceImpl;
import service.ProductTypeServiceImpl;
import service.IProductTypeService;
import service.IVoucherByPriceService;
import service.IVoucherByProductService;
import jakarta.persistence.EntityManager;
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

@WebServlet(urlPatterns = "/admin/addVoucher")
public class AddVoucherController extends HttpServlet {
    IVoucherByProductService voucherByProduct;
    IVoucherByPriceService voucherByPrice;
    private EntityManager entityManager;

    @Override
    public void init() throws ServletException {
        // Initialize the service implementation
        voucherByProduct = new VoucherByProductServiceImpl();
        voucherByPrice = new VoucherByPriceServiceImpl();


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/login");
//            return;
//        }
        try {
            // Lấy loại voucher
            String voucherType = request.getParameter("voucherType");

            // Lấy thông tin chung từ form
            String code = request.getParameter("code");
            double discount = Double.parseDouble(request.getParameter("discount"));
            Date dateStart = parseDate(request.getParameter("dateStart"));
            Date dateEnd = parseDate(request.getParameter("dateEnd"));
            String[] selectedProductTypes = request.getParameterValues("productTypes");
            List<ProductType> productTypeList = new ArrayList<>();
            IProductTypeService productTypeService = new ProductTypeServiceImpl();
            if (selectedProductTypes != null) {
                for(int i=0; i< selectedProductTypes.length;i++)
                {
                    ProductType type = productTypeService.getProductTypeById((Integer.parseInt(selectedProductTypes[i])));
                    productTypeList.add(type);
                }
            }

            try {
                if ("byPrice".equals(voucherType)) {
                    // Tạo VoucherByPrice
                    double lowerbound = Double.parseDouble(request.getParameter("lowerbound"));
                    VoucherByPrice voucher = new VoucherByPrice();
                    voucher.setCode(code);
                    voucher.setDiscount(discount);
                    voucher.setDateStart(dateStart);
                    voucher.setDateEnd(dateEnd);
                    voucher.setLowerbound(lowerbound);

                    // Thêm vào cơ sở dữ liệu
                    voucherByPrice.insert(voucher);
                } else if ("byProduct".equals(voucherType)) {
                    // Tạo VoucherByProduct
                    VoucherByProduct voucher = new VoucherByProduct();
                    voucher.setCode(code);
                    voucher.setDiscount(discount);
                    voucher.setDateStart(dateStart);
                    voucher.setDateEnd(dateEnd);
                    voucher.setProductTypes(productTypeList);

                    // Thêm vào cơ sở dữ liệu
                    voucherByProduct.insert(voucher);
                }


                // Redirect to the employee management page upon success
                response.sendRedirect(request.getContextPath() + "/admin/marketing");

            } catch (Exception e) {
                e.printStackTrace();
                // Forward the error details to an error page
                request.setAttribute("errorMessage", "Failed to add the voucher. Please try again.");
                request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
            }
        }catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to add the voucher. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
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
