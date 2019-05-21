
package com.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.model.ProductQuantityOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.model.Product;
import com.service.ProductService;

import static java.lang.Boolean.TRUE;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	// Getters and Setters

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	// Configuration for MultiPartResolver
	// Multipart resolver is for uploading images and other media
	// maxupload size is for image size should not be maximum than 10240000

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(10240000);
		return multipartResolver;
	}

	// which displays the list of products to the productList page
	@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping(value="/getAllProducts", method = RequestMethod.GET)
	 public String getAllProducts(Model model) {
		List<Product> products = productService.getAllProducts();
		List<HashMap<String, Object>> allItem=new ArrayList<>();
		for (Product product:products){
			HashMap<String, Object> map = new HashMap<>();
			map.put("productId",product.getProductId());
			map.put("name",product.getName());
			map.put("description",product.getDescription());
			map.put("category",product.getCategory());
			map.put("subCategory",product.getSubCategory());
			map.put("selectedQuantity",product.getSelectedQuantity());
			map.put("cuisine",product.getCuisine());
			map.put("isAdd",product.getIsAdd());
			List<HashMap<String, Object>> quantityOptions=new ArrayList<>();
			for (ProductQuantityOptions quantO:product.getQuantityOption()){
				HashMap<String, Object> opt = new HashMap<>();
				opt.put("option",quantO.getOption());
				opt.put("price",quantO.getPrice());
				opt.put("quantity",quantO.getQuantity());
				quantityOptions.add(opt);
			}
			map.put("quantityOption",quantityOptions);
			allItem.add(map);
		}
		model.addAttribute("itemList", allItem);
		return "jsonTemplate";
	}

	// this is used for getting the product by productId
    @CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping(value="/getProductById/{productId}", method = RequestMethod.GET)
	public String getProductById(@PathVariable(value = "productId") int productId,Model model) {
        HashMap<String, Object> map = new HashMap<>();
	    Product product = productService.getProductById(productId);
	    if (product==null){
            model.addAttribute("itemDetails", map);
            return "jsonTemplate";
        }
        map.put("productId",product.getProductId());
        map.put("name",product.getName());
        map.put("description",product.getDescription());
        map.put("category",product.getCategory());
        map.put("subCategory",product.getSubCategory());
        map.put("selectedQuantity",product.getSelectedQuantity());
        map.put("cuisine",product.getCuisine());
        map.put("isAdd",product.getIsAdd());
        List<HashMap<String, Object>> quantityOptions=new ArrayList<>();
        for (ProductQuantityOptions quantO:product.getQuantityOption()){
            HashMap<String, Object> opt = new HashMap<>();
            opt.put("option",quantO.getOption());
            opt.put("price",quantO.getPrice());
            opt.put("quantity",quantO.getQuantity());
            quantityOptions.add(opt);
        }
        map.put("quantityOption",quantityOptions);
        model.addAttribute("itemDetails", map);
        return "jsonTemplate";
		//return new ModelAndView("productPage", "productObj", product);
	}

	@RequestMapping(value ="/admin/delete/{productId}",method = RequestMethod.DELETE)
	public String deleteProduct(@PathVariable(value = "productId") int productId) {
		productService.deleteProduct(productId);
		return "redirect:/getAllProducts";
	}
	@RequestMapping("/admin/disable/{productId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void disableProduct(@PathVariable(value = "productId") int productId) {

		// Here the Path class is used to refer the path of the file

		productService.disableProduct(productId);
		// http://localhost:8080/shoppingCart/getAllProducts
		//return "redirect:/getAllProducts";
	}
	@RequestMapping("/admin/enable/{productId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void enableProduct(@PathVariable(value = "productId") int productId) {

		// Here the Path class is used to refer the path of the file
		productService.enableProduct(productId);
		// http://localhost:8080/shoppingCart/getAllProducts
		//return "redirect:/getAllProducts";
	}

    @CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	//public String addProduct(@Valid @ModelAttribute(value = "productFormObj") Product product, BindingResult result) {
    public String addProduct(@RequestBody Product product, BindingResult result){
		// Binding Result is used if the form that has any error then it will
		// redirect to the same page without performing any functions
		if (result.hasErrors())
			return "addProduct";
		product.setIsAdd(TRUE);
		productService.addProduct(product);
		/*MultipartFile image = product.getProductImage();
		if (image != null && !image.isEmpty()) {
			*//*Path path = Path.getFileName("C:/Users/bijbeher.ORADEV/Documents/workspace_JAVA/ShoppingCart-master/src/main/webapp/WEB-INF/resource/images/"
                    + product.getProductId() + ".jpg") {
            };*//*

			try {
				*//*image.transferTo(new File(path.toString()));*//*
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}*//* catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}*/
		return "redirect:/getAllProducts";
	}

	@RequestMapping(value = "/admin/product/editProduct/{productId}")
	public ModelAndView getEditForm(@PathVariable(value = "productId") int productId) {
		Product product = productService.getProductById(productId);
		return new ModelAndView("editProduct", "editProductObj", product);
	}
	@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping(value = "/admin/product/editProduct", method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
	public String editProduct(@RequestBody Product product, BindingResult result) {
		if (result.hasErrors())
			return "addProduct";
		//productService.addProduct(product);
		productService.editProduct(product);
		return "redirect:/getAllProducts";
	}

	@RequestMapping("/getProductsList")
	public @ResponseBody List<Product> getProductsListInJson() {
		return productService.getAllProducts();
	}

	@RequestMapping("/productsListAngular")
	public String getProducts() {
		return "productListAngular";
	}

}
