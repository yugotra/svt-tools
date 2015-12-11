package org.jlab.svt.validation;

import dnl.utils.text.table.TextTable;
import org.jlab.evio.clas12.EvioDataBank;
import org.jlab.evio.clas12.EvioDataEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

public class SVTEvent {

    static long eventNr;
    static int nTracks;
    static int nTrajectories;
    static int nTrackCrosses;
    static int nTrackClusters;
    static int nTrackHits;
    static int nOfftrackCrosses;
    static int nOfftrackClusters;
    static int nOfftrackHits;
    static int nCrosses;
    static int nClusters;
    static int nHits;
    static int nDgtzs;
    static ArrayList<SVTCosmicTrack> svtCosmicTracks;
    static ArrayList <SVTTrajectory> svtTrajectories;
    static ArrayList <SVTCross> svtTrackCrosses;
    static ArrayList <SVTCluster> svtTrackClusters;
    static ArrayList <SVTHit> svtTrackHits;
    static ArrayList <SVTCross> svtOfftrackCrosses;
    static ArrayList <SVTCluster> svtOfftrackClusters;
    static ArrayList <SVTHit> svtOfftrackHits;
    static ArrayList <SVTDgtz> svtDgtzs;
    private Object[][] data;

    public SVTEvent(){
        svtCosmicTracks = new ArrayList<>();
        svtCosmicTracks.clear();
        svtTrajectories = new ArrayList<>();
        svtTrajectories.clear();
        svtTrackCrosses = new ArrayList<>();
        svtTrackCrosses.clear();
        svtTrackClusters = new ArrayList<>();
        svtTrackClusters.clear();
        svtTrackHits = new ArrayList<>();
        svtTrackHits.clear();
        svtOfftrackCrosses = new ArrayList<>();
        svtOfftrackCrosses.clear();
        svtOfftrackClusters = new ArrayList<>();
        svtOfftrackClusters.clear();
        svtOfftrackHits = new ArrayList<>();
        svtOfftrackHits.clear();
        svtDgtzs = new ArrayList<>();
        svtDgtzs.clear();
        nTracks =0;
        nTrajectories =0;
        nTrackCrosses =0;
        nTrackClusters =0;
        nTrackHits =0;
        nOfftrackCrosses =0;
        nOfftrackClusters =0;
        nOfftrackHits =0;
        nCrosses =0;
        nClusters =0;
        nHits =0;
        nDgtzs =0;
    }

    ArrayList<SVTCosmicTrack> getSvtTracks() {
        return svtCosmicTracks;
    }

    public void SetEventNumber(long event_nr) {
        eventNr =event_nr;
    }

    public void FillEventHistos(EvioDataEvent event, SVTHistos svthistos) {

        if(event.hasBank("BSTRec::Trajectory")){
            EvioDataBank bank_SVTTrajectory = (EvioDataBank) event.getBank("BSTRec::Trajectory");
            int nrows=bank_SVTTrajectory.rows();
            for(int row = 0; row < nrows; row++){
                int sector=bank_SVTTrajectory.getInt("SectorTrackIntersPlane",row);
                int layer=bank_SVTTrajectory.getInt("LayerTrackIntersPlane",row);
                double phi=bank_SVTTrajectory.getDouble("PhiTrackIntersPlane",row);
                double theta=bank_SVTTrajectory.getDouble("ThetaTrackIntersPlane",row);
                double angle_3d=bank_SVTTrajectory.getDouble("trkToMPlnAngl",row);
            }
        }
        if(event.hasBank("BSTRec::Crosses")){
            EvioDataBank bank_SVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
            int nrows=bank_SVTCross.rows();
            svthistos.histoMap.get("event_crossMultiplicity").h_.fill(nrows);
            for(int row = 0; row < nrows; ++row){
                double cross_x=bank_SVTCross.getDouble("x",row);
                double err_x=bank_SVTCross.getDouble("err_x",row);
                double cross_y=bank_SVTCross.getDouble("y",row);
                double err_y=bank_SVTCross.getDouble("err_y",row);
                double cross_z=bank_SVTCross.getDouble("z",row);
                double err_z=bank_SVTCross.getDouble("err_z",row);
                svthistos.histoMap.get("event_crossX").h_.fill(cross_x);
                svthistos.histoMap.get("event_crossY").h_.fill(cross_y);
                svthistos.histoMap.get("event_crossZ").h_.fill(cross_z);
                svthistos.histoMap.get("event_crossErrX").h_.fill(err_x);
                svthistos.histoMap.get("event_crossErrY").h_.fill(err_y);
                svthistos.histoMap.get("event_crossErrZ").h_.fill(err_z);
            }
        }
        if(event.hasBank("BSTRec::Clusters")){
            EvioDataBank bank_SVTCluster = (EvioDataBank) event.getBank("BSTRec::Clusters");
            int nrows=bank_SVTCluster.rows();
            svthistos.histoMap.get("event_clusterMultiplicity").h_.fill(nrows);
            for(int row = 0; row < nrows; ++row){
                int cluster_size=bank_SVTCluster.getInt("size",row);
                double cluster_charge=bank_SVTCluster.getDouble("ETot",row);
                double seed_charge=bank_SVTCluster.getDouble("seedE",row);
                int seed=bank_SVTCluster.getInt("seedStrip",row);
                double centroid=bank_SVTCluster.getDouble("centroid",row);
                svthistos.histoMap.get("event_centroid").h_.fill(centroid);
                svthistos.histoMap.get("event_clusterCharge").h_.fill(cluster_charge);
                svthistos.histoMap.get("event_seedCharge").h_.fill(seed_charge);
                svthistos.histoMap.get("event_seedStrip").h_.fill(seed);
                svthistos.histoMap.get("event_stripMultiplicity").h_.fill(cluster_size);
            }
        }
        if(event.hasBank("BSTRec::Hits")){
            EvioDataBank bank_SVTHit = (EvioDataBank) event.getBank("BSTRec::Hits");
            int nrows=bank_SVTHit.rows();
            svthistos.histoMap.get("event_hitMultiplicity").h_.fill(nrows);
            for(int row = 0; row < nrows; ++row){
                int sector=bank_SVTHit.getInt("sector",row);
                int layer=bank_SVTHit.getInt("layer",row);
                int strip=bank_SVTHit.getInt("strip",row);
                svthistos.histoMap.get("event_hitStrip").h_.fill(strip);
                svthistos.histoMap.get("event_occupancy_sensor").h2_.fill(strip,Sensor(layer,sector));
            }
        }
        if(event.hasBank("BST::dgtz")) {
            EvioDataBank bank_SVTDgtz = (EvioDataBank) event.getBank("BST::dgtz");
            int nrows=bank_SVTDgtz.rows();
            for(int row = 0; row < nrows; ++row){
                int strip=bank_SVTDgtz.getInt("strip",row);
                int adc=bank_SVTDgtz.getInt("ADC",row);
                int bco=bank_SVTDgtz.getInt("bco",row);
                svthistos.histoMap.get("event_dgtzStrip").h_.fill(strip);
                svthistos.histoMap.get("event_hitAdc").h_.fill(adc);
                svthistos.histoMap.get("event_hitBco").h_.fill(bco);
            }
        }
    }

