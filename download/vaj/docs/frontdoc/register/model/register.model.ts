export interface RegisterForm {
  fullName: string;
  username: string;
  email: string;
  mobile: string;
  password: string;
  confirmPassword: string;
  country: string;
  language: string;
  acceptTerms: boolean;
  receiveNotifications: boolean;
}

export interface RegisterResponse {
  accessToken: string;
  refreshToken: string | null;
  expiresAt: string;
  user: RegisterUserSummary;
}

export interface RegisterUserSummary {
  id: string;
  email: string;
  username: string;
  status: string;
}

export interface PasswordRequirement {
  label: string;
  validator: RegExp;
  met: boolean;
}

export interface CountryOption {
  code: string;
  name: string;
  flag: string;
}

export interface LanguageOption {
  code: string;
  name: string;
  nativeName: string;
}

export type PasswordStrengthLevel = 'none' | 'weak' | 'fair' | 'good' | 'strong';

export interface PasswordStrengthInfo {
  score: number;
  level: PasswordStrengthLevel;
  label: string;
  color: string;
  width: string;
}

export type RegisterState = 'idle' | 'loading' | 'success' | 'error';

export interface RegisterError {
  code: string;
  message: string;
  details?: Array<{ field: string; message: string }>;
}

export const PASSWORD_REQUIREMENTS: PasswordRequirement[] = [
  { label: 'حرف بزرگ انگلیسی', validator: /[A-Z]/, met: false },
  { label: 'حرف کوچک انگلیسی', validator: /[a-z]/, met: false },
  { label: 'عدد', validator: /[0-9]/, met: false },
  { label: 'نماد', validator: /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/, met: false },
  { label: 'حداقل ۸ کاراکتر', validator: /.{8,}/, met: false },
];

export const COUNTRIES: CountryOption[] = [
  { code: 'IR', name: 'ایران', flag: '🇮🇷' },
  { code: 'AF', name: 'افغانستان', flag: '🇦🇫' },
  { code: 'US', name: 'آمریکا', flag: '🇺🇸' },
  { code: 'GB', name: 'بریتانیا', flag: '🇬🇧' },
  { code: 'DE', name: 'آلمان', flag: '🇩🇪' },
  { code: 'FR', name: 'فرانسه', flag: '🇫🇷' },
  { code: 'CA', name: 'کانادا', flag: '🇨🇦' },
  { code: 'AU', name: 'استرالیا', flag: '🇦🇺' },
  { code: 'TR', name: 'ترکیه', flag: '🇹🇷' },
  { code: 'AE', name: 'امارات', flag: '🇦🇪' },
];

export const LANGUAGES: LanguageOption[] = [
  { code: 'fa', name: 'Persian', nativeName: 'فارسی' },
  { code: 'en', name: 'English', nativeName: 'English' },
  { code: 'ar', name: 'Arabic', nativeName: 'العربية' },
];