package service;

import java.util.List;

import model.ProductImage;

public interface IProductImageService {
	void addProductImage(ProductImage productImage);
	void deleteProductImage(ProductImage productImage);
	List<ProductImage> getProductImagesByProductId(int productId);
	List<ProductImage> getAllProductImages();
	int getProductImageCount();
}
