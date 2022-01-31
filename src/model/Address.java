package model;

public class Address {
    private final int addressId;
    private final String address;
    private final String phone;
    private final City city;

    public Address(int addressId, String address, String phone, City city) {
        this.addressId = addressId;
        this.address = address;
        this.phone = phone;
        this.city = city;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public City getCity() {
        return city;
    }
}
