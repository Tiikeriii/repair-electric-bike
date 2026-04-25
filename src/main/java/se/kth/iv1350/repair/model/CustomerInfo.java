package se.kth.iv1350.repair.model;

/**
 * Holds all information about a customer and their bike. 
 * This is a DTO for passing Customer information accross layers.
 */
public class CustomerInfo {
    private final String name;
    private final int phoneNumber;
    private final String email;
    private final String bikeBrand;
    private final String bikeModel;
    private final String bikeSerialNumber;

    /**
     * Creates a new instance with the specified customer and bike details.
     *
     * @param name             The customer's name.
     * @param phoneNumber      The customer's phone number.
     * @param email            The customer's email address.
     * @param bikeBrand        The brand of the customer's bike.
     * @param bikeModel        The model of the customer's bike.
     * @param bikeSerialNumber The serial number of the customer's bike.
     */
    public CustomerInfo(String name, int phoneNumber, String email,
                        String bikeBrand, String bikeModel, String bikeSerialNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bikeBrand = bikeBrand;
        this.bikeModel = bikeModel;
        this.bikeSerialNumber = bikeSerialNumber;
    }

    /** @return The customer's name. */
    public String getName() {
        return name;
    }

    /** @return The customer's phone number. */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /** @return The customer's email address. */
    public String getEmail() {
        return email;
    }

    /** @return The brand of the customer's bike. */
    public String getBikeBrand() {
        return bikeBrand;
    }

    /** @return The model of the customer's bike. */
    public String getBikeModel() {
        return bikeModel;
    }

    /** @return The serial number of the customer's bike. */
    public String getBikeSerialNumber() {
        return bikeSerialNumber;
    }

    /** @return A formatted string with all customer and bike information. */
    @Override
    public String toString() {
        return "Customer: " + name + " | Phone: " + phoneNumber + " | Email: " + email +
               "\nBike: " + bikeBrand + " " + bikeModel + " | Serial: " + bikeSerialNumber;
    }
}
