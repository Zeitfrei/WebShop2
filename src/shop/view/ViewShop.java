package shop.view;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import shop.model.ModelShop;
import fpt.com.Product;

public class ViewShop implements Observer {
	JButton addProductButton = new JButton("Add Product");
	JTextPane nameText, quantityText, priceText;
	private JPanel panel2, panel3, panel4;
	JFrame frame;

	/**
	 * required because the controller wants to add itself as an ActionListener 
	 */
	public JButton getAddProductButton() {
		return addProductButton;
	}

	/**
	 * @return the value of the name field form
	 */
	public String newProductsName() {
		return nameText.getText();
	}

	/**
	 * @return the value of the quantity field form
	 */
	public Integer newProductsQuantity() {
		return Integer.parseInt(quantityText.getText());
	}

	/**
	 * @return the value of the price field form
	 */
	public Double newProductsPrice() {
		return Double.parseDouble(priceText.getText());
	}

	/**
	 * reset the form (required after successfully adding a product)
	 */
	public void resetForm() {
		nameText.setText("");
		quantityText.setText("");
		priceText.setText("");
	}

	/**
	 * initialize the view and all of it's components
	 * 
	 * @param model  a <code>ModelShop</code> displayed in the window 
	 */
	public ViewShop(ModelShop model) {
		frame = new JFrame("View-Shop");
		frame.setLayout(new BorderLayout());

		frame.addWindowListener(new SerializeOnClose());

		panel2 = new JPanel(new MigLayout("wrap 3", "[130!][130!][130!]", ""));
		panel3 = new JPanel(new MigLayout());
		panel4 = new JPanel(new MigLayout());

		nameText = new JTextPane();
		quantityText = new JTextPane();
		priceText = new JTextPane();

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Offline");
		JMenuItem menuItem1 = new JMenuItem("Work Offline");
		menu.add(menuItem1);
		JMenuItem menuItem2 = new JMenuItem("Work Online");
		menu.add(menuItem2);
		menuBar.add(menu);

		JMenu persistence_menu = new JMenu("Persistence");

		JMenuItem persistence_menu_item_1 = new JMenuItem("BinaryStrategy");
		persistence_menu_item_1.addActionListener(new MenuListener());
		persistence_menu.add(persistence_menu_item_1);

		JMenuItem persistence_menu_item_2 = new JMenuItem("XMLStrategy");
		persistence_menu_item_2.addActionListener(new MenuListener());
		persistence_menu.add(persistence_menu_item_2);

		JMenuItem persistence_menu_item_3 = new JMenuItem("XStreamStrategy");
		persistence_menu_item_3.addActionListener(new MenuListener());
		persistence_menu.add(persistence_menu_item_3);

		menuBar.add(persistence_menu);

		panel2.setBorder(LineBorder.createBlackLineBorder());
		panel3.setBorder(LineBorder.createBlackLineBorder());

		panel2.add(new JLabel("Name"));
		panel2.add(new JLabel("Quantity"));
		panel2.add(new JLabel("Price"), "wrap 20px");

		for (Product product : model.getProducts()) {
			panel2.add(new JLabel(product.getName()));
			panel2.add(new JLabel(String.valueOf(product.getQuantity())));
			panel2.add(new JLabel(String.valueOf(product.getPrice())));
		}
	

		panel3.add(new JLabel("Orders"), "w 350!");
		nameText.setBorder(LineBorder.createBlackLineBorder());
		quantityText.setBorder(LineBorder.createBlackLineBorder());
		priceText.setBorder(LineBorder.createBlackLineBorder());
		
		nameText.addKeyListener(new java.awt.event.KeyAdapter() {
		    public void keyPressed(java.awt.event.KeyEvent evt) {
		        diagInputKeyPressed(evt);
		    }
		});

		quantityText.addKeyListener(new java.awt.event.KeyAdapter() {
		    public void keyPressed(java.awt.event.KeyEvent evt) {
		        diagInputKeyPressed(evt);
		    }
		});
		priceText.addKeyListener(new java.awt.event.KeyAdapter() {
		    public void keyPressed(java.awt.event.KeyEvent evt) {
		        diagInputKeyPressed(evt);
		    }
		});
		
		panel4.add(new JLabel("Name:"));
		panel4.add(new JLabel("Quantity:"));
		panel4.add(new JLabel("Price:"), "wrap");
		panel4.add(nameText, "w 200!");
		
		panel4.add(quantityText, "w 200!");
		panel4.add(priceText, "w 200!");
		panel4.add(addProductButton);
		

		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		frame.getContentPane().add(panel2, BorderLayout.WEST);
		frame.getContentPane().add(panel3, BorderLayout.EAST);
		frame.getContentPane().add(panel4, BorderLayout.SOUTH);
	}
	
	private void diagInputKeyPressed(java.awt.event.KeyEvent evt) {
	    if(evt.getKeyCode() == KeyEvent.VK_ENTER){
	    	if(addProductButton.hasFocus()){
	    	
	    	}
	    }
		if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
	    	if(nameText.hasFocus()){
	    		quantityText.requestFocus();
	    	}else if(quantityText.hasFocus()){
	    		priceText.requestFocus();
	    	}else if(priceText.hasFocus()){
	    		addProductButton.requestFocus();
	    	}
	    }
	}

	

	/**
	 * pack, position and show the window
	 */
	public void viewShop() {
		frame.pack();
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * a callback to notify the view whenever the model changes
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object p) {
		panel2.add(new JLabel(((Product) p).getName()));
		panel2.add(new JLabel(String.valueOf(((Product) p).getQuantity())));
		panel2.add(new JLabel(String.valueOf(((Product) p).getPrice())));
		panel2.revalidate();
	}
}
