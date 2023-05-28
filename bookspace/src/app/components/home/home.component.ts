import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Book } from 'src/app/model/book';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { BookService } from 'src/app/service/book.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  books: Book[] = [];

  constructor(private bookService: BookService, private authenticationService: AuthenticationService, private router:Router) { }
  ngOnInit(): void {
    this.getAllBooks();
  }

  goToBook(book: Book) {

    this.router.navigate(['/book'])
    localStorage.setItem('bookId', book.id.toString());
    console.log("localStorage.getItem('bookId')",localStorage.getItem('bookId'));
    console.log('testt', localStorage.getItem('bookId'))

  }

  getAllBooks(){
    this.bookService.getAllBooks().subscribe(res => {

      this.books = res;
      // this.mostPopularMovies = this.mostPopularMovies.slice(0,5); // de sters
      console.log("all books:", this.books)

    }, err => {
      console.log("Error while fetching data")
    });
  }

}
