-- Switch to host_agent database
\c host_agent
CREATE TABLE IF NOT EXISTS PUBLIC.host_info
(
    id               SERIAL NOT NULL,
    hostname         VARCHAR(110) NOT NULL,
    cpu_number       SMALLINT NOT NULL,
    cpu_architecture VARCHAR NOT NULL,
    cpu_model        TEXT NOT NULL,
    cpu_mhz          REAL NOT NULL,
    L2_cache         INTEGER NOT NULL,
    total_mem        INTEGER NOT NULL,
    "timestamp"      TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    UNIQUE(hostname)

);

-- DML
-- INSERT statement
INSERT INTO host_info (1, "spry-framework-236416.internal",1,"X86_64","Intel(R) Xeon(R) CPU @ 2.30GHz",2300.000,256,601324,"2019-05-29 17:49:53");

CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
(
    "timestamp"     TIMESTAMP NOT NULL,
    host_id         SERIAL NOT NULL,
    memory_free     INTEGER NOT NULL,
    cpu_idle        REAL NOT NULL,
    cpu_kernel      REAL NOT NULL,
    disk_io         INTEGER NOT NULL,
    disk_available  INTEGER NOT NULL,

    FOREIGN KEY (host_id) REFERENCES Public.host_info (id)
);

-- DML
-- INSERT statement
INSERT INTO host_usage ("2019-05-29 16:53:28", 1,256,95,0,0,31220);