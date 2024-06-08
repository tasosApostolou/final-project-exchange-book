import { Component, inject } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { Book } from 'src/app/shared/interfaces/book';
import { BookService } from 'src/app/shared/services/book.service';
import { PersonService } from 'src/app/shared/services/person.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-neo',
  standalone: true,
  imports: [MatIcon],
  templateUrl: './neo.component.html',
  styleUrl: './neo.component.css'
})
export class NeoComponent {
  userService = inject(UserService)
  personService = inject(PersonService)
  user = this.userService.user
  bookService = inject(BookService)
  books:Book[]
ngOnInit(): void {
  this.personService.getPersonBooks().subscribe((data:Book[]) => {
    this.books = data
  })

}

deleteBook(bookId:number){
  this.userService.deleteBookFromLoggedInUser(bookId).subscribe({
    next:(response)=> {
      console.log("book deleted",response)
      if(this.books.length===1){
        this.books = null
      } 
      this.ngOnInit()
    },
    error:(response) => {
      console.error('Error in delete book',response)
    }
  })
}
}
