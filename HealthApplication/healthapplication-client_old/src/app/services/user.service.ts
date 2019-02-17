import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../models/user';

@Injectable({ providedIn: 'root' })
export class UserService {
    users: User[] = [];

    constructor(private httpClient: HttpClient) { }

    async getAllUsers(): Promise<User[]> {
        await this.httpClient
                .get<User[]>('/api/users')
                .toPromise()
                .then(data =>
                this.users = data);

        console.log(this.users);

        return this.users;
    }

    getSize(): number{
        return this.users.length;
      }

    getById(id: number) {
        return this.httpClient.get(`/api/users/${id}`);
    }

    async register(user: User) {
        await this.httpClient.post<User>(`/api/users`, user).toPromise();
    }

    update(user: User) {
        return this.httpClient.put(`/api/users/${user.userID}`, user);
    }

    delete(id: number) {
        return this.httpClient.delete(`/api/users/${id}`);
    }
}