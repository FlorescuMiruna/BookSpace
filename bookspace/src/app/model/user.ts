export class User {
    public id: number;
   public userId: string;
   public firstName: string;
   public lastName: string;
   public username: string ;
   public email: string;
   public lastLoginDate: Date;
   public lastLoginDateDisplay: Date;
   public joinDate: Date;
   public profileImageUrl: string;
   public active: boolean;
   public notLocked: boolean;
   public role: string;
   public authorities: [];
   public movies:[];
   public movies_watch_list:[];
   public favorites:[];
   public password: string;
 
   constructor() {
      this.id = null as any;
     this.userId = '';
     this.firstName = '';
     this.lastName = '';
     this.username = '';
     this.email = '';
     this.lastLoginDate = null as any;
     this.lastLoginDateDisplay = null as any;
     this.joinDate = null as any;
     this.profileImageUrl = '';
     this.active = false;
     this.notLocked = false;
     this.role = '';
     this.authorities = [];
     this.movies = [];
     this.movies_watch_list = [];
     this.favorites = [];
     this.password = '';
   }
 
 }