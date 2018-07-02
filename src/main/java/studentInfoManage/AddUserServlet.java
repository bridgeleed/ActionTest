package studentInfoManage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import entity.User;
import util.DBUtils;

public class AddUserServlet extends HttpServlet {
	
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");	
		response.setContentType("text/html;charset=utf-8");
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
					response.sendRedirect("list");
			}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("error", "ϵͳ��æ���Ժ����ԣ�");
				request.getRequestDispatcher("error.jsp")
				 .forward(request, response);
				
				
			}
			
		
		
		
	}
	

}
