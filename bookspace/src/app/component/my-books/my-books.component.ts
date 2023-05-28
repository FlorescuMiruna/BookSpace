import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { Book } from 'src/app/model/book';
import { User } from 'src/app/model/user';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { BookService } from 'src/app/service/book.service';

@Component({
  selector: 'app-my-books',
  templateUrl: './my-books.component.html',
  styleUrls: ['./my-books.component.css']
})
export class MyBooksComponent implements OnInit {
  books: Book[] = [];
  movies_watch_list: Book[] = [];
  favorites: Book[] = [];
  time: [number,number] = [0,0];
  favorites_ids : String[] = [];
  user: User = this.authenticationService.getUserFromLocalCache();
  pageAll: Boolean = true;
  pageFavorites: Boolean = true;
  pageWatched: Boolean = true;
  pageWatchList: Boolean = true;
  constructor(private bookService: BookService, private authenticationService: AuthenticationService, private router:Router) { }

  ngOnInit(): void {
    this.initializeReadBooks();
    this.checkPage();
  }


  checkPage(){

    var page = localStorage.getItem("my-movies") || "";
    this.pageWatchList = true;
    this.pageWatched = true;
    this.pageFavorites = true;
    if(page== "Watched"){
      this.pageWatchList = false;
      this.pageFavorites = false;
    }


     if(page== "WatchList"){
      this.pageWatched = false;
      this.pageFavorites = false;
     
    }
    if(page== "Favorites"){
      this.pageWatchList = false;
      this.pageWatched = false;

    }

    console.log("***********************")
    console.log("pageWatchList",this.pageWatchList);
    console.log("pageWatched",this.pageWatched);
    console.log("pageFavorites",this.pageFavorites);

  }
  initializeReadBooks(){
    // this.refreshUserFromLocalChash(this.authenticationService.getUserFromLocalCache().id);
    // console.log("USER",this.authenticationService.getUserFromLocalCache())
    const userId = this.authenticationService.getUserFromLocalCache().id;
    this.bookService.getReadBooks(userId.toString()).subscribe(res => {

      this.books = res;
      // this.mostPopularMovies = this.mostPopularMovies.slice(0,5); // de sters
      this.books.forEach((book) => {
        if (book.cover) {
          book.cover = 'data:image/jpeg;base64,' + book.cover;
          console.log("Book with cover:", book);
        }
      });
      console.log("books:", this.books)

    }, err => {
      console.log("Error while fetching data")
    });

    this.bookService.getTBRBooks(userId.toString()).subscribe(res => {
      console.log('test &&& TBR res', res)
      this.movies_watch_list = res;
      // this.mostPopularMovies = this.mostPopularMovies.slice(0,5); // de sters
      this.movies_watch_list.forEach((book) => {
        if (book.cover) {
          book.cover = 'data:image/jpeg;base64,' + book.cover;
          console.log("Book with cover:", book);
        }
      });
      console.log("all books:", this.books)

    }, err => {
      console.log("Error while fetching data")
    });


    this.movies_watch_list = this.authenticationService.getUserFromLocalCache().movies_watch_list;

    if(this.movies_watch_list)
    this.movies_watch_list =  this.movies_watch_list.filter(val => !(this.books.map(a => a.id)).includes(val.id));




  }

  
  convertMinutes(minutes: number) : [number,number]{
    let hours: number;
    let days: number;
    console.log(minutes)
    hours = Math.floor(minutes/60);
    days = Math.floor(hours/24);
    hours = hours%24;
    
    return [days,hours];

  }

  goToMovie(movie: Book) {


    // this.router.navigate(['/movie-page'])
    // localStorage.setItem("movieIdImdb", movie.id)


  }
  // refreshUserFromLocalChash(id: number) {

  //   this.userService.getUserById(id).subscribe(res => {

  //     this.authenticationService.addUserToLocalCache(res);


  //   }, err => {
  //     console.log("Error while fetching data.")
  //   });
  // }

  goToProfile(){
    this.router.navigate(['/user/management']);
    localStorage.setItem('page', 'Profile');
  }

  goToUsers(){
    this.router.navigate(['/user/management']);
    localStorage.setItem('page', 'Users');
  }

  goToSettings(){
    this.router.navigate(['/user/management']);
    localStorage.setItem('page', 'Settings');
  }
  goToMyMovies(){
    // this.router.navigate(['/my-movies']);
    localStorage.setItem('my-movies', 'All');
    this.checkPage();
    console.log("goToMyMovies");

  }
  
  goToMyWatchedMovies(){
    // this.router.navigate(['/my-movies']);
    localStorage.setItem('my-movies', 'Watched');
    this.checkPage();
  }
  goToMyWatchListMovies(){
   // this.router.navigate(['/my-movies']);
    localStorage.setItem('my-movies', 'WatchList');
    this.checkPage();
  }

  goToMyFavoriteMovies(){
   // this.router.navigate(['/my-movies']);
    localStorage.setItem('my-movies', 'Favorites');
    this.checkPage();

  }
}
