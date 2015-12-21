package org.jlab.svt.validation;

import org.jlab.evio.clas12.EvioDataBank;
import org.jlab.evio.clas12.EvioDataEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SVTCross {
    int id;
    int sector;
    int region;
    double x;
    double y;
    double z;
    double errX;
    double errY;
    double errZ;
    double ux;
    double uy;
    double uz;
    int[] clusterId;
//    int trjId;
    int[] trjId;
    int trackId;
    int nTrackHits;
    int nTrackClusters;
    int nOfftrackHits;
    int nOfftrackClusters;
    ArrayList <SVTCluster> svtTrackClusters;

    SVTCross() {
        id =-1;
        sector =-1;
        region =-1;
        x =-1;
        y =-1;
        z =-1;
        errX =-1;
        errY =-1;
        errZ =-1;
        ux =-1;
        uy =-1;
        uz =-1;
        clusterId = new int[2];
        for(int i=0;i<2;++i) clusterId[i]=-1;
//        trjId = -1;
        trjId = new int[2];
        for(int i=0;i<2;++i) trjId[i]=-1;
        nTrackHits =0;
        nTrackClusters =0;
        nOfftrackHits =0;
        nOfftrackClusters =0;
        trackId =-1;
        svtTrackClusters = new ArrayList<>();
    }

    SVTCross GetByModule(int region, int sector) {
        if(this.region ==region&& this.sector ==sector) return this;
        else return null;
    }

    void ReadTrackClusters(EvioDataEvent event, int cluster_id, SVTTrajectory svt_trajectory, boolean print) {
        if(event.hasBank("BSTRec::Clusters")){
            EvioDataBank bank_SVTCluster = (EvioDataBank) event.getBank("BSTRec::Clusters");
            int nrows=bank_SVTCluster.rows();
            AddTrackCluster();
            int cluster_row=-1;
            int sector=-1;
            int layer=-1;
            for(int row = 0; row < nrows; row++) if(cluster_id==bank_SVTCluster.getInt("ID",row)) cluster_row=row;
            svtTrackClusters.get(nTrackClusters -1).id =cluster_id;
            svtTrackClusters.get(nTrackClusters -1).id =bank_SVTCluster.getInt("ID",cluster_row);
            sector=bank_SVTCluster.getInt("sector",cluster_row);
            layer=bank_SVTCluster.getInt("layer",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).sector =sector;
            svtTrackClusters.get(nTrackClusters -1).layer =layer;
            svtTrackClusters.get(nTrackClusters -1).size =bank_SVTCluster.getInt("size",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).eTot =bank_SVTCluster.getDouble("ETot",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).seedE =bank_SVTCluster.getDouble("seedE",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).seedStrip =bank_SVTCluster.getInt("seedStrip",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).centroid =bank_SVTCluster.getDouble("centroid",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).hit1Id =bank_SVTCluster.getInt("Hit1_ID",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).hit2Id =bank_SVTCluster.getInt("Hit2_ID",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).hit3Id =bank_SVTCluster.getInt("Hit3_ID",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).hit4Id =bank_SVTCluster.getInt("Hit4_ID",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).hit5Id =bank_SVTCluster.getInt("Hit5_ID",cluster_row);
            svtTrackClusters.get(nTrackClusters -1).crossId = id;
            svtTrackClusters.get(nTrackClusters -1).trackId = trackId;
            svtTrackClusters.get(nTrackClusters -1).trjId =svt_trajectory.GetIdByModule(trackId,layer,sector);
            svtTrackClusters.get(nTrackClusters -1).ReadTrackHits(event,cluster_id, print);
            nTrackHits += svtTrackClusters.get(nTrackClusters -1).nTrackHits;
            if(print) svtTrackClusters.get(nTrackClusters -1).Show();
        }
    }

    public void AddTrackCluster() {
        SVTCluster svtCluster = new SVTCluster();
        svtTrackClusters.add(svtCluster);
        nTrackClusters = svtTrackClusters.size();
    }

    void Show() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
//     System.out.println("Cross " + id + " " + sector + " " + region + " " + nf.format(x) + " " + nf.format(y) + " " + nf.format(z) + " " + nf.format(errX) +
// 		       " " + nf.format(errY) + " " + nf.format(errZ) + " " + nf.format(ux) + " " + nf.format(uy) + " " + nf.format(uz) + " " + " clId " + clusterId[0] +
//                        " " + clusterId[1] + " trId " + trackId + " ncl " + nTrackClusters + " nh " + nTrackHits);

        TableBuilder tb = new TableBuilder();
        tb.addRow("CrossID", "sector", "region", "x", "y", "z", "err_x", "err_y", "err_z", "ux", "uy", "uz", "Cl1_ID", "Cl2_ID", "Tk_ID", "N_cl", "N_h");
        tb.addRow("-----", "----", "-----");
        tb.addRow(id +"", sector +"", region +"", nf.format(x)+"", nf.format(y)+"", nf.format(z)+"", nf.format(errX)+"", nf.format(errY)+"", nf.format(errZ)+"", nf.format(ux)+"", nf.format(uy)+"", nf.format(uz)+"", clusterId[0]+"", clusterId[1]+"", trackId +"", nTrackClusters +"", nTrackHits +"" );
        System.out.println(tb.toString());
    }
}
