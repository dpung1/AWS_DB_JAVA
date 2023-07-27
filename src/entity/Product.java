package entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
	private int productId;
	private String productName;
	private int productPrice;
	private int productcolorId;
	private int productcategoryId;
	
	private ProductColor productColor;
	private ProductCategory productCategory;
}
