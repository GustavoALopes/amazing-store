
CREATE TABLE IF NOT EXISTS product {
    id UUID NOT NULL PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
}