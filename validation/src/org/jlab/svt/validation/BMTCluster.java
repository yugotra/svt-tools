package org.jlab.svt.validation;

import org.jlab.evio.clas12.EvioDataBank;
import org.jlab.evio.clas12.EvioDataEvent;
import java.util.ArrayList;

public class BMTCluster {
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
    ArrayList <BMTHit> bmtTrackHits;

    BMTCluster() {
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
        bmtTrackHits = new ArrayList<>();
    }

    BMTCluster GetBySensor(int layer, int sector) {
        if(this.layer ==layer&& this.sector ==sector) return this;
        else return null;
    }

    void ReadTrackHits(EvioDataEvent event, int cluster_id, boolean print) {
        if(event.hasBank("BSTRec::Hits")){
            EvioDataBank bank_BMTHit = (EvioDataBank) event.getBank("BSTRec::Hits");
            boolean dgtzBank;
            EvioDataBank bank_BMTDgtz = null;
            if(event.hasBank("BST::dgtz")) {
                bank_BMTDgtz = (EvioDataBank) event.getBank("BST::dgtz");
                dgtzBank=true;
            }
            else {
                dgtzBank=false;
                System.out.println("No dgtz bank in event");
            }
            int nrows=bank_BMTHit.rows();
            int hit_row=-1;
            for(int row = 0; row < nrows; row++){
                if(bank_BMTHit.getInt("clusterID",row)!=cluster_id) continue;
                hit_row++;
                AddTrackHit();
                // bmtTrackHits.get(hit_row).id=row+1;
                // bmtTrackHits.get(hit_row).id=bank_BMTHit.getInt("id",row);
                bmtTrackHits.get(hit_row).id =bank_BMTHit.getInt("ID",row);
                bmtTrackHits.get(hit_row).sector =bank_BMTHit.getInt("sector",row);
                bmtTrackHits.get(hit_row).layer =bank_BMTHit.getInt("layer",row);
                bmtTrackHits.get(hit_row).strip =bank_BMTHit.getInt("strip",row);
                bmtTrackHits.get(hit_row).fitResidual =bank_BMTHit.getDouble("fitResidual",row);
                bmtTrackHits.get(hit_row).trkingStat =bank_BMTHit.getInt("trkingStat",row);
                bmtTrackHits.get(hit_row).clusterId =bank_BMTHit.getInt("clusterID",row);
                if(dgtzBank) {
                    bmtTrackHits.get(hit_row).adc =bank_BMTDgtz.getInt("ADC", bmtTrackHits.get(hit_row).id -1);
//                    bmtTrackHits.get(hit_row).bco =bank_BMTDgtz.getInt("bco", bmtTrackHits.get(hit_row).id -1);
                    bmtTrackHits.get(hit_row).hitN =bank_BMTDgtz.getInt("hitn", bmtTrackHits.get(hit_row).id -1);
                }
                bmtTrackHits.get(hit_row).crossId = crossId;
                bmtTrackHits.get(hit_row).trjId = trjId;
                bmtTrackHits.get(hit_row).trackId = trackId;
                if(print) bmtTrackHits.get(hit_row).Show();
            }
        }
    }

    public void AddTrackHit() {
        BMTHit bmtHit = new BMTHit();
        bmtTrackHits.add(bmtHit);
        nTrackHits = bmtTrackHits.size();
    }

    void Show() {
        System.out.println("Cluster " + id + " " + sector + " " + layer + " " + size + " " + eTot + " " +
                seedE + " " + seedStrip + " " + centroid + " h " + hit1Id + " " + hit2Id + " " + hit3Id + " " +
                hit4Id + " " + hit5Id + " crId " + crossId + " trId " + trackId + " trjId " + trjId);
    }
}
