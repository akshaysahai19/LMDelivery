package aksahysahai.lmdelivery;

public class DeliveryGetSet {

    String description;
    String imageUrl;
    LocationGetSet locationGetSet;

    public DeliveryGetSet(String description, String imageUrl, LocationGetSet locationGetSet) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.locationGetSet = locationGetSet;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocationGetSet getLocationGetSet() {
        return locationGetSet;
    }
}
