import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Book } from 'src/app/model/book';
import { Review } from 'src/app/model/review';
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

    this.initializeComments();
    this.commDetails = this.formBuilder.group({

      review: [''],
 
    });

    this.commDetailsEdit = this.formBuilder.group({
      id: [''],
      revieww: ['']
      
    });
  }

  isRead: boolean = false;
  averageRating: number = 0;
  isInTBR: boolean = false;
  book: Book = new Book();
  comments: Review[] = [];

  myCommObj: Review = new Review();
  myCommObjEdit : Review = new Review();

  commDetails !: FormGroup;
  commDetailsEdit !: FormGroup;

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
    console.log('initializeBook');
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

  // addBookToTBR(book: Book){
  //   this.bookService.getAllBooks();
  // }

  addBookToTBR() {

    if (!this.isInTBR) {
      // var userId = this.authenticationService.getUserFromLocalCache().id;
      var userId = "4";
      this.bookService.addBookToTBR(this.book, userId).subscribe(res => {
        console.log('testt res', res);
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'The book was added to your TBR',
          showConfirmButton: false,
          timer: 1500
        })


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

           this.http.delete('http://localhost:8000/book/1/user/4/toRead').subscribe(
            () => {
              console.log('Delete request successful');
              return { status: 200, message: 'Delete request successful' };
            },
            (            error: any) => {
              console.log('Error occurred:', error);
            }
          );
          // let userId = this.authenticationService.getUserFromLocalCache().id;
          // var userId = "4";
          // this.bookService.removeBookFromTBR(this.book.id, userId).subscribe(res => {

          //   console.log(res);
          //   Swal.fire(
          //     'The book was removed!',
          //   )
          //   this.isInTBR = false;

          // }, err => {
          //   console.log(err)
          //   console.log("Error while fetching data")
          // });


          // this.refreshUserFromLocalChash(userId);

        }
      })
    }

  }

  addComm() {


    this.myCommObj.review = this.commDetails.value.text;
  

    let userId = this.authenticationService.getUserFromLocalCache().id;

    // this.commentService.addComment(this.myCommObj, this.movieAPI.id, userId).subscribe(res => {
  
    //   this.initializeComments();

    // }, err => {
    //   console.log("EROARE");
    //   console.log(err);

    // });
  }
  deleteComm(comm: Review){

  }

  editComm(comm: Review){

  }

  initializeComments() {
    console.log('initializeComments this.book', this.book);
    let bookId = this.book.id.toString();
    this.commentService.getAllCommentsByMovieId(bookId).subscribe(res => {


      this.comments = res;
      console.log("comments",this.comments);

      /**Iau din lista doar comentariile carora le-am dat like*/

      // this.likedComms = this.comments.filter(val => val.likes.includes(this.user.id))

      this.commDetails = this.formBuilder.group({
        id: [''],
        text: [''],
        date:['']
      });



    }, err => {
      console.log("Error while fetching data")
      console.log(err);
    });

  }

}