    public void FillOffTrackHistos(SVTHistos svthistos) {

        svthistos.histoMap.get("off_crossMultiplicity").h_.fill(nOfftrackCrosses);
        svthistos.histoMap.get("off_clusterMultiplicity").h_.fill(nOfftrackClusters);
        svthistos.histoMap.get("off_hitMultiplicity").h_.fill(nOfftrackHits);
        for(int i = 0; i< svtOfftrackCrosses.size(); ++i) {
            double cross_x= svtOfftrackCrosses.get(i).x_;
            double cross_y= svtOfftrackCrosses.get(i).y_;
            double cross_z= svtOfftrackCrosses.get(i).z_;
            double err_x= svtOfftrackCrosses.get(i).err_x_;
            double err_y= svtOfftrackCrosses.get(i).err_y_;
            double err_z= svtOfftrackCrosses.get(i).err_z_;
            svthistos.histoMap.get("off_crossX").h_.fill(cross_x);
            svthistos.histoMap.get("off_crossY").h_.fill(cross_y);
            svthistos.histoMap.get("off_crossZ").h_.fill(cross_z);
            svthistos.histoMap.get("off_crossErrX").h_.fill(err_x);
            svthistos.histoMap.get("off_crossErrY").h_.fill(err_y);
            svthistos.histoMap.get("off_crossErrZ").h_.fill(err_z);
        }
        for(int i = 0; i< svtOfftrackClusters.size(); ++i) {
            int layer= svtOfftrackClusters.get(i).layer_;
            int sector= svtOfftrackClusters.get(i).sector_;
            int seed= svtOfftrackClusters.get(i).seed_strip_;
            double centroid= svtOfftrackClusters.get(i).centroid_;
            double seed_charge= svtOfftrackClusters.get(i).seed_e_;
            double cluster_charge= svtOfftrackClusters.get(i).e_tot_;
            double cluster_size= svtOfftrackClusters.get(i).size_;
            svthistos.histoMap.get("off_clusterCharge").h_.fill(cluster_charge);
            svthistos.histoMap.get("off_seedStrip").h_.fill(seed);
            svthistos.histoMap.get("off_seedCharge").h_.fill(seed_charge);
            svthistos.histoMap.get("off_centroid").h_.fill(centroid);
            svthistos.histoSensorMap.get("off_seedCharge").h[layer-1][sector-1].fill(seed_charge);
            svthistos.histoSensorMap.get("off_clusterCharge").h[layer-1][sector-1].fill(cluster_charge);


            if(nTracks ==1) {
                // System.out.println(eventNr+" "+layer+" "+sector);
                int trj_id=Trajectory(0).GetIdByModule(0,layer,sector);
                if(trj_id!=-1) {
                    double local_angle_3d=Trajectory(0).angle_3d_.get(trj_id);
                    // System.out.println(trj_id+" "+local_angle_3d);
//                    if(Math.abs(local_angle_3d-90)>45)
                    svthistos.histoSensorMap.get("off_seedStrip").h[layer-1][sector-1].fill(seed);
//                    if(seed==190&&sector==10&&layer==1) System.out.println(eventNr);
                }
            }

            svthistos.histoSensorMap.get("off_stripMultiplicity").h[layer-1][sector-1].fill(cluster_size);
            svthistos.histoMap.get("off_stripMultiplicity").h_.fill(cluster_size);
            int region=(layer%2==0 ? layer/2 : (layer+1)/2);
            svthistos.histoRegionMap.get("off_clusterCharge").h[region-1].fill(cluster_charge);
        }
        for(int i = 0; i< svtOfftrackHits.size(); ++i) {
            int layer= svtOfftrackHits.get(i).layer_;
            int sector= svtOfftrackHits.get(i).sector_;
            int strip= svtOfftrackHits.get(i).strip_;
            int channel=(layer%2==0 ? strip+255 : strip-1);
            int adc= svtOfftrackHits.get(i).adc_;
            int bco= svtOfftrackHits.get(i).bco_;
            svthistos.histoMap.get("off_hitStrip").h_.fill(strip);
            svthistos.histoMap.get("off_hitAdc").h_.fill(adc);
            svthistos.histoMap.get("off_hitBco").h_.fill(bco);
            svthistos.histoMap.get("off_occupancy_module").h2_.fill(channel,Module(layer,sector));
            svthistos.histoMap.get("off_occupancy_sensor").h2_.fill(strip,Sensor(layer,sector));
            svthistos.histoSensorMap.get("off_adc").h[layer-1][sector-1].fill(adc);
            svthistos.histoSensorMap.get("off_strip").h[layer-1][sector-1].fill(strip);
            // if(layer==1&&sector==2&&strip==15) println(eventNr);
        }
    }

