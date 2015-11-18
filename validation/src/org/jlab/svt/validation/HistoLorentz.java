package org.jlab.svt.validation;

import org.root.group.TDirectory;
import org.root.histogram.H1D;

public class HistoLorentz {

    public H1D[] h_;
    String name_;
    public TDirectory rootdir_;
    int min_angle_=-90;
    int angle_spread_=180;
    int angle_space_=4;
    int array_size_=45;
    int angle_min_;
    int angle_max_;

    public HistoLorentz(TDirectory rootdir,String name){
        name_=name;
        rootdir_=rootdir;
    }

    public void book(String varName, int nBins, double xMin, double xMax) {

        h_ = new H1D[array_size_];
        String hname;
        String htitle;
        htitle=varName;

        angle_min_ = min_angle_;
        angle_max_ = angle_min_ + angle_space_;
        for(int j = 0; j < array_size_; j++){
            hname="h"+varName+"_"+(j+1);
            htitle=varName+" "+(j+1);
            h_[j] = new H1D(hname, htitle, nBins, xMin, xMax);
            h_[j].setXTitle(varName);
            h_[j].setYTitle("Entries");
            h_[j].setFillColor(24);
            rootdir_.add(h_[j]);
        }
    }
}


