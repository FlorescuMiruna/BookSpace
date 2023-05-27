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

  constructor(private http: HttpClient) {
    this.getBooksURL = `${this.host}/book`;
    this.getBookByIdURL = `${this.host}/book`;
  }

  getAllBooks(): Observable<Book[]> {
    console.log(this.getBooksURL)
    return this.http.get<Book[]>(this.getBooksURL);
  }

  getBookById(id: string): Observable<Book> {
    console.log(this.getBookByIdURL + '/' + id)
    return this.http.get<Book>(this.getBookByIdURL + '/' + id);
  }
}
