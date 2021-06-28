package com.sixfold.flightplanner.service.exception.types;


public class ResourceNotFound extends RuntimeException {

    private String from;
    private String to;

    public ResourceNotFound() {
        super();
    }

    public ResourceNotFound(String from, String to) {
        super(String.format("Route/Flight from %s to %s not found with given params", from, to));
        this.from = from;
        this.to = to;
    }

    public ResourceNotFound(String entityId, Class type) {
        this(entityId, type.getSimpleName());
    }
}
