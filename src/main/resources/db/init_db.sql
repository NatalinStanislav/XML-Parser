DROP TABLE IF EXISTS companies;
CREATE TABLE companies
(
    id               VARCHAR     PRIMARY KEY    NOT NULL,
    name             VARCHAR                    NOT NULL,
    location         VARCHAR                    NOT NULL,

    CONSTRAINT company_duplicate UNIQUE (id, name, location)
);

