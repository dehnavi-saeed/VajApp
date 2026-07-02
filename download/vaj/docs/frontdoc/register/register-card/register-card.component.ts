import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthHeaderComponent } from '../auth-header/auth-header.component';
import { RegisterFormComponent } from '../register-form/register-form.component';
import { SocialLoginComponent } from '../social-login/social-login.component';
import { AuthFooterComponent } from '../auth-footer/auth-footer.component';

@Component({
  selector: 'vaj-register-card',
  standalone: true,
  imports: [CommonModule, AuthHeaderComponent, RegisterFormComponent, SocialLoginComponent, AuthFooterComponent],
  templateUrl: './register-card.component.html',
  styleUrls: ['./register-card.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RegisterCardComponent {}