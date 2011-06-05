DROP TABLE IF EXISTS ITEM_INFO;
CREATE TABLE ITEM_INFO (
    -- System columns for BulkLoader
    SID               BIGINT     PRIMARY KEY AUTO_INCREMENT,
    VERSION_NO BIGINT            NULL,
    RGST_DATETIME DATETIME       NULL,
    UPDT_DATETIME DATETIME       NULL,
    
    -- Application columns
    CODE   BIGINT        NOT NULL,
    NAME   VARCHAR(255)  NOT NULL,
    PRICE  INT           NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS ORDER_DETAIL;
CREATE TABLE ORDER_DETAIL (
    -- System columns for BulkLoader
    SID               BIGINT     PRIMARY KEY AUTO_INCREMENT,
    VERSION_NO BIGINT            NULL,
    RGST_DATETIME DATETIME        NULL,
    UPDT_DATETIME DATETIME        NULL,
    
    -- Application columns
    ORDER_ID   BIGINT        NOT NULL,
    ITEM_CODE  BIGINT        NOT NULL,
    STATUS     VARCHAR(255)  NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS ORDER_AMOUNT;
CREATE TABLE ORDER_AMOUNT (
    -- System columns for BulkLoader
    SID               BIGINT     PRIMARY KEY AUTO_INCREMENT,
    VERSION_NO BIGINT            NULL,
    RGST_DATETIME DATETIME        NULL,
    UPDT_DATETIME DATETIME        NULL,
    
    -- Application columns
    ORDER_ID   BIGINT        NOT NULL,
    AMOUNT     BIGINT        NOT NULL
) ENGINE=InnoDB;

DROP VIEW IF EXISTS JOIN_ORDER;
CREATE VIEW JOIN_ORDER AS
SELECT
    ORDER_ID,
    ITEM_CODE,
    PRICE
FROM
    ORDER_DETAIL, ITEM_INFO
WHERE
    ORDER_DETAIL.ITEM_CODE = ITEM_INFO.CODE;

DROP VIEW IF EXISTS SUM_ORDER;
CREATE VIEW SUM_ORDER AS
SELECT
    ORDER_ID,
    SUM(PRICE) AS AMOUNT
FROM
    JOIN_ORDER
GROUP BY
    ORDER_ID;


-- test data
INSERT INTO ITEM_INFO (CODE, NAME, PRICE) VALUES (1, 'example1', 100);
INSERT INTO ITEM_INFO (CODE, NAME, PRICE) VALUES (2, 'example2', 200);
INSERT INTO ITEM_INFO (CODE, NAME, PRICE) VALUES (3, 'example3', 500);

-- ORDER_AMOUNT(ORDER_ID=100, AMOUNT=100)
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (100, 1);

-- ORDER_AMOUNT(ORDER_ID=101, AMOUNT=800)
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (101, 1);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (101, 2);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (101, 3);

-- ORDER_AMOUNT(ORDER_ID=102, AMOUNT=2000)
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (102, 1);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (102, 2);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (102, 2);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (102, 3);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (102, 3);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (102, 3);

-- erroneous
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_CODE) VALUES (999, 100);