package org.jlab.svt.validation;

import org.root.group.TDirectory;

import java.util.HashMap;
import java.util.Map;

public class SVTHistos {
    public TDirectory rootDir;
    public TDirectory dirGenPart;
    public TDirectory dirGraph;
    public TDirectory dirTrack;
    public TDirectory dirCosmicTrack;
    public TDirectory dirTrajectory;
    public TDirectory dirCross;
    public TDirectory dirCrossSVT;
    public TDirectory dirCrossBMT;
    public TDirectory dirCluster;
    public TDirectory dirClusterSVT;
    public TDirectory dirClusterBMT;
    public TDirectory dirHit;
    public TDirectory dirHitSVT;
    public TDirectory dirHitBMT;
    public TDirectory dirMcHit;
    public TDirectory dirSensor;
    public TDirectory dirModule;
    public TDirectory dirRegion;
    public TDirectory dirOfftrack;
    public TDirectory dirOfftrackCross;
    public TDirectory dirOfftrackCrossSVT;
    public TDirectory dirOfftrackCrossBMT;
    public TDirectory dirOfftrackCluster;
    public TDirectory dirOfftrackClusterSVT;
    public TDirectory dirOfftrackClusterBMT;
    public TDirectory dirOfftrackHit;
    public TDirectory dirOfftrackHitSVT;
    public TDirectory dirOfftrackHitBMT;
    public TDirectory dirOfftrackSensor;
    public TDirectory dirOfftrackSensorSVT;
    public TDirectory dirOfftrackSensorBMT;
    public TDirectory dirOfftrackModule;
    public TDirectory dirOfftrackModuleSVT;
    public TDirectory dirOfftrackModuleBMT;
    public TDirectory dirOfftrackRegion;
    public TDirectory dirOfftrackRegionSVT;
    public TDirectory dirOfftrackRegionBMT;
    public TDirectory dirEvent;
    public TDirectory dirEventSVT;
    public TDirectory dirEventBMT;
    public TDirectory dirEventSVTCross;
    public TDirectory dirEventBMTCross;
    public TDirectory dirEventSVTCluster;
    public TDirectory dirEventBMTCluster;
    public TDirectory dirEventSVTHit;
    public TDirectory dirEventBMTHit;
    public TDirectory dirLorentz;
    public Map<String, Histo> histoMap = new HashMap<String, Histo>();
    public Map<String, HistoSensor> histoSensorMap = new HashMap<String, HistoSensor>();
    public Map<String, HistoModule> histoModuleMap = new HashMap<String, HistoModule>();
    public Map<String, HistoRegion> histoRegionMap = new HashMap<String, HistoRegion>();
    public Map<String, HistoLorentz> histoLorentzMap = new HashMap<String, HistoLorentz>();

