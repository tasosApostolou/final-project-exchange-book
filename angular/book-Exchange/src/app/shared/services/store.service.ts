import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { Book, StoreBook } from '../interfaces/book';
import { Person } from '../interfaces/person';
import { Store, storeRegister } from '../interfaces/store';
import { User } from '../interfaces/user';
import { UserService } from './user.service';
const API_URL = `${environment.apiURL}/store`;

@Injectable({
  providedIn: 'root'
})
export class StoreService {
  http: HttpClient = inject(HttpClient);
  userService = inject(UserService)
  personId = () => {
    return this.userService.user().roleEntityId
  }
  personSignal = signal<Person | null>(null);
  
    constructor() { }
  
    // registerPerson(person:PersonRegister){
    //   return this.http.post<{data:JSON}>(`${API_URL}/register`, person);
    // }
    registerStore(store:storeRegister){
      return this.http.post<{data:JSON}>(`${API_URL}/register`, store);
    }
  
    getStoreBooks(){
      // console.log(`func:${storeId}`)
      return this.http.get<StoreBook[]>(`${API_URL}/${this.userService.user().roleEntityId}/books`,{
        headers: {
          Accept:'application/json'
        },
      })
    }

    getStoreBooksByTitle(title:string){
      return this.http.get<StoreBook[]>(`${API_URL}/books?title=${title}`)
    }
  
    deleteBookFromLoggedInStore(bookId:number){
     return this.http.delete<{any:Book}>(`${API_URL}/${this.userService.user().roleEntityId}/books/${bookId}`)
    }
    updateStore(store:Store){
      return this.http.put<{any:Store}>(`${API_URL}/update/${
        store.id}`,store)
    }
  
    getStoreByUserId(userID:number){
      return this.http.get<Person>(`${API_URL}/user/${userID}`)
    }
  
    deleteUser(){
      return this.http.delete<{user:User}>(`${environment.apiURL}/${this.userService.user().role.toLowerCase()}/${this.userService.user().roleEntityId}`)
    }

  }