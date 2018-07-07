package studentInfoManage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.AllMain;


public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   @Override
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	   String uri = request.getRequestURI();
	String  path =  uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
	System.out.println("PATH"+path);
	if ("/login".equals(path)) {
		AllMain.login(request, response);
		
	}else if ("/list".equals(path)) {
		AllMain.list(request, response);
		
	}else if ("/del".equals(path)) {
		AllMain.del(request, response);
	}else if ("/add".equals(path)) {
		AllMain.add(request, response);
	}
	
	   
}
	

}
