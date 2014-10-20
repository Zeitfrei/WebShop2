package shop.view;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

public class ViewCustomer extends JFrame {
	private static final long serialVersionUID = 4404058995092529085L;
	private JFrame frame;
	private JPanel panel1, panel2, panel3;
	private JButton bestellen;
	private String[] arr = new String[100];
	private JPasswordField pW;
	private JTextField tF;

	public void startView() {
		for (int i = 0; i < 100; i++) {
			arr[i] = String.valueOf(i);
		}

		frame = new JFrame("View-Customer");
		frame.setLayout(new BorderLayout());

		panel1 = new JPanel();
		panel1.setLayout(new MigLayout("wrap 4", "[140!][140!][140!][140!]", ""));
		panel2 = new JPanel();

		bestellen = new JButton("Bestellen");
		bestellen.addActionListener(new bestellListener());

		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.SOUTH);

		panel2.add(bestellen);

		panel1.add(new JLabel("Name"));
		panel1.add(new JLabel("Quantity"));
		panel1.add(new JLabel("Price"));
		panel1.add(new JLabel("Buy"), "wrap 20px");

		// Example data
		panel1.add(new JLabel("R�ckenkratzer"));
		panel1.add(new JLabel("28"));
		panel1.add(new JLabel("4�"));
		panel1.add(new JComboBox(arr));

		pW = new JPasswordField();
		tF = new JTextField();
		panel3 = new JPanel(new GridLayout(2, 2));

		pW.setEchoChar('*');

		panel3.add(new JLabel("Benutzername: "));
		panel3.add(tF);
		panel3.add(new JLabel("Passwort: "));
		panel3.add(pW);

		frame.pack();
		frame.setSize(600, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	class bestellListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, panel3);
		}
	}
}
