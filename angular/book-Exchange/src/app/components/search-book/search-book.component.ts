import { Component, inject,QueryList, ViewChildren, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, NgModel, NgModelGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormField, MatFormFieldControl, MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Title } from '@angular/platform-browser';
import { Book, BookWithPersons, BookWithUsers } from 'src/app/shared/interfaces/book';
import { User } from 'src/app/shared/interfaces/user';
import { BookService } from 'src/app/shared/services/book.service';
import { MatButtonModule, MatIconButton } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

import { UserService } from 'src/app/shared/services/user.service';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { UserLoginComponent } from '../user-login/user-login.component';
import { MatIconModule } from '@angular/material/icon';
import { MatMenu } from '@angular/material/menu';
import { MatToolbar, MatToolbarModule, MatToolbarRow } from '@angular/material/toolbar';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { Person } from 'src/app/shared/interfaces/person';
import { BooksInStoresComponent } from '../books-in-stores/books-in-stores.component';


@Component({
  selector: 'app-search-book',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule,MatInputModule,MatSelectModule,MatButtonModule,RouterLink,RouterOutlet,UserLoginComponent,MatIconModule,MatMenu, MatToolbar, MatToolbarRow,MatToolbarModule,MatButtonModule, MatIconModule, MatButtonModule,MatButtonToggleModule,MatIconModule,MatIconButton,BooksInStoresComponent],
  templateUrl: './search-book.component.html',
  styleUrl: './search-book.component.css'
})
export class SearchBookComponent {
  // @ViewChildren('checkbox') checkbox:QueryList<ElementRef<HTMLInputElement>>;
  @ViewChild('iconRef', { static: true }) iconRef: ElementRef;
// searchSelect:string
bookService = inject(BookService)
notificationService = inject(NotificationService)
userservce = inject(UserService)
books:Book[]=[]
users:User[]=[]
persons:Person[] = []
booksWithUsers:BookWithUsers[]
booksWithPersons:BookWithPersons[] // a list with books and for each book a list with persons which have this book
title:string

constructor(){
  this.booksWithUsers
  console.log(this.booksWithUsers)
}
form = new FormGroup({
title:new FormControl('', Validators.required),
searching:new FormControl('others',Validators.required)

})


ngOnInit(): void {
  if(this.title){
  this.onSubmit()
  }
}

onSubmit(){
  // if(this.form.get('searching').value==='others')
  // this.searchSelect='others'

  // if(this.form.get('searching').value==='stores')
  // this.searchSelect='stores'
  let t = this.form.get('title').value
  let f = this.booksWithPersons
  this.bookService.getBooksByTitle(t).subscribe( {
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
interest(bkps:BookWithPersons,person:Person,event:Event){

this.iconType(event)
const notification = {
  isSeen:false,
  user:{
    id:person.userId,
  },
  book:{
    id:bkps.id
  }
}

this.notificationService.createNotification(notification as any as JSON ).subscribe({
  next:(response) => {
    console.log(`${response} data`)
  },
  error:(response) => {
    console.log('Error in notification creation', response)
  }
})
}


iconType(event:Event){
  let matIcon: HTMLElement = event.target as HTMLElement;
  if(matIcon.textContent ==="thumb_down"){
    matIcon.innerText = "thumb_up"
  }
  else{
    matIcon.innerText = "thumb_down"
  }
}
}