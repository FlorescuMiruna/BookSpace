import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Review } from '../model/review';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private host = environment.apiUrl;
  getAllCommentsByBookIdURL: string;
  addCommentURL: string;
  deleteCommentURL: string;

  updateCommURL: string;


  constructor(private http: HttpClient) {
    this.getAllCommentsByBookIdURL = `${this.host}/book/`;
    this.addCommentURL = `${this.host}/comment/movie/`;
    this.deleteCommentURL = `${this.host}/comment/`;
    this.updateCommURL = `${this.host}/comment/`;


  }

  getAllCommentsByBookId(bookId: string): Observable<Review[]> {

    var URL = this.getAllCommentsByBookIdURL + bookId + '/reviews';
    console.log('testt getAllCommentsByBookId URL', URL);
    return this.http.get<Review[]>(URL);
  }

  addComment(comm : Review, bookId : string, userId: number ): Observable<Review> {
    var URL = this.getAllCommentsByBookIdURL + bookId + '/user/' + userId + '/review';
    return this.http.post<Review>(URL,comm);
  }

  deleteComment(comm: Review,bookId : string, userId: number): Observable<Review> {
    var URL = this.getAllCommentsByBookIdURL + bookId + '/user/' + userId + '/review';
    return this.http.delete<Review>(URL);
  }


  

  updateComm(comm : Review): Observable<Review>{
    return this.http.put<Review>(this.updateCommURL + comm.id, comm);

  }
}
