import {
  Component,
  ChangeDetectionStrategy,
  computed,
  effect,
  input,
} from '@angular/core';
import { CommonModule } from '@angular/common';

export interface Requirement {
  label: string;
  regex: RegExp;
  met: boolean;
}

export interface StrengthInfo {
  score: number;
  level: 'none' | 'weak' | 'fair' | 'good' | 'strong';
  color: string;
  label: string;
}

export const PASSWORD_REQUIREMENTS: Omit<Requirement, 'met'>[] = [
  { label: 'حرف بزرگ انگلیسی', regex: /[A-Z]/ },
  { label: 'حرف کوچک انگلیسی', regex: /[a-z]/ },
  { label: 'عدد', regex: /[0-9]/ },
  { label: 'نماد', regex: /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/ },
  { label: 'حداقل ۸ کاراکتر', regex: /.{8,}/ },
];

const STRENGTH_MAP: Record<
  number,
  Pick<StrengthInfo, 'level' | 'color' | 'label'>
> = {
  0: { level: 'none', color: '#B9C7DE', label: '—' },
  1: { level: 'weak', color: '#FF8C8C', label: 'ضعیف' },
  2: { level: 'weak', color: '#FF8C8C', label: 'ضعیف' },
  3: { level: 'fair', color: '#FACC15', label: 'متوسط' },
  4: { level: 'good', color: '#6FA8FF', label: 'خوب' },
  5: { level: 'strong', color: '#86EFAC', label: 'قوی' },
};

@Component({
  selector: 'vaj-password-strength',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './password-strength.component.html',
  styleUrls: ['./password-strength.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PasswordStrengthComponent {
  /** Password signal from parent input */
  password = input.required<string>();

  /** Computed: evaluates each requirement against the current password */
  requirements = computed<Requirement[]>(() => {
    const pwd = this.password();

    return PASSWORD_REQUIREMENTS.map((req) => ({
      ...req,
      met: req.regex.test(pwd),
    }));
  });

  /** Computed: calculates overall strength info (score, level, color, label) */
  strengthInfo = computed<StrengthInfo>(() => {
    const reqs = this.requirements();
    const score = reqs.filter((r) => r.met).length;
    const config = STRENGTH_MAP[score];

    return {
      score,
      level: config.level,
      color: config.color,
      label: config.label,
    };
  });

  /** Optional: log strength changes for debugging */
  private _logEffect = effect(() => {
    const info = this.strengthInfo();
    // eslint-disable-next-line no-console
    console.log(
      `[PasswordStrength] level=${info.level}, score=${info.score}, label="${info.label}"`
    );
  });
}