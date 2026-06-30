package app.vaj.user.application.handler;

import app.vaj.user.application.command.UpdateProfileCommand;
import app.vaj.user.application.dto.ProfileResponse;
import app.vaj.user.domain.UserProfile;
import app.vaj.user.domain.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateProfileHandler {

    private final UserProfileRepository profileRepository;

    public UpdateProfileHandler(UserProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Transactional
    public ProfileResponse handle(UUID userId, UpdateProfileCommand command) {
        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> UserProfile.create(UUID.randomUUID(), userId));

        if (command.displayName() != null) {
            profile.updateDisplayName(command.displayName());
        }
        if (command.bio() != null) {
            profile.updateBio(command.bio());
        }

        profileRepository.save(profile);

        return new ProfileResponse(
                profile.getId(), profile.getUserId(), profile.getDisplayName(),
                profile.getBio(), profile.getAvatarUrl(), profile.getCreatedAt()
        );
    }
}