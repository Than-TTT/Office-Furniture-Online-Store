package service;


import model.CampaignImage;

import java.util.List;

public interface ICampaignImageService {
	void addImage(CampaignImage campaignImage);
    List<CampaignImage> findImagesByCampaignId(Long campaignId);
    void deleteByCampaignId(Long campaignId);
    CampaignImage finByPath(String path);
    void update (CampaignImage image);
}
