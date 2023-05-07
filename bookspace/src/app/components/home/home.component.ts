import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { BookService } from 'src/app/service/book.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private bokService: BookService, private authenticationService: AuthenticationService, private router:Router) { }
  ngOnInit(): void {
  }

  goToBook() {


    this.router.navigate(['/book'])



  }

}
