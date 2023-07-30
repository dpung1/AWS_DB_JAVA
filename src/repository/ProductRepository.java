package repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConnectionMgr;
import entity.Product;
import entity.ProductCategory;
import entity.ProductColor;

public class ProductRepository {
	
	private DBConnectionMgr pool;
	private static ProductRepository instance;
	
	private ProductRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static ProductRepository getInstace() {
		if(instance == null) {
			instance = new ProductRepository();
		}
		
		return instance;
	}
	
	public Product findProductByProductId(int productId) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null;
		
		try {
			con = pool.getConnection();
			String sql = "select\r\n"
					+ "	pt.product_id,\r\n"
					+ "    pt.product_name,\r\n"
					+ "    pt.product_price,\r\n"
					+ "    \r\n"
					+ "    pt.product_color_id,\r\n"
					+ "    pcot.product_color_name,\r\n"
					+ "    \r\n"
					+ "    pt.product_category_id,\r\n"
					+ "    pcat.product_category_name\r\n"
					+ "from\r\n"
					+ "	product_tb pt\r\n"
					+ "    left outer join product_color_tb pcot \r\n"
					+ "		on (pcot.product_color_id = pt.product_color_id)\r\n"
					+ "    left outer join product_category_tb pcat \r\n"
					+ "		on (pcat.product_category_id = pt.product_category_id)\r\n"
					+ "where\r\n"
					+ "	pt.product_id = ?";
					
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = Product.builder()
						.productId(rs.getInt(1))
						.productName(rs.getString(2))
						.productPrice(rs.getInt(3))
						.productColor(ProductColor.builder()
								.productColorId(rs.getInt(4))
								.productColorName(rs.getNString(5))
								.build())
						.productCategory(ProductCategory.builder()
								.productCategoryId(rs.getInt(6))
								.productCategoryName(rs.getString(7))
								.build())
						.build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return product;
		
	}
	
	public Product findProductByProductName(String productName) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null;
		
		try {
			con = pool.getConnection();
			String sql = "select\r\n"
					+ "	pt.product_id,\r\n"
					+ "    pt.product_name,\r\n"
					+ "    pt.product_price,\r\n"
					+ "    \r\n"
					+ "    pt.product_color_id,\r\n"
					+ "    pcot.product_color_name,\r\n"
					+ "    \r\n"
					+ "    pt.product_category_id,\r\n"
					+ "    pcat.product_category_name\r\n"
					+ "from\r\n"
					+ "	product_tb pt\r\n"
					+ "    left outer join product_color_tb pcot \r\n"
					+ "		on (pcot.product_color_id = pt.product_color_id)\r\n"
					+ "    left outer join product_category_tb pcat \r\n"
					+ "		on (pcat.product_category_id = pt.product_category_id)\r\n"
					+ "where\r\n"
					+ "	pt.product_name = ?";
					
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = Product.builder()
						.productId(rs.getInt(1))
						.productName(rs.getString(2))
						.productPrice(rs.getInt(3))
						.productColor(ProductColor.builder()
								.productColorId(rs.getInt(4))
								.productColorName(rs.getNString(5))
								.build())
						.productCategory(ProductCategory.builder()
								.productCategoryId(rs.getInt(6))
								.productCategoryName(rs.getString(7))
								.build())
						.build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return product;
		
	}
	
	public int saveProduct(Product product) {
		
		Connection con = null;
		CallableStatement cstmt = null;
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "{ call p_insert_product(?, ?, ?, ?) }";
			cstmt = con.prepareCall(sql);
			
			cstmt.setString(1, product.getProductName());
			cstmt.setInt(2, product.getProductPrice());
			cstmt.setString(3, product.getProductColor().getProductColorName());
			cstmt.setString(4, product.getProductCategory().getProductCategoryName());
			
			successCount = cstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if(cstmt != null) {
				try {
					cstmt.close();
				} catch (SQLException e) { 
					e.printStackTrace();
				}
			}
			pool.freeConnection(con);
		}
		
		return successCount;
	}
	
	public List<Product> getSearchProductList(String searchOption, String searchValue) {
	    Connection con = null;
	    CallableStatement cstmt = null;
	    ResultSet rs = null;
	    List<Product> productList = null;
	    
	    try {
	        con = pool.getConnection();
	        String sql = "{ call p_select_product(?, ?) }";
	        
	        cstmt = con.prepareCall(sql);
	        
	        cstmt.setString(1, searchOption);
	        cstmt.setString(2, searchValue);
	        
	        rs = cstmt.executeQuery();
	        
	        productList = new ArrayList<>();
	        
	        while (rs.next()) {
	            Product product = Product.builder()
	                .productId(rs.getInt(1))
	                .productName(rs.getString(2))
	                .productPrice(rs.getInt(3))
	                .productcolorId(rs.getInt(4))
	                .productColor(ProductColor.builder()
	                    .productColorId(rs.getInt(4))
	                    .productColorName(rs.getString(5))
	                    .build())
	                .productcategoryId(rs.getInt(6))
	                .productCategory(ProductCategory.builder()
	                    .productCategoryId(rs.getInt(6))
	                    .productCategoryName(rs.getString(7))
	                    .build())
	                .build();
	            productList.add(product);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con);
	    }
	    
	    return productList;
	}
	public int deleteProduct(int productId) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "delete from product_tb where product_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productId);
			successCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return successCount;
	}
	
	public int updateProduct(Product product) {
		
		Connection con = null;
		CallableStatement cstmt = null;
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "{ call p_update_product(?, ?, ?, ?, ?) }";
			
			cstmt = con.prepareCall(sql);
			
			cstmt.setInt(1, product.getProductId());
			cstmt.setString(2, product.getProductName());
			cstmt.setInt(3, product.getProductPrice());
			cstmt.setString(4, product.getProductColor().getProductColorName());
			cstmt.setString(5, product.getProductCategory().getProductCategoryName());
			
			successCount = cstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con);
		}
		
		return successCount;
	}
}









