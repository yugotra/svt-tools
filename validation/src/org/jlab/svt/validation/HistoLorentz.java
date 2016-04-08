package org.jlab.svt.validation;

import org.root.group.TDirectory;
import org.root.histogram.H1D;

public class HistoLorentz {

    public H1D[] h;
    String name_;
    public TDirectory rootdir_;
    int minAngle =-90;
    int angle_spread_=180;
    int angleSpace =4;
    int arraySize =45;
    int angleMin;
    int angleMax;

    public HistoLorentz(TDirectory rootdir,String name){
        name_=name;
        rootdir_=rootdir;
    }

    public void book(String varName, int nBins, double xMin, double xMax) {

        h = new H1D[arraySize];
        String hname;
        String htitle;
        htitle=varName;

        angleMin = minAngle;
        angleMax = angleMin + angleSpace;
        for(int j = 0; j < arraySize; j++){
            hname="h"+varName+"_"+(j+1);
            htitle=varName+" "+(j+1);
            h[j] = new H1D(hname, htitle, nBins, xMin, xMax);
            h[j].setXTitle(varName);
            h[j].setYTitle("Entries");
            h[j].setFillColor(24);
            rootdir_.add(h[j]);
        }
    }
}


