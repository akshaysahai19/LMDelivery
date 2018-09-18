package aksahysahai.lmdelivery;

public class LocationGetSet {

    String address;
    double lat;
    double lng;

    public LocationGetSet(String address, double lat, double lng) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
