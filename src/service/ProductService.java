package service;

import java.util.List;

import entity.Product;
import repository.ProductRepository;

public class ProductService {
	
	private ProductRepository productRepository;
	private static ProductService instance;
	
	private ProductService() {
		productRepository = ProductRepository.getInstace();
	}
	
	public static ProductService getInstance() {
		if(instance == null) {
			instance = new ProductService();
		}
		return instance;
	}
	
	public boolean isProductNameDuplicated(String productName) {
		boolean result = false;
		result = productRepository.findProductByProductName(productName) != null;
		return result;
	}
	
	public boolean registerProduct(Product Product) {
		return productRepository.saveProduct(Product) > 0;
		
	}
	public List<Product> searchProduct(String searchOption, String searchValue){
		return productRepository.getSearchProductList(searchOption, searchValue);
	}
	
	public boolean removeProduct(int productId) {
		return productRepository.deleteProduct(productId) > 0;
	}
	
	public Product getProductByProdcutId(int productId) {
		return productRepository.findProductByProductId(productId);
	}
	
	public boolean modifyProduct(Product product) {
		return productRepository.updateProduct(product) > 0;
	}
}


