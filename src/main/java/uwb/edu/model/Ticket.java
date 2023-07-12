package uwb.edu.model;

import java.math.BigDecimal;

public class Ticket {

    private BigDecimal basePrice;
    private BigDecimal priceForDog;
    private BigDecimal bigFamily;
    private BigDecimal numberOfTickets;
    private BigDecimal happyHours;

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getPriceForDog() {
        return priceForDog;
    }

    public void setPriceForDog(BigDecimal priceForDog) {
        this.priceForDog = priceForDog;
    }

    public BigDecimal getBigFamily() {
        return bigFamily;
    }

    public void setBigFamily(BigDecimal bigFamily) {
        this.bigFamily = bigFamily;
    }

    public BigDecimal getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(BigDecimal numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public BigDecimal getHappyHours() {
        return happyHours;
    }

    public void setHappyHours(BigDecimal happyHours) {
        this.happyHours = happyHours;
    }
}
