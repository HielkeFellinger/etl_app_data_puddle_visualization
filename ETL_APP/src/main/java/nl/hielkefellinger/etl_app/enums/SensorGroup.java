package nl.hielkefellinger.etl_app.enums;

public enum SensorGroup {
    QUALITY, SECURITY, ACCOUNT, FINANCE, ITSM;

    public static SensorGroup getSensorGroup(String sensorGroup) {
        return SensorGroup.valueOf(sensorGroup.toUpperCase());
    }
}
