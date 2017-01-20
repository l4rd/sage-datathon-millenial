package com.millennial.sageup;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TehLe on 20/01/2017.
 */

public class SectorData {

    ArrayList<Industry> majorIndustry;
    ArrayList<Industry> subIndustry;

    public SectorData(ArrayList<Industry> majorIndustry, ArrayList<Industry> subIndustry) {
        this.majorIndustry = majorIndustry;
        this.subIndustry = subIndustry;
    }

    public SectorData() {
    }


    public ArrayList<Industry> getMajorIndustry() {
        return majorIndustry;
    }

    public void setMajorIndustry(ArrayList<Industry> majorIndustry) {
        this.majorIndustry = majorIndustry;
    }

    public ArrayList<Industry> getSubIndustry() {
        return subIndustry;
    }

    public void setSubIndustry(ArrayList<Industry> subIndustry) {
        this.subIndustry = subIndustry;
    }

    public void addMajorIndustry(Industry majorIndustry) {
        this.majorIndustry.add(majorIndustry);
    }

    public void addSubIndustry(Industry subIndustry) {
        this.subIndustry.add(subIndustry);
    }
}
