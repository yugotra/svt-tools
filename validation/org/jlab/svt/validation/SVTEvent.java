package org.jlab.svt.validation;

import dnl.utils.text.table.TextTable;
import org.jlab.evio.clas12.EvioDataBank;
import org.jlab.evio.clas12.EvioDataEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

public class SVTEvent {

    static long event_nr_;
    static int n_tracks_;
    static int n_trajectories_;
    static int n_track_crosses_;
    static int n_track_clusters_;
    static int n_track_hits_;
    static int n_offtrack_crosses_;
    static int n_offtrack_clusters_;
    static int n_offtrack_hits_;
    static int n_crosses_;
    static int n_clusters_;
    static int n_hits_;
    static int n_dgtzs_;
    static ArrayList<SVTCosmicTrack> svt_tracks_;
    static ArrayList <SVTTrajectory> svt_trajectories_;
    static ArrayList <SVTCross> svt_track_crosses_;
    static ArrayList <SVTCluster> svt_track_clusters_;
    static ArrayList <SVTHit> svt_track_hits_;
    static ArrayList <SVTCross> svt_offtrack_crosses_;
    static ArrayList <SVTCluster> svt_offtrack_clusters_;
    static ArrayList <SVTHit> svt_offtrack_hits_;
    static ArrayList <SVTDgtz> svt_dgtzs_;
    private Object[][] data;

    public SVTEvent(){
        svt_tracks_ = new ArrayList<>();
        svt_tracks_.clear();
        svt_trajectories_ = new ArrayList<>();
        svt_trajectories_.clear();
        svt_track_crosses_ = new ArrayList<>();
        svt_track_crosses_.clear();
        svt_track_clusters_ = new ArrayList<>();
        svt_track_clusters_.clear();
        svt_track_hits_ = new ArrayList<>();
        svt_track_hits_.clear();
        svt_offtrack_crosses_ = new ArrayList<>();
        svt_offtrack_crosses_.clear();
        svt_offtrack_clusters_ = new ArrayList<>();
        svt_offtrack_clusters_.clear();
        svt_offtrack_hits_ = new ArrayList<>();
        svt_offtrack_hits_.clear();
        svt_dgtzs_ = new ArrayList<>();
        svt_dgtzs_.clear();
        n_tracks_=0;
        n_trajectories_=0;
        n_track_crosses_=0;
        n_track_clusters_=0;
        n_track_hits_=0;
        n_offtrack_crosses_=0;
        n_offtrack_clusters_=0;
        n_offtrack_hits_=0;
        n_crosses_=0;
        n_clusters_=0;
        n_hits_=0;
        n_dgtzs_=0;
    }

    ArrayList<SVTCosmicTrack> getSvtTracks() {
        return svt_tracks_;
    }

