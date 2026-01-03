package service;

import dao.IVoucherByProductDAOImpl;
import dao.IVoucherByProductDAO;
import model.Voucher;
import model.VoucherByPrice;
import model.VoucherByProduct;
import service.IVoucherByProductService;

import java.util.List;

public class VoucherByProductServiceImpl implements IVoucherByProductService {
    IVoucherByProductDAO voucherDao = new IVoucherByProductDAOImpl();
    @Override
    public void insert(VoucherByProduct voucher) {
        voucherDao.insert(voucher);
    }

    @Override
    public void update(VoucherByProduct voucher) {
        voucherDao.update(voucher);
    }

    @Override
    public void delete(VoucherByProduct voucher) {
        voucherDao.update(voucher);
    }

    @Override
    public List<VoucherByProduct> findAll() {
        return voucherDao.findAll();
    }

    @Override
    public VoucherByProduct findById(int Id) {
        return voucherDao.findById(Id);
    }

    @Override
    public List<VoucherByProduct> findAll(int pageNo, int pageSize) {
        return voucherDao.findAll(pageNo,pageSize);
    }
}
