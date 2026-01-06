package cnpm.ergo.controller.Admin.Marketing.Voucher;

import cnpm.ergo.entity.ProductType;
import cnpm.ergo.entity.VoucherByProduct;
import cnpm.ergo.service.implement.IVoucherByProductServiceImpl;
import cnpm.ergo.service.implement.ProductTypeServiceImpl;
import cnpm.ergo.service.interfaces.IProductTypeService;
import cnpm.ergo.service.interfaces.IVoucherByProductService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/voucher/editProduct", "/admin/views/voucher/editProduct"})
public class UpdateVoucherProduct extends HttpServlet {

    IVoucherByProductService voucherByProduct;

    @Override
    public void init() throws ServletException {
        voucherByProduct = new IVoucherByProductServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String voucherIdParam = safeTrim(request.getParameter("voucherId"));
            if (voucherIdParam == null || voucherIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
                return;
            }

            int voucherId = Integer.parseInt(voucherIdParam);
            VoucherByProduct voucher = voucherByProduct.findById(voucherId);
            if (voucher == null) {
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStartFormatted = voucher.getDateStart() != null ? sdf.format(voucher.getDateStart()) : "";
            String dateEndFormatted = voucher.getDateEnd() != null ? sdf.format(voucher.getDateEnd()) : "";

            request.setAttribute("editvoucherId", voucher.getVoucherId());
            request.setAttribute("editVoucherCodeProduct", voucher.getCode());
            request.setAttribute("editVoucherDiscountProduct", voucher.getDiscount());
            request.setAttribute("editVoucherDateStartProduct", dateStartFormatted);
            request.setAttribute("editVoucherDateEndProduct", dateEndFormatted);
            
            // Pass currently selected product type IDs
            List<Integer> selectedTypeIds = new ArrayList<>();
            if (voucher.getProductTypes() != null) {
                for (ProductType pt : voucher.getProductTypes()) {
                    selectedTypeIds.add(pt.getTypeId());
                }
            }
            request.setAttribute("selectedTypeIds", selectedTypeIds);

            IProductTypeService productTypeService = new ProductTypeServiceImpl();
            List<ProductType> productTypes = productTypeService.getAllProductTypes();
            request.setAttribute("productTypes", productTypes);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/views/editVoucherProduct.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to load voucher: " + e.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            String voucherIdParam = safeTrim(request.getParameter("voucherId"));
            if (voucherIdParam == null || voucherIdParam.isEmpty()) {
                throw new IllegalArgumentException("Missing voucherId");
            }
            int voucherID = Integer.parseInt(voucherIdParam);
            System.out.println("testID: " + voucherID);

            VoucherByProduct voucher = voucherByProduct.findById(voucherID);
            if (voucher == null) {
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
                return;
            }

            String code = safeTrim(request.getParameter("editVoucherCodeProduct"));
            if (code != null) voucher.setCode(code);
            System.out.println("Voucher Code: " + code);

            String discountStr = safeTrim(request.getParameter("editVoucherDiscountProduct"));
            if (discountStr != null && !discountStr.isEmpty()) {
                try {
                    double discount = Double.parseDouble(discountStr);
                    voucher.setDiscount(discount);
                    System.out.println("Discount: " + discount);
                } catch (NumberFormatException ignored) {
                    System.err.println("Invalid discount format");
                }
            }

            String dateStartStr = safeTrim(request.getParameter("editVoucherDateStartProduct"));
            Date dateStart = parseDate(dateStartStr);
            if (dateStart != null) {
                voucher.setDateStart(dateStart);
                System.out.println("Start Date: " + dateStart);
            }

            String dateEndStr = safeTrim(request.getParameter("editVoucherDateEndProduct"));
            Date dateEnd = parseDate(dateEndStr);
            if (dateEnd != null) {
                voucher.setDateEnd(dateEnd);
                System.out.println("End Date: " + dateEnd);
            }

            String[] selectedProductTypes = request.getParameterValues("productTypes");
            List<ProductType> productTypeList = new ArrayList<>();
            IProductTypeService productTypeService = new ProductTypeServiceImpl();
            if (selectedProductTypes != null && selectedProductTypes.length > 0) {
                for (String typeIdStr : selectedProductTypes) {
                    try {
                        int typeId = Integer.parseInt(typeIdStr);
                        ProductType type = productTypeService.getProductTypeById(typeId);
                        if (type != null) {
                            productTypeList.add(type);
                        }
                    } catch (NumberFormatException ignored) {
                        System.err.println("Invalid product type ID: " + typeIdStr);
                    }
                }
            }
            voucher.setProductTypes(productTypeList);
            System.out.println("Selected product types: " + productTypeList.size());

            voucherByProduct.update(voucher);
            System.out.println("Voucher updated successfully");

            response.sendRedirect(request.getContextPath() + "/admin/marketing");
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
            request.setAttribute("errorMessage", "Invalid input format. Please check your data.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to update the voucher. Please try again: " + e.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            System.err.println("Failed to parse date: " + dateStr);
            return null;
        }
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }
}