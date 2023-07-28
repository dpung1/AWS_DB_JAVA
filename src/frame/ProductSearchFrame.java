package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import entity.Product;
import service.ProductService;
import utils.CustomSwingTableutil;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductSearchFrame extends JFrame {

	private JPanel contentPane;
	private JTextField searchTextField;
	private JTable productTable;
	
	private DefaultTableModel searchProductTableModel;
	private JComboBox comboBox;
	
	private JButton productRemoveButton;
	private JButton productModifyButton;
	
	private static ProductSearchFrame instance;
	
	

	private ProductSearchFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLable = new JLabel("상품조회");
		titleLable.setBounds(12, 10, 120, 25);
		contentPane.add(titleLable);
		
		productModifyButton = new JButton("수정");
		productModifyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { 
				if(!productModifyButton.isEnabled()) { return; }
				
				int productId = Integer.parseInt((String)searchProductTableModel.getValueAt(productTable.getSelectedRow(), 0));
				
				ProductModifyFrame productModifyFrame = 
						new ProductModifyFrame(productId);
				productModifyFrame.setVisible(true);
				

			}
		});
		productModifyButton.setBounds(764, 10, 98, 25);
		contentPane.add(productModifyButton);
		
		productRemoveButton = new JButton("삭제");
		productRemoveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!productRemoveButton.isEnabled()) { return; }
				int ProductId = Integer.parseInt((String)searchProductTableModel.getValueAt(productTable.getSelectedRow(), 0));
				if(!ProductService.getInstance().removeProduct(ProductId)) {
					JOptionPane.showMessageDialog(contentPane, "상품삭제 중 오류가 발생하였습니다.", "삭제오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				JOptionPane.showMessageDialog(contentPane, "선택한 상품을 삭제하였습니다.", "삭제성공", JOptionPane.PLAIN_MESSAGE);
				setSearchProductTableModel();
			}
		});
		productRemoveButton.setBounds(874, 11, 98, 25);
		contentPane.add(productRemoveButton);
		
		JLabel searchLable = new JLabel("검색");
		searchLable.setHorizontalAlignment(SwingConstants.RIGHT);
		searchLable.setBounds(549, 45, 50, 25);
		contentPane.add(searchLable);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"전체", "상품명", "색상", "카테고리"}));
		comboBox.setBounds(611, 46, 109, 23);
		contentPane.add(comboBox);
		
		searchTextField = new JTextField();
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					setSearchProductTableModel();
					
				}
			}
		});
		searchTextField.setBounds(732, 46, 240, 23);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 80, 960, 371);
		contentPane.add(scrollPane);
		
		productTable = new JTable();
		setSearchProductTableModel();
		productTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productModifyButton.setEnabled(true);
				productRemoveButton.setEnabled(true);
			}
		});
		scrollPane.setViewportView(productTable);
	}
	
	public static ProductSearchFrame getInstance() {
		if(instance == null) {
			instance = new ProductSearchFrame();
		}
		return instance;
	}
	
	public void setSearchProductTableModel() {
		String searchOption = (String) comboBox.getSelectedItem();
		String searchValue = searchTextField.getText();
		
		
		List<Product> searcProductList = ProductService.getInstance().searchProduct(searchOption, searchValue);
		String[][] searchProductModelArray = CustomSwingTableutil.searchProductListToArray(searcProductList);
		
		searchProductTableModel = new DefaultTableModel(
			searchProductModelArray,
			new String[] {
				"product_id", "product_name", "product_price", "product_color_id", "product_color_name", "product_category_id", "product_category_name" 
			}	
		);
		productTable.setModel(searchProductTableModel);
		productModifyButton.setEnabled(false);
		productRemoveButton.setEnabled(false);
	}
}











