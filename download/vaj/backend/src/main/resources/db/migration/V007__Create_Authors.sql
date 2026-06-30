-- V007__Create_Authors.sql
-- Re-create Authors table with full DDD aggregate fields (Type, Status, soft-delete, audit)
DROP TABLE IF EXISTS BookAuthors;
DROP TABLE IF EXISTS Authors;

CREATE TABLE Authors (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    Name NVARCHAR(200) NOT NULL,
    Bio NVARCHAR(5000) NULL,
    Type NVARCHAR(50) NOT NULL DEFAULT 'PERSON',
    Status NVARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    UpdatedAt DATETIME2 NULL,
    DeletedAt DATETIME2 NULL,
    IsDeleted BIT NOT NULL DEFAULT 0,
    Version ROWVERSION NOT NULL,
    CONSTRAINT UQ_Authors_Name WHERE IsDeleted = 0 UNIQUE (Name)
);
CREATE NONCLUSTERED INDEX IX_Authors_Name ON Authors(Name) WHERE IsDeleted = 0;

-- Re-create BookAuthors junction table
CREATE TABLE BookAuthors (
    BookId UNIQUEIDENTIFIER NOT NULL,
    AuthorId UNIQUEIDENTIFIER NOT NULL,
    CONSTRAINT PK_BookAuthors PRIMARY KEY (BookId, AuthorId),
    CONSTRAINT FK_BookAuthors_Books FOREIGN KEY (BookId) REFERENCES Books(Id),
    CONSTRAINT FK_BookAuthors_Authors FOREIGN KEY (AuthorId) REFERENCES Authors(Id)
);