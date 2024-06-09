import { Component, Input, inject } from '@angular/core';
import { SearchBookFormComponent } from '../search-book-form/search-book-form.component'; 
import { BooksWithPersonsComponent } from '../books-with-persons/books-with-persons.component'; 
import { Book, BookWithPersons, BookWithUsers } from 'src/app/shared/interfaces/book';
import { RouterLink, RouterOutlet } from '@angular/router';
import { BooksInStoresComponent } from '../books-in-stores/books-in-stores.component';
import { SearchBookForm } from 'src/app/shared/interfaces/Search-book-form';
import { BookService } from 'src/app/shared/services/book.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { UserService } from 'src/app/shared/services/user.service';
import { StoreBooksComponent } from '../store-books/store-books.component';

@Component({
  selector: 'app-search-book-manager',
  standalone: true,
  imports: [RouterLink,RouterOutlet,SearchBookFormComponent,BooksWithPersonsComponent,BooksInStoresComponent],
  templateUrl: './search-book-manager.component.html',
  styleUrl: './search-book-manager.component.css'
})
export class SearchBookManagerComponent {
  choice = ''
  title = ''
  booksWithPersons:BookWithPersons[] = []

  bookService = inject(BookService)
notificationService = inject(NotificationService)
userservce = inject(UserService)
  ngOnInit(): void {

    this.loadBooksOfPersons()
  }

  formResults(formValue:SearchBookForm){
    this.choice = formValue.searching
    this.title = formValue.title
    if (this.choice==="others"){
      this.loadBooksOfPersons()
}
  }

  loadBooksOfPersons(){
    let f = this.booksWithPersons
  this.bookService.getBooksByTitle(this.title).subscribe( {
    next:(response) => {
      this.booksWithPersons = response
      console.log(this.userservce.user().role)    

    for(let i in response.length as any){
      const book:Book ={
        id:response[i].id,
        title:response[i].title,
      author:response[i].author
      }
      let bkps:BookWithPersons={
        id:response[i].id,
        title:response[i].title,
        author:response[i].author,
        persons:response[i].persons
      }
      console.log(bkps)
  }
    },
    error: (response) => {
      console.log('Error in response',response);
      
    }
  })
  }
}
