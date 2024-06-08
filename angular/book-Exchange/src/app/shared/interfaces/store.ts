export interface store{
    id:number
    name: string;
    address: string;
    // user:User;
    userId:number;
    // city: number;
    // email: string;
    // phone: string;
}

export interface storeRegister{
  name:string;
  address:string;
  username:string;
  password:string;
  role:string;
}