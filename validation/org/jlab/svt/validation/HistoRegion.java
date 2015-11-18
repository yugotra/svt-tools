package org.jlab.svt.validation;

import org.root.group.TDirectory;
import org.root.histogram.H1D;
import org.root.histogram.H2D;

public class HistoRegion {

    public H1D[] h_;
    public H2D[] h2_;
    String name_;
    public TDirectory rootdir_;
    public TDirectory  dir_;

    public HistoRegion(TDirectory rootdir,TDirectory dir,String name){
        name_=name;
        rootdir_=rootdir;
        dir_=dir;
    }

    public void book(String varName, int nBins, double xMin, double xMax) {

        h_ = new H1D[Constants.NREGIONS];
        String dirVar = dir_.getName()+"/"+varName;
        TDirectory dir = new TDirectory(dirVar);
        String hname;
        String htitle;

        for(int i=0; i<Constants.NREGIONS; ++i) {
            hname="h"+varName+"_"+(i+1);
            htitle=varName+", Region "+(i+1);
            h_[i] = new H1D(hname, htitle, nBins, xMin, xMax);
            h_[i].setXTitle(varName);
            h_[i].setYTitle("Entries");
            h_[i].setFillColor(24);
            dir.add(h_[i]);
        }//region
        rootdir_.addDirectory(dir);
    }

    public void book(String varName, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {

        h2_ = new H2D[Constants.NREGIONS];
        String dirVar = dir_.getName()+"/"+varName;
        TDirectory dir = new TDirectory(dirVar);
        String hname;
        String htitle;

        for(int i=0; i<Constants.NREGIONS; ++i) {
            hname="h"+varName+"_"+(i+1);
            htitle=varName+", Region "+(i+1);
            h2_[i] = new H2D(hname, htitle, nBinsX, xMin, xMax, nBinsY, yMin, yMax);
            h2_[i].setTitle(hname);
            h2_[i].setXTitle(xTitle);
            h2_[i].setYTitle(yTitle);
// 	h2_[i].setMarkerStyle(20);
// 	h2_[i].setMarkerSize(0.3);
// 	h2_[i].setMarkerColor(24);
            dir.add(h2_[i]);
        }//region
        rootdir_.addDirectory(dir);
    }
}
