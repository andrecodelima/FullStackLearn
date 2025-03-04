import { Component } from '@angular/core';
import { DefaultLoginComponent } from '../../components/default-login/default-login.component';
import { PrimaryInputComponent } from "../../components/primary-input/primary-input.component";

@Component({
  selector: 'app-login',
  imports: [DefaultLoginComponent, PrimaryInputComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

}