    public void FillTrackHistos(SVTHistos svthistos) {

        svthistos.histoMap.get("trackMultiplicity").h_.fill(nTracks);
        svthistos.histoMap.get("crossMultiplicity").h_.fill(nTrackCrosses);
        svthistos.histoMap.get("clusterMultiplicity").h_.fill(nTrackClusters);
        svthistos.histoMap.get("hitMultiplicity").h_.fill(nTrackHits);
        for(int i = 0; i< nTracks; ++i) {
            if(TrackClusters(i,0).get(0).trj_id_==-1) return; // catch for bad Trajectory bank
            double phi=Tracks().get(i).phi_;
            double theta=Tracks().get(i).theta_;
            double kf_chi2=Tracks().get(i).kf_chi2_;
            int kf_ndf=Tracks().get(i).kf_ndf_;
            svthistos.histoMap.get("normChi2").h_.fill(kf_chi2/(double)kf_ndf);
            svthistos.histoMap.get("phi").h_.fill(phi);
            svthistos.histoMap.get("theta").h_.fill(theta);
            svthistos.histoMap.get("thetaPhi").h2_.fill(phi,theta);
            for(int j=0;j<TrackCrosses(i).size();++j) {
                int region=TrackCrosses(i).get(j).region_;
                int sector=TrackCrosses(i).get(j).sector_;
                double cross_x=TrackCrosses(i).get(j).x_;
                double cross_y=TrackCrosses(i).get(j).y_;
                double cross_z=TrackCrosses(i).get(j).z_;
                double err_x=TrackCrosses(i).get(j).err_x_;
                double err_y=TrackCrosses(i).get(j).err_y_;
                double err_z=TrackCrosses(i).get(j).err_z_;
                svthistos.histoMap.get("crossX").h_.fill(cross_x);
                svthistos.histoMap.get("crossY").h_.fill(cross_y);
                svthistos.histoMap.get("crossZ").h_.fill(cross_z);
                svthistos.histoMap.get("crossErrX").h_.fill(err_x);
                svthistos.histoMap.get("crossErrY").h_.fill(err_y);
                svthistos.histoMap.get("crossErrZ").h_.fill(err_z);
                svthistos.histoRegionMap.get("crossX").h[region-1].fill(cross_x);
                svthistos.histoRegionMap.get("crossY").h[region-1].fill(cross_y);
                svthistos.histoRegionMap.get("crossZ").h[region-1].fill(cross_z);
                svthistos.histoRegionMap.get("crossErrX").h[region-1].fill(err_x);
                svthistos.histoRegionMap.get("crossErrY").h[region-1].fill(err_y);
                svthistos.histoRegionMap.get("crossErrZ").h[region-1].fill(err_z);
                svthistos.histoModuleMap.get("crossX").h_[region-1][sector-1].fill(cross_x);
                svthistos.histoModuleMap.get("crossY").h_[region-1][sector-1].fill(cross_y);
                svthistos.histoModuleMap.get("crossZ").h_[region-1][sector-1].fill(cross_z);
                svthistos.histoModuleMap.get("crossErrX").h_[region-1][sector-1].fill(err_x);
                svthistos.histoModuleMap.get("crossErrY").h_[region-1][sector-1].fill(err_y);
                svthistos.histoModuleMap.get("crossErrZ").h_[region-1][sector-1].fill(err_z);
                for(int k=0;k<TrackClusters(i,j).size();++k) {




                    int trj_id;
                    if(TrackClusters(i,j).get(k).trj_id_!=-1) trj_id=TrackClusters(i,j).get(k).trj_id_;
                    else trj_id=0;
// !!!!!!!!!!!!! temporary to kludge the bug with track id in traj bank


//                    System.out.println(i+" "+j+" "+k+" "+Trajectory(i).phi_.size()+" "+TrackClusters(i,j).get(k).trj_id_);



                    double local_phi=Trajectory(i).phi_.get(trj_id);
                    double local_theta=Trajectory(i).theta_.get(trj_id);
                    double local_angle_3d=Trajectory(i).angle_3d_.get(trj_id);
                    double local_x=Trajectory(i).x_.get(trj_id);
                    double local_y=Trajectory(i).y_.get(trj_id);
                    double local_z=Trajectory(i).z_.get(trj_id);
                    int layer=TrackClusters(i,j).get(k).layer_;
                    double cluster_charge=TrackClusters(i,j).get(k).e_tot_;
                    int cluster_size=TrackClusters(i,j).get(k).size_;
                    double centroid=TrackClusters(i,j).get(k).centroid_;
                    double seed=TrackClusters(i,j).get(k).seed_strip_;
                    double seed_charge=TrackClusters(i,j).get(k).seed_e_;
                    svthistos.histoMap.get("centroid").h_.fill(centroid);
                    svthistos.histoMap.get("clusterCharge").h_.fill(cluster_charge);
                    svthistos.histoMap.get("seedCharge").h_.fill(seed_charge);
                    svthistos.histoMap.get("seedStrip").h_.fill(seed);
                    svthistos.histoMap.get("stripMultiplicity").h_.fill(cluster_size);
                    if(!Double.isNaN(local_phi)) svthistos.histoMap.get("localPhi").h_.fill(local_phi);
                    if(!Double.isNaN(local_theta)) svthistos.histoMap.get("localTheta").h_.fill(local_theta);
                    if(!Double.isNaN(local_angle_3d)) {
                        svthistos.histoMap.get("localTrackAngle").h_.fill(local_angle_3d);
                        svthistos.histoMap.get("correctedClusterCharge").h_.fill(cluster_charge*Math.cos(local_angle_3d));
                    }
                    if(!Double.isNaN(local_x)) svthistos.histoMap.get("localX").h_.fill(local_x);
                    if(!Double.isNaN(local_y)) svthistos.histoMap.get("localY").h_.fill(local_y);
                    if(!Double.isNaN(local_z)) svthistos.histoMap.get("localZ").h_.fill(local_z);
                    svthistos.histoSensorMap.get("clusterCharge").h[layer-1][sector-1].fill(cluster_charge);
                    svthistos.histoSensorMap.get("correctedClusterCharge").h[layer-1][sector-1].fill(cluster_charge*Math.cos(local_angle_3d));
                    svthistos.histoSensorMap.get("seedCharge").h[layer-1][sector-1].fill(seed_charge);
                    svthistos.histoSensorMap.get("stripMultiplicity").h[layer-1][sector-1].fill(cluster_size);
                    svthistos.histoSensorMap.get("localPhi").h[layer-1][sector-1].fill(local_phi);
                    svthistos.histoSensorMap.get("localTheta").h[layer-1][sector-1].fill(local_theta);
                    svthistos.histoSensorMap.get("localTrackAngle").h[layer-1][sector-1].fill(local_angle_3d);
                    svthistos.histoModuleMap.get("clusterCharge").h_[region-1][sector-1].fill(cluster_charge);
                    svthistos.histoRegionMap.get("clusterCharge").h[region-1].fill(cluster_charge);
                    svthistos.histoRegionMap.get("localPhi").h[region-1].fill(local_phi);
                    svthistos.histoRegionMap.get("localTheta").h[region-1].fill(local_theta);
                    svthistos.histoRegionMap.get("localTrackAngle").h[region-1].fill(local_angle_3d);
                    for(int l=0;l<TrackHits(i,j,k).size();++l) {
                        int strip=TrackHits(i,j,k).get(l).strip_;
                        int adc=TrackHits(i,j,k).get(l).adc_;
                        int bco=TrackHits(i,j,k).get(l).bco_;
                        int channel=(layer%2==0 ? strip+255 : strip-1);
                        svthistos.histoMap.get("hitStrip").h_.fill(strip);
                        svthistos.histoMap.get("hitAdc").h_.fill(adc);
                        svthistos.histoMap.get("hitBco").h_.fill(bco);
                        svthistos.histoMap.get("occupancy_module").h2_.fill(channel,Module(layer,sector));
                        svthistos.histoMap.get("occupancy_sensor").h2_.fill(strip,Sensor(layer,sector));
                        svthistos.histoSensorMap.get("strip").h[layer-1][sector-1].fill(strip);
                        svthistos.histoSensorMap.get("adc").h[layer-1][sector-1].fill(adc);
                        double fit_residual=TrackHits(i,j,k).get(l).fit_residual_;
                            svthistos.histoSensorMap.get("fitResidual").h[layer-1][sector-1].fill(fit_residual);
                            svthistos.histoSensorMap.get("fitResidual_angle").h2[layer-1][sector-1].fill(local_angle_3d,fit_residual);
                            svthistos.histoSensorMap.get("fitResidual_phi").h2[layer-1][sector-1].fill(local_phi,fit_residual);
                            svthistos.histoSensorMap.get("fitResidual_theta").h2[layer-1][sector-1].fill(local_theta,fit_residual);
                            svthistos.histoSensorMap.get("fitResidual_x").h2[layer-1][sector-1].fill(local_x,fit_residual);
                            svthistos.histoSensorMap.get("fitResidual_y").h2[layer-1][sector-1].fill(local_y,fit_residual);
                            svthistos.histoSensorMap.get("fitResidual_z").h2[layer-1][sector-1].fill(local_z,fit_residual);
                        if(!Double.isNaN(local_phi)) {
                            int angle_min=svthistos.histoLorentzMap.get("sizeAngle").angle_min_;
                            int min_angle=svthistos.histoLorentzMap.get("sizeAngle").min_angle_;
                            int angle_max=svthistos.histoLorentzMap.get("sizeAngle").angle_max_;
                            int angle_space=svthistos.histoLorentzMap.get("sizeAngle").angle_space_;
                            int array_size=svthistos.histoLorentzMap.get("sizeAngle").array_size_;
                            angle_min=min_angle;
                            angle_max = angle_min + angle_space;
                            for(int jl = 0; jl < array_size; jl++){
                                if((local_phi-90)<angle_max&&(local_phi-90)>angle_min) svthistos.histoLorentzMap.get("sizeAngle").h_[jl].fill(cluster_size);
                                angle_min += angle_space;
                                angle_max += angle_space;
                            }//jl
                        }
                    }
                }
            }
        }//n_tracks
    }

