package service;

import dao.IVoucherByPriceDAOImpl;
import dao.IVoucherByProductDAOImpl;
import dao.IVoucherByPriceDAO;
import dao.IVoucherByProductDAO;
import model.VoucherByPrice;
import model.VoucherByProduct;
import service.IVoucherByPriceService;

import java.util.List;

public class VoucherByPriceServiceImpl implements IVoucherByPriceService {
    IVoucherByPriceDAO voucherDao = new IVoucherByPriceDAOImpl();
    @Override
    public void insert(VoucherByPrice voucher) {
        voucherDao.insert(voucher);
    }

    @Override
    public void update(VoucherByPrice voucher) {
        voucherDao.update(voucher);
    }

    @Override
    public void delete(VoucherByPrice voucher) {
        voucherDao.delete(voucher);
    }

    @Override
    public List<VoucherByPrice> findAll() {
        return voucherDao.findAll();
    }

    @Override
    public VoucherByPrice findById(int Id) {
        return voucherDao.findById(Id);
    }

    @Override
    public List<VoucherByPrice> findAll(int pageNo, int pageSize) {
        return voucherDao.findAll(pageNo,pageSize);
    }
}
