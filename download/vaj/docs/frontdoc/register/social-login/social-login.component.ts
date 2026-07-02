import { Component, input, output, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'vaj-social-login',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './social-login.component.html',
  styleUrl: './social-login.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SocialLoginComponent {
  readonly disabled = input<boolean>(false);
  readonly clicked = output<void>();

  /**
   * Emit the clicked event when the Google button is pressed.
   * No-op when the component is disabled.
   */
  onGoogleClick(): void {
    if (this.disabled()) return;
    this.clicked.emit();
  }
}