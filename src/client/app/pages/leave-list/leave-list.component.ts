// Import Libraries
import { Observable } from 'rxjs/Rx';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { ModalRemoveComponent } from '../../components/modal-remove.component';


// Import Services
import { LeaveService } from '../../services/leave.service';

// Import Models
import { Leave } from '../../domain/leave-management_db/leave';

import { User } from '../../domain/leave-management_db/user';

// START - USED SERVICES
/*
 *	LeaveService.delete
 *		PARAMS: 
 *					ObjectId id - Id
 *		
 *
 *	LeaveService.list
 *		PARAMS: 
 *		
 *
 */
// END - USED SERVICES

// START - REQUIRED RESOURCES
/*
 * LeaveService  
 */
// END - REQUIRED RESOURCES

@Component({
    selector: "leave-list",
    templateUrl: './leave-list.component.html',
    styleUrls: ['./leave-list.component.css']
})
export class LeaveListComponent implements OnInit {
    
    // Attributes
    list: Leave[];
    search: any = {};
    idSelected: string;
    
    // Constructor
    constructor(
        private leaveService: LeaveService, 
        public dialog: MatDialog) {}

    // Functions
    ngOnInit(): void {
        this.leaveService.list().subscribe(list => this.list = list);
    }

    openModal(id: string): void {
        let dialogRef = this.dialog.open(ModalRemoveComponent, {
            width: '250px',
            data: () => {
                // Execute on confirm
                this.leaveService.remove(id).subscribe(() => {
                    dialogRef.close();
                });
                this.list = this.list.filter(item => item._id != id);
            }
        });
    }

}