package model;

public class City {
    private final int cityId;
    private final String city;
    private final Country country;

    public City(int cityId, String city, Country country) {
        this.cityId = cityId;
        this.city = city;
        this.country = country;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCity() {
        return city;
    }

    public Country getCountry() {
        return country;
    }
}
