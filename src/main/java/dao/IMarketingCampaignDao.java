package dao;

import java.util.List;

import model.MarketingCampaign;

public interface IMarketingCampaignDao {
	void insert(MarketingCampaign campaignEntity);
	void update(MarketingCampaign campaignEntity);
	void delete(MarketingCampaign campaignEntity);
//	void delete(Long campaignId);
	List<MarketingCampaign> findAll();
	MarketingCampaign findById(Long id);
	public MarketingCampaign getLatestCampaign();
}