    public void AddTrack() {
        SVTCosmicTrack svtCosmicTrack = new SVTCosmicTrack();
        svtCosmicTracks.add(svtCosmicTrack);
        nTracks = svtCosmicTracks.size();
    }

    public void AddTrackCross() {
        SVTCross svtCross = new SVTCross();
        svtTrackCrosses.add(svtCross);
        nTrackCrosses = svtTrackCrosses.size();
    }

    public void AddTrackCluster() {
        SVTCluster svtCluster = new SVTCluster();
        svtTrackClusters.add(svtCluster);
        nTrackClusters = svtTrackClusters.size();
    }

    public void AddTrackHit() {
        SVTHit svtHit = new SVTHit();
        svtTrackHits.add(svtHit);
        nTrackHits = svtTrackHits.size();
    }

    public void AddDgtz() {
        SVTDgtz svtDgtz = new SVTDgtz();
        svtDgtzs.add(svtDgtz);
        nDgtzs = svtDgtzs.size();
    }

    SVTTrajectory Trajectory(int track_id) {
        return svtCosmicTracks.get(track_id).svt_trajectory_;
    }

    public void AddOffTrackCross() {
        SVTCross svtCross = new SVTCross();
        svtOfftrackCrosses.add(svtCross);
        nOfftrackCrosses = svtOfftrackCrosses.size();
    }

    public void AddOffTrackCluster() {
        SVTCluster svtCluster = new SVTCluster();
        svtOfftrackClusters.add(svtCluster);
        nOfftrackClusters = svtOfftrackClusters.size();
    }

    static public void AddOffTrackHit() {
        SVTHit svtHit = new SVTHit();
        svtOfftrackHits.add(svtHit);
        nOfftrackHits = svtOfftrackHits.size();
    }

    ArrayList <SVTCosmicTrack> Tracks() {
        return svtCosmicTracks;
    }

    static ArrayList <SVTCross> TrackCrosses(int track_id) {
        return svtCosmicTracks.get(track_id).svt_track_crosses_;
    }

    static ArrayList <SVTCluster> TrackClusters(int track_id, int cross_id) {
        return svtCosmicTracks.get(track_id).svt_track_crosses_.get(cross_id).svt_track_clusters_;
    }

    static ArrayList <SVTHit> TrackHits(int track_id, int cross_id, int cluster_id) {
        return svtCosmicTracks.get(track_id).svt_track_crosses_.get(cross_id).svt_track_clusters_.get(cluster_id).svt_track_hits_;
    }

    boolean SkipEvent(EvioDataEvent event, boolean print) {
        if(!event.hasBank("BSTRec::Cosmics")) {
            if(print) System.out.println(Constants.RED+"No tracks found, skip..."+Constants.RESET);
            return true;
        }
        else {
            EvioDataBank bank_SVTCosmics = (EvioDataBank) event.getBank("BSTRec::Cosmics");
            int nrows=bank_SVTCosmics.rows();
            if(nrows!=1) {
                if(print) System.out.println(Constants.RED+nrows+"multiple tracks, skip..."+Constants.RESET);
                return true; // 1 track per event
            }
            else{
                ArrayList<Integer> track_crosses =new ArrayList<Integer>();
                for(int i=0;i<Constants.NLAYERS;++i) {
                    String varname = String.format("Cross%d_ID", i+1);
                    int cross_id=bank_SVTCosmics.getInt(varname,0); // get cross id's for the 1st track
                    if(cross_id!=-1) track_crosses.add(cross_id);
                }
                if(track_crosses.size()!=8) {
                    if(print) System.out.println(Constants.RED+nrows+"less than 8 crosses, skip..."+Constants.RESET);
                    return true; // 8 crosses per track
                }
                else {
                    Collections.sort(track_crosses);
                    for(int i=0;i<track_crosses.size();++i) System.out.print(track_crosses.get(i)+" "); System.out.println("");
                    for(int i=0;i<track_crosses.size();++i) if(track_crosses.get(i)!=i+1) {
                        // if(print)
                        System.out.println(Constants.RED+"no cross in every region, skip..."+Constants.RESET);
                        return true;
                    }
                }
            }
        }
    /*
    int[][] CrossPerSector = new int[Constants.NREGIONS][Constants.NSECTORS];
    if(event.hasBank("BSTRec::Crosses")){
      EvioDataBank bank_SVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
      int sector;
      int region;
      int nrows=bank_SVTCross.rows();
      if(nrows!=8) { 
//       if(nrows<7) { 
	if(print) System.out.println(RED+nrows+" crosses, skip..."+RESET);
	return true; // 8 crosses per event
      }
      for(int row = 0; row < nrows; ++row){
	sector=bank_SVTCross.getInt("sector",row);
	region=bank_SVTCross.getInt("region",row);
	if(CrossPerSector[region-1][sector-1]==0) CrossPerSector[region-1][sector-1]++;
	else {
	  if(print) System.out.println(RED+"Multiple crosses per sector, skip..."+RESET);
	  return true; // one cross per sector
	}
      }
    }
    else return true;
    */
//        System.out.println("pass");
        return false;
    }

    void ReadOffTrack(EvioDataEvent event, boolean print) {
        ReadOffTrackCrosses(event,print);
        ReadOffTrackClusters(event,print);
        ReadOffTrackHits(event,print);
    }

