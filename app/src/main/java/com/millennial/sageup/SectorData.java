package com.millennial.sageup;

import java.util.HashMap;

/**
 * Created by TehLe on 20/01/2017.
 */

public class SectorData {

    HashMap<Integer, String> majorIndustry;
    HashMap<Integer, String> subIndustry;

    public SectorData(HashMap<Integer, String> majorIndustry, HashMap<Integer, String> subIndustry) {
        this.majorIndustry = majorIndustry;
        this.subIndustry = subIndustry;
    }

    public SectorData() {
    }


    public HashMap<Integer, String> getMajorIndustry() {
        return majorIndustry;
    }

    public void setMajorIndustry(HashMap<Integer, String> majorIndustry) {
        this.majorIndustry = majorIndustry;
    }

    public HashMap<Integer, String> getSubIndustry() {
        return subIndustry;
    }

    public void setSubIndustry(HashMap<Integer, String> subIndustry) {
        this.subIndustry = subIndustry;
    }
}
