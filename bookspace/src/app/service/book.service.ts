import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private host = environment.apiUrl;
  getMovieURL = `${this.host}/book/test`;

  constructor(private http: HttpClient) {}

  getAllMovies(): Observable<String> {
  
    return this.http.get<String>(this.getMovieURL);
  }
}
