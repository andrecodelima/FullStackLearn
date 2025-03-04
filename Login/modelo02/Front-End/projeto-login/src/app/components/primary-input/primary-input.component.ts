import { Component, forwardRef, Input } from '@angular/core';
type InputTypes = "text" | "email" | "password"

@Component({
  selector: 'app-primary-input',
  imports: [],
  templateUrl: './primary-input.component.html',
  styleUrl: './primary-input.component.scss'
})

export class PrimaryInputComponent{

  @Input() type:InputTypes = "text";
  @Input() placeHolder:String = "";
  @Input() label:String="";
  @Input() inputNome:String="";

  

}
