# ETL
ETL service for private data cleansing
Data quality and availability is important for applications running in the framework.
The framework defines so-called data modules stored on S3 or in a RDBMS. 
A data module is described by a json-ld compatible metadata, a collection protocol and the versioned data. 
The ETL service would extract data from internal corporate systems, cleanse, merge, anonymize, transform the data and upload it to a new version of a data module. 
Then the module can be used by machine learning applications deployed on the framework.
