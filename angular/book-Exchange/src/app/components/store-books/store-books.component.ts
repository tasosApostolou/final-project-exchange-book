import { Component,inject } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { Book, BookWithPrice, StoreBook } from 'src/app/shared/interfaces/book';
import { BookService } from 'src/app/shared/services/book.service';
import { StoreService } from 'src/app/shared/services/store.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-store-books',
  standalone: true,
  imports: [MatIcon],
  templateUrl: './store-books.component.html',
  styleUrl: './store-books.component.css'
})
export class StoreBooksComponent {
  userService = inject(UserService)
  user = this.userService.user
  bookService = inject(BookService)
  storeService = inject(StoreService)
  books:Book[] = []
  storeBooks:StoreBook[]
  booksWithPrice:BookWithPrice[] = []

  ngOnInit(): void {
    this.userService.getUserBooks().subscribe((data:StoreBook[]) => {
      this.storeBooks = data
      this.getBooks(this.storeBooks)
    })
  }
  getBooks(storeBooks:StoreBook[]){
    let i =0
    console.log(storeBooks[0])
    for (let storeBook of storeBooks){
      // this.storeBooks.push(storeBook)
      this.bookService.getBookById(storeBook.bookId).subscribe((book:Book) => {
        this.books.push(book)
        // const bookWithPrice:BookWithPrice = {
        //   book:book,
        //   price:storeBook.price
        // }
        console.log(storeBook.price)
        this.booksWithPrice.push({
          book:book,
          price:storeBooks[i].price
        })
        i+=1
      })
    }
  }
  
  deleteBook(bookId:number){
    this.userService.deleteBookFromLoggedInUser(bookId).subscribe({
      next:(response)=> {
        console.log("book deleted",response)
        if(this.books.length===1){
          this.books = null
        } 
      //Not completed as expected
        // this.ngOnInit()
        // this.getBooks(this.storeBooks)
      },
      error:(response) => {
        console.error('Error in delete book',response)
      }
    })
  }
  }
