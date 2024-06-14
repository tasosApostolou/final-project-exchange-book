import { Injectable, inject, signal } from '@angular/core';
import {Person, PersonRegister } from '../interfaces/person';
import { environment } from 'src/environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Book } from '../interfaces/book';
import { UserService } from './user.service';
import { User } from '../interfaces/user';
import { storeRegister } from '../interfaces/store';

const API_URL = `${environment.apiURL}/personal`;

@Injectable({
  providedIn: 'root'
})
export class PersonService {
http: HttpClient = inject(HttpClient);
userService = inject(UserService)
personId = () => {
  return this.userService.user().roleEntityId
}
personSignal = signal<Person | null>(null);

  constructor() { }

  registerPerson(person:PersonRegister){
    return this.http.post<{data:JSON}>(`${API_URL}/register`, person);
  }

  getPersonBooks(){
    console.log(`func:${this.userService.user().role}`)
    return this.http.get<Book[]>(`${API_URL}/${this.userService.user().roleEntityId}/books`,{
      headers: {
        Accept:'application/json'
      },
    })
  }

  deleteBookFromLoggedInPerson(bookId:number){
   return this.http.delete<{any:Book}>(`${API_URL}/${this.userService.user().roleEntityId}/books/${bookId}`)
  }
  updatePersonal(person:Person){
    return this.http.put<{any:Person}>(`${API_URL}/update/${
      person.id}`,person)
  }

  getPersonByUserId(userID:number){
    return this.http.get<Person>(`${API_URL}/user/${userID}`,{
      headers: {
        Accept:'application/json'
      },
    })
  }

  deleteUser(){
    return this.http.delete<{user:User}>(`${environment.apiURL}/${this.userService.user().role.toLowerCase()}/${this.userService.user().roleEntityId}`)
  }
  // deleteUser(){
  //   return this.http.delete<{user:User}>(`${environment.apiURL}/user/${this.userService.user().userId}`)
  // }
}