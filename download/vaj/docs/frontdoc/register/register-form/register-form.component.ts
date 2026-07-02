import {
  Component,
  ChangeDetectionStrategy,
  inject,
  signal,
  computed,
  DestroyRef,
  OnInit,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { RegisterService } from '../services/register.service';
import { PasswordStrengthComponent } from '../password-strength/password-strength.component';
import { TermsCheckboxComponent } from '../terms-checkbox/terms-checkbox.component';
import {
  matchingFields,
  persianMobile,
  usernameValidator,
  strongPassword,
  noLeadingTrailingWhitespace,
} from '../validators/register.validators';
import { COUNTRIES, LANGUAGES } from '../model/register.model';

@Component({
  selector: 'vaj-register-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PasswordStrengthComponent,
    TermsCheckboxComponent,
  ],
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RegisterFormComponent implements OnInit {
  /** ── Injected Services ── */
  private readonly fb = inject(FormBuilder);
  private readonly registerService = inject(RegisterService);
  private readonly destroyRef = inject(DestroyRef);

  /** ── Constants exposed to template ── */
  readonly countries = COUNTRIES;
  readonly languages = LANGUAGES;

  /** ── Reactive Form ── */
  form!: FormGroup;

  /** ── Password Visibility Signals ── */
  readonly showPassword = signal(false);
  readonly showConfirmPassword = signal(false);

  /** ── Error Alert Dismiss ── */
  readonly errorDismissed = signal(false);

  /** ── Password signal for PasswordStrengthComponent ── */
  readonly passwordValue = signal('');

  /** ── Computed States from Service ── */
  readonly isLoading = computed(() => this.registerService.isLoading());
  readonly isSuccess = computed(() => this.registerService.isSuccess());
  readonly hasError = computed(() => this.registerService.hasError());
  readonly errorMessage = computed(() => this.registerService.error()?.message ?? '');

  /** ── Show error alert only when there IS an error AND it hasn't been dismissed ── */
  readonly showErrorAlert = computed(
    () => this.hasError() && !this.errorDismissed()
  );

  ngOnInit(): void {
    this.buildForm();
    this.subscribeToPasswordChanges();
  }

  /** ── Build the form with all validators ── */
  private buildForm(): void {
    this.form = this.fb.group(
      {
        fullName: [
          '',
          [
            Validators.required,
            Validators.minLength(3),
            noLeadingTrailingWhitespace(),
          ],
        ],
        username: [
          '',
          [Validators.required, usernameValidator()],
        ],
        email: ['', [Validators.required, Validators.email]],
        mobile: ['', [Validators.required, persianMobile()]],
        password: [
          '',
          [
            Validators.required,
            Validators.minLength(8),
            strongPassword(),
          ],
        ],
        confirmPassword: ['', [Validators.required]],
        country: ['', [Validators.required]],
        language: ['', [Validators.required]],
      },
      {
        validators: [matchingFields('password', 'confirmPassword')],
      }
    );
  }

  /** ── Pipe password valueChanges into a signal for the strength component ── */
  private subscribeToPasswordChanges(): void {
    this.form
      .get('password')!
      .valueChanges.pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((value: string) => {
        this.passwordValue.set(value ?? '');
      });
  }

  /** ── Toggle password visibility ── */
  togglePasswordVisibility(field: 'password' | 'confirmPassword'): void {
    if (field === 'password') {
      this.showPassword.update((v) => !v);
    } else {
      this.showConfirmPassword.update((v) => !v);
    }
  }

  /** ── Dismiss the error alert ── */
  dismissError(): void {
    this.errorDismissed.set(true);
  }

  /** ── Helper: get error message for a given control ── */
  getErrorMessage(control: AbstractControl | null, fieldName: string): string {
    if (!control || !control.errors || !(control.dirty && control.touched)) {
      return '';
    }

    const errors = control.errors;

    switch (fieldName) {
      case 'fullName':
        if (errors['required']) return 'نام و نام خانوادگی الزامی است.';
        if (errors['minlength'])
          return `حداقل ${errors['minlength'].requiredLength} کاراکتر وارد کنید.`;
        if (errors['leadingTrailingWhitespace'])
          return 'لطفاً از فاصله در ابتدا یا انتهای نام خودداری کنید.';
        break;

      case 'username':
        if (errors['required']) return 'نام کاربری الزامی است.';
        if (errors['minlength'])
          return `حداقل ${errors['minlength'].requiredLength} کاراکتر وارد کنید.`;
        if (errors['maxlength'])
          return `حداکثر ${errors['maxlength'].requiredLength} کاراکتر مجاز است.`;
        if (errors['invalidCharacters'])
          return 'فقط حروف انگلیسی، عدد و زیرخط (_) مجاز است.';
        if (errors['startsWithNumber'])
          return 'نام کاربری نمی‌تواند با عدد شروع شود.';
        break;

      case 'email':
        if (errors['required']) return 'ایمیل الزامی است.';
        if (errors['email']) return 'لطفاً یک ایمیل معتبر وارد کنید.';
        break;

      case 'mobile':
        if (errors['required']) return 'شماره موبایل الزامی است.';
        if (errors['invalidMobile'])
          return 'شماره موبایل معتبر نیست (مثلاً: ۰۹۱۲۳۴۵۶۷۸۹).';
        break;

      case 'password':
        if (errors['required']) return 'رمز عبور الزامی است.';
        if (errors['minlength'])
          return `حداقل ${errors['minlength'].requiredLength} کاراکتر وارد کنید.`;
        if (errors['noUppercase']) return 'باید حداقل یک حرف بزرگ انگلیسی داشته باشد.';
        if (errors['noLowercase']) return 'باید حداقل یک حرف کوچک انگلیسی داشته باشد.';
        if (errors['noNumber']) return 'باید حداقل یک عدد داشته باشد.';
        if (errors['noSymbol']) return 'باید حداقل یک نماد داشته باشد.';
        break;

      case 'confirmPassword':
        if (errors['required']) return 'تکرار رمز عبور الزامی است.';
        if (errors['mismatch']) return 'رمز عبور با تکرار آن مطابقت ندارد.';
        break;

      case 'country':
        if (errors['required']) return 'انتخاب کشور الزامی است.';
        break;

      case 'language':
        if (errors['required']) return 'انتخاب زبان الزامی است.';
        break;
    }

    return 'لطفاً مقدار معتبری وارد کنید.';
  }

  /** ── Submit Handler ── */
  onSubmit(): void {
    // Reset error dismissed state so new errors can show
    this.errorDismissed.set(false);

    // Mark all controls as touched to trigger validation display
    this.markAllTouched(this.form);

    if (this.form.valid) {
      const raw = this.form.getRawValue();
      this.registerService.register({
        fullName: raw.fullName,
        username: raw.username,
        email: raw.email,
        mobile: raw.mobile,
        password: raw.password,
        confirmPassword: raw.confirmPassword,
        country: raw.country,
        language: raw.language,
        acceptTerms: true,
        receiveNotifications: true,
      });
    }
  }

  /** ── Recursively mark all controls as touched ── */
  private markAllTouched(control: AbstractControl): void {
    control.markAsTouched({ onlySelf: true });
    if (control instanceof FormGroup) {
      Object.values(control.controls).forEach((c) => this.markAllTouched(c));
    }
  }
}