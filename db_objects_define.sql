CREATE SEQUENCE ORDER_SEQUENCE AS INTEGER START WITH 1;
CREATE SEQUENCE CUSTOMER_SEQUENCE AS INTEGER START WITH 1;
CREATE TABLE PUBLIC.CUSTOMERS(
  ID BIGINT NOT NULL PRIMARY KEY,
  NAME VARCHAR(100) NOT NULL,
  PHONE VARCHAR(15) NOT NULL,
  SECNAME VARCHAR(100),
  SURNAME VARCHAR(100) NOT NULL
);
CREATE TABLE PUBLIC.ORDERS(
  ID BIGINT NOT NULL PRIMARY KEY,
  COST NUMERIC(19,2),
  CREATEDATE DATE,
  DESC VARCHAR(255),
  ENDWORKDATE DATE,
  STATUSSTATE INTEGER,
  CUSTOMER BIGINT,
  CONSTRAINT CUSTOMER_FK FOREIGN KEY(CUSTOMER) REFERENCES PUBLIC.CUSTOMERS(ID) ON DELETE RESTRICT
);
CREATE INDEX order_desc_index ON ORDERS (desc)