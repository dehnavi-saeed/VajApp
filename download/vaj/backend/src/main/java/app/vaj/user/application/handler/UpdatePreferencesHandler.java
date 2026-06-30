package app.vaj.user.application.handler;

import app.vaj.user.application.command.UpdatePreferencesCommand;
import app.vaj.user.application.dto.PreferencesResponse;
import app.vaj.user.domain.UserPreferences;
import app.vaj.user.domain.repository.UserPreferencesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdatePreferencesHandler {

    private final UserPreferencesRepository preferencesRepository;

    public UpdatePreferencesHandler(UserPreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    @Transactional
    public PreferencesResponse handle(UUID userId, UpdatePreferencesCommand command) {
        UserPreferences prefs = preferencesRepository.findByUserId(userId)
                .orElseGet(() -> UserPreferences.create(UUID.randomUUID(), userId, command.timezone()));

        if (command.language() != null) {
            prefs.updateLanguage(command.language());
        }
        prefs.updateTimezone(command.timezone());
        if (command.theme() != null) {
            prefs.updateTheme(command.theme());
        }

        preferencesRepository.save(prefs);

        return new PreferencesResponse(
                prefs.getId(), prefs.getUserId(), prefs.getLanguage(),
                prefs.getTimezone(), prefs.getTheme()
        );
    }
}