    void ReadTracks(EvioDataEvent event, boolean print) {
        if(event.hasBank("BSTRec::Cosmics")){
            EvioDataBank bank_SVTCosmics = (EvioDataBank) event.getBank("BSTRec::Cosmics");
            nTracks =bank_SVTCosmics.rows();
            for(int row = 0; row < bank_SVTCosmics.rows(); ++row){
                AddTrack();
                svtCosmicTracks.get(row).id_=bank_SVTCosmics.getInt("ID",row);
                svtCosmicTracks.get(row).trkline_yx_slope_=bank_SVTCosmics.getDouble("trkline_yx_slope",row);
                svtCosmicTracks.get(row).trkline_yx_interc_=bank_SVTCosmics.getDouble("trkline_yx_interc",row);
                svtCosmicTracks.get(row).trkline_yz_slope_=bank_SVTCosmics.getDouble("trkline_yz_slope",row);
                svtCosmicTracks.get(row).trkline_yz_interc_=bank_SVTCosmics.getDouble("trkline_yz_interc",row);
                svtCosmicTracks.get(row).phi_=bank_SVTCosmics.getDouble("phi",row);
                svtCosmicTracks.get(row).theta_=bank_SVTCosmics.getDouble("theta",row);
                svtCosmicTracks.get(row).kf_chi2_=bank_SVTCosmics.getDouble("KF_chi2",row);
                // svtCosmicTracks.get(row).kf_ndf_=(int)bank_SVTCosmics.getDouble("KF_ndf",row);
                svtCosmicTracks.get(row).kf_ndf_=bank_SVTCosmics.getInt("KF_ndf",row);
                svtCosmicTracks.get(row).ReadTrackTrajectory(event, svtCosmicTracks.get(row).id_,print);
                ArrayList<Integer> track_crosses =new ArrayList<Integer>();
                for(int i=0;i<Constants.NLAYERS;++i) {
                    String varname = String.format("Cross%d_ID", i+1);
                    int cross_id=bank_SVTCosmics.getInt(varname,row);
                    svtCosmicTracks.get(row).cross_id_[i]=cross_id;
                    if(cross_id!=-1) track_crosses.add(cross_id);
                }
                Collections.sort(track_crosses);
                for(int i=0;i<track_crosses.size();++i) svtCosmicTracks.get(row).ReadTrackCrosses(event,track_crosses.get(i),print);
                if(print) svtCosmicTracks.get(row).Show();
                nTrackCrosses +=track_crosses.size();
                nTrackHits += svtCosmicTracks.get(row).n_track_hits_;
            }
            nTrackClusters = nTrackCrosses *2;
        }
    }

    static int Module(int layer, int sector) {
        int[] shift={0,0,10,10,24,24,42,42};
        if(layer==0||sector==0) return -1;
        return sector+shift[layer-1]-1;
    }

    static int Sensor(int layer, int sector) {
        int[] shift={0,10,20,34,48,66,84,108};
        if(layer==0||sector==0) return -1;
        return sector+shift[layer-1]-1;
    }

    static boolean IsTrackCross(int cross_id) {
        for(int i = 0; i< nTracks; ++i) {
            for(int j=0;j<TrackCrosses(i).size();++j)  if(cross_id==TrackCrosses(i).get(j).id_) return true;
        }
        return false;
    }

    boolean IsTrackCluster(int cluster_id) {
        for(int i = 0; i< nTracks; ++i) {
            for(int j=0;j<TrackCrosses(i).size();++j) {
                for(int k=0;k<TrackClusters(i,j).size();++k) if(cluster_id==TrackClusters(i,j).get(k).id_) return true;
            }
        }
        return false;
    }

    boolean IsTrackHit(int hit_id) {
        for(int i = 0; i< nTracks; ++i) {
            for(int j=0;j<TrackCrosses(i).size();++j) {
                for(int k=0;k<TrackClusters(i,j).size();++k) {
                    for(int l=0;l<TrackHits(i,j,k).size();++l) if(hit_id==TrackHits(i,j,k).get(l).id_) return true;
                }
            }
        }
        return false;
    }

    void ReadOffTrackCrosses(EvioDataEvent event, boolean print) {
        if(event.hasBank("BSTRec::Crosses")){
            EvioDataBank bank_SVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
            int nclrows;
            EvioDataBank bank_SVTCluster = null;
            if(event.hasBank("BSTRec::Clusters")) {
                bank_SVTCluster = (EvioDataBank) event.getBank("BSTRec::Clusters");
                nclrows=bank_SVTCluster.rows();
            }
            else nclrows=0;
            int nrows=bank_SVTCross.rows();
            int cross_id=-1;
            for(int row = 0; row < nrows; ++row){
                cross_id=bank_SVTCross.getInt("ID",row);
                if(IsTrackCross(cross_id)) continue;
                AddOffTrackCross();
                svtOfftrackCrosses.get(nOfftrackCrosses -1).id_=cross_id;
                svtOfftrackCrosses.get(nOfftrackCrosses -1).sector_=bank_SVTCross.getInt("sector",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).region_=bank_SVTCross.getInt("region",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).x_=bank_SVTCross.getDouble("x",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).err_x_=bank_SVTCross.getDouble("err_x",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).ux_=bank_SVTCross.getDouble("ux",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).y_=bank_SVTCross.getDouble("y",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).err_y_=bank_SVTCross.getDouble("err_y",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).uy_=bank_SVTCross.getDouble("uy",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).z_=bank_SVTCross.getDouble("z",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).err_z_=bank_SVTCross.getDouble("err_z",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).uz_=bank_SVTCross.getDouble("uz",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).cluster_id_[0]=bank_SVTCross.getInt("Cluster1_ID",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).cluster_id_[1]=bank_SVTCross.getInt("Cluster2_ID",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).n_offtrack_clusters_=2;
                for(int clrow = 0; clrow < nclrows; ++clrow) {
                    if(bank_SVTCluster.getInt("ID",clrow)== svtOfftrackCrosses.get(nOfftrackCrosses -1).cluster_id_[0] ||
                            bank_SVTCluster.getInt("ID",clrow)== svtOfftrackCrosses.get(nOfftrackCrosses -1).cluster_id_[1])
                        svtOfftrackCrosses.get(nOfftrackCrosses -1).n_offtrack_hits_ += bank_SVTCluster.getInt("size",clrow);
                }
                if(print) {
                    System.out.println(Constants.RED+"Off track"+Constants.RESET);
                    svtOfftrackCrosses.get(nOfftrackCrosses -1).Show();
                }
            }
        }
    }

    void ReadOffTrackClusters(EvioDataEvent event, boolean print) {
        if(event.hasBank("BSTRec::Clusters")){
            EvioDataBank bank_SVTCluster = (EvioDataBank) event.getBank("BSTRec::Clusters");
            int ncrrows;
            EvioDataBank bank_SVTCross = null;
            if(event.hasBank("BSTRec::Crosses")) {
                bank_SVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
                ncrrows=bank_SVTCross.rows();
            }
            else ncrrows=0;
            int nrows=bank_SVTCluster.rows();
            int cluster_id=-1;
            for(int row = 0; row < nrows; ++row){
                cluster_id=bank_SVTCluster.getInt("ID",row);
                if(IsTrackCluster(cluster_id)) continue;
                AddOffTrackCluster();
                svtOfftrackClusters.get(nOfftrackClusters -1).id_=cluster_id;
                svtOfftrackClusters.get(nOfftrackClusters -1).sector_=bank_SVTCluster.getInt("sector",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).layer_=bank_SVTCluster.getInt("layer",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).size_=bank_SVTCluster.getInt("size",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).e_tot_=bank_SVTCluster.getDouble("ETot",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).seed_e_=bank_SVTCluster.getDouble("seedE",row);


                for(int row1 = 0; row1 < nrows; ++row1) {

                    int sector=bank_SVTCluster.getInt("sector",row1);
                    int layer=bank_SVTCluster.getInt("layer",row1);
                    if(sector!= svtOfftrackClusters.get(nOfftrackClusters -1).sector_||
                            layer!= svtOfftrackClusters.get(nOfftrackClusters -1).layer_+1) continue;
                    svtOfftrackClusters.get(nOfftrackClusters - 1).seed_strip_ = bank_SVTCluster.getInt("seedStrip", row);
if(svtOfftrackClusters.get(nOfftrackClusters -1).layer_==1&& svtOfftrackClusters.get(nOfftrackClusters -1).sector_==1&& svtOfftrackClusters.get(nOfftrackClusters - 1).seed_strip_==200) System.out.println(eventNr);
                }





                svtOfftrackClusters.get(nOfftrackClusters -1).centroid_=bank_SVTCluster.getDouble("centroid",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).hit1_id_=bank_SVTCluster.getInt("Hit1_ID",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).hit2_id_=bank_SVTCluster.getInt("Hit2_ID",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).hit3_id_=bank_SVTCluster.getInt("Hit3_ID",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).hit4_id_=bank_SVTCluster.getInt("Hit4_ID",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).hit5_id_=bank_SVTCluster.getInt("Hit5_ID",row);

                for(int crrow = 0; crrow < ncrrows; ++crrow) {
                    if(cluster_id==bank_SVTCross.getInt("Cluster1_ID",crrow) ||
                            cluster_id==bank_SVTCross.getInt("Cluster2_ID",crrow))
                        svtOfftrackClusters.get(nOfftrackClusters -1).cross_id_=bank_SVTCross.getInt("ID",crrow);
                }
                if(print) {
                    System.out.println(Constants.RED+"Off track"+Constants.RESET);
                    svtOfftrackClusters.get(nOfftrackClusters -1).Show();
                }
            }
        }
    }

    void ReadOffTrackHits(EvioDataEvent event, boolean print) {
        if(event.hasBank("BSTRec::Hits")){
            EvioDataBank bank_SVTHit = (EvioDataBank) event.getBank("BSTRec::Hits");
            int nrows=bank_SVTHit.rows();
            EvioDataBank bank_SVTDgtz = null;
            boolean dgtzBank;
            if(event.hasBank("BST::dgtz")) {
                bank_SVTDgtz = (EvioDataBank) event.getBank("BST::dgtz");
                dgtzBank=true;
            }
            else {
                dgtzBank=false;
                System.out.println("No dgtz bank in event");
                // return;
            }
            int hit_id=-1;
            for(int row = 0; row < nrows; ++row){
                // hit_id=row+1;
                // hit_id=bank_SVTHit.getInt("id",row);
                hit_id=bank_SVTHit.getInt("ID",row);
                if(IsTrackHit(hit_id)) continue;
                AddOffTrackHit();
                svtOfftrackHits.get(nOfftrackHits -1).id_=hit_id;
                svtOfftrackHits.get(nOfftrackHits -1).sector_=bank_SVTHit.getInt("sector",row);
                svtOfftrackHits.get(nOfftrackHits -1).layer_=bank_SVTHit.getInt("layer",row);
                svtOfftrackHits.get(nOfftrackHits -1).strip_=bank_SVTHit.getInt("strip",row);
                svtOfftrackHits.get(nOfftrackHits -1).fit_residual_=bank_SVTHit.getDouble("fitResidual",row);
                svtOfftrackHits.get(nOfftrackHits -1).trking_stat_=bank_SVTHit.getInt("trkingStat",row);
                svtOfftrackHits.get(nOfftrackHits -1).cluster_id_=bank_SVTHit.getInt("clusterID",row);
                if(dgtzBank) {
                    svtOfftrackHits.get(nOfftrackHits -1).adc_=bank_SVTDgtz.getInt("ADC",row);
                    svtOfftrackHits.get(nOfftrackHits -1).bco_=bank_SVTDgtz.getInt("bco",row);
                    svtOfftrackHits.get(nOfftrackHits -1).hit_n_=bank_SVTDgtz.getInt("hitn",row);
                }
                for(int clrow = 0; clrow < svtOfftrackClusters.size(); ++clrow) {
                    if(svtOfftrackHits.get(nOfftrackHits -1).cluster_id_== svtOfftrackClusters.get(clrow).id_)
                        svtOfftrackHits.get(nOfftrackHits -1).cross_id_= svtOfftrackClusters.get(clrow).cross_id_;
                }
                if(print) {
                    System.out.println(Constants.RED+"Off track"+Constants.RESET);
                    svtOfftrackHits.get(nOfftrackHits -1).Show();
                }
            }
        }
    }

    /*
    void ReadHits(EvioDataEvent event, boolean print) {
      if(event.hasBank("BSTRec::Hits")){
        EvioDataBank bank_SVTHit = (EvioDataBank) event.getBank("BSTRec::Hits");
        int nrows=bank_SVTHit.rows();
        EvioDataBank bank_SVTDgtz;
        boolean dgtzBank;
        if(event.hasBank("BST::dgtz")) {
      bank_SVTDgtz = (EvioDataBank) event.getBank("BST::dgtz");
      dgtzBank=true;
        }
        else {
      dgtzBank=false;
      System.out.println("No dgtz bank in event");
      return;
        }
        int hit_id=-1;
        for(int row = 0; row < nrows; ++row){
      // hit_id=row+1;
      hit_id=bank_SVTHit.getInt("id",row);
      // hit_id=bank_SVTHit.getInt("ID",row);
      AddHit();
      svt_hits_.get(nHits-1).id_=hit_id;
      svt_hits_.get(nHits-1).sector_=bank_SVTHit.getInt("sector",row);
      svt_hits_.get(nHits-1).layer_=bank_SVTHit.getInt("layer",row);
      svt_hits_.get(nHits-1).strip_=bank_SVTHit.getInt("strip",row);
      svt_hits_.get(nHits-1).fit_residual_=bank_SVTHit.getDouble("fitResidual",row);
      svt_hits_.get(nHits-1).trking_stat_=bank_SVTHit.getInt("trkingStat",row);
      svt_hits_.get(nHits-1).cluster_id_=bank_SVTHit.getInt("clusterID",row);
      if(dgtzBank) {
        svt_hits_.get(nHits-1).adc_=bank_SVTDgtz.getInt("ADC",row);
        svt_hits_.get(nHits-1).bco_=bank_SVTDgtz.getInt("bco",row);
        svt_hits_.get(nHits-1).hit_n_=bank_SVTDgtz.getInt("hitn",row);
      }
      for(int clrow = 0; clrow < svt_clusters_.size(); ++clrow) {
        if(svt_hits_.get(nHits-1).cluster_id_==svt_clusters_.get(clrow).id_)
          svt_hits_.get(nHits-1).cross_id_=svt_clusters_.get(clrow).cross_id_;
      }
      if(print) {
        System.out.println(RED+"Off track"+RESET);
        svt_hits_.get(nHits-1).Show();
      }
        }
      }
    }
    */
    public void Show() {
        svtCosmicTracks.get(0).Show();
        svtCosmicTracks.get(0).svt_trajectory_.Show();
    }

//  String Color(Object data,int index) {
//    if(index==0) return RED + data + RESET;
//    return data;
//  }

    public void ShowTracks() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        NumberFormat nf0 = NumberFormat.getInstance();
        nf0.setMaximumFractionDigits(0);
        String[] trackColumnNames = {"TkID","sl_yx","int_yx","sl_yz","int_yz","theta","phi","chi2","ndf",
                "Cr1_ID","Cr2_ID","Cr3_ID","Cr4_ID","Cr5_ID","Cr6_ID","Cr7_ID","Cr8_ID","n_cr","n_cl","n_h"};
        int n_vars = trackColumnNames.length;
        data = new Object[nTracks][n_vars];
        for(int i = 0; i< nTracks; ++i) {
            svtCosmicTracks.get(i).svt_trajectory_.Show();
            data[i][0]= svtCosmicTracks.get(i).id_;
            data[i][1]=nf.format(svtCosmicTracks.get(i).trkline_yx_slope_);
            data[i][2]=nf.format(svtCosmicTracks.get(i).trkline_yx_interc_);
            data[i][3]=nf.format(svtCosmicTracks.get(i).trkline_yz_slope_);
            data[i][4]=nf.format(svtCosmicTracks.get(i).trkline_yz_interc_);
            data[i][5]=nf0.format(svtCosmicTracks.get(i).theta_);
            data[i][6]=nf0.format(svtCosmicTracks.get(i).phi_);
            data[i][7]=nf0.format(svtCosmicTracks.get(i).kf_chi2_);
            data[i][8]= svtCosmicTracks.get(i).kf_ndf_;
            data[i][9]= svtCosmicTracks.get(i).cross_id_[0];
            data[i][10]= svtCosmicTracks.get(i).cross_id_[1];
            data[i][11]= svtCosmicTracks.get(i).cross_id_[2];
            data[i][12]= svtCosmicTracks.get(i).cross_id_[3];
            data[i][13]= svtCosmicTracks.get(i).cross_id_[4];
            data[i][14]= svtCosmicTracks.get(i).cross_id_[5];
            data[i][15]= svtCosmicTracks.get(i).cross_id_[6];
            data[i][16]= svtCosmicTracks.get(i).cross_id_[7];
            data[i][17]= nTrackCrosses;
            data[i][18]= nTrackClusters;
            data[i][19]= nTrackHits;
        }
        TextTable tt = new TextTable(trackColumnNames, data);
        tt.printTable();
        String[] crossesColumnNames = {"CrID","sector","region","x","y","z","errx","erry","errz","ux","uy","uz","Cl1ID","Cl2ID","TkID","n_cl","n_h"};
        n_vars = crossesColumnNames.length;
        data = new Object[nTrackCrosses][n_vars];
        int m=0;
        for(int i = 0; i< nTracks; ++i) {
            for(int j=0;j<TrackCrosses(i).size();++j) {
// 	if(j%2==0) { 
// 	}
// 	else { 
// 	  data[m][0]=RED+TrackCrosses(i).get(j).id_+RESET;
// 	}
                data[m][0]=TrackCrosses(i).get(j).id_;
                data[m][1]=TrackCrosses(i).get(j).sector_;
                data[m][2]=TrackCrosses(i).get(j).region_;
                data[m][3]=nf0.format(TrackCrosses(i).get(j).x_);
                data[m][4]=nf0.format(TrackCrosses(i).get(j).y_);
                data[m][5]=nf0.format(TrackCrosses(i).get(j).z_);
                data[m][6]=nf.format(TrackCrosses(i).get(j).err_x_);
                data[m][7]=nf.format(TrackCrosses(i).get(j).err_y_);
                data[m][8]=nf.format(TrackCrosses(i).get(j).err_z_);
                data[m][9]=nf.format(TrackCrosses(i).get(j).ux_);
                data[m][10]=nf.format(TrackCrosses(i).get(j).uy_);
                data[m][11]=nf.format(TrackCrosses(i).get(j).uz_);
                data[m][12]=TrackCrosses(i).get(j).cluster_id_[0];
                data[m][13]=TrackCrosses(i).get(j).cluster_id_[1];
                data[m][14]=TrackCrosses(i).get(j).track_id_;
                data[m][15]=TrackCrosses(i).get(j).n_track_clusters_;
                data[m][16]=TrackCrosses(i).get(j).n_track_hits_;
                m++;
// 	}
// 	  data[m][1]=RED+TrackCrosses(i).get(j).sector_+RESET;
// 	  data[m][2]=RED+TrackCrosses(i).get(j).region_+RESET;
// 	  data[m][3]=RED+nf0.format(TrackCrosses(i).get(j).x_)+RESET;
// 	  data[m][4]=RED+nf0.format(TrackCrosses(i).get(j).y_)+RESET;
// 	  data[m][5]=RED+nf0.format(TrackCrosses(i).get(j).z_)+RESET;
// 	  data[m][6]=RED+nf.format(TrackCrosses(i).get(j).err_x_)+RESET;
// 	  data[m][7]=RED+nf.format(TrackCrosses(i).get(j).err_y_)+RESET;
// 	  data[m][8]=RED+nf.format(TrackCrosses(i).get(j).err_z_)+RESET;
// 	  data[m][9]=RED+nf.format(TrackCrosses(i).get(j).ux_)+RESET;
// 	  data[m][10]=RED+nf.format(TrackCrosses(i).get(j).uy_)+RESET;
// 	  data[m][11]=RED+nf.format(TrackCrosses(i).get(j).uz_)+RESET;
// 	  data[m][12]=RED+TrackCrosses(i).get(j).cluster_id_[0]+RESET;
// 	  data[m][13]=RED+TrackCrosses(i).get(j).cluster_id_[1]+RESET;
// 	  data[m][14]=RED+TrackCrosses(i).get(j).track_id_+RESET;
// 	  data[m][15]=RED+TrackCrosses(i).get(j).nTrackClusters+RESET;
// 	  data[m][16]=RED+TrackCrosses(i).get(j).nTrackHits+RESET;
// 	}
            }
        }
        TextTable ttcr = new TextTable(crossesColumnNames, data);
        ttcr.printTable();

        String[] clustersColumnNames = {"ClID","sector","layer","size","eTot","eSeed","seed","centroid","Hit1","Hit2","Hit3","Hit4","Hit5","CrID","TkID","TrjID"};
        n_vars = clustersColumnNames.length;
        data = new Object[nTrackClusters][n_vars];
        m=0;
        for(int i = 0; i< nTracks; ++i) {
            for(int j=0;j<TrackCrosses(i).size();++j) {
                for(int k=0;k<TrackClusters(i,j).size();++k) {
                    data[m][0]=TrackClusters(i,j).get(k).id_;
                    data[m][1]=TrackClusters(i,j).get(k).sector_;
                    data[m][2]=TrackClusters(i,j).get(k).layer_;
                    data[m][3]=TrackClusters(i,j).get(k).size_;
                    data[m][4]=nf0.format(TrackClusters(i,j).get(k).e_tot_);
                    data[m][5]=nf0.format(TrackClusters(i,j).get(k).seed_e_);
                    data[m][6]=TrackClusters(i,j).get(k).seed_strip_;
                    data[m][7]=nf.format(TrackClusters(i,j).get(k).centroid_);
                    data[m][8]=TrackClusters(i,j).get(k).hit1_id_;
                    data[m][9]=TrackClusters(i,j).get(k).hit2_id_;
                    data[m][10]=TrackClusters(i,j).get(k).hit3_id_;
                    data[m][11]=TrackClusters(i,j).get(k).hit4_id_;
                    data[m][12]=TrackClusters(i,j).get(k).hit5_id_;
                    data[m][13]=TrackClusters(i,j).get(k).cross_id_;
                    data[m][14]=TrackClusters(i,j).get(k).track_id_;
                    data[m][15]=TrackClusters(i,j).get(k).trj_id_;
                    m++;
                }
            }
        }
        TextTable ttcl = new TextTable(clustersColumnNames, data);
        ttcl.printTable();

