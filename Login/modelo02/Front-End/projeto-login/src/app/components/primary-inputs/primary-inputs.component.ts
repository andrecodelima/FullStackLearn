import { Component, forwardRef, Input } from '@angular/core';
import { ControlValueAccessor, FormControlName, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';

type InputTypes = "text" | "email" | "password"

@Component({
  selector: 'app-primary-inputs',
  standalone:true,
  imports: [
    ReactiveFormsModule
  ],

  providers:[
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting:forwardRef(() => PrimaryInputsComponent),
      multi:true
    }
  ],


  templateUrl: './primary-inputs.component.html',
  styleUrl: './primary-inputs.component.scss'
})

/*
Adicoes a ControlValuerAcessor 
providers:[
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting:forwardRef(() => PrimaryInputsComponent),
      multi:true
    }
  ],

   value:string = ''
  onChange:any = () => {}
  onTouched:any = () =>{}

  onInput(event: Event){
    const value = (event.target as HTMLInputElement).value
    this.onChange(value)
  }

  writeValue(value: any): void {
      this.value = value
  }

  registerOnChange(fn: any): void {
      this.onChange = fn
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  } 

  setDisabledState(isDisabled: boolean): void {}


*/
export class PrimaryInputsComponent implements ControlValueAccessor{ // Corrije o erro:  NG01050: formControlName must be used with a parent formGroup.


  @Input() type: InputTypes = "text";
  // @Input() formName:string = "";
  @Input() placeHolder:string = "";
  @Input() label:string = "";
  @Input() inputName:string = "";

  value:string = ''
  onChange:any = () => {}
  onTouched:any = () =>{}

  onInput(event: Event){
    const value = (event.target as HTMLInputElement).value
    this.onChange(value)
  }

  writeValue(value: any): void {
      this.value = value
  }

  registerOnChange(fn: any): void {
      this.onChange = fn
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  } 

  setDisabledState(isDisabled: boolean): void {}
}
