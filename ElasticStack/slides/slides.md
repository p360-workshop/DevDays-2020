class: center, middle

# Elastic

---
layout: true

.right[![:img Elastic Logo, 20%](logo-elastic-horizontal-color.png)]

---

# Agenda

1. Introduction
1. Elastic Stack
1. Data Ingestion
1. Indexing
1. Data Analysis

---

# Introduction

* What is it?
  * Open source, distributed search and analytics engine
  * Index data of all types
  * Scalable
  * Core of "Elastic Stack" (formerly ELK stack)

* What can I do with it?
  * Index data - Structured or unstructured (possibly using the "L")
  * Search for it (Using the "E")
  * Analyze and Visualize it (Using the "K")
  
Read about it at elastic.co
---
# Elastic Stack (formerly ELK stack)

## E - Elasticsearch
* Heart of the whole operation.  The data lives here.

## L - Logstash
* Used for ingesting logs and other things

## K - Kibana
* Used for analyzing and visualizing data

---
# Data Ingestion

* Elastic Beats - Lightweight data shippers
  * Low resource usage
  * Used for ingesting logs, metrics, many other things
  
* Logstash - Used for ingesting many things - not just logs
  * Use to enrich ingested data
  * Inputs - Data sources include files, logs, http, jdbc, kafka, and on and on
  * Filters - Process and enrich the data (DNS lookups, Geographic information, custom dictionaries)
  * Output - Usually to Elasticsearch, but other places as well
  
---
# Data Ingestion

* Application code
  * Use code to push data into Elasticsearch
  * Many native client libraries including for Java, Python, etc.
  
* Kibana - Some functionality to put data into Elasticsearch

* Any kind of REST client (Postman, curl, etc.)

---
# Indexing / Mapping

* Dynamic Mapping - Automatically created by just indexing a document
  * Elasticsearch guesses everything
  * All properties are mapped and indexed
  * Good way to get a base mapping

* Explicit Mapping - Usually a better option
  * You know your data types
  * You know what data needs to be indexed

---
# Text Analysis

Enables full-text searching

* Tokenization - Breaking text into smaller chunks (tokens)
  * Quick brown fox -> Quick, brown, fox
* Normalization - Lowercasing, stemming, synonyms
    * Quick -> quick
    * foxes -> fox
    * leap -> jump
* Write custom analyzers

Default analyzer works pretty well for basic full-text search

---
# Data Analysis

Use Kibana!

* Web-based UI for Elasticsearch
* Search application logs or other data
* Visualize data with charts, graphs, tables, and more
* Use it for some Elasticsearch management (configure or delete indexes, other settings)

