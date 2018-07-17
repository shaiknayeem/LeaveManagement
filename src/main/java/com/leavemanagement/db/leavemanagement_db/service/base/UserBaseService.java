package com.leavemanagement.db.leavemanagement_db.service.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import com.leavemanagement.db.leavemanagement_db.service.RolesService;

import com.leavemanagement.db.leavemanagement_db.entity.User;
import com.leavemanagement.db.leavemanagement_db.service.UserService;

//IMPORT RELATIONS
import com.leavemanagement.db.leavemanagement_db.entity.Leave;

@Service
public class UserBaseService {

	private static NamedParameterJdbcTemplate jdbcTemplate;
	
		@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	private static RolesService rolesService = new RolesService();


    //CRUD METHODS
    
    	
        //CRUD - CREATE
    	
	public User insert(User obj) {
		Long id = jdbcTemplate.queryForObject("select max(_id) from `user`", new MapSqlParameterSource(), Long.class);
		obj.set_id(id == null ? 1 : id + 1);
		String sql = "INSERT INTO `user` (`_id`, `username`, `password`, `mail`,`name`,`surname` )	VALUES (:id, :username , :password, :mail,:name,:surname )";
		SqlParameterSource parameters = new MapSqlParameterSource()
		    .addValue("id", obj.get_id())
			.addValue("mail", obj.getMail())
			.addValue("name", obj.getName())
			.addValue("password", obj.getPassword())
			.addValue("surname", obj.getSurname())
			.addValue("username", obj.getUsername())
			.addValue("leave", obj.getLeave());

		jdbcTemplate.update(sql, parameters);
		this.updateRoles(obj.get_id(), obj.getRoles());
		return obj;
	}
	
    	
    //CRUD - REMOVE
    
	public void delete(Long id) {
		String sql = "DELETE FROM `User` WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		
		jdbcTemplate.update(sql, parameters);
	}

    	
    //CRUD - FIND BY Leave
    	
	public List<User> findByleave(Long idleave) {
		
		String sql = "select * from `User` WHERE `leave` = :idleave";
		
	    SqlParameterSource parameters = new MapSqlParameterSource()
		.addValue("idleave", idleave);
	    
	    return jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper(User.class));
	}
    	
    //CRUD - GET ONE
    	
	public User get(Long id) {
	    
		String sql = "select * from `User` where `_id` = :id";
		
	    SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
	    
	    return (User) jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper(User.class));
	}


    	
        	
    //CRUD - GET LIST
    	
	public List<User> getList() {
	    
		String sql = "select * from `User`";
		
	    SqlParameterSource parameters = new MapSqlParameterSource();
	    List<User> list = jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper(User.class));
	    
	    for (User user : list) {
			user = this.addRoles(user);
		}
	    
	    return list;
	    
	    
	}

    	
    //CRUD - EDIT
    	
	public User update(User obj, Long id) {

		String sql = "UPDATE `User` SET `mail`,`name`,`surname` WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
		    .addValue("id", obj.get_id())
			.addValue("mail", obj.getMail())
			.addValue("name", obj.getName())
			.addValue("surname", obj.getSurname())
			.addValue("leave", obj.getLeave());
		jdbcTemplate.update(sql, parameters);
	    return obj;
	}
	
    	
    
    
    
    

    
    /*
     * CUSTOM SERVICES
     * 
     *	These services will be overwritten and implemented in UserService.java
     */
    
    
    /*
    
    YOU CAN COPY AND MODIFY THIS METHOD IN UserService.java
    
    Name: changePassword
    Description: Change password of user from admin
    Params: 
    */
	public Object changePassword () {
		
        return null;
        
	}
	
	
    	public User login(String username, String password) {
		String sql = "select * from `user` where `username` = :username AND  `password` = :password";
		
	    SqlParameterSource parameters = new MapSqlParameterSource()
		.addValue("username", username)
		.addValue("password", password);
	    
	    try {
		    User user = (User) jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper(User.class));
		    user = this.addRoles(user);
		    return user;
	    } catch(EmptyResultDataAccessException e){
	    	return null;
	    }
	}
		public boolean changePassword(Long id_user, Map<String, String> params) throws Exception {
		
		//AuthenticationService auth =(AuthenticationService) SecurityContextHolder.getContext().getAuthentication();

		String usernameAdmin = SecurityContextHolder.getContext().getAuthentication().getName();
		String passwordAdmin = params.get("passwordAdmin");
		String passwordNew= params.get("passwordNew");

		// Check admin user
		User admin = this.login(usernameAdmin, passwordAdmin);
		
		if(admin == null)
			throw new Exception("Admin password not valid");
		if (!admin.hasRole("ADMIN"))
			throw new Exception("User is not admin");
		
		return this.updatePassword(id_user, passwordNew);
	}
	

	// UTILS FUNCTION

	public boolean updatePassword(Long id_user, String password) {
		String sql = "UPDATE `user` SET `password` = :password WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id_user)
			.addValue("password", password);
		Integer rowNum = jdbcTemplate.update(sql, parameters);
		return rowNum != null && rowNum > 0;
	}
	
    public User addRoles(User user) {
	    ArrayList<String> roles = rolesService.getRolesAsStringArray(user.get_id());
	    user.setRoles(roles);
	    return user;
	}

	public void updateRoles(Long id_user, ArrayList<String> roles) {

		// Delete not in array
	    rolesService.deleteNotInArray(id_user, roles);
		
		// Get actual    		
	    List<String> actual = rolesService.getRolesAsStringArray(id_user);
	    
		// Insert new
		for (String role : roles) {
			if (actual.indexOf(role) == -1){
				rolesService.insert(id_user, role);
			}
		}
		
	}

}
