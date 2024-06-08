import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MenuItem } from 'src/app/shared/interfaces/menu-item';

@Component({
  selector: 'app-list-group-menu',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './list-group-menu.component.html',
  styleUrl: './list-group-menu.component.css',
})
export class ListGroupMenuComponent {
  menu: MenuItem[] = [

    {text: 'User Registration Example',
      routerLink: 'user-registration',
    },
    // {text:'teacher registration', routerLink:'teacher-registration'},
    // {text:'book registration', routerLink:'book-registration'},
];
}
