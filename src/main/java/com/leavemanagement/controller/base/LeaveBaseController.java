package com.leavemanagement.controller.base;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.ArrayList;
import org.springframework.security.access.annotation.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import com.leavemanagement.db.leavemanagement_db.service.LeaveService;
import com.leavemanagement.db.leavemanagement_db.entity.Leave;

//IMPORT RELATIONS
import com.leavemanagement.db.leavemanagement_db.entity.Leave;

public class LeaveBaseController {
    
    @Autowired
	LeaveService leaveService;



//CRUD METHODS


    //CRUD - CREATE
    @Secured({ "ROLE_PRIVATE_USER" })
		@RequestMapping(value = "/leaves", method = RequestMethod.POST, headers = "Accept=application/json")
	public Leave insert(@RequestBody Leave obj) {
		Leave result = leaveService.insert(obj);

	    
		
		return result;
	}

	
    //CRUD - REMOVE
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/leaves/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void delete(@PathVariable("id") Long id) {
		leaveService.delete(id);
	}
	
	
    //CRUD - GET ONE
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/leaves/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Leave get(@PathVariable Long id) {
		Leave obj = leaveService.get(id);
		
		
		
		return obj;
	}
	
	
    //CRUD - GET LIST
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/leaves", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Leave> getList() {
		return leaveService.getList();
	}
	
	

    //CRUD - EDIT
    @Secured({ "ROLE_PRIVATE_USER" })
	@RequestMapping(value = "/leaves/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
	public Leave update(@RequestBody Leave obj, @PathVariable("id") Long id) {
		Leave result = leaveService.update(obj, id);

	    
		
		return result;
	}
	


/*
 * CUSTOM SERVICES
 * 
 *	These services will be overwritten and implemented in  Custom.js
 */


	
}
