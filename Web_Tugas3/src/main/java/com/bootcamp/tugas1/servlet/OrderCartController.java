package com.bootcamp.tugas1.servlet;

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

import com.bootcamp.tugas1.entities.Order;
import com.bootcamp.tugas1.entities.OrderItem;
import com.bootcamp.tugas1.entities.Product;
import com.bootcamp.tugas1.service.OrderService;
import com.bootcamp.tugas1.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class OrderCartController
 */
@WebServlet("/product-list")
public class OrderCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderCartController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatcherName = "";
		String action = request.getParameter("action");
		
		try {
			if("cart".equals(action)) {
				HttpSession session = request.getSession();
				
				Type orderItemsTypeHashMap = new TypeToken<HashMap<Integer, OrderItem>>() {}.getType();
				
				String orderItemsSession = (String) session.getAttribute("orderItems");
				Gson gson = new Gson();
				HashMap<Integer, OrderItem> orderItems = gson.fromJson(orderItemsSession, orderItemsTypeHashMap);
				 if(orderItems == null) {
			    	   orderItems = new HashMap<Integer, OrderItem>();
			       }
				 
//				System.out.println(orderItems);
//				Get Total Price
				double totalPrice = 0;
				for (Integer key : orderItems.keySet()) {
				    totalPrice += orderItems.get(key).getPrice() * orderItems.get(key).getQuantity();
				}
				request.setAttribute("totalPrice", totalPrice);
				request.setAttribute("orderItems", orderItems);
				dispatcherName = "ProductCart.jsp";
			}else {
				ProductService productService = new ProductService();
				List<Product> products = productService.findAll();
				
				dispatcherName = "ListsProduct.jsp";
				request.setAttribute("products", products);
			}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Type orderItemsTypeHashMap = new TypeToken<HashMap<Integer, OrderItem>>() {}.getType();
			
	    	String orderItemsSession = (String) session.getAttribute("orderItems");
	    	String customerName = (String) session.getAttribute("customer_name");
	    	String address = (String) session.getAttribute("address");
			Gson gson = new Gson();
			HashMap<Integer, OrderItem> orderItems = gson.fromJson(orderItemsSession, orderItemsTypeHashMap);
			
			if(orderItems != null) {
				List<OrderItem> orderItemsList = new ArrayList<OrderItem>(orderItems.values());
				
				Order order = new Order(customerName, address);
				for (OrderItem orderItem : orderItemsList) {
					order.addOrderItem(orderItem);
				}
				try {
					OrderService orderService = new OrderService();
					int result = orderService.insertOrder(order);
					if(result > 0) {
						session.removeAttribute("orderItems");
					}
				} catch (NamingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }
	    
	    response.sendRedirect("product-list");

	}
	
	protected void addOrderItemIntoSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		Receive Json Payload From Fetch Js
		BufferedReader reader = request.getReader(); 
        Gson gson = new Gson();
        JsonObject productJson = gson.fromJson(reader, JsonObject.class);
        
//        Create OrderItem Object from request
        OrderItem orderItem = new OrderItem(productJson.get("product_id").getAsInt(), 
        		productJson.get("name").getAsString(), 
        		productJson.get("type").getAsString(), 
        		productJson.get("price").getAsDouble(), 
        		productJson.get("quantity").getAsInt());
       
//        Get Order Items from Session Attribute
       HttpSession session = request.getSession();
       String orderItemsSession = (String) session.getAttribute("orderItems");
       
//       Declare Order Items Type as Hashmap
       HashMap<Integer, OrderItem> orderItems;
       Type orderItemsTypeHashMap = new TypeToken<HashMap<Integer, OrderItem>>() {}.getType();
       
//       Parse Session Order Items into Hashmap of Integer And Order Item
       orderItems = gson.fromJson(orderItemsSession, orderItemsTypeHashMap);
//       Initialize if orderItems null
       if(orderItems == null) {
    	   orderItems = new HashMap<Integer, OrderItem>();
       }
       
//       Check if orderItems hashmap has OrderItem or not
       if(orderItems.size() > 0) {
//    	   Add Quantity orderItems if OrderItem with same product_id exists
    	   OrderItem existingOrderItem = orderItems.get(orderItem.getProductId());
    	   if(existingOrderItem != null) {
    		   orderItem.setQuantity(orderItem.getQuantity() + existingOrderItem.getQuantity());
    	   }
       }
//       Add orderItem into OrderItems hashMap
       orderItems.put(orderItem.getProductId(), orderItem);
       
//       Add OrderItems hashmap into session
       session.setAttribute("orderItems", gson.toJson(orderItems));
	}

}
