package com.engineer.enumeration;

/**
 * Created by Konrad on 25.10.2015.
 */
public enum WeightEnum {
    admin,
    regular,
    serviceman;

    private static WeightEnum[] allValues = values();
    public static WeightEnum fromOrdinal(int n) {return allValues[n];}

}
