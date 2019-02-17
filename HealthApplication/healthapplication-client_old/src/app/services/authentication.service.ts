import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

import { User } from '../models/user';
import { UserService } from './user.service';
import { ParseSourceSpan } from '@angular/compiler';

export const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': ''
    })
  };

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    
  isLoggedIn: boolean = false;
  wrongCredentials = false;
  user: User;
  token: string;
  redirectUrl ='/register';

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(
    private http: HttpClient,
    private userService: UserService,
  ) { 
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  async login(username: string, password: string): Promise<User> {
    console.log(username);
    console.log(password);
    console.log(btoa(`${username}:${password}`));

    for( let user of (await this.userService.getAllUsers()) ) {
      if( user.userName == username && user.password == btoa(password) ) {
        this.wrongCredentials = false;
        try {
          this.token = btoa(`${username}:${password}`);
          httpOptions.headers = httpOptions.headers.set('Authorization', `Basic ${this.token}`);
          this.isLoggedIn = true;
          return this.user;
        }
        catch (e) {
          console.log(e);
          throw e;
        }
      } else {
        this.isLoggedIn = false;
        this.wrongCredentials = true;
      }
    }
  }

  logout() {
    httpOptions.headers = httpOptions.headers.set('Authorization', ``);
    this.isLoggedIn = false;
    this.user = null;
    this.token = null;
  }
}