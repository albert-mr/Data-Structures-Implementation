package Tables;

public class Business {
    private String name; //name of the business
    private String date; //day of the week they are interested in
    private long price; //price they are willing to pay, in euros

    /**
     * Constructor of the class Business
     * @param name name of the business
     * @param date day of the week they are interested in
     * @param price price they are willing to pay, in euros
     */
    public Business(String name, String date, long price) {
        this.name = name;
        this.date = date;
        this.price = price;
    }

    /**
     * Get of the name of the business
     * @return name of the business
     */
    public String getName() {
        return name;
    }

    /**
     * Get of the day of the week they are interested in
     * @return day of the week they are interested in
     */
    public String getDate() {
        return date;
    }

    /**
     * Get of the price they are willing to pay, in euros
     * @return price they are willing to pay, in euros
     */
    public long getPrice() {
        return price;
    }

}
