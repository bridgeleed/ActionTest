package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.User;
import util.DBUtils;
/**
 * DAO�࣬�����װ���ݷ����߼�������������ݿ�
 * @author Super_man
 *
 */
public class UserDao {
	
	
	public void delete(int id){
		
		Connection conn = null;
		PreparedStatement ps =null;
		try {
			conn = DBUtils.getConn();
			String sql = "delete from t_user where id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.close(null, ps, conn);
		}
		
	}
	/**
	 * �����ݿ����������Ϣ
	 * @param user
	 * @throws SQLException 
	 */
	
	public void add(User user) throws SQLException{
		Connection conn = null;
		PreparedStatement ps =null;//Ԥ�����ִ�ж���
		try {
			conn = DBUtils.getConn();
			String sql = "insert into t_user values(null,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(null, ps, conn);
		}
		
		
	}
	
	/**
	 * ���ڽ������û���ѯ������ÿһ���û�����Ϣ��Ӧһ��User����
	 * ����һ������Щ������ɵļ���
	 * @return
	 * @throws SQLException 
	 */
	public List<User> findAll() throws SQLException{
		List<User> users = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_user";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email"); 
				User user = new User();
				user.setId(id);
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(email);
				users.add(user);
					
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			//�ֲ���� ,��ϵͳ�׳���servlet
			throw e;
		}finally{
			DBUtils.close(rs, ps, conn);
			
		}
		
		return users;
	}
	/**
	 * �����û�����ѯָ���û�����Ϣ������Ҳ��� ����null
	 * @throws SQLException 
	 */
	public boolean select_login(String uname,String pwd) throws SQLException{
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_user";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String username = rs.getString("username");
				String password = rs.getString("password"); 
				if (username.equals(uname)) {
					if(password.equals(pwd)){
						return true;
					}else {
						return false;
					}
				}
				
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}finally{
			DBUtils.close(rs, ps, conn);
		}
		return false;
	}
	/**
	 * �����û�����ѯָ���û�����Ϣ������Ҳ��� ����null
	 * @throws SQLException 
	 */
	public User login_judge(String username) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_user where username=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}finally{
			DBUtils.close(rs, ps, conn);
		}
		
		return user;
	}
	
}
