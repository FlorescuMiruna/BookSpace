import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Book } from '../model/book';

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

  getBookById(id: string): Observable<Book> {
    console.log(this.getBookByIdURL + '/' + id)
    return this.http.get<Book>(this.getBookByIdURL + '/' + id);
  }


  addBookToTBR(book: Book,userId: string): Observable<any> {
    var URL = "";
    URL = this.addBookToTBRURL + '/' + book.id + '/user/' + userId + '/toRead';
    console.log('URL', URL);
    return this.http.post(URL, null);
  }

  removeBookFromTBR(bookId: number, userId: string):  Observable<any>{
    var URL = "";
    URL = this.removeBookFromTBRURL +'/' + bookId + '/user/' + userId + '/toRead';
    console.log('URL', URL);
    return this.http.delete<any>(URL);

  }
}
