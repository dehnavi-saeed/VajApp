-- V009__Add_Publisher_To_Books.sql
ALTER TABLE Books ADD PublisherId UNIQUEIDENTIFIER NULL;
ALTER TABLE Books ADD CONSTRAINT FK_Books_Publishers FOREIGN KEY (PublisherId) REFERENCES Publishers(Id);
CREATE NONCLUSTERED INDEX IX_Books_PublisherId ON Books(PublisherId) WHERE IsDeleted = 0;

-- Re-create BookAuthors junction table with Id and CreatedAt
DROP TABLE IF EXISTS BookAuthors;
CREATE TABLE BookAuthors (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWID(),
    BookId UNIQUEIDENTIFIER NOT NULL,
    AuthorId UNIQUEIDENTIFIER NOT NULL,
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CONSTRAINT FK_BookAuthors_Books FOREIGN KEY (BookId) REFERENCES Books(Id),
    CONSTRAINT FK_BookAuthors_Authors FOREIGN KEY (AuthorId) REFERENCES Authors(Id),
    CONSTRAINT UQ_BookAuthors_Book_Author UNIQUE (BookId, AuthorId)
);