import { Component, Input } from '@angular/core';
import { StoreBook } from 'src/app/shared/interfaces/book';

@Component({
  selector: 'app-books-in-stores',
  standalone: true,
  imports: [],
  templateUrl: './books-in-stores.component.html',
  styleUrl: './books-in-stores.component.css'
})
export class BooksInStoresComponent {
  @Input()  storeBooks:StoreBook[]
  
}
