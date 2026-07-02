import { Component, ChangeDetectionStrategy } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'vaj-auth-footer',
  standalone: true,
  imports: [RouterLink],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './auth-footer.component.html',
  styleUrl: './auth-footer.component.css',
})
export class AuthFooterComponent {}