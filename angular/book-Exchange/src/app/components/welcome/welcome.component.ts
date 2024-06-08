import { Component, inject } from '@angular/core';
import { MatButton, MatButtonModule, MatIconButton } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenu, MatMenuContent, MatMenuModule } from '@angular/material/menu';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NgModule } from '@angular/core';
import { ɵBrowserAnimationBuilder } from '@angular/animations';
import { UserLoginComponent } from '../user-login/user-login.component';
import { Route, Router } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import {MatListModule, MatNavList} from '@angular/material/list'; 
import {MatBadgeModule} from '@angular/material/badge';
import { MatButtonToggle, MatButtonToggleChange, MatButtonToggleGroup, MatButtonToggleModule } from '@angular/material/button-toggle';




@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [
  MatButtonModule,MatIconModule,MatIconButton,MatButtonModule,MatButtonModule,MatMenuModule,MatToolbarModule,MatButton,MatMenuContent,MatSidenavModule,MatNavList,MatBadgeModule,MatToolbarModule,MatButtonToggle,MatButtonToggleGroup,MatButtonToggleModule

    
  ],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.css',
})
export class WelcomeComponent {
  router:Router = inject(Router)

  navigate(path: string): void {
    this.router.navigate([path]);
  }

  dropdownOpen = false;

  toggleDropdown(): void {
    this.dropdownOpen = !this.dropdownOpen;
  }
  notifications = [
    { title: 'New Message', content: 'You have a new message from Clara.' },
    { title: 'Meeting Reminder', content: 'Meeting at 3 PM today.' }
  ];




  hidden = false;

  toggleBadgeVisibility() {
    this.hidden = !this.hidden;
  }

}