package org.jlab.svt.validation;

import org.root.group.TDirectory;
import java.util.HashMap;
import java.util.Map;

public class SVTHistos {
    public TDirectory rootDir;
    public TDirectory dirTrack;
    public TDirectory dirTrajectory;
    public TDirectory dirCross;
    public TDirectory dirCluster;
    public TDirectory dirHit;
    public TDirectory dirSensor;
    public TDirectory dirModule;
    public TDirectory dirRegion;
    public TDirectory dirOfftrack;
    public TDirectory dirOfftrackCross;
    public TDirectory dirOfftrackCluster;
    public TDirectory dirOfftrackHit;
    public TDirectory dirOfftrackSensor;
    public TDirectory dirOfftrackModule;
    public TDirectory dirOfftrackRegion;
    public TDirectory dirEvent;
    public TDirectory dirEventCross;
    public TDirectory dirEventCluster;
    public TDirectory dirEventHit;
    public TDirectory dirLorentz;
    public Map<String,Histo> histoMap = new HashMap<String,Histo>();
    public Map<String,HistoSensor> histoSensorMap = new HashMap<String,HistoSensor>();
    public Map<String,HistoModule> histoModuleMap = new HashMap<String,HistoModule>();
    public Map<String,HistoRegion> histoRegionMap = new HashMap<String,HistoRegion>();
    public Map<String,HistoLorentz> histoLorentzMap = new HashMap<String,HistoLorentz>();

    public SVTHistos(){
        rootDir = new TDirectory();
        dirTrack = new TDirectory("Track");
        rootDir.addDirectory(dirTrack);
        dirTrajectory = new TDirectory("Trajectory");
        rootDir.addDirectory(dirTrajectory);
        dirCross = new TDirectory("Cross");
        rootDir.addDirectory(dirCross);
        dirCluster = new TDirectory("Cluster");
        rootDir.addDirectory(dirCluster);
        dirHit = new TDirectory("Hit");
        rootDir.addDirectory(dirHit);
        dirSensor = new TDirectory("Sensor");
        rootDir.addDirectory(dirSensor);
        dirModule = new TDirectory("Module");
        rootDir.addDirectory(dirModule);
        dirRegion = new TDirectory("Region");
        rootDir.addDirectory(dirRegion);
        dirOfftrack = new TDirectory("Offtrack");
        rootDir.addDirectory(dirOfftrack);
        dirOfftrackCross = new TDirectory("Offtrack/Cross");
        rootDir.addDirectory(dirOfftrackCross);
        dirOfftrackCluster = new TDirectory("Offtrack/Cluster");
        rootDir.addDirectory(dirOfftrackCluster);
        dirOfftrackHit = new TDirectory("Offtrack/Hit");
        rootDir.addDirectory(dirOfftrackHit);
        dirOfftrackSensor = new TDirectory("Offtrack/Sensor");
        rootDir.addDirectory(dirOfftrackSensor);
        dirOfftrackModule = new TDirectory("Offtrack/Module");
        rootDir.addDirectory(dirOfftrackModule);
        dirOfftrackRegion = new TDirectory("Offtrack/Region");
        rootDir.addDirectory(dirOfftrackRegion);
        dirEvent = new TDirectory("Event");
        rootDir.addDirectory(dirEvent);
        dirEventCross = new TDirectory("Event/Cross");
        rootDir.addDirectory(dirEventCross);
        dirEventCluster = new TDirectory("Event/Cluster");
        rootDir.addDirectory(dirEventCluster);
        dirEventHit = new TDirectory("Event/Hit");
        rootDir.addDirectory(dirEventHit);
        dirLorentz = new TDirectory("Lorentz");
        rootDir.addDirectory(dirLorentz);
    }

    public void book() {
        bookHisto(dirTrack,"trackMultiplicity",10,-0.5,9.5);
        bookHisto(dirTrack,"normChi2",30,-0.5,29.5);
        bookHisto(dirTrack,"phi",180,-0.5,179.5);
        bookHisto(dirTrack,"theta",180,-0.5,179.5);
        bookHisto(dirTrack,"thetaPhi","phi","theta",180,-0.5,179.5,180,-0.5,179.5);
        bookHisto(dirTrajectory,"localPhi",360,-180,180);
        bookHisto(dirTrajectory,"localTheta",180,-0.5,179.5);
        bookHisto(dirTrajectory,"localTrackAngle",180,-0.5,179.5);
        bookHisto(dirTrajectory,"localX",100,-200,200);
        bookHisto(dirTrajectory,"localY",100,-200,200);
        bookHisto(dirTrajectory,"localZ",100,-250,250);
        bookHisto(dirCross,"crossMultiplicity",20,-0.5,19.5);
        bookHisto(dirCross,"crossX",100,-200,200);
        bookHisto(dirCross,"crossY",100,-200,200);
        bookHisto(dirCross,"crossZ",100,-250,250);
        bookHisto(dirCross,"crossErrX",100,-0.01,0.15);
        bookHisto(dirCross,"crossErrY",100,-0.01,0.15);
        bookHisto(dirCross,"crossErrZ",100,-0.5,19.5);
        bookHisto(dirCluster,"clusterMultiplicity",20,-0.5,19.5);
        bookHisto(dirCluster,"stripMultiplicity",30,-0.5,29.5);
        bookHisto(dirCluster,"seedStrip",256,0.5,256.5);
         bookHisto(dirCluster,"seedCharge",100,-0.5,299.5);
//        bookHisto(dirCluster,"seedCharge",50,0,0.50);
         bookHisto(dirCluster,"clusterCharge",300,-0.5,299.5);
//        bookHisto(dirCluster,"clusterCharge",50,0,0.50);
         bookHisto(dirCluster,"correctedClusterCharge",300,-0.5,299.5);
//        bookHisto(dirCluster,"correctedClusterCharge",50,0,0.50);
        bookHisto(dirCluster,"centroid",256,0.5,256.5);
        bookHisto(dirHit,"hitMultiplicity",30,-0.5,29.5);
        bookHisto(dirHit,"hitStrip",256,0.5,256.5);
        bookHisto(dirHit,"hitAdc",8,-0.5,7.5);
        bookHisto(dirHit,"hitBco",256,0.5,256.5);
        bookHistoSensor(dirSensor,"fitResidual",200,-3,3);
        bookHistoSensor(dirSensor,"strip",256,0.5,256.5);
        bookHistoSensor(dirSensor,"adc",8,-0.5,7.5);
         bookHistoSensor(dirSensor,"clusterCharge",300,-0.5,299.5);
//        bookHistoSensor(dirSensor,"clusterCharge",50,0,0.50);
         bookHistoSensor(dirSensor,"correctedClusterCharge",300,-0.5,299.5);
//        bookHistoSensor(dirSensor,"correctedClusterCharge",50,0,0.50);
        bookHistoSensor(dirSensor,"stripMultiplicity",30,-0.5,29.5);
         bookHistoSensor(dirSensor,"seedCharge",100,-0.5,299.5);
//        bookHistoSensor(dirSensor,"seedCharge",50,0,0.50);
        bookHistoSensor(dirSensor,"fitResidual_angle","track angle","fitResidual",180,-0.5,179.5,200,-3,3);
        bookHistoSensor(dirSensor,"fitResidual_phi","phi","fitResidual",180,-0.5,179.5,200,-3,3);
        bookHistoSensor(dirSensor,"fitResidual_theta","theta","fitResidual",180,-0.5,179.5,200,-3,3);
        bookHistoSensor(dirSensor,"fitResidual_x","X","fitResidual",200,-200,200,200,-3,3);
        bookHistoSensor(dirSensor,"fitResidual_y","Y","fitResidual",200,-200,200,200,-3,3);
        bookHistoSensor(dirSensor,"fitResidual_z","Z","fitResidual",200,-150,250,200,-3,3);
        bookHistoSensor(dirSensor,"localPhi",360,-180,180);
        bookHistoSensor(dirSensor,"localTheta",180,-0.5,179.5);
        bookHistoSensor(dirSensor,"localTrackAngle",180,-0.5,179.5);
        bookHisto(dirSensor,"occupancy_sensor","Channel","Sensor",256,0,256,132,0,132);
        bookHisto(dirSensor,"inefficiency_sensor",132,0.5,132.5);
        // bookHisto(dirSensor,"fitResidual_sensor","sensor","fitResidual",132,0.5,132.5,100,-1.5,1.5);
        // bookHisto(dirSensor,"fitResidual_sensor1",132,0.5,132.5);
         bookHistoModule(dirModule,"clusterCharge",300,-0.5,299.5);
//        bookHistoModule(dirModule,"clusterCharge",50,0,0.50);
        bookHistoModule(dirModule,"crossX",100,-200,200);
        bookHistoModule(dirModule,"crossY",100,-200,200);
        bookHistoModule(dirModule,"crossZ",100,-250,250);
        bookHistoModule(dirModule,"crossErrX",100,-0.01,0.15);
        bookHistoModule(dirModule,"crossErrY",100,-0.01,0.15);
        bookHistoModule(dirModule,"crossErrZ",100,-0.5,19.5);
        bookHisto(dirModule,"occupancy_module","Channel","Module",512,0,512,66,0,66);
         bookHistoRegion(dirRegion,"clusterCharge",300,-0.5,299.5);
//        bookHistoRegion(dirRegion,"clusterCharge",50,0,0.50);
        bookHistoRegion(dirRegion,"localPhi",360,-180,180);
        bookHistoRegion(dirRegion,"localTheta",180,-0.5,179.5);
        bookHistoRegion(dirRegion,"localTrackAngle",180,-0.5,179.5);
        bookHistoRegion(dirRegion,"crossX",100,-200,200);
        bookHistoRegion(dirRegion,"crossY",100,-200,200);
        bookHistoRegion(dirRegion,"crossZ",100,-250,250);
        bookHistoRegion(dirRegion,"crossErrX",100,-0.01,0.15);
        bookHistoRegion(dirRegion,"crossErrY",100,-0.01,0.15);
        bookHistoRegion(dirRegion,"crossErrZ",100,-0.5,19.5);
        bookHisto(dirOfftrackCross,"off_crossMultiplicity",30,-0.5,29.5);
        bookHisto(dirOfftrackCross,"off_crossX",100,-200,200);
        bookHisto(dirOfftrackCross,"off_crossY",100,-200,200);
        bookHisto(dirOfftrackCross,"off_crossZ",100,-250,250);
        bookHisto(dirOfftrackCross,"off_crossErrX",100,-0.01,0.15);
        bookHisto(dirOfftrackCross,"off_crossErrY",100,-0.01,0.15);
        bookHisto(dirOfftrackCross,"off_crossErrZ",100,-0.5,19.5);
        bookHisto(dirOfftrackCluster,"off_clusterMultiplicity",30,-0.5,29.5);
        bookHisto(dirOfftrackCluster,"off_stripMultiplicity",30,-0.5,29.5);
//        bookHisto(dirOfftrackCluster,"off_seedStrip",256,0.5,256.5);
        bookHisto(dirOfftrackCluster,"off_seedStrip",254,2.5,256.5);
         bookHisto(dirOfftrackCluster,"off_seedCharge",100,-0.5,299.5);
//        bookHisto(dirOfftrackCluster,"off_seedCharge",50,0,0.50);
         bookHisto(dirOfftrackCluster,"off_clusterCharge",300,-0.5,299.5);
//        bookHisto(dirOfftrackCluster,"off_clusterCharge",50,0,0.50);
        bookHisto(dirOfftrackCluster,"off_centroid",256,0.5,256.5);
        bookHisto(dirOfftrackHit,"off_hitMultiplicity",30,-0.5,29.5);
        bookHisto(dirOfftrackHit,"off_hitStrip",256,0.5,256.5);
        bookHisto(dirOfftrackHit,"off_hitAdc",8,-0.5,7.5);
        bookHisto(dirOfftrackHit,"off_hitBco",256,0.5,256.5);
//        bookHistoSensor(dirOfftrackSensor,"off_strip",256,0.5,256.5);
        bookHistoSensor(dirOfftrackSensor,"off_strip",254,2.5,256.5);
        bookHistoSensor(dirOfftrackSensor,"off_adc",8,-0.5,7.5);
        bookHistoSensor(dirOfftrackSensor,"off_seedStrip",256,0.5,256.5);
         bookHistoSensor(dirOfftrackSensor,"off_seedCharge",100,-0.5,299.5);
//        bookHistoSensor(dirOfftrackSensor,"off_seedCharge",50,0,0.50);
         bookHistoSensor(dirOfftrackSensor,"off_clusterCharge",300,-0.5,299.5);
//        bookHistoSensor(dirOfftrackSensor,"off_clusterCharge",50,0,0.50);
        bookHistoSensor(dirOfftrackSensor,"off_stripMultiplicity",30,-0.5,29.5);
        bookHisto(dirOfftrackSensor,"off_occupancy_sensor","Channel","Sensor",256,0,256,132,0,132);
        bookHisto(dirOfftrackModule,"off_occupancy_module","Channel","Module",512,0,512,66,0,66);
        bookHistoRegion(dirOfftrackRegion,"off_clusterCharge",300,-0.5,299.5);
        bookHisto(dirEvent,"event_occupancy_sensor","Channel","Sensor",256,0,256,132,0,132);
        bookHisto(dirEventCross,"event_crossMultiplicity",20,-0.5,19.5);
        bookHisto(dirEventCross,"event_crossX",100,-200,200);
        bookHisto(dirEventCross,"event_crossY",100,-200,200);
        bookHisto(dirEventCross,"event_crossZ",100,-250,250);
        bookHisto(dirEventCross,"event_crossErrX",100,-0.01,0.15);
        bookHisto(dirEventCross,"event_crossErrY",100,-0.01,0.15);
        bookHisto(dirEventCross,"event_crossErrZ",100,-0.5,19.5);
        bookHisto(dirEventCluster,"event_clusterMultiplicity",30,-0.5,29.5);
        bookHisto(dirEventCluster,"event_stripMultiplicity",30,-0.5,29.5);
        bookHisto(dirEventCluster,"event_seedStrip",256,0.5,256.5);
         bookHisto(dirEventCluster,"event_seedCharge",100,-0.5,299.5);
//        bookHisto(dirEventCluster,"event_seedCharge",50,0,0.50);
         bookHisto(dirEventCluster,"event_clusterCharge",300,-0.5,299.5);
//        bookHisto(dirEventCluster,"event_clusterCharge",50,0,0.50);
        bookHisto(dirEventCluster,"event_centroid",256,0.5,256.5);
        bookHisto(dirEventHit,"event_hitMultiplicity",30,-0.5,29.5);
        bookHisto(dirEventHit,"event_hitStrip",256,0.5,256.5);
        bookHisto(dirEventHit,"event_hitAdc",8,-0.5,7.5);
        bookHisto(dirEventHit,"event_hitBco",256,0.5,256.5);
        bookHisto(dirEventHit,"event_dgtzStrip",256,0.5,256.5);
        bookHistoLorentz("sizeAngle",20,-0.5,19.5);
    }

    public Map getHisto(String key) {
        return this.histoMap;
    }

    public void bookHisto(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        Histo histo = new Histo(dir,name);
        histo.book(name,nBins,xMin,xMax);
        histoMap.put(name,histo);
    }

    public void bookHisto(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        Histo histo = new Histo(dir,name);
        histo.book(name,xTitle,yTitle,nBinsX,xMin,xMax,nBinsY,yMin,yMax);
        histoMap.put(name,histo);
    }

    public void bookHistoLorentz(String name, int nBins, double xMin, double xMax) {
        HistoLorentz histoLorentz = new HistoLorentz(dirLorentz,name);
        histoLorentz.book(name,nBins,xMin,xMax);
        histoLorentzMap.put(name,histoLorentz);
    }

    public void bookHistoSensor(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        HistoSensor histoSensor = new HistoSensor(rootDir,dir,name);
        histoSensor.book(name,nBins,xMin,xMax);
        histoSensorMap.put(name,histoSensor);
    }

    public void bookHistoSensor(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        HistoSensor histoSensor = new HistoSensor(rootDir,dir,name);
        histoSensor.book(name,xTitle,yTitle,nBinsX,xMin,xMax,nBinsY,yMin,yMax);
        histoSensorMap.put(name,histoSensor);
    }

    public void bookHistoModule(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        HistoModule histoModule = new HistoModule(rootDir,dir,name);
        histoModule.book(name,nBins,xMin,xMax);
        histoModuleMap.put(name,histoModule);
    }

    public void bookHistoModule(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        HistoModule histoModule = new HistoModule(rootDir,dir,name);
        histoModule.book(name,xTitle,yTitle,nBinsX,xMin,xMax,nBinsY,yMin,yMax);
        histoModuleMap.put(name,histoModule);
    }

    public void bookHistoRegion(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        HistoRegion histoRegion = new HistoRegion(rootDir,dir,name);
        histoRegion.book(name,nBins,xMin,xMax);
        histoRegionMap.put(name,histoRegion);
    }

    public void bookHistoRegion(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        HistoRegion histoRegion = new HistoRegion(rootDir,dir,name);
        histoRegion.book(name,xTitle,yTitle,nBinsX,xMin,xMax,nBinsY,yMin,yMax);
        histoRegionMap.put(name,histoRegion);
    }
}

