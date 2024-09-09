# Team name: Grapes

# Team Members
- Anoshenko Daniil
- Rogoza Kirill
- Sergey Rogachev
- Pham Manh 
- Allan Pravira
  
# ETL
ETL service for private data cleansing
Data quality and availability is important for applications running in the framework.
The framework defines so-called data modules stored on S3 or in a RDBMS. 
A data module is described by a json-ld compatible metadata, a collection protocol and the versioned data. 
The ETL service would extract data from internal corporate systems, cleanse, merge, anonymize, transform the data and upload it to a new version of a data module. 
Then the module can be used by machine learning applications deployed on the framework.

# Stakeholders
 - ML projects
 - DevOps Platforms
 - Companies that overpassed 3rd Azure level model
 - Database Administrators
 - Data Scientists
 - Project owners

# Feature List
## Exctract recieved data

This feature involves retrieving data from various internal corporate systems, such as databases (RDBMS) and cloud storage (S3). The extraction process ensures that all relevant data, along with its associated metadata, is collected and prepared for further processing.
### Implementation Rules: 
- Set up secure connections to various data sources
- Maintain data versions for traceability
- Collect Metadata
  
## Cleanse recieved data

Data cleansing involves detecting and correcting errors or inconsistencies in the extracted data. This feature addresses common data quality issues such as missing values, duplicates, and outliers. 
### Implementation Rules:
  - Handle missing values, duplicates, and outliers
  - Track cleansing operations

 ## Merge recieved data

This feature handles the integration of data from multiple sources, combining them into a unified dataset. The merging process resolves conflicts, aligns schemas, and ensures that data from different systems can work together seamlessly.
### Implementation Rules:  
  - Apply rules to handle conflicting data
  - Track data source origins

## Anonymize recieved data

Anonymization is the process of protecting sensitive information within the dataset. This feature ensures that personal or confidential data is anonymized to comply with privacy regulations and corporate policies.  
### Implementation Rules:
  - Mask or encrypt sensitive data
  - Ensure data privacy regulation compliance

## Transform recieved data

Data transformation involves converting the cleansed and merged data into a format suitable for specific applications. This feature includes tasks such as data enrichment, normalization, and schema mapping. The transformed data is structured in a way that makes it easy to load.
### Implementation Rules:
  - Convert data to the required format
  - Contract compliance
  - Log all transformations

## Upload new data for future ML integration

After processing, the transformed data is uploaded as a new version of the data module, making it available for future use. This feature ensures that the data is stored efficiently, with proper version control, and is accessible to machine learning applications deployed on the framework.
### Implementation Rules:
  -  Upload as new data module versions
  -  Ensure only authorized users access data

# Constrains
- Data Privacy and Compliance
- Scalability
- Data Volume
- Integration with Existing Systems
- Resource Constraints
- Security Requirements


# Non-functional requirements
- The system must have high availability and include fault-tolerance mechanisms for 4xx and 5xx errors
- The system must be compatible with other systems of MLOps framework
- The system must comply with Federal Law No. 152 of the Russian Federation
- The system must have a logging system for transparency of its all operational stages: Exctract > Cleanse > Merge > Anonymize > Transform > Upload
- The system must work with datasets of fixed preset dimension (limit in GB)



