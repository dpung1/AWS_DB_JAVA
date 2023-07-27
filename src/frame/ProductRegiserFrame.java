package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Product;
import entity.ProductCategory;
import entity.ProductColor;
import service.ProductCategoryService;
import service.ProductColorService;
import service.ProductService;
import utils.CustomSwingComboBoxUtil;
import utils.CustomSwingTextUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductRegiserFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productNameTextField;
	private JTextField productPriceTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductRegiserFrame frame = new ProductRegiserFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductRegiserFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLable = new JLabel("상품등록");
		titleLable.setHorizontalAlignment(SwingConstants.CENTER);
		titleLable.setBounds(12, 10, 410, 21);
		contentPane.add(titleLable);
		
		JLabel productnameLabel = new JLabel("상품명");
		productnameLabel.setBounds(12, 72, 57, 21);
		contentPane.add(productnameLabel);
		
		productNameTextField = new JTextField();
		productNameTextField.setBounds(81, 72, 341, 21);
		contentPane.add(productNameTextField);
		productNameTextField.setColumns(10);
		
		productPriceTextField = new JTextField();
		productPriceTextField.setColumns(10);
		productPriceTextField.setBounds(81, 103, 341, 21);
		contentPane.add(productPriceTextField);
		
		JLabel productPriceLable = new JLabel("가격");
		productPriceLable.setBounds(12, 103, 57, 21);
		contentPane.add(productPriceLable);
		
		JLabel productColorLabel = new JLabel("색상");
		productColorLabel.setBounds(12, 134, 57, 21);
		contentPane.add(productColorLabel);
		
		JComboBox colorComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setComboBoxModel(colorComboBox, ProductColorService.getInstance().getProductColorNameList());
		colorComboBox.setBounds(81, 133, 341, 23);
		contentPane.add(colorComboBox);
		
		JLabel productCtegoryLabel = new JLabel("카테고리");
		productCtegoryLabel.setBounds(12, 165, 57, 21);
		contentPane.add(productCtegoryLabel);
		
		JComboBox CategoryComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setComboBoxModel(CategoryComboBox, ProductCategoryService.getInstance().getProductCategoryNameList());
		CategoryComboBox.setBounds(81, 164, 341, 23);
		contentPane.add(CategoryComboBox);
		
		JButton registerSubmitButton = new JButton("등록하기");
		registerSubmitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String prodcutName = productNameTextField.getText();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, prodcutName)) {return;}
				
				String productPrice = productPriceTextField.getText();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productPrice)) {return;}
				
				String productColorName = (String) colorComboBox.getSelectedItem();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productColorName)) {return;}
				
				String productCategoryName = (String) CategoryComboBox.getSelectedItem();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productCategoryName)) {return;}
				
				if(ProductService.getInstance().isProductNameDuplicated(prodcutName)) {
					JOptionPane.showMessageDialog(contentPane, "이미 존재하는 상품명입니다.", "중복오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Product product = Product.builder()
						.productName(prodcutName)
						.productPrice(Integer.parseInt(productPrice))
						.productColor(ProductColor.builder().productColorName(productColorName).build())
						.productCategory(ProductCategory.builder().productCategoryName(productCategoryName).build())
						.build();
				
				if(!ProductService.getInstance().registerProduct(product)) {
					JOptionPane.showMessageDialog(contentPane, "상품등록 중 오류가 발생했습니다.", "등록오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				JOptionPane.showMessageDialog(contentPane, "상품을 등록하였습니다.", "등록성공", JOptionPane.PLAIN_MESSAGE);
				CustomSwingTextUtil.clearTextField(productNameTextField);
				CustomSwingTextUtil.clearTextField(productPriceTextField);
				colorComboBox.setSelectedIndex(0);
				CategoryComboBox.setSelectedIndex(0);
			}
		});
		registerSubmitButton.setBounds(12, 196, 410, 55);
		contentPane.add(registerSubmitButton);
		
		
		

	}
}
