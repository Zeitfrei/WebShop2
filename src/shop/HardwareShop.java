package shop;

import shop.controller.ControllerShop;
import shop.view.ViewCustomer;

public class HardwareShop {
	private static ControllerShop shop_controller;

	public static ControllerShop getShopController() {
		return shop_controller;
	}

	public static void main(String[] args) {
		shop_controller = new ControllerShop();

		ViewCustomer customer = new ViewCustomer();
		//customer.startView();
	}
}
