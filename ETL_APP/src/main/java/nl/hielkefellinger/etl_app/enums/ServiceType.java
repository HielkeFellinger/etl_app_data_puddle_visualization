package nl.hielkefellinger.etl_app.enums;

public enum ServiceType {
    HOSTING, SUPPORT, MAINTENANCE;

    public static ServiceType getServiceType(String serviceType) {
        return ServiceType.valueOf(serviceType.toUpperCase());
    }
}
