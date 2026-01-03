// ...existing code...
package controller.marketing.Campain;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CampaignImage;
import model.MarketingCampaign;
import model.Voucher;
import service.*;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/campaign/addCampaign")
public class AddController extends HttpServlet {
    private IMarketingCampaignService marketingCampaignService = new MarketingCampaignServiceImpl();
    private IVoucherByPriceService voucherByPriceService = new IVoucherByPriceServiceImpl();
    private IVoucherByProductService voucherByProductService = new IVoucherByProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/login");
//            return;
//        }
        try {
            String content = request.getParameter("content");
            MarketingCampaign campaign = new MarketingCampaign();

            String voucherIdParam = request.getParameter("voucherId");
            if (voucherIdParam != null && !voucherIdParam.isEmpty()) {
                int voucherId = Integer.parseInt(voucherIdParam);
                Voucher voucher;
                if (voucherByPriceService.findById(voucherId) == null) {
                    if (voucherByProductService.findById(voucherId) == null) {
                        voucher = null;
                    } else {
                        voucher = voucherByProductService.findById(voucherId);
                    }
                } else {
                    voucher = voucherByPriceService.findById(voucherId);
                }

                if (voucher != null) {
                    // if voucher already belongs to a campaign, detach it first
                    if (voucher.getMarketingCampaign() != null) {
                        voucher.getMarketingCampaign().setVoucher(null);
                        marketingCampaignService.updateCampaign(voucher.getMarketingCampaign());
                    }
                    // link voucher to new campaign
                    campaign.setVoucher(voucher);
                }
            }

            campaign.setContent(content);
            // persist campaign
            marketingCampaignService.addCampaign(campaign);

            // Re-fetch the persisted campaign (with images loaded) so further updates use a managed entity
            MarketingCampaign persisted = marketingCampaignService.getLatestCampaign();
            if (persisted == null) {
                // fallback to find by id if needed (if your DAO sets campaignId on the passed campaign, you can use findByID)
                persisted = campaign;
            }

            String image = request.getParameter("image");
            if (image != null && !image.isEmpty()) {
                ICampaignImageService campaignImageService = new CampaignImageServiceImpl();
                CampaignImage existing = campaignImageService.finByPath(image);
                if (existing != null) {
                    existing.setMarketingCampaign(persisted);
                    campaignImageService.update(existing);
                } else {
                    CampaignImage image1 = new CampaignImage();
                    image1.setImagePath(image);
                    image1.setMarketingCampaign(persisted);
                    campaignImageService.addImage(image1);
                }
            }

            response.sendRedirect(request.getContextPath() + "/admin/marketing");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to add the campaign. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}
// ...existing code...
