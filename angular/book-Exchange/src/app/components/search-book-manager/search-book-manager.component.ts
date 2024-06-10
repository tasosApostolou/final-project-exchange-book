import { Component, Input, inject } from '@angular/core';
import { SearchBookFormComponent } from '../search-book-form/search-book-form.component'; 
import { BooksWithPersonsComponent } from '../books-with-persons/books-with-persons.component'; 
import { Book, BookWithPersons, BookWithUsers, StoreBook } from 'src/app/shared/interfaces/book';
import { RouterLink, RouterOutlet } from '@angular/router';
import { BooksInStoresComponent } from '../books-in-stores/books-in-stores.component';
import { SearchBookForm } from 'src/app/shared/interfaces/Search-book-form';
import { BookService } from 'src/app/shared/services/book.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { UserService } from 'src/app/shared/services/user.service';
import { StoreBooksComponent } from '../store-books/store-books.component';
import { StoreService } from 'src/app/shared/services/store.service';

@Component({
  selector: 'app-search-book-manager',
  standalone: true,
  imports: [RouterLink,RouterOutlet,SearchBookFormComponent,BooksWithPersonsComponent,BooksInStoresComponent],
  templateUrl: './search-book-manager.component.html',
  styleUrl: './search-book-manager.component.css'
})
export class SearchBookManagerComponent {
  bookService = inject(BookService)
  storeService = inject(StoreService)
  choice = ''
  title = ''
  booksWithPersons:BookWithPersons[] = []
  storeBooks:StoreBook[] = []

  ngOnInit(): void {
    this.loadBooksOfPersons()
  }

  formResults(formValue:SearchBookForm){
    this.choice = formValue.searching
    this.title = formValue.title
    if (this.choice==="others"){
      this.loadBooksOfPersons()
}
    if (this.choice==="stores"){
      this.loadBooksOfPersons()
    }


  }

  loadBooksOfPersons(){
  this.bookService.getBooksByTitle(this.title).subscribe( {
    next:(response) => {
      this.booksWithPersons = response
    },
    error: (response) => {
      console.log('Error in response',response);   
    }
  })
  }

  loadBooksOfStores(){
    this.storeService.getStoreBooksByTitle(this.title).subscribe({
      next: (response) => {
        this.storeBooks=response
      },
      error:(response) =>{

      }
    })
  }
}
