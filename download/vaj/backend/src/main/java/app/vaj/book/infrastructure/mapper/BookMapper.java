package app.vaj.book.infrastructure.mapper;

import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "format", source = "format", qualifiedByName = "formatToString")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    @Mapping(target = "authorIds", ignore = true)
    @Mapping(target = "publisherId", ignore = true)
    BookResponse toResponse(Book book);

    List<BookResponse> toResponseList(List<Book> books);

    @Named("formatToString")
    default String formatToString(app.vaj.book.domain.BookFormat format) {
        return format != null ? format.name() : null;
    }

    @Named("statusToString")
    default String statusToString(app.vaj.book.domain.ReadingStatus status) {
        return status != null ? status.name() : null;
    }
}