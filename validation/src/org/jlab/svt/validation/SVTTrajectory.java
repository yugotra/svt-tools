package org.jlab.svt.validation;

import dnl.utils.text.table.TextTable;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SVTTrajectory {
    ArrayList<Integer> id;
    ArrayList <Integer> layer;
    ArrayList <Integer> sector;
    ArrayList <Double> x;
    ArrayList <Double> y;
    ArrayList <Double> z;
    ArrayList <Double> phi;
    ArrayList <Double> theta;
    ArrayList <Double> angle3D;
    ArrayList <Double> centroid;
    int nLayers;
    int trackId;

    SVTTrajectory() {
        id = new ArrayList<>();
        id.clear();
        sector = new ArrayList<>();
        sector.clear();
        layer = new ArrayList<>();
        layer.clear();
        x = new ArrayList<>();
        x.clear();
        y = new ArrayList<>();
        y.clear();
        z = new ArrayList<>();
        z.clear();
        phi = new ArrayList<>();
        phi.clear();
        theta = new ArrayList<>();
        theta.clear();
        angle3D = new ArrayList<>();
        angle3D.clear();
        centroid = new ArrayList<>();
        centroid.clear();
        nLayers =-1;
        trackId =-1;
    }

    int GetIdByModule(int trackId, int layer, int sector) {
        for(int i = 0; i< id.size(); ++i) {
            if(trackId== this.trackId && this.layer.get(i)==layer&& this.sector.get(i)==sector) return i;
        }
        return -1;
    }

    void Show() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        NumberFormat nf0 = NumberFormat.getInstance();
        nf0.setMaximumFractionDigits(0);
        String[] columnNames = {"TrjID","layer","sector","x","y","z","phi","theta","angle3d","centroid","tk_ID"};
        int n_vars = columnNames.length;
        Object[][] data = new Object[nLayers][n_vars];
        for(int i = 0; i< nLayers; ++i) {
            data[i][0]= id.get(i);
            data[i][1]= layer.get(i);
            data[i][2]= sector.get(i);
            data[i][3]=nf0.format(x.get(i));
            data[i][4]=nf0.format(y.get(i));
            data[i][5]=nf0.format(z.get(i));
            data[i][6]=nf0.format(phi.get(i));
            data[i][7]=nf0.format(theta.get(i));
            data[i][8]=nf0.format(angle3D.get(i));
            data[i][9]=nf0.format(centroid.get(i));
            data[i][10]= trackId;
        }

        TextTable tt = new TextTable(columnNames, data);
//     tt.setAddRowNumbering(false);      
// sort by the first column                              
// tt.setSort(0);                                                 
//     tt.addSeparatorPolicy(new LastRowSeparatorPolicy());
        tt.printTable();
    }
}
