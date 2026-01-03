package service;

import java.util.List;

public interface IProductTypeService {
	void addProductType(model.ProductType productType);
	void updateProductType(model.ProductType productType);
	void deleteProductType(int typeId);
	model.ProductType getProductTypeById(int typeId);
	List<model.ProductType> getAllProductTypes();
	int getProductTypeCount();

	List<model.ProductType> getAllProductTypesByPage(int offset, int limit);

}
