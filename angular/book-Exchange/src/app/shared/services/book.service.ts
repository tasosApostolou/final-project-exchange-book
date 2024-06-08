import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Book, BookWithPersons, BookWithUsers, InsertBook, InsertStoreBook } from '../interfaces/book';
import { environment } from 'src/environments/environment.development';
import { UserService } from './user.service';
const API_URL = `${environment.apiURL}/book`

@Injectable({
  providedIn: 'root'
})
export class BookService {
http:HttpClient = inject(HttpClient)
userService = inject(UserService)
id = this.userService.id
title:string=''

addBook(book:InsertBook | InsertStoreBook){
  console.log(`serviceID:${this.id},,,userid:${this.userService.user().userId}`)
  return this.http.post<{data:JSON}>(`${API_URL}/${this.userService.user().role.toLowerCase()}/${this.userService.user().roleEntityId}/add`,book)
}// "api/book/personal/{personId}/add" || "api/book/store/{storeId}/add"

getBooksByTitle(title:string){
  this.title=title
  console.log(`title:${this.title}`)
  return this.http.get<BookWithPersons[]>(`${API_URL}/books/${this.title}`,{
    headers:{
      Accept:'application/json'
    }
  })
    }

  getUserBooks(userID:number){
    console.log(`books user id:${userID}`)
    return this.http.get <Book[]>(`${API_URL}/user/${userID}`,{
      headers: {
        Accept:'application/json'
      },
    })
  }

  getBookById(bookId:number){
    return this.http.get<Book>(`${API_URL}/${bookId}`,{
      headers: {
        Accept:'application/json'
      },
    })
  }
}
