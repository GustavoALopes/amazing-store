
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
    email VARCHAR(100) NOT NULL UNIQUE,
    created_by VARCHAR(100) NOT NULL,
    created_at timestamp NOT NULL,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS address (
    id UUID NOT NULL PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES client(id),
    country VARCHAR(3) NOT NULL,
    state VARCHAR(2) NOT NULL,
    city VARCHAR(100) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    street VARCHAR(100) NOT NULL,
    number VARCHAR(10) NOT NULL,
    zip_code VARCHAR(11) NOT NULL,
    details VARCHAR(100),
    is_default boolean default false,
    created_by VARCHAR(100) NOT NULL,
    created_at timestamp NOT NULL,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);