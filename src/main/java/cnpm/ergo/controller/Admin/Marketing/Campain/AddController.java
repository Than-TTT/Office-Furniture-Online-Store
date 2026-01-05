package cnpm.ergo.controller.Admin.Marketing.Campain;

import cnpm.ergo.entity.*;
import cnpm.ergo.service.implement.CampaignImageServiceImpl;
import cnpm.ergo.service.implement.IVoucherByPriceServiceImpl;
import cnpm.ergo.service.implement.IVoucherByProductServiceImpl;
import cnpm.ergo.service.implement.MarketingCampaignServiceImpl;
import cnpm.ergo.service.interfaces.ICampaignImageService;
import cnpm.ergo.service.interfaces.IMarketingCampaignService;
import cnpm.ergo.service.interfaces.IVoucherByPriceService;
import cnpm.ergo.service.interfaces.IVoucherByProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@WebServlet(urlPatterns = "/admin/campaign/addCampaign")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AddController extends HttpServlet {
    // Sử dụng Service của bạn
    private IMarketingCampaignService campaignService = new MarketingCampaignServiceImpl();
    private ICampaignImageService campaignImageService = new CampaignImageServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            // 1. Lấy nội dung text
            String content = request.getParameter("content");
            MarketingCampaign campaign = new MarketingCampaign();
            campaign.setContent(content);
            campaign.setIsDelete(false);

            // Lưu Campaign vào DB trước
            campaignService.addCampaign(campaign);

            // 2. Xử lý file ảnh được chọn từ máy tính
            Part filePart = request.getPart("imageFile"); 
            if (filePart != null && filePart.getSize() > 0) {
                // Lấy tên file gốc
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                
                // Đường dẫn đến thư mục marketing-images trong project
                String uploadPath = getServletContext().getRealPath("/") + "marketing-images";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                // Lưu file vật lý vào server
                filePart.write(uploadPath + File.separator + fileName);

                // 3. Tạo Entity CampaignImage và gọi Service của bạn
                CampaignImage newImg = new CampaignImage();
                newImg.setImagePath(fileName);
                newImg.setMarketingCampaign(campaign);
                
                // GỌI HÀM TỪ SERVICE CỦA BẠN
                campaignImageService.addImage(newImg);
            }

            response.sendRedirect(request.getContextPath() + "/admin/marketing");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
        }
    }
}