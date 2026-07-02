import {
  Component,
  ChangeDetectionStrategy,
  input,
  signal,
  computed,
  output,
} from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'vaj-terms-checkbox',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './terms-checkbox.component.html',
  styleUrl: './terms-checkbox.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TermsCheckboxComponent {
  readonly checked = input<boolean>(false);
  readonly label = input.required<string>();
  readonly disabled = input<boolean>(false);
  readonly checkedChange = output<boolean>();

  /** Internal signal to track the current error/shake state */
  private _errorSignal = signal(false);

  /** Whether the checkbox is currently in an error state (drives shake animation) */
  errorState = computed(() => this._errorSignal());

  /**
   * Toggle the checked value. If the checkbox is disabled, do nothing.
   */
  toggle(): void {
    if (this.disabled()) return;
    this.checkedChange.emit(!this.checked());
  }

  /**
   * Programmatically trigger the error/shake animation.
   * Useful when parent form validation detects the checkbox is unchecked.
   */
  triggerError(): void {
    this._errorSignal.set(true);
    // Reset after the shake animation completes (500ms)
    setTimeout(() => this._errorSignal.set(false), 500);
  }
}