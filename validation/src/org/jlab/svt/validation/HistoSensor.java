package org.jlab.svt.validation;

import org.root.group.TDirectory;
import org.root.histogram.H1D;
import org.root.histogram.H2D;

public class HistoSensor {

    public H1D[][] h;
    public H2D[][] h2;
    String name;
    public TDirectory rootDir;
    public TDirectory directory;

    public HistoSensor(TDirectory rootDir,TDirectory dir,String name){
        this.name =name;
        this.rootDir =rootDir;
        directory =dir;
    }

    public void book(String varName, int nBins, double xMin, double xMax) {

        h = new H1D[Constants.NLAYERS][Constants.NSECTORS];
        String dirVar = directory.getName()+"/"+varName;
        TDirectory dir = new TDirectory(dirVar);
        String hName;
        String hTitle;

        for(int i=0; i<Constants.NLAYERS; ++i) {
            for(int j=0; j<Constants.NSECTORS; ++j) {
                if(i<2 && j>Constants.NR1SECTORS-1) continue;
                else if((i==2||i==3) && j>Constants.NR2SECTORS-1) continue;
                else if((i==4||i==5) && j>Constants.NR3SECTORS-1) continue;
                hName="h"+varName+"_"+(i+1)+"_"+(j+1);
                hTitle=varName+", Layer "+(i+1)+", Sector "+(j+1);
                h[i][j] = new H1D(hName, hTitle, nBins, xMin, xMax);
                h[i][j].setXTitle(varName);
                h[i][j].setYTitle("Entries");
                h[i][j].setFillColor(24);
                dir.add(h[i][j]);
            }//sector
        }//layer
        rootDir.addDirectory(dir);
    }

    public void book(String varName, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {

        h2 = new H2D[Constants.NLAYERS][Constants.NSECTORS];
        String dirVar = directory.getName()+"/"+varName;
        TDirectory dir = new TDirectory(dirVar);
        String hname;
        String htitle;

        for(int i=0; i<Constants.NLAYERS; ++i) {
            for(int j=0; j<Constants.NSECTORS; ++j) {
                if(i<2 && j>Constants.NR1SECTORS-1) continue;
                else if((i==2||i==3) && j>Constants.NR2SECTORS-1) continue;
                else if((i==4||i==5) && j>Constants.NR3SECTORS-1) continue;
                hname="h"+varName+"_"+(i+1)+"_"+(j+1);
                htitle=varName+", Layer "+(i+1)+", Sector "+(j+1);
                h2[i][j] = new H2D(hname, htitle, nBinsX, xMin, xMax, nBinsY, yMin, yMax);
                h2[i][j].setTitle(hname);
                h2[i][j].setXTitle(xTitle);
                h2[i][j].setYTitle(yTitle);
// 	h2[i][j].setMarkerStyle(20);
// 	h2[i][j].setMarkerSize(0.3);
// 	h2[i][j].setMarkerColor(24);
                dir.add(h2[i][j]);
            }//sector
        }//layer
        rootDir.addDirectory(dir);
    }
}

