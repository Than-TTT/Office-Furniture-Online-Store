package service;


import dao.IProductType;
import dao.ProductTypeDaoImpl;

import java.util.List;
public class ProductTypeServiceImpl implements service.IProductTypeService {

    private final IProductType productTypeDao = new ProductTypeDaoImpl();

    @Override
    public void addProductType(model.ProductType productType) {
        productTypeDao.insert(productType);
    }

    @Override
    public void updateProductType(model.ProductType productType) {
        productTypeDao.update(productType);
    }

    @Override
    public void deleteProductType(int typeId) {
        try {
            productTypeDao.delete(typeId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting product type with ID: " + typeId);
        }
    }

    @Override
    public model.ProductType getProductTypeById(int typeId) {
        return productTypeDao.findById(typeId);
    }

    @Override
    public List<model.ProductType> getAllProductTypes() {
        return productTypeDao.findAll();
    }

    @Override
    public int getProductTypeCount() {
        return productTypeDao.count();
    }

    @Override
    public List<model.ProductType> getAllProductTypesByPage(int offset, int limit) {
        return productTypeDao.findAllByPage(offset, limit);
    }

}

