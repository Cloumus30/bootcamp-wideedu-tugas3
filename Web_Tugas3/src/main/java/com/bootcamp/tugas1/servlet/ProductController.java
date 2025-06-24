package com.bootcamp.tugas1.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;

import com.bootcamp.tugas1.entities.Product;
import com.bootcamp.tugas1.service.ProductService;
import com.bootcamp.tugas1.service.TypeService;
import com.bootcamp.tugas1.utils.DBUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/products")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
    private ProductService service;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		ProductService service;
		String dispatcherName = "";
		
		if("form-add".equals(action)) {
			try {
				TypeService typeService = new TypeService();
				request.setAttribute("types", typeService.findAll());
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dispatcherName = "ProductAddForm.jsp";
			
		}else if("csv".equals(action)){
			// Set response headers
	        response.setContentType("text/csv");
	        response.setHeader("Content-Disposition", "attachment; filename=\"products.csv\"");
	        
	        List<Product> products;
			try {
//				service = new ProductService();
				products = this.service.findAll();
				
//				Get Writer
	        	PrintWriter writer = response.getWriter();
	            // Header File
	            writer.println("Name, Type, Price");

	            for (Product product : products) {
	                writer.println(product.getName()+", "+product.getType().getName()+", "+product.getPrice());
	            }
	            
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        
		}else if("form-view".equals(action)) {
			try {
				TypeService typeService = new TypeService();
//				service = new ProductService();
				int productId = Integer.parseInt(request.getParameter("product"));
				
				request.setAttribute("types", typeService.findAll());
				request.setAttribute("product", this.service.findOneById(productId));
				
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dispatcherName = "ProductViewForm.jsp";
		} else {
			try {
//				service = new ProductService();
				request.setAttribute("products", this.service.findAll());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dispatcherName = "ProductListCatalog.jsp";
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
		
		try {
//			ProductService service = new ProductService();
			if("1".equals(isDelete)) {
				int productId = Integer.parseInt(request.getParameter("product_id"));
				service.delete(productId);
			}else if("1".equals(isUpdate)) {
				String productName = request.getParameter("name");
				Double price = Double.parseDouble(request.getParameter("price"));
				int productId = Integer.parseInt(request.getParameter("product_id"));
				int typeId = Integer.parseInt(request.getParameter("type_id"));
				
				Product product = new Product(productName, price, typeId);
				
				service.update(product, productId);
			}
			else {
				String productName = request.getParameter("name");
				Double price = Double.parseDouble(request.getParameter("price"));
				int typeId = Integer.parseInt(request.getParameter("type_id"));
				
				Product product = new Product(productName, price, typeId);
				
				service.insert(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("products");
	}

}
