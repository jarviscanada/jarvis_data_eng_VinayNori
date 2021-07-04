package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "symbol",
        "companyName",
        "primaryExchange",
        "calculationPrice",
        "open",
        "openTime",
        "openSource",
        "close",
        "closeTime",
        "closeSource",
        "high",
        "highTime",
        "highSource",
        "low",
        "lowTime",
        "lowSource",
        "latestPrice",
        "latestSource",
        "latestTime",
        "latestUpdate",
        "latestVolume",
        "iexRealtimePrice",
        "iexRealtimeSize",
        "iexLastUpdated",
        "delayedPrice",
        "delayedPriceTime",
        "oddLotDelayedPrice",
        "oddLotDelayedPriceTime",
        "extendedPrice",
        "extendedChange",
        "extendedChangePercent",
        "extendedPriceTime",
        "previousClose",
        "previousVolume",
        "change",
        "changePercent",
        "volume",
        "iexMarketPercent",
        "iexVolume",
        "avgTotalVolume",
        "iexBidPrice",
        "iexBidSize",
        "iexAskPrice",
        "iexAskSize",
        "iexOpen",
        "iexOpenTime",
        "iexClose",
        "iexCloseTime",
        "marketCap",
        "peRatio",
        "week52High",
        "week52Low",
        "ytdChange",
        "lastTradeTime",
        "isUSMarketOpen"
})

public class IexQuote {

    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("primaryExchange")
    private String primaryExchange;
    @JsonProperty("calculationPrice")
    private String calculationPrice;
    @JsonProperty("open")
    private Double open;
    @JsonProperty("openTime")
    private Long openTime;
    @JsonProperty("openSource")
    private String openSource;
    @JsonProperty("close")
    private Double close;
    @JsonProperty("closeTime")
    private Long closeTime;
    @JsonProperty("closeSource")
    private String closeSource;
    @JsonProperty("high")
    private Double high;
    @JsonProperty("highTime")
    private Long highTime;
    @JsonProperty("highSource")
    private String highSource;
    @JsonProperty("low")
    private Double low;
    @JsonProperty("lowTime")
    private Long lowTime;
    @JsonProperty("lowSource")
    private String lowSource;
    @JsonProperty("latestPrice")
    private Double latestPrice;
    @JsonProperty("latestSource")
    private String latestSource;
    @JsonProperty("latestTime")
    private String latestTime;
    @JsonProperty("latestUpdate")
    private Long latestUpdate;
    @JsonProperty("latestVolume")
    private Long latestVolume;
    @JsonProperty("iexRealtimePrice")
    private Double iexRealtimePrice;
    @JsonProperty("iexRealtimeSize")
    private Long iexRealtimeSize;
    @JsonProperty("iexLastUpdated")
    private Long iexLastUpdated;
    @JsonProperty("delayedPrice")
    private Double delayedPrice;
    @JsonProperty("delayedPriceTime")
    private Long delayedPriceTime;
    @JsonProperty("oddLotDelayedPrice")
    private Double oddLotDelayedPrice;
    @JsonProperty("oddLotDelayedPriceTime")
    private Long oddLotDelayedPriceTime;
    @JsonProperty("extendedPrice")
    private Double extendedPrice;
    @JsonProperty("extendedChange")
    private Double extendedChange;
    @JsonProperty("extendedChangePercent")
    private Double extendedChangePercent;
    @JsonProperty("extendedPriceTime")
    private Long extendedPriceTime;
    @JsonProperty("previousClose")
    private Double previousClose;
    @JsonProperty("previousVolume")
    private Long previousVolume;
    @JsonProperty("change")
    private Double change;
    @JsonProperty("changePercent")
    private Double changePercent;
    @JsonProperty("volume")
    private Long volume;
    @JsonProperty("iexMarketPercent")
    private Double iexMarketPercent;
    @JsonProperty("iexVolume")
    private Long iexVolume;
    @JsonProperty("avgTotalVolume")
    private Long avgTotalVolume;
    @JsonProperty("iexBidPrice")
    private Double iexBidPrice;
    @JsonProperty("iexBidSize")
    private Integer iexBidSize;
    @JsonProperty("iexAskPrice")
    private Double iexAskPrice;
    @JsonProperty("iexAskSize")
    private Integer iexAskSize;
    @JsonProperty("iexOpen")
    private Double iexOpen;
    @JsonProperty("iexOpenTime")
    private Long iexOpenTime;
    @JsonProperty("iexClose")
    private Double iexClose;
    @JsonProperty("iexCloseTime")
    private Long iexCloseTime;
    @JsonProperty("marketCap")
    private Long marketCap;
    @JsonProperty("peRatio")
    private Double peRatio;
    @JsonProperty("week52High")
    private Double week52High;
    @JsonProperty("week52Low")
    private Double week52Low;
    @JsonProperty("ytdChange")
    private Double ytdChange;
    @JsonProperty("lastTradeTime")
    private Long lastTradeTime;
    @JsonProperty("isUSMarketOpen")
    private Boolean isUSMarketOpen;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPrimaryExchange() {
        return primaryExchange;
    }

    public void setPrimaryExchange(String primaryExchange) {
        this.primaryExchange = primaryExchange;
    }

    public String getCalculationPrice() {
        return calculationPrice;
    }

    public void setCalculationPrice(String calculationPrice) {
        this.calculationPrice = calculationPrice;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Long openTime) {
        this.openTime = openTime;
    }

    public String getOpenSource() {
        return openSource;
    }

    public void setOpenSource(String openSource) {
        this.openSource = openSource;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Long closeTime) {
        this.closeTime = closeTime;
    }

    public String getCloseSource() {
        return closeSource;
    }

    public void setCloseSource(String closeSource) {
        this.closeSource = closeSource;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Long getHighTime() {
        return highTime;
    }

    public void setHighTime(Long highTime) {
        this.highTime = highTime;
    }

    public String getHighSource() {
        return highSource;
    }

    public void setHighSource(String highSource) {
        this.highSource = highSource;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Long getLowTime() {
        return lowTime;
    }

    public void setLowTime(Long lowTime) {
        this.lowTime = lowTime;
    }

    public String getLowSource() {
        return lowSource;
    }

    public void setLowSource(String lowSource) {
        this.lowSource = lowSource;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getLatestSource() {
        return latestSource;
    }

    public void setLatestSource(String latestSource) {
        this.latestSource = latestSource;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public Long getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(Long latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public Long getLatestVolume() {
        return latestVolume;
    }

    public void setLatestVolume(Long latestVolume) {
        this.latestVolume = latestVolume;
    }

    public Double getIexRealtimePrice() {
        return iexRealtimePrice;
    }

    public void setIexRealtimePrice(Double iexRealtimePrice) {
        this.iexRealtimePrice = iexRealtimePrice;
    }

    public Long getIexRealtimeSize() {
        return iexRealtimeSize;
    }

    public void setIexRealtimeSize(Long iexRealtimeSize) {
        this.iexRealtimeSize = iexRealtimeSize;
    }

    public Long getIexLastUpdated() {
        return iexLastUpdated;
    }

    public void setIexLastUpdated(Long iexLastUpdated) {
        this.iexLastUpdated = iexLastUpdated;
    }

    public Double getDelayedPrice() {
        return delayedPrice;
    }

    public void setDelayedPrice(Double delayedPrice) {
        this.delayedPrice = delayedPrice;
    }

    public Long getDelayedPriceTime() {
        return delayedPriceTime;
    }

    public void setDelayedPriceTime(Long delayedPriceTime) {
        this.delayedPriceTime = delayedPriceTime;
    }

    public Double getOddLotDelayedPrice() {
        return oddLotDelayedPrice;
    }

    public void setOddLotDelayedPrice(Double oddLotDelayedPrice) {
        this.oddLotDelayedPrice = oddLotDelayedPrice;
    }

    public Long getOddLotDelayedPriceTime() {
        return oddLotDelayedPriceTime;
    }

    public void setOddLotDelayedPriceTime(Long oddLotDelayedPriceTime) {
        this.oddLotDelayedPriceTime = oddLotDelayedPriceTime;
    }

    public Double getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(Double extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public Double getExtendedChange() {
        return extendedChange;
    }

    public void setExtendedChange(Double extendedChange) {
        this.extendedChange = extendedChange;
    }

    public Double getExtendedChangePercent() {
        return extendedChangePercent;
    }

    public void setExtendedChangePercent(Double extendedChangePercent) {
        this.extendedChangePercent = extendedChangePercent;
    }

    public Long getExtendedPriceTime() {
        return extendedPriceTime;
    }

    public void setExtendedPriceTime(Long extendedPriceTime) {
        this.extendedPriceTime = extendedPriceTime;
    }

    public Double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(Double previousClose) {
        this.previousClose = previousClose;
    }

    public Long getPreviousVolume() {
        return previousVolume;
    }

    public void setPreviousVolume(Long previousVolume) {
        this.previousVolume = previousVolume;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(Double changePercent) {
        this.changePercent = changePercent;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Double getIexMarketPercent() {
        return iexMarketPercent;
    }

    public void setIexMarketPercent(Double iexMarketPercent) {
        this.iexMarketPercent = iexMarketPercent;
    }

    public Long getIexVolume() {
        return iexVolume;
    }

    public void setIexVolume(Long iexVolume) {
        this.iexVolume = iexVolume;
    }

    public Long getAvgTotalVolume() {
        return avgTotalVolume;
    }

    public void setAvgTotalVolume(Long avgTotalVolume) {
        this.avgTotalVolume = avgTotalVolume;
    }

    public Double getIexBidPrice() {
        return iexBidPrice;
    }

    public void setIexBidPrice(Double iexBidPrice) {
        this.iexBidPrice = iexBidPrice;
    }

    public Integer getIexBidSize() {
        return iexBidSize;
    }

    public void setIexBidSize(Integer iexBidSize) {
        this.iexBidSize = iexBidSize;
    }

    public Double getIexAskPrice() {
        return iexAskPrice;
    }

    public void setIexAskPrice(Double iexAskPrice) {
        this.iexAskPrice = iexAskPrice;
    }

    public Integer getIexAskSize() {
        return iexAskSize;
    }

    public void setIexAskSize(Integer iexAskSize) {
        this.iexAskSize = iexAskSize;
    }

    public Double getIexOpen() {
        return iexOpen;
    }

    public void setIexOpen(Double iexOpen) {
        this.iexOpen = iexOpen;
    }

    public Long getIexOpenTime() {
        return iexOpenTime;
    }

    public void setIexOpenTime(Long iexOpenTime) {
        this.iexOpenTime = iexOpenTime;
    }

    public Double getIexClose() {
        return iexClose;
    }

    public void setIexClose(Double iexClose) {
        this.iexClose = iexClose;
    }

    public Long getIexCloseTime() {
        return iexCloseTime;
    }

    public void setIexCloseTime(Long iexCloseTime) {
        this.iexCloseTime = iexCloseTime;
    }

    public Long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Long marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(Double peRatio) {
        this.peRatio = peRatio;
    }

    public Double getWeek52High() {
        return week52High;
    }

    public void setWeek52High(Double week52High) {
        this.week52High = week52High;
    }

    public Double getWeek52Low() {
        return week52Low;
    }

    public void setWeek52Low(Double week52Low) {
        this.week52Low = week52Low;
    }

    public Double getYtdChange() {
        return ytdChange;
    }

    public void setYtdChange(Double ytdChange) {
        this.ytdChange = ytdChange;
    }

    public Long getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(Long lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public Boolean getUSMarketOpen() {
        return isUSMarketOpen;
    }

    public void setUSMarketOpen(Boolean USMarketOpen) {
        isUSMarketOpen = USMarketOpen;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"symbol\":\"" + symbol + "\""
                + ",\n                         \"companyName\":\"" + companyName + "\""
                + ",\n                         \"primaryExchange\":\"" + primaryExchange + "\""
                + ",\n                         \"calculationPrice\":\"" + calculationPrice + "\""
                + ",\n                         \"open\":\"" + open + "\""
                + ",\n                         \"openTime\":\"" + openTime + "\""
                + ",\n                         \"openSource\":\"" + openSource + "\""
                + ",\n                         \"close\":\"" + close + "\""
                + ",\n                         \"closeTime\":\"" + closeTime + "\""
                + ",\n                         \"closeSource\":\"" + closeSource + "\""
                + ",\n                         \"high\":\"" + high + "\""
                + ",\n                         \"highTime\":\"" + highTime + "\""
                + ",\n                         \"highSource\":\"" + highSource + "\""
                + ",\n                         \"low\":\"" + low + "\""
                + ",\n                         \"lowTime\":\"" + lowTime + "\""
                + ",\n                         \"lowSource\":\"" + lowSource + "\""
                + ",\n                         \"latestPrice\":\"" + latestPrice + "\""
                + ",\n                         \"latestSource\":\"" + latestSource + "\""
                + ",\n                         \"latestTime\":\"" + latestTime + "\""
                + ",\n                         \"latestUpdate\":\"" + latestUpdate + "\""
                + ",\n                         \"latestVolume\":\"" + latestVolume + "\""
                + ",\n                         \"iexRealtimePrice\":\"" + iexRealtimePrice + "\""
                + ",\n                         \"iexRealtimeSize\":\"" + iexRealtimeSize + "\""
                + ",\n                         \"iexLastUpdated\":\"" + iexLastUpdated + "\""
                + ",\n                         \"delayedPrice\":\"" + delayedPrice + "\""
                + ",\n                         \"delayedPriceTime\":\"" + delayedPriceTime + "\""
                + ",\n                         \"oddLotDelayedPrice\":\"" + oddLotDelayedPrice + "\""
                + ",\n                         \"oddLotDelayedPriceTime\":\"" + oddLotDelayedPriceTime
                + "\""
                + ",\n                         \"extendedPrice\":\"" + extendedPrice + "\""
                + ",\n                         \"extendedChange\":\"" + extendedChange + "\""
                + ",\n                         \"extendedChangePercent\":\"" + extendedChangePercent + "\""
                + ",\n                         \"extendedPriceTime\":\"" + extendedPriceTime + "\""
                + ",\n                         \"previousClose\":\"" + previousClose + "\""
                + ",\n                         \"previousVolume\":\"" + previousVolume + "\""
                + ",\n                         \"change\":\"" + change + "\""
                + ",\n                         \"changePercent\":\"" + changePercent + "\""
                + ",\n                         \"volume\":\"" + volume + "\""
                + ",\n                         \"iexMarketPercent\":\"" + iexMarketPercent + "\""
                + ",\n                         \"iexVolume\":\"" + iexVolume + "\""
                + ",\n                         \"avgTotalVolume\":\"" + avgTotalVolume + "\""
                + ",\n                         \"iexBidPrice\":\"" + iexBidPrice + "\""
                + ",\n                         \"iexBidSize\":\"" + iexBidSize + "\""
                + ",\n                         \"iexAskPrice\":\"" + iexAskPrice + "\""
                + ",\n                         \"iexAskSize\":\"" + iexAskSize + "\""
                + ",\n                         \"iexOpen\":\"" + iexOpen + "\""
                + ",\n                         \"iexOpenTime\":\"" + iexOpenTime + "\""
                + ",\n                         \"iexClose\":\"" + iexClose + "\""
                + ",\n                         \"iexCloseTime\":\"" + iexCloseTime + "\""
                + ",\n                         \"marketCap\":\"" + marketCap + "\""
                + ",\n                         \"peRatio\":\"" + peRatio + "\""
                + ",\n                         \"week52High\":\"" + week52High + "\""
                + ",\n                         \"week52Low\":\"" + week52Low + "\""
                + ",\n                         \"ytdChange\":\"" + ytdChange + "\""
                + ",\n                         \"lastTradeTime\":\"" + lastTradeTime + "\""
                + ",\n                         \"isUSMarketOpen\":\"" + isUSMarketOpen + "\""
                + "}";
    }

}