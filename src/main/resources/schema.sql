CREATE TABLE exchange_transaction_table
(
    transaction_id    VARCHAR(255) NOT NULL,
    base              VARCHAR(5),
    symbol            VARCHAR(5),
    range             DOUBLE not null,
    amount            DOUBLE    DEFAULT 0.0,
    conversion        DOUBLE    DEFAULT 0.0,
    date_created      DATE      DEFAULT CURRENT_DATE,
    timestamp_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (transaction_id)
);

CREATE INDEX CONVERSION_DATE ON exchange_transaction_table (date_created);
