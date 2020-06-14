package com.shivangi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/homePage")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet resultset = null;
	Statement statement = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String username = request.getParameter("username");
		String pass = request.getParameter("pass");

		String sql = "select * from users where username='" + username + "'";
		try {

			resultset = statement.executeQuery(sql);
			String un = null;
			String pw = null;
			@SuppressWarnings("unused")
			String name = null;
			PrintWriter prwr1 = response.getWriter();
			if (!resultset.isBeforeFirst()) {
				prwr1.write("<h1> No Such User in Database<h1>");
			} else {

				while (resultset.next()) {
					un = resultset.getString("username");
					pw = resultset.getString("password");
					name = resultset.getString("name");
				}

				PrintWriter pww = response.getWriter();

				if (un.equalsIgnoreCase(username) && pw.equals(pass)) {

					response.setContentType("text/html");
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/homePage.html");
					dispatcher.forward(request, response);
				} else {
					pww.write("Wrong username or password\n");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
