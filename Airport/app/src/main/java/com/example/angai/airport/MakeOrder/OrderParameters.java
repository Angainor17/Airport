package com.example.angai.airport.MakeOrder;

/**
 * Created by angai on 01.10.2016.
 */
public class OrderParameters {

    private String placeTo;
    private String placeFrom;

    private Integer costLow;
    private Integer costHigh;

    private Integer places;

    OrderParameters(String placeFrom, String placeTo, Integer costLow, Integer costHigh, Integer places){
        this.placeFrom = placeFrom;
        this.placeTo = placeTo;
        this.costHigh = costHigh;
        this.costLow = costLow;
        this.places = places;
    }


    public String getPlaceTo() {
        return placeTo;
    }

    public String getPlaceFrom() {
        return placeFrom;
    }

    public Integer getCostLow() {
        return costLow;
    }

    public Integer getCostHigh() {
        return costHigh;
    }

    public Integer getPlaces() {
        return places;
    }


}
