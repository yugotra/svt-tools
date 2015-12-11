package org.jlab.svt.validation;

import org.jlab.evio.clas12.EvioDataBank;
import org.jlab.evio.clas12.EvioDataEvent;
import java.util.ArrayList;

public class SVTCluster {
    int id_;
    int sector_;
    int layer_;
    int size_;
    double e_tot_;
    double seed_e_;
    int seed_strip_;
    double centroid_;
    int hit1_id_;
    int hit2_id_;
    int hit3_id_;
    int hit4_id_;
    int hit5_id_;
    int cross_id_;
    int trj_id_;
    int track_id_;
    int n_track_hits_;
    ArrayList <SVTHit> svt_track_hits_;

    SVTCluster() {
        id_=-1;
        sector_=-1;
        layer_=-1;
        size_=-1;
        e_tot_=-1;
        seed_e_=-1;
        seed_strip_=-1;
        centroid_=-1;
        hit1_id_=-1;
        hit2_id_=-1;
        hit3_id_=-1;
        hit4_id_=-1;
        hit5_id_=-1;
        cross_id_=-1;
        trj_id_=-1;
        track_id_=-1;
        n_track_hits_=0;
        svt_track_hits_ = new ArrayList<>();
    }

    SVTCluster GetBySensor(int layer, int sector) {
        if(layer_==layer&&sector_==sector) return this;
        else return null;
    }

    void ReadTrackHits(EvioDataEvent event, int cluster_id, boolean print) {
        if(event.hasBank("BSTRec::Hits")){
            EvioDataBank bank_SVTHit = (EvioDataBank) event.getBank("BSTRec::Hits");
            boolean dgtzBank;
            EvioDataBank bank_SVTDgtz = null;
            if(event.hasBank("BST::dgtz")) {
                bank_SVTDgtz = (EvioDataBank) event.getBank("BST::dgtz");
                dgtzBank=true;
            }
            else {
                dgtzBank=false;
                System.out.println("No dgtz bank in event");
            }
            int nrows=bank_SVTHit.rows();
            int hit_row=-1;
            for(int row = 0; row < nrows; row++){
                if(bank_SVTHit.getInt("clusterID",row)!=cluster_id) continue;
                hit_row++;
                AddTrackHit();
                // svtTrackHits.get(hit_row).id_=row+1;
                // svtTrackHits.get(hit_row).id_=bank_SVTHit.getInt("id",row);
                svt_track_hits_.get(hit_row).id_=bank_SVTHit.getInt("ID",row);
                svt_track_hits_.get(hit_row).sector_=bank_SVTHit.getInt("sector",row);
                svt_track_hits_.get(hit_row).layer_=bank_SVTHit.getInt("layer",row);
                svt_track_hits_.get(hit_row).strip_=bank_SVTHit.getInt("strip",row);
                svt_track_hits_.get(hit_row).fit_residual_=bank_SVTHit.getDouble("fitResidual",row);
                svt_track_hits_.get(hit_row).trking_stat_=bank_SVTHit.getInt("trkingStat",row);
                svt_track_hits_.get(hit_row).cluster_id_=bank_SVTHit.getInt("clusterID",row);
                if(dgtzBank) {
                    svt_track_hits_.get(hit_row).adc_=bank_SVTDgtz.getInt("ADC",svt_track_hits_.get(hit_row).id_-1);
                    svt_track_hits_.get(hit_row).bco_=bank_SVTDgtz.getInt("bco",svt_track_hits_.get(hit_row).id_-1);
                    svt_track_hits_.get(hit_row).hit_n_=bank_SVTDgtz.getInt("hitn",svt_track_hits_.get(hit_row).id_-1);
                }
                svt_track_hits_.get(hit_row).cross_id_=cross_id_;
                svt_track_hits_.get(hit_row).trj_id_=trj_id_;
                svt_track_hits_.get(hit_row).track_id_=track_id_;
                if(print) svt_track_hits_.get(hit_row).Show();
            }
        }
    }

    public void AddTrackHit() {
        SVTHit svtHit = new SVTHit();
        svt_track_hits_.add(svtHit);
        n_track_hits_=svt_track_hits_.size();
    }

    void Show() {
        System.out.println("Cluster " + id_ + " " + sector_ + " " + layer_ + " " + size_ + " " + e_tot_ + " " +
                seed_e_ + " " + seed_strip_ + " " + centroid_ + " h " + hit1_id_ + " " + hit2_id_ + " " + hit3_id_ + " " +
                hit4_id_ + " " + hit5_id_ + " crId " + cross_id_ + " trId " + track_id_ + " trjId " + trj_id_);
    }
}
