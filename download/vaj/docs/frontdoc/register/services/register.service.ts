import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {
  RegisterForm,
  RegisterResponse,
  RegisterError,
  RegisterState,
} from '../model/register.model';

@Injectable({ providedIn: 'root' })
export class RegisterService {
  private readonly API_BASE = '/api/v1/auth';

  readonly state = signal<RegisterState>('idle');
  readonly error = signal<RegisterError | null>(null);

  readonly isLoading = computed(() => this.state() === 'loading');
  readonly isSuccess = computed(() => this.state() === 'success');
  readonly hasError = computed(() => this.state() === 'error');

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router,
  ) {}

  register(form: RegisterForm): void {
    this.state.set('loading');
    this.error.set(null);

    const payload = {
      username: form.username,
      email: form.email,
      password: form.password,
    };

    this.http
      .post<RegisterResponse>(`${this.API_BASE}/register`, payload)
      .subscribe({
        next: (response) => {
          this.state.set('success');
          this.storeTokens(response);
          this.scheduleRedirect();
        },
        error: (err) => {
          this.state.set('error');
          this.error.set(this.parseError(err));
        },
      });
  }

  private storeTokens(response: RegisterResponse): void {
    if (response.accessToken) {
      localStorage.setItem('vaj_access_token', response.accessToken);
    }
    if (response.refreshToken) {
      localStorage.setItem('vaj_refresh_token', response.refreshToken);
    }
  }

  private scheduleRedirect(): void {
    setTimeout(() => {
      this.router.navigate(['/dashboard']);
    }, 2500);
  }

  private parseError(err: any): RegisterError {
    const apiError = err?.error?.error;

    if (apiError) {
      return {
        code: apiError.code || 'UNKNOWN',
        message: apiError.message || 'خطای ناشناخته رخ داد.',
        details: apiError.details,
      };
    }

    if (err?.status === 0) {
      return {
        code: 'NETWORK_ERROR',
        message: 'اتصال به سرور برقرار نیست. لطفاً اینترنت خود را بررسی کنید.',
      };
    }

    return {
      code: 'UNEXPECTED',
      message: 'خطای غیرمنتظره‌ای رخ داد. لطفاً دوباره تلاش کنید.',
    };
  }

  reset(): void {
    this.state.set('idle');
    this.error.set(null);
  }
}