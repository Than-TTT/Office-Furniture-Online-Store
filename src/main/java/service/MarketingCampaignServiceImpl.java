package service;

import java.util.List;

import dao.MarketingCampaignDaoImpl;
import dao.IMarketingCampaignDao;
import model.MarketingCampaign;
import service.IMarketingCampaignService;

public class MarketingCampaignServiceImpl implements IMarketingCampaignService{

	public IMarketingCampaignDao campaignDao = new MarketingCampaignDaoImpl();
	@Override
	public void addCampaign(MarketingCampaign campaignEntity) {
		campaignDao.insert(campaignEntity);
	}

	@Override
	public void updateCampaign(MarketingCampaign campaignEntity) {
		campaignDao.update(campaignEntity);
	}

	@Override
	public void deleteCampaign(MarketingCampaign campaignEntity) {
		campaignDao.delete(campaignEntity);
	}

	@Override
	public List<MarketingCampaign> findAllMarketingCampaign() {
		List<MarketingCampaign> campaignEntities = campaignDao.findAll();
		return campaignEntities;
	}

	@Override
	public MarketingCampaign findByID(Long Id) {
		return campaignDao.findById(Id);
	}

	@Override
	public MarketingCampaign getLatestCampaign() {
		return campaignDao.getLatestCampaign();
	}
}
