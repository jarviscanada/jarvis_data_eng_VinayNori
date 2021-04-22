# Linux Cluster Monitoring Agent
## Introduction
The Linux Cluster Monitoring Agent project was designed to record the hardware specifications of each node and monitor node resource usages (e.g. CPU/Memory) of linux nodes within a cluster. This project is primarily meant for Linux Cluster Administrators.The hardware specifications are noted once whereas the memory usage scripts is run automatically every minute using Crontab. The technologies used int this project are Docker, Git, Bash, SQL and Google Cloud Platform.

## Quick Start
- Psql instance is started using psql_docker.sh:
 ```
 bash psql_docker.sh start
 psql -h hostname -U username -p 5432
 ```
- Tables are created using ddl.sql

  ```
    psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
  ```
- Run host_info.sh to insert hardware specifications
  ```
    bash host_info.sh psql_host psql_port db_name psql_user psql_password
  ```
- Run host_usage.sh to insert memory usage
  ```
    bash host_usage.sh psql_host psql_port db_name psql_user psql_password
  ```
- Crontab Setup
  ```
    crontab -e
    //Type this in file
    * * * * * bash <pwd>/host_usage.sh psql_host psql_port db_name psql_user psql_password > /tmp/host_usage.log
    crontab -l
  ```
## Implementation
1. The psql_docker.sh script was set up in order to create a psql container. 
2. Creation & execution of ddl.sql script to create the tables. 
3. Creation of the bash scripts - host_info.sh and host_usage.sh. 
4. Insertion of the script output data into the postgre database.
5. Crontab was set up to run host_usage.sh every minute.
## Architechture
![arch_img](https://user-images.githubusercontent.com/28710310/115005742-0bad8380-9e5d-11eb-836d-4727fc17d7f0.png)
## Scripts
- A psql instance is installed on a node labeled as Monitoring Agent. This psql instance is used to store persistent data collected from other nodes within the cluster.
  * psql_docker.sh : Bash script to create a psql container using Docker to host the PostgreSQL database.
- A bash agent is installed on every node/server within the cluster. This agent consists of two scripts
  * host_info.sh : Obtain the hardware specifications and inserts them into the psql instance. This script is only run once at the installation time.
  * host_usage.sh : Collects the current host usage data then inserts them into the database. It will be scheduled by the crontab command to run every minute.
- ddl.sql : SQL script to create tables
- queries.sql : SQL script to run queries on the tables to detect hardware failure in the cluster
## Database Modeling
- host_info
Stores Hardware Specific data

|Column                |Type             | Description                     |Example                       |
|--------------------|---------------|-------------------------------|-----------------------------|
|id                  |`serial`         |`primary key` for the host info table | 1
|hostname            |`unique varchar` |`fully qualified name` for each host system |jrvs-remote-desktop-centos7.us-east1-c.c.prefab-mapper-303519.internal |
|cpu_number          |`integer`        |number of cpu(s) on system  |2 |
|cpu_architecture    |`varchar`        |architecture of the cpu     |x86_64 |
|cpu_model           |`varchar`        |model of the cpu            |AMD EPYC 7B12 |
|cpu_mhz             |`real`           |speed of cpu in `mhz`               |2249.998046875
|L2_cache            |`integer`        |storage of L2 cache in `kb`         |512 |
|total_mem           |`integer`        |total memory on host system in `kb` |7492120 |
|timestamp           |`timestamp`      |recorded time in `UTC`              |Apr 15, 2021, 1:17:57 PM |

- host_usage
Stores Linux Resource Data Usage

|Column         |Type        |Description                  		|Example                      |
|---------------|------------|------------------------------------------|-----------------------------|
|timestamp      |`timestamp` |recorded time in `UTC`             	|Apr 15, 2021, 2:07:45 PM      |
|host_id        |`serial`    |`foreign key` for the host info table 	|1 			      |
|memory_free    |`integer`   |free memory in `mb` 			|3811920                      |
|cpu_idle       |`integer`   |idle cpu usage in `percentage` 		|96                           |
|cpu_kernel     |`integer`   |kernel cpu usage in `percentage` 		|0                            |
|disk_io        |`integer`   |number of disk I/O 			|1                 	      |
|disk_available |`integer`   |root directory available memory 		|25905 			      |

## Tests
1. Bash Scripts
Tests were done manually for all bash scripts. Including creating, starting, and stopping the `psql` instance, and inserting values into `host_usage` and `host_info` tables. 
2. SQL Queries
Query results were verified using data created by developer.

## Improvements
- Scheduling crontab jobs with more flexible intervals 1-minute, 1 day, etc
- Further implementation of queries to answer more business questions
- Set up Crontab automation for hardware specifications



