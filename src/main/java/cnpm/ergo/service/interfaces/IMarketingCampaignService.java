package cnpm.ergo.service.interfaces;

import java.util.List;

import cnpm.ergo.entity.MarketingCampaign;

public interface IMarketingCampaignService {
	void addCampaign(MarketingCampaign campaignEntity);
	void updateCampaign(MarketingCampaign campaignEntity);
	void deleteCampaign(Long id);
//	void deleteCampaign(Long Id);
	List<MarketingCampaign> findAllMarketingCampaign();
	MarketingCampaign findByID(Long Id);
	MarketingCampaign getLatestCampaign();
}
