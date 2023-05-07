import { Component } from '@angular/core';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent {
  isRead: boolean = false;
  averageRating: number = 0;
  isInTBR: boolean = false;
  calculateClasses1() {
    if (this.isRead === false)
      return 'btn btn-outline-success';
    else
      return 'btn btn-success';
  }


  calculateClasses2() {
    if (this.isInTBR === false)
      return 'btn btn-outline-dark';
    else
      return 'btn btn-dark';
  }
  addMovieToTBR() {
    this.isInTBR = !this.isInTBR;

  }
  addBook() {
    this.isRead =  !this.isRead;
  }



}
