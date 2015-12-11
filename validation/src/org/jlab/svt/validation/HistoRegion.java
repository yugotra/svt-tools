package org.jlab.svt.validation;

import org.root.group.TDirectory;
import org.root.histogram.H1D;
import org.root.histogram.H2D;

public class HistoRegion {

    public H1D[] h;
    public H2D[] h2;
    String name;
    public TDirectory rootDir;
    public TDirectory directory;

    public HistoRegion(TDirectory rootDir,TDirectory dir,String name){
        this.name =name;
        this.rootDir =rootDir;
        directory =dir;
    }

    public void book(String varName, int nBins, double xMin, double xMax) {

        h = new H1D[Constants.NREGIONS];
        String dirVar = directory.getName()+"/"+varName;
        TDirectory dir = new TDirectory(dirVar);
        String hName;
        String hTitle;

        for(int i=0; i<Constants.NREGIONS; ++i) {
            hName="h"+varName+"_"+(i+1);
            hTitle=varName+", Region "+(i+1);
            h[i] = new H1D(hName, hTitle, nBins, xMin, xMax);
            h[i].setXTitle(varName);
            h[i].setYTitle("Entries");
            h[i].setFillColor(24);
            dir.add(h[i]);
        }//region
        rootDir.addDirectory(dir);
    }

    public void book(String varName, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {

        h2 = new H2D[Constants.NREGIONS];
        String dirVar = directory.getName()+"/"+varName;
        TDirectory dir = new TDirectory(dirVar);
        String hName;
        String hTitle;

        for(int i=0; i<Constants.NREGIONS; ++i) {
            hName="h"+varName+"_"+(i+1);
            hTitle=varName+", Region "+(i+1);
            h2[i] = new H2D(hName, hTitle, nBinsX, xMin, xMax, nBinsY, yMin, yMax);
            h2[i].setTitle(hName);
            h2[i].setXTitle(xTitle);
            h2[i].setYTitle(yTitle);
// 	h2[i].setMarkerStyle(20);
// 	h2[i].setMarkerSize(0.3);
// 	h2[i].setMarkerColor(24);
            dir.add(h2[i]);
        }//region
        rootDir.addDirectory(dir);
    }
}
