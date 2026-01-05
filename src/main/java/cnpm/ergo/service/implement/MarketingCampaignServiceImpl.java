package cnpm.ergo.service.implement;

import java.util.List;

import cnpm.ergo.DAO.implement.MarketingCampaignDaoImpl;
import cnpm.ergo.DAO.interfaces.IMarketingCampaignDao;
import cnpm.ergo.entity.MarketingCampaign;
import cnpm.ergo.service.interfaces.IMarketingCampaignService;

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
    public void deleteCampaign(Long id) {
        campaignDao.delete(id); 
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
