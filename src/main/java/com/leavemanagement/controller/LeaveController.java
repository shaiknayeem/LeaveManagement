package com.leavemanagement.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.PropertySource;

import com.leavemanagement.controller.base.LeaveBaseController;

@RestController
@PropertySource("classpath:${configfile.path}/LeaveManagement.properties")
@RequestMapping(value="${endpoint.api}", headers = "Accept=application/json")
public class LeaveController extends LeaveBaseController {

	//OVERRIDE HERE YOUR CUSTOM CONTROLLER

}
