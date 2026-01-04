package service;

import java.util.List;

public interface IMarketingCampaignService {
	void addCampaign(model.MarketingCampaign campaignEntity);
	void updateCampaign(model.MarketingCampaign campaignEntity);
	void deleteCampaign(model.MarketingCampaign campaignEntity);
//	void deleteCampaign(Long Id);
	List<model.MarketingCampaign> findAllMarketingCampaign();
	model.MarketingCampaign findByID(Long Id);
	model.MarketingCampaign getLatestCampaign();
}
