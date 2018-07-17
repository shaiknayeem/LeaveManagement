package com.leavemanagement.controller.base;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.ArrayList;
import org.springframework.security.access.annotation.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import com.leavemanagement.db.leavemanagement_db.service.UserService;
import com.leavemanagement.db.leavemanagement_db.entity.User;

//IMPORT RELATIONS
import com.leavemanagement.db.leavemanagement_db.entity.Leave;

public class UserBaseController {
    
    @Autowired
	UserService userService;



//CRUD METHODS


    //CRUD - CREATE
    @Secured({ "ROLE_PRIVATE_USER" })
		@RequestMapping(value = "/Users", method = RequestMethod.POST, headers = "Accept=application/json")
	public User insert(@RequestBody User obj) {
		User result = userService.insert(obj);

	    
		
		return result;
	}

	
    //CRUD - REMOVE
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/Users/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void delete(@PathVariable("id") Long id) {
		userService.delete(id);
	}
	

    //CRUD - FIND BY Leave
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/Users/findByleave/{key}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<User> findByleave(@PathVariable("key") Long idleave) {
		List<User> list = userService.findByleave(idleave);
		return list;
	}
	
    //CRUD - GET ONE
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/Users/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public User get(@PathVariable Long id) {
		User obj = userService.get(id);
		
		
		
		return obj;
	}
	
	
    //CRUD - GET LIST
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/Users", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<User> getList() {
		return userService.getList();
	}
	
	

    //CRUD - EDIT
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/Users/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
	public User update(@RequestBody User obj, @PathVariable("id") Long id) {
		User result = userService.update(obj, id);

	    
		
		return result;
	}
	


/*
 * CUSTOM SERVICES
 * 
 *	These services will be overwritten and implemented in  Custom.js
 */


    /*
    Name: changePassword
    Description: Change password of user from admin
    Params: 
    
    
    @RequestMapping(value = "/Users/{id}/changePassword", method = RequestMethod.POST, headers = "Accept=application/json")
    public Object changePassword() {
		return new HashMap<String, String>();
    }
    */
    		

	
}
