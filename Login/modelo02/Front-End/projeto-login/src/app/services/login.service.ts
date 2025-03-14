import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponse } from '../Types/login-response.type';
import { tap } from 'rxjs';

// Logica enviar os dados de login para o backEnd e pegar o retorno do backEnd e salvar o token na sessão do usuario.
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient:HttpClient) {}

  login(name:string, password:string){
    return this.httpClient.post<LoginResponse>("/login", {name, password}).pipe(
      tap((value)=>{
        sessionStorage.setItem("auth-token", value.token)
        sessionStorage.setItem("username", value.name)
      })
    )
  }

}
