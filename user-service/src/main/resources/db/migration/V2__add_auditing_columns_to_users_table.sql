ALTER TABLE users ADD COLUMN created_at TIMESTAMP;

-- Add createdBy column
ALTER TABLE users ADD COLUMN created_by VARCHAR(255);

-- Add updatedAt column
ALTER TABLE users ADD COLUMN updated_at TIMESTAMP;

-- Add updatedBy column
ALTER TABLE users ADD COLUMN updated_by VARCHAR(255);