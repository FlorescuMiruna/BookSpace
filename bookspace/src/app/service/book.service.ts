import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Book } from '../model/book';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private host = environment.apiUrl;
  getBooksURL : string;
  getBookByIdURL : string;
  addBookToTBRURL: string;
  removeBookFromTBRURL: string;



  constructor(private http: HttpClient) {
    this.getBooksURL = `${this.host}/book`;
    this.getBookByIdURL = `${this.host}/book`;
    this.addBookToTBRURL = `${this.host}/book`;
    this.removeBookFromTBRURL = `${this.host}/book`;
  }

  getAllBooks(): Observable<Book[]> {
    console.log(this.getBooksURL)
    return this.http.get<Book[]>(this.getBooksURL);
  }

  getTBRBooks(userId: string): Observable<Book[]> {

    const URL = this.getBooksURL + '/user/' + userId + '/toRead';
    console.log(URL);
    return this.http.get<Book[]>(URL);
  }

  getReadBooks(userId: string): Observable<Book[]> {
    console.log('userId **', userId)
    const URL = this.getBooksURL + '/user/' + userId + '/read';
    console.log(URL);
    return this.http.get<Book[]>(URL);
  }

  getBookById(id: string): Observable<Book> {
    console.log('getBookById URL:', this.getBookByIdURL + '/' + id)
    return this.http.get<Book>(this.getBookByIdURL + '/' + id);
  }


  // addBookToTBR(book: Book,userId: number): Observable<any> {
  //   var URL = "";
  //   URL = this.addBookToTBRURL + '/' + book.id + '/user/' + userId + '/toRead';
  //   console.log('URL', URL);
  //   return this.http.post(URL, null);
  // }

  addBookToTBR(book: Book, userId: number): Observable<any> {
    const URL = this.addBookToTBRURL + '/' + book.id + '/user/' + userId + '/toRead';
    return this.http.post(URL, null, { responseType: 'text' }).pipe(
      map(response => {
        console.log(response);
        return response;
      }),
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );
  }

  addBook(book: Book, userId: number): Observable<any> {
    const URL = this.addBookToTBRURL + '/' + book.id + '/user/' + userId + '/read';
    return this.http.post(URL, null, { responseType: 'text' }).pipe(
      map(response => {
        console.log(response);
        return response;
      }),
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );
  }



  removeBookFromTBR(bookId: number, userId: number):  Observable<any>{
    const URL = this.addBookToTBRURL + '/' + bookId+ '/user/' + userId + '/toRead';
    return this.http.delete(URL, { responseType: 'text' }).pipe(
      map(response => {
        console.log(response);
        return response;
      }),
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );

  }

  removeBook(bookId: number, userId: number):  Observable<any>{
    const URL = this.addBookToTBRURL + '/' + bookId+ '/user/' + userId + '/read';
    return this.http.delete(URL, { responseType: 'text' }).pipe(
      map(response => {
        console.log(response);
        return response;
      }),
      catchError(error => {
        console.log(error);
        return throwError(error);
      })
    );

  }
}
