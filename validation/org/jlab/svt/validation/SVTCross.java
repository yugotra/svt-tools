package org.jlab.svt.validation;

import org.jlab.evio.clas12.EvioDataBank;
import org.jlab.evio.clas12.EvioDataEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SVTCross {
    int id_;
    int sector_;
    int region_;
    double x_;
    double y_;
    double z_;
    double err_x_;
    double err_y_;
    double err_z_;
    double ux_;
    double uy_;
    double uz_;
    int[] cluster_id_;
//    int trj_id_;
    int[] trj_id_;
    int track_id_;
    int n_track_hits_;
    int n_track_clusters_;
    int n_offtrack_hits_;
    int n_offtrack_clusters_;
    ArrayList <SVTCluster> svt_track_clusters_;

    SVTCross() {
        id_=-1;
        sector_=-1;
        region_=-1;
        x_=-1;
        y_=-1;
        z_=-1;
        err_x_=-1;
        err_y_=-1;
        err_z_=-1;
        ux_=-1;
        uy_=-1;
        uz_=-1;
        cluster_id_ = new int[2];
        for(int i=0;i<2;++i) cluster_id_[i]=-1;
//        trj_id_ = -1;
        trj_id_ = new int[2];
        for(int i=0;i<2;++i) trj_id_[i]=-1;
        n_track_hits_=0;
        n_track_clusters_=0;
        n_offtrack_hits_=0;
        n_offtrack_clusters_=0;
        track_id_=-1;
        svt_track_clusters_ = new ArrayList<>();
    }

    SVTCross GetByModule(int region, int sector) {
        if(region_==region&&sector_==sector) return this;
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
            svt_track_clusters_.get(n_track_clusters_-1).id_=cluster_id;
            svt_track_clusters_.get(n_track_clusters_-1).id_=bank_SVTCluster.getInt("ID",cluster_row);
            sector=bank_SVTCluster.getInt("sector",cluster_row);
            layer=bank_SVTCluster.getInt("layer",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).sector_=sector;
            svt_track_clusters_.get(n_track_clusters_-1).layer_=layer;
            svt_track_clusters_.get(n_track_clusters_-1).size_=bank_SVTCluster.getInt("size",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).e_tot_=bank_SVTCluster.getDouble("ETot",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).seed_e_=bank_SVTCluster.getDouble("seedE",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).seed_strip_=bank_SVTCluster.getInt("seedStrip",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).centroid_=bank_SVTCluster.getDouble("centroid",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).hit1_id_=bank_SVTCluster.getInt("Hit1_ID",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).hit2_id_=bank_SVTCluster.getInt("Hit2_ID",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).hit3_id_=bank_SVTCluster.getInt("Hit3_ID",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).hit4_id_=bank_SVTCluster.getInt("Hit4_ID",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).hit5_id_=bank_SVTCluster.getInt("Hit5_ID",cluster_row);
            svt_track_clusters_.get(n_track_clusters_-1).cross_id_=id_;
            svt_track_clusters_.get(n_track_clusters_-1).track_id_=track_id_;
            svt_track_clusters_.get(n_track_clusters_-1).trj_id_=svt_trajectory.GetIdByModule(track_id_,layer,sector);
            svt_track_clusters_.get(n_track_clusters_-1).ReadTrackHits(event,cluster_id, print);
            n_track_hits_+=svt_track_clusters_.get(n_track_clusters_-1).n_track_hits_;
            if(print) svt_track_clusters_.get(n_track_clusters_-1).Show();
        }
    }

    public void AddTrackCluster() {
        SVTCluster svtCluster = new SVTCluster();
        svt_track_clusters_.add(svtCluster);
        n_track_clusters_=svt_track_clusters_.size();
    }

    void Show() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
//     System.out.println("Cross " + id_ + " " + sector_ + " " + region_ + " " + nf.format(x_) + " " + nf.format(y_) + " " + nf.format(z_) + " " + nf.format(err_x_) + 
// 		       " " + nf.format(err_y_) + " " + nf.format(err_z_) + " " + nf.format(ux_) + " " + nf.format(uy_) + " " + nf.format(uz_) + " " + " clId " + cluster_id_[0] + 
//                        " " + cluster_id_[1] + " trId " + track_id_ + " ncl " + n_track_clusters_ + " nh " + n_track_hits_);

        TableBuilder tb = new TableBuilder();
        tb.addRow("CrossID", "sector", "region", "x", "y", "z", "err_x", "err_y", "err_z", "ux", "uy", "uz", "Cl1_ID", "Cl2_ID", "Tk_ID", "N_cl", "N_h");
        tb.addRow("-----", "----", "-----");
        tb.addRow(id_+"", sector_+"", region_+"", nf.format(x_)+"", nf.format(y_)+"", nf.format(z_)+"", nf.format(err_x_)+"", nf.format(err_y_)+"", nf.format(err_z_)+"", nf.format(ux_)+"", nf.format(uy_)+"", nf.format(uz_)+"", cluster_id_[0]+"", cluster_id_[1]+"", track_id_+"", n_track_clusters_+"", n_track_hits_+"" );
        System.out.println(tb.toString());
    }
}
