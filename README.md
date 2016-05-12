# Asakusa Framework Application Examples
This repository provides a number of examples to show usage of [Asakusa Framework](https://github.com/asakusafw/asakusafw) features.

This includes following application projects:

## Basic exmaples

### [Asakusa on M<sup>3</sup>BP](https://github.com/asakusafw/asakusafw-m3bp)

* [`example-basic-m3bp`](example-basic-m3bp) - A basic batch application using [Asakusa on M<sup>3</sup>BP](https://github.com/asakusafw/asakusafw-m3bp)

## Using various components

### Direct I/O
* [`example-directio-csv`](example-directio-csv) - Demonstrates the use of Direct I/O CSV
* [`example-directio-hive`](example-directio-hive) - Demonstrates the use of Direct I/O Hive
* [`example-directio-tsv`](example-directio-tsv) - Demonstrates the use of Direct I/O TSV with Gzip codec

### WindGate
* [`example-windgate-csv`](example-windgate-csv) - Demonstrates the use of WindGate CSV
* [`example-windgate-jdbc`](example-windgate-jdbc) - Demonstrates the use of WindGate JDBC (configured for PostgreSQL)

### ThunderGate
ThunderGate supportes only Asakusa on MapReduce.

* [`example-thundergate`](example-thundergate) - Demonstrates the use of ThunderGate

## Others
* [`example-multiproject`](example-multiproject) - Demonstrates the configuration of multi-project (DMDL/Application/Testcase)
* [`example-summarize-sales`](example-summarize-sales) - A simple application of aggregating multiple conditions in parallel

