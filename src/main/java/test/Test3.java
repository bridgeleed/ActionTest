package test;

import java.sql.SQLException;

import dao.UserDao;
import entity.User;

public class Test3 {
 public static void main(String[] args) {
	      try {
			User user = new UserDao().login_judge("��״��");
			System.out.println(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
