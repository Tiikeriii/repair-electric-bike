package se.kth.iv1350.repair.model;

/**
 * Represents a customer and their bike in the domain model.
 * This class is internal to the model and integration layers.
 */
public class Customer {
    private final String name;
    private final int phoneNumber;
    private final String email;
    private final String bikeBrand;
    private final String bikeModel;
    private final String bikeSerialNumber;

    /**
     * Creates a new Customer with the specified details.
     *
     * @param name             The customer's name.
     * @param phoneNumber      The customer's phone number.
     * @param email            The customer's email address.
     * @param bikeBrand        The brand of the customer's bike.
     * @param bikeModel        The model of the customer's bike.
     * @param bikeSerialNumber The serial number of the customer's bike.
     */
    public Customer(String name, int phoneNumber, String email,
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

    /**
     * Creates a CustomerDTO containing all customer data for use outside the model layer.
     *
     * @return A CustomerDTO representing this customer.
     */
    public CustomerDTO toDTO() {
        return new CustomerDTO(name, phoneNumber, email, bikeBrand, bikeModel, bikeSerialNumber);
    }
}
