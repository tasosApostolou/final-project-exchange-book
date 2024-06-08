export interface User{
    id:number
    username:string;
    password:string;
    role:string
}

export interface Credentials{
    username:string;
    password: string;
}
export interface LoggedInUser{
    userId:number;
    sub:string; // username taken from jwt as sub
    role :string;
    roleEntityId:number //PERSONAL or STORE logged in user
    
}