import { Routes } from '@angular/router'
import { WelcomeComponent } from './components/welcome/welcome.component';


import { UserRegistrationComponent } from './components/user-registration/user-registration.component';
import {authPersonalGuard} from './shared/guards/authPersonal.guard';
import { UserLoginComponent } from './components/user-login/user-login.component';
import { AddBookComponent } from './components/add-book/add-book.component';
import { BooksTableComponent } from './components/books-table/books-table.component';
import { UpdateComponent } from './components/update/update.component';
import { ManageAccountComponent } from './components/manage-account/manage-account.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { StoreRegistrationComponent } from './components/store-registration/store-registration.component';
import { AddBookStoreComponent } from './components/add-book-store/add-book-store.component';
import { StoreBooksComponent } from './components/store-books/store-books.component';
import { authStoreGuard } from './shared/guards/authStore.guard';
import { authCommonGuard } from './shared/guards/authCommon.guarrd';
// import { MyBooksComponent } from './components/my-books/my-books.component';
import { BooksInStoresComponent } from './components/books-in-stores/books-in-stores.component';
import { BooksWithPersonsComponent } from './components/books-with-persons/books-with-persons.component';
import { MyBooksComponent } from './components/my-books/my-books.component';
import { SearchBookManagerComponent } from './components/search-book-manager/search-book-manager.component';
import { SearchBookFormComponent } from './components/search-book-form/search-book-form.component';



export const routes: Routes = [
  { path: '', component: WelcomeComponent },
  {path:'user-registration', component: UserRegistrationComponent},
  {path:'store-registration', component: StoreRegistrationComponent},

  { path: 'login', component: UserLoginComponent },
  {path:'add-book-store', component: AddBookStoreComponent,canActivate:[authStoreGuard]},
  {path:'store-books', component: StoreBooksComponent,canActivate:[authStoreGuard]},
  {path: 'add-book-person', component: AddBookComponent,canActivate:[authPersonalGuard] },
  {path:'books-table', component: BooksTableComponent,canActivate:[authPersonalGuard]},
  {path:'my-books', component: MyBooksComponent,canActivate:[authPersonalGuard]},
  { path: `books-in-stores`, component: BooksInStoresComponent,canActivate:[authPersonalGuard]},
  { path: `books-with-persons`, component:BooksWithPersonsComponent,canActivate:[authPersonalGuard]},
  {path:'search-book-form', component: SearchBookFormComponent,canActivate:[authPersonalGuard]},
  {path:'search-book-manager', component: SearchBookManagerComponent,canActivate:[authPersonalGuard]},
  {path: "books-table", component:BooksTableComponent,canActivate:[authPersonalGuard]},
  {path:'update',component:UpdateComponent,canActivate:[authCommonGuard]},
  {path:"manage-account", component:ManageAccountComponent,canActivate:[authCommonGuard],},
  {path: "change-password", component:ChangePasswordComponent,canActivate:[authCommonGuard]},
];
