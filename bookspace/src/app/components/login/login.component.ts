import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enums/notification-type';
import { User } from 'src/app/model/user';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { NotificationService } from 'src/app/service/notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  public showLoading: boolean | undefined;
  private subscriptions: Subscription[] = [];

  constructor(private router: Router, private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserLoggedIn()) {
      this.router.navigateByUrl('/user/management');
    //  this.router.navigateByUrl('login');
    } else {
      this.router.navigateByUrl('/login');
    }
  }
  public onLogin(user: User): void {
    this.showLoading = true;
    this.subscriptions.push(
      // this.authenticationService.login(user).subscribe(
      //   (response: HttpResponse<User>) => {
      //     const token = response.headers.get(HeaderType.JWT_TOKEN) as any;
      //     this.authenticationService.saveToken(token);
      //     if (response.body) // GUARD
      //       this.authenticationService.addUserToLocalCache(response.body);
      //     this.router.navigateByUrl('/movie');
      //     this.showLoading = false;
      //   },
      //   (errorResponse: HttpErrorResponse) => {
      //     this.sendErrorNotification(NotificationType.ERROR, errorResponse.error.message);
      //     this.showLoading = false;
      //   }
      // )
    );
  }
  // private sendErrorNotification(notificationType: NotificationType, message: string): void {
  //   if (message) {
  //     this.notificationService.notify(notificationType, message);
  //   } else {
  //     this.notificationService.notify(notificationType, 'An error occurred. Please try again.');
  //   }
  // }



  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}

