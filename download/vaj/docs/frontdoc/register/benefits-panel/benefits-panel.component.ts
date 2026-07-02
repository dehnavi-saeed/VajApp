import {
  Component,
  ChangeDetectionStrategy,
  signal,
  computed,
  AfterViewInit,
  DestroyRef,
  inject,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  BookOpen,
  PenLine,
  BarChart3,
  Tags,
  Target,
} from 'lucide-angular';

// ─── Static benefit definitions ─────────────────────────────────────────────
interface Benefit {
  icon: typeof BookOpen;
  label: string;
}

const BENEFITS: Benefit[] = [
  { icon: BookOpen, label: 'دسترسی به هزاران کتاب' },
  { icon: PenLine, label: 'یادداشت‌برداری هوشمند' },
  { icon: BarChart3, label: 'آمار خوانش پیشرفته' },
  { icon: Tags, label: 'دسته‌بندی و برچسب‌گذاری' },
  { icon: Target, label: 'هدف‌گذاری مطالعه' },
];

// ─── Stat counter target values ─────────────────────────────────────────────
interface StatTarget {
  value: number;
  label: string;
  suffix: string;
}

const STAT_TARGETS: StatTarget[] = [
  { value: 12000, label: 'کتاب', suffix: '+' },
  { value: 5000, label: 'کاربر', suffix: '+' },
  { value: 1200, label: 'نویسنده', suffix: '+' },
];

@Component({
  selector: 'vaj-benefits-panel',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './benefits-panel.component.html',
  styleUrl: './benefits-panel.component.css',
  imports: [BookOpen, PenLine, BarChart3, Tags, Target],
})
export class BenefitsPanelComponent implements AfterViewInit {
  // ── Public signals ────────────────────────────────────────────────────────
  /** Stagger animation trigger — flips to `true` after view init */
  readonly animateIn = signal(false);

  /** Current animated counter values for each stat */
  readonly counters = signal<number[]>([0, 0, 0]);

  /** Formatted counter strings with locale & suffix */
  readonly formattedCounters = computed(() =>
    this.counters().map((v, i) =>
      v.toLocaleString('fa-IR') + STAT_TARGETS[i].suffix,
    ),
  );

  /** Expose static data to the template */
  readonly benefits = BENEFITS;
  readonly statTargets = STAT_TARGETS;

  private readonly destroyRef = inject(DestroyRef);

  // ── Lifecycle ─────────────────────────────────────────────────────────────
  ngAfterViewInit(): void {
    // Small delay so the DOM is painted before animations start
    setTimeout(() => {
      this.animateIn.set(true);
      this.startCounters();
    }, 200);
  }

  // ── Counter animation ─────────────────────────────────────────────────────
  /**
   * Animates each counter from 0 → target using `setInterval`.
   * Duration is ~1 600 ms per counter; each starts with a slight stagger.
   */
  private startCounters(): void {
    const DURATION = 1600; // ms
    const TICK = 16; // ≈ 1 frame at 60 fps
    const steps = Math.ceil(DURATION / TICK);

    STAT_TARGETS.forEach((stat, idx) => {
      let current = 0;
      const increment = stat.value / steps;

      // Stagger each counter start by 150 ms
      setTimeout(() => {
        const id = setInterval(() => {
          current += increment;
          if (current >= stat.value) {
            current = stat.value;
            clearInterval(id);
          }

          // Update the signal immutably
          this.counters.update((arr) => {
            const next = [...arr];
            next[idx] = Math.floor(current);
            return next;
          });
        }, TICK);
      }, idx * 150);
    });
  }
}