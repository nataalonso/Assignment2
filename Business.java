import java.time.*;
import java.time.format.DateTimeFormatter;
/**
 * The Business class represents a business entity with relevant attributes such as name, address, location, NAICS code, and dates.
 */
public class Business {
    /**
     * The name of the business.
     */
    private String name;
    /**
     * The zip code of the business.
     */
    private String zipCode;
    /**
     * The NAICS code of the business.
     */
    private String naicsCode;
    /**
     * The neighborhood of the business.
     */
    private String neighborhood;
    /**
     * The state of the business.
     */
    private String state;
    /**
     * The city of the business.
     */
    private String city;
    /**
     * The address of the business.
     */
    private String address;
    /**
     * The start date of the business.
     */
    private LocalDate startDate;
    /**
     * The closure date of the business.
     */
    private LocalDate closureDate;

    /**
     * Constructs a Business object with the given parameters.
     *
     * @param name          The name of the business.
     * @param address       The address of the business.
     * @param city          The city of the business.
     * @param state         The state of the business.
     * @param zipCode       The zip code of the business.
     * @param naicsCode     The NAICS code of the business.
     * @param neighborhood  The neighborhood of the business.
     * @param startDate     The start date of the business.
     * @param closureDate   The closure date of the business.
     */
    public Business(String name, String address, String city, String state, String zipCode, String naicsCode, String neighborhood, LocalDate startDate, LocalDate closureDate) {
        this.name = name;
        this.zipCode = zipCode;
        this.naicsCode = naicsCode;
        this.neighborhood = neighborhood;
        this.address = address;
        this.state = state;
        this.city = city;
        this.startDate = startDate;
        this.closureDate = closureDate;
    }

    /**
     * Returns the name of the business.
     *
     * @return The name of the business.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the zip code of the business.
     *
     * @return The zip code of the business.
     */
    public String getZip() {
        return zipCode;
    }

    /**
     * Returns the NAICS code of the business.
     *
     * @return The NAICS code of the business.
     */
    public String getNaicsCode() {
        return naicsCode;
    }

    /**
     * Returns the city of the business.
     *
     * @return The city of the business.
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the address of the business.
     *
     * @return The address of the business.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the state of the business.
     *
     * @return The state of the business.
     */
    public String getState() {
        return state;
    }

    /**
     * Returns the neighborhood of the business.
     *
     * @return The neighborhood of the business.
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Returns the start date of the business.
     *
     * @return The start date of the business.
     */
    public LocalDate getStartDate() {
        return startDate;
    }
      /**
     * Returns the closure date of the business.
     *
     * @return The closure date of the business.
     */
    public LocalDate getClosureDate() {
        return closureDate;
    }
}

   
