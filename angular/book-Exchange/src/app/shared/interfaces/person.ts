import { User } from "./user";

export interface Person {
  id:number
  firstname: string;
  lastname: string;
  userId:number;

}
export interface PersonRegister{
  firstname:string;
  lastname:string;
  username:string;
  password:string;
  role:string;
}