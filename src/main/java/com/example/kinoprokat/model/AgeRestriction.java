package com.example.kinoprokat.model;

public enum AgeRestriction {
    A(0), B(6), C(12), D(16), E(18);
    private int numVal;

    AgeRestriction(int numVal) {
        this.numVal = numVal;
    }

    AgeRestriction() {
    }

    public int getNumVal() {
        return numVal;
    }
}
