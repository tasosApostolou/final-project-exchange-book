import { User } from "./user";

export interface Person {
  id:number
  firstname: string;
  lastname: string;
  // user:User;
  userId:number;
  // city: number;
  // email: string;
  // phone: string;
}
export interface PersonRegister{
  firstname:string;
  lastname:string;
  username:string;
  password:string;
  role:string;
}