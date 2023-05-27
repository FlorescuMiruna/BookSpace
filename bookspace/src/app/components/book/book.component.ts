import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Book } from 'src/app/model/book';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { BookService } from 'src/app/service/book.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent {

  constructor(private bookService: BookService, private authenticationService: AuthenticationService, private router:Router) { }
  
  ngOnInit(): void {
    this.initializeBook();
  }

  isRead: boolean = false;
  averageRating: number = 0;
  isInTBR: boolean = false;
  book: Book = new Book();

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

  initializeBook(){
    var idBook = '';
    var temp = localStorage.getItem('bookId');
    if (temp !== null) {
      var idBook = temp;
    }

    this.bookService.getBookById(idBook).subscribe(res => {

      this.book = res;
      console.log(this.book)

    }, err => {
      console.log("Error while fetching data")
    });
  }


}
