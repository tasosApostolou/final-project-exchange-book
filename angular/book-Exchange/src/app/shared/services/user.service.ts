import { HttpClient } from '@angular/common/http';
import { Injectable, effect, inject, signal } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { Credentials, LoggedInUser, User } from '../interfaces/user';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { Book, StoreBook } from '../interfaces/book';

const API_USER = `${environment.apiURL}/user`;

@Injectable({
  providedIn: 'root',
})
export class UserService {

http: HttpClient = inject(HttpClient);
router: Router = inject(Router);
user = signal<LoggedInUser | null>(null);
id:number;

  constructor() {
    // const access_token = localStorage.getItem('access_token');
    // if (access_token) {
    //   const decodedTokenSubject = JSON.parse(access_token) as unknown as User;
    //   this.id = decodedTokenSubject.id
    //   this.user.set({
    //     fullname: decodedTokenSubject.username,
    //     role: decodedTokenSubject.role,
    //   });
    // }

    effect(() => {
      if (this.user()) {
        console.log('USer loggedin', this.user().sub);
        this.id=this.user().userId
      } else {
        console.log('No user Logged In');
      }
    });
  }

  loginUser(credentials: Credentials) {
   return this.http.post<{jwt:string} >(`${environment.apiURL}/login`,credentials);
  }

  logoutUser(){
    this.user.set(null)
    // this.user().id = 0
localStorage.removeItem('access_token');
this.router.navigate(['login']);
  }
  // api/{role}/roleEntityId/books
  getUserBooks(){
    return this.http.get<Book[] | StoreBook[]>(`${environment.apiURL}/${this.user().role.toLowerCase()}/${this.user().roleEntityId}/books`,{
      headers: {
        Accept:'application/json'
      },
    })
  
  }

  deleteBookFromLoggedInUser(bookId:number){
    return this.http.delete<{any:User}>(`${environment.apiURL}/${this.user().role.toLowerCase()}/${this.user().roleEntityId}/books/${bookId}`)
  
  }
  
  getUserNotifications(){//getNotificationsByUserID
  return this.http.get<Notification[]>(`${API_USER}/notification/${this.id}`)
}

  updateUser(user:User){
    return this.http.put<{user:User}>(`${API_USER}/update/${this.user().userId}`,user)
  }

  deleteUser(){
    return this.http.delete<{user:User}>(`${API_USER}/${this.user().roleEntityId}`)
  }
}