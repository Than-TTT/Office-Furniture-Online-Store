package controller.Marketing.Campain;

import model.MarketingCampaign;
import model.Voucher;
import model.VoucherByPrice;
import model.VoucherByProduct;
import service.VoucherByPriceServiceImpl;
import service.VoucherByProductServiceImpl;
import service.MarketingCampaignServiceImpl;
import service.IMarketingCampaignService;
import service.IVoucherByPriceService;
import service.IVoucherByProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "/admin/campaign/deleteCampaign")
public class DeleteController extends HttpServlet {
    private IMarketingCampaignService marketingCampaignService = new MarketingCampaignServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/login");
//            return;
//        }
        try {
            // Lấy thông tin
            Long campaingID = Long.parseLong(request.getParameter("campaignId"));
            // Tạo đối tượng Campaign
            marketingCampaignService.deleteCampaign(marketingCampaignService.findByID(campaingID));
            // Redirect hoặc thông báo thành công
            response.sendRedirect(request.getContextPath() + "/admin/marketing");

        }catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to delete the campaign. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }


    }
}
