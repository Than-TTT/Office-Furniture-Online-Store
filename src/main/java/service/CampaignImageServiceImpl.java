package service;

import java.util.List;

import dao.CampaignImageDaoImpl;
import dao.ICampaignImageDao;
import model.CampaignImage;
import service.ICampaignImageService;

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
