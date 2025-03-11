import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrimaryInputsComponent } from './primary-inputs.component';

describe('PrimaryInputsComponent', () => {
  let component: PrimaryInputsComponent;
  let fixture: ComponentFixture<PrimaryInputsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrimaryInputsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrimaryInputsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
