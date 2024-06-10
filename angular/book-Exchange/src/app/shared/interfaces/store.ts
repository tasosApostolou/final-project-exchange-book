export interface Store{
  id:number
  name: string;
  address: string;
  // user:User;
  userId:number;
}

export interface storeRegister{
  name:string;
  address:string;
  username:string;
  password:string;
  role:string;
}