package util;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.group.interceptors.StaticMembershipInterceptor;

import dao.UserDao;
import entity.User;

public class AllMain {
	
	public static void login(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		setUnicode(request, response);
		String uname = request.getParameter("username");
		String pwd = request.getParameter("password");
		UserDao dao = new UserDao();
		User user;
		try {
			user = dao.login_judge(uname);
			if (user != null && user.getPassword().equals(pwd)) {
				//登陆成功
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect("list.go");
			}else {
				request.setAttribute("login_failed", "用户名或密码错误！");
			    request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "系统繁忙，稍后重试！");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		
	}
	
	public static void list(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		setUnicode(request, response);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user==null){
			//没有登陆，重定向到登陆页面
			response.sendRedirect("login.jsp");
			return;
		}
		
		try {
			
			UserDao userdao = new UserDao();
			List<User> users = userdao.findAll();
			/**
			 * 因为servlet不便于生成复杂的页面，所以我们通知转发给jsp，由jsp来生成页面		 */
			//1.绑订数据到request上面
			request.setAttribute("users", users);
			//2.获得转发器
			RequestDispatcher  rd = request.getRequestDispatcher("listUser3.jsp");
			//3.转发
			rd.forward(request, response);
} catch (SQLException e) {
			
			e.printStackTrace();
			//转发到系统异常处理页面
			//1.绑订数据
			request.setAttribute("error", "系统繁忙，稍后重试！");
			request.getRequestDispatcher("error.jsp")
			 .forward(request, response);
			
		}
		
	}

	public static void add(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		setUnicode(request, response);
		HttpSession session = request.getSession();
		User user2 = (User)session.getAttribute("user");
		if (user2==null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");	
		PrintWriter out = response.getWriter();
		System.out.println("账号："+username+"  "+"密码："+password+"  "+"邮箱："+email);
		
		//将用户信息插入到数据库

		try {
			 
			
			UserDao userdao =  new UserDao();
			User user1 = userdao.login_judge(username);
			
			if (user1!=null) {
				request.setAttribute("add_failed", "用户已经存在");
				request.getRequestDispatcher("addUser.jsp").forward(request, response);
				
			}else {
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(email);
				
					userdao.add(user);
					//重定向到用户列表
					response.sendRedirect("list.go");
			}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("error", "系统繁忙，稍后重试！");
				request.getRequestDispatcher("error.jsp")
				 .forward(request, response);
				
				
			}
			
	}

	public static void del(HttpServletRequest request,HttpServletResponse response) throws IOException{
		setUnicode(request, response);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if (user==null) {
			response.sendRedirect("login.jsp");
			return;
		}
		String id = request.getParameter("id");
		UserDao userdao = new UserDao();
		userdao.delete(Integer.parseInt(id));
		response.sendRedirect("list.go");
	}

	
	public static void setUnicode(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
	}
}
