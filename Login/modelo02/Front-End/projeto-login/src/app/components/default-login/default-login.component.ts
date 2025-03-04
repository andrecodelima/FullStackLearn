import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-default-login',
  imports: [],
  templateUrl: './default-login.component.html',
  styleUrl: './default-login.component.scss'
})
export class DefaultLoginComponent {

  @Input() title:string = "";
  @Input() primaryBtnText="";
  @Input() secondaryBtnText="";
  @Input() disablePrimaryText="";

  @Output("submit") onSubmit = new EventEmitter();
  @Output("navigate") onNavigate = new EventEmitter();

  submit(){
    this.onSubmit.emit();
  }

  navigate(){
    this.onNavigate.emit();
  }
}