    public SVTHistos() {
        rootDir = new TDirectory();
        dirGenPart = new TDirectory("GenPart");
        rootDir.addDirectory(dirGenPart);
        dirGraph = new TDirectory("Graph");
        rootDir.addDirectory(dirGraph);
        dirTrack = new TDirectory("Track");
        rootDir.addDirectory(dirTrack);
        dirCosmicTrack = new TDirectory("CosmicTrack");
        rootDir.addDirectory(dirCosmicTrack);
        dirTrajectory = new TDirectory("Trajectory");
        rootDir.addDirectory(dirTrajectory);
        dirCross = new TDirectory("Cross");
        rootDir.addDirectory(dirCross);
        dirCrossSVT = new TDirectory("Cross/SVT");
        rootDir.addDirectory(dirCrossSVT);
        dirCrossBMT = new TDirectory("Cross/BMT");
        rootDir.addDirectory(dirCrossBMT);
        dirCluster = new TDirectory("Cluster");
        rootDir.addDirectory(dirCluster);
        dirClusterSVT = new TDirectory("Cluster/SVT");
        rootDir.addDirectory(dirClusterSVT);
        dirClusterBMT = new TDirectory("Cluster/BMT");
        rootDir.addDirectory(dirClusterBMT);
        dirHit = new TDirectory("Hit");
        rootDir.addDirectory(dirHit);
        dirHitSVT = new TDirectory("Hit/SVT");
        rootDir.addDirectory(dirHitSVT);
        dirHitBMT = new TDirectory("Hit/BMT");
        rootDir.addDirectory(dirHitBMT);
        dirMcHit = new TDirectory("McHit");
        rootDir.addDirectory(dirMcHit);
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
        dirOfftrackCrossSVT = new TDirectory("Offtrack/Cross/SVT");
        rootDir.addDirectory(dirOfftrackCrossSVT);
        dirOfftrackCrossBMT = new TDirectory("Offtrack/Cross/BMT");
        rootDir.addDirectory(dirOfftrackCrossBMT);
        dirOfftrackCluster = new TDirectory("Offtrack/Cluster");
        rootDir.addDirectory(dirOfftrackCluster);
        dirOfftrackClusterSVT = new TDirectory("Offtrack/Cluster/SVT");
        rootDir.addDirectory(dirOfftrackClusterSVT);
        dirOfftrackClusterBMT = new TDirectory("Offtrack/Cluster/BMT");
        rootDir.addDirectory(dirOfftrackClusterBMT);
        dirOfftrackHit = new TDirectory("Offtrack/Hit");
        rootDir.addDirectory(dirOfftrackHit);
        dirOfftrackHitSVT = new TDirectory("Offtrack/Hit/SVT");
        rootDir.addDirectory(dirOfftrackHitSVT);
        dirOfftrackHitBMT = new TDirectory("Offtrack/Hit/BMT");
        rootDir.addDirectory(dirOfftrackHitBMT);
        dirOfftrackSensor = new TDirectory("Offtrack/Sensor");
        rootDir.addDirectory(dirOfftrackSensor);
        dirOfftrackSensorSVT = new TDirectory("Offtrack/Sensor/SVT");
        rootDir.addDirectory(dirOfftrackSensorSVT);
        dirOfftrackSensorBMT = new TDirectory("Offtrack/Sensor/BMT");
        rootDir.addDirectory(dirOfftrackSensorBMT);
        dirOfftrackModule = new TDirectory("Offtrack/Module");
        rootDir.addDirectory(dirOfftrackModule);
        dirOfftrackModuleSVT = new TDirectory("Offtrack/Module/SVT");
        rootDir.addDirectory(dirOfftrackModuleSVT);
        dirOfftrackModuleBMT = new TDirectory("Offtrack/Module/BMT");
        rootDir.addDirectory(dirOfftrackModuleBMT);
        dirOfftrackRegion = new TDirectory("Offtrack/Region");
        rootDir.addDirectory(dirOfftrackRegion);
        dirOfftrackRegionSVT = new TDirectory("Offtrack/Region/SVT");
        rootDir.addDirectory(dirOfftrackRegionSVT);
        dirOfftrackRegionBMT = new TDirectory("Offtrack/Region/BMT");
        rootDir.addDirectory(dirOfftrackRegionBMT);
        dirEvent = new TDirectory("Event");
        rootDir.addDirectory(dirEvent);
        dirEventSVT = new TDirectory("Event/SVT");
        rootDir.addDirectory(dirEventSVT);
        dirEventBMT = new TDirectory("Event/BMT");
        rootDir.addDirectory(dirEventBMT);
        dirEventSVTCross = new TDirectory("Event/SVT/Cross");
        rootDir.addDirectory(dirEventSVTCross);
        dirEventBMTCross = new TDirectory("Event/BMT/Cross");
        rootDir.addDirectory(dirEventBMTCross);
        dirEventSVTCluster = new TDirectory("Event/SVT/Cluster");
        rootDir.addDirectory(dirEventSVTCluster);
        dirEventBMTCluster = new TDirectory("Event/BMT/Cluster");
        rootDir.addDirectory(dirEventBMTCluster);
        dirEventSVTHit = new TDirectory("Event/SVT/Hit");
        rootDir.addDirectory(dirEventSVTHit);
        dirEventBMTHit = new TDirectory("Event/BMT/Hit");
        rootDir.addDirectory(dirEventBMTHit);
        dirLorentz = new TDirectory("Lorentz");
        rootDir.addDirectory(dirLorentz);
    }

