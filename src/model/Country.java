package model;

public class Country {
    private final int countryId;
    private final String country;

    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountry() {
        return country;
    }
}
