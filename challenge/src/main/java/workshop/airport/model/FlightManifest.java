package workshop.airport.model;

import java.util.List;

public class FlightManifest {
    private String flightId;
    private String aircraftType;
    private String origin;
    private List<Shipment> items;

    public FlightManifest() {}

    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }
    public String getAircraftType() { return aircraftType; }
    public void setAircraftType(String aircraftType) { this.aircraftType = aircraftType; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public List<Shipment> getItems() { return items; }
    public void setItems(List<Shipment> items) { this.items = items; }
}
