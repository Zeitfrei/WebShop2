package shop.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import shop.HardwareShop;
import shop.persistence.BinaryStrategy;
import shop.persistence.XMLStrategy;
import shop.persistence.XStreamStrategy;

public class MenuListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "BinaryStrategy":
				HardwareShop.getShopController().setFileSerializationStrategy(new BinaryStrategy());
				break;
			case "XMLStrategy":
				HardwareShop.getShopController().setFileSerializationStrategy(new XMLStrategy());
				break;
			case "XStreamStrategy":
				HardwareShop.getShopController().setFileSerializationStrategy(new XStreamStrategy());
				break;
		}
	}
}
