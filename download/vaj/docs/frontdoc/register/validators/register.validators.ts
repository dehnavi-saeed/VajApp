import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function matchingFields(controlName: string, matchingControlName: string): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const formGroup = control as AbstractControl;
    const source = formGroup.get(controlName);
    const target = formGroup.get(matchingControlName);

    if (!source || !target) return null;

    if (target.value && source.value !== target.value) {
      target.setErrors({ ...target.errors, mismatch: true });
      return { mismatch: true };
    }

    if (target.hasError('mismatch')) {
      const errors = { ...target.errors };
      delete errors['mismatch'];
      target.setErrors(Object.keys(errors).length ? errors : null);
    }

    return null;
  };
}

export function persianMobile(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (!value) return null;

    const cleaned = value.replace(/[\s\-+]/g, '');
    const iranPattern = /^09[0-9]{9}$/;
    const internationalPattern = /^\+[0-9]{10,14}$/;

    if (iranPattern.test(cleaned) || internationalPattern.test(cleaned)) {
      return null;
    }

    return { invalidMobile: true };
  };
}

export function usernameValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (!value) return null;

    if (value.length < 3) return { minlength: { requiredLength: 3, actualLength: value.length } };
    if (value.length > 30) return { maxlength: { requiredLength: 30, actualLength: value.length } };
    if (!/^[a-zA-Z0-9_]+$/.test(value)) return { invalidCharacters: true };
    if (/^\d/.test(value)) return { startsWithNumber: true };

    return null;
  };
}

export function strongPassword(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value: string = control.value;
    if (!value) return null;

    const checks = [
      { key: 'noUppercase', test: /[A-Z]/ },
      { key: 'noLowercase', test: /[a-z]/ },
      { key: 'noNumber', test: /[0-9]/ },
      { key: 'noSymbol', test: /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/ },
      { key: 'tooShort', test: (v: string) => v.length >= 8 },
    ];

    const errors: ValidationErrors = {};
    for (const check of checks) {
      if (!check.test(value)) {
        errors[check.key] = true;
      }
    }

    return Object.keys(errors).length ? errors : null;
  };
}

export function noLeadingTrailingWhitespace(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value: string = control.value;
    if (!value) return null;
    if (value !== value.trim()) return { leadingTrailingWhitespace: true };
    return null;
  };
}