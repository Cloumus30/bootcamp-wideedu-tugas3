package com.bootcamp.tugas.jpa.controller;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.bootcamp.tugas.jpa.entities.Product;
import com.bootcamp.tugas.jpa.entities.Type;
import com.bootcamp.tugas.jpa.repositories.ProductRepository;
import com.bootcamp.tugas.jpa.repositories.TypeRepository;
import com.bootcamp.tugas1.service.ProductService;
import com.bootcamp.tugas1.service.TypeService;

/**
 * Servlet implementation class ProductControllerJpa
 */
@WebServlet("/jpa/products")
public class ProductControllerJpa extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Inject
    private TypeRepository typeRepo;
    
    @Inject
    private ProductRepository productRepo;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductControllerJpa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String dispatcherName = "";
		
		if("form-add".equals(action)) {
			request.setAttribute("types", typeRepo.findAll());
			dispatcherName = "../ProductAddForm.jsp";
			
		}else if("csv".equals(action)){
			// Set response headers
	        response.setContentType("text/csv");
	        response.setHeader("Content-Disposition", "attachment; filename=\"products.csv\"");
	        
	        List<Product> products;
			//				service = new ProductService();
							products = productRepo.findAll();
							
			//				Get Writer
				        	PrintWriter writer = response.getWriter();
				            // Header File
				            writer.println("Name, Type, Price");
			
				            for (Product product : products) {
				                writer.println(product.getName()+", "+product.getType().getName()+", "+product.getPrice());
				            }
	        
		}else if("form-view".equals(action)) {
			//				TypeService typeService = new TypeService();
			//				service = new ProductService();
							int productId = Integer.parseInt(request.getParameter("product"));
							
			request.setAttribute("types", typeRepo.findAll());
			request.setAttribute("product", productRepo.findById(productId));
			dispatcherName = "../ProductViewForm.jsp";
		} else {
			//				service = new ProductService();
			request.setAttribute("products", productRepo.findAll());
			dispatcherName = "../ProductListCatalog.jsp";
		}
		if(!dispatcherName.equals("")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(dispatcherName);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isDelete = request.getParameter("is_delete");
		String isUpdate = request.getParameter("is_update");
		
		//			ProductService service = new ProductService();
		if("1".equals(isDelete)) {
			int productId = Integer.parseInt(request.getParameter("product_id"));
			productRepo.delete(productId);
		}else if("1".equals(isUpdate)) {
			String productName = request.getParameter("name");
			Double price = Double.parseDouble(request.getParameter("price"));
			int productId = Integer.parseInt(request.getParameter("product_id"));
			int typeId = Integer.parseInt(request.getParameter("type_id"));
			
			 Type type = typeRepo.findById(typeId);
			
			Product product = new Product(productName, price, type);
			
			productRepo.update(productId, product);
		}
		else {
			String productName = request.getParameter("name");
			Double price = Double.parseDouble(request.getParameter("price"));
			int typeId = Integer.parseInt(request.getParameter("type_id"));
			
			Type type = typeRepo.findById(typeId);
			
			Product product = new Product(productName, price, type);
			
			productRepo.insertProduct(product);
		}
		
		response.sendRedirect("products");
	}

}