    public void SetEventNumber(long event_nr) {
        event_nr_=event_nr;
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
            svthistos.m_.get("event_crossMultiplicity").h_.fill(nrows);
            for(int row = 0; row < nrows; ++row){
                double cross_x=bank_SVTCross.getDouble("x",row);
                double err_x=bank_SVTCross.getDouble("err_x",row);
                double cross_y=bank_SVTCross.getDouble("y",row);
                double err_y=bank_SVTCross.getDouble("err_y",row);
                double cross_z=bank_SVTCross.getDouble("z",row);
                double err_z=bank_SVTCross.getDouble("err_z",row);
                svthistos.m_.get("event_crossX").h_.fill(cross_x);
                svthistos.m_.get("event_crossY").h_.fill(cross_y);
                svthistos.m_.get("event_crossZ").h_.fill(cross_z);
                svthistos.m_.get("event_crossErrX").h_.fill(err_x);
                svthistos.m_.get("event_crossErrY").h_.fill(err_y);
                svthistos.m_.get("event_crossErrZ").h_.fill(err_z);
            }
        }
        if(event.hasBank("BSTRec::Clusters")){
            EvioDataBank bank_SVTCluster = (EvioDataBank) event.getBank("BSTRec::Clusters");
            int nrows=bank_SVTCluster.rows();
            svthistos.m_.get("event_clusterMultiplicity").h_.fill(nrows);
            for(int row = 0; row < nrows; ++row){
                int cluster_size=bank_SVTCluster.getInt("size",row);
                double cluster_charge=bank_SVTCluster.getDouble("ETot",row);
                double seed_charge=bank_SVTCluster.getDouble("seedE",row);
                int seed=bank_SVTCluster.getInt("seedStrip",row);
                double centroid=bank_SVTCluster.getDouble("centroid",row);
                svthistos.m_.get("event_centroid").h_.fill(centroid);
                svthistos.m_.get("event_clusterCharge").h_.fill(cluster_charge);
                svthistos.m_.get("event_seedCharge").h_.fill(seed_charge);
                svthistos.m_.get("event_seedStrip").h_.fill(seed);
                svthistos.m_.get("event_stripMultiplicity").h_.fill(cluster_size);
            }
        }
        if(event.hasBank("BSTRec::Hits")){
            EvioDataBank bank_SVTHit = (EvioDataBank) event.getBank("BSTRec::Hits");
            int nrows=bank_SVTHit.rows();
            svthistos.m_.get("event_hitMultiplicity").h_.fill(nrows);
            for(int row = 0; row < nrows; ++row){
                int sector=bank_SVTHit.getInt("sector",row);
                int layer=bank_SVTHit.getInt("layer",row);
                int strip=bank_SVTHit.getInt("strip",row);
                svthistos.m_.get("event_hitStrip").h_.fill(strip);
                svthistos.m_.get("event_occupancy_sensor").h2_.fill(strip,Sensor(layer,sector));
            }
        }
        if(event.hasBank("BST::dgtz")) {
            EvioDataBank bank_SVTDgtz = (EvioDataBank) event.getBank("BST::dgtz");
            int nrows=bank_SVTDgtz.rows();
            for(int row = 0; row < nrows; ++row){
                int strip=bank_SVTDgtz.getInt("strip",row);
                int adc=bank_SVTDgtz.getInt("ADC",row);
                int bco=bank_SVTDgtz.getInt("bco",row);
                svthistos.m_.get("event_dgtzStrip").h_.fill(strip);
                svthistos.m_.get("event_hitAdc").h_.fill(adc);
                svthistos.m_.get("event_hitBco").h_.fill(bco);
            }
        }
    }

    public void FillOffTrackHistos(SVTHistos svthistos) {

        svthistos.m_.get("off_crossMultiplicity").h_.fill(n_offtrack_crosses_);
        svthistos.m_.get("off_clusterMultiplicity").h_.fill(n_offtrack_clusters_);
        svthistos.m_.get("off_hitMultiplicity").h_.fill(n_offtrack_hits_);
        for(int i=0;i<svt_offtrack_crosses_.size();++i) {
            double cross_x=svt_offtrack_crosses_.get(i).x_;
            double cross_y=svt_offtrack_crosses_.get(i).y_;
            double cross_z=svt_offtrack_crosses_.get(i).z_;
            double err_x=svt_offtrack_crosses_.get(i).err_x_;
            double err_y=svt_offtrack_crosses_.get(i).err_y_;
            double err_z=svt_offtrack_crosses_.get(i).err_z_;
            svthistos.m_.get("off_crossX").h_.fill(cross_x);
            svthistos.m_.get("off_crossY").h_.fill(cross_y);
            svthistos.m_.get("off_crossZ").h_.fill(cross_z);
            svthistos.m_.get("off_crossErrX").h_.fill(err_x);
            svthistos.m_.get("off_crossErrY").h_.fill(err_y);
            svthistos.m_.get("off_crossErrZ").h_.fill(err_z);
        }
        for(int i=0;i<svt_offtrack_clusters_.size();++i) {
            int layer=svt_offtrack_clusters_.get(i).layer_;
            int sector=svt_offtrack_clusters_.get(i).sector_;
            int seed=svt_offtrack_clusters_.get(i).seed_strip_;
            double centroid=svt_offtrack_clusters_.get(i).centroid_;
            double seed_charge=svt_offtrack_clusters_.get(i).seed_e_;
            double cluster_charge=svt_offtrack_clusters_.get(i).e_tot_;
            double cluster_size=svt_offtrack_clusters_.get(i).size_;
            svthistos.m_.get("off_clusterCharge").h_.fill(cluster_charge);
            svthistos.m_.get("off_seedStrip").h_.fill(seed);
            svthistos.m_.get("off_seedCharge").h_.fill(seed_charge);
            svthistos.m_.get("off_centroid").h_.fill(centroid);
            svthistos.m_sensor_.get("off_seedCharge").h_[layer-1][sector-1].fill(seed_charge);
            svthistos.m_sensor_.get("off_clusterCharge").h_[layer-1][sector-1].fill(cluster_charge);


            if(n_tracks_==1) {
                // System.out.println(event_nr_+" "+layer+" "+sector);
                int trj_id=Trajectory(0).GetIdByModule(0,layer,sector);
                if(trj_id!=-1) {
                    double local_angle_3d=Trajectory(0).angle_3d_.get(trj_id);
                    // System.out.println(trj_id+" "+local_angle_3d);
//                    if(Math.abs(local_angle_3d-90)>45)
                    svthistos.m_sensor_.get("off_seedStrip").h_[layer-1][sector-1].fill(seed);
//                    if(seed==190&&sector==10&&layer==1) System.out.println(event_nr_);
                }
            }

            svthistos.m_sensor_.get("off_stripMultiplicity").h_[layer-1][sector-1].fill(cluster_size);
            svthistos.m_.get("off_stripMultiplicity").h_.fill(cluster_size);
            int region=(layer%2==0 ? layer/2 : (layer+1)/2);
            svthistos.m_region_.get("off_clusterCharge").h_[region-1].fill(cluster_charge);
        }
        for(int i=0;i<svt_offtrack_hits_.size();++i) {
            int layer=svt_offtrack_hits_.get(i).layer_;
            int sector=svt_offtrack_hits_.get(i).sector_;
            int strip=svt_offtrack_hits_.get(i).strip_;
            int channel=(layer%2==0 ? strip+255 : strip-1);
            int adc=svt_offtrack_hits_.get(i).adc_;
            int bco=svt_offtrack_hits_.get(i).bco_;
            svthistos.m_.get("off_hitStrip").h_.fill(strip);
            svthistos.m_.get("off_hitAdc").h_.fill(adc);
            svthistos.m_.get("off_hitBco").h_.fill(bco);
            svthistos.m_.get("off_occupancy_module").h2_.fill(channel,Module(layer,sector));
            svthistos.m_.get("off_occupancy_sensor").h2_.fill(strip,Sensor(layer,sector));
            svthistos.m_sensor_.get("off_adc").h_[layer-1][sector-1].fill(adc);
            svthistos.m_sensor_.get("off_strip").h_[layer-1][sector-1].fill(strip);
            // if(layer==1&&sector==2&&strip==15) println(event_nr_);
        }
    }

    public void FillTrackHistos(SVTHistos svthistos) {

        svthistos.m_.get("trackMultiplicity").h_.fill(n_tracks_);
        svthistos.m_.get("crossMultiplicity").h_.fill(n_track_crosses_);
        svthistos.m_.get("clusterMultiplicity").h_.fill(n_track_clusters_);
        svthistos.m_.get("hitMultiplicity").h_.fill(n_track_hits_);
        for(int i=0;i<n_tracks_;++i) {
            if(TrackClusters(i,0).get(0).trj_id_==-1) return; // catch for bad Trajectory bank
            double phi=Tracks().get(i).phi_;
            double theta=Tracks().get(i).theta_;
            double kf_chi2=Tracks().get(i).kf_chi2_;
            int kf_ndf=Tracks().get(i).kf_ndf_;
            svthistos.m_.get("normChi2").h_.fill(kf_chi2/(double)kf_ndf);
            svthistos.m_.get("phi").h_.fill(phi);
            svthistos.m_.get("theta").h_.fill(theta);
            svthistos.m_.get("thetaPhi").h2_.fill(phi,theta);
            for(int j=0;j<TrackCrosses(i).size();++j) {
                int region=TrackCrosses(i).get(j).region_;
                int sector=TrackCrosses(i).get(j).sector_;
                double cross_x=TrackCrosses(i).get(j).x_;
                double cross_y=TrackCrosses(i).get(j).y_;
                double cross_z=TrackCrosses(i).get(j).z_;
                double err_x=TrackCrosses(i).get(j).err_x_;
                double err_y=TrackCrosses(i).get(j).err_y_;
                double err_z=TrackCrosses(i).get(j).err_z_;
                svthistos.m_.get("crossX").h_.fill(cross_x);
                svthistos.m_.get("crossY").h_.fill(cross_y);
                svthistos.m_.get("crossZ").h_.fill(cross_z);
                svthistos.m_.get("crossErrX").h_.fill(err_x);
                svthistos.m_.get("crossErrY").h_.fill(err_y);
                svthistos.m_.get("crossErrZ").h_.fill(err_z);
                svthistos.m_region_.get("crossX").h_[region-1].fill(cross_x);
                svthistos.m_region_.get("crossY").h_[region-1].fill(cross_y);
                svthistos.m_region_.get("crossZ").h_[region-1].fill(cross_z);
                svthistos.m_region_.get("crossErrX").h_[region-1].fill(err_x);
                svthistos.m_region_.get("crossErrY").h_[region-1].fill(err_y);
                svthistos.m_region_.get("crossErrZ").h_[region-1].fill(err_z);
                svthistos.m_module_.get("crossX").h_[region-1][sector-1].fill(cross_x);
                svthistos.m_module_.get("crossY").h_[region-1][sector-1].fill(cross_y);
                svthistos.m_module_.get("crossZ").h_[region-1][sector-1].fill(cross_z);
                svthistos.m_module_.get("crossErrX").h_[region-1][sector-1].fill(err_x);
                svthistos.m_module_.get("crossErrY").h_[region-1][sector-1].fill(err_y);
                svthistos.m_module_.get("crossErrZ").h_[region-1][sector-1].fill(err_z);
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
                    svthistos.m_.get("centroid").h_.fill(centroid);
                    svthistos.m_.get("clusterCharge").h_.fill(cluster_charge);
                    svthistos.m_.get("seedCharge").h_.fill(seed_charge);
                    svthistos.m_.get("seedStrip").h_.fill(seed);
                    svthistos.m_.get("stripMultiplicity").h_.fill(cluster_size);
                    if(!Double.isNaN(local_phi)) svthistos.m_.get("localPhi").h_.fill(local_phi);
                    if(!Double.isNaN(local_theta)) svthistos.m_.get("localTheta").h_.fill(local_theta);
                    if(!Double.isNaN(local_angle_3d)) {
                        svthistos.m_.get("localTrackAngle").h_.fill(local_angle_3d);
                        svthistos.m_.get("correctedClusterCharge").h_.fill(cluster_charge*Math.cos(local_angle_3d));
                    }
                    if(!Double.isNaN(local_x)) svthistos.m_.get("localX").h_.fill(local_x);
                    if(!Double.isNaN(local_y)) svthistos.m_.get("localY").h_.fill(local_y);
                    if(!Double.isNaN(local_z)) svthistos.m_.get("localZ").h_.fill(local_z);
                    svthistos.m_sensor_.get("clusterCharge").h_[layer-1][sector-1].fill(cluster_charge);
                    svthistos.m_sensor_.get("correctedClusterCharge").h_[layer-1][sector-1].fill(cluster_charge*Math.cos(local_angle_3d));
                    svthistos.m_sensor_.get("seedCharge").h_[layer-1][sector-1].fill(seed_charge);
                    svthistos.m_sensor_.get("stripMultiplicity").h_[layer-1][sector-1].fill(cluster_size);
                    svthistos.m_sensor_.get("localPhi").h_[layer-1][sector-1].fill(local_phi);
                    svthistos.m_sensor_.get("localTheta").h_[layer-1][sector-1].fill(local_theta);
                    svthistos.m_sensor_.get("localTrackAngle").h_[layer-1][sector-1].fill(local_angle_3d);
                    svthistos.m_module_.get("clusterCharge").h_[region-1][sector-1].fill(cluster_charge);
                    svthistos.m_region_.get("clusterCharge").h_[region-1].fill(cluster_charge);
                    svthistos.m_region_.get("localPhi").h_[region-1].fill(local_phi);
                    svthistos.m_region_.get("localTheta").h_[region-1].fill(local_theta);
                    svthistos.m_region_.get("localTrackAngle").h_[region-1].fill(local_angle_3d);
                    for(int l=0;l<TrackHits(i,j,k).size();++l) {
                        int strip=TrackHits(i,j,k).get(l).strip_;
                        int adc=TrackHits(i,j,k).get(l).adc_;
                        int bco=TrackHits(i,j,k).get(l).bco_;
                        int channel=(layer%2==0 ? strip+255 : strip-1);
                        svthistos.m_.get("hitStrip").h_.fill(strip);
                        svthistos.m_.get("hitAdc").h_.fill(adc);
                        svthistos.m_.get("hitBco").h_.fill(bco);
                        svthistos.m_.get("occupancy_module").h2_.fill(channel,Module(layer,sector));
                        svthistos.m_.get("occupancy_sensor").h2_.fill(strip,Sensor(layer,sector));
                        svthistos.m_sensor_.get("strip").h_[layer-1][sector-1].fill(strip);
                        svthistos.m_sensor_.get("adc").h_[layer-1][sector-1].fill(adc);
                        double fit_residual=TrackHits(i,j,k).get(l).fit_residual_;
                        if(!Double.isNaN(fit_residual)) {
                            svthistos.m_sensor_.get("fitResidual").h_[layer-1][sector-1].fill(fit_residual);
                            svthistos.m_sensor_.get("fitResidual_angle").h2_[layer-1][sector-1].fill(local_angle_3d,fit_residual);
                            svthistos.m_sensor_.get("fitResidual_phi").h2_[layer-1][sector-1].fill(local_phi,fit_residual);
                            svthistos.m_sensor_.get("fitResidual_theta").h2_[layer-1][sector-1].fill(local_theta,fit_residual);
                            svthistos.m_sensor_.get("fitResidual_x").h2_[layer-1][sector-1].fill(local_x,fit_residual);
                            svthistos.m_sensor_.get("fitResidual_y").h2_[layer-1][sector-1].fill(local_y,fit_residual);
                            svthistos.m_sensor_.get("fitResidual_z").h2_[layer-1][sector-1].fill(local_z,fit_residual);
                        }
                        if(!Double.isNaN(local_phi)) {
                            int angle_min=svthistos.m_lorentz_.get("sizeAngle").angle_min_;
                            int min_angle=svthistos.m_lorentz_.get("sizeAngle").min_angle_;
                            int angle_max=svthistos.m_lorentz_.get("sizeAngle").angle_max_;
                            int angle_space=svthistos.m_lorentz_.get("sizeAngle").angle_space_;
                            int array_size=svthistos.m_lorentz_.get("sizeAngle").array_size_;
                            angle_min=min_angle;
                            angle_max = angle_min + angle_space;
                            for(int jl = 0; jl < array_size; jl++){
                                if((local_phi-90)<angle_max&&(local_phi-90)>angle_min) svthistos.m_lorentz_.get("sizeAngle").h_[jl].fill(cluster_size);
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
        svt_tracks_.add(svtCosmicTrack);
        n_tracks_=svt_tracks_.size();
    }

    public void AddTrackCross() {
        SVTCross svtCross = new SVTCross();
        svt_track_crosses_.add(svtCross);
        n_track_crosses_=svt_track_crosses_.size();
    }

    public void AddTrackCluster() {
        SVTCluster svtCluster = new SVTCluster();
        svt_track_clusters_.add(svtCluster);
        n_track_clusters_=svt_track_clusters_.size();
    }

    public void AddTrackHit() {
        SVTHit svtHit = new SVTHit();
        svt_track_hits_.add(svtHit);
        n_track_hits_=svt_track_hits_.size();
    }

    public void AddDgtz() {
        SVTDgtz svtDgtz = new SVTDgtz();
        svt_dgtzs_.add(svtDgtz);
        n_dgtzs_=svt_dgtzs_.size();
    }

    SVTTrajectory Trajectory(int track_id) {
        return this.svt_tracks_.get(track_id).svt_trajectory_;
    }

    public void AddOffTrackCross() {
        SVTCross svtCross = new SVTCross();
        svt_offtrack_crosses_.add(svtCross);
        n_offtrack_crosses_=svt_offtrack_crosses_.size();
    }

    public void AddOffTrackCluster() {
        SVTCluster svtCluster = new SVTCluster();
        svt_offtrack_clusters_.add(svtCluster);
        n_offtrack_clusters_=svt_offtrack_clusters_.size();
    }

    static public void AddOffTrackHit() {
        SVTHit svtHit = new SVTHit();
        svt_offtrack_hits_.add(svtHit);
        n_offtrack_hits_=svt_offtrack_hits_.size();
    }

    ArrayList <SVTCosmicTrack> Tracks() {
        return this.svt_tracks_;
    }

    static ArrayList <SVTCross> TrackCrosses(int track_id) {
        return svt_tracks_.get(track_id).svt_track_crosses_;
    }

    static ArrayList <SVTCluster> TrackClusters(int track_id, int cross_id) {
        return svt_tracks_.get(track_id).svt_track_crosses_.get(cross_id).svt_track_clusters_;
    }

    static ArrayList <SVTHit> TrackHits(int track_id, int cross_id, int cluster_id) {
        return svt_tracks_.get(track_id).svt_track_crosses_.get(cross_id).svt_track_clusters_.get(cluster_id).svt_track_hits_;
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
            n_tracks_=bank_SVTCosmics.rows();
            for(int row = 0; row < bank_SVTCosmics.rows(); ++row){
                AddTrack();
                svt_tracks_.get(row).id_=bank_SVTCosmics.getInt("ID",row);
                svt_tracks_.get(row).trkline_yx_slope_=bank_SVTCosmics.getDouble("trkline_yx_slope",row);
                svt_tracks_.get(row).trkline_yx_interc_=bank_SVTCosmics.getDouble("trkline_yx_interc",row);
                svt_tracks_.get(row).trkline_yz_slope_=bank_SVTCosmics.getDouble("trkline_yz_slope",row);
                svt_tracks_.get(row).trkline_yz_interc_=bank_SVTCosmics.getDouble("trkline_yz_interc",row);
                svt_tracks_.get(row).phi_=bank_SVTCosmics.getDouble("phi",row);
                svt_tracks_.get(row).theta_=bank_SVTCosmics.getDouble("theta",row);
                svt_tracks_.get(row).kf_chi2_=bank_SVTCosmics.getDouble("KF_chi2",row);
                // svt_tracks_.get(row).kf_ndf_=(int)bank_SVTCosmics.getDouble("KF_ndf",row);
                svt_tracks_.get(row).kf_ndf_=bank_SVTCosmics.getInt("KF_ndf",row);
                svt_tracks_.get(row).ReadTrackTrajectory(event,svt_tracks_.get(row).id_,print);
                ArrayList<Integer> track_crosses =new ArrayList<Integer>();
                for(int i=0;i<Constants.NLAYERS;++i) {
                    String varname = String.format("Cross%d_ID", i+1);
                    int cross_id=bank_SVTCosmics.getInt(varname,row);
                    svt_tracks_.get(row).cross_id_[i]=cross_id;
                    if(cross_id!=-1) track_crosses.add(cross_id);
                }
                Collections.sort(track_crosses);
                for(int i=0;i<track_crosses.size();++i) svt_tracks_.get(row).ReadTrackCrosses(event,track_crosses.get(i),print);
                if(print) svt_tracks_.get(row).Show();
                n_track_crosses_+=track_crosses.size();
                n_track_hits_+=svt_tracks_.get(row).n_track_hits_;
            }
            n_track_clusters_=n_track_crosses_*2;
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
        for(int i=0;i<n_tracks_;++i) {
            for(int j=0;j<TrackCrosses(i).size();++j)  if(cross_id==TrackCrosses(i).get(j).id_) return true;
        }
        return false;
    }

    boolean IsTrackCluster(int cluster_id) {
        for(int i=0;i<n_tracks_;++i) {
            for(int j=0;j<TrackCrosses(i).size();++j) {
                for(int k=0;k<TrackClusters(i,j).size();++k) if(cluster_id==TrackClusters(i,j).get(k).id_) return true;
            }
        }
        return false;
    }

    boolean IsTrackHit(int hit_id) {
        for(int i=0;i<n_tracks_;++i) {
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
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).id_=cross_id;
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).sector_=bank_SVTCross.getInt("sector",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).region_=bank_SVTCross.getInt("region",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).x_=bank_SVTCross.getDouble("x",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).err_x_=bank_SVTCross.getDouble("err_x",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).ux_=bank_SVTCross.getDouble("ux",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).y_=bank_SVTCross.getDouble("y",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).err_y_=bank_SVTCross.getDouble("err_y",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).uy_=bank_SVTCross.getDouble("uy",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).z_=bank_SVTCross.getDouble("z",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).err_z_=bank_SVTCross.getDouble("err_z",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).uz_=bank_SVTCross.getDouble("uz",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).cluster_id_[0]=bank_SVTCross.getInt("Cluster1_ID",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).cluster_id_[1]=bank_SVTCross.getInt("Cluster2_ID",row);
                svt_offtrack_crosses_.get(n_offtrack_crosses_-1).n_offtrack_clusters_=2;
                for(int clrow = 0; clrow < nclrows; ++clrow) {
                    if(bank_SVTCluster.getInt("ID",clrow)==svt_offtrack_crosses_.get(n_offtrack_crosses_-1).cluster_id_[0] ||
                            bank_SVTCluster.getInt("ID",clrow)==svt_offtrack_crosses_.get(n_offtrack_crosses_-1).cluster_id_[1])
                        svt_offtrack_crosses_.get(n_offtrack_crosses_-1).n_offtrack_hits_ += bank_SVTCluster.getInt("size",clrow);
                }
                if(print) {
                    System.out.println(Constants.RED+"Off track"+Constants.RESET);
                    svt_offtrack_crosses_.get(n_offtrack_crosses_-1).Show();
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
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).id_=cluster_id;
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).sector_=bank_SVTCluster.getInt("sector",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).layer_=bank_SVTCluster.getInt("layer",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).size_=bank_SVTCluster.getInt("size",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).e_tot_=bank_SVTCluster.getDouble("ETot",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).seed_e_=bank_SVTCluster.getDouble("seedE",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).seed_strip_=bank_SVTCluster.getInt("seedStrip",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).centroid_=bank_SVTCluster.getDouble("centroid",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).hit1_id_=bank_SVTCluster.getInt("Hit1_ID",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).hit2_id_=bank_SVTCluster.getInt("Hit2_ID",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).hit3_id_=bank_SVTCluster.getInt("Hit3_ID",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).hit4_id_=bank_SVTCluster.getInt("Hit4_ID",row);
                svt_offtrack_clusters_.get(n_offtrack_clusters_-1).hit5_id_=bank_SVTCluster.getInt("Hit5_ID",row);

                for(int crrow = 0; crrow < ncrrows; ++crrow) {
                    if(cluster_id==bank_SVTCross.getInt("Cluster1_ID",crrow) ||
                            cluster_id==bank_SVTCross.getInt("Cluster2_ID",crrow))
                        svt_offtrack_clusters_.get(n_offtrack_clusters_-1).cross_id_=bank_SVTCross.getInt("ID",crrow);
                }
                if(print) {
                    System.out.println(Constants.RED+"Off track"+Constants.RESET);
                    svt_offtrack_clusters_.get(n_offtrack_clusters_-1).Show();
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
                svt_offtrack_hits_.get(n_offtrack_hits_-1).id_=hit_id;
                svt_offtrack_hits_.get(n_offtrack_hits_-1).sector_=bank_SVTHit.getInt("sector",row);
                svt_offtrack_hits_.get(n_offtrack_hits_-1).layer_=bank_SVTHit.getInt("layer",row);
                svt_offtrack_hits_.get(n_offtrack_hits_-1).strip_=bank_SVTHit.getInt("strip",row);
                svt_offtrack_hits_.get(n_offtrack_hits_-1).fit_residual_=bank_SVTHit.getDouble("fitResidual",row);
                svt_offtrack_hits_.get(n_offtrack_hits_-1).trking_stat_=bank_SVTHit.getInt("trkingStat",row);
                svt_offtrack_hits_.get(n_offtrack_hits_-1).cluster_id_=bank_SVTHit.getInt("clusterID",row);
                if(dgtzBank) {
                    svt_offtrack_hits_.get(n_offtrack_hits_-1).adc_=bank_SVTDgtz.getInt("ADC",row);
                    svt_offtrack_hits_.get(n_offtrack_hits_-1).bco_=bank_SVTDgtz.getInt("bco",row);
                    svt_offtrack_hits_.get(n_offtrack_hits_-1).hit_n_=bank_SVTDgtz.getInt("hitn",row);
                }
                for(int clrow = 0; clrow < svt_offtrack_clusters_.size(); ++clrow) {
                    if(svt_offtrack_hits_.get(n_offtrack_hits_-1).cluster_id_==svt_offtrack_clusters_.get(clrow).id_)
                        svt_offtrack_hits_.get(n_offtrack_hits_-1).cross_id_=svt_offtrack_clusters_.get(clrow).cross_id_;
                }
                if(print) {
                    System.out.println(Constants.RED+"Off track"+Constants.RESET);
                    svt_offtrack_hits_.get(n_offtrack_hits_-1).Show();
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
      svt_hits_.get(n_hits_-1).id_=hit_id;
      svt_hits_.get(n_hits_-1).sector_=bank_SVTHit.getInt("sector",row);
      svt_hits_.get(n_hits_-1).layer_=bank_SVTHit.getInt("layer",row);
      svt_hits_.get(n_hits_-1).strip_=bank_SVTHit.getInt("strip",row);
      svt_hits_.get(n_hits_-1).fit_residual_=bank_SVTHit.getDouble("fitResidual",row);
      svt_hits_.get(n_hits_-1).trking_stat_=bank_SVTHit.getInt("trkingStat",row);
      svt_hits_.get(n_hits_-1).cluster_id_=bank_SVTHit.getInt("clusterID",row);
      if(dgtzBank) {
        svt_hits_.get(n_hits_-1).adc_=bank_SVTDgtz.getInt("ADC",row);
        svt_hits_.get(n_hits_-1).bco_=bank_SVTDgtz.getInt("bco",row);
        svt_hits_.get(n_hits_-1).hit_n_=bank_SVTDgtz.getInt("hitn",row);
      }
      for(int clrow = 0; clrow < svt_clusters_.size(); ++clrow) {
        if(svt_hits_.get(n_hits_-1).cluster_id_==svt_clusters_.get(clrow).id_)
          svt_hits_.get(n_hits_-1).cross_id_=svt_clusters_.get(clrow).cross_id_;
      }
      if(print) {
        System.out.println(RED+"Off track"+RESET);
        svt_hits_.get(n_hits_-1).Show();
      }
        }
      }
    }
    */
    public void Show() {
        svt_tracks_.get(0).Show();
        svt_tracks_.get(0).svt_trajectory_.Show();
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
        data = new Object[n_tracks_][n_vars];
        for(int i=0;i<n_tracks_;++i) {
            svt_tracks_.get(i).svt_trajectory_.Show();
            data[i][0]=svt_tracks_.get(i).id_;
            data[i][1]=nf.format(svt_tracks_.get(i).trkline_yx_slope_);
            data[i][2]=nf.format(svt_tracks_.get(i).trkline_yx_interc_);
            data[i][3]=nf.format(svt_tracks_.get(i).trkline_yz_slope_);
            data[i][4]=nf.format(svt_tracks_.get(i).trkline_yz_interc_);
            data[i][5]=nf0.format(svt_tracks_.get(i).theta_);
            data[i][6]=nf0.format(svt_tracks_.get(i).phi_);
            data[i][7]=nf0.format(svt_tracks_.get(i).kf_chi2_);
            data[i][8]=svt_tracks_.get(i).kf_ndf_;
            data[i][9]=svt_tracks_.get(i).cross_id_[0];
            data[i][10]=svt_tracks_.get(i).cross_id_[1];
            data[i][11]=svt_tracks_.get(i).cross_id_[2];
            data[i][12]=svt_tracks_.get(i).cross_id_[3];
            data[i][13]=svt_tracks_.get(i).cross_id_[4];
            data[i][14]=svt_tracks_.get(i).cross_id_[5];
            data[i][15]=svt_tracks_.get(i).cross_id_[6];
            data[i][16]=svt_tracks_.get(i).cross_id_[7];
            data[i][17]=n_track_crosses_;
            data[i][18]=n_track_clusters_;
            data[i][19]=n_track_hits_;
        }
        TextTable tt = new TextTable(trackColumnNames, data);
        tt.printTable();
        String[] crossesColumnNames = {"CrID","sector","region","x","y","z","errx","erry","errz","ux","uy","uz","Cl1ID","Cl2ID","TkID","n_cl","n_h"};
        n_vars = crossesColumnNames.length;
        data = new Object[n_track_crosses_][n_vars];
        int m=0;
        for(int i=0;i<n_tracks_;++i) {
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
// 	  data[m][15]=RED+TrackCrosses(i).get(j).n_track_clusters_+RESET;
// 	  data[m][16]=RED+TrackCrosses(i).get(j).n_track_hits_+RESET;
// 	}
            }
        }
        TextTable ttcr = new TextTable(crossesColumnNames, data);
        ttcr.printTable();

        String[] clustersColumnNames = {"ClID","sector","layer","size","eTot","eSeed","seed","centroid","Hit1","Hit2","Hit3","Hit4","Hit5","CrID","TkID","TrjID"};
        n_vars = clustersColumnNames.length;
        data = new Object[n_track_clusters_][n_vars];
        m=0;
        for(int i=0;i<n_tracks_;++i) {
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
        data = new Object[n_track_hits_][n_vars];
        m=0;
        for(int i=0;i<n_tracks_;++i) {
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
        data = new Object[svt_offtrack_crosses_.size()][n_vars];
        for(int j=0;j<svt_offtrack_crosses_.size();++j) {
            data[j][0]=svt_offtrack_crosses_.get(j).id_;
            data[j][1]=svt_offtrack_crosses_.get(j).sector_;
            data[j][2]=svt_offtrack_crosses_.get(j).region_;
            data[j][3]=nf0.format(svt_offtrack_crosses_.get(j).x_);
            data[j][4]=nf0.format(svt_offtrack_crosses_.get(j).y_);
            data[j][5]=nf0.format(svt_offtrack_crosses_.get(j).z_);
            data[j][6]=nf.format(svt_offtrack_crosses_.get(j).err_x_);
            data[j][7]=nf.format(svt_offtrack_crosses_.get(j).err_y_);
            data[j][8]=nf.format(svt_offtrack_crosses_.get(j).err_z_);
            data[j][9]=nf.format(svt_offtrack_crosses_.get(j).ux_);
            data[j][10]=nf.format(svt_offtrack_crosses_.get(j).uy_);
            data[j][11]=nf.format(svt_offtrack_crosses_.get(j).uz_);
            data[j][12]=svt_offtrack_crosses_.get(j).cluster_id_[0];
            data[j][13]=svt_offtrack_crosses_.get(j).cluster_id_[1];
            data[j][14]=svt_offtrack_crosses_.get(j).track_id_;
            data[j][15]=svt_offtrack_crosses_.get(j).n_offtrack_clusters_;
            data[j][16]=svt_offtrack_crosses_.get(j).n_offtrack_hits_;
        }
        TextTable ttcr = new TextTable(offtrackCrossesColumnNames, data);
        ttcr.printTable();

        String[] offtrackClustersColumnNames = {"ClID","sector","layer","size","eTot","eSeed","seed","centroid","Hit1","Hit2","Hit3","Hit4","Hit5","CrID","TkID"};
        n_vars = offtrackClustersColumnNames.length;
        data = new Object[svt_offtrack_clusters_.size()][n_vars];
        for(int k=0;k<svt_offtrack_clusters_.size();++k) {
            data[k][0]=svt_offtrack_clusters_.get(k).id_;
            data[k][1]=svt_offtrack_clusters_.get(k).sector_;
            data[k][2]=svt_offtrack_clusters_.get(k).layer_;
            data[k][3]=svt_offtrack_clusters_.get(k).size_;
            data[k][4]=nf0.format(svt_offtrack_clusters_.get(k).e_tot_);
            data[k][5]=nf0.format(svt_offtrack_clusters_.get(k).seed_e_);
            data[k][6]=svt_offtrack_clusters_.get(k).seed_strip_;
            data[k][7]=nf.format(svt_offtrack_clusters_.get(k).centroid_);
            data[k][8]=svt_offtrack_clusters_.get(k).hit1_id_;
            data[k][9]=svt_offtrack_clusters_.get(k).hit2_id_;
            data[k][10]=svt_offtrack_clusters_.get(k).hit3_id_;
            data[k][11]=svt_offtrack_clusters_.get(k).hit4_id_;
            data[k][12]=svt_offtrack_clusters_.get(k).hit5_id_;
            data[k][13]=svt_offtrack_clusters_.get(k).cross_id_;
            data[k][14]=svt_offtrack_clusters_.get(k).track_id_;
        }
        TextTable ttcl = new TextTable(offtrackClustersColumnNames, data);
        ttcl.printTable();

        String[] offtrackHitsColumnNames = {"HitID","sector","layer","strip","residual","tkStat","adc","bco","hitN","ClID","CrID","TkID"};
        n_vars = offtrackHitsColumnNames.length;
        data = new Object[svt_offtrack_hits_.size()][n_vars];
        for(int l=0;l<svt_offtrack_hits_.size();++l) {
            data[l][0]=svt_offtrack_hits_.get(l).id_;
            data[l][1]=svt_offtrack_hits_.get(l).sector_;
            data[l][2]=svt_offtrack_hits_.get(l).layer_;
            data[l][3]=svt_offtrack_hits_.get(l).strip_;
            data[l][4]=nf.format(svt_offtrack_hits_.get(l).fit_residual_);
            data[l][5]=svt_offtrack_hits_.get(l).trking_stat_;
            data[l][6]=svt_offtrack_hits_.get(l).adc_;
            data[l][7]=svt_offtrack_hits_.get(l).bco_;
            data[l][8]=svt_offtrack_hits_.get(l).hit_n_;
            data[l][9]=svt_offtrack_hits_.get(l).cluster_id_;
            data[l][10]=svt_offtrack_hits_.get(l).cross_id_;
            data[l][11]=svt_offtrack_hits_.get(l).track_id_;
        }
        TextTable tth = new TextTable(offtrackHitsColumnNames, data);
        tth.printTable();

    }
}
