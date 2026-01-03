package service;

import java.util.List;

import model.MarketingCampaign;

public interface IMarketingCampaignService {
	void addCampaign(MarketingCampaign campaignEntity);
	void updateCampaign(MarketingCampaign campaignEntity);
	void deleteCampaign(MarketingCampaign campaignEntity);
//	void deleteCampaign(Long Id);
	List<MarketingCampaign> findAllMarketingCampaign();
	MarketingCampaign findByID(Long Id);
	MarketingCampaign getLatestCampaign();
}
