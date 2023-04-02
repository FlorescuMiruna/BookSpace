import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
@Injectable({providedIn: 'root'})
export class AuthenticationService {
  public host = environment.apiUrl;
  private token: any;
  private loggedInUsername: any;
  // private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient) {}

  public login(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>(`${this.host}/user/login`, user, { observe: 'response' });
  }

  public register(user: User): Observable<User> {
    return this.http.post<User>(`${this.host}/user/register`, user);
  }

  public logOut(): void {
    this.token = null;
    this.loggedInUsername = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    localStorage.removeItem('users');
  }

  public saveToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  public addUserToLocalCache(user: User): void {

    localStorage.setItem('user', JSON.stringify(user));
  }



  public getUserFromLocalCache(): User {
    return JSON.parse(localStorage.getItem('user')|| '{}');
  }

  public loadToken(): void {
    this.token = localStorage.getItem('token');
  }

  public getToken(): string {
    return this.token;
  }

  public isUserLoggedIn(): any {
    this.loadToken();
    // if (this.token != null && this.token !== ''){
    //   if (this.jwtHelper.decodeToken(this.token).sub != null || '') {
    //     if (!this.jwtHelper.isTokenExpired(this.token)) {
    //       this.loggedInUsername = this.jwtHelper.decodeToken(this.token).sub;
    //       return true;
    //     }
    //   }
    // } else {
    //   this.logOut();
    //   return false;
    // }
  }

}