
## Dash PoC + Data Puddle

A small three-day PoC. An attempt to create a mini "_Data lake_" (Data-puddle) Architecture for data collection and visualisation.
Main focus: Create a solution that enables you to discover your data (sources, parsing and visualisation) requirements and
needs on a small risc/scale, to make an informed choice on your next steps. (supports relation as well as time-series data)

### Elements:
- **TimescaleDB** - Basically PostgreSQL with great optimisations for time-series data. Allowing you to scale from Data-puddle to Data-pond
- **Grafana** - Visualisation tool with great support for all kind of data-sources. The combination with TimescaleDB allows you to basically
  define entire dashboards/visualisations in SQL
- **Data/ETL App** - Small Java/Spring application to discover your data parsing requirements as well allowing the potential
  for easy access to API's (Currently only producing test-data)

### Run:
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
