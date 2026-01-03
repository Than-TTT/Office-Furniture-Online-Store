package controller.marketing.Campain;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(urlPatterns = "/admin/campaign/editCampaign")
public class UpdateController extends HttpServlet {
    private IMarketingCampaignService marketingCampaignService = new MarketingCampaignServiceImpl();
    private IVoucherByPriceService voucherByPriceService = new IVoucherByPriceServiceImpl();
    private IVoucherByProductService voucherByProductService = new IVoucherByProductServiceImpl();



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy giá trị từ tham số của form
        Long campaingID = null;
        try {
            campaingID = Long.parseLong(request.getParameter("campaignId"));
        } catch (Exception ignored) {}
        String content = request.getParameter("content");
        String image = request.getParameter("image");

        List<Voucher> vouchers = new ArrayList<>();
        vouchers = createVouchers(vouchers);
        request.setAttribute("vouchers",vouchers);
        // Gửi giá trị vào trang editVoucherPrice.jsp
        request.setAttribute("content", content);
        request.setAttribute("campaignId", campaingID);
        if(image != null && !Objects.equals(image, "Rong"))
            request.setAttribute("image",image);

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
            // Lấy thông tin
            Long campaingID = Long.parseLong(request.getParameter("campaignId"));
            String content = request.getParameter("content");
            String image = request.getParameter("image");

            // Tạo đối tượng Campaign
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
                    } else {
                        // voucher not found -> ignore
                    }
                }

                if (voucher != null) {
                    // nếu voucher được chọn đã thuộc campaign nào đó rồi, thì gỡ cái voucher đó ra khỏi cái cũ
                    MarketingCampaign oldCampaign = voucher.getMarketingCampaign();
                    if (oldCampaign != null) {
                        oldCampaign.setVoucher(null);
                        marketingCampaignService.updateCampaign(oldCampaign);
                    }

                    // Liên kết voucher với campaign mới
                    campaign.setVoucher(voucher);
                }
            }

            if(request.getParameter("image") != null ) {
                ICampaignImageService campaignImageService = new CampaignImageServiceImpl();
                CampaignImage testDaTontai = campaignImageService.finByPath(image);

                if (campaign.getCampaignImages() != null && campaign.getCampaignImages().size() > 0) {
                    // remove existing links
                    for(CampaignImage campaignImage : new ArrayList<>(campaign.getCampaignImages()))
                    {
                        campaignImage.setMarketingCampaign(null);
                        campaignImageService.update(campaignImage);
                    }
                    campaign.getCampaignImages().clear();

                    if(testDaTontai != null)
                    {
                        testDaTontai.setMarketingCampaign(campaign);
                        campaignImageService.update(testDaTontai);
                    } else {
                        CampaignImage imagetaomoi = new CampaignImage();
                        imagetaomoi.setImagePath(image);
                        imagetaomoi.setMarketingCampaign(campaign);
                        campaignImageService.addImage(imagetaomoi);
                    }
                }
                else {
                    if(testDaTontai != null)
                    {
                        testDaTontai.setMarketingCampaign(campaign);
                        campaignImageService.update(testDaTontai);
                    }
                    else {
                        CampaignImage image1 = new CampaignImage();
                        image1.setImagePath(image);
                        image1.setMarketingCampaign(campaign);
                        campaignImageService.addImage(image1);
                    }
                }
            }

            marketingCampaignService.updateCampaign(campaign);
            // Redirect hoặc thông báo thành công
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
