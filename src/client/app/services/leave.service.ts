// DEPENDENCIES
import { Observable } from 'rxjs/Rx';
import { Http, RequestOptions, Headers, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

// SECURITY
import { AuthenticationService } from '../security/authentication.service';

// CONFIG
import { config } from "../../config/properties";

// MODEL
import { LeaveBaseService } from "./base/leave.base.service";
import { Leave } from '../domain/leave-management_db/leave';

/**
 * YOU CAN OVERRIDE HERE LeaveBaseService
 */

@Injectable()
export class LeaveService extends LeaveBaseService {
    
    // CONSTRUCTOR
    constructor(http: Http, authenticationService: AuthenticationService) {
            super(http, authenticationService);
    }
    
}