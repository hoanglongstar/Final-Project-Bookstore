package com.bookstore.client.shopcart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.client.services.OrderServiecs;
import com.bookstore.client.services.ProductService;
import com.bookstore.model.entities.Product;



@Controller
public class ShoppingCartController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderServiecs orderService;
	
	@RequestMapping("/addtocart")
	public String byProductHandler(HttpServletRequest request, Model model, @RequestParam(value = "code", defaultValue = "") String code) {
		
		Product product = productService.getByCode(code);
		
		if (product != null) {
			CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
			cartInfo.addProduct(product, 1);
		}

		return "redirect:/shopping_cart";
	}
	
	@RequestMapping("/shopping_cart")
	public String showCartView(HttpServletRequest request, Model model) {
		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);

		model.addAttribute("cartInfo", cartInfo);
		model.addAttribute("totalCartInfo", cartInfo.totalCartInfo());
		
		return "cart";
	}
	
	@PostMapping("/update_cart")
	public String updateCart(@ModelAttribute("cartInfo") CartInfo cartForm ,HttpServletRequest request, Model model) {
		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
		System.out.println("updateCart -> " + cartInfo.getCartLines().size());
		
		for (int i = 0; i < cartInfo.getCartLines().size(); i++) {
			cartInfo.getCartLines().get(i).setQuantity(cartForm.getCartLines().get(i).getQuantity());
		}
		return "redirect:/shopping_cart";
	}
	
	@GetMapping("/checkout")
	public String checkoutShoppingCart(HttpServletRequest req, Model model) {
		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(req);
		model.addAttribute("orderCode", orderService.saveOrder(cartInfo));
		ShopCartSessionUtil.removeCartInSession(req);
		return "checkout";
	}
	
	@GetMapping("/deletecart")
	public String removeCart(HttpServletRequest request) {
		ShopCartSessionUtil.removeCartInSession(request);
		
		return "redirect:/shopping_cart";
	}
}
