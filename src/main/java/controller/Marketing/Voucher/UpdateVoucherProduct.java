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

@WebServlet(urlPatterns = {"/admin/voucher/editProduct", "/admin/views/voucher/editProduct"})
public class UpdateVoucherProduct extends HttpServlet {

    IVoucherByProductService voucherByProduct;

    @Override
    public void init() throws ServletException {
        // Initialize the service implementation
        voucherByProduct = new VoucherByProductServiceImpl();
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
        // Gửi giá trị vào trang editVoucherProduct.jsp
        request.setAttribute("editvoucherId", voucherId);
        request.setAttribute("editVoucherCodeProduct", code);
        request.setAttribute("editVoucherDiscountProduct", discount);
        request.setAttribute("editVoucherDateStartProduct", dateStartFormated);
        request.setAttribute("editVoucherDateEndProduct", dateEndFormated);



        IProductTypeService productTypeService = new ProductTypeServiceImpl();
        List<ProductType> productTypes = productTypeService.getAllProductTypes();
        request.setAttribute("productTypes", productTypes);

        // Chuyển hướng đến trang editVoucherProduct
//        RequestDispatcher dispatcher = request.getRequestDispatcher("views/editVoucherProduct.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/views/editVoucherProduct.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/login");
//            return;
//        }
        int voucherID = Integer.parseInt(request.getParameter("voucherId"));
        System.out.println("testID" + voucherID);

        String code = request.getParameter("editVoucherCodeProduct");
        System.out.println("Voucher Code: " + code);

        double discount = Double.parseDouble(request.getParameter("editVoucherDiscountProduct"));
        System.out.println("Discount: " + discount);

        Date dateStart = parseDate(request.getParameter("editVoucherDateStartProduct"));
        System.out.println("Start Date: " + dateStart);

        Date dateEnd = parseDate(request.getParameter("editVoucherDateEndProduct"));
        System.out.println("End Date: " + dateEnd);

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
// In ra các giá trị của các biến

        try {
            // Retrieve form data from the request
            // Validate input fields (optional, add your validation logic here)
            VoucherByProduct voucher = voucherByProduct.findById(voucherID);
            if (voucher == null) {
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
                return;
            }
            voucher.setCode(code);
            voucher.setDiscount(discount);
            voucher.setDateStart(dateStart);
            voucher.setDateEnd(dateEnd);
            voucher.setProductTypes(productTypeList);

//                     Thêm vào cơ sở dữ liệu
            voucherByProduct.update(voucher);


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
