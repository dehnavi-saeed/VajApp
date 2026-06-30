-- V021__Create_Categories.sql
CREATE TABLE Categories (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    Description NVARCHAR(1000) NULL,
    DisplayOrder INT NOT NULL DEFAULT 0,
    Status NVARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CreatedBy UNIQUEIDENTIFIER NULL,
    UpdatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    UpdatedBy UNIQUEIDENTIFIER NULL,
    DeletedAt DATETIME2 NULL,
    DeletedBy UNIQUEIDENTIFIER NULL,
    IsDeleted BIT NOT NULL DEFAULT 0,
    Version INT NOT NULL DEFAULT 0,
    CONSTRAINT UQ_Categories_Name WHERE IsDeleted = 0 UNIQUE (Name)
);
CREATE NONCLUSTERED INDEX IX_Categories_Status ON Categories(Status) WHERE IsDeleted = 0;
CREATE NONCLUSTERED INDEX IX_Categories_DisplayOrder ON Categories(DisplayOrder) WHERE IsDeleted = 0;

-- V022__Create_Book_Categories.sql
CREATE TABLE BookCategories (
    BookId UNIQUEIDENTIFIER NOT NULL,
    CategoryId UNIQUEIDENTIFIER NOT NULL,
    AssignedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CONSTRAINT PK_BookCategories PRIMARY KEY (BookId, CategoryId),
    CONSTRAINT FK_BookCategories_Books FOREIGN KEY (BookId) REFERENCES Books(Id),
    CONSTRAINT FK_BookCategories_Categories FOREIGN KEY (CategoryId) REFERENCES Categories(Id)
);
CREATE NONCLUSTERED INDEX IX_BookCategories_CategoryId ON BookCategories(CategoryId);

-- V023__Create_Reading_States.sql (Read-only projection table)
CREATE TABLE ReadingStates (
    Id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    BookId UNIQUEIDENTIFIER NOT NULL,
    UserId UNIQUEIDENTIFIER NOT NULL,
    CurrentPage INT NOT NULL DEFAULT 0,
    TotalPagesRead INT NOT NULL DEFAULT 0,
    ProgressPercentage DECIMAL(5,2) NOT NULL DEFAULT 0,
    TotalReadingMinutes INT NOT NULL DEFAULT 0,
    LastSessionId UNIQUEIDENTIFIER NULL,
    LastReadAt DATETIME2 NULL,
    EstimatedFinishDate DATETIME2 NULL,
    Status NVARCHAR(20) NOT NULL DEFAULT 'NOT_STARTED',
    UpdatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    CONSTRAINT UQ_ReadingStates_BookId UNIQUE (BookId),
    CONSTRAINT FK_ReadingStates_Books FOREIGN KEY (BookId) REFERENCES Books(Id)
);
CREATE NONCLUSTERED INDEX IX_ReadingStates_UserId ON ReadingStates(UserId);
CREATE NONCLUSTERED INDEX IX_ReadingStates_Status ON ReadingStates(Status);