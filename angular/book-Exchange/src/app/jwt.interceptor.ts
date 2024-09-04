import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const authToken = localStorage.getItem('access_token');
    // console.log('authToken', authToken);
    if (!authToken) {
        return next.handle(req);
    }
  
    const authRequest = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + authToken),
    });
    return next.handle(authRequest);
  }
}