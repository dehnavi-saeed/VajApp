-- V024__Align_Entities_With_Migrations.sql
-- Fixes column mismatches between JPA entities and database schema

-- ============================================================
-- 1. AUTHORS TABLE: Add missing columns (Type, Status, soft-delete, Version)
-- ============================================================
ALTER TABLE Authors ADD Type NVARCHAR(50) NOT NULL DEFAULT 'PERSON';
ALTER TABLE Authors ADD Status NVARCHAR(50) NOT NULL DEFAULT 'ACTIVE';
ALTER TABLE Authors ADD DeletedAt DATETIME2 NULL;
ALTER TABLE Authors ADD IsDeleted BIT NOT NULL DEFAULT 0;
ALTER TABLE Authors ADD Version INT NOT NULL DEFAULT 0;

CREATE NONCLUSTERED INDEX IX_Authors_Status ON Authors(Status) WHERE IsDeleted = 0;
CREATE NONCLUSTERED INDEX IX_Authors_Name_Active ON Authors(Name) WHERE IsDeleted = 0;
DROP INDEX IX_Authors_Name ON Authors;

-- ============================================================
-- 2. PUBLISHERS TABLE: Add missing columns (Website, Country, Status, audit, soft-delete, Version)
-- ============================================================
ALTER TABLE Publishers ADD Website NVARCHAR(500) NULL;
ALTER TABLE Publishers ADD Country NVARCHAR(10) NULL;
ALTER TABLE Publishers ADD Status NVARCHAR(50) NOT NULL DEFAULT 'ACTIVE';
ALTER TABLE Publishers ADD CreatedBy UNIQUEIDENTIFIER NULL;
ALTER TABLE Publishers ADD UpdatedBy UNIQUEIDENTIFIER NULL;
ALTER TABLE Publishers ADD DeletedAt DATETIME2 NULL;
ALTER TABLE Publishers ADD DeletedBy UNIQUEIDENTIFIER NULL;
ALTER TABLE Publishers ADD IsDeleted BIT NOT NULL DEFAULT 0;
ALTER TABLE Publishers ADD Version INT NOT NULL DEFAULT 0;

CREATE NONCLUSTERED INDEX IX_Publishers_Status ON Publishers(Status) WHERE IsDeleted = 0;
CREATE NONCLUSTERED INDEX IX_Publishers_Name_Active ON Publishers(Name) WHERE IsDeleted = 0;
DROP INDEX IX_Publishers_Name ON Publishers;

-- ============================================================
-- 3. BOOKS TABLE: Add PublisherId column
-- ============================================================
ALTER TABLE Books ADD PublisherId UNIQUEIDENTIFIER NULL;

-- ============================================================
-- 4. Fix ROWVERSION -> INT on 7 tables
--    JPA @Version Long cannot map to SQL Server ROWVERSION (binary).
--    SQL Server allows dropping ROWVERSION columns.
-- ============================================================
ALTER TABLE ReadingSessions DROP COLUMN Version;
ALTER TABLE ReadingSessions ADD Version INT NOT NULL DEFAULT 0;

ALTER TABLE Bookmarks DROP COLUMN Version;
ALTER TABLE Bookmarks ADD Version INT NOT NULL DEFAULT 0;

ALTER TABLE ReadingGoals DROP COLUMN Version;
ALTER TABLE ReadingGoals ADD Version INT NOT NULL DEFAULT 0;

ALTER TABLE Highlights DROP COLUMN Version;
ALTER TABLE Highlights ADD Version INT NOT NULL DEFAULT 0;

ALTER TABLE KnowledgeNotes DROP COLUMN Version;
ALTER TABLE KnowledgeNotes ADD Version INT NOT NULL DEFAULT 0;

ALTER TABLE Collections DROP COLUMN Version;
ALTER TABLE Collections ADD Version INT NOT NULL DEFAULT 0;

ALTER TABLE Tags DROP COLUMN Version;
ALTER TABLE Tags ADD Version INT NOT NULL DEFAULT 0;

-- ============================================================
-- 5. Fix ReadingGoals.EndDate nullability (entity allows null)
-- ============================================================
ALTER TABLE ReadingGoals ALTER COLUMN EndDate DATETIME2 NULL;

-- ============================================================
-- 6. Add missing foreign key constraints (WITH NOCHECK for existing data)
-- ============================================================
ALTER TABLE ReadingSessions WITH NOCHECK ADD CONSTRAINT FK_ReadingSessions_Books FOREIGN KEY (BookId) REFERENCES Books(Id);
ALTER TABLE ReadingSessions WITH NOCHECK ADD CONSTRAINT FK_ReadingSessions_Users FOREIGN KEY (UserId) REFERENCES Users(Id);

ALTER TABLE Bookmarks WITH NOCHECK ADD CONSTRAINT FK_Bookmarks_Books FOREIGN KEY (BookId) REFERENCES Books(Id);
ALTER TABLE Bookmarks WITH NOCHECK ADD CONSTRAINT FK_Bookmarks_Users FOREIGN KEY (UserId) REFERENCES Users(Id);

ALTER TABLE ReadingGoals WITH NOCHECK ADD CONSTRAINT FK_ReadingGoals_Users FOREIGN KEY (UserId) REFERENCES Users(Id);

ALTER TABLE Highlights WITH NOCHECK ADD CONSTRAINT FK_Highlights_Books FOREIGN KEY (BookId) REFERENCES Books(Id);
ALTER TABLE Highlights WITH NOCHECK ADD CONSTRAINT FK_Highlights_Users FOREIGN KEY (UserId) REFERENCES Users(Id);

ALTER TABLE KnowledgeNotes WITH NOCHECK ADD CONSTRAINT FK_KnowledgeNotes_Users FOREIGN KEY (UserId) REFERENCES Users(Id);

ALTER TABLE Collections WITH NOCHECK ADD CONSTRAINT FK_Collections_Users FOREIGN KEY (UserId) REFERENCES Users(Id);

ALTER TABLE Tags WITH NOCHECK ADD CONSTRAINT FK_Tags_Users FOREIGN KEY (UserId) REFERENCES Users(Id);

ALTER TABLE ReadingStates WITH NOCHECK ADD CONSTRAINT FK_ReadingStates_Users FOREIGN KEY (UserId) REFERENCES Users(Id);