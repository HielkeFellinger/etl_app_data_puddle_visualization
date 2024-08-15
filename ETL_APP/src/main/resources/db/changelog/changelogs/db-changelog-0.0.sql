-- liquibase formatted sql

-- changeset hielke:start-001
CREATE TABLE organisation
(
    id          UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT pk_organisation PRIMARY KEY (id)
);
ALTER TABLE organisation
    ADD CONSTRAINT us_organisation_name UNIQUE (name);

-- changeset hielke:start-002
CREATE TABLE contract
(
    id              UUID                        NOT NULL,
    name            VARCHAR(255)                NOT NULL,
    description     VARCHAR(255),
    start_time      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    stop_time       TIMESTAMP WITHOUT TIME ZONE,
    organisation_id UUID                        NOT NULL,
    CONSTRAINT pk_contract PRIMARY KEY (id)
);
ALTER TABLE contract
    ADD CONSTRAINT FK_CONTRACT_ON_ORGANISATION FOREIGN KEY (organisation_id) REFERENCES organisation (id);
ALTER TABLE contract
    ADD CONSTRAINT uc_contract_name UNIQUE (name);

-- changeset hielke:start-003
CREATE TABLE service
(
    id           UUID         NOT NULL,
    name         VARCHAR(255),
    description  VARCHAR(255),
    service_type VARCHAR(255) NOT NULL,
    contract_id  UUID         NOT NULL,
    CONSTRAINT pk_service PRIMARY KEY (id)
);
ALTER TABLE service
    ADD CONSTRAINT FK_SERVICE_ON_CONTRACT FOREIGN KEY (contract_id) REFERENCES contract (id);
ALTER TABLE contract
    ADD CONSTRAINT us_service_name UNIQUE (name);

-- changeset hielke:start-004
CREATE TABLE sensor
(
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(255),
    unit         VARCHAR(255),
    sensor_group VARCHAR(255) NOT NULL,
    CONSTRAINT pk_sensor PRIMARY KEY (name)
);

-- changeset hielke:start-005a
CREATE TABLE sensor_data
(
    id          UUID                        NOT NULL,
    time        TIMESTAMPTZ                 NOT NULL,
    sensor_name VARCHAR(255)                NOT NULL,
    service_id  UUID                        NOT NULL,
    value       DECIMAL,
    CONSTRAINT pk_sensor_data PRIMARY KEY (id, time, sensor_name, service_id)
);
ALTER TABLE sensor_data
    ADD CONSTRAINT FK_SENSOR_DATA_ON_SENSOR_NAME FOREIGN KEY (sensor_name) REFERENCES sensor (name);
ALTER TABLE sensor_data
    ADD CONSTRAINT FK_SENSOR_DATA_ON_SERVICE FOREIGN KEY (service_id) REFERENCES service (id);

-- changeset hielke:start-005b
SELECT create_hypertable('sensor_data', by_range('time'));

-- changeset hielke:start-006
CREATE TABLE kpi
(
    id              UUID                        NOT NULL,
    name            VARCHAR(255)                NOT NULL,
    description     VARCHAR(255),
    start_time      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    stop_time       TIMESTAMP WITHOUT TIME ZONE,
    organisation_id UUID                        NOT NULL,
    CONSTRAINT pk_kpi PRIMARY KEY (id)
);
ALTER TABLE kpi
    ADD CONSTRAINT FK_KPI_ON_ORGANISATION FOREIGN KEY (organisation_id) REFERENCES organisation (id);

-- changeset hielke:start-007a
CREATE TABLE kpi_data
(
    id     UUID                        NOT NULL,
    time   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    value  DECIMAL,
    kpi_id UUID                        NOT NULL,
    CONSTRAINT pk_kpi_data PRIMARY KEY (id, time, kpi_id)
);
ALTER TABLE kpi_data
    ADD CONSTRAINT FK_KPI_DATA_ON_KPI FOREIGN KEY (kpi_id) REFERENCES kpi (id);

-- changeset hielke:start-007b
SELECT create_hypertable('kpi_data', by_range('time'));

-- changeset hielke:start-008
CREATE TABLE sla
(
    id          UUID                        NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    description VARCHAR(255),
    start_time  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    stop_time   TIMESTAMP WITHOUT TIME ZONE,
    service_id  UUID                        NOT NULL,
    CONSTRAINT pk_sla PRIMARY KEY (id)
);
ALTER TABLE sla
    ADD CONSTRAINT FK_SLA_ON_SERVICE FOREIGN KEY (service_id) REFERENCES service (id);

-- changeset hielke:start-009a
CREATE TABLE sla_data
(
    id     UUID                        NOT NULL,
    time   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    value  DECIMAL,
    sla_id UUID                        NOT NULL,
    CONSTRAINT pk_sla_data PRIMARY KEY (id, time, sla_id)
);
ALTER TABLE sla_data
    ADD CONSTRAINT FK_SLA_DATA_ON_SLA FOREIGN KEY (sla_id) REFERENCES sla (id);

-- changeset hielke:start-009b
SELECT create_hypertable('sla_data', by_range('time'));
