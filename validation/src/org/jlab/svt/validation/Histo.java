package org.jlab.svt.validation;

import org.root.group.TDirectory;
import org.root.histogram.H1D;
import org.root.histogram.H2D;

public class Histo {

    public H1D h;
    public H2D h2;
    String name;
    public TDirectory rootDir;

    public Histo(TDirectory rootdir,String name){
        this.name =name;
        rootDir =rootdir;
    }

    public H1D getH() {return h;}

    public void book(String varName, int nBins, double xMin, double xMax) {

        String hName;
        String hTitle;
        hName="h"+varName;
        hTitle=varName;
        h = new H1D(hName, hTitle, nBins, xMin, xMax);
        h.setXTitle(varName);
        h.setYTitle("Entries");
        h.setFillColor(24);
        rootDir.add(h);
    }

    public void book(String varName, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {

        String hName;
        String hTitle;
        hName="h"+varName;
        hTitle=varName;
        h2 = new H2D(hName, hTitle, nBinsX, xMin, xMax, nBinsY, yMin, yMax);
        h2.setTitle(varName);
        h2.setXTitle(xTitle);
        h2.setYTitle(yTitle);
        rootDir.add(h2);
    }
}

