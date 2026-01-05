package cnpm.ergo.service.interfaces;

import java.util.List;

import cnpm.ergo.entity.CampaignImage;

public interface ICampaignImageService {
	void addImage(CampaignImage campaignImage);
    List<CampaignImage> findImagesByCampaignId(Long campaignId);
    void deleteByCampaignId(Long campaignId);
    CampaignImage finByPath(String path);
    void update (CampaignImage image);
}
