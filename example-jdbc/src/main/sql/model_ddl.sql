/**********************************/
/* テーブル名: 売上明細           */
/**********************************/
DROP TABLE IF EXISTS SALES_DETAIL;
CREATE TABLE SALES_DETAIL(
    SID                 BIGSERIAL PRIMARY KEY,
    SALES_DATE_TIME     TIMESTAMP NOT NULL,
    STORE_CODE          VARCHAR(50) NOT NULL,
    ITEM_CODE           VARCHAR(50) NOT NULL,
    AMOUNT              BIGINT NOT NULL,
    UNIT_SELLING_PRICE  BIGINT NULL,
    SELLING_PRICE       BIGINT NULL
);

/**********************************/
/* テーブル名: 店舗マスタ         */
/**********************************/
DROP TABLE IF EXISTS STORE_INFO;
CREATE TABLE STORE_INFO(
    SID                 BIGSERIAL PRIMARY KEY,
    STORE_CODE          VARCHAR(50) NOT NULL,
    STORE_NAME          VARCHAR(50) NULL
);

/**********************************/
/* テーブル名: 商品マスタ         */
/**********************************/
DROP TABLE IF EXISTS ITEM_INFO;
CREATE TABLE ITEM_INFO(
    SID                 BIGSERIAL PRIMARY KEY,
    ITEM_CODE           VARCHAR(50) NOT NULL,
    ITEM_NAME           VARCHAR(50) NULL,
    DEPARTMENT_CODE     VARCHAR(50) NULL,
    DEPARTMENT_NAME     VARCHAR(50) NULL,
    CATEGORY_CODE       VARCHAR(50) NULL,
    CATEGORY_NAME       VARCHAR(50) NULL,
    UNIT_SELLING_PRICE  BIGINT NULL,
    REGISTERED_DATE     DATE NULL,
    BEGIN_DATE          DATE NULL,
    END_DATE            DATE NULL
);

/**********************************/
/* テーブル名: カテゴリ別売上集計 */
/**********************************/
DROP TABLE IF EXISTS CATEGORY_SUMMARY;
CREATE TABLE CATEGORY_SUMMARY(
    SID                 BIGSERIAL PRIMARY KEY,
    CATEGORY_CODE       VARCHAR(50) NULL,
    AMOUNT              INTEGER NOT NULL,
    SELLING_PRICE       INTEGER NULL
);

/**********************************/
/* テーブル名: エラー情報         */
/**********************************/
DROP TABLE IF EXISTS ERROR_RECORD;
CREATE TABLE ERROR_RECORD(
    SID                 BIGSERIAL PRIMARY KEY,
    SALES_DATE_TIME     TIMESTAMP NOT NULL,
    STORE_CODE          VARCHAR(50) NOT NULL,
    ITEM_CODE           VARCHAR(50) NOT NULL,
    MESSAGE             VARCHAR(1024) NULL
);
