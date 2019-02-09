import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { first } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { User } from '../models/user';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../services/authentication.service';

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent implements OnInit, OnDestroy {
    currentUser: User;
    currentUserSubscription: Subscription;
    users: User[] = [];

    constructor(
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private httpClient: HttpClient,
        private route: ActivatedRoute,
    ) {
        this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
            this.currentUser = user;
        });
    }

    async ngOnInit() {
        this.users = await this.userService.getAllUsers();
    }

    ngOnDestroy() {
        // unsubscribe to ensure no memory leaks
        this.currentUserSubscription.unsubscribe();
    }

    async deleteUser(id: number) {
        const userId = parseInt(this.route.snapshot.params.id as string);
        await this.httpClient.delete<null>(`/api/users/${userId}`).toPromise();
        await this.userService.getAllUsers();
    }
}