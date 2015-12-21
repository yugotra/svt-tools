package org.jlab.svt.validation;

import org.jlab.evio.clas12.EvioDataBank;
import org.jlab.evio.clas12.EvioDataEvent;
import java.util.ArrayList;

public class SVTCluster {
    int id;
    int sector;
    int layer;
    int size;
    double eTot;
    double seedE;
    int seedStrip;
    double centroid;
    int hit1Id;
    int hit2Id;
    int hit3Id;
    int hit4Id;
    int hit5Id;
    int crossId;
    int trjId;
    int trackId;
    int nTrackHits;
    ArrayList <SVTHit> svtTrackHits;

    SVTCluster() {
        id =-1;
        sector =-1;
        layer =-1;
        size =-1;
        eTot =-1;
        seedE =-1;
        seedStrip =-1;
        centroid =-1;
        hit1Id =-1;
        hit2Id =-1;
        hit3Id =-1;
        hit4Id =-1;
        hit5Id =-1;
        crossId =-1;
        trjId =-1;
        trackId =-1;
        nTrackHits =0;
        svtTrackHits = new ArrayList<>();
    }

    SVTCluster GetBySensor(int layer, int sector) {
        if(this.layer ==layer&& this.sector ==sector) return this;
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
                // svtTrackHits.get(hit_row).id=row+1;
                // svtTrackHits.get(hit_row).id=bank_SVTHit.getInt("id",row);
                svtTrackHits.get(hit_row).id =bank_SVTHit.getInt("ID",row);
                svtTrackHits.get(hit_row).sector =bank_SVTHit.getInt("sector",row);
                svtTrackHits.get(hit_row).layer =bank_SVTHit.getInt("layer",row);
                svtTrackHits.get(hit_row).strip =bank_SVTHit.getInt("strip",row);
                svtTrackHits.get(hit_row).fitResidual =bank_SVTHit.getDouble("fitResidual",row);
                svtTrackHits.get(hit_row).trkingStat =bank_SVTHit.getInt("trkingStat",row);
                svtTrackHits.get(hit_row).clusterId =bank_SVTHit.getInt("clusterID",row);
                if(dgtzBank) {
                    svtTrackHits.get(hit_row).adc =bank_SVTDgtz.getInt("ADC", svtTrackHits.get(hit_row).id -1);
                    svtTrackHits.get(hit_row).bco =bank_SVTDgtz.getInt("bco", svtTrackHits.get(hit_row).id -1);
                    svtTrackHits.get(hit_row).hitN =bank_SVTDgtz.getInt("hitn", svtTrackHits.get(hit_row).id -1);
                }
                svtTrackHits.get(hit_row).crossId = crossId;
                svtTrackHits.get(hit_row).trjId = trjId;
                svtTrackHits.get(hit_row).trackId = trackId;
                if(print) svtTrackHits.get(hit_row).Show();
            }
        }
    }

    public void AddTrackHit() {
        SVTHit svtHit = new SVTHit();
        svtTrackHits.add(svtHit);
        nTrackHits = svtTrackHits.size();
    }

    void Show() {
        System.out.println("Cluster " + id + " " + sector + " " + layer + " " + size + " " + eTot + " " +
                seedE + " " + seedStrip + " " + centroid + " h " + hit1Id + " " + hit2Id + " " + hit3Id + " " +
                hit4Id + " " + hit5Id + " crId " + crossId + " trId " + trackId + " trjId " + trjId);
    }
}
