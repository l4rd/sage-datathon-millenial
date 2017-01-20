package com.millennial.sageup;

/**
 * Created by TehLe on 20/01/2017.
 */

public class Industry {

    String sectorId;
    String sectorName;


    public Industry(String sectorId, String sectorName) {
        this.sectorId = sectorId;
        this.sectorName = sectorName;
    }

    public Industry() {
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    @Override
    public String toString() {
        return sectorName;
    }
}
