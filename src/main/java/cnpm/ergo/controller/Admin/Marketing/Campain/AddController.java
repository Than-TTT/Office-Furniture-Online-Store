package cnpm.ergo.controller.Admin.Marketing.Campain;

import cnpm.ergo.entity.*;
import cnpm.ergo.service.implement.*;
import cnpm.ergo.service.interfaces.*;
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

@WebServlet(urlPatterns = "/admin/campaign/addCampaign")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AddController extends HttpServlet {
    // 1. Khai báo đầy đủ các Service
    private IMarketingCampaignService campaignService = new MarketingCampaignServiceImpl();
    private ICampaignImageService campaignImageService = new CampaignImageServiceImpl();
    private IVoucherByPriceService priceService = new IVoucherByPriceServiceImpl();
    private IVoucherByProductService productService = new IVoucherByProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String content = request.getParameter("content");
            String vIdStr = request.getParameter("voucherId");

            MarketingCampaign campaign = new MarketingCampaign();
            campaign.setContent(content);
            campaign.setIsDelete(false);

            // 2. Logic tìm Voucher
            if (vIdStr != null && !vIdStr.isEmpty()) {
                int vId = Integer.parseInt(vIdStr);
                Voucher voucher = priceService.findById(vId);
                if (voucher == null) {
                    voucher = productService.findById(vId);
                }
                campaign.setVoucher(voucher);
            }

            // 3. Lưu Campaign (Sử dụng hàm void của bạn)
            campaignService.addCampaign(campaign);

            // 4. Lưu Ảnh
            Part filePart = request.getPart("imageFile"); 
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("/") + "marketing-images";
                
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                filePart.write(uploadPath + File.separator + fileName);

                CampaignImage newImg = new CampaignImage();
                newImg.setImagePath(fileName);
                newImg.setMarketingCampaign(campaign); 
                
                campaignImageService.addImage(newImg);
            }

            response.sendRedirect(request.getContextPath() + "/admin/marketing?success=add");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/marketing?err=add");
        }
    }
}