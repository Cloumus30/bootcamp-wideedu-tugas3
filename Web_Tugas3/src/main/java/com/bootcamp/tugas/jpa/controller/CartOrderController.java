package com.bootcamp.tugas.jpa.controller;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;

import com.bootcamp.tugas.jpa.entities.OrderItem;
import com.bootcamp.tugas.jpa.entities.Product;
import com.bootcamp.tugas.jpa.repositories.OrderRepository;
import com.bootcamp.tugas.jpa.repositories.ProductRepository;
import com.bootcamp.tugas.jpa.dto.OrderItemDto;
import com.bootcamp.tugas.jpa.entities.Order;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class CartOrderController
 */
@WebServlet("/jpa/product-list")
public class CartOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Inject
    private OrderRepository orderRepo;
    @Inject
    private ProductRepository productRepo;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartOrderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcherName = "";
		String action = request.getParameter("action");
		
		if("cart".equals(action)) {
			HttpSession session = request.getSession();
			
			Type orderItemsTypeHashMap = new TypeToken<HashMap<Integer, OrderItemDto>>() {}.getType();
			
			String orderItemsSession = (String) session.getAttribute("orderItems");
			Gson gson = new Gson();
			HashMap<Integer, OrderItemDto> orderItems = gson.fromJson(orderItemsSession, orderItemsTypeHashMap);
			 if(orderItems == null) {
		    	   orderItems = new HashMap<Integer, OrderItemDto>();
		       }
			 
//				System.out.println(orderItems);
//				Get Total Price
			double totalPrice = 0;
			for (Integer key : orderItems.keySet()) {
			    totalPrice += orderItems.get(key).getPrice() * orderItems.get(key).getQuantity();
			}
			request.setAttribute("totalPrice", totalPrice);
			request.setAttribute("orderItems", orderItems);
			dispatcherName = "../ProductCart.jsp";
		}else {
//				ProductService productService = new ProductService();
			List<Product> products = productRepo.findAll();
			
			dispatcherName = "../ListsProduct.jsp";
			request.setAttribute("products", products);
		}
		
		if(dispatcherName !="") {
			RequestDispatcher dispatcher = request.getRequestDispatcher(dispatcherName);
			dispatcher.forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contentType = request.getContentType();
		String action = request.getParameter("action");
		
	    if (("application/json".equals(contentType))) {
//			Update ORder Item in Session
	    	if("order-item-session".equals(action)) {
	    		this.addOrderItemIntoSession(request, response);
	    	}
		    response.getWriter()
	        .append("Success Add Product");
		    return;
	    }
	    
	    if("place-order".equals(action)) {
	    	HttpSession session = request.getSession();
			Type orderItemsTypeHashMap = new TypeToken<HashMap<Integer, OrderItemDto>>() {}.getType();
			
	    	String orderItemsSession = (String) session.getAttribute("orderItems");
	    	String customerName = (String) session.getAttribute("customer_name");
	    	String address = (String) session.getAttribute("address");
			Gson gson = new Gson();
			HashMap<Integer, OrderItemDto> orderItems = gson.fromJson(orderItemsSession, orderItemsTypeHashMap);
			
			if(orderItems != null) {
				List<OrderItemDto> orderItemsList = new ArrayList<OrderItemDto>(orderItems.values());
				
				Order order = new Order(customerName, address);
				List<Integer> productIds = new ArrayList<Integer>();
				for (OrderItemDto orderItem : orderItemsList) {
					productIds.add(orderItem.getproductId());
				}
				HashMap<Integer, Product> productsMap = productRepo.findAllWhereIn(productIds);
				
				for (OrderItemDto orderItemDto : orderItemsList) {
//					new OrderItem
					order.addOrderItem(new OrderItem(orderItemDto.getProductName(), 
							orderItemDto.getType(), 
							orderItemDto.getPrice(), 
							orderItemDto.getQuantity(), 
							productsMap.get(orderItemDto.getproductId()) ));
				}
				order.countTotalPrice();
			//					OrderService orderService = new OrderService();
				orderRepo.insert(order);
			
				session.removeAttribute("orderItems");
			}
	    }
	    
	    response.sendRedirect("product-list");
	}
	
	protected void addOrderItemIntoSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		Receive Json Payload From Fetch Js
		BufferedReader reader = request.getReader(); 
        Gson gson = new Gson();
        JsonObject productJson = gson.fromJson(reader, JsonObject.class);
        Product product = productRepo.findById(productJson.get("product_id").getAsInt());
//        Create OrderItem Object from request
        OrderItemDto orderItemDto = new OrderItemDto(productJson.get("name").getAsString(), 
        		productJson.get("type").getAsString(), 
        		productJson.get("price").getAsDouble(), 
        		productJson.get("quantity").getAsInt(),
        		productJson.get("product_id").getAsInt());
       
//        Get Order Items from Session Attribute
       HttpSession session = request.getSession();
       String orderItemsSession = (String) session.getAttribute("orderItems");
       
//       Declare Order Items Type as Hashmap
       HashMap<Integer, OrderItemDto> orderItems;
       Type orderItemsTypeHashMap = new TypeToken<HashMap<Integer, OrderItemDto>>() {}.getType();
       
//       Parse Session Order Items into Hashmap of Integer And Order Item
       orderItems = gson.fromJson(orderItemsSession, orderItemsTypeHashMap);
//       Initialize if orderItems null
       if(orderItems == null) {
    	   orderItems = new HashMap<Integer, OrderItemDto>();
       }
       
//       Check if orderItems hashmap has OrderItem or not
       if(orderItems.size() > 0) {
//    	   Add Quantity orderItems if OrderItem with same product_id exists
    	   OrderItemDto existingOrderItem = orderItems.get(orderItemDto.getproductId());
    	   if(existingOrderItem != null) {
    		   orderItemDto.setQuantity(orderItemDto.getQuantity() + existingOrderItem.getQuantity());
    	   }
       }
//       Add orderItem into OrderItems hashMap
       
       orderItems.put(orderItemDto.getproductId(), orderItemDto);
       System.out.println(orderItems);
       System.out.println(orderItems.get(1));
//       Add OrderItems hashmap into session
       session.setAttribute("orderItems", gson.toJson(orderItems));
       System.out.println(orderItems);
	}
}
