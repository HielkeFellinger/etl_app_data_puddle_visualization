
## Dash PoC + Data Puddle

PoC. An attempt to create a mini "Datalake" Architecture for data collection and visualisation. 

- TimescaleDB
- Grafana
- Data/ETL App

Run:
1. First start the container/service `timescaledb` from the `compose.yml` file
2. Run the application ETL_APP: `./gradlew bootRun`
    - Liquibase will ensure the right state of `timescaledb`
    - The App will, add dummy data via the pipelines to the right sensors
        - Controller have `@Scheduled` methods
3. Start the container/service `grafana` from the `compose.yml` file
    - This will start with the mounted `./grafana` dir to autoload sources and dashboard
    - Dynamic Coupling is achieved via the `./grafana/**/*.yml` files and the `"datasource": "mytimescaledb",` entries in the `./grafana/dashboards/*.json`
4. Dashboard Customization;
    - re-import the *.json file under another name and uid
      - Warning: Changing this will break Data/Panel/Dashboard links; they will still look to the old/default dashboard.
