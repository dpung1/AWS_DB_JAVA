package service;

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
	
	public boolean registerProduct(Product Product) {
		return productRepository.saveProduct(Product) > 0;
		
	}
	
}


