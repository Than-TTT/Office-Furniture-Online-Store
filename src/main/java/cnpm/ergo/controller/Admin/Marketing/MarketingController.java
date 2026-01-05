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

        List<VoucherByPrice> voucherByPriceList = voucherByPriceService.findAll();
        List<VoucherByProduct> voucherByProductList = voucherByProductService.findAll();
        List<Voucher> vouchers = new ArrayList<>();
        vouchers = createVouchers(vouchers);

        List<MarketingCampaign> Campaigns = marketingCampaignService.findAllMarketingCampaign();

        IProductTypeService productTypeService = new ProductTypeServiceImpl();
        List<ProductType> productTypes = productTypeService.getAllProductTypes();

        IMarketingCampaignService marketingCampaignService = new MarketingCampaignServiceImpl();
        List<MarketingCampaign> marketingCampaignList = marketingCampaignService.findAllMarketingCampaign();


//        long totalvoucher = vouchers.stream().count();
//        int totalPages = (int) Math.ceil((double) totalvoucher / pageSize);

        request.setAttribute("vouchers",vouchers);
        request.setAttribute("campaigns",Campaigns);
        request.setAttribute("productTypes", productTypes);
        request.setAttribute("campaigns", marketingCampaignList);

//        request.setAttribute("currentPage", pageNo);
//        request.setAttribute("totalPages", totalPages);

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
