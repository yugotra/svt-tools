package org.jlab.svt.validation;

import org.root.group.TDirectory;
import org.root.histogram.H1D;
import org.root.histogram.H2D;

public class Histo {

    public H1D h_;
    public H2D h2_;
    String name_;
    public TDirectory rootdir_;

    public Histo(TDirectory rootdir,String name){
        name_=name;
        rootdir_=rootdir;
    }

    public H1D getH() {return h_;}

    public void book(String varName, int nBins, double xMin, double xMax) {

        String hname;
        String htitle;
        hname="h"+varName;
        htitle=varName;
        h_ = new H1D(hname, htitle, nBins, xMin, xMax);
        h_.setXTitle(varName);
        h_.setYTitle("Entries");
        h_.setFillColor(24);
        rootdir_.add(h_);
    }

    public void book(String varName, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {

        String hname;
        String htitle;
        hname="h"+varName;
        htitle=varName;
        h2_ = new H2D(hname, htitle, nBinsX, xMin, xMax, nBinsY, yMin, yMax);
        h2_.setTitle(varName);
        h2_.setXTitle(xTitle);
        h2_.setYTitle(yTitle);
        rootdir_.add(h2_);
    }
}

