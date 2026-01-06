package cnpm.ergo.controller.Admin.Marketing.Campain;

import cnpm.ergo.DAO.implement.BlogDaoImpl;
import cnpm.ergo.entity.*;
import cnpm.ergo.service.implement.CampaignImageServiceImpl;
import cnpm.ergo.service.implement.IVoucherByPriceServiceImpl;
import cnpm.ergo.service.implement.IVoucherByProductServiceImpl;
import cnpm.ergo.service.implement.MarketingCampaignServiceImpl;
import cnpm.ergo.service.interfaces.ICampaignImageService;
import cnpm.ergo.service.interfaces.IMarketingCampaignService;
import cnpm.ergo.service.interfaces.IVoucherByPriceService;
import cnpm.ergo.service.interfaces.IVoucherByProductService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.io.File;

@WebServlet(urlPatterns = "/admin/campaign/update")
@MultipartConfig
public class UpdateController extends HttpServlet {
    private IMarketingCampaignService campaignService = new MarketingCampaignServiceImpl();
    private ICampaignImageService campaignImageService = new CampaignImageServiceImpl();
    private IVoucherByPriceService priceService = new IVoucherByPriceServiceImpl();
    private IVoucherByProductService productService = new IVoucherByProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            Long id = Long.parseLong(request.getParameter("campaignId"));
            // Sử dụng "editContent" và "editVoucherId" để khớp với Modal JSP đã sửa ở bước trước
            String content = request.getParameter("editContent");
            String vIdStr = request.getParameter("editVoucherId");

            MarketingCampaign campaign = campaignService.findByID(id);
            if (campaign != null) {
                campaign.setContent(content);

                // --- CẬP NHẬT VOUCHER ĐÍNH KÈM ---
                if (vIdStr != null && !vIdStr.isEmpty()) {
                    int vId = Integer.parseInt(vIdStr);
                    Voucher voucher = priceService.findById(vId);
                    if (voucher == null) {
                        voucher = productService.findById(vId);
                    }
                    campaign.setVoucher(voucher);
                } else {
                    campaign.setVoucher(null); // Gỡ voucher nếu chọn "Không đính kèm"
                }

                campaignService.updateCampaign(campaign);

                // --- CẬP NHẬT ẢNH (Dùng "editImageFile") ---
                Part filePart = request.getPart("editImageFile");
                if (filePart != null && filePart.getSize() > 0) {
                    campaignImageService.deleteByCampaignId(id);
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String uploadPath = getServletContext().getRealPath("/") + "marketing-images";
                    filePart.write(uploadPath + File.separator + fileName);
                    
                    CampaignImage img = new CampaignImage();
                    img.setImagePath(fileName);
                    img.setMarketingCampaign(campaign);
                    campaignImageService.addImage(img);
                }
            }
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
        } catch (Exception e) { 
            e.printStackTrace(); 
            response.sendRedirect(request.getContextPath() + "/admin/marketing?err=update"); 
        }
    }
}