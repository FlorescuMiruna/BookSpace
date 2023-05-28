import { User } from "./user";


 export class Review {
    public id: number;
   public review: string;
   public date: string;
   public user: User;
  //  public userId: number;
  //  public bookId: number;

  
 
   constructor() {
      this.id = null as any;
     this.review = '';
     this.date = '';
    //  this.userId = null as any;
    //  this.bookId = null as any;
    this.user = new User();

     
   }
 
 }