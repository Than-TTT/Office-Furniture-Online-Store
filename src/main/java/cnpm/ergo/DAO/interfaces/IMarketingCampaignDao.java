package cnpm.ergo.DAO.interfaces;

import java.util.List;

import cnpm.ergo.entity.MarketingCampaign;

public interface IMarketingCampaignDao {
	void insert(MarketingCampaign campaignEntity);
	void update(MarketingCampaign campaignEntity);
	void delete(MarketingCampaign campaignEntity);
//	void delete(Long campaignId);
	List<MarketingCampaign> findAll();
	MarketingCampaign findById(Long id);
	public MarketingCampaign getLatestCampaign();
}