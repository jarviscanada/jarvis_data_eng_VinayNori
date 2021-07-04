package ca.jrvs.apps.trading.model.domain;

public class Quote implements Entity<Integer> {

    private Integer id;
    private String ticker;
    private Double lastPrice;
    private Double bidPrice;
    private Integer bidSize;
    private Double askPrice;
    private Integer askSize;

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Integer getBidSize() {
        return bidSize;
    }

    public void setBidSize(Integer bidSize) {
        this.bidSize = bidSize;
    }

    public Double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Integer getAskSize() {
        return askSize;
    }

    public void setAskSize(Integer askSize) {
        this.askSize = askSize;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public void setId(Integer s) {
        this.id = s;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"ticker\":\"" + ticker + "\""
                + ",\n                         \"lastPrice\":\"" + lastPrice + "\""
                + ",\n                         \"bidPrice\":\"" + bidPrice + "\""
                + ",\n                         \"bidSize\":\"" + bidSize + "\""
                + ",\n                         \"askPrice\":\"" + askPrice + "\""
                + ",\n                         \"askSize\":\"" + askSize + "\""
                + "}";
    }
}