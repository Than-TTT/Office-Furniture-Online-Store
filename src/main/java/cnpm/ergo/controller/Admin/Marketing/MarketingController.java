// ...existing code...
package cnpm.ergo.controller.Admin.Marketing;

import cnpm.ergo.entity.*;
import cnpm.ergo.service.implement.IVoucherByPriceServiceImpl;
import cnpm.ergo.service.implement.IVoucherByProductServiceImpl;
import cnpm.ergo.service.implement.MarketingCampaignServiceImpl;
import cnpm.ergo.service.implement.ProductTypeServiceImpl;
import cnpm.ergo.service.interfaces.IMarketingCampaignService;
import cnpm.ergo.service.interfaces.IProductTypeService;
import cnpm.ergo.service.interfaces.IVoucherByPriceService;
import cnpm.ergo.service.interfaces.IVoucherByProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/admin/marketing")
public class MarketingController extends HttpServlet {
    IMarketingCampaignService marketingCampaignService = new MarketingCampaignServiceImpl();
    IVoucherByPriceService voucherByPriceService = new IVoucherByPriceServiceImpl();
    IVoucherByProductService voucherByProductService = new IVoucherByProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if admin is logged in
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        // Get vouchers
        List<VoucherByPrice> voucherByPriceList = voucherByPriceService.findAll();
        List<VoucherByProduct> voucherByProductList = voucherByProductService.findAll();
        List<Voucher> vouchers = new ArrayList<>();
        vouchers = createVouchers(vouchers);

        List<MarketingCampaign> marketingCampaignList = marketingCampaignService.findAllMarketingCampaign();

        IProductTypeService productTypeService = new ProductTypeServiceImpl();
        List<ProductType> productTypes = productTypeService.getAllProductTypes();

        request.setAttribute("vouchers", vouchers);
        request.setAttribute("productTypes", productTypes);
        request.setAttribute("campaigns", marketingCampaignList);

        request.getRequestDispatcher("views/marketing.jsp").forward(request, response);
    }

    private List<Voucher> createVouchers(List<Voucher> vouchers)
    {
        List<VoucherByPrice> voucherByPriceList = voucherByPriceService.findAll();
        List<VoucherByProduct> voucherByProductList = voucherByProductService.findAll();

        for(VoucherByPrice voucherByPrice : voucherByPriceList){
            vouchers.add(voucherByPrice);
        }
        for(VoucherByProduct voucher : voucherByProductList){
            vouchers.add(voucher);
        }
        return vouchers;
    }
}
// ...existing code...