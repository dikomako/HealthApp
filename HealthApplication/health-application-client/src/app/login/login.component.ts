import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthService } from '../services/auth.service';
// import { AlertService } from '../services';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  hidePassword = true;
  message: string;
  username: string;
  password: string;
  wrongCredentials = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthService,
    // private alertService: AlertService,
  ) { 
    if (this.authenticationService.currentUserValue) { 
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }
  
  async onSubmit() {

    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;

    try {
      await this.authenticationService.login(this.loginForm.controls.username.value, this.loginForm.controls.password.value);
      
      if( !this.authenticationService.wrongCredentials ) {
        if (this.authenticationService.redirectUrl) {
          this.router.navigate([this.authenticationService.redirectUrl]);
        } else {
          this.router.navigate(['/']);
        }
      }
    }
    catch(e) {
      this.message = 'Cannot log in!'
    }
  }

}