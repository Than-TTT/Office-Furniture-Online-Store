package cnpm.ergo.controller.Admin.Marketing.Campain;

import cnpm.ergo.service.implement.CampaignImageServiceImpl;
import cnpm.ergo.service.implement.MarketingCampaignServiceImpl;
import cnpm.ergo.service.interfaces.ICampaignImageService;
import cnpm.ergo.service.interfaces.IMarketingCampaignService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/campaign/delete")
public class DeleteController extends HttpServlet {
    private IMarketingCampaignService campaignService = new MarketingCampaignServiceImpl();
    private ICampaignImageService campaignImageService = new CampaignImageServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                
                // 1. Xóa ảnh ở bảng con trước để không bị lỗi ràng buộc khóa ngoại (Foreign Key)
                campaignImageService.deleteByCampaignId(id);
                
                // 2. Xóa campaign ở bảng cha bằng ID
                campaignService.deleteCampaign(id);
            }
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/marketing?error=delete_failed");
        }
    }
}