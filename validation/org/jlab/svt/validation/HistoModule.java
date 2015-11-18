package org.jlab.svt.validation;

import org.root.group.TDirectory;
import org.root.histogram.H1D;
import org.root.histogram.H2D;

public class HistoModule {

    public H1D[][] h_;
    public H2D[][] h2_;
    String name_;
    public TDirectory rootdir_;
    public TDirectory dir_;

    public HistoModule(TDirectory rootdir, TDirectory dir, String name) {
        name_ = name;
        rootdir_ = rootdir;
        dir_ = dir;
    }

    public void book(String varName, int nBins, double xMin, double xMax) {

        h_ = new H1D[Constants.NREGIONS][Constants.NSECTORS];
        String dirVar = dir_.getName() + "/" + varName;
        TDirectory dir = new TDirectory(dirVar);
        String hname;
        String htitle;

        for (int i = 0; i < Constants.NREGIONS; ++i) {
            for (int j = 0; j < Constants.NSECTORS; ++j) {
                if (i == 0 && j > Constants.NR1SECTORS - 1) continue;
                else if (i == 1 && j > Constants.NR2SECTORS - 1) continue;
                else if (i == 2 && j > Constants.NR3SECTORS - 1) continue;
                hname = "h" + varName + "_" + (i + 1) + "_" + (j + 1);
                htitle = varName + ", Region " + (i + 1) + ", Sector " + (j + 1);
                h_[i][j] = new H1D(hname, htitle, nBins, xMin, xMax);
                h_[i][j].setXTitle(varName);
                h_[i][j].setYTitle("Entries");
                h_[i][j].setFillColor(24);
                dir.add(h_[i][j]);
            }//sector
        }//region
        rootdir_.addDirectory(dir);
    }

    public void book(String varName, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {

        h2_ = new H2D[Constants.NREGIONS][Constants.NSECTORS];
        String dirVar = dir_.getName()+"/"+varName;
        TDirectory dir = new TDirectory(dirVar);
        String hname;
        String htitle;

        for(int i=0; i<Constants.NREGIONS; ++i) {
            for(int j=0; j<Constants.NSECTORS; ++j) {
                if(i==0 && j>Constants.NR1SECTORS-1) continue;
                else if(i==1 && j>Constants.NR2SECTORS-1) continue;
                else if(i==2 && j>Constants.NR3SECTORS-1) continue;
                hname="h"+varName+"_"+(i+1)+"_"+(j+1);
                htitle=varName+", Region "+(i+1)+", Sector "+(j+1);
                h2_[i][j] = new H2D(hname, htitle, nBinsX, xMin, xMax, nBinsY, yMin, yMax);
                h2_[i][j].setTitle(hname);
                h2_[i][j].setXTitle(xTitle);
                h2_[i][j].setYTitle(yTitle);
// 	h2_[i][j].setMarkerStyle(20);
// 	h2_[i][j].setMarkerSize(0.3);
// 	h2_[i][j].setMarkerColor(24);
                dir.add(h2_[i][j]);
            }//sector
        }//region
        rootdir_.addDirectory(dir);
    }
}