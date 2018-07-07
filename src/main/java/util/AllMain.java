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
				//��½�ɹ�
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect("list.go");
			}else {
				request.setAttribute("login_failed", "�û������������");
			    request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "ϵͳ��æ���Ժ����ԣ�");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		
	}
	
	public static void list(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		setUnicode(request, response);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user==null){
			//û�е�½���ض��򵽵�½ҳ��
			response.sendRedirect("login.jsp");
			return;
		}
		
		try {
			
			UserDao userdao = new UserDao();
			List<User> users = userdao.findAll();
			/**
			 * ��Ϊservlet���������ɸ��ӵ�ҳ�棬��������֪ͨת����jsp����jsp������ҳ��		 */
			//1.�����ݵ�request����
			request.setAttribute("users", users);
			//2.���ת����
			RequestDispatcher  rd = request.getRequestDispatcher("listUser3.jsp");
			//3.ת��
			rd.forward(request, response);
} catch (SQLException e) {
			
			e.printStackTrace();
			//ת����ϵͳ�쳣����ҳ��
			//1.������
			request.setAttribute("error", "ϵͳ��æ���Ժ����ԣ�");
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
		System.out.println("�˺ţ�"+username+"  "+"���룺"+password+"  "+"���䣺"+email);
		
		//���û���Ϣ���뵽���ݿ�

		try {
			 
			
			UserDao userdao =  new UserDao();
			User user1 = userdao.login_judge(username);
			
			if (user1!=null) {
				request.setAttribute("add_failed", "�û��Ѿ�����");
				request.getRequestDispatcher("addUser.jsp").forward(request, response);
				
			}else {
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(email);
				
					userdao.add(user);
					//�ض����û��б�
					response.sendRedirect("list.go");
			}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("error", "ϵͳ��æ���Ժ����ԣ�");
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
