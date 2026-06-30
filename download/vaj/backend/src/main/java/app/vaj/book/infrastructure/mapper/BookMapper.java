package app.vaj.book.infrastructure.mapper;

import app.vaj.book.application.dto.BookResponse;
import app.vaj.book.domain.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponse toResponse(Book book);
    List<BookResponse> toResponseList(List<Book> books);
}