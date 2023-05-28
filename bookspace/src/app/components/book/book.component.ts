import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Book } from 'src/app/model/book';
import { Review } from 'src/app/model/review';
import { User } from 'src/app/model/user';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { BookService } from 'src/app/service/book.service';
import { CommentService } from 'src/app/service/comment.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent {

  constructor(private bookService: BookService, private authenticationService: AuthenticationService, private router:Router, private http: HttpClient,private commentService: CommentService,private formBuilder: FormBuilder) { }
  
  ngOnInit(): void {
    this.initializeBook();


    this.commDetails = this.formBuilder.group({

      review: [''],
 
    });

    this.commDetailsEdit = this.formBuilder.group({
      id: [''],
      textt: ['']
      
    });

    // this.initializeReadBooks();
    // this.initializeTBRBooks();
  }
  coverImageUrl: any;
  isRead: boolean = false;
  averageRating: number = 0;
  isInTBR: boolean = false;
  book: Book = new Book();
  comments: Review[] = [];

  myCommObj: Review = new Review();
  myCommObjEdit : Review = new Review();

  commDetails !: FormGroup;
  commDetailsEdit !: FormGroup;

  books: Book[] = [];
  movies_watch_list: Book[] = [];

  user: User = this.authenticationService.getUserFromLocalCache();

  checkRead(): void {

    let found: boolean = false;
    // let movies: Movie[] = this.authenticationService.getUserFromLocalCache().movies;

    for (let book of this.books) {
      if (book.id == this.book.id) {
        found = true;
        this.isRead = true;
        break;
      }
    }
    if (found == false)
      this.isRead = false;
  }

  checkTBR(): void {

    let found: boolean = false;
    // let movies: Movie[] = this.authenticationService.getUserFromLocalCache().movies;
    // console.log('testt this.movies_watch_list', this.movies_watch_list);
    console.log('#', this.movies_watch_list)
    for (let book of this.movies_watch_list) {
      console.log('###',book.id, this.book.id);
      if (book.id == this.book.id) {
        found = true;
        console.log('')
        this.isInTBR = true;
        break;
      }
    }
    if (found == false)
      this.isInTBR = false;
  }

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
  // addBook() {
  //   this.isRead =  !this.isRead;
  // }

  initializeBook(){
    console.log('initializeBook');
    var idBook = '';
    var temp = localStorage.getItem('bookId');
    if (temp !== null) {
      var idBook = temp;
    }
    console.log('idBook', idBook);
    this.bookService.getBookById(idBook).subscribe(res => {
      // console.log('res', res);
      this.book = res;
      this.coverImageUrl = 'data:image/jpeg;base64,' + this.book.cover;

      this.initializeComments();
      this.initializeReadBooks();
      this.initializeTBRBooks();

    }, err => {
      console.log("Error while fetching data")
    });
  }

  addBookToTBR() {

    if (!this.isInTBR) {
      // var userId = this.authenticationService.getUserFromLocalCache().id;
      var userId = this.authenticationService.getUserFromLocalCache().id;
      this.bookService.addBookToTBR(this.book, userId).subscribe(res => {
        console.log('testt res', res);
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'The book was added to your TBR',
          showConfirmButton: false,
          timer: 1500
        })
        // this.movies_watch_list = res;

        // this.refreshUserFromLocalChash(this.authenticationService.getUserFromLocalCache().id);
        this.isInTBR = true;
        console.log("ESTE IN WATCH FILMUL?", this.isInTBR);

      }, err => {
        console.log(err);



      });
    }
    else {
      Swal.fire({
        title: 'Do you want to remove the book from your TBR?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#4E9A9B',
        confirmButtonText: 'Yes, remove it!'
      }).then((result) => {
        if (result.isConfirmed) {

          let userId = this.authenticationService.getUserFromLocalCache().id;
    
          this.bookService.removeBookFromTBR(this.book.id, userId).subscribe(res => {

            console.log(res);
            Swal.fire(
              'The book was removed!',
            )
            this.isInTBR = false;

          }, err => {
            console.log(err)
            console.log("Error while fetching data")
          });


          // this.refreshUserFromLocalChash(userId);

        }
      })
    }

  }

  addBook() {
    if (!this.isRead) {
      var id = this.authenticationService.getUserFromLocalCache().id;
      this.bookService.addBook(this.book, id).subscribe(res => {

        // console.log("Filmul a fost adaugat cu sucess:", res);

        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'The book was added to your list',
          showConfirmButton: false,
          timer: 1500
        })

        // this.refreshUserFromLocalChash(this.authenticationService.getUserFromLocalCache().id);
        this.isRead = true;
        console.log("ESTE VAZUT FILMUL?", this.isRead);

      }, err => {
        console.log(err);

      });

    }
    else {
      Swal.fire({
        title: 'Do you want to remove the movie from your list?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#4E9A9B',
        confirmButtonText: 'Yes, delete it!'
      }).then((result) => {
        if (result.isConfirmed) {


          let userId = this.authenticationService.getUserFromLocalCache().id;

          this.bookService.removeBook(this.book.id, userId).subscribe(res => {


            Swal.fire(
              'The movie was removed!',
            )
            this.isRead = false;

            //Atunci cand sterg filmul din lista il sterg si din lista favoritelor
  
   


            // this.refreshUserFromLocalChash(userId);





          }, err => {
            console.log(err)
            console.log("Error while fetching data")
          });

          // this.refreshUserFromLocalChash(userId);


        }
      })
    }
  }

  addComm() {
    console.log('addComm');

    this.myCommObj.review = this.commDetails.value.review;
    console.log('this.myCommObj', this.myCommObj);
    // let userId = 3;
    var userId = this.authenticationService.getUserFromLocalCache().id;
    // let userId = this.authenticationService.getUserFromLocalCache().id;

    this.commentService.addComment(this.myCommObj, this.book.id.toString(), userId).subscribe(res => {
      
      this.initializeComments();
      console.log('addComm res', res);
    }, err => {
 
      console.log(err);

    });
  }
  deleteComm(comm: Review) {
    Swal.fire({
  title: 'Do you want to delete your review?',
  icon: 'warning',
  showCancelButton: true,
  confirmButtonColor: '#3085d6',
  cancelButtonColor: '#4E9A9B',
  confirmButtonText: 'Yes, delete it!'
}).then((result) => {
  if (result.isConfirmed) {


    var userId = this.authenticationService.getUserFromLocalCache().id;
    this.commentService.deleteComment(comm, this.book.id.toString(),userId).subscribe(res => {
      Swal.fire(
        'The review was deleted!',
      )
      this.initializeComments();
    }, err => {
      
      console.log("ERROR:", err);

    })






  }
})


}
editComm(comm: Review) {
 
  this.commDetailsEdit.controls['id'].setValue(comm.id);
   this.commDetailsEdit.controls['textt'].setValue(comm.review);


 }
