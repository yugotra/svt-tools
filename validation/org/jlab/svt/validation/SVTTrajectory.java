package org.jlab.svt.validation;

import dnl.utils.text.table.TextTable;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SVTTrajectory {
    ArrayList<Integer> id_;
    ArrayList <Integer> layer_;
    ArrayList <Integer> sector_;
    ArrayList <Double> x_;
    ArrayList <Double> y_;
    ArrayList <Double> z_;
    ArrayList <Double> phi_;
    ArrayList <Double> theta_;
    ArrayList <Double> angle_3d_;
    ArrayList <Double> centroid_;
    int n_layers_;
    int track_id_;

    SVTTrajectory() {
        id_ = new ArrayList<>();
        id_.clear();
        sector_ = new ArrayList<>();
        sector_.clear();
        layer_ = new ArrayList<>();
        layer_.clear();
        x_ = new ArrayList<>();
        x_.clear();
        y_ = new ArrayList<>();
        y_.clear();
        z_ = new ArrayList<>();
        z_.clear();
        phi_ = new ArrayList<>();
        phi_.clear();
        theta_ = new ArrayList<>();
        theta_.clear();
        angle_3d_ = new ArrayList<>();
        angle_3d_.clear();
        centroid_ = new ArrayList<>();
        centroid_.clear();
        n_layers_=-1;
        track_id_=-1;
    }

    int GetIdByModule(int track_id, int layer, int sector) {
        for(int i=0;i<id_.size();++i) {
            if(track_id==track_id_&&layer_.get(i)==layer&&sector_.get(i)==sector) return i;
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
        Object[][] data = new Object[n_layers_][n_vars];
        for(int i=0;i<n_layers_;++i) {
            data[i][0]=id_.get(i);
            data[i][1]=layer_.get(i);
            data[i][2]=sector_.get(i);
            data[i][3]=nf0.format(x_.get(i));
            data[i][4]=nf0.format(y_.get(i));
            data[i][5]=nf0.format(z_.get(i));
            data[i][6]=nf0.format(phi_.get(i));
            data[i][7]=nf0.format(theta_.get(i));
            data[i][8]=nf0.format(angle_3d_.get(i));
            data[i][9]=nf0.format(centroid_.get(i));
            data[i][10]=track_id_;
        }

        TextTable tt = new TextTable(columnNames, data);
//     tt.setAddRowNumbering(false);      
// sort by the first column                              
// tt.setSort(0);                                                 
//     tt.addSeparatorPolicy(new LastRowSeparatorPolicy());
        tt.printTable();
    }
}
