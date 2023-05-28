export class Book {
  public id: number;
 public title: string;
 public author: string;
 public genre: string;
 public date: string ;
 public description: string;
 public cover: string;


 constructor() {
    this.id = null as any;
   this.title = '';
   this.author = '';
   this.date = '';
   this.description = '';
   this.genre = '';
   this.cover = '';
   
 }

}