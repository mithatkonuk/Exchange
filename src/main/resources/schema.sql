CREATE TABLE EXCHANGE_CONVERSION
(
    transaction_id    UUID NOT NULL,
    base              VARCHAR(5),
    symbol            VARCHAR(5),
    range             DOUBLE not null,
    amount            DOUBLE    DEFAULT 0.0,
    conversion        DOUBLE    DEFAULT 0.0,
    date_created      DATE      DEFAULT CURRENT_DATE,
    timestamp_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (transaction_id)
);

CREATE INDEX CONVERSION_DATE ON EXCHANGE_CONVERSION (date_created);
