/***************************************************/
/* src/test/example-dataset ディレクトリを /tmp に */
/* コピーしてからこのSQLを実行してください。       */
/*                                                 */
/* ex.                                             */
/* cp -r src/test/example-dataset /tmp             */
/* psql -f src/test/sql/import-example-dataset.sql */
/***************************************************/
TRUNCATE TABLE SALES_DETAIL;
COPY SALES_DETAIL(
    SALES_DETAIL_TIME
    ,STORE_CODE
    ,ITEM_CODE
    ,AMOUNT
    ,UNIT_SELLING_PRICE
    ,SELLING_PRICE
) FROM '/tmp/example-dataset/sales/2011-04-01.csv' WITH csv HEADER;

TRUNCATE TABLE STORE_INFO;
COPY STORE_INFO(
    STORE_CODE
    ,STORE_NAME
) FROM '/tmp/example-dataset/master/store_info.csv' WITH csv HEADER;

TRUNCATE TABLE ITEM_INFO;
COPY ITEM_INFO(
    ITEM_CODE
    ,ITEM_NAME
    ,DEPARTMENT_CODE
    ,DEPARTMENT_NAME
    ,CATEGORY_CODE
    ,CATEGORY_NAME
    ,UNIT_SELLING_PRICE
    ,REGISTERED_DATE
    ,BEGIN_DATE
    ,END_DATE
) FROM '/tmp/example-dataset/master/item_info.csv' WITH csv HEADER;
