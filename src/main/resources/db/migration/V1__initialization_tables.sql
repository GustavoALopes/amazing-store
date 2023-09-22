
CREATE TABLE IF NOT EXISTS product (
    id UUID NOT NULL PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_at timestamp NOT NULL,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);