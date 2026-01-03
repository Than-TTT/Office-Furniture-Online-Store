// ...existing code...
package controller.marketing;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/admin/marketing")
public class MarketingController extends HttpServlet {
    service.IMarketingCampaignService marketingCampaignService = new service.MarketingCampaignServiceImpl();
    service.IVoucherByPriceService voucherByPriceService = new service.IVoucherByPriceServiceImpl();
    service.IVoucherByProductService voucherByProductService = new service.IVoucherByProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get vouchers
        List<VoucherByPrice> voucherByPriceList = voucherByPriceService.findAll();
        List<VoucherByProduct> voucherByProductList = voucherByProductService.findAll();
        List<Voucher> vouchers = new ArrayList<>();
        vouchers = createVouchers(vouchers);

        // Get marketing campaigns (now returned with images loaded by DAO)
        List<MarketingCampaign> marketingCampaignList = marketingCampaignService.findAllMarketingCampaign();

        service.IProductTypeService productTypeService = new service.ProductTypeServiceImpl();
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
