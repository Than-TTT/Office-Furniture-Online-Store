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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        Long campaingID = Long.parseLong(request.getParameter("campaignId"));
        System.out.println("testIDshow" + campaingID);
        String content = request.getParameter("content");
        System.out.println("testContentshow" + content);
        String image = request.getParameter("image");
        System.out.println("testimageshow" + image);

        List<Voucher> vouchers = new ArrayList<>();
        vouchers = createVouchers(vouchers);
        request.setAttribute("vouchers",vouchers);
        // Gửi giá trị vào trang editVoucherPrice.jsp
        request.setAttribute("content", content);
        request.setAttribute("campaignId", campaingID);
        if(!Objects.equals(image, "Rong"))
            request.setAttribute("image",image);

        // Chuyển hướng đến trang editVoucherPrice
//        RequestDispatcher dispatcher = request.getRequestDispatcher("views/editVoucherPrice.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/views/editCampaign.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getSession().getAttribute("admin") == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/login");
//            return;
//        }
        System.out.println("vo duoc controller");
        try {
            // Lấy thông tin
            Long campaingID = Long.parseLong(request.getParameter("campaignId"));
            System.out.println("testID" + campaingID);
            String content = request.getParameter("content");
            System.out.println("testContent" + content);
            String image = request.getParameter("image");
            System.out.println(image);
            // Tạo đối tượng Campaign
            MarketingCampaign campaign = marketingCampaignService.findByID(campaingID);
            campaign.setContent(content);

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
                campaign.setVoucher(voucher);
                System.out.println("Lien ket thanh cong voucher:" + voucher.getCode());
                System.out.println("Lien ket thanh cong " + campaign.getVoucher().getCode());

//                System.out.println("Lien ket thanh cong " + voucher.getMarketingCampaign());


            } else {
                System.out.println("No voucher selected.");
            }

            //xử lý image này đã có chưa, nếu có thì gỡ ra khỏi cái cũ thêm cái mới
            // có 4 trường hợp
//            đã có đã tạo
//            đã có chưa tạo
//            chưa có đã tạo
//            chưa có chưa tạo
            if(request.getParameter("image") != null ) {
                ICampaignImageService campaignImageService = new CampaignImageServiceImpl();
                CampaignImage testDaTontai = campaignImageService.finByPath(image);

                if (campaign.getCampaignImages() != null && campaign.getCampaignImages().size() > 0) {
                    CampaignImage ImageDaTao = campaign.getCampaignImages().get(0);
                    //đã có đã tạo
                    if(testDaTontai != null)
                    {
//                        ImageDaTao.setMarketingCampaign(null);
                        ImageDaTao.setMarketingCampaign(null);
                        campaignImageService.update(ImageDaTao);

                        for(CampaignImage campaignImage : campaign.getCampaignImages())
                        {
                            campaignImage.setMarketingCampaign(null);
                            campaignImageService.update(campaignImage);
                        }
                        campaign.getCampaignImages().clear();

                        testDaTontai.setMarketingCampaign(null);
                        testDaTontai.setMarketingCampaign(campaign);
                        campaignImageService.update(testDaTontai);
//                        marketingCampaignService.updateCampaign(campaign);
                        System.out.println("da co: "+ ImageDaTao.getImagePath());
                        System.out.println("da tao: "+ testDaTontai.getImagePath());

                    }
                    //đã có chưa tạo
                    else {
//                        ImageDaTao.setMarketingCampaign(null);
                        ImageDaTao.setMarketingCampaign(null);
                        campaignImageService.update(ImageDaTao);

                        for(CampaignImage campaignImage : campaign.getCampaignImages())
                        {
                            campaignImage.setMarketingCampaign(null);
                            campaignImageService.update(campaignImage);
                        }
                        campaign.getCampaignImages().clear();

                        CampaignImage imagetaomoi = new CampaignImage();
                        imagetaomoi.setImagePath(image);
                        imagetaomoi.setMarketingCampaign(campaign);

//                        marketingCampaignService.updateCampaign(campaign);
                        campaignImageService.update(ImageDaTao);
                        campaignImageService.addImage(imagetaomoi);
                        System.out.println("da co: "+ ImageDaTao.getImagePath());
                        System.out.println("chua tao: "+ imagetaomoi.getImagePath());
                    }
                }
                else {
                    //chưa có đã tạo
                    if(testDaTontai != null)
                    {
                        //gỡ cái cũ ra, gắn cái mới dzo
                        testDaTontai.setMarketingCampaign(null);
                        testDaTontai.setMarketingCampaign(campaign);
                        campaignImageService.update(testDaTontai);
                        System.out.println("chua co");
                        System.out.println("da tao: "+ testDaTontai.getImagePath());

                    }
                    //chưa có chưa tạo
                    else {
                        CampaignImage image1 = new CampaignImage();
                        image1.setImagePath(image);
                        image1.setMarketingCampaign(campaign);

                        campaignImageService.addImage(image1);
                        System.out.println("chua co");
                        System.out.println("chua tao: "+ image1.getImagePath());
                    }
                }
            }
//
            System.out.println("CampaignId" + campaingID);

            marketingCampaignService.updateCampaign(campaign);
            // Redirect hoặc thông báo thành công
            response.sendRedirect(request.getContextPath() + "/admin/marketing");

        }catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to delete the campaign. Please try again.");
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