        String[] hitsColumnNames = {"HitID","sector","layer","strip","residual","tkStat","adc","bco","hitN","ClID","CrID","TkID"};
        n_vars = hitsColumnNames.length;
        data = new Object[nTrackHits][n_vars];
        m=0;
        for(int i = 0; i< nTracks; ++i) {
            for(int j=0;j<TrackCrosses(i).size();++j) {
                for(int k=0;k<TrackClusters(i,j).size();++k) {
                    for(int l=0;l<TrackHits(i,j,k).size();++l) {
                        data[m][0]=TrackHits(i,j,k).get(l).id_;
                        data[m][1]=TrackHits(i,j,k).get(l).sector_;
                        data[m][2]=TrackHits(i,j,k).get(l).layer_;
                        data[m][3]=TrackHits(i,j,k).get(l).strip_;
                        data[m][4]=nf.format(TrackHits(i,j,k).get(l).fit_residual_);
                        data[m][5]=TrackHits(i,j,k).get(l).trking_stat_;
                        data[m][6]=TrackHits(i,j,k).get(l).adc_;
                        data[m][7]=TrackHits(i,j,k).get(l).bco_;
                        data[m][8]=TrackHits(i,j,k).get(l).hit_n_;
                        data[m][9]=TrackHits(i,j,k).get(l).cluster_id_;
                        data[m][10]=TrackHits(i,j,k).get(l).cross_id_;
                        data[m][11]=TrackHits(i,j,k).get(l).track_id_;
                        m++;
                    }
                }
            }
        }
        TextTable tth = new TextTable(hitsColumnNames, data);
        tth.printTable();

    }

    public void ShowOffTrack() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        NumberFormat nf0 = NumberFormat.getInstance();
        nf0.setMaximumFractionDigits(0);
        String[] offtrackCrossesColumnNames = {"CrID","sector","region","x","y","z","errx","erry","errz","ux","uy","uz","Cl1ID","Cl2ID","TkID","n_cl","n_h"};
        int n_vars = offtrackCrossesColumnNames.length;
        data = new Object[svtOfftrackCrosses.size()][n_vars];
        for(int j = 0; j< svtOfftrackCrosses.size(); ++j) {
            data[j][0]= svtOfftrackCrosses.get(j).id_;
            data[j][1]= svtOfftrackCrosses.get(j).sector_;
            data[j][2]= svtOfftrackCrosses.get(j).region_;
            data[j][3]=nf0.format(svtOfftrackCrosses.get(j).x_);
            data[j][4]=nf0.format(svtOfftrackCrosses.get(j).y_);
            data[j][5]=nf0.format(svtOfftrackCrosses.get(j).z_);
            data[j][6]=nf.format(svtOfftrackCrosses.get(j).err_x_);
            data[j][7]=nf.format(svtOfftrackCrosses.get(j).err_y_);
            data[j][8]=nf.format(svtOfftrackCrosses.get(j).err_z_);
            data[j][9]=nf.format(svtOfftrackCrosses.get(j).ux_);
            data[j][10]=nf.format(svtOfftrackCrosses.get(j).uy_);
            data[j][11]=nf.format(svtOfftrackCrosses.get(j).uz_);
            data[j][12]= svtOfftrackCrosses.get(j).cluster_id_[0];
            data[j][13]= svtOfftrackCrosses.get(j).cluster_id_[1];
            data[j][14]= svtOfftrackCrosses.get(j).track_id_;
            data[j][15]= svtOfftrackCrosses.get(j).n_offtrack_clusters_;
            data[j][16]= svtOfftrackCrosses.get(j).n_offtrack_hits_;
        }
        TextTable ttcr = new TextTable(offtrackCrossesColumnNames, data);
        ttcr.printTable();

        String[] offtrackClustersColumnNames = {"ClID","sector","layer","size","eTot","eSeed","seed","centroid","Hit1","Hit2","Hit3","Hit4","Hit5","CrID","TkID"};
        n_vars = offtrackClustersColumnNames.length;
        data = new Object[svtOfftrackClusters.size()][n_vars];
        for(int k = 0; k< svtOfftrackClusters.size(); ++k) {
            data[k][0]= svtOfftrackClusters.get(k).id_;
            data[k][1]= svtOfftrackClusters.get(k).sector_;
            data[k][2]= svtOfftrackClusters.get(k).layer_;
            data[k][3]= svtOfftrackClusters.get(k).size_;
            data[k][4]=nf0.format(svtOfftrackClusters.get(k).e_tot_);
            data[k][5]=nf0.format(svtOfftrackClusters.get(k).seed_e_);
            data[k][6]= svtOfftrackClusters.get(k).seed_strip_;
            data[k][7]=nf.format(svtOfftrackClusters.get(k).centroid_);
            data[k][8]= svtOfftrackClusters.get(k).hit1_id_;
            data[k][9]= svtOfftrackClusters.get(k).hit2_id_;
            data[k][10]= svtOfftrackClusters.get(k).hit3_id_;
            data[k][11]= svtOfftrackClusters.get(k).hit4_id_;
            data[k][12]= svtOfftrackClusters.get(k).hit5_id_;
            data[k][13]= svtOfftrackClusters.get(k).cross_id_;
            data[k][14]= svtOfftrackClusters.get(k).track_id_;
        }
        TextTable ttcl = new TextTable(offtrackClustersColumnNames, data);
        ttcl.printTable();

        String[] offtrackHitsColumnNames = {"HitID","sector","layer","strip","residual","tkStat","adc","bco","hitN","ClID","CrID","TkID"};
        n_vars = offtrackHitsColumnNames.length;
        data = new Object[svtOfftrackHits.size()][n_vars];
        for(int l = 0; l< svtOfftrackHits.size(); ++l) {
            data[l][0]= svtOfftrackHits.get(l).id_;
            data[l][1]= svtOfftrackHits.get(l).sector_;
            data[l][2]= svtOfftrackHits.get(l).layer_;
            data[l][3]= svtOfftrackHits.get(l).strip_;
            data[l][4]=nf.format(svtOfftrackHits.get(l).fit_residual_);
            data[l][5]= svtOfftrackHits.get(l).trking_stat_;
            data[l][6]= svtOfftrackHits.get(l).adc_;
            data[l][7]= svtOfftrackHits.get(l).bco_;
            data[l][8]= svtOfftrackHits.get(l).hit_n_;
            data[l][9]= svtOfftrackHits.get(l).cluster_id_;
            data[l][10]= svtOfftrackHits.get(l).cross_id_;
            data[l][11]= svtOfftrackHits.get(l).track_id_;
        }
        TextTable tth = new TextTable(offtrackHitsColumnNames, data);
        tth.printTable();

    }
}
