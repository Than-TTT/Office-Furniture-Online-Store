package dao;


import model.ProductType;

import java.util.List;


public interface IProductType {
	void insert(ProductType productType);
	void update(ProductType productType);
	void delete(int typeId);
	ProductType findById(int typeId);
	List<ProductType> findAll();
	int count();

	List<ProductType> findAllByPage(int offset, int limit);

}

