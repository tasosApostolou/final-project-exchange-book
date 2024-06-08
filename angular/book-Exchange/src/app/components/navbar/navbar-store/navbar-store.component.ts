import { Component, inject } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import{ MatIcon, MatIconModule } from'@angular/material/icon'
import { Router, RouterLink } from '@angular/router';
import { MatMenu, MatMenuTrigger } from '@angular/material/menu';
import { MatBadge, MatBadgeModule } from '@angular/material/badge';
import { MatButtonToggle, MatButtonToggleGroup, MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatButton, MatIconButton } from '@angular/material/button';
import { Notifica } from 'src/app/shared/interfaces/notification';
import { AddBookComponent } from '../../add-book/add-book.component';
import { User } from 'src/app/shared/interfaces/user';
import { BooksTableComponent } from '../../books-table/books-table.component';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { UpdateComponent } from '../../update/update.component';
import { ManageAccountComponent } from '../../manage-account/manage-account.component';

@Component({
  selector: 'app-navbar-store',
  standalone: true,
  imports: [UpdateComponent,BooksTableComponent,ManageAccountComponent,
    MatIcon,RouterLink,AddBookComponent,MatIconModule,MatMenu,MatMenuTrigger,MatBadgeModule,MatBadge,MatButtonToggle,MatButtonToggleGroup,MatButtonToggleModule,MatIcon,MatIconButton,MatButton,BooksTableComponent,BooksTableComponent],
  templateUrl: './navbar-store.component.html',
  styleUrl: './navbar-store.component.css'
})
export class NavbarStoreComponent {
  router = inject(Router)
  userService = inject(UserService)
  notificationService = inject(NotificationService)
  user = this.userService.user
  notifications:Notifica[] = []

// notificationsNumber:any = ''
notificationsUnseenNumber:any
matBadgeValue(){
  if (this.notificationsUnseenNumber == 0){
    return ''
  }else{
    return this.notificationsUnseenNumber
  }
}
  logout(){
    this.userService.logoutUser();
  }

  ngOnInit(){
    this.userService.getUserNotifications().subscribe(
      (data:[]) =>{
        this.notifications = data
        
        this.notificationsUnseenNumber = this.notifications.filter(notification => !notification.isSeen).length
      
      })
    // this.loadNotifications()
  }
  // loadNotifications(){
  //   this.userService.getUserNotifications().subscribe(
  //     (data:[]) =>{
  //       this.fff = data
  //       this.notificationsNumber = this.fff.filter(not => !not.isSeen).length
      
  //     })
  // }



// Router navigates in a table component with interestUser's books
 notificationClicked(notification:Notifica){
    this.ngOnInit() // maybe notificationType has be changed from INTEREST to MATCH
    console.log(`${notification.interested.id} a-sss-as`)
    let notificationToUpdate = this.notifications.find(noti => noti.id === notification.id) //ngOnInit refreshing notification
    this.UpdateNotificationAsSeen(notificationToUpdate)
    
    localStorage.setItem('interestedUser_id',notification.interested.id.toString())// to find user's books after navigating router in user's bookstable 
    console.log(`${this.notificationsUnseenNumber} eidopoihseis`)
  
    // this.router.navigate(['neo'])
    // this.router.navigate(['books-table']);
    // localStorage.removeItem('user_id')
  }


  UpdateNotificationAsSeen(notificationToUpdate:Notifica){

   
    // let notificationToUpdate = this.fff.find(not => not.id === notification.id)
    console.log(notificationToUpdate.notificationType)
    //     not:Notifica ={
    //   id:notification.id,
    //   interested:notification.interested,
    //   user:notification.user,
    //   book:notification.book,
    //   timestamp:notification.timestamp,
    //   isSeen:true
    // }
    notificationToUpdate.isSeen = true
    console.log(`type:${notificationToUpdate.notificationType}`)
    this.notificationService.updateNotificationStatus(notificationToUpdate as Notifica).subscribe({
      next:(response) => {
        console.log(`${response} data`)
        console.log(`${response} data`)
      },
      error:(response) => {
        console.log('Error in notification update', response)
      }
    })
    }

    // <div class= {{bgNotif(notif)}}></div>
    bgNotif(notif:Notifica):string{
      if (!notif.isSeen){
        return 'bg-light'
      }
      return ''
    }
  
  }


