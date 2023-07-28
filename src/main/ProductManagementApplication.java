package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import frame.ProductCategoryRegiserFrame;
import frame.ProductColorRegiserFrame;
import frame.ProductRegiserFrame;
import frame.ProductSearchFrame;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductManagementApplication extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductManagementApplication frame = new ProductManagementApplication();
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
	public ProductManagementApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton productRegisterFrameOpenButton = new JButton("상품등록");
		productRegisterFrameOpenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductRegiserFrame productRegiserFrame = new ProductRegiserFrame();
				productRegiserFrame.setVisible(true);
			}
		});
		productRegisterFrameOpenButton.setBounds(12, 35, 410, 40);
		contentPane.add(productRegisterFrameOpenButton);
		
		JButton productSearchFrameOpenButton = new JButton("상품조회");
		productSearchFrameOpenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductSearchFrame productSearchFrame = 
						ProductSearchFrame.getInstance();
				productSearchFrame.setVisible(true);
			}
		});
		productSearchFrameOpenButton.setBounds(12, 85, 410, 40);
		contentPane.add(productSearchFrameOpenButton);
		
		JButton productColorRegisterFrameOpenButton = new JButton("상품색상등록");
		productColorRegisterFrameOpenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductColorRegiserFrame productColorRegiserFrame= new ProductColorRegiserFrame();
				productColorRegiserFrame.setVisible(true);
			}
		});
		productColorRegisterFrameOpenButton.setBounds(12, 135, 410, 40);
		contentPane.add(productColorRegisterFrameOpenButton);
		
		JButton productCategoryRegisterFrameOpenButton = new JButton("상품카테고리등록");
		productCategoryRegisterFrameOpenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductCategoryRegiserFrame productCategoryRegiserFrame = new ProductCategoryRegiserFrame();
				productCategoryRegiserFrame.setVisible(true);
			}
		});
		productCategoryRegisterFrameOpenButton.setBounds(12, 185, 410, 40);
		contentPane.add(productCategoryRegisterFrameOpenButton);
	}
}
