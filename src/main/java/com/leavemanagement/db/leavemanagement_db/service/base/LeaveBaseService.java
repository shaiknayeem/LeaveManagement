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

import com.leavemanagement.db.leavemanagement_db.entity.Leave;
import com.leavemanagement.db.leavemanagement_db.service.LeaveService;

//IMPORT RELATIONS
import com.leavemanagement.db.leavemanagement_db.entity.Leave;

@Service
public class LeaveBaseService {

	private static NamedParameterJdbcTemplate jdbcTemplate;
	
		@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	


    //CRUD METHODS
    
    //CRUD - CREATE
    	
	public Leave insert(Leave obj) {
		Long id = jdbcTemplate.queryForObject("select max(_id) from `leave`", new MapSqlParameterSource(), Long.class);
		obj.set_id(id == null ? 1 : id + 1);
		String sql = "INSERT INTO `leave` (`_id`, `cancel`,`fromDate`,`reason`,`status`,`toDate`,`type`) VALUES (:id,:cancel,:fromDate,:reason,:status,:toDate,:type)";
			SqlParameterSource parameters = new MapSqlParameterSource()
		    .addValue("id", obj.get_id())
			.addValue("cancel", obj.getCancel())
			.addValue("fromDate", obj.getFromDate())
			.addValue("reason", obj.getReason())
			.addValue("status", obj.getStatus())
			.addValue("toDate", obj.getToDate())
			.addValue("type", obj.getType());
		
		jdbcTemplate.update(sql, parameters);
		return obj;
	}
	
    	
    //CRUD - REMOVE
    
	public void delete(Long id) {
		String sql = "DELETE FROM `Leave` WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		
		jdbcTemplate.update(sql, parameters);
	}

    	
    //CRUD - GET ONE
    	
	public Leave get(Long id) {
	    
		String sql = "select * from `Leave` where `_id` = :id";
		
	    SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
	    
	    return (Leave) jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper(Leave.class));
	}


    	
        	
    //CRUD - GET LIST
    	
	public List<Leave> getList() {
	    
		String sql = "select * from `Leave`";
		
	    SqlParameterSource parameters = new MapSqlParameterSource();
	    return jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper(Leave.class));
	    
	    
	}

    	
    //CRUD - EDIT
    	
	public Leave update(Leave obj, Long id) {

		String sql = "UPDATE `Leave` SET `cancel` = :cancel,`fromDate` = :fromDate,`reason` = :reason,`status` = :status,`toDate` = :toDate,`type` = :type  WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("cancel", obj.getCancel())
			.addValue("fromDate", obj.getFromDate())
			.addValue("reason", obj.getReason())
			.addValue("status", obj.getStatus())
			.addValue("toDate", obj.getToDate())
			.addValue("type", obj.getType());
		jdbcTemplate.update(sql, parameters);
	    return obj;
	}
	
    	
    
    
    
    

    
    /*
     * CUSTOM SERVICES
     * 
     *	These services will be overwritten and implemented in LeaveService.java
     */
    

}
