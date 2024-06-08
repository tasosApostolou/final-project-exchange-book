import { Component, Inject, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { User } from 'src/app/shared/interfaces/user';
import { UserService } from 'src/app/shared/services/user.service';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatMenu } from '@angular/material/menu';
import {
  Dialog,
  DialogRef,
  DIALOG_DATA,
  DialogModule,
} from '@angular/cdk/dialog';
import { PersonService } from 'src/app/shared/services/person.service';
// import { ChangePasswordComponent } from '../change-password/change-password.component';
import { Person } from 'src/app/shared/interfaces/person';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { ChangePasswordComponent } from '../change-password/change-password.component';
@Component({
  selector: 'app-manage-account',
  standalone: true,
  imports: [ ReactiveFormsModule, MatFormFieldModule,MatInputModule,MatSelectModule,MatButtonModule,RouterLink,RouterOutlet,MatIconModule,MatMenu,MatButtonModule],
  templateUrl: './manage-account.component.html',
  styleUrl: './manage-account.component.css'
})
export class ManageAccountComponent {
  constructor(public dialog: Dialog, dia:Dialog) {}
  userService = inject(UserService);
  userSignal = this.userService.user
  personService = inject(PersonService);
  router:Router = 
  inject(Router)
  registrationStatus:{succes:boolean;message:string} = {succes:false,message:'Not Attempted yet'}
  
  // form = new FormGroup({
  //   oldPassword:new FormControl('', Validators.required),
  //   password: new FormControl('',[Validators.required, Validators.minLength(4)]),
  //   confirmPassword:new FormControl('',[Validators.required, Validators.minLength(4)]),
  // },this.passwordConfirmsValidator)
  
  
  
  // passwordConfirmsValidator(form:FormGroup){
  //   if (form.get('password').value !== form.get('confirmPassword').value){
  //     form.get('confirmPassword').setErrors({passwordMismatch:true});
  //     return{ passwordMismatch:true}
  //   }
  //   return {}
  // }
  
  // onSubmit(value:any){
  //   const x  = this.form.value
  //   this.changePassword(this.form.get('oldPassword').value)
  //   console.log(value);
  // }
  // changePassword(oldPassword:string){
  //   //check authendication first
  //   this.userService.loginUser({username:this.userSignal().sub, password:oldPassword}).subscribe({
  //     next: (response) =>{
  //       console.log(response.jwt)
  //       if (this.passwordConfirmsValidator(this.form)){
  //         this.updatePassword(this.form.get('password').value)
  //         this.registrationStatus = {succes:true,message:"User register succesfully"}
  //       }else{
  //         console.log("Confirm password is not access")
  //       }
  //     },
  //     error:(response) => {
  //       console.log("Unothorized user")
  //       this.registrationStatus.succes=false
  //     }
  //   })

  // }
  // updatePassword(password:string):void{
  //   const user:User = {
  //   id:this.userSignal().userId,username:this.userSignal().sub,
  //   password:password,
  //   role:this.userSignal().role
  // }
  //   this.userService.updateUser(user).subscribe({
  //     next:(response) => {
  //       console.log("password changed",response)
  //     },
  //     error:(response) => {
  //       console.log(response)
  //     }
      
  //   })
  // }

  // registerUser(){
  // this.form.reset();
  // this.registrationStatus ={
  //   succes:false,message:'Not Attempted yet'
  // }
  // }
  
  passwordChange(){
    // this.router.navigate(['change-password']);
    this.dialog.open(ChangePasswordComponent)
  }
  
  deleteUserDialog(){
    // this.personService.deleteUser().subscribe({
    //   next:(response) =>{
    //     console.log("user deleted",response)
    //     this.userService.logoutUser()
    //   },
    //   error:(response) => {
    //     console.log(response)
    //   }
    // })
    this.dialog.open(DeleteDialogComponent);

  }
  }
  // @Component({
  //   imports: [ChangePasswordComponent],
  //   standalone: true,
  //   template: `
  //     <!-- <app-change-password></app-change-password> -->
  //     <!-- <button class="btn btn-primary btn-sm" (click)="deleteUser">
  //       ok
  //     </button> -->
  //     <p>Are you sure that you want to delete your account?</p>
  //     <button class="ms-5 btn btn-primary" (click)="userDelete()">delete  Account</button>
  //   `,
  //   styles: [
  //     `
  //       :host {
  //         display: block;
  //         background: #fff;
  //         border-radius: 8px;
  //         padding: 16px;
  //         max-width: 500px;
  //       }
  //     `,
  //   ],
  // })
  // class DeleteDialogComponent {
  //   constructor(public dialogRef: DialogRef, @Inject(DIALOG_DATA)
  //   public person: Person) {}
  //   personService = inject(PersonService)
  //   userService = inject(UserService)
  //   userDelete(){
  //     this.personService.deleteUser().subscribe({
  //           next:(response) =>{
  //             console.log("user deleted",response)
  //             this.userService.logoutUser()
  //           },
  //           error:(response) => {
  //             console.log(response)
  //           }
  //         })
      
  //     this.dialogRef.close()
  //   }
  // }
  