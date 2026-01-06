package cnpm.ergo.service.implement;


import java.util.List;

import cnpm.ergo.service.interfaces.IProductService;
import cnpm.ergo.DAO.implement.*;
import cnpm.ergo.DAO.interfaces.*;
import cnpm.ergo.entity.Product;
public class ProductServiceImpl implements IProductService {

    private final IProductDao productDao = new ProductDaoImpl();

    @Override
    public void addProduct(Product product) {
        productDao.insert(product);
    }

    @Override
    public void updateProduct(Product product) {
        productDao.update(product);
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            productDao.delete(productId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting product with ID: " + productId);
        }
    }
    @Override
    public Product getProductById(int productId) {
        return productDao.findById(productId);
    }
    @Override
    public List<String> getAllColors() {
        return productDao.findAllColors();
    }
    @Override
    public List<String> getAllMaterials() {
        return productDao.findAllMaterials();
    }
    @Override
    public List<Double> getAllLengths() {
        return productDao.findAllLengths();
    }
    @Override
    public List<Double> getAllHeights() {
        return productDao.findAllHeights();
    }

    @Override
    public int getProductCount() {
        return productDao.count();
    }
    @Override
    public int getProductAvailableCount() {
        return  productDao.countAvailable();
    }

    @Override
    public List<Product> getProductsByPage(int offset, int limit) {
        return productDao.findProductsByPage(offset, limit);
    }

    @Override
    public List<Product> getProductsAvailableByPage(int offset, int limit) {
        return productDao.findProductsAvailableByPage(offset, limit);
    }
    @Override
    public List<Product> getAllProducts(int page, int size) {
        return productDao.findAllList(page, size);
    }
    
    @Override
    public List<Product> findByKeywordOrCategory(String keyword, String categoryName) {
        return productDao.findByKeywordOrCategory(keyword, categoryName);
    }

    @Override
    public List<Product> applyFiltersAfterKeywordOrCategory(List<Long> productIdsLong, String filterPrice, String[] colors,
            String[] materials, String[] heights, String[] lengths) {
        return productDao.applyFiltersAfterKeywordOrCategory(productIdsLong, filterPrice, colors, materials, heights, lengths);
    }
    @Override
    public long getProductCount (String keyword, String categoryName, String filterPrice, String[] colors, String[] materials,
			String[] heights, String[] lengths) {
    	return productDao.Count(keyword, categoryName, filterPrice, colors, materials, heights, lengths);
    }
    @Override
    public List<Product> findRelatedProductsByProductId(int productId,int page, int pageSize) {
        return productDao.findRelatedProductsByProductId(productId, page, pageSize);
    }
    @Override
    public long getTotalRelatedProducts(int productId) {
        return productDao.getTotalRelatedProducts(productId);
    }
    
    public List<Product> findAllProduct() {
        return productDao.findAll();
    }
    
    public static void main(String[] args) {
    }
}