    public void book() {
        bookHisto(dirGenPart, "gen_nGenParts", 100, -0.5, 99.5);
        bookHisto(dirGenPart, "gen_pId", 100, -300, 300);
        bookHisto(dirGenPart, "gen_pX", 100, -3, 3); // in GeV
        bookHisto(dirGenPart, "gen_pY", 100, -3, 3);
        bookHisto(dirGenPart, "gen_pZ", 100, -3, 3);
        bookHisto(dirGenPart, "gen_pT", 50, 0.4, 2.2); // in GeV
        bookHisto(dirGenPart, "gen_p", 50, 0.4, 2.2); // in GeV
        bookHisto(dirGenPart, "gen_phi", 100, -3.5, 3.5);
        bookHisto(dirGenPart, "gen_theta", 100, 0, 3.5);
        bookHisto(dirGenPart, "gen_vX", 100, -3000, 3000);
        bookHisto(dirGenPart, "gen_vY", 100, -3000, 3000);
        bookHisto(dirGenPart, "gen_vZ", 100, -2000, 2000);
        bookHisto(dirMcHit, "mchit_pId", 100, -300, 300);
        bookHisto(dirMcHit, "mchit_mPId", 100, -300, 300);
        bookHisto(dirMcHit, "mchit_tId", 100, -0.5, 1999.5);
        bookHisto(dirMcHit, "mchit_mTId", 100, -300, 300);
        bookHisto(dirMcHit, "mchit_oTId", 100, -300, 300);
        bookHisto(dirMcHit, "mchit_trackE", 100, -0.5, 29999.5);
        bookHisto(dirMcHit, "mchit_totEDep", 100, -0.5, 49.5);
        bookHisto(dirMcHit, "mchit_avgX", 100, -200, 200);
        bookHisto(dirMcHit, "mchit_avgY", 100, -200, 200);
        bookHisto(dirMcHit, "mchit_avgZ", 100, -300, 300);
        bookHisto(dirMcHit, "mchit_avgLX", 100, -30, 30);
        bookHisto(dirMcHit, "mchit_avgLY", 100, -0.2, 0.2);
        bookHisto(dirMcHit, "mchit_avgLZ", 100, -60, 60);
        bookHisto(dirMcHit, "mchit_pX", 100, -3, 3);
        bookHisto(dirMcHit, "mchit_pY", 100, -0.05, 2.95);
        bookHisto(dirMcHit, "mchit_pZ", 100, -3, 3);
        bookHisto(dirMcHit, "mchit_pT", 100, -0.05, 2.95); // in GeV
        bookHisto(dirMcHit, "mchit_p", 100, -0.05, 2.95); // in GeV
        bookHisto(dirMcHit, "mchit_vX", 100, -400, 400);
        bookHisto(dirMcHit, "mchit_vY", 100, -400, 400);
        bookHisto(dirMcHit, "mchit_vZ", 100, -400, 400);
        bookHisto(dirMcHit, "mchit_mVX", 100, -400, 400);
        bookHisto(dirMcHit, "mchit_mVY", 100, -400, 400);
        bookHisto(dirMcHit, "mchit_mVZ", 100, -400, 400);
        bookHisto(dirMcHit, "mchit_avgT", 100, -0.5, 19999.5);
        bookHisto(dirMcHit, "mchit_hitN", 300, -0.5, 299.5);
        bookHisto(dirTrack, "fittingMethod", 3, -0.5, 2.5);
        bookHisto(dirTrack, "cX", 100, -30, 30);
        bookHisto(dirTrack, "cY", 100, -30, 30);
        bookHisto(dirTrack, "cZ", 100, -600, 600);
        bookHisto(dirTrack, "cUX", 100, -200, 200);
        bookHisto(dirTrack, "cUY", 100, -200, 200);
        bookHisto(dirTrack, "cUZ", 100, -200, 200);
        bookHisto(dirTrack, "pathLength", 200, -0.5, 599.5);
        bookHisto(dirTrack, "q", 3, -1, 1);
        bookHisto(dirTrack, "p", 50, -0.05, 2.95);
        bookHisto(dirTrack, "pT", 50, -0.05, 2.95); // -0.5,199.5
        bookHisto(dirTrack, "pEff", 50, 0.4, 2.2);
        bookHisto(dirTrack, "pTEff", 50, 0.4, 2.2); // -0.5,199.5
        bookHisto(dirTrack, "pTRes", 100, -30, 30); // pT resolution, %
        bookHisto(dirTrack, "pRes", 100, -3, 3); // p resolution, %
        bookHisto(dirTrack, "phi0", 200, 0, 6.5);
        bookHisto(dirTrack, "theta0", 200, 0, 3.5);
        bookHisto(dirTrack, "tanDip", 100, -0.5, 9.5);
        bookHisto(dirTrack, "phiRes", 200, -0.03, 0.03); // phi resolution
        bookHisto(dirTrack, "thetaRes", 200, -1, 1); // theta resolution
        bookHistos(dirTrack, "deltaPt", 40, -0.6, 0.6, 9); // pT - pT_gen pT bin
        bookHistos(dirTrack, "deltaP", 40, -0.6, 0.6, 9); // p - p_gen p bin
        bookHistos(dirTrack, "deltaPhi", 40, -180, 180, 9); // phi - phi_gen phi bin
        bookHistos(dirTrack, "deltaTheta", 40, -180, 180, 9); // theta - theta_gen theta bin
        bookHisto(dirTrack, "z0", 100, -5, 5);
        bookHisto(dirTrack, "d0", 100, -100, 100);
        bookHisto(dirTrack, "covD02", 100, -100000, 100000);
        bookHisto(dirTrack, "covD0Phi0", 100, -20000, 20000);
        bookHisto(dirTrack, "covD0Rho", 100, -200, 200);
        bookHisto(dirTrack, "covPhi02", 100, -200, 200);
        bookHisto(dirTrack, "covPhi0Rho", 100, -20, 20);
        bookHisto(dirTrack, "covRho2", 100, -0.6, 0.6);
        bookHisto(dirTrack, "covZ02", 100, 0, 0.5);
        bookHisto(dirTrack, "covTanDip2", 100, 0, 0.05);
        bookHisto(dirTrack, "cirFitChi2Ndf", 100, -0.5, 49.95);
        bookHisto(dirTrack, "linFitChi2Ndf", 100, -0.5, 49.95);
        bookHisto(dirTrack, "pTResPt", "pT", "pT resolution, %", 10, 0.4, 2.1, 80, -40, 40);
        bookHisto(dirTrack, "pResP", "p", "p resolution, %", 10, 0.4, 2.1, 40, 0, 40);
        bookHisto(dirCosmicTrack, "trackMultiplicity", 10, -0.5, 9.5);
        bookHisto(dirCosmicTrack, "normChi2", 30, -0.5, 29.5);
        bookHisto(dirCosmicTrack, "phi", 180, -0.5, 179.5);
        bookHisto(dirCosmicTrack, "theta", 180, -0.5, 179.5);
        bookHisto(dirCosmicTrack, "thetaPhi", "phi", "theta", 180, -0.5, 179.5, 180, -0.5, 179.5);
        bookHisto(dirTrajectory, "localPhi", 360, -180, 180);
        bookHisto(dirTrajectory, "localTheta", 180, -0.5, 179.5);
        bookHisto(dirTrajectory, "localTrackAngle", 180, -0.5, 179.5);
        bookHisto(dirTrajectory, "localX", 100, -200, 200);
        bookHisto(dirTrajectory, "localY", 100, -200, 200);
        bookHisto(dirTrajectory, "localZ", 100, -250, 250);
        bookHisto(dirCrossSVT, "crossMultiplicity_svt", 20, -0.5, 19.5);
        bookHisto(dirCrossSVT, "crossX_svt", 100, -200, 200);
        bookHisto(dirCrossSVT, "crossY_svt", 100, -200, 200);
        bookHisto(dirCrossSVT, "crossZ_svt", 100, -250, 250);
        bookHisto(dirCrossSVT, "crossErrX_svt", 100, -0.01, 0.15);
        bookHisto(dirCrossSVT, "crossErrY_svt", 100, -0.01, 0.15);
        bookHisto(dirCrossSVT, "crossErrZ_svt", 100, -0.5, 19.5);
        bookHisto(dirCrossBMT, "crossMultiplicity_bmt", 20, -0.5, 19.5);
        bookHisto(dirCrossBMT, "crossX_bmt", 100, -200, 200);
        bookHisto(dirCrossBMT, "crossY_bmt", 100, -200, 200);
        bookHisto(dirCrossBMT, "crossZ_bmt", 100, -250, 250);
        bookHisto(dirCrossBMT, "crossErrX_bmt", 100, -0.01, 0.15);
        bookHisto(dirCrossBMT, "crossErrY_bmt", 100, -0.01, 0.15);
        bookHisto(dirCrossBMT, "crossErrZ_bmt", 100, -0.5, 19.5);
        bookHisto(dirClusterSVT, "eta_svt", 100, 0, 1);
        bookHisto(dirClusterSVT, "etaAdc_svt", 100, 0, 1);
        bookHisto(dirClusterSVT, "clusterMultiplicity_svt", 20, -0.5, 19.5);
        bookHisto(dirClusterSVT, "stripMultiplicity_svt", 30, -0.5, 29.5);
        bookHisto(dirClusterSVT, "seedStrip_svt", 256, 0.5, 256.5);
        bookHisto(dirClusterSVT, "seedCharge_svt", 100, -0.5, 299.5);
        bookHisto(dirClusterSVT, "clusterCharge_svt", 100, -0.5, 299.5);
        bookHisto(dirClusterSVT, "correctedClusterCharge_svt", 300, -0.5, 299.5);
        bookHisto(dirClusterSVT, "centroid_svt", 256, 0.5, 256.5);
        bookHisto(dirClusterSVT, "centroidResidual_svt", 200, -10, 10);
        bookHisto(dirClusterSVT, "seedResidual_svt", 200, -10, 10);
        bookHisto(dirClusterBMT, "clusterMultiplicity_bmt", 20, -0.5, 19.5);
        bookHisto(dirClusterBMT, "stripMultiplicity_bmt", 30, -0.5, 29.5);
        bookHisto(dirClusterBMT, "seedStrip_bmt", 256, 0.5, 256.5);
        bookHisto(dirClusterBMT, "seedCharge_bmt", 100, -0.5, 299.5);
        bookHisto(dirClusterBMT, "clusterCharge_bmt", 100, -0.5, 299.5);
        bookHisto(dirClusterBMT, "correctedClusterCharge_bmt", 100, -0.5, 299.5);
        bookHisto(dirClusterBMT, "centroid_bmt", 256, 0.5, 256.5);
        bookHisto(dirClusterBMT, "centroidResidual_bmt", 200, -10, 10);
        bookHisto(dirClusterBMT, "seedResidual_bmt", 200, -10, 10);
        bookHisto(dirHitSVT, "hitMultiplicity_svt", 30, -0.5, 29.5);
        bookHisto(dirHitSVT, "hitStrip_svt", 256, 0.5, 256.5);
        bookHisto(dirHitSVT, "hitAdc_svt", 8, -0.5, 7.5);
        bookHisto(dirHitSVT, "hitBco_svt", 256, 0.5, 256.5);
        bookHisto(dirHitBMT, "hitMultiplicity_bmt", 30, -0.5, 29.5);
        bookHisto(dirHitBMT, "hitStrip_bmt", 256, 0.5, 256.5);
        bookHisto(dirHitBMT, "hitAdc_bmt", 8, -0.5, 7.5);
        bookHisto(dirHitBMT, "hitBco_bmt", 256, 0.5, 256.5);
        bookHistoSensor(dirSensor, "fitResidual", 200, -10, 10);//100,-2,2
        bookHistoSensor(dirSensor, "centroidResidual", 200, -10, 10);//100,-2,2
        bookHistoSensor(dirSensor, "seedResidual", 200, -10, 10);//100,-2,2
        bookHistoSensor(dirSensor, "strip", 256, 0.5, 256.5);
        bookHistoSensor(dirSensor, "adc", 8, -0.5, 7.5);
        bookHistoSensor(dirSensor, "clusterCharge", 100, -0.5, 299.5);
        bookHistoSensor(dirSensor, "correctedClusterCharge", 100, -0.5, 299.5);
        bookHistoSensor(dirSensor, "stripMultiplicity", 30, -0.5, 29.5);
        bookHistoSensor(dirSensor, "seedCharge", 100, -0.5, 299.5);
        bookHistoSensor(dirSensor, "layerEfficiency", 3, -1.5, 1.5);
        bookHistoSensor(dirSensor, "centroidResidual_angle", "track angle", "centroidResidual", 180, -0.5, 179.5, 200, -10, 10);
        bookHistoSensor(dirSensor, "centroidResidual_phi", "phi", "centroidResidual", 180, -0.5, 179.5, 200, -10, 10);
        bookHistoSensor(dirSensor, "centroidResidual_theta", "theta", "centroidResidual", 180, -0.5, 179.5, 200, -10, 10);
        bookHistoSensor(dirSensor, "centroidResidual_x", "X", "centroidResidual", 200, -200, 200, 200, -10, 10); //-3,3
        bookHistoSensor(dirSensor, "centroidResidual_y", "Y", "centroidResidual", 200, -200, 200, 200, -10, 10);
        bookHistoSensor(dirSensor, "centroidResidual_z", "Z", "centroidResidual", 200, -150, 250, 200, -10, 10);
        bookHistoSensor(dirSensor, "localPhi", 360, -180, 180);
        bookHistoSensor(dirSensor, "localTheta", 180, -0.5, 179.5);
        bookHistoSensor(dirSensor, "localTrackAngle", 180, -0.5, 179.5);
        bookHisto(dirSensor, "occupancy_sensor", "Channel", "Sensor", 256, 0, 256, 132, 0, 132);
        bookHisto(dirSensor, "inefficiency_sensor", 132, 0.5, 132.5);
        // bookHisto(dirSensor,"fitResidual_sensor","sensor","fitResidual",132,0.5,132.5,100,-1.5,1.5);
        // bookHisto(dirSensor,"fitResidual_sensor1",132,0.5,132.5);
        bookHistoModule(dirModule, "clusterCharge", 100, -0.5, 299.5);
        bookHistoModule(dirModule, "crossX", 100, -200, 200);
        bookHistoModule(dirModule, "crossY", 100, -200, 200);
        bookHistoModule(dirModule, "crossZ", 100, -250, 250);
        bookHistoModule(dirModule, "crossErrX", 100, -0.01, 0.15);
        bookHistoModule(dirModule, "crossErrY", 100, -0.01, 0.15);
        bookHistoModule(dirModule, "crossErrZ", 100, -0.5, 19.5);
        bookHisto(dirModule, "occupancy_module", "Channel", "Module", 512, 0, 512, 66, 0, 66);
        bookHistoRegion(dirRegion, "fitResidual", 200, -10, 10);//100,-2,2
        bookHistoRegion(dirRegion, "centroidResidual", 200, -10, 10);//100,-2,2
        bookHistoRegion(dirRegion, "seedResidual", 200, -10, 10);//100,-2,2
        bookHistoRegion(dirRegion, "strip", 256, 0.5, 256.5);
        bookHistoRegion(dirRegion, "adc", 8, -0.5, 7.5);
        bookHistoRegion(dirRegion, "stripMultiplicity", 30, -0.5, 29.5);
        bookHistoRegion(dirRegion, "seedCharge", 100, -0.5, 299.5);
        bookHistoRegion(dirRegion, "clusterCharge", 100, -0.5, 299.5);
        bookHistoRegion(dirRegion, "correctedClusterCharge", 100, -0.5, 299.5);
        bookHistoRegion(dirRegion, "localPhi", 360, -180, 180);
        bookHistoRegion(dirRegion, "localTheta", 180, -0.5, 179.5);
        bookHistoRegion(dirRegion, "localTrackAngle", 180, -0.5, 179.5);
        bookHistoRegion(dirRegion, "crossX", 100, -200, 200);
        bookHistoRegion(dirRegion, "crossY", 100, -200, 200);
        bookHistoRegion(dirRegion, "crossZ", 100, -250, 250);
        bookHistoRegion(dirRegion, "crossErrX", 100, -0.01, 0.15);
        bookHistoRegion(dirRegion, "crossErrY", 100, -0.01, 0.15);
        bookHistoRegion(dirRegion, "crossErrZ", 100, -0.5, 19.5);
        bookHisto(dirOfftrackCrossSVT, "off_crossMultiplicity_svt", 30, -0.5, 29.5);
        bookHisto(dirOfftrackCrossSVT, "off_crossX_svt", 100, -200, 200);
        bookHisto(dirOfftrackCrossSVT, "off_crossY_svt", 100, -200, 200);
        bookHisto(dirOfftrackCrossSVT, "off_crossZ_svt", 100, -250, 250);
        bookHisto(dirOfftrackCrossSVT, "off_crossErrX_svt", 100, -0.01, 0.15);
        bookHisto(dirOfftrackCrossSVT, "off_crossErrY_svt", 100, -0.01, 0.15);
        bookHisto(dirOfftrackCrossSVT, "off_crossErrZ_svt", 100, -0.5, 19.5);
        bookHisto(dirOfftrackCrossBMT, "off_crossMultiplicity_bmt", 30, -0.5, 29.5);
        bookHisto(dirOfftrackCrossBMT, "off_crossX_bmt", 100, -200, 200);
        bookHisto(dirOfftrackCrossBMT, "off_crossY_bmt", 100, -200, 200);
        bookHisto(dirOfftrackCrossBMT, "off_crossZ_bmt", 100, -250, 250);
        bookHisto(dirOfftrackCrossBMT, "off_crossErrX_bmt", 100, -0.01, 0.15);
        bookHisto(dirOfftrackCrossBMT, "off_crossErrY_bmt", 100, -0.01, 0.15);
        bookHisto(dirOfftrackCrossBMT, "off_crossErrZ_bmt", 100, -0.5, 19.5);
        bookHisto(dirOfftrackClusterSVT, "off_eta_svt", 100, 0, 1);
        bookHisto(dirOfftrackClusterSVT, "off_etaAdc_svt", 100, 0, 1);
        bookHisto(dirOfftrackClusterSVT, "off_clusterMultiplicity_svt", 30, -0.5, 29.5);
        bookHisto(dirOfftrackClusterSVT, "off_stripMultiplicity_svt", 30, -0.5, 29.5);
        bookHisto(dirOfftrackClusterSVT,"off_seedStrip_svt",256,0.5,256.5);
        bookHisto(dirOfftrackClusterSVT, "off_seedCharge_svt", 100, -0.5, 299.5);
        bookHisto(dirOfftrackClusterSVT, "off_clusterCharge_svt", 100, -0.5, 299.5);
        bookHisto(dirOfftrackClusterSVT, "off_centroid_svt", 256, 0.5, 256.5);
        bookHisto(dirOfftrackClusterSVT, "off_centroidResidual_svt", 200, -10, 10);
        bookHisto(dirOfftrackClusterSVT, "off_seedResidual_svt", 200, -10, 10);
        bookHisto(dirOfftrackClusterBMT, "off_clusterMultiplicity_bmt", 30, -0.5, 29.5);
        bookHisto(dirOfftrackClusterBMT, "off_stripMultiplicity_bmt", 30, -0.5, 29.5);
        bookHisto(dirOfftrackClusterBMT,"off_seedStrip_bmt",256,0.5,256.5);
        bookHisto(dirOfftrackClusterBMT, "off_seedCharge_bmt", 100, -0.5, 299.5);
        bookHisto(dirOfftrackClusterBMT, "off_clusterCharge_bmt", 100, -0.5, 299.5);
        bookHisto(dirOfftrackClusterBMT, "off_centroid_bmt", 256, 0.5, 256.5);
        bookHisto(dirOfftrackClusterBMT, "off_centroidResidual_bmt", 200, -10, 10);
        bookHisto(dirOfftrackClusterBMT, "off_seedResidual_bmt", 200, -10, 10);
        bookHisto(dirOfftrackHitSVT, "off_hitMultiplicity_svt", 30, -0.5, 29.5);
        bookHisto(dirOfftrackHitSVT, "off_hitStrip_svt", 256, 0.5, 256.5);
        bookHisto(dirOfftrackHitSVT, "off_fitResidual_svt", 200, -10, 10);
        bookHisto(dirOfftrackHitSVT, "off_hitAdc_svt", 8, -0.5, 7.5);
        bookHisto(dirOfftrackHitSVT, "off_hitBco_svt", 256, 0.5, 256.5);
        bookHisto(dirOfftrackHitBMT, "off_hitMultiplicity_bmt", 30, -0.5, 29.5);
        bookHisto(dirOfftrackHitBMT, "off_hitStrip_bmt", 256, 0.5, 256.5);
        bookHisto(dirOfftrackHitBMT, "off_fitResidual_bmt", 200, -10, 10);
        bookHisto(dirOfftrackHitBMT, "off_hitAdc_bmt", 8, -0.5, 7.5);
        bookHisto(dirOfftrackHitBMT, "off_hitBco_bmt", 256, 0.5, 256.5);
        bookHistoSensor(dirOfftrackSensorSVT,"off_strip_svt",256,0.5,256.5);
        bookHistoSensor(dirOfftrackSensorSVT, "off_adc_svt", 8, -0.5, 7.5);
        bookHistoSensor(dirOfftrackSensorSVT, "off_seedStrip_svt", 256, 0.5, 256.5);
        bookHistoSensor(dirOfftrackSensorSVT, "off_seedCharge_svt", 100, -0.5, 299.5);
        bookHistoSensor(dirOfftrackSensorSVT, "off_clusterCharge_svt", 100, -0.5, 299.5);
        bookHistoSensor(dirOfftrackSensorSVT, "off_stripMultiplicity_svt", 30, -0.5, 29.5);
        bookHisto(dirOfftrackSensorSVT, "off_occupancy_sensor_svt", "Channel", "Sensor", 256, 0, 256, 132, 0, 132);
        bookHisto(dirOfftrackModuleSVT, "off_occupancy_module_svt", "Channel", "Module", 512, 0, 512, 66, 0, 66);
        bookHistoRegion(dirOfftrackRegionSVT, "off_clusterCharge_svt", 100, -0.5, 299.5);
        bookHistoSensor(dirOfftrackSensorBMT,"off_strip_bmt",256,0.5,256.5);
        bookHistoSensor(dirOfftrackSensorBMT, "off_adc_bmt", 8, -0.5, 7.5);
        bookHistoSensor(dirOfftrackSensorBMT, "off_seedStrip_bmt", 256, 0.5, 256.5);
        bookHistoSensor(dirOfftrackSensorBMT, "off_seedCharge_bmt", 100, -0.5, 299.5);
        bookHistoSensor(dirOfftrackSensorBMT, "off_clusterCharge_bmt", 100, -0.5, 299.5);
        bookHistoSensor(dirOfftrackSensorBMT, "off_stripMultiplicity_bmt", 30, -0.5, 29.5);
        bookHisto(dirOfftrackSensorBMT, "off_occupancy_sensor_bmt", "Channel", "Sensor", 256, 0, 256, 132, 0, 132);
        bookHisto(dirOfftrackModuleBMT, "off_occupancy_module_bmt", "Channel", "Module", 512, 0, 512, 66, 0, 66);
        bookHistoRegion(dirOfftrackRegionBMT, "off_clusterCharge_bmt", 100, -0.5, 299.5);
        bookHisto(dirEvent, "event_occupancy_sensor_svt", "Channel", "Sensor", 256, 0, 256, 132, 0, 132);
        bookHisto(dirEvent, "event_occupancy_sensor_bmt", "Channel", "Sensor", 256, 0, 256, 132, 0, 132);
        bookHisto(dirEventSVTCross, "event_crossMultiplicity_svt", 20, -0.5, 19.5);
        bookHisto(dirEventSVTCross, "event_crossX_svt", 100, -200, 200);
        bookHisto(dirEventSVTCross, "event_crossY_svt", 100, -200, 200);
        bookHisto(dirEventSVTCross, "event_crossZ_svt", 100, -250, 250);
        bookHisto(dirEventSVTCross, "event_crossErrX_svt", 100, -0.01, 0.15);
        bookHisto(dirEventSVTCross, "event_crossErrY_svt", 100, -0.01, 0.15);
        bookHisto(dirEventSVTCross, "event_crossErrZ_svt", 100, -0.5, 19.5);
        bookHisto(dirEventBMTCross, "event_crossMultiplicity_bmt", 20, -0.5, 19.5);
        bookHisto(dirEventBMTCross, "event_crossX_bmt", 100, -200, 200);
        bookHisto(dirEventBMTCross, "event_crossY_bmt", 100, -200, 200);
        bookHisto(dirEventBMTCross, "event_crossZ_bmt", 100, -250, 250);
        bookHisto(dirEventBMTCross, "event_crossErrX_bmt", 100, -0.01, 0.15);
        bookHisto(dirEventBMTCross, "event_crossErrY_bmt", 100, -0.01, 0.15);
        bookHisto(dirEventBMTCross, "event_crossErrZ_bmt", 100, -0.5, 19.5);
        bookHisto(dirEventSVTCluster, "event_eta_svt", 100, 0, 1);
        bookHisto(dirEventSVTCluster, "event_etaAdc_svt", 100, 0, 1);
        bookHisto(dirEventSVTCluster, "event_clusterMultiplicity_svt", 30, -0.5, 29.5);
        bookHisto(dirEventSVTCluster, "event_stripMultiplicity_svt", 30, -0.5, 29.5);
        bookHisto(dirEventSVTCluster, "event_seedStrip_svt", 256, 0.5, 256.5);
        bookHisto(dirEventSVTCluster, "event_seedCharge_svt", 100, -0.5, 299.5);
        bookHisto(dirEventSVTCluster, "event_clusterCharge_svt", 100, -0.5, 299.5);
        bookHisto(dirEventSVTCluster, "event_centroid_svt", 256, 0.5, 256.5);
        bookHisto(dirEventSVTCluster, "event_centroidResidual_svt", 200, -10, 10);
        bookHisto(dirEventSVTCluster, "event_seedResidual_svt", 200, -10, 10);
        bookHisto(dirEventBMTCluster, "event_clusterMultiplicity_bmt", 30, -0.5, 29.5);
        bookHisto(dirEventBMTCluster, "event_stripMultiplicity_bmt", 30, -0.5, 29.5);
        bookHisto(dirEventBMTCluster, "event_seedStrip_bmt", 256, 0.5, 256.5);
        bookHisto(dirEventBMTCluster, "event_seedCharge_bmt", 100, -0.5, 299.5);
        bookHisto(dirEventBMTCluster, "event_clusterCharge_bmt", 100, -0.5, 299.5);
        bookHisto(dirEventBMTCluster, "event_centroid_bmt", 256, 0.5, 256.5);
        bookHisto(dirEventBMTCluster, "event_centroidResidual_bmt", 200, -10, 10);
        bookHisto(dirEventBMTCluster, "event_seedResidual_bmt", 200, -10, 10);
        bookHisto(dirEventSVTHit, "event_hitMultiplicity_svt", 100, -0.5, 99.5);
        bookHisto(dirEventSVTHit, "event_hitStrip_svt", 256, 0.5, 256.5);
        bookHisto(dirEventSVTHit, "event_fitResidual_svt", 200, -2, 2);
        bookHisto(dirEventSVTHit, "event_hitAdc_svt", 8, -0.5, 7.5);
        bookHisto(dirEventSVTHit, "event_hitBco_svt", 256, 0.5, 256.5);
        bookHisto(dirEventSVTHit, "event_dgtzStrip_svt", 256, 0.5, 256.5);
        bookHisto(dirEventSVTHit, "event_dgtzPerLayer_svt", 8, 0.5, 8.5);
        bookHisto(dirEventSVTHit, "event_trackHitsPerLayer_svt", 8, 0.5, 8.5);
        bookHisto(dirEventBMTHit, "event_hitMultiplicity_bmt", 100, -0.5, 99.5);
        bookHisto(dirEventBMTHit, "event_fitResidual_bmt", 200, -2, 2);
        bookHisto(dirEventBMTHit, "event_hitStrip_bmt", 256, 0.5, 256.5);
        bookHisto(dirEventBMTHit, "event_hitAdc_bmt", 8, -0.5, 7.5);
        bookHisto(dirEventBMTHit, "event_hitBco_bmt", 256, 0.5, 256.5);
        bookHisto(dirEventBMTHit, "event_dgtzStrip_bmt", 256, 0.5, 256.5);
        bookHistoLorentz("sizeAngle", 20, -0.5, 19.5);
    }

