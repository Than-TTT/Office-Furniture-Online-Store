package service;

import java.util.List;

import model.CampaignImage;

public interface ICampaignImageService {
	void addImage(CampaignImage campaignImage);
    List<CampaignImage> findImagesByCampaignId(Long campaignId);
    void deleteByCampaignId(Long campaignId);
    CampaignImage finByPath(String path);
    void update (CampaignImage image);
}
