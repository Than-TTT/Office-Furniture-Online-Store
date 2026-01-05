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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            // Lấy thông tin chung từ form

            String content = request.getParameter("content");
            System.out.println("test content" + content);
            MarketingCampaign campaign = new MarketingCampaign();


            String voucherIdParam = request.getParameter("voucherId");
            System.out.println("lay duoc ID tu form:" + voucherIdParam);
            if (voucherIdParam != null && !voucherIdParam.isEmpty()) {
                int voucherId = Integer.parseInt(voucherIdParam); // Chuyển đổi sang kiểu số nếu cần
                System.out.println("chuyen doi ID:" + voucherId);

                Voucher voucher;
                if(voucherByPriceService.findById(voucherId) == null)
                {
                    if(voucherByProductService.findById(voucherId) == null)
                    {
                        return;
                    }
//                    voucher = new VoucherByProduct();
                    voucher = voucherByProductService.findById(voucherId);
                    System.out.println("loai voucher la product");

                }
                else {
//                    voucher = new VoucherByPrice();
                    voucher = voucherByPriceService.findById(voucherId);
                    System.out.println("loai voucher la price");
                }

                //nếu voucher được chọn đã thuộc campaign nào đó rồi, thì gỡ cái voucher đó ra khỏi cái cũ
                if (voucher.getMarketingCampaign() != null) {
                    System.out.println("go voucher cu: "+ voucher.getMarketingCampaign().getContent());
                    voucher.getMarketingCampaign().setVoucher(null);
                    marketingCampaignService.updateCampaign(voucher.getMarketingCampaign());
                    System.out.println("do go voucher cu: "+ voucher.getMarketingCampaign().getVoucher());
                }
                else {
                    System.out.println("voucher chua gang voi campaign nao" + voucher.getMarketingCampaign());
                }


                // Liên kết voucher với campaign mới
                System.out.println("lien ket voucher da chon voi campaign moi");
//                voucher.setMarketingCampaign(campaign);
                campaign.setVoucher(voucher);
                System.out.println("Lien ket thanh cong voucher:" + voucher.getCode());
                System.out.println("Lien ket thanh cong " + voucher.getMarketingCampaign());
                System.out.println("Lien ket thanh cong " + campaign.getVoucher().getCode());


            } else {
                System.out.println("No voucher selected.");
            }
            campaign.setContent(content);
            System.out.println("set conten moi");




            // Gọi service để lưu campaigns
            marketingCampaignService.addCampaign(campaign);

            String image = request.getParameter("image");
            System.out.println("test add image" + image);
            if(image != null )
            {
                ICampaignImageService campaignImageService = new CampaignImageServiceImpl();
                CampaignImage testDaTontai = campaignImageService.finByPath(image);
                if(testDaTontai != null)
                {
                    testDaTontai.setMarketingCampaign(null);
                    testDaTontai.setMarketingCampaign(campaign);
                    campaignImageService.update(testDaTontai);
                }
                else {

                    CampaignImage image1 = new CampaignImage();
                    image1.setImagePath(image);

                    List<CampaignImage> list = new ArrayList<>();
                    list.add(image1);

                    image1.setMarketingCampaign(marketingCampaignService.getLatestCampaign());

                    campaignImageService.addImage(image1);
                }
            }


            // Redirect hoặc thông báo thành công
            response.sendRedirect(request.getContextPath() + "/admin/marketing");




        }catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to add the campaign. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }


    }
}
