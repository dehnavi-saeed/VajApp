import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BenefitsPanelComponent } from '../benefits-panel/benefits-panel.component';
import { RegisterCardComponent } from '../register-card/register-card.component';

@Component({
  selector: 'vaj-register-page',
  standalone: true,
  imports: [CommonModule, BenefitsPanelComponent, RegisterCardComponent],
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RegisterPageComponent {}