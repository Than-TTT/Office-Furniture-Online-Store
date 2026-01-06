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
import cnpm.ergo.util.FileUploadUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 5 * 1024 * 1024
)
@WebServlet(urlPatterns = "/admin/campaign/editCampaign")
public class UpdateController extends HttpServlet {
    private IMarketingCampaignService marketingCampaignService = new MarketingCampaignServiceImpl();
    private IVoucherByPriceService voucherByPriceService = new IVoucherByPriceServiceImpl();
    private IVoucherByProductService voucherByProductService = new IVoucherByProductServiceImpl();



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long campaignId = null;
        try {
            campaignId = Long.parseLong(request.getParameter("campaignId"));
        } catch (Exception ignored) {}
        
        if (campaignId == null) {
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
            return;
        }
        
        // Fetch campaign from database
        MarketingCampaign campaign = marketingCampaignService.findByID(campaignId);
        if (campaign == null) {
            response.sendRedirect(request.getContextPath() + "/admin/marketing");
            return;
        }
        
        // Get all vouchers for dropdown
        List<Voucher> vouchers = new ArrayList<>();
        vouchers = createVouchers(vouchers);
        request.setAttribute("vouchers", vouchers);
        
        // Set campaign data for the form
        request.setAttribute("campaignId", campaign.getCampaignId());
        request.setAttribute("content", campaign.getContent());
        request.setAttribute("currentVoucherId", campaign.getVoucher() != null ? campaign.getVoucher().getVoucherId() : null);
        
        if (campaign.getCampaignImages() != null && !campaign.getCampaignImages().isEmpty()) {
            request.setAttribute("image", campaign.getCampaignImages().get(0).getImagePath());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/views/editCampaign.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/login");
//            return;
//        }
        try {
            // Get campaign info
            Long campaingID = Long.parseLong(request.getParameter("campaignId"));
            String content = request.getParameter("content");

            // Find the campaign
            MarketingCampaign campaign = marketingCampaignService.findByID(campaingID);
            if (campaign == null) {
                response.sendRedirect(request.getContextPath() + "/admin/marketing");
                return;
            }
            campaign.setContent(content);

            String voucherIdParam = request.getParameter("voucherId");
            if (voucherIdParam != null && !voucherIdParam.isEmpty()) {
                int voucherId = Integer.parseInt(voucherIdParam);

                Voucher voucher = null;
                VoucherByPrice vbp = voucherByPriceService.findById(voucherId);
                if (vbp != null) {
                    voucher = vbp;
                } else {
                    VoucherByProduct vprod = voucherByProductService.findById(voucherId);
                    if (vprod != null) {
                        voucher = vprod;
                    }
                }

                if (voucher != null) {
                    // If selected voucher already belongs to another campaign, detach it
                    MarketingCampaign oldCampaign = voucher.getMarketingCampaign();
                    if (oldCampaign != null && oldCampaign.getCampaignId() != campaign.getCampaignId()) {
                        oldCampaign.setVoucher(null);
                        marketingCampaignService.updateCampaign(oldCampaign);
                        // Re-fetch the campaign to avoid stale reference issues
                        campaign = marketingCampaignService.findByID(campaingID);
                        campaign.setContent(content);
                    }

                    // Link voucher to new campaign
                    campaign.setVoucher(voucher);
                }
            } else {
                // Clear voucher if none selected
                campaign.setVoucher(null);
            }

            // Handle file upload
            Part filePart = request.getPart("campaignImage");
            if (filePart != null && filePart.getSize() > 0) {
                String uploadBasePath = getServletContext().getRealPath("");
                String imagePath = FileUploadUtil.uploadFile(filePart, uploadBasePath);
                
                if (imagePath != null && !imagePath.isEmpty()) {
                    ICampaignImageService campaignImageService = new CampaignImageServiceImpl();
                    
                    // Remove existing images
                    if (campaign.getCampaignImages() != null && campaign.getCampaignImages().size() > 0) {
                        for (CampaignImage campaignImage : new ArrayList<>(campaign.getCampaignImages())) {
                            campaignImage.setMarketingCampaign(null);
                            campaignImageService.update(campaignImage);
                        }
                        campaign.getCampaignImages().clear();
                    }
                    
                    // Add new image
                    CampaignImage newCampaignImage = new CampaignImage();
                    newCampaignImage.setImagePath(imagePath);
                    newCampaignImage.setMarketingCampaign(campaign);
                    campaignImageService.addImage(newCampaignImage);
                }
            }

            marketingCampaignService.updateCampaign(campaign);
            response.sendRedirect(request.getContextPath() + "/admin/marketing");

        }catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to update the campaign. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }


    }

    private List<Voucher> createVouchers(List<Voucher> vouchers)
    {
        IVoucherByProductService voucherByProductService = new IVoucherByProductServiceImpl();
        IVoucherByPriceService voucherByPriceService = new IVoucherByPriceServiceImpl();
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
