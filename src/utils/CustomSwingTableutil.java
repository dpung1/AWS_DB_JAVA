package utils;

import java.util.List;

import entity.Product;

public class CustomSwingTableutil {
	
	public static String[][] searchProductListToArray(List<Product> searchProductList) {
		if(searchProductList == null) {
			return null;
		}
		
		String[][] searchProductModelArray = new String[searchProductList.size()][7];
		
		for(int i = 0; i < searchProductList.size(); i++) {
			searchProductModelArray[i][0] = Integer.toString(searchProductList.get(i).getProductId()); 
			searchProductModelArray[i][1] = searchProductList.get(i).getProductName(); 
			searchProductModelArray[i][2] = Integer.toString(searchProductList.get(i).getProductPrice()); 
			searchProductModelArray[i][3] = Integer.toString(searchProductList.get(i).getProductcolorId()); 
			searchProductModelArray[i][4] = searchProductList.get(i).getProductColor().getProductColorName();
			searchProductModelArray[i][5] = Integer.toString(searchProductList.get(i).getProductcategoryId()); 
			searchProductModelArray[i][6] = searchProductList.get(i).getProductCategory().getProductCategoryName();
		}
		return searchProductModelArray;
	}
}
