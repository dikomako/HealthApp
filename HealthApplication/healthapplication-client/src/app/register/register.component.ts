import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AlertService } from '../services/alert.service';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';
import { User } from '@/models/user';

@Component({templateUrl: 'register.component.html'})
export class RegisterComponent implements OnInit {
    registerForm: FormGroup;
    loading = false;
    submitted = false;
    model: User;
    newID: number;
    dailyWaterAmount: number;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private alertService: AlertService
    ) { 
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue) { 
            this.router.navigate(['/']);
        }
    }

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            userName: ['', Validators.required],
            password: ['', [Validators.required, Validators.minLength(8)]],
            emailAddress: ['', Validators.required],
            currentWeight: ['', Validators.required],
            goalWeight: ['', Validators.required],
            height: ['', Validators.required],
            age: ['', Validators.required],
            gender: ['', Validators.required],
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }


    async onSubmit() {
        this.submitted = true;
        this.newID = Math.floor(Math.random() * 10000) + 1;

        console.log(this.newID);

        this.waterConsumptionCalculator();

        this.model = new User(  this.newID,
                                this.registerForm.value.userName,
                                this.registerForm.value.emailAddress,
                                this.registerForm.value.currentWeight,
                                this.registerForm.value.goalWeight,
                                this.registerForm.value.height,
                                this.registerForm.value.age,
                                this.registerForm.value.password,
                                this.dailyWaterAmount,
                                0,
                                'ROLE_USER',
                                this.registerForm.value.gender);

        console.log(this.model);
        
        
        

        // stop here if form is invalid
        if (this.registerForm.invalid) {
            this.alertService.error('Something went wrong... :(', false);
            return;
        }

        this.loading = true;
        await this.userService.register(this.model);
    }

    waterConsumptionCalculator() {
        this.dailyWaterAmount = (this.registerForm.value.currentWeight / 2 ) * 120;
        console.log(this.dailyWaterAmount);
    }
}