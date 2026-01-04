package controller.marketing.Campain;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.IMarketingCampaignService;
import service.MarketingCampaignServiceImpl;

import java.io.IOException;

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
            Long campaingID = Long.parseLong(request.getParameter("campaignId"));
            marketingCampaignService.deleteCampaign(marketingCampaignService.findByID(campaingID));
            response.sendRedirect(request.getContextPath() + "/admin/marketing");

        }catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to delete the campaign. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }


    }
}
