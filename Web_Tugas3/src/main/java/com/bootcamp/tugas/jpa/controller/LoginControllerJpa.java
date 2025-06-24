package com.bootcamp.tugas.jpa.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class LoginControllerJpa
 */
@WebServlet("/jpa/login")
public class LoginControllerJpa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControllerJpa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("../Login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if("cloudias".equals(username) && "secret".equals(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("customer_name", "Cloudias Imani Pradana");
			session.setAttribute("address", "Jawa Timur Indonesia");
			System.out.println(session.getAttribute(username));
			response.sendRedirect("product-list");
			return;
		}
		request.setAttribute("login_message", "Credentials does not match, Login Failed");
		RequestDispatcher dispatcher = request.getRequestDispatcher("../Login.jsp");
		dispatcher.forward(request, response);
	}

}
