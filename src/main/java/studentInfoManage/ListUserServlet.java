package studentInfoManage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import entity.User;
import util.DBUtils;

public class ListUserServlet extends HttpServlet{
	public ListUserServlet() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ѯ�����û���Ϣ
//		response.setContentType("text/html;charset=utf-8");
//		
//		PrintWriter out = response.getWriter();
		
		//��Session��֤
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
			
//			//���ݲ�ѯ���Ľ�����ɱ��
//			out.println("<table align='center' border='2'bgcolor='#add8e6' width='60%' cellpadding='0' cellspacing='0'>"); 
//			out.print("<tr><td>ID</td><td>�û���</td><td>����</td><td>����</td><td>����</td></tr>");
//			for(User u:users){
//				int id = u.getId();
//				String username = u.getUsername();
//				String password = u.getPassword();
//				String email = u.getEmail();
//				
//				out.println("<tr><td>"+id+"</td><td>"+username+"</td><td>"+
//				      password+"</td><td>"+email+"</td><td><a href='del?id="+id+"'>ɾ��</a></td></tr>");
//			
//			}
//			out.println("</table>"); 
//			//����һ��������
//			out.println("<p><a href='stuInfo.html'>����û�</a></p>");
			 
		
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			//ת����ϵͳ�쳣����ҳ��
			//1.������
			request.setAttribute("error", "ϵͳ��æ���Ժ����ԣ�");
			request.getRequestDispatcher("error.jsp")
			 .forward(request, response);
			
		}
		
		
		
		
	}

}
