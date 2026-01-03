package service;

import dao.IVoucherByProductDAO;
import model.VoucherByProduct;

import java.util.List;

public class IVoucherByProductServiceImpl implements service.IVoucherByProductService {
    IVoucherByProductDAO voucherDao = new dao.IVoucherByProductDAOImpl();
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
        voucherDao.delete(voucher);
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
