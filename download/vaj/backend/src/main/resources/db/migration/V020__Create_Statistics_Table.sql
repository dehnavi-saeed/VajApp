-- V020__Create_Statistics_Table.sql
CREATE TABLE ReadingStatistics (
    Id UNIQUEIDENTIFIER NOT NULL DEFAULT NEWID(),
    UserId UNIQUEIDENTIFIER NOT NULL,
    LibraryId UNIQUEIDENTIFIER NULL,
    StatDate DATE NOT NULL,
    PagesRead INT NOT NULL DEFAULT 0,
    MinutesRead INT NOT NULL DEFAULT 0,
    BooksCompleted INT NOT NULL DEFAULT 0,
    SessionsCount INT NOT NULL DEFAULT 0,
    NotesCreated INT NOT NULL DEFAULT 0,
    HighlightsCreated INT NOT NULL DEFAULT 0,
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CONSTRAINT PK_ReadingStatistics PRIMARY KEY (Id),
    CONSTRAINT FK_ReadingStatistics_Users FOREIGN KEY (UserId) REFERENCES Users(Id),
    CONSTRAINT UQ_ReadingStatistics_Date UNIQUE (UserId, StatDate)
);
CREATE INDEX IX_ReadingStatistics_UserId_Date ON ReadingStatistics (UserId, StatDate);