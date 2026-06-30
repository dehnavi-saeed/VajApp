package app.vaj.author.infrastructure.mapper;

import app.vaj.author.application.dto.AuthorResponse;
import app.vaj.author.domain.Author;
import app.vaj.author.infrastructure.persistence.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.lang.reflect.Field;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "type", source = "type", qualifiedByName = "typeToString")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    AuthorResponse toResponse(Author author);

    @Mapping(target = "type", source = "type", qualifiedByName = "typeToString")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    @Mapping(target = "isDeleted", source = ".", qualifiedByName = "isDeletedCheck")
    AuthorEntity toEntity(Author author);

    @Named("typeToString")
    static String typeToString(app.vaj.author.domain.AuthorType type) {
        return type != null ? type.name() : null;
    }

    @Named("statusToString")
    static String statusToString(app.vaj.author.domain.AuthorStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("isDeletedCheck")
    static boolean isDeletedCheck(Author author) {
        return author.isDeleted();
    }
}