import { Component, inject,QueryList, ViewChildren, ElementRef, ViewChild, EventEmitter, Output } from '@angular/core';
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
import { SearchBookForm } from 'src/app/shared/interfaces/Search-book-form'; 


@Component({
  selector: 'app-search-book-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule,MatInputModule,MatSelectModule,MatButtonModule,RouterLink,RouterOutlet,UserLoginComponent,MatIconModule,MatMenu,MatButtonModule, MatIconModule, MatButtonModule,MatButtonToggleModule,MatIconModule,MatIconButton,BooksInStoresComponent],
  templateUrl: './search-book-form.component.html',
  styleUrl: './search-book-form.component.css'
})
export class SearchBookFormComponent {
  title:string
  @Output() formValue = new EventEmitter<SearchBookForm>();
  
  form = new FormGroup({
  title:new FormControl('', Validators.required),
  searching:new FormControl('others',Validators.required)
  })

  onSubmit(){
    // this.formValue.emit({title:this.form.get('title').value, searching:this.form.get('searching').value})
    const form = this.form.value as SearchBookForm
    this.formValue.emit(form)
  }
  }