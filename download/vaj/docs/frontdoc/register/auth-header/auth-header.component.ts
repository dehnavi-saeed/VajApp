import { Component, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'vaj-auth-header',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './auth-header.component.html',
  styleUrl: './auth-header.component.css',
})
export class AuthHeaderComponent {}