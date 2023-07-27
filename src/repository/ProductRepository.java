package repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DBConnectionMgr;
import entity.Product;

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
	
	public Product findProductByProductName(String productName) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null;
		
		try {
			con = pool.getConnection();
			String sql = "select "
					+ "product_id, "
					+ "product_name "
					+ "from "
					+ "product_tb "
					+ "where "
					+ "product_name = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = Product.builder()
						.productId(rs.getInt(1))
						.productName(rs.getString(2))
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
}