    public Map getHisto(String key) {
        return this.histoMap;
    }

    public void bookHistos(TDirectory dir, String name, int nBins, double xMin, double xMax, int nHistos) {
        for (int i = 0; i < nHistos; i++) {
            bookHisto(dir, name + (i + 1), nBins, xMin, xMax);
        }

        Histo histo = new Histo(dir, name);
        histo.book(name, nBins, xMin, xMax);
        histoMap.put(name, histo);
    }

    public void bookHisto(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        Histo histo = new Histo(dir, name);
        histo.book(name, nBins, xMin, xMax);
        histoMap.put(name, histo);
    }

    public void bookHisto(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        Histo histo = new Histo(dir, name);
        histo.book(name, xTitle, yTitle, nBinsX, xMin, xMax, nBinsY, yMin, yMax);
        histoMap.put(name, histo);
    }

    public void bookHistoLorentz(String name, int nBins, double xMin, double xMax) {
        HistoLorentz histoLorentz = new HistoLorentz(dirLorentz, name);
        histoLorentz.book(name, nBins, xMin, xMax);
        histoLorentzMap.put(name, histoLorentz);
    }

    public void bookHistoSensor(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        HistoSensor histoSensor = new HistoSensor(rootDir, dir, name);
        histoSensor.book(name, nBins, xMin, xMax);
        histoSensorMap.put(name, histoSensor);
    }

    public void bookHistoSensor(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        HistoSensor histoSensor = new HistoSensor(rootDir, dir, name);
        histoSensor.book(name, xTitle, yTitle, nBinsX, xMin, xMax, nBinsY, yMin, yMax);
        histoSensorMap.put(name, histoSensor);
    }

    public void bookHistoModule(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        HistoModule histoModule = new HistoModule(rootDir, dir, name);
        histoModule.book(name, nBins, xMin, xMax);
        histoModuleMap.put(name, histoModule);
    }

    public void bookHistoModule(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        HistoModule histoModule = new HistoModule(rootDir, dir, name);
        histoModule.book(name, xTitle, yTitle, nBinsX, xMin, xMax, nBinsY, yMin, yMax);
        histoModuleMap.put(name, histoModule);
    }

    public void bookHistoRegion(TDirectory dir, String name, int nBins, double xMin, double xMax) {
        HistoRegion histoRegion = new HistoRegion(rootDir, dir, name);
        histoRegion.book(name, nBins, xMin, xMax);
        histoRegionMap.put(name, histoRegion);
    }

    public void bookHistoRegion(TDirectory dir, String name, String xTitle, String yTitle, int nBinsX, double xMin, double xMax, int nBinsY, double yMin, double yMax) {
        HistoRegion histoRegion = new HistoRegion(rootDir, dir, name);
        histoRegion.book(name, xTitle, yTitle, nBinsX, xMin, xMax, nBinsY, yMin, yMax);
        histoRegionMap.put(name, histoRegion);
    }
}

