package studentInfoManage;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.ldap.Rdn;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import entity.User;

public class LoginServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uname = request.getParameter("username");
		String pwd = request.getParameter("password");
		UserDao dao = new UserDao();
		try {
			User user = dao.login_judge(uname);
			if (user != null && user.getPassword().equals(pwd)) {
				response.sendRedirect("list");
			}else {
				request.setAttribute("login_failed", "�û������������");
			    request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("error", "ϵͳ��æ���Ժ����ԣ�");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
//		boolean flag = dao.select_login(uname, pwd);
//		if (flag==true) {
//			response.sendRedirect("list");
//		}else {
//			//1.�����ݵ�request����
//			request.setAttribute("users", uname+pwd);
//			//2.���ת����
//			RequestDispatcher  rd = request.getRequestDispatcher("login.jsp");
//			//3.ת��
//			rd.forward(request, response);
//		}
	}

}
