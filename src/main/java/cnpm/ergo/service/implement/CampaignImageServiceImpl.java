package cnpm.ergo.service.implement;

import java.util.List;

import cnpm.ergo.DAO.implement.CampaignImageDaoImpl;
import cnpm.ergo.DAO.interfaces.ICampaignImageDao;
import cnpm.ergo.entity.CampaignImage;
import cnpm.ergo.service.interfaces.ICampaignImageService;

public class CampaignImageServiceImpl implements ICampaignImageService {
	
	private ICampaignImageDao campaignImageDao = new CampaignImageDaoImpl(); 
	@Override
	public void addImage(CampaignImage campaignImage) {
		campaignImageDao.addImage(campaignImage);
	}

	@Override
	public List<CampaignImage> findImagesByCampaignId(Long campaignId) {
		return campaignImageDao.findImagesByCampaignId(campaignId);
	}

	@Override
	public void deleteByCampaignId(Long campaignId) {
		campaignImageDao.deleteByCampaignId(campaignId);
	}

	@Override
	public CampaignImage finByPath(String path)
	{
		return campaignImageDao.finByPath(path);
	}

	@Override
	public void update(CampaignImage image) {
		campaignImageDao.update(image);
	}
}
