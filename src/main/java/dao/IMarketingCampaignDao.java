package dao;

import java.util.List;

public interface IMarketingCampaignDao {
	void insert(model.MarketingCampaign campaignEntity);
	void update(model.MarketingCampaign campaignEntity);
	void delete(model.MarketingCampaign campaignEntity);
//	void delete(Long campaignId);
	List<model.MarketingCampaign> findAll();
	model.MarketingCampaign findById(Long id);
	public model.MarketingCampaign getLatestCampaign();
}
