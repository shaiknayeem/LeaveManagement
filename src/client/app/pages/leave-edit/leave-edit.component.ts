// Import Libraries
import { ActivatedRoute, Params } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

// Import Services
import { LeaveService } from '../../services/leave.service';
import { UserService } from '../../services/user.service';

// Import Models
import { Leave } from '../../domain/leave-management_db/leave';

import { User } from '../../domain/leave-management_db/user';

// START - USED SERVICES
/*
 *	LeaveService.create
 *		PARAMS: 
 *		
 *
 *	UserService.findByleave
 *		PARAMS: 
 *					Objectid key - Id della risorsa leave da cercare
 *		
 *
 *	LeaveService.get
 *		PARAMS: 
 *					ObjectId id - Id 
 *		
 *
 *	LeaveService.update
 *		PARAMS: 
 *					ObjectId id - Id
 *		
 *
 */
// END - USED SERVICES

// START - REQUIRED RESOURCES
/*
 * LeaveService  
 * UserService  
 */
// END - REQUIRED RESOURCES

/**
 * Edit component for LeaveEdit
 */
@Component({
    selector: 'leave-edit',
    templateUrl : './leave-edit.component.html',
    styleUrls: ['./leave-edit.component.css']
})
export class LeaveEditComponent implements OnInit {

    item: Leave;
    listLeave: Leave[];
	externalUser: User[];
    model: Leave;
    
    constructor(
        private leaveService: LeaveService,
        private userService: UserService,
        private route: ActivatedRoute,
        private location: Location) {
        // Init item
        this.item = new Leave();
	   this.externalUser = [];
    }

    ngOnInit(): void {
            this.route.params.subscribe(param => {
                let id: string = param['id'];
                if (id !== 'new') {
                    // Get item from server 
                    this.leaveService.get(id).subscribe(item => this.item = item);
                    
                    
                    this.userService.findByLeave(id).subscribe(list => this.externalUser = list);
                    
                }
            });
    }

    /**
     * Save Item
     */
    save (formValid:boolean, item: Leave): void{
        if (formValid) {
            if(item._id){
                this.leaveService.update(item).subscribe(data => this.goBack());
            } else {
                this.leaveService.create(item).subscribe(data => this.goBack());
            }  
        }
    }

    /**
     * Go Back
     */
    goBack(): void {
        this.location.back();
    }
    

}