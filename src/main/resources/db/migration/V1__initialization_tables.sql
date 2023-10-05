
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

CREATE TABLE IF NOT EXISTS client (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birthdate DATE NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_at timestamp NOT NULL,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);