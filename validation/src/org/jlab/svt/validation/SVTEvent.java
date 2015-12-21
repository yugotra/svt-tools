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
    static ArrayList<SVTTrack> svtTracks;
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
        svtTracks = new ArrayList<>();
        svtTracks.clear();
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

    public void SetEventNumber(long eventNr) {
        SVTEvent.eventNr =eventNr;
    }

    public void FillEventHistos(EvioDataEvent event, SVTHistos svthistos) {

        if(event.hasBank("BSTRec::Trajectory")){
            EvioDataBank bankSVTTrajectory = (EvioDataBank) event.getBank("BSTRec::Trajectory");
            int nRows=bankSVTTrajectory.rows();
            for(int row = 0; row < nRows; row++){
                int sector=bankSVTTrajectory.getInt("SectorTrackIntersPlane",row);
                int layer=bankSVTTrajectory.getInt("LayerTrackIntersPlane",row);
                double phi=bankSVTTrajectory.getDouble("PhiTrackIntersPlane",row);
                double theta=bankSVTTrajectory.getDouble("ThetaTrackIntersPlane",row);
                double angle3D=bankSVTTrajectory.getDouble("trkToMPlnAngl",row);
            }
        }
        if(event.hasBank("BSTRec::Crosses")){
            EvioDataBank bankSVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
            int nRows=bankSVTCross.rows();
            svthistos.histoMap.get("event_crossMultiplicity").h.fill(nRows);
            for(int row = 0; row < nRows; ++row){
                double crossX=bankSVTCross.getDouble("x",row);
                double errX=bankSVTCross.getDouble("errX",row);
                double crossY=bankSVTCross.getDouble("y",row);
                double errY=bankSVTCross.getDouble("errY",row);
                double crossZ=bankSVTCross.getDouble("z",row);
                double errZ=bankSVTCross.getDouble("errZ",row);
                svthistos.histoMap.get("event_crossX").h.fill(crossX);
                svthistos.histoMap.get("event_crossY").h.fill(crossY);
                svthistos.histoMap.get("event_crossZ").h.fill(crossZ);
                svthistos.histoMap.get("event_crossErrX").h.fill(errX);
                svthistos.histoMap.get("event_crossErrY").h.fill(errY);
                svthistos.histoMap.get("event_crossErrZ").h.fill(errZ);
            }
        }
        if(event.hasBank("BSTRec::Clusters")){
            EvioDataBank bankSVTCluster = (EvioDataBank) event.getBank("BSTRec::Clusters");
            int nRows=bankSVTCluster.rows();
            svthistos.histoMap.get("event_clusterMultiplicity").h.fill(nRows);
            for(int row = 0; row < nRows; ++row){
                int clusterSize=bankSVTCluster.getInt("size",row);
                double clusterCharge=bankSVTCluster.getDouble("ETot",row);
                double seedCharge=bankSVTCluster.getDouble("seedE",row);
                int seed=bankSVTCluster.getInt("seedStrip",row);
                double centroid=bankSVTCluster.getDouble("centroid",row);
                svthistos.histoMap.get("event_centroid").h.fill(centroid);
                svthistos.histoMap.get("event_clusterCharge").h.fill(clusterCharge);
                svthistos.histoMap.get("event_seedCharge").h.fill(seedCharge);
                svthistos.histoMap.get("event_seedStrip").h.fill(seed);
                svthistos.histoMap.get("event_stripMultiplicity").h.fill(clusterSize);
            }
        }
        if(event.hasBank("BSTRec::Hits")){
            EvioDataBank bankSVTHit = (EvioDataBank) event.getBank("BSTRec::Hits");
            int nRows=bankSVTHit.rows();
            svthistos.histoMap.get("event_hitMultiplicity").h.fill(nRows);
            for(int row = 0; row < nRows; ++row){
                int sector=bankSVTHit.getInt("sector",row);
                int layer=bankSVTHit.getInt("layer",row);
                int strip=bankSVTHit.getInt("strip",row);
                svthistos.histoMap.get("event_hitStrip").h.fill(strip);
                svthistos.histoMap.get("event_occupancy_sensor").h2.fill(strip,Sensor(layer,sector));
            }
        }
        if(event.hasBank("BST::dgtz")) {
            EvioDataBank bankSVTDgtz = (EvioDataBank) event.getBank("BST::dgtz");
            int nRows=bankSVTDgtz.rows();
            for(int row = 0; row < nRows; ++row){
                int strip=bankSVTDgtz.getInt("strip",row);
                int adc=bankSVTDgtz.getInt("ADC",row);
                int bco=bankSVTDgtz.getInt("bco",row);
                svthistos.histoMap.get("event_dgtzStrip").h.fill(strip);
                svthistos.histoMap.get("event_hitAdc").h.fill(adc);
                svthistos.histoMap.get("event_hitBco").h.fill(bco);
            }
        }
    }

    public void FillOffTrackHistos(SVTHistos svthistos) {

        svthistos.histoMap.get("off_crossMultiplicity").h.fill(nOfftrackCrosses);
        svthistos.histoMap.get("off_clusterMultiplicity").h.fill(nOfftrackClusters);
        svthistos.histoMap.get("off_hitMultiplicity").h.fill(nOfftrackHits);
        for(int i = 0; i< svtOfftrackCrosses.size(); ++i) {
            double cross_x= svtOfftrackCrosses.get(i).x;
            double cross_y= svtOfftrackCrosses.get(i).y;
            double cross_z= svtOfftrackCrosses.get(i).z;
            double err_x= svtOfftrackCrosses.get(i).errX;
            double err_y= svtOfftrackCrosses.get(i).errY;
            double err_z= svtOfftrackCrosses.get(i).errZ;
            svthistos.histoMap.get("off_crossX").h.fill(cross_x);
            svthistos.histoMap.get("off_crossY").h.fill(cross_y);
            svthistos.histoMap.get("off_crossZ").h.fill(cross_z);
            svthistos.histoMap.get("off_crossErrX").h.fill(err_x);
            svthistos.histoMap.get("off_crossErrY").h.fill(err_y);
            svthistos.histoMap.get("off_crossErrZ").h.fill(err_z);
        }
        for(int i = 0; i< svtOfftrackClusters.size(); ++i) {
            int layer= svtOfftrackClusters.get(i).layer;
            int sector= svtOfftrackClusters.get(i).sector;
            int seed= svtOfftrackClusters.get(i).seedStrip;
            double centroid= svtOfftrackClusters.get(i).centroid;
            double seedCharge= svtOfftrackClusters.get(i).seedE;
            double clusterCharge= svtOfftrackClusters.get(i).eTot;
            double clusterSize= svtOfftrackClusters.get(i).size;
            svthistos.histoMap.get("off_clusterCharge").h.fill(clusterCharge);
            svthistos.histoMap.get("off_seedStrip").h.fill(seed);
            svthistos.histoMap.get("off_seedCharge").h.fill(seedCharge);
            svthistos.histoMap.get("off_centroid").h.fill(centroid);
            svthistos.histoSensorMap.get("off_seedCharge").h[layer-1][sector-1].fill(seedCharge);
            svthistos.histoSensorMap.get("off_clusterCharge").h[layer-1][sector-1].fill(clusterCharge);


            if(nTracks ==1) {
                // System.out.println(eventNr+" "+layer+" "+sector);
                int trjId=Trajectory(0).GetIdByModule(0,layer,sector);
                if(trjId!=-1) {
                    double local_angle_3d=Trajectory(0).angle3D.get(trjId);
                    // System.out.println(trjId+" "+local_angle_3d);
//                    if(Math.abs(local_angle_3d-90)>45)
                    svthistos.histoSensorMap.get("off_seedStrip").h[layer-1][sector-1].fill(seed);
//                    if(seed==190&&sector==10&&layer==1) System.out.println(eventNr);
                }
            }

            svthistos.histoSensorMap.get("off_stripMultiplicity").h[layer-1][sector-1].fill(clusterSize);
            svthistos.histoMap.get("off_stripMultiplicity").h.fill(clusterSize);
            int region=(layer%2==0 ? layer/2 : (layer+1)/2);
            svthistos.histoRegionMap.get("off_clusterCharge").h[region-1].fill(clusterCharge);
        }
        for(int i = 0; i< svtOfftrackHits.size(); ++i) {
            int layer= svtOfftrackHits.get(i).layer;
            int sector= svtOfftrackHits.get(i).sector;
            int strip= svtOfftrackHits.get(i).strip;
            int channel=(layer%2==0 ? strip+255 : strip-1);
            int adc= svtOfftrackHits.get(i).adc;
            int bco= svtOfftrackHits.get(i).bco;
            svthistos.histoMap.get("off_hitStrip").h.fill(strip);
            svthistos.histoMap.get("off_hitAdc").h.fill(adc);
            svthistos.histoMap.get("off_hitBco").h.fill(bco);
            svthistos.histoMap.get("off_occupancy_module").h2.fill(channel,Module(layer,sector));
            svthistos.histoMap.get("off_occupancy_sensor").h2.fill(strip,Sensor(layer,sector));
            svthistos.histoSensorMap.get("off_adc").h[layer-1][sector-1].fill(adc);
            svthistos.histoSensorMap.get("off_strip").h[layer-1][sector-1].fill(strip);
            // if(layer==1&&sector==2&&strip==15) println(eventNr);
        }
    }

    public void FillTrackHistos(SVTHistos svthistos) {

        svthistos.histoMap.get("trackMultiplicity").h.fill(nTracks);
        svthistos.histoMap.get("crossMultiplicity").h.fill(nTrackCrosses);
        svthistos.histoMap.get("clusterMultiplicity").h.fill(nTrackClusters);
        svthistos.histoMap.get("hitMultiplicity").h.fill(nTrackHits);
        for(int i = 0; i< nTracks; ++i) {
            if(TrackClusters(i,0).get(0).trjId ==-1) return; // catch for bad Trajectory bank
            double phi=Tracks().get(i).phi;
            double theta=Tracks().get(i).theta;
            double kfChi2=Tracks().get(i).kfChi2;
            int kfNdf=Tracks().get(i).kfNdf;
            svthistos.histoMap.get("normChi2").h.fill(kfChi2/(double)kfNdf);
            svthistos.histoMap.get("phi").h.fill(phi);
            svthistos.histoMap.get("theta").h.fill(theta);
            svthistos.histoMap.get("thetaPhi").h2.fill(phi,theta);
            for(int j=0;j<TrackCrosses(i).size();++j) {
                int region=TrackCrosses(i).get(j).region;
                int sector=TrackCrosses(i).get(j).sector;
                double crossX=TrackCrosses(i).get(j).x;
                double crossY=TrackCrosses(i).get(j).y;
                double crossZ=TrackCrosses(i).get(j).z;
                double errX=TrackCrosses(i).get(j).errX;
                double errY=TrackCrosses(i).get(j).errY;
                double errZ=TrackCrosses(i).get(j).errZ;
                svthistos.histoMap.get("crossX").h.fill(crossX);
                svthistos.histoMap.get("crossY").h.fill(crossY);
                svthistos.histoMap.get("crossZ").h.fill(crossZ);
                svthistos.histoMap.get("crossErrX").h.fill(errX);
                svthistos.histoMap.get("crossErrY").h.fill(errY);
                svthistos.histoMap.get("crossErrZ").h.fill(errZ);
                svthistos.histoRegionMap.get("crossX").h[region-1].fill(crossX);
                svthistos.histoRegionMap.get("crossY").h[region-1].fill(crossY);
                svthistos.histoRegionMap.get("crossZ").h[region-1].fill(crossZ);
                svthistos.histoRegionMap.get("crossErrX").h[region-1].fill(errX);
                svthistos.histoRegionMap.get("crossErrY").h[region-1].fill(errY);
                svthistos.histoRegionMap.get("crossErrZ").h[region-1].fill(errZ);
                svthistos.histoModuleMap.get("crossX").h_[region-1][sector-1].fill(crossX);
                svthistos.histoModuleMap.get("crossY").h_[region-1][sector-1].fill(crossY);
                svthistos.histoModuleMap.get("crossZ").h_[region-1][sector-1].fill(crossZ);
                svthistos.histoModuleMap.get("crossErrX").h_[region-1][sector-1].fill(errX);
                svthistos.histoModuleMap.get("crossErrY").h_[region-1][sector-1].fill(errY);
                svthistos.histoModuleMap.get("crossErrZ").h_[region-1][sector-1].fill(errZ);
                for(int k=0;k<TrackClusters(i,j).size();++k) {




                    int trjId;
                    if(TrackClusters(i,j).get(k).trjId !=-1) trjId=TrackClusters(i,j).get(k).trjId;
                    else trjId=0;
// !!!!!!!!!!!!! temporary to kludge the bug with track id in traj bank


//                    System.out.println(i+" "+j+" "+k+" "+Trajectory(i).phi.size()+" "+TrackClusters(i,j).get(k).trjId);



                    double localPhi=Trajectory(i).phi.get(trjId);
                    double localTheta=Trajectory(i).theta.get(trjId);
                    double localAngle3D=Trajectory(i).angle3D.get(trjId);
                    double localX=Trajectory(i).x.get(trjId);
                    double localY=Trajectory(i).y.get(trjId);
                    double localZ=Trajectory(i).z.get(trjId);
                    int layer=TrackClusters(i,j).get(k).layer;
                    double cluster_charge=TrackClusters(i,j).get(k).eTot;
                    int clusterSize=TrackClusters(i,j).get(k).size;
                    double centroid=TrackClusters(i,j).get(k).centroid;
                    double seed=TrackClusters(i,j).get(k).seedStrip;
                    double seedCharge=TrackClusters(i,j).get(k).seedE;
                    svthistos.histoMap.get("centroid").h.fill(centroid);
                    svthistos.histoMap.get("clusterCharge").h.fill(cluster_charge);
                    svthistos.histoMap.get("seedCharge").h.fill(seedCharge);
                    svthistos.histoMap.get("seedStrip").h.fill(seed);
                    svthistos.histoMap.get("stripMultiplicity").h.fill(clusterSize);
                    if(!Double.isNaN(localPhi)) svthistos.histoMap.get("localPhi").h.fill(localPhi);
                    if(!Double.isNaN(localTheta)) svthistos.histoMap.get("localTheta").h.fill(localTheta);
                    if(!Double.isNaN(localAngle3D)) {
                        svthistos.histoMap.get("localTrackAngle").h.fill(localAngle3D);
                        svthistos.histoMap.get("correctedClusterCharge").h.fill(cluster_charge*Math.cos(localAngle3D));
                    }
                    if(!Double.isNaN(localX)) svthistos.histoMap.get("localX").h.fill(localX);
                    if(!Double.isNaN(localY)) svthistos.histoMap.get("localY").h.fill(localY);
                    if(!Double.isNaN(localZ)) svthistos.histoMap.get("localZ").h.fill(localZ);
                    svthistos.histoSensorMap.get("clusterCharge").h[layer-1][sector-1].fill(cluster_charge);
                    svthistos.histoSensorMap.get("correctedClusterCharge").h[layer-1][sector-1].fill(cluster_charge*Math.cos(localAngle3D));
                    svthistos.histoSensorMap.get("seedCharge").h[layer-1][sector-1].fill(seedCharge);
                    svthistos.histoSensorMap.get("stripMultiplicity").h[layer-1][sector-1].fill(clusterSize);
                    svthistos.histoSensorMap.get("localPhi").h[layer-1][sector-1].fill(localPhi);
                    svthistos.histoSensorMap.get("localTheta").h[layer-1][sector-1].fill(localTheta);
                    svthistos.histoSensorMap.get("localTrackAngle").h[layer-1][sector-1].fill(localAngle3D);
                    svthistos.histoModuleMap.get("clusterCharge").h_[region-1][sector-1].fill(cluster_charge);
                    svthistos.histoRegionMap.get("clusterCharge").h[region-1].fill(cluster_charge);
                    svthistos.histoRegionMap.get("localPhi").h[region-1].fill(localPhi);
                    svthistos.histoRegionMap.get("localTheta").h[region-1].fill(localTheta);
                    svthistos.histoRegionMap.get("localTrackAngle").h[region-1].fill(localAngle3D);
                    for(int l=0;l<TrackHits(i,j,k).size();++l) {
                        int strip=TrackHits(i,j,k).get(l).strip;
                        int adc=TrackHits(i,j,k).get(l).adc;
                        int bco=TrackHits(i,j,k).get(l).bco;
                        int channel=(layer%2==0 ? strip+255 : strip-1);
                        svthistos.histoMap.get("hitStrip").h.fill(strip);
                        svthistos.histoMap.get("hitAdc").h.fill(adc);
                        svthistos.histoMap.get("hitBco").h.fill(bco);
                        svthistos.histoMap.get("occupancy_module").h2.fill(channel,Module(layer,sector));
                        svthistos.histoMap.get("occupancy_sensor").h2.fill(strip,Sensor(layer,sector));
                        svthistos.histoSensorMap.get("strip").h[layer-1][sector-1].fill(strip);
                        svthistos.histoSensorMap.get("adc").h[layer-1][sector-1].fill(adc);
                        double fitResidual=TrackHits(i,j,k).get(l).fitResidual;
                            svthistos.histoSensorMap.get("fitResidual").h[layer-1][sector-1].fill(fitResidual);
                            svthistos.histoSensorMap.get("fitResidual_angle").h2[layer-1][sector-1].fill(localAngle3D,fitResidual);
                            svthistos.histoSensorMap.get("fitResidual_phi").h2[layer-1][sector-1].fill(localPhi,fitResidual);
                            svthistos.histoSensorMap.get("fitResidual_theta").h2[layer-1][sector-1].fill(localTheta,fitResidual);
                            svthistos.histoSensorMap.get("fitResidual_x").h2[layer-1][sector-1].fill(localX,fitResidual);
                            svthistos.histoSensorMap.get("fitResidual_y").h2[layer-1][sector-1].fill(localY,fitResidual);
                            svthistos.histoSensorMap.get("fitResidual_z").h2[layer-1][sector-1].fill(localZ,fitResidual);
                        if(!Double.isNaN(localPhi)) {
                            int angleMin=svthistos.histoLorentzMap.get("sizeAngle").angle_min_;
                            int minAngle=svthistos.histoLorentzMap.get("sizeAngle").min_angle_;
                            int angleMax=svthistos.histoLorentzMap.get("sizeAngle").angle_max_;
                            int angleSpace=svthistos.histoLorentzMap.get("sizeAngle").angle_space_;
                            int arraySize=svthistos.histoLorentzMap.get("sizeAngle").array_size_;
                            angleMin=minAngle;
                            angleMax = angleMin + angleSpace;
                            for(int jl = 0; jl < arraySize; jl++){
                                if((localPhi-90)<angleMax&&(localPhi-90)>angleMin) svthistos.histoLorentzMap.get("sizeAngle").h_[jl].fill(clusterSize);
                                angleMin += angleSpace;
                                angleMax += angleSpace;
                            }//jl
                        }
                    }
                }
            }
        }//n_tracks
    }

    public void AddCosmicTrack() {
        SVTCosmicTrack svtCosmicTrack = new SVTCosmicTrack();
        svtCosmicTracks.add(svtCosmicTrack);
        nTracks = svtCosmicTracks.size();
    }

    public void AddTrack() {
        SVTTrack svtTrack = new SVTTrack();
        svtTracks.add(svtTrack);
        nTracks = svtTracks.size();
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

    SVTTrajectory Trajectory(int trackId) {
        return svtCosmicTracks.get(trackId).svtTrajectory;
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

    static ArrayList <SVTCross> TrackCrosses(int trackId) {
        return svtCosmicTracks.get(trackId).svtTrackCrosses;
    }

    static ArrayList <SVTCluster> TrackClusters(int trackId, int crossId) {
        return svtCosmicTracks.get(trackId).svtTrackCrosses.get(crossId).svtTrackClusters;
    }

    static ArrayList <SVTHit> TrackHits(int trackId, int crossId, int clusterId) {
        return svtCosmicTracks.get(trackId).svtTrackCrosses.get(crossId).svtTrackClusters.get(clusterId).svtTrackHits;
    }

    boolean SkipEvent(EvioDataEvent event, boolean print) {
        if(!event.hasBank("BSTRec::Cosmics")) {
            if(print) System.out.println(Constants.RED+"No tracks found, skip..."+Constants.RESET);
            return true;
        }
        else {
            EvioDataBank bank_SVTCosmics = (EvioDataBank) event.getBank("BSTRec::Cosmics");
            int nRows=bank_SVTCosmics.rows();
            if(nRows!=1) {
                if(print) System.out.println(Constants.RED+nRows+"multiple tracks, skip..."+Constants.RESET);
                return true; // 1 track per event
            }
            else{
                ArrayList<Integer> trackCrosses =new ArrayList<Integer>();
                for(int i=0;i<Constants.NLAYERS;++i) {
                    String varname = String.format("Cross%d_ID", i+1);
                    int crossId=bank_SVTCosmics.getInt(varname,0); // get cross id's for the 1st track
                    if(crossId!=-1) trackCrosses.add(crossId);
                }
                if(trackCrosses.size()!=8) {
                    if(print) System.out.println(Constants.RED+nRows+"less than 8 crosses, skip..."+Constants.RESET);
                    return true; // 8 crosses per track
                }
                else {
                    Collections.sort(trackCrosses);
                    for(int i=0;i<trackCrosses.size();++i) System.out.print(trackCrosses.get(i)+" "); System.out.println("");
                    for(int i=0;i<trackCrosses.size();++i) if(trackCrosses.get(i)!=i+1) {
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
        if (event.hasBank("BSTRec::Cosmics")) {
            EvioDataBank bankSVTCosmics = (EvioDataBank) event.getBank("BSTRec::Cosmics");
            nTracks = bankSVTCosmics.rows();
            for (int row = 0; row < bankSVTCosmics.rows(); ++row) {
                AddCosmicTrack();
                svtCosmicTracks.get(row).id = bankSVTCosmics.getInt("ID", row);
                svtCosmicTracks.get(row).trklineYxSlope = bankSVTCosmics.getDouble("trkline_yx_slope", row);
                svtCosmicTracks.get(row).trklineYxInterc = bankSVTCosmics.getDouble("trkline_yx_interc", row);
                svtCosmicTracks.get(row).trklineYzSlope = bankSVTCosmics.getDouble("trkline_yz_slope", row);
                svtCosmicTracks.get(row).trklineYzInterc = bankSVTCosmics.getDouble("trkline_yz_interc", row);
                svtCosmicTracks.get(row).phi = bankSVTCosmics.getDouble("phi", row);
                svtCosmicTracks.get(row).theta = bankSVTCosmics.getDouble("theta", row);
                svtCosmicTracks.get(row).kfChi2 = bankSVTCosmics.getDouble("KF_chi2", row);
                // svtCosmicTracks.get(row).kfNdf=(int)bankSVTCosmics.getDouble("KF_ndf",row);
                svtCosmicTracks.get(row).kfNdf = bankSVTCosmics.getInt("KF_ndf", row);
                svtCosmicTracks.get(row).ReadTrackTrajectory(event, svtCosmicTracks.get(row).id, print);
                ArrayList<Integer> trackCrosses = new ArrayList<Integer>();
                for (int i = 0; i < Constants.NLAYERS; ++i) {
                    String varName = String.format("Cross%d_ID", i + 1);
                    int crossId = bankSVTCosmics.getInt(varName, row);
                    svtCosmicTracks.get(row).crossId[i] = crossId;
                    if (crossId != -1) trackCrosses.add(crossId);
                }
                Collections.sort(trackCrosses);
                for (int i = 0; i < trackCrosses.size(); ++i)
                    svtCosmicTracks.get(row).ReadTrackCrosses(event, trackCrosses.get(i), print);
                if (print) svtCosmicTracks.get(row).Show();
                nTrackCrosses += trackCrosses.size();
                nTrackHits += svtCosmicTracks.get(row).nTrackHits;
            }
        }
        else if (event.hasBank("BSTRec::Tracks")) {
            EvioDataBank bankSVTTracks = (EvioDataBank) event.getBank("BSTRec::Tracks");
            nTracks = bankSVTTracks.rows();
            System.out.println(nTracks);
            for (int row = 0; row < bankSVTTracks.rows(); ++row) {
                AddTrack();
                svtTracks.get(row).id = bankSVTTracks.getInt("ID", row);
                svtTracks.get(row).fittingMethod = bankSVTTracks.getInt("fittingMethod", row);
                svtTracks.get(row).cX = bankSVTTracks.getDouble("c_x", row);
                svtTracks.get(row).cY = bankSVTTracks.getDouble("c_y", row);
                svtTracks.get(row).cZ = bankSVTTracks.getDouble("c_z", row);
                svtTracks.get(row).cUX = bankSVTTracks.getDouble("c_ux", row);
                svtTracks.get(row).cUY = bankSVTTracks.getDouble("c_uy", row);
                svtTracks.get(row).cUZ = bankSVTTracks.getDouble("c_uz", row);
                svtTracks.get(row).q = bankSVTTracks.getInt("q", row);
                svtTracks.get(row).p = bankSVTTracks.getDouble("p", row);
                svtTracks.get(row).pT = bankSVTTracks.getDouble("pt", row);
                svtTracks.get(row).phi0 = bankSVTTracks.getDouble("phi0", row);
                svtTracks.get(row).tanDip = bankSVTTracks.getDouble("tandip", row);
                svtTracks.get(row).z0 = bankSVTTracks.getDouble("z0", row);
                svtTracks.get(row).d0 = bankSVTTracks.getDouble("d0", row);
                svtTracks.get(row).covD02 = bankSVTTracks.getDouble("cov_d02", row);
                svtTracks.get(row).covD0Phi0 = bankSVTTracks.getDouble("cov_d0phi0", row);
                svtTracks.get(row).covD0Rho = bankSVTTracks.getDouble("cov_d0rho", row);
                svtTracks.get(row).covPhi02 = bankSVTTracks.getDouble("cov_phi02", row);
                svtTracks.get(row).covPhi0Rho = bankSVTTracks.getDouble("cov_phi0rho", row);
                svtTracks.get(row).covRho2 = bankSVTTracks.getDouble("cov_rho2", row);
                svtTracks.get(row).covZ02 = bankSVTTracks.getDouble("cov_z02", row);
                svtTracks.get(row).covTanDip2 = bankSVTTracks.getDouble("cov_tandip2", row);
                svtTracks.get(row).ReadTrackTrajectory(event, svtTracks.get(row).id, print);
                ArrayList<Integer> trackCrosses = new ArrayList<Integer>();
                for (int i = 0; i < Constants.NREGIONS; ++i) {
                    String varName = String.format("Cross%d_ID", i + 1);
                    int crossId = bankSVTTracks.getInt(varName, row);
                    svtTracks.get(row).crossId[i] = crossId;
                    if (crossId != -1) trackCrosses.add(crossId);
                }
                Collections.sort(trackCrosses);
                for (int i = 0; i < trackCrosses.size(); ++i)
                    svtTracks.get(row).ReadTrackCrosses(event, trackCrosses.get(i), print);
                if (print) svtTracks.get(row).Show();
                nTrackCrosses += trackCrosses.size();
                nTrackHits += svtTracks.get(row).nTrackHits;
            }
        }
        nTrackClusters = nTrackCrosses * 2;
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

    static boolean IsTrackCross(int crossId) {
        for(int i = 0; i< nTracks; ++i) {
            for(int j=0;j<TrackCrosses(i).size();++j)  if(crossId==TrackCrosses(i).get(j).id) return true;
        }
        return false;
    }

    boolean IsTrackCluster(int clusterId) {
        for(int i = 0; i< nTracks; ++i) {
            for(int j=0;j<TrackCrosses(i).size();++j) {
                for(int k=0;k<TrackClusters(i,j).size();++k) if(clusterId==TrackClusters(i,j).get(k).id) return true;
            }
        }
        return false;
    }

    boolean IsTrackHit(int hitId) {
        for(int i = 0; i< nTracks; ++i) {
            for(int j=0;j<TrackCrosses(i).size();++j) {
                for(int k=0;k<TrackClusters(i,j).size();++k) {
                    for(int l=0;l<TrackHits(i,j,k).size();++l) if(hitId==TrackHits(i,j,k).get(l).id) return true;
                }
            }
        }
        return false;
    }

    void ReadOffTrackCrosses(EvioDataEvent event, boolean print) {
        if(event.hasBank("BSTRec::Crosses")){
            EvioDataBank bankSVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
            int nClusterRows;
            EvioDataBank bank_SVTCluster = null;
            if(event.hasBank("BSTRec::Clusters")) {
                bank_SVTCluster = (EvioDataBank) event.getBank("BSTRec::Clusters");
                nClusterRows=bank_SVTCluster.rows();
            }
            else nClusterRows=0;
            int nrows=bankSVTCross.rows();
            int crossId=-1;
            for(int row = 0; row < nrows; ++row){
                crossId=bankSVTCross.getInt("ID",row);
                if(IsTrackCross(crossId)) continue;
                AddOffTrackCross();
                svtOfftrackCrosses.get(nOfftrackCrosses -1).id =crossId;
                svtOfftrackCrosses.get(nOfftrackCrosses -1).sector =bankSVTCross.getInt("sector",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).region =bankSVTCross.getInt("region",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).x =bankSVTCross.getDouble("x",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).errX =bankSVTCross.getDouble("err_x",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).ux =bankSVTCross.getDouble("ux",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).y =bankSVTCross.getDouble("y",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).errY =bankSVTCross.getDouble("err_y",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).uy =bankSVTCross.getDouble("uy",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).z =bankSVTCross.getDouble("z",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).errZ =bankSVTCross.getDouble("err_z",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).uz =bankSVTCross.getDouble("uz",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).clusterId[0]=bankSVTCross.getInt("Cluster1_ID",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).clusterId[1]=bankSVTCross.getInt("Cluster2_ID",row);
                svtOfftrackCrosses.get(nOfftrackCrosses -1).nOfftrackClusters =2;
                for(int clusterRow = 0; clusterRow < nClusterRows; ++clusterRow) {
                    if(bank_SVTCluster.getInt("ID",clusterRow)== svtOfftrackCrosses.get(nOfftrackCrosses -1).clusterId[0] ||
                            bank_SVTCluster.getInt("ID",clusterRow)== svtOfftrackCrosses.get(nOfftrackCrosses -1).clusterId[1])
                        svtOfftrackCrosses.get(nOfftrackCrosses -1).nOfftrackHits += bank_SVTCluster.getInt("size",clusterRow);
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
            EvioDataBank bankSVTCluster = (EvioDataBank) event.getBank("BSTRec::Clusters");
            int nCrossRows;
            EvioDataBank bankSVTCross = null;
            if(event.hasBank("BSTRec::Crosses")) {
                bankSVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
                nCrossRows=bankSVTCross.rows();
            }
            else nCrossRows=0;
            int nrows=bankSVTCluster.rows();
            int clusterId=-1;
            for(int row = 0; row < nrows; ++row){
                clusterId=bankSVTCluster.getInt("ID",row);
                if(IsTrackCluster(clusterId)) continue;
                AddOffTrackCluster();
                svtOfftrackClusters.get(nOfftrackClusters -1).id =clusterId;
                svtOfftrackClusters.get(nOfftrackClusters -1).sector =bankSVTCluster.getInt("sector",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).layer =bankSVTCluster.getInt("layer",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).size =bankSVTCluster.getInt("size",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).eTot =bankSVTCluster.getDouble("ETot",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).seedE =bankSVTCluster.getDouble("seedE",row);


                for(int row1 = 0; row1 < nrows; ++row1) {

                    int sector=bankSVTCluster.getInt("sector",row1);
                    int layer=bankSVTCluster.getInt("layer",row1);
                    if(sector!= svtOfftrackClusters.get(nOfftrackClusters -1).sector ||
                            layer!= svtOfftrackClusters.get(nOfftrackClusters -1).layer +1) continue;
                    svtOfftrackClusters.get(nOfftrackClusters - 1).seedStrip = bankSVTCluster.getInt("seedStrip", row);
if(svtOfftrackClusters.get(nOfftrackClusters -1).layer ==1&& svtOfftrackClusters.get(nOfftrackClusters -1).sector ==1&& svtOfftrackClusters.get(nOfftrackClusters - 1).seedStrip ==200) System.out.println(eventNr);
                }





                svtOfftrackClusters.get(nOfftrackClusters -1).centroid =bankSVTCluster.getDouble("centroid",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).hit1Id =bankSVTCluster.getInt("Hit1_ID",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).hit2Id =bankSVTCluster.getInt("Hit2_ID",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).hit3Id =bankSVTCluster.getInt("Hit3_ID",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).hit4Id =bankSVTCluster.getInt("Hit4_ID",row);
                svtOfftrackClusters.get(nOfftrackClusters -1).hit5Id =bankSVTCluster.getInt("Hit5_ID",row);

                for(int crossRow = 0; crossRow < nCrossRows; ++crossRow) {
                    if(clusterId==bankSVTCross.getInt("Cluster1_ID",crossRow) ||
                            clusterId==bankSVTCross.getInt("Cluster2_ID",crossRow))
                        svtOfftrackClusters.get(nOfftrackClusters -1).crossId =bankSVTCross.getInt("ID",crossRow);
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
            EvioDataBank bankSVTHit = (EvioDataBank) event.getBank("BSTRec::Hits");
            int nRows=bankSVTHit.rows();
            EvioDataBank bankSVTDgtz = null;
            boolean dgtzBank;
            if(event.hasBank("BST::dgtz")) {
                bankSVTDgtz = (EvioDataBank) event.getBank("BST::dgtz");
                dgtzBank=true;
            }
            else {
                dgtzBank=false;
                System.out.println("No dgtz bank in event");
                // return;
            }
            int hitId=-1;
            for(int row = 0; row < nRows; ++row){
                // hitId=row+1;
                // hitId=bankSVTHit.getInt("id",row);
                hitId=bankSVTHit.getInt("ID",row);
                if(IsTrackHit(hitId)) continue;
                AddOffTrackHit();
                svtOfftrackHits.get(nOfftrackHits -1).id =hitId;
                svtOfftrackHits.get(nOfftrackHits -1).sector =bankSVTHit.getInt("sector",row);
                svtOfftrackHits.get(nOfftrackHits -1).layer =bankSVTHit.getInt("layer",row);
                svtOfftrackHits.get(nOfftrackHits -1).strip =bankSVTHit.getInt("strip",row);
                svtOfftrackHits.get(nOfftrackHits -1).fitResidual =bankSVTHit.getDouble("fitResidual",row);
                svtOfftrackHits.get(nOfftrackHits -1).trkingStat =bankSVTHit.getInt("trkingStat",row);
                svtOfftrackHits.get(nOfftrackHits -1).clusterId =bankSVTHit.getInt("clusterID",row);
                if(dgtzBank) {
                    svtOfftrackHits.get(nOfftrackHits -1).adc =bankSVTDgtz.getInt("ADC",row);
                    svtOfftrackHits.get(nOfftrackHits -1).bco =bankSVTDgtz.getInt("bco",row);
                    svtOfftrackHits.get(nOfftrackHits -1).hitN =bankSVTDgtz.getInt("hitn",row);
                }
                for(int clusterRow = 0; clusterRow < svtOfftrackClusters.size(); ++clusterRow) {
                    if(svtOfftrackHits.get(nOfftrackHits -1).clusterId == svtOfftrackClusters.get(clusterRow).id)
                        svtOfftrackHits.get(nOfftrackHits -1).crossId = svtOfftrackClusters.get(clusterRow).crossId;
                }
                if(print) {
                    System.out.println(Constants.RED+"Off track"+Constants.RESET);
                    svtOfftrackHits.get(nOfftrackHits -1).Show();
                }
            }
        }
    }

    public void Show() {
        svtCosmicTracks.get(0).Show();
        svtCosmicTracks.get(0).svtTrajectory.Show();
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
            svtCosmicTracks.get(i).svtTrajectory.Show();
            data[i][0]= svtCosmicTracks.get(i).id;
            data[i][1]=nf.format(svtCosmicTracks.get(i).trklineYxSlope);
            data[i][2]=nf.format(svtCosmicTracks.get(i).trklineYxInterc);
            data[i][3]=nf.format(svtCosmicTracks.get(i).trklineYzSlope);
            data[i][4]=nf.format(svtCosmicTracks.get(i).trklineYzInterc);
            data[i][5]=nf0.format(svtCosmicTracks.get(i).theta);
            data[i][6]=nf0.format(svtCosmicTracks.get(i).phi);
            data[i][7]=nf0.format(svtCosmicTracks.get(i).kfChi2);
            data[i][8]= svtCosmicTracks.get(i).kfNdf;
            data[i][9]= svtCosmicTracks.get(i).crossId[0];
            data[i][10]= svtCosmicTracks.get(i).crossId[1];
            data[i][11]= svtCosmicTracks.get(i).crossId[2];
            data[i][12]= svtCosmicTracks.get(i).crossId[3];
            data[i][13]= svtCosmicTracks.get(i).crossId[4];
            data[i][14]= svtCosmicTracks.get(i).crossId[5];
            data[i][15]= svtCosmicTracks.get(i).crossId[6];
            data[i][16]= svtCosmicTracks.get(i).crossId[7];
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
// 	  data[m][0]=RED+TrackCrosses(i).get(j).id+RESET;
// 	}
                data[m][0]=TrackCrosses(i).get(j).id;
                data[m][1]=TrackCrosses(i).get(j).sector;
                data[m][2]=TrackCrosses(i).get(j).region;
                data[m][3]=nf0.format(TrackCrosses(i).get(j).x);
                data[m][4]=nf0.format(TrackCrosses(i).get(j).y);
                data[m][5]=nf0.format(TrackCrosses(i).get(j).z);
                data[m][6]=nf.format(TrackCrosses(i).get(j).errX);
                data[m][7]=nf.format(TrackCrosses(i).get(j).errY);
                data[m][8]=nf.format(TrackCrosses(i).get(j).errZ);
                data[m][9]=nf.format(TrackCrosses(i).get(j).ux);
                data[m][10]=nf.format(TrackCrosses(i).get(j).uy);
                data[m][11]=nf.format(TrackCrosses(i).get(j).uz);
                data[m][12]=TrackCrosses(i).get(j).clusterId[0];
                data[m][13]=TrackCrosses(i).get(j).clusterId[1];
                data[m][14]=TrackCrosses(i).get(j).trackId;
                data[m][15]=TrackCrosses(i).get(j).nTrackClusters;
                data[m][16]=TrackCrosses(i).get(j).nTrackHits;
                m++;
// 	}
// 	  data[m][1]=RED+TrackCrosses(i).get(j).sector+RESET;
// 	  data[m][2]=RED+TrackCrosses(i).get(j).region+RESET;
// 	  data[m][3]=RED+nf0.format(TrackCrosses(i).get(j).x)+RESET;
// 	  data[m][4]=RED+nf0.format(TrackCrosses(i).get(j).y)+RESET;
// 	  data[m][5]=RED+nf0.format(TrackCrosses(i).get(j).z)+RESET;
// 	  data[m][6]=RED+nf.format(TrackCrosses(i).get(j).errX)+RESET;
// 	  data[m][7]=RED+nf.format(TrackCrosses(i).get(j).errY)+RESET;
// 	  data[m][8]=RED+nf.format(TrackCrosses(i).get(j).errZ)+RESET;
// 	  data[m][9]=RED+nf.format(TrackCrosses(i).get(j).ux)+RESET;
// 	  data[m][10]=RED+nf.format(TrackCrosses(i).get(j).uy)+RESET;
// 	  data[m][11]=RED+nf.format(TrackCrosses(i).get(j).uz)+RESET;
// 	  data[m][12]=RED+TrackCrosses(i).get(j).clusterId[0]+RESET;
// 	  data[m][13]=RED+TrackCrosses(i).get(j).clusterId[1]+RESET;
// 	  data[m][14]=RED+TrackCrosses(i).get(j).trackId+RESET;
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
                    data[m][0]=TrackClusters(i,j).get(k).id;
                    data[m][1]=TrackClusters(i,j).get(k).sector;
                    data[m][2]=TrackClusters(i,j).get(k).layer;
                    data[m][3]=TrackClusters(i,j).get(k).size;
                    data[m][4]=nf0.format(TrackClusters(i,j).get(k).eTot);
                    data[m][5]=nf0.format(TrackClusters(i,j).get(k).seedE);
                    data[m][6]=TrackClusters(i,j).get(k).seedStrip;
                    data[m][7]=nf.format(TrackClusters(i,j).get(k).centroid);
                    data[m][8]=TrackClusters(i,j).get(k).hit1Id;
                    data[m][9]=TrackClusters(i,j).get(k).hit2Id;
                    data[m][10]=TrackClusters(i,j).get(k).hit3Id;
                    data[m][11]=TrackClusters(i,j).get(k).hit4Id;
                    data[m][12]=TrackClusters(i,j).get(k).hit5Id;
                    data[m][13]=TrackClusters(i,j).get(k).crossId;
                    data[m][14]=TrackClusters(i,j).get(k).trackId;
                    data[m][15]=TrackClusters(i,j).get(k).trjId;
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
                        data[m][0]=TrackHits(i,j,k).get(l).id;
                        data[m][1]=TrackHits(i,j,k).get(l).sector;
                        data[m][2]=TrackHits(i,j,k).get(l).layer;
                        data[m][3]=TrackHits(i,j,k).get(l).strip;
                        data[m][4]=nf.format(TrackHits(i,j,k).get(l).fitResidual);
                        data[m][5]=TrackHits(i,j,k).get(l).trkingStat;
                        data[m][6]=TrackHits(i,j,k).get(l).adc;
                        data[m][7]=TrackHits(i,j,k).get(l).bco;
                        data[m][8]=TrackHits(i,j,k).get(l).hitN;
                        data[m][9]=TrackHits(i,j,k).get(l).clusterId;
                        data[m][10]=TrackHits(i,j,k).get(l).crossId;
                        data[m][11]=TrackHits(i,j,k).get(l).trackId;
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
            data[j][0]= svtOfftrackCrosses.get(j).id;
            data[j][1]= svtOfftrackCrosses.get(j).sector;
            data[j][2]= svtOfftrackCrosses.get(j).region;
            data[j][3]=nf0.format(svtOfftrackCrosses.get(j).x);
            data[j][4]=nf0.format(svtOfftrackCrosses.get(j).y);
            data[j][5]=nf0.format(svtOfftrackCrosses.get(j).z);
            data[j][6]=nf.format(svtOfftrackCrosses.get(j).errX);
            data[j][7]=nf.format(svtOfftrackCrosses.get(j).errY);
            data[j][8]=nf.format(svtOfftrackCrosses.get(j).errZ);
            data[j][9]=nf.format(svtOfftrackCrosses.get(j).ux);
            data[j][10]=nf.format(svtOfftrackCrosses.get(j).uy);
            data[j][11]=nf.format(svtOfftrackCrosses.get(j).uz);
            data[j][12]= svtOfftrackCrosses.get(j).clusterId[0];
            data[j][13]= svtOfftrackCrosses.get(j).clusterId[1];
            data[j][14]= svtOfftrackCrosses.get(j).trackId;
            data[j][15]= svtOfftrackCrosses.get(j).nOfftrackClusters;
            data[j][16]= svtOfftrackCrosses.get(j).nOfftrackHits;
        }
        TextTable ttcr = new TextTable(offtrackCrossesColumnNames, data);
        ttcr.printTable();

        String[] offtrackClustersColumnNames = {"ClID","sector","layer","size","eTot","eSeed","seed","centroid","Hit1","Hit2","Hit3","Hit4","Hit5","CrID","TkID"};
        n_vars = offtrackClustersColumnNames.length;
        data = new Object[svtOfftrackClusters.size()][n_vars];
        for(int k = 0; k< svtOfftrackClusters.size(); ++k) {
            data[k][0]= svtOfftrackClusters.get(k).id;
            data[k][1]= svtOfftrackClusters.get(k).sector;
            data[k][2]= svtOfftrackClusters.get(k).layer;
            data[k][3]= svtOfftrackClusters.get(k).size;
            data[k][4]=nf0.format(svtOfftrackClusters.get(k).eTot);
            data[k][5]=nf0.format(svtOfftrackClusters.get(k).seedE);
            data[k][6]= svtOfftrackClusters.get(k).seedStrip;
            data[k][7]=nf.format(svtOfftrackClusters.get(k).centroid);
            data[k][8]= svtOfftrackClusters.get(k).hit1Id;
            data[k][9]= svtOfftrackClusters.get(k).hit2Id;
            data[k][10]= svtOfftrackClusters.get(k).hit3Id;
            data[k][11]= svtOfftrackClusters.get(k).hit4Id;
            data[k][12]= svtOfftrackClusters.get(k).hit5Id;
            data[k][13]= svtOfftrackClusters.get(k).crossId;
            data[k][14]= svtOfftrackClusters.get(k).trackId;
        }
        TextTable ttcl = new TextTable(offtrackClustersColumnNames, data);
        ttcl.printTable();

        String[] offtrackHitsColumnNames = {"HitID","sector","layer","strip","residual","tkStat","adc","bco","hitN","ClID","CrID","TkID"};
        n_vars = offtrackHitsColumnNames.length;
        data = new Object[svtOfftrackHits.size()][n_vars];
        for(int l = 0; l< svtOfftrackHits.size(); ++l) {
            data[l][0]= svtOfftrackHits.get(l).id;
            data[l][1]= svtOfftrackHits.get(l).sector;
            data[l][2]= svtOfftrackHits.get(l).layer;
            data[l][3]= svtOfftrackHits.get(l).strip;
            data[l][4]=nf.format(svtOfftrackHits.get(l).fitResidual);
            data[l][5]= svtOfftrackHits.get(l).trkingStat;
            data[l][6]= svtOfftrackHits.get(l).adc;
            data[l][7]= svtOfftrackHits.get(l).bco;
            data[l][8]= svtOfftrackHits.get(l).hitN;
            data[l][9]= svtOfftrackHits.get(l).clusterId;
            data[l][10]= svtOfftrackHits.get(l).crossId;
            data[l][11]= svtOfftrackHits.get(l).trackId;
        }
        TextTable tth = new TextTable(offtrackHitsColumnNames, data);
        tth.printTable();

    }
}
