package productProject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import productProject.dao.ProductDao;
import productProject.model.Product;

@Component
@Controller
public class MainController {
	
	@Autowired
	private ProductDao productDao;
	
	@RequestMapping("/")
	public String homePage(Model model)
	{
		List<Product> products = productDao.getProducts();
		model.addAttribute("product",products);
		return "index";
	}
	
	//show add product form
	@RequestMapping("/add-product")
	public String addProduct(Model model)
	{
		model.addAttribute("title", "Add Product");
		return "add_product_form";
	}
	
	//handle add product form
	@RequestMapping(value = "/handle-product",method = RequestMethod.POST)
	public RedirectView handleProduct(@ModelAttribute Product product,HttpServletRequest httpServletRequest)
	{
		System.out.println(product);
		productDao.createProduct(product);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(httpServletRequest.getContextPath() + "/");
		return redirectView;
	}
	
	//delete handler
	@RequestMapping("/delete/{productId}")
	public RedirectView deleteProduct(@PathVariable("productId") int productId,HttpServletRequest httpServletRequest)
	{
		productDao.deleteProduct(productId);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(httpServletRequest.getContextPath() + "/");
		return redirectView;
	}
	
	//update handler
	@RequestMapping("/update/{productId}")
	public String updateProduct(@PathVariable("productId") int productId,HttpServletRequest httpServletRequest,Model model)
	{
		Product product = productDao.getProduct(productId);
		model.addAttribute("product",product);
		return "update_form";
	}

}