updateComm() {
   
  
  this.myCommObjEdit.id = this.commDetailsEdit.value.id;
 this.myCommObjEdit.review = this.commDetailsEdit.value.textt;

 var userId = this.authenticationService.getUserFromLocalCache().id;
  this.commentService.updateComm(this.myCommObjEdit, this.book.id.toString(), userId).subscribe(res=>{
    console.log("res",res);

    this.commDetailsEdit = this.formBuilder.group({
      id : [''],
      textt : ['']


    });  
   
    this.initializeComments();
  },err=>{
    console.log(err);
  })
}

  initializeComments() {
    console.log('initializeComments this.book', this.book);
    let bookId = this.book.id.toString();
    this.commentService.getAllCommentsByBookId(bookId).subscribe(res => {
      console.log("res",res);

      this.comments = res;
      console.log("comments",this.comments);

      for (let comment of this.comments){
        console.log(comment)
      }
      /**Iau din lista doar comentariile carora le-am dat like*/

      // this.likedComms = this.comments.filter(val => val.likes.includes(this.user.id))

      this.commDetails = this.formBuilder.group({
        id: [''],
        review: [''],
        date:['']
      });



    }, err => {
      console.log("Error while fetching data")
      console.log(err);
    });

  }

  goToMyBooks(){
    this.router.navigate(['/my-books'])
  }

  logOut(){
    this.authenticationService.logOut();
    this.router.navigate(['/login'])
  }

  initializeReadBooks(){
    // this.refreshUserFromLocalChash(this.authenticationService.getUserFromLocalCache().id);
    // console.log("USER",this.authenticationService.getUserFromLocalCache())
    const userId = this.authenticationService.getUserFromLocalCache().id;
    this.bookService.getReadBooks(userId.toString()).subscribe(res => {

      this.books = res;
      // this.mostPopularMovies = this.mostPopularMovies.slice(0,5); // de sters
      this.checkRead();
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

    
  

    // this.bookService.getTBRBooks(userId.toString()).subscribe(res => {
    //   console.log('test &&& TBR res', res)
    //   this.movies_watch_list = res;
    //   // this.mostPopularMovies = this.mostPopularMovies.slice(0,5); // de sters
    //   // this.movies_watch_list.forEach((book) => {
    //   //   if (book.cover) {
    //   //     book.cover = 'data:image/jpeg;base64,' + book.cover;
    //   //     console.log("Book with cover:", book);
    //   //   }
    //   // });
    //   console.log("this.movies_watch_list *:", this.books)

    // }, err => {
    //   console.log("Error while fetching data")
    // });


    // this.movies_watch_list = this.authenticationService.getUserFromLocalCache().movies_watch_list;

    // if(this.movies_watch_list)
    // this.movies_watch_list =  this.movies_watch_list.filter(val => !(this.books.map(a => a.id)).includes(val.id));


    // this.checkRead();
    // this.checkTBR();

  }

  initializeTBRBooks(){
    // this.refreshUserFromLocalChash(this.authenticationService.getUserFromLocalCache().id);
    // console.log("USER",this.authenticationService.getUserFromLocalCache())
    const userId = this.authenticationService.getUserFromLocalCache().id;
  

    this.bookService.getTBRBooks(userId.toString()).subscribe(res => {
      console.log('test &&& TBR res', res)
      this.movies_watch_list = res;
      console.log("this.movies_watch_list *:", this.movies_watch_list)
      this.checkTBR();
      // this.mostPopularMovies = this.mostPopularMovies.slice(0,5); // de sters
      // this.movies_watch_list.forEach((book) => {
      //   if (book.cover) {
      //     book.cover = 'data:image/jpeg;base64,' + book.cover;
      //     console.log("Book with cover:", book);
      //   }
      // });
      

    }, err => {
      console.log("Error while fetching data")
    });


    // this.movies_watch_list = this.authenticationService.getUserFromLocalCache().movies_watch_list;

    if(this.movies_watch_list)
    this.movies_watch_list =  this.movies_watch_list.filter(val => !(this.books.map(a => a.id)).includes(val.id));

    
    this.checkTBR();

  }
}
