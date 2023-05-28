import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enums/notification-type';
import { User } from 'src/app/model/user';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { NotificationService } from 'src/app/service/notification.service';
import { environment } from 'src/environments/environment';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  public showLoading: boolean | undefined;
  private subscriptions: Subscription[] = [];
  public host = environment.apiUrl;
  constructor(private router: Router, private authenticationService: AuthenticationService,private http: HttpClient) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserLoggedIn()) {
      this.router.navigateByUrl('/user/management');
    //  this.router.navigateByUrl('login');
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  public onLogin(user: User): void {
    console.log('testt onLogin');
    this.showLoading = true;

 
      this.subscriptions.push(
        this.authenticationService.login(user).subscribe(
      
          (response: User) => {
            console.log('response', response);
            this.showLoading = false;
       
            this.authenticationService.addUserToLocalCache(response);
            // const token = response.headers.get(HeaderType.JWT_TOKEN) as any;
            // const decodedToken = jwt_decode(token);
            this.router.navigateByUrl('/home');
              // Swal.fire({
              //   icon: 'success',
              //   title: `User ${response.username}.`,
              //   showConfirmButton: false,
              //   timer: 1500
              // })
            // this.sendNotification(NotificationType.SUCCESS, `A new account was created for ${response.username}.`);
          },
          (errorResponse: HttpErrorResponse) => {
            console.log('error',errorResponse);
            // this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
            this.showLoading = false;
          }
        )
      );
    
    
 


  }


  public onRegister(user: User): void {
    console.log('testt on register');
    this.showLoading = true;

   /** 
    * Validam adresa de email
    * Conditii:
    * 1.Emailul nu este gol
    * 2.Contine simbolul @ cu cel putin o litera inaintea lui
    * 3.Simbolul @ este urmat de cel putin 2 litere dupa el
    * 4.Se termina cu punct si cel putin 2 litere dupa el
    */
    if(/(.+)@(.+){2,}\.(.+){2,}/.test(user.email) === false){
      Swal.fire({
        icon: 'error',
        title: 'Please enter a valid email address!',
 
      })

      this.showLoading = false;
    }

    else{
      this.subscriptions.push(
        this.authenticationService.register(user).subscribe(
      
          (response: User) => {
            console.log('response', response);
            this.showLoading = false;
       
              Swal.fire({
                icon: 'success',
                title: `A new account was created for ${response.username}.`,
                showConfirmButton: false,
                timer: 1500
              })
            // this.sendNotification(NotificationType.SUCCESS, `A new account was created for ${response.username}.`);
          },
          (errorResponse: HttpErrorResponse) => {
            console.log('error',errorResponse);
            // this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
            this.showLoading = false;
          }
        )
      );
    }
    
 


  }

  // public onLogin(user: User): void {
  //   console.log(user);
  //   this.showLoading = true;

    
    // this.subscriptions.push(
    //   this.authenticationService.login(user.username, user.password).subscribe(
    //     (response: HttpResponse<User>) => {
    //       console.log('response',response);
    //       // const token = response.headers.get(HeaderType.JWT_TOKEN) as any;
    //       // this.authenticationService.saveToken(token);
    //       // if (response.body) // GUARD
    //       //   this.authenticationService.addUserToLocalCache(response.body);
    //       // this.router.navigateByUrl('/movie');
    //       this.showLoading = false;
    //     },
    //     (errorResponse: HttpErrorResponse) => {
    //       console.log(errorResponse);
    //       // this.sendErrorNotification(NotificationType.ERROR, errorResponse.error.message);
    //       this.showLoading = false;
    //     }
    //   )
    // );
  // }
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

function jwt_decode(token: any) {
  throw new Error('Function not implemented.');
}

