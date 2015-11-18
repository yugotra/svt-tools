package org.jlab.svt.validation;

import org.root.group.TDirectory;
import java.util.HashMap;
import java.util.Map;

public class SVTHistos {
    public TDirectory rootdir_;
    public TDirectory dir_track_;
    public TDirectory dir_trajectory_;
    public TDirectory dir_cross_;
    public TDirectory dir_cluster_;
    public TDirectory dir_hit_;
    public TDirectory dir_sensor_;
    public TDirectory dir_module_;
    public TDirectory dir_region_;
    public TDirectory dir_offtrack_;
    public TDirectory dir_offtrack_cross_;
    public TDirectory dir_offtrack_cluster_;
    public TDirectory dir_offtrack_hit_;
    public TDirectory dir_offtrack_sensor_;
    public TDirectory dir_offtrack_module_;
    public TDirectory dir_offtrack_region_;
    public TDirectory dir_event_;
    public TDirectory dir_event_cross_;
    public TDirectory dir_event_cluster_;
    public TDirectory dir_event_hit_;
    // public TDirectory dir_event_sensor_;
    public TDirectory dir_lorentz_;
    public Map<String,Histo> m_ = new HashMap<String,Histo>();
    public Map<String,HistoSensor> m_sensor_ = new HashMap<String,HistoSensor>();
    public Map<String,HistoModule> m_module_ = new HashMap<String,HistoModule>();
    public Map<String,HistoRegion> m_region_ = new HashMap<String,HistoRegion>();
    public Map<String,HistoLorentz> m_lorentz_ = new HashMap<String,HistoLorentz>();

    public SVTHistos(){
        rootdir_    = new TDirectory();
        dir_track_  = new TDirectory("Track");
        rootdir_.addDirectory(dir_track_);
        dir_trajectory_  = new TDirectory("Trajectory");
        rootdir_.addDirectory(dir_trajectory_);
        dir_cross_  = new TDirectory("Cross");
        rootdir_.addDirectory(dir_cross_);
        dir_cluster_  = new TDirectory("Cluster");
        rootdir_.addDirectory(dir_cluster_);
        dir_hit_  = new TDirectory("Hit");
        rootdir_.addDirectory(dir_hit_);
        dir_sensor_  = new TDirectory("Sensor");
        rootdir_.addDirectory(dir_sensor_);
        dir_module_  = new TDirectory("Module");
        rootdir_.addDirectory(dir_module_);
        dir_region_  = new TDirectory("Region");
        rootdir_.addDirectory(dir_region_);
        dir_offtrack_  = new TDirectory("Offtrack");
        rootdir_.addDirectory(dir_offtrack_);
        dir_offtrack_cross_  = new TDirectory("Offtrack/Cross");
        rootdir_.addDirectory(dir_offtrack_cross_);
        dir_offtrack_cluster_  = new TDirectory("Offtrack/Cluster");
        rootdir_.addDirectory(dir_offtrack_cluster_);
        dir_offtrack_hit_  = new TDirectory("Offtrack/Hit");
        rootdir_.addDirectory(dir_offtrack_hit_);
        dir_offtrack_sensor_  = new TDirectory("Offtrack/Sensor");
        rootdir_.addDirectory(dir_offtrack_sensor_);
        dir_offtrack_module_  = new TDirectory("Offtrack/Module");
        rootdir_.addDirectory(dir_offtrack_module_);
        dir_offtrack_region_  = new TDirectory("Offtrack/Region");
        rootdir_.addDirectory(dir_offtrack_region_);
        dir_event_  = new TDirectory("Event");
        rootdir_.addDirectory(dir_event_);
        dir_event_cross_  = new TDirectory("Event/Cross");
        rootdir_.addDirectory(dir_event_cross_);
        dir_event_cluster_  = new TDirectory("Event/Cluster");
        rootdir_.addDirectory(dir_event_cluster_);
        dir_event_hit_  = new TDirectory("Event/Hit");
        rootdir_.addDirectory(dir_event_hit_);
        // dir_event_sensor_  = new TDirectory("Event/Sensor");
        // rootdir_.addDirectory(dir_event_sensor_);
        dir_lorentz_  = new TDirectory("Lorentz");
        rootdir_.addDirectory(dir_lorentz_);
    }

    public void book() {
        bookHisto(dir_track_,"trackMultiplicity",10,-0.5,9.5);
        bookHisto(dir_track_,"normChi2",30,-0.5,29.5);
        bookHisto(dir_track_,"phi",180,-0.5,179.5);
        bookHisto(dir_track_,"theta",180,-0.5,179.5);
        bookHisto(dir_track_,"thetaPhi","phi","theta",180,-0.5,179.5,180,-0.5,179.5);
        bookHisto(dir_trajectory_,"localPhi",360,-180,180);
        bookHisto(dir_trajectory_,"localTheta",180,-0.5,179.5);
        bookHisto(dir_trajectory_,"localTrackAngle",180,-0.5,179.5);
        bookHisto(dir_trajectory_,"localX",100,-200,200);
        bookHisto(dir_trajectory_,"localY",100,-200,200);
        bookHisto(dir_trajectory_,"localZ",100,-250,250);
        bookHisto(dir_cross_,"crossMultiplicity",20,-0.5,19.5);
        bookHisto(dir_cross_,"crossX",100,-200,200);
        bookHisto(dir_cross_,"crossY",100,-200,200);
        bookHisto(dir_cross_,"crossZ",100,-250,250);
        bookHisto(dir_cross_,"crossErrX",100,-0.01,0.15);
        bookHisto(dir_cross_,"crossErrY",100,-0.01,0.15);
        bookHisto(dir_cross_,"crossErrZ",100,-0.5,19.5);
        bookHisto(dir_cluster_,"clusterMultiplicity",20,-0.5,19.5);
        bookHisto(dir_cluster_,"stripMultiplicity",30,-0.5,29.5);
        bookHisto(dir_cluster_,"seedStrip",256,0.5,256.5);
         bookHisto(dir_cluster_,"seedCharge",100,-0.5,299.5);
//        bookHisto(dir_cluster_,"seedCharge",50,0,0.50);
         bookHisto(dir_cluster_,"clusterCharge",300,-0.5,299.5);
//        bookHisto(dir_cluster_,"clusterCharge",50,0,0.50);
         bookHisto(dir_cluster_,"correctedClusterCharge",300,-0.5,299.5);
//        bookHisto(dir_cluster_,"correctedClusterCharge",50,0,0.50);
        bookHisto(dir_cluster_,"centroid",256,0.5,256.5);
        bookHisto(dir_hit_,"hitMultiplicity",30,-0.5,29.5);
        bookHisto(dir_hit_,"hitStrip",256,0.5,256.5);
        bookHisto(dir_hit_,"hitAdc",8,-0.5,7.5);
        bookHisto(dir_hit_,"hitBco",256,0.5,256.5);
        bookHistoSensor(dir_sensor_,"fitResidual",200,-3,3);
        bookHistoSensor(dir_sensor_,"strip",256,0.5,256.5);
        bookHistoSensor(dir_sensor_,"adc",8,-0.5,7.5);
         bookHistoSensor(dir_sensor_,"clusterCharge",300,-0.5,299.5);
//        bookHistoSensor(dir_sensor_,"clusterCharge",50,0,0.50);
         bookHistoSensor(dir_sensor_,"correctedClusterCharge",300,-0.5,299.5);
//        bookHistoSensor(dir_sensor_,"correctedClusterCharge",50,0,0.50);
        bookHistoSensor(dir_sensor_,"stripMultiplicity",30,-0.5,29.5);
         bookHistoSensor(dir_sensor_,"seedCharge",100,-0.5,299.5);
//        bookHistoSensor(dir_sensor_,"seedCharge",50,0,0.50);
        bookHistoSensor(dir_sensor_,"fitResidual_angle","track angle","fitResidual",180,-0.5,179.5,200,-3,3);
        bookHistoSensor(dir_sensor_,"fitResidual_phi","phi","fitResidual",180,-0.5,179.5,200,-3,3);
        bookHistoSensor(dir_sensor_,"fitResidual_theta","theta","fitResidual",180,-0.5,179.5,200,-3,3);
        bookHistoSensor(dir_sensor_,"fitResidual_x","X","fitResidual",200,-200,200,200,-3,3);
        bookHistoSensor(dir_sensor_,"fitResidual_y","Y","fitResidual",200,-200,200,200,-3,3);
        bookHistoSensor(dir_sensor_,"fitResidual_z","Z","fitResidual",200,-150,250,200,-3,3);
        bookHistoSensor(dir_sensor_,"localPhi",360,-180,180);
        bookHistoSensor(dir_sensor_,"localTheta",180,-0.5,179.5);
        bookHistoSensor(dir_sensor_,"localTrackAngle",180,-0.5,179.5);
        bookHisto(dir_sensor_,"occupancy_sensor","Channel","Sensor",256,0,256,132,0,132);
        bookHisto(dir_sensor_,"inefficiency_sensor",132,0.5,132.5);
        // bookHisto(dir_sensor_,"fitResidual_sensor","sensor","fitResidual",132,0.5,132.5,100,-1.5,1.5);
        // bookHisto(dir_sensor_,"fitResidual_sensor1",132,0.5,132.5);
         bookHistoModule(dir_module_,"clusterCharge",300,-0.5,299.5);
//        bookHistoModule(dir_module_,"clusterCharge",50,0,0.50);
        bookHistoModule(dir_module_,"crossX",100,-200,200);
        bookHistoModule(dir_module_,"crossY",100,-200,200);
        bookHistoModule(dir_module_,"crossZ",100,-250,250);
        bookHistoModule(dir_module_,"crossErrX",100,-0.01,0.15);
        bookHistoModule(dir_module_,"crossErrY",100,-0.01,0.15);
        bookHistoModule(dir_module_,"crossErrZ",100,-0.5,19.5);
        bookHisto(dir_module_,"occupancy_module","Channel","Module",512,0,512,66,0,66);
         bookHistoRegion(dir_region_,"clusterCharge",300,-0.5,299.5);
//        bookHistoRegion(dir_region_,"clusterCharge",50,0,0.50);
        bookHistoRegion(dir_region_,"localPhi",360,-180,180);
        bookHistoRegion(dir_region_,"localTheta",180,-0.5,179.5);
        bookHistoRegion(dir_region_,"localTrackAngle",180,-0.5,179.5);
        bookHistoRegion(dir_region_,"crossX",100,-200,200);
        bookHistoRegion(dir_region_,"crossY",100,-200,200);
        bookHistoRegion(dir_region_,"crossZ",100,-250,250);
        bookHistoRegion(dir_region_,"crossErrX",100,-0.01,0.15);
        bookHistoRegion(dir_region_,"crossErrY",100,-0.01,0.15);
        bookHistoRegion(dir_region_,"crossErrZ",100,-0.5,19.5);
        bookHisto(dir_offtrack_cross_,"off_crossMultiplicity",30,-0.5,29.5);
        bookHisto(dir_offtrack_cross_,"off_crossX",100,-200,200);
        bookHisto(dir_offtrack_cross_,"off_crossY",100,-200,200);
        bookHisto(dir_offtrack_cross_,"off_crossZ",100,-250,250);
        bookHisto(dir_offtrack_cross_,"off_crossErrX",100,-0.01,0.15);
        bookHisto(dir_offtrack_cross_,"off_crossErrY",100,-0.01,0.15);
        bookHisto(dir_offtrack_cross_,"off_crossErrZ",100,-0.5,19.5);
        bookHisto(dir_offtrack_cluster_,"off_clusterMultiplicity",30,-0.5,29.5);
        bookHisto(dir_offtrack_cluster_,"off_stripMultiplicity",30,-0.5,29.5);
        bookHisto(dir_offtrack_cluster_,"off_seedStrip",256,0.5,256.5);
         bookHisto(dir_offtrack_cluster_,"off_seedCharge",100,-0.5,299.5);
//        bookHisto(dir_offtrack_cluster_,"off_seedCharge",50,0,0.50);
         bookHisto(dir_offtrack_cluster_,"off_clusterCharge",300,-0.5,299.5);
//        bookHisto(dir_offtrack_cluster_,"off_clusterCharge",50,0,0.50);
        bookHisto(dir_offtrack_cluster_,"off_centroid",256,0.5,256.5);
        bookHisto(dir_offtrack_hit_,"off_hitMultiplicity",30,-0.5,29.5);
        bookHisto(dir_offtrack_hit_,"off_hitStrip",256,0.5,256.5);
        bookHisto(dir_offtrack_hit_,"off_hitAdc",8,-0.5,7.5);
        bookHisto(dir_offtrack_hit_,"off_hitBco",256,0.5,256.5);
        bookHistoSensor(dir_offtrack_sensor_,"off_strip",256,0.5,256.5);
        bookHistoSensor(dir_offtrack_sensor_,"off_adc",8,-0.5,7.5);
        bookHistoSensor(dir_offtrack_sensor_,"off_seedStrip",256,0.5,256.5);
         bookHistoSensor(dir_offtrack_sensor_,"off_seedCharge",100,-0.5,299.5);
//        bookHistoSensor(dir_offtrack_sensor_,"off_seedCharge",50,0,0.50);
         bookHistoSensor(dir_offtrack_sensor_,"off_clusterCharge",300,-0.5,299.5);
//        bookHistoSensor(dir_offtrack_sensor_,"off_clusterCharge",50,0,0.50);
        bookHistoSensor(dir_offtrack_sensor_,"off_stripMultiplicity",30,-0.5,29.5);
        bookHisto(dir_offtrack_sensor_,"off_occupancy_sensor","Channel","Sensor",256,0,256,132,0,132);
        bookHisto(dir_offtrack_module_,"off_occupancy_module","Channel","Module",512,0,512,66,0,66);
        bookHistoRegion(dir_offtrack_region_,"off_clusterCharge",300,-0.5,299.5);
        bookHisto(dir_event_,"event_occupancy_sensor","Channel","Sensor",256,0,256,132,0,132);
        bookHisto(dir_event_cross_,"event_crossMultiplicity",20,-0.5,19.5);
        bookHisto(dir_event_cross_,"event_crossX",100,-200,200);
        bookHisto(dir_event_cross_,"event_crossY",100,-200,200);
        bookHisto(dir_event_cross_,"event_crossZ",100,-250,250);
        bookHisto(dir_event_cross_,"event_crossErrX",100,-0.01,0.15);
        bookHisto(dir_event_cross_,"event_crossErrY",100,-0.01,0.15);
        bookHisto(dir_event_cross_,"event_crossErrZ",100,-0.5,19.5);
        bookHisto(dir_event_cluster_,"event_clusterMultiplicity",30,-0.5,29.5);
        bookHisto(dir_event_cluster_,"event_stripMultiplicity",30,-0.5,29.5);
        bookHisto(dir_event_cluster_,"event_seedStrip",256,0.5,256.5);
         bookHisto(dir_event_cluster_,"event_seedCharge",100,-0.5,299.5);
//        bookHisto(dir_event_cluster_,"event_seedCharge",50,0,0.50);
         bookHisto(dir_event_cluster_,"event_clusterCharge",300,-0.5,299.5);
//        bookHisto(dir_event_cluster_,"event_clusterCharge",50,0,0.50);
        bookHisto(dir_event_cluster_,"event_centroid",256,0.5,256.5);
        bookHisto(dir_event_hit_,"event_hitMultiplicity",30,-0.5,29.5);
        bookHisto(dir_event_hit_,"event_hitStrip",256,0.5,256.5);
        bookHisto(dir_event_hit_,"event_hitAdc",8,-0.5,7.5);
        bookHisto(dir_event_hit_,"event_hitBco",256,0.5,256.5);
        bookHisto(dir_event_hit_,"event_dgtzStrip",256,0.5,256.5);
        bookHistoLorentz("sizeAngle",20,-0.5,19.5);
    }

    public Map getHisto(String key) {
        return this.m_;
    }

    public void bookHisto(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        Histo thhisto = new Histo(dir,name);
        thhisto.book(name,nBins,xMin,xMax);
        m_.put(name,thhisto);
    }

    public void bookHisto(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        Histo thhisto = new Histo(dir,name);
        thhisto.book(name,xTitle,yTitle,nBinsX,xMin,xMax,nBinsY,yMin,yMax);
        m_.put(name,thhisto);
    }

    public void bookHistoLorentz(String name, int nBins, double xMin, double xMax) {
        HistoLorentz thhistolorentz = new HistoLorentz(dir_lorentz_,name);
        thhistolorentz.book(name,nBins,xMin,xMax);
        m_lorentz_.put(name,thhistolorentz);
    }

    public void bookHistoSensor(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        HistoSensor thhistosensor = new HistoSensor(rootdir_,dir,name);
        thhistosensor.book(name,nBins,xMin,xMax);
        m_sensor_.put(name,thhistosensor);
    }

    public void bookHistoSensor(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        HistoSensor thhistosensor = new HistoSensor(rootdir_,dir,name);
        thhistosensor.book(name,xTitle,yTitle,nBinsX,xMin,xMax,nBinsY,yMin,yMax);
        m_sensor_.put(name,thhistosensor);
    }

    public void bookHistoModule(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        HistoModule thhistomodule = new HistoModule(rootdir_,dir,name);
        thhistomodule.book(name,nBins,xMin,xMax);
        m_module_.put(name,thhistomodule);
    }

    public void bookHistoModule(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        HistoModule thhistomodule = new HistoModule(rootdir_,dir,name);
        thhistomodule.book(name,xTitle,yTitle,nBinsX,xMin,xMax,nBinsY,yMin,yMax);
        m_module_.put(name,thhistomodule);
    }

    public void bookHistoRegion(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        HistoRegion thhistoregion = new HistoRegion(rootdir_,dir,name);
        thhistoregion.book(name,nBins,xMin,xMax);
        m_region_.put(name,thhistoregion);
    }

    public void bookHistoRegion(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        HistoRegion thhistoregion = new HistoRegion(rootdir_,dir,name);
        thhistoregion.book(name,xTitle,yTitle,nBinsX,xMin,xMax,nBinsY,yMin,yMax);
        m_region_.put(name,thhistoregion);
    }
}

