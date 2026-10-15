package workshop.airport.model;

public class Shipment {
    private String id;
    private String type;
    private String destination;
    private String route;
    private String flightId;

    public Shipment() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }
    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }
}
