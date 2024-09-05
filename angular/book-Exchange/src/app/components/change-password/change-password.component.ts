import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ChangePasswordForm, User } from 'src/app/shared/interfaces/user';
import { UserService } from 'src/app/shared/services/user.service';
import { DialogRef } from '@angular/cdk/dialog';
import { bootstrapApplication } from '@angular/platform-browser';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule,MatInputModule,MatButtonModule],
  templateUrl: './change-password.component.html',
  styles: [
    `
      :host {
        display: block;
        background: #fff;
        border-radius: 8px;
        padding: 16px;
        width: 500px;
      }
      .a{
        background:lightGreen;
      }
    `,
  ],
})
export class ChangePasswordComponent {
  constructor(public dialogPassword: DialogRef) {}

  userService = inject(UserService);
  userSignal = this.userService.user
  changeStatus:{succes:boolean;message:string} = {succes:false,message:'Not Attempted yet'}
  
  form = new FormGroup({
    oldPassword:new FormControl('', Validators.required),
    newPassword: new FormControl('',[Validators.required, Validators.minLength(4)]),
    confirmPassword:new FormControl('',[Validators.required, Validators.minLength(4)]),
  },this.passwordConfirmsValidator)
  
  passwordConfirmsValidator(form:FormGroup){
    if (form.get('newPassword').value !== form.get('confirmPassword').value){
      form.get('confirmPassword').setErrors({passwordMismatch:true});
      return{ passwordMismatch:true}
    }
    return {}
  }
  
  onSubmit(value:any){
    const x  = this.form.value
    this.changePassword(this.form.get('oldPassword').value)
    console.log(value);
  }
  changePassword(oldPassword:string){
    //check authendication before password changing using credentials
    const changePasswordDTO = this.form.value as ChangePasswordForm
    this.userService.changePassword(changePasswordDTO).subscribe({
      next: (response) =>{
        if (this.passwordConfirmsValidator(this.form)){
          // this.updatePassword(this.form.get('password').value)
          this.changeStatus = {succes:true,message:"password changed succesfully"}
        }else{
          this.changeStatus ={
            succes:false,message:'Not valid password '
          }
          console.log("Confirm password is not access")
        }
      },
      error:(response) => {
        console.log("Unothorized user")
        this.changeStatus.succes=false
        this.changeStatus.message="unauthorized"
      }
    })

  }
  // updatePassword(password:string):void{
  //   const user:User = {
  //   id:this.userSignal().userId,
  //   username:this.userSignal().sub,// sub is username taken from token
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

  TryToChangeAgain(){
  this.form.reset();
  this.changeStatus ={
    succes:false,message:'Not Attempted yet'
  }
  }
}
  
