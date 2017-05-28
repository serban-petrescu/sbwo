package spet.sbwo.integration.web.googlemaps;

class Geometry {
    private Bound bounds;
    private String locationType;
    private Bound viewport;
    private LatLng location;

    public Bound getBounds() {
        return bounds;
    }

    public void setBounds(Bound bounds) {
        this.bounds = bounds;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Bound getViewport() {
        return viewport;
    }

    public void setViewport(Bound viewport) {
        this.viewport = viewport;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

}
