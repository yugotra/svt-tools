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
    static int nMcHits;
    static int nGenParts;
    boolean cosmics = false;
    static ArrayList<SVTCosmicTrack> svtCosmicTracks;
    static ArrayList<SVTTrack> svtTracks;
    static ArrayList<SVTTrajectory> svtTrajectories;
    static ArrayList<SVTCross> svtTrackCrosses;
    static ArrayList<SVTCluster> svtTrackClusters;
    static ArrayList<SVTHit> svtTrackHits;
    static ArrayList<SVTCross> svtOfftrackCrosses;
    static ArrayList<SVTCluster> svtOfftrackClusters;
    static ArrayList<SVTHit> svtOfftrackHits;
    static ArrayList<SVTDgtz> svtDgtzs;
    static ArrayList<SVTMcHit> svtMcHits;
    static ArrayList<GenPart> genParts;
    private Object[][] data;

    public int[] nDgtzPerLayer = {0, 0, 0, 0, 0, 0, 0, 0};
    public int[] nTrackHitsPerLayer = {0, 0, 0, 0, 0, 0, 0, 0};

    public SVTEvent() {
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
        svtMcHits = new ArrayList<>();
        svtMcHits.clear();
        genParts = new ArrayList<>();
        genParts.clear();
        nTracks = 0;
        nTrajectories = 0;
        nTrackCrosses = 0;
        nTrackClusters = 0;
        nTrackHits = 0;
        nOfftrackCrosses = 0;
        nOfftrackClusters = 0;
        nOfftrackHits = 0;
        nCrosses = 0;
        nClusters = 0;
        nHits = 0;
        nDgtzs = 0;
        nMcHits = 0;
        nGenParts = 0;
    }

    ArrayList<SVTCosmicTrack> getSvtCosmicTracks() {
        return svtCosmicTracks;
    }

    public void SetEventNumber(long eventNr) {
        SVTEvent.eventNr = eventNr;
    }

    public void FillEventHistos(EvioDataEvent event, SVTHistos svthistos) {

        for (int i = 0; i < 2; i++) {
            String detType;
            String suffix;
            String bankName;
            if (i == 0) {
                detType = "BST";
                suffix = "_svt";
            } else {
                detType = "BMT";
                suffix = "_bmt";
            }
            bankName = detType + "Rec::Crosses";
            if (event.hasBank(bankName)) {
                EvioDataBank bankSVTCross = (EvioDataBank) event.getBank(bankName);
                int nRows = bankSVTCross.rows();
                svthistos.histoMap.get("event_crossMultiplicity" + suffix).h.fill(nRows);
                for (int row = 0; row < nRows; ++row) {
                    double crossX = bankSVTCross.getDouble("x", row);
                    double errX = bankSVTCross.getDouble("errX", row);
                    double crossY = bankSVTCross.getDouble("y", row);
                    double errY = bankSVTCross.getDouble("errY", row);
                    double crossZ = bankSVTCross.getDouble("z", row);
                    double errZ = bankSVTCross.getDouble("errZ", row);
                    svthistos.histoMap.get("event_crossX" + suffix).h.fill(crossX);
                    svthistos.histoMap.get("event_crossY" + suffix).h.fill(crossY);
                    svthistos.histoMap.get("event_crossZ" + suffix).h.fill(crossZ);
                    svthistos.histoMap.get("event_crossErrX" + suffix).h.fill(errX);
                    svthistos.histoMap.get("event_crossErrY" + suffix).h.fill(errY);
                    svthistos.histoMap.get("event_crossErrZ" + suffix).h.fill(errZ);
                }
            }
            bankName = detType + "Rec::Clusters";
            if (event.hasBank(bankName)) {
                EvioDataBank bankSVTCluster = (EvioDataBank) event.getBank(bankName);
                int nRows = bankSVTCluster.rows();
                svthistos.histoMap.get("event_clusterMultiplicity" + suffix).h.fill(nRows);
                for (int row = 0; row < nRows; ++row) {
                    int clusterSize = bankSVTCluster.getInt("size", row);
                    double clusterCharge = bankSVTCluster.getDouble("ETot", row);
                    double seedCharge = bankSVTCluster.getDouble("seedE", row);
                    int seed = bankSVTCluster.getInt("seedStrip", row);
                    int clusterSector = bankSVTCluster.getInt("sector", row);
                    int clusterLayer = bankSVTCluster.getInt("layer", row);
                    double centroid = bankSVTCluster.getDouble("centroid", row);
                    svthistos.histoMap.get("event_centroid" + suffix).h.fill(centroid);
                    double centroidResidual = bankSVTCluster.getDouble("centroidResidual", row);
                    svthistos.histoMap.get("event_centroidResidual" + suffix).h.fill(centroidResidual);
                    double seedResidual = bankSVTCluster.getDouble("seedResidual", row);
                    svthistos.histoMap.get("event_seedResidual" + suffix).h.fill(seedResidual);
                    svthistos.histoMap.get("event_clusterCharge" + suffix).h.fill(clusterCharge);
                    svthistos.histoMap.get("event_seedCharge" + suffix).h.fill(seedCharge);
                    svthistos.histoMap.get("event_seedStrip" + suffix).h.fill(seed);
                    svthistos.histoMap.get("event_stripMultiplicity" + suffix).h.fill(clusterSize);
                    if (detType == "BMT") continue;
                    if (clusterSize == 2) {
                        double qLeft;
                        int stripLeft, stripRight;
                        if (centroid > seed) {
                            qLeft = seedCharge;
                            stripLeft = seed;
                            stripRight = seed + 1;
                        } else {
                            qLeft = clusterCharge - seedCharge;
                            stripLeft = seed - 1;
                            stripRight = seed;
                        }
                        double eta = qLeft / clusterCharge;
                        double etaADC;
                        svthistos.histoMap.get("event_eta" + suffix).h.fill(eta);
                        double adcLeft = 0, adcRight = 0;
                        if (event.hasBank("BST::dgtz")) {
                            EvioDataBank bankSVTDgtz = (EvioDataBank) event.getBank("BST::dgtz");
                            int nHitRows = bankSVTDgtz.rows();
                            for (int hitRow = 0; hitRow < nHitRows; ++hitRow) {
                                int hitSector = bankSVTDgtz.getInt("sector", hitRow);
                                int hitLayer = bankSVTDgtz.getInt("layer", hitRow);
                                int hitStrip = bankSVTDgtz.getInt("strip", hitRow);
                                if (hitSector == clusterSector && hitLayer == clusterLayer) {
                                    if (hitStrip == stripLeft) adcLeft = bankSVTDgtz.getInt("ADC", hitRow) + 1.0;
                                    if (hitStrip == stripRight) adcRight = bankSVTDgtz.getInt("ADC", hitRow) + 1.0;
                                }
                            }
                            etaADC = adcLeft / (adcLeft + adcRight);
                            svthistos.histoMap.get("event_etaAdc" + suffix).h.fill(etaADC);
                        }
                    }
                }
            }
            bankName = detType + "Rec::Hits";
            if (event.hasBank(bankName)) {
                EvioDataBank bankSVTHit = (EvioDataBank) event.getBank(bankName);
                int nRows = bankSVTHit.rows();
                svthistos.histoMap.get("event_hitMultiplicity" + suffix).h.fill(nRows);
                for (int row = 0; row < nRows; ++row) {
                    int sector = bankSVTHit.getInt("sector", row);
                    int layer = bankSVTHit.getInt("layer", row);
                    int strip = bankSVTHit.getInt("strip", row);
                    double hitFitResidual = bankSVTHit.getDouble("fitResidual", row);


                    svthistos.histoMap.get("event_fitResidual" + suffix).h.fill(hitFitResidual);
                    svthistos.histoMap.get("event_hitStrip" + suffix).h.fill(strip);
                    svthistos.histoMap.get("event_occupancy_sensor" + suffix).h2.fill(strip, Sensor(layer, sector));
                }
            }
            bankName = detType + "::dgtz";
            if (event.hasBank(bankName)) {
                EvioDataBank bankSVTDgtz = (EvioDataBank) event.getBank(bankName);
                int nRows = bankSVTDgtz.rows();
                for (int row = 0; row < nRows; ++row) {
                    int strip = bankSVTDgtz.getInt("strip", row);
                    int layer = bankSVTDgtz.getInt("layer", row);
                    if (detType.equals("BST") && Constants.mcRun) nDgtzPerLayer[layer - 1]++;
                    int adc = bankSVTDgtz.getInt("ADC", row);
//                    int bco = bankSVTDgtz.getInt("bco", row);
                    svthistos.histoMap.get("event_dgtzStrip" + suffix).h.fill(strip);
                    svthistos.histoMap.get("event_hitAdc" + suffix).h.fill(adc);
//                    svthistos.histoMap.get("event_hitBco" + suffix).h.fill(bco);
                }
            }
            if (detType.equals("BMT") || nTracks > 1) continue;
            for (int j = 0; j < Constants.NLAYERS; j++) {
                svthistos.histoMap.get("event_dgtzPerLayer" + suffix).h.incrementBinContent(j, nDgtzPerLayer[j]);
                svthistos.histoMap.get("event_trackHitsPerLayer" + suffix).h.incrementBinContent(j, nTrackHitsPerLayer[j]);
            }

        }
    }

    public void FillGenPartHistos(SVTHistos svthistos) {
        svthistos.histoMap.get("gen_nGenParts").h.fill(nGenParts);
        for (int i = 0; i < genParts.size(); i++) {
            svthistos.histoMap.get("gen_pId").h.fill(genParts.get(i).pId);
            svthistos.histoMap.get("gen_pX").h.fill(genParts.get(i).pX);
            svthistos.histoMap.get("gen_pY").h.fill(genParts.get(i).pY);
            svthistos.histoMap.get("gen_pZ").h.fill(genParts.get(i).pZ);
            svthistos.histoMap.get("gen_pT").h.fill(genParts.get(i).pT);
            svthistos.histoMap.get("gen_p").h.fill(genParts.get(i).p);
            svthistos.histoMap.get("gen_phi").h.fill(genParts.get(i).phi);
            svthistos.histoMap.get("gen_theta").h.fill(genParts.get(i).theta);
            svthistos.histoMap.get("gen_vX").h.fill(genParts.get(i).vX);
            svthistos.histoMap.get("gen_vY").h.fill(genParts.get(i).vY);
            svthistos.histoMap.get("gen_vZ").h.fill(genParts.get(i).vZ);
        }
    }

    public void FillMcHitHistos(SVTHistos svthistos) {
        svthistos.histoMap.get("mchit_hitN").h.fill(nMcHits);
        for (int i = 0; i < svtMcHits.size(); i++) {
            svthistos.histoMap.get("mchit_pId").h.fill(svtMcHits.get(i).pId);
            svthistos.histoMap.get("mchit_mPId").h.fill(svtMcHits.get(i).pId);
            svthistos.histoMap.get("mchit_tId").h.fill(svtMcHits.get(i).pId);
            svthistos.histoMap.get("mchit_mTId").h.fill(svtMcHits.get(i).pId);
            svthistos.histoMap.get("mchit_oTId").h.fill(svtMcHits.get(i).pId);
            if (svtMcHits.get(i).pId == -211) {
                svthistos.histoMap.get("mchit_pX").h.fill(svtMcHits.get(i).pX);
                svthistos.histoMap.get("mchit_pY").h.fill(svtMcHits.get(i).pY);
                svthistos.histoMap.get("mchit_pZ").h.fill(svtMcHits.get(i).pZ);
                svthistos.histoMap.get("mchit_pT").h.fill(svtMcHits.get(i).pT);
                svthistos.histoMap.get("mchit_p").h.fill(svtMcHits.get(i).p);
                svthistos.histoMap.get("mchit_vX").h.fill(svtMcHits.get(i).vX);
                svthistos.histoMap.get("mchit_vY").h.fill(svtMcHits.get(i).vY);
                svthistos.histoMap.get("mchit_vZ").h.fill(svtMcHits.get(i).vZ);
                svthistos.histoMap.get("mchit_mVX").h.fill(svtMcHits.get(i).mVX);
                svthistos.histoMap.get("mchit_mVY").h.fill(svtMcHits.get(i).mVY);
                svthistos.histoMap.get("mchit_mVZ").h.fill(svtMcHits.get(i).mVZ);
                svthistos.histoMap.get("mchit_avgT").h.fill(svtMcHits.get(i).avgT);
                svthistos.histoMap.get("mchit_trackE").h.fill(svtMcHits.get(i).trackE);
                svthistos.histoMap.get("mchit_totEDep").h.fill(svtMcHits.get(i).totEDep);
                svthistos.histoMap.get("mchit_avgX").h.fill(svtMcHits.get(i).avgX);
                svthistos.histoMap.get("mchit_avgY").h.fill(svtMcHits.get(i).avgY);
                svthistos.histoMap.get("mchit_avgZ").h.fill(svtMcHits.get(i).avgZ);
                svthistos.histoMap.get("mchit_avgLX").h.fill(svtMcHits.get(i).avgLX);
                svthistos.histoMap.get("mchit_avgLY").h.fill(svtMcHits.get(i).avgLY);
                svthistos.histoMap.get("mchit_avgLZ").h.fill(svtMcHits.get(i).avgLZ);
            }
        }
    }

    public void FillOffTrackHistos(SVTHistos svthistos) {

        String suffix = "_svt";
        svthistos.histoMap.get("off_crossMultiplicity" + suffix).h.fill(nOfftrackCrosses);
        svthistos.histoMap.get("off_clusterMultiplicity" + suffix).h.fill(nOfftrackClusters);
        svthistos.histoMap.get("off_hitMultiplicity" + suffix).h.fill(nOfftrackHits);
        for (int i = 0; i < svtOfftrackCrosses.size(); ++i) {
            double cross_x = svtOfftrackCrosses.get(i).x;
            double cross_y = svtOfftrackCrosses.get(i).y;
            double cross_z = svtOfftrackCrosses.get(i).z;
            double err_x = svtOfftrackCrosses.get(i).errX;
            double err_y = svtOfftrackCrosses.get(i).errY;
            double err_z = svtOfftrackCrosses.get(i).errZ;
            svthistos.histoMap.get("off_crossX" + suffix).h.fill(cross_x);
            svthistos.histoMap.get("off_crossY" + suffix).h.fill(cross_y);
            svthistos.histoMap.get("off_crossZ" + suffix).h.fill(cross_z);
            svthistos.histoMap.get("off_crossErrX" + suffix).h.fill(err_x);
            svthistos.histoMap.get("off_crossErrY" + suffix).h.fill(err_y);
            svthistos.histoMap.get("off_crossErrZ" + suffix).h.fill(err_z);
        }
        for (int i = 0; i < svtOfftrackClusters.size(); ++i) {
            int layer = svtOfftrackClusters.get(i).layer;
            int sector = svtOfftrackClusters.get(i).sector;
            int seed = svtOfftrackClusters.get(i).seedStrip;
            double centroid = svtOfftrackClusters.get(i).centroid;
            double centroidResidual = svtOfftrackClusters.get(i).centroidResidual;
            double seedResidual = svtOfftrackClusters.get(i).seedResidual;
            double seedCharge = svtOfftrackClusters.get(i).seedE;
            double clusterCharge = svtOfftrackClusters.get(i).eTot;
            double clusterSize = svtOfftrackClusters.get(i).size;
            svthistos.histoMap.get("off_clusterCharge" + suffix).h.fill(clusterCharge);
            svthistos.histoMap.get("off_seedStrip" + suffix).h.fill(seed);
            svthistos.histoMap.get("off_seedCharge" + suffix).h.fill(seedCharge);
            svthistos.histoMap.get("off_centroid" + suffix).h.fill(centroid);
            svthistos.histoMap.get("off_centroidResidual" + suffix).h.fill(centroidResidual);
            svthistos.histoMap.get("off_seedResidual" + suffix).h.fill(seedResidual);
            svthistos.histoSensorMap.get("off_seedCharge" + suffix).h[layer - 1][sector - 1].fill(seedCharge);
            svthistos.histoSensorMap.get("off_clusterCharge" + suffix).h[layer - 1][sector - 1].fill(clusterCharge);
            svthistos.histoSensorMap.get("off_seedStrip" + suffix).h[layer - 1][sector - 1].fill(seed);

//            if (nTracks == 1) {
//                 System.out.println(eventNr+" "+layer+" "+sector);
//                int trjId = Trajectory(0).GetIdByModule(0, layer, sector);
//                if (trjId != -1) {
//                    double local_angle_3d = Trajectory(0).angle3D.get(trjId);
            // System.out.println(trjId+" "+local_angle_3d);
//                    if(Math.abs(local_angle_3d-90)>45)
//                    if(seed==190&&sector==10&&layer==1) System.out.println(eventNr);
//                }
//            }

            svthistos.histoSensorMap.get("off_stripMultiplicity" + suffix).h[layer - 1][sector - 1].fill(clusterSize);
            svthistos.histoMap.get("off_stripMultiplicity" + suffix).h.fill(clusterSize);
            int region = (layer % 2 == 0 ? layer / 2 : (layer + 1) / 2);
            svthistos.histoRegionMap.get("off_clusterCharge" + suffix).h[region - 1].fill(clusterCharge);
            if (clusterSize == 2) {
                double qLeft;
                if (centroid > seed) qLeft = seedCharge;
                else qLeft = clusterCharge - seedCharge;
                double eta = qLeft / clusterCharge;
                svthistos.histoMap.get("off_eta" + suffix).h.fill(eta);
            }

        }
        for (int i = 0; i < svtOfftrackHits.size(); ++i) {
            int layer = svtOfftrackHits.get(i).layer;
            int sector = svtOfftrackHits.get(i).sector;
            int strip = svtOfftrackHits.get(i).strip;
            double fitResidual = svtOfftrackHits.get(i).fitResidual;
            int channel = (layer % 2 == 0 ? strip + 255 : strip - 1);
            int adc = svtOfftrackHits.get(i).adc;
            int bco = svtOfftrackHits.get(i).bco;
            svthistos.histoMap.get("off_hitStrip" + suffix).h.fill(strip);
            svthistos.histoMap.get("off_fitResidual" + suffix).h.fill(fitResidual);
            svthistos.histoMap.get("off_hitAdc" + suffix).h.fill(adc);
            svthistos.histoMap.get("off_hitBco" + suffix).h.fill(bco);
            svthistos.histoMap.get("off_occupancy_module" + suffix).h2.fill(channel, Module(layer, sector));
            svthistos.histoMap.get("off_occupancy_sensor" + suffix).h2.fill(strip, Sensor(layer, sector));
            svthistos.histoSensorMap.get("off_adc" + suffix).h[layer - 1][sector - 1].fill(adc);
            svthistos.histoSensorMap.get("off_strip" + suffix).h[layer - 1][sector - 1].fill(strip);
        }
    }

    public void FillTrackHistos(SVTHistos svthistos) {

        String suffix = "_svt";
        svthistos.histoMap.get("trackMultiplicity").h.fill(nTracks);
        svthistos.histoMap.get("crossMultiplicity" + suffix).h.fill(nTrackCrosses);
        svthistos.histoMap.get("clusterMultiplicity" + suffix).h.fill(nTrackClusters);
        svthistos.histoMap.get("hitMultiplicity" + suffix).h.fill(nTrackHits);
        for (int i = 0; i < nTracks; ++i) {
            if (TrackClusters(i, 0).get(0).trjId == -1) return; // catch for bad Trajectory bank
            if (this.cosmics) {
                double phi = SvtCosmicTracks().get(i).phi;
                double theta = SvtCosmicTracks().get(i).theta;
                double kfChi2 = SvtCosmicTracks().get(i).kfChi2;
                int kfNdf = SvtCosmicTracks().get(i).kfNdf;
                if (kfNdf != 0) svthistos.histoMap.get("normChi2").h.fill(kfChi2 / (double) kfNdf);
                svthistos.histoMap.get("phi").h.fill(phi);
                svthistos.histoMap.get("theta").h.fill(theta);
                svthistos.histoMap.get("thetaPhi").h2.fill(phi, theta);
            } else {
                int fittingMethod = SvtTracks().get(i).fittingMethod;
                double cX = SvtTracks().get(i).cX;
                double cY = SvtTracks().get(i).cY;
                double cZ = SvtTracks().get(i).cZ;
                double cUX = SvtTracks().get(i).cUX;
                double cUY = SvtTracks().get(i).cUY;
                double cUZ = SvtTracks().get(i).cUZ;
                double pathLength = SvtTracks().get(i).pathLength;
                int q = SvtTracks().get(i).q;
                double p = SvtTracks().get(i).p;
                double pT = SvtTracks().get(i).pT;
                double phi0 = SvtTracks().get(i).phi0;
                double tanDip = SvtTracks().get(i).tanDip;
                double z0 = SvtTracks().get(i).z0;
                double d0 = SvtTracks().get(i).d0;
                double covD02 = SvtTracks().get(i).covD02;
                double covD0Phi0 = SvtTracks().get(i).covD0Phi0;
                double covD0Rho = SvtTracks().get(i).covD0Rho;
                double covPhi02 = SvtTracks().get(i).covPhi02;
                double covPhi0Rho = SvtTracks().get(i).covPhi0Rho;
                double covRho2 = SvtTracks().get(i).covRho2;
                double covZ02 = SvtTracks().get(i).covZ02;
                double covTanDip2 = SvtTracks().get(i).covTanDip2;
                double cirFitChi2Ndf = SvtTracks().get(i).cirFitChi2Ndf;
                double linFitChi2Ndf = SvtTracks().get(i).linFitChi2Ndf;
                svthistos.histoMap.get("fittingMethod").h.fill(fittingMethod);
                svthistos.histoMap.get("cX").h.fill(cX);
                svthistos.histoMap.get("cY").h.fill(cY);
                svthistos.histoMap.get("cZ").h.fill(cZ);
                svthistos.histoMap.get("cUX").h.fill(cUX);
                svthistos.histoMap.get("cUY").h.fill(cUY);
                svthistos.histoMap.get("cUZ").h.fill(cUZ);
                svthistos.histoMap.get("pathLength").h.fill(pathLength);
                svthistos.histoMap.get("q").h.fill(q);
                svthistos.histoMap.get("p").h.fill(p);
                svthistos.histoMap.get("pT").h.fill(pT);
                if (i == 0) { //count only one track per event
                    svthistos.histoMap.get("pEff").h.fill(genParts.get(0).p);
                    svthistos.histoMap.get("pTEff").h.fill(genParts.get(0).pT);
                }
                double pTRes = 100 * (pT - genParts.get(0).pT) / genParts.get(0).pT;
                svthistos.histoMap.get("pTRes").h.fill(pTRes);
                int nPtBins = 9;
                double pTMin = 0.4;
                double pTBinSize = 0.2;

                for (int j = 0; j < nPtBins; j++) {
                    if (genParts.get(0).pT >= (pTMin + j*pTBinSize) && genParts.get(0).pT < (pTMin + (j+1)*pTBinSize)) {
                        svthistos.histoMap.get("deltaPt"+(j+1)).h.fill(pT - genParts.get(0).pT);
                        break;
                    }
                }
                int nPBins = 9;
                double pMin = 0.4;
                double pBinSize = 0.2;

                for (int j = 0; j < nPBins; j++) {
                    if (genParts.get(0).p >= (pMin + j*pBinSize) && genParts.get(0).p < (pMin + (j+1)*pBinSize)) {
                        svthistos.histoMap.get("deltaP"+(j+1)).h.fill(p - genParts.get(0).p);
                        break;
                    }
                }
                double pRes = 100 * (p - genParts.get(0).p) / genParts.get(0).p;
                svthistos.histoMap.get("pRes").h.fill(pRes);
                double phiRes = phi0 - genParts.get(0).phi - Math.PI;
// To fix - why PI difference
                svthistos.histoMap.get("phiRes").h.fill(phiRes);
                double theta0 = Math.acos(tanDip * pT);
                double thetaRes = theta0 - genParts.get(0).theta;
                svthistos.histoMap.get("thetaRes").h.fill(thetaRes);
//                double pRes = 100 * Math.abs((p - genParts.get(0).p) / genParts.get(0).p);
//                svthistos.histoMap.get("pRes").h.fill(pRes);
//                double phiRes = 100 * Math.abs((phi0 - genParts.get(0).phi) / genParts.get(0).phi);
//                svthistos.histoMap.get("phiRes").h.fill(phiRes);
//                double thetaRes = 100 * Math.abs((theta0 - genParts.get(0).theta) / genParts.get(0).theta);
//                svthistos.histoMap.get("thetaRes").h.fill(thetaRes);
                svthistos.histoMap.get("pTResPt").h2.fill(pT, pTRes);
                svthistos.histoMap.get("pResP").h2.fill(p, pRes);
                svthistos.histoMap.get("phi0").h.fill(phi0);
                svthistos.histoMap.get("theta0").h.fill(theta0);
                svthistos.histoMap.get("tanDip").h.fill(tanDip);
                svthistos.histoMap.get("z0").h.fill(z0);
                svthistos.histoMap.get("d0").h.fill(d0);
                svthistos.histoMap.get("covD02").h.fill(covD02);
                svthistos.histoMap.get("covD0Phi0").h.fill(covD0Phi0);
                svthistos.histoMap.get("covD0Rho").h.fill(covD0Rho);
                svthistos.histoMap.get("covPhi02").h.fill(covPhi02);
                svthistos.histoMap.get("covPhi0Rho").h.fill(covPhi0Rho);
                svthistos.histoMap.get("covRho2").h.fill(covRho2);
                svthistos.histoMap.get("covZ02").h.fill(covZ02);
                svthistos.histoMap.get("covTanDip2").h.fill(covTanDip2);
                svthistos.histoMap.get("cirFitChi2Ndf").h.fill(cirFitChi2Ndf);
                svthistos.histoMap.get("linFitChi2Ndf").h.fill(linFitChi2Ndf);
            }
            for (int j = 0; j < TrackCrosses(i).size(); ++j) {
                int region = TrackCrosses(i).get(j).region;
                int sector = TrackCrosses(i).get(j).sector;
                double crossX = TrackCrosses(i).get(j).x;
                double crossY = TrackCrosses(i).get(j).y;
                double crossZ = TrackCrosses(i).get(j).z;
                double errX = TrackCrosses(i).get(j).errX;
                double errY = TrackCrosses(i).get(j).errY;
                double errZ = TrackCrosses(i).get(j).errZ;
                svthistos.histoMap.get("crossX_svt").h.fill(crossX);
                svthistos.histoMap.get("crossY_svt").h.fill(crossY);
                svthistos.histoMap.get("crossZ_svt").h.fill(crossZ);
                svthistos.histoMap.get("crossErrX_svt").h.fill(errX);
                svthistos.histoMap.get("crossErrY_svt").h.fill(errY);
                svthistos.histoMap.get("crossErrZ_svt").h.fill(errZ);
                svthistos.histoRegionMap.get("crossX").h[region - 1].fill(crossX);
                svthistos.histoRegionMap.get("crossY").h[region - 1].fill(crossY);
                svthistos.histoRegionMap.get("crossZ").h[region - 1].fill(crossZ);
                svthistos.histoRegionMap.get("crossErrX").h[region - 1].fill(errX);
                svthistos.histoRegionMap.get("crossErrY").h[region - 1].fill(errY);
                svthistos.histoRegionMap.get("crossErrZ").h[region - 1].fill(errZ);
                svthistos.histoModuleMap.get("crossX").h_[region - 1][sector - 1].fill(crossX);
                svthistos.histoModuleMap.get("crossY").h_[region - 1][sector - 1].fill(crossY);
                svthistos.histoModuleMap.get("crossZ").h_[region - 1][sector - 1].fill(crossZ);
                svthistos.histoModuleMap.get("crossErrX").h_[region - 1][sector - 1].fill(errX);
                svthistos.histoModuleMap.get("crossErrY").h_[region - 1][sector - 1].fill(errY);
                svthistos.histoModuleMap.get("crossErrZ").h_[region - 1][sector - 1].fill(errZ);
                for (int k = 0; k < TrackClusters(i, j).size(); ++k) {
                    int trjId;
                    if (TrackClusters(i, j).get(k).trjId != -1) trjId = TrackClusters(i, j).get(k).trjId;
                    else trjId = 0;
// !!!!!!!!!!!!! temporary to kludge the bug with track id in traj bank
//                    System.out.println(i+" "+j+" "+k+" "+Trajectory(i).phi.size()+" "+TrackClusters(i,j).get(k).trjId);
//                    double localPhi=Trajectory(i).phi.get(trjId)*180/Math.PI;
//                    double localTheta=Trajectory(i).theta.get(trjId)*180/Math.PI;
//                    double localAngle3D=Trajectory(i).angle3D.get(trjId)*180/Math.PI;
                    double localPhi = Trajectory(i).phi.get(trjId);
//                    if(localPhi<0&&localPhi>-20) System.out.println("localphi "+eventNr);
                    double localTheta = Trajectory(i).theta.get(trjId);
                    double localAngle3D = Trajectory(i).angle3D.get(trjId);
                    double localX = Trajectory(i).x.get(trjId);
                    double localY = Trajectory(i).y.get(trjId);
                    double localZ = Trajectory(i).z.get(trjId);
                    int layer = TrackClusters(i, j).get(k).layer;
                    double cluster_charge = TrackClusters(i, j).get(k).eTot;
                    int clusterSize = TrackClusters(i, j).get(k).size;
                    double centroid = TrackClusters(i, j).get(k).centroid;
                    double centroidResidual = TrackClusters(i, j).get(k).centroidResidual;
                    double seedResidual = TrackClusters(i, j).get(k).seedResidual;
                    double seed = TrackClusters(i, j).get(k).seedStrip;
                    double centroidResidualSensor = TrackClusters(i, j).get(k).centroidResidual;
                    double seedResidualSensor = TrackClusters(i, j).get(k).seedResidual;
                    double seedCharge = TrackClusters(i, j).get(k).seedE;
                    svthistos.histoMap.get("centroid" + suffix).h.fill(centroid);
                    svthistos.histoMap.get("centroidResidual" + suffix).h.fill(centroidResidual);
                    svthistos.histoMap.get("seedResidual" + suffix).h.fill(seedResidual);
                    svthistos.histoMap.get("clusterCharge" + suffix).h.fill(cluster_charge);
                    svthistos.histoMap.get("seedCharge" + suffix).h.fill(seedCharge);
                    svthistos.histoMap.get("seedStrip" + suffix).h.fill(seed);
                    svthistos.histoMap.get("stripMultiplicity" + suffix).h.fill(clusterSize);
                    if (!Double.isNaN(localPhi)) svthistos.histoMap.get("localPhi").h.fill(localPhi);
                    if (!Double.isNaN(localTheta)) svthistos.histoMap.get("localTheta").h.fill(localTheta);
                    if (!Double.isNaN(localAngle3D)) {
                        svthistos.histoMap.get("localTrackAngle").h.fill(localAngle3D);
                        svthistos.histoMap.get("correctedClusterCharge" + suffix).h.fill(cluster_charge * Math.cos(localAngle3D));
                    }
                    if (!Double.isNaN(localX)) svthistos.histoMap.get("localX").h.fill(localX);
                    if (!Double.isNaN(localY)) svthistos.histoMap.get("localY").h.fill(localY);
                    if (!Double.isNaN(localZ)) svthistos.histoMap.get("localZ").h.fill(localZ);
                    svthistos.histoSensorMap.get("centroidResidual").h[layer - 1][sector - 1].fill(centroidResidualSensor);
                    svthistos.histoSensorMap.get("seedResidual").h[layer - 1][sector - 1].fill(seedResidualSensor);
                    svthistos.histoSensorMap.get("clusterCharge").h[layer - 1][sector - 1].fill(cluster_charge);
                    svthistos.histoSensorMap.get("correctedClusterCharge").h[layer - 1][sector - 1].fill(cluster_charge * Math.cos(localAngle3D));
                    svthistos.histoSensorMap.get("seedCharge").h[layer - 1][sector - 1].fill(seedCharge);
                    svthistos.histoSensorMap.get("stripMultiplicity").h[layer - 1][sector - 1].fill(clusterSize);
                    svthistos.histoSensorMap.get("localPhi").h[layer - 1][sector - 1].fill(localPhi);
                    svthistos.histoSensorMap.get("localTheta").h[layer - 1][sector - 1].fill(localTheta);
                    svthistos.histoSensorMap.get("localTrackAngle").h[layer - 1][sector - 1].fill(localAngle3D);
                    svthistos.histoModuleMap.get("clusterCharge").h_[region - 1][sector - 1].fill(cluster_charge);
                    svthistos.histoRegionMap.get("centroidResidual").h[region - 1].fill(centroidResidualSensor);
                    svthistos.histoRegionMap.get("seedResidual").h[region - 1].fill(seedResidualSensor);
                    svthistos.histoRegionMap.get("clusterCharge").h[region - 1].fill(cluster_charge);
                    svthistos.histoRegionMap.get("correctedClusterCharge").h[region - 1].fill(cluster_charge * Math.cos(localAngle3D));
                    svthistos.histoRegionMap.get("seedCharge").h[region - 1].fill(seedCharge);
                    svthistos.histoRegionMap.get("stripMultiplicity").h[region - 1].fill(clusterSize);
                    svthistos.histoRegionMap.get("localPhi").h[region - 1].fill(localPhi);
                    svthistos.histoRegionMap.get("localTheta").h[region - 1].fill(localTheta);
                    svthistos.histoRegionMap.get("localTrackAngle").h[region - 1].fill(localAngle3D);
                    svthistos.histoSensorMap.get("centroidResidual_angle").h2[layer - 1][sector - 1].fill(localAngle3D, centroidResidualSensor);
                    svthistos.histoSensorMap.get("centroidResidual_phi").h2[layer - 1][sector - 1].fill(localPhi, centroidResidualSensor);
                    svthistos.histoSensorMap.get("centroidResidual_theta").h2[layer - 1][sector - 1].fill(localTheta, centroidResidualSensor);
                    svthistos.histoSensorMap.get("centroidResidual_x").h2[layer - 1][sector - 1].fill(localX, centroidResidualSensor);
                    svthistos.histoSensorMap.get("centroidResidual_y").h2[layer - 1][sector - 1].fill(localY, centroidResidualSensor);
                    svthistos.histoSensorMap.get("centroidResidual_z").h2[layer - 1][sector - 1].fill(localZ, centroidResidualSensor);
                    if (clusterSize == 2) {
                        double qLeft;
                        if (centroid > seed) qLeft = seedCharge;
                        else qLeft = cluster_charge - seedCharge;
                        double eta = qLeft / cluster_charge;
                        svthistos.histoMap.get("eta" + suffix).h.fill(eta);
                    }
                    for (int l = 0; l < TrackHits(i, j, k).size(); ++l) {
                        if (nTracks == 1) nTrackHitsPerLayer[layer - 1]++;
                        int strip = TrackHits(i, j, k).get(l).strip;
                        int adc = TrackHits(i, j, k).get(l).adc;
                        int bco = TrackHits(i, j, k).get(l).bco;
                        int channel = (layer % 2 == 0 ? strip + 255 : strip - 1);
                        svthistos.histoMap.get("hitStrip" + suffix).h.fill(strip);
                        svthistos.histoMap.get("hitAdc" + suffix).h.fill(adc);
                        svthistos.histoMap.get("hitBco" + suffix).h.fill(bco);
                        svthistos.histoMap.get("occupancy_module").h2.fill(channel, Module(layer, sector));
                        svthistos.histoMap.get("occupancy_sensor").h2.fill(strip, Sensor(layer, sector));
                        svthistos.histoSensorMap.get("strip").h[layer - 1][sector - 1].fill(strip);
                        svthistos.histoSensorMap.get("adc").h[layer - 1][sector - 1].fill(adc);
                        svthistos.histoRegionMap.get("strip").h[region - 1].fill(strip);
                        svthistos.histoRegionMap.get("adc").h[region - 1].fill(adc);
                        double fitResidual = TrackHits(i, j, k).get(l).fitResidual;
                        svthistos.histoSensorMap.get("fitResidual").h[layer - 1][sector - 1].fill(fitResidual);
                        svthistos.histoRegionMap.get("fitResidual").h[region - 1].fill(fitResidual);
                        if (!Double.isNaN(localPhi)) {
                            int angleMin = svthistos.histoLorentzMap.get("sizeAngle").angleMin;
                            int minAngle = svthistos.histoLorentzMap.get("sizeAngle").minAngle;
                            int angleMax = svthistos.histoLorentzMap.get("sizeAngle").angleMax;
                            int angleSpace = svthistos.histoLorentzMap.get("sizeAngle").angleSpace;
                            int arraySize = svthistos.histoLorentzMap.get("sizeAngle").arraySize;
                            angleMin = minAngle;
                            angleMax = angleMin + angleSpace;
                            for (int jl = 0; jl < arraySize; jl++) {
                                if ((localPhi - 90) < angleMax && (localPhi - 90) > angleMin)
                                    svthistos.histoLorentzMap.get("sizeAngle").h[jl].fill(clusterSize);
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

    public void AddMcHit() {
        SVTMcHit svtMcHit = new SVTMcHit();
        svtMcHits.add(svtMcHit);
        nMcHits = svtMcHits.size();
    }

    public void AddGenPart() {
        GenPart genPart = new GenPart();
        genParts.add(genPart);
        nGenParts = genParts.size();
    }

    SVTTrajectory Trajectory(int trackId) {
        if (Constants.cosmicRun) return svtCosmicTracks.get(trackId).svtTrajectory;
        else return svtTracks.get(trackId).svtTrajectory;
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

    ArrayList<SVTCosmicTrack> SvtCosmicTracks() {
        return svtCosmicTracks;
    }

    ArrayList<SVTTrack> SvtTracks() {
        return svtTracks;
    }

    static ArrayList<SVTCross> TrackCrosses(int trackId) {
        if (Constants.cosmicRun) return svtCosmicTracks.get(trackId).svtTrackCrosses;
        else return svtTracks.get(trackId).svtTrackCrosses;
    }

    static ArrayList<SVTCluster> TrackClusters(int trackId, int crossId) {
        if (Constants.cosmicRun) return svtCosmicTracks.get(trackId).svtTrackCrosses.get(crossId).svtTrackClusters;
        else return svtTracks.get(trackId).svtTrackCrosses.get(crossId).svtTrackClusters;
    }

    static ArrayList<SVTHit> TrackHits(int trackId, int crossId, int clusterId) {
        if (Constants.cosmicRun)
            return svtCosmicTracks.get(trackId).svtTrackCrosses.get(crossId).svtTrackClusters.get(clusterId).svtTrackHits;
        else return svtTracks.get(trackId).svtTrackCrosses.get(crossId).svtTrackClusters.get(clusterId).svtTrackHits;
    }

    boolean SkipEvent(EvioDataEvent event, boolean print) {
        if (!event.hasBank("CVTRec::Cosmics") && !event.hasBank("BSTRec::Tracks") && !event.hasBank("CVTRec::Tracks")) {
            if (print) System.out.println(Constants.RED + "No tracks found, skip..." + Constants.RESET);
            return true;
        } else {

            EvioDataBank bank_SVTTracks;
            if (event.hasBank("CVTRec::Cosmics")) bank_SVTTracks = (EvioDataBank) event.getBank("CVTRec::Cosmics");
            else if (event.hasBank("CVTRec::Tracks")) bank_SVTTracks = (EvioDataBank) event.getBank("CVTRec::Tracks");
            else bank_SVTTracks = (EvioDataBank) event.getBank("BSTRec::Tracks");
            int nRows = bank_SVTTracks.rows();
            if (nRows != 1) {
                if (print) System.out.println(Constants.RED + nRows + " tracks, skip..." + Constants.RESET);
                return true; // 1 track per event
            } else {
                /*
                if (bank_SVTTracks.getDouble("circlefit_chi2_per_ndf", 0) > 0.05) {
                    if (print) System.out.println(Constants.RED + " chi2CheckBox large, skip..." + Constants.RESET);
                    return true; // chi2CheckBox cut
                }
                ArrayList<Integer> trackCrosses = new ArrayList<Integer>();
                for (int i = 0; i < Constants.NLAYERS; ++i) {
                    String varname = String.format("Cross%d_ID", i + 1);
                    int crossId = bank_SVTTracks.getInt(varname, 0); // get cross id's for the 1st track
                    if (crossId != -1) trackCrosses.add(crossId);
                }
                if (trackCrosses.size() < 4) {
                    if (print)
                        System.out.println(Constants.RED + trackCrosses.size() + " crosses, skip..." + Constants.RESET);
                    return true; // 8 crosses per track
                }
                */
//                else {
//                    Collections.sort(trackCrosses);
//                    if (event.hasBank("BSTRec::Crosses")) {
//                        EvioDataBank bank_SVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
//                        int sector;
//                        int region;
//                        int id;
//                        int nrows = bank_SVTCross.rows();
//                        for (int row = 0; row < nrows; ++row) {
//                            boolean track_cross = false;
//                            id = bank_SVTCross.getInt("ID", row);
//                            for (int i = 0; i < trackCrosses.size(); ++i) {
//                                if (id == trackCrosses.get(i)) {
//                                    track_cross = true;
//                                    break;
//                                }
//                            }
//                            if (!track_cross || id > 1000) continue; // MVT cross id > 1000
//                            sector = bank_SVTCross.getInt("sector", row);
//                            region = bank_SVTCross.getInt("region", row);
//                            boolean isType1Track = true;
//                            switch (region) { // type 1 tracks
//                                case 1: {
//                                    if (sector != 1 && sector != 6) isType1Track = false;
//                                    break;
//                                }
//                                case 2: {
//                                    if (sector != 1 && sector != 8) isType1Track = false;
//                                    break;
//                                }
//                                case 3: {
//                                    if (sector != 1 && sector != 10) isType1Track = false;
//                                    break;
//                                }
//                                case 4: {
//                                    if (sector != 1 && sector != 13) isType1Track = false;
//                                    break;
//                                }
//                                default:
//                                    System.out.println(Constants.RED + "region " + region);
//                            }
//                            if (!isType1Track) {
//                                if (print)
//                                    System.out.println(Constants.RED + "region " + region + ", sector " + sector + ", not a Type 1 track, skip..." + Constants.RESET);
//                                return true; // 8 crosses per track
//                            }
//                        }
//                    }
//                    if (print) {
//                        for (int i = 0; i < trackCrosses.size(); ++i) {
//                            System.out.print(trackCrosses.get(i) + " ");
//                        }
//                        System.out.println("");
//                    }

//                    for(int i=0;i<trackCrosses.size();++i) if(trackCrosses.get(i)!=i+1) {
//                        // if(print)
//                        System.out.println(Constants.RED+"no cross in every region, skip..."+Constants.RESET);
//                        return true;
//                    }
//                }
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
        ReadOffTrackCrosses(event, print);
        ReadOffTrackClusters(event, print);
        ReadOffTrackHits(event, print);
    }

    void ReadTracks(EvioDataEvent event, boolean print) {
        if (!event.hasBank("BSTRec::Trajectory") && !event.hasBank("CVTRec::Trajectory"))
            return; // temp fix, why not all events with tracks have a trajectory bank?
        if (event.hasBank("CVTRec::Cosmics")) {
            this.cosmics = true;
            EvioDataBank bankSVTCosmics = (EvioDataBank) event.getBank("CVTRec::Cosmics");
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
//                svtCosmicTracks.get(row).kfChi2 = bankSVTCosmics.getDouble("KF_chi2", row);
                svtCosmicTracks.get(row).kfChi2 = bankSVTCosmics.getDouble("chi2CheckBox", row);
                // svtCosmicTracks.get(row).kfNdf=(int)bankSVTCosmics.getDouble("KF_ndf",row);
//                svtCosmicTracks.get(row).kfNdf = bankSVTCosmics.getInt("KF_ndf", row);
                svtCosmicTracks.get(row).kfNdf = bankSVTCosmics.getInt("ndf", row);
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
        } else if (event.hasBank("BSTRec::Tracks") || event.hasBank("CVTRec::Tracks")) {
            this.cosmics = false;
            EvioDataBank bankSVTTracks;
            if (event.hasBank("BSTRec::Tracks")) bankSVTTracks = (EvioDataBank) event.getBank("BSTRec::Tracks");
            else bankSVTTracks = (EvioDataBank) event.getBank("CVTRec::Tracks");
            nTracks = bankSVTTracks.rows();
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
                svtTracks.get(row).pathLength = bankSVTTracks.getDouble("pathlength", row);
                svtTracks.get(row).q = bankSVTTracks.getInt("q", row);
                svtTracks.get(row).p = bankSVTTracks.getDouble("p", row);
                svtTracks.get(row).pT = bankSVTTracks.getDouble("pt", row);
//                svtTracks.get(row).phi0 = bankSVTTracks.getDouble("phi0", row) * 180 / Math.PI;
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
                svtTracks.get(row).cirFitChi2Ndf = bankSVTTracks.getDouble("circlefit_chi2_per_ndf", row);
                svtTracks.get(row).linFitChi2Ndf = bankSVTTracks.getDouble("linefit_chi2_per_ndf", row);
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
        int[] shift = {0, 0, 10, 10, 24, 24, 42, 42};
        if (layer == 0 || sector == 0) return -1;
        return sector + shift[layer - 1] - 1;
    }

    static int Sensor(int layer, int sector) {
        int[] shift = {0, 10, 20, 34, 48, 66, 84, 108};
        if (layer == 0 || sector == 0) return -1;
        return sector + shift[layer - 1] - 1;
    }

    static boolean IsTrackCross(int crossId) {
        for (int i = 0; i < nTracks; ++i) {
            for (int j = 0; j < TrackCrosses(i).size(); ++j) if (crossId == TrackCrosses(i).get(j).id) return true;
        }
        return false;
    }

    boolean IsTrackCluster(int clusterId) {
        for (int i = 0; i < nTracks; ++i) {
            for (int j = 0; j < TrackCrosses(i).size(); ++j) {
                for (int k = 0; k < TrackClusters(i, j).size(); ++k)
                    if (clusterId == TrackClusters(i, j).get(k).id) return true;
            }
        }
        return false;
    }

    boolean IsTrackHit(int hitId) {
        for (int i = 0; i < nTracks; ++i) {
            for (int j = 0; j < TrackCrosses(i).size(); ++j) {
                for (int k = 0; k < TrackClusters(i, j).size(); ++k) {
                    for (int l = 0; l < TrackHits(i, j, k).size(); ++l)
                        if (hitId == TrackHits(i, j, k).get(l).id) return true;
                }
            }
        }
        return false;
    }

    void ReadOffTrackCrosses(EvioDataEvent event, boolean print) {
        if (event.hasBank("BSTRec::Crosses")) {
            EvioDataBank bankSVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
            int nClusterRows;
            EvioDataBank bank_SVTCluster = null;
            if (event.hasBank("BSTRec::Clusters")) {
                bank_SVTCluster = (EvioDataBank) event.getBank("BSTRec::Clusters");
                nClusterRows = bank_SVTCluster.rows();
            } else nClusterRows = 0;
            int nrows = bankSVTCross.rows();
            int crossId = -1;
            for (int row = 0; row < nrows; ++row) {
                crossId = bankSVTCross.getInt("ID", row);
                if (IsTrackCross(crossId)) continue;
                AddOffTrackCross();
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).id = crossId;
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).sector = bankSVTCross.getInt("sector", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).region = bankSVTCross.getInt("region", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).x = bankSVTCross.getDouble("x", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).errX = bankSVTCross.getDouble("err_x", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).ux = bankSVTCross.getDouble("ux", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).y = bankSVTCross.getDouble("y", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).errY = bankSVTCross.getDouble("err_y", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).uy = bankSVTCross.getDouble("uy", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).z = bankSVTCross.getDouble("z", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).errZ = bankSVTCross.getDouble("err_z", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).uz = bankSVTCross.getDouble("uz", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).clusterId[0] = bankSVTCross.getInt("Cluster1_ID", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).clusterId[1] = bankSVTCross.getInt("Cluster2_ID", row);
                svtOfftrackCrosses.get(nOfftrackCrosses - 1).nOfftrackClusters = 2;
                for (int clusterRow = 0; clusterRow < nClusterRows; ++clusterRow) {
                    if (bank_SVTCluster.getInt("ID", clusterRow) == svtOfftrackCrosses.get(nOfftrackCrosses - 1).clusterId[0] ||
                            bank_SVTCluster.getInt("ID", clusterRow) == svtOfftrackCrosses.get(nOfftrackCrosses - 1).clusterId[1])
                        svtOfftrackCrosses.get(nOfftrackCrosses - 1).nOfftrackHits += bank_SVTCluster.getInt("size", clusterRow);
                }
                if (print) {
                    System.out.println(Constants.RED + "Off track" + Constants.RESET);
                    svtOfftrackCrosses.get(nOfftrackCrosses - 1).Show();
                }
            }
        }
    }

    void ReadOffTrackClusters(EvioDataEvent event, boolean print) {
        if (event.hasBank("BSTRec::Clusters")) {
            EvioDataBank bankSVTCluster = (EvioDataBank) event.getBank("BSTRec::Clusters");
            int nCrossRows;
            EvioDataBank bankSVTCross = null;
            if (event.hasBank("BSTRec::Crosses")) {
                bankSVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
                nCrossRows = bankSVTCross.rows();
            } else nCrossRows = 0;
            int nrows = bankSVTCluster.rows();
            int clusterId = -1;
            for (int row = 0; row < nrows; ++row) {
                clusterId = bankSVTCluster.getInt("ID", row);
                if (IsTrackCluster(clusterId)) continue;
                AddOffTrackCluster();
                svtOfftrackClusters.get(nOfftrackClusters - 1).id = clusterId;
                svtOfftrackClusters.get(nOfftrackClusters - 1).sector = bankSVTCluster.getInt("sector", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).layer = bankSVTCluster.getInt("layer", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).size = bankSVTCluster.getInt("size", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).eTot = bankSVTCluster.getDouble("ETot", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).seedE = bankSVTCluster.getDouble("seedE", row);
/*
                for (int row1 = 0; row1 < nrows; ++row1) {
                    int sector = bankSVTCluster.getInt("sector", row1);
                    int layer = bankSVTCluster.getInt("layer", row1);
                    if (sector != svtOfftrackClusters.get(nOfftrackClusters - 1).sector ||
                            layer != svtOfftrackClusters.get(nOfftrackClusters - 1).layer + 1) continue;
                    svtOfftrackClusters.get(nOfftrackClusters - 1).seedStrip = bankSVTCluster.getInt("seedStrip", row);
                    if (svtOfftrackClusters.get(nOfftrackClusters - 1).layer == 1 && svtOfftrackClusters.get(nOfftrackClusters - 1).sector == 1 && svtOfftrackClusters.get(nOfftrackClusters - 1).seedStrip == 200)
                        System.out.println(eventNr);
                }
*/
                svtOfftrackClusters.get(nOfftrackClusters - 1).centroid = bankSVTCluster.getDouble("centroid", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).centroidResidual = bankSVTCluster.getDouble("centroidResidual", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).seedResidual = bankSVTCluster.getDouble("seedResidual", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).hit1Id = bankSVTCluster.getInt("Hit1_ID", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).hit2Id = bankSVTCluster.getInt("Hit2_ID", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).hit3Id = bankSVTCluster.getInt("Hit3_ID", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).hit4Id = bankSVTCluster.getInt("Hit4_ID", row);
                svtOfftrackClusters.get(nOfftrackClusters - 1).hit5Id = bankSVTCluster.getInt("Hit5_ID", row);

                for (int crossRow = 0; crossRow < nCrossRows; ++crossRow) {
                    if (clusterId == bankSVTCross.getInt("Cluster1_ID", crossRow) ||
                            clusterId == bankSVTCross.getInt("Cluster2_ID", crossRow))
                        svtOfftrackClusters.get(nOfftrackClusters - 1).crossId = bankSVTCross.getInt("ID", crossRow);
                }
                if (print) {
                    System.out.println(Constants.RED + "Off track" + Constants.RESET);
                    svtOfftrackClusters.get(nOfftrackClusters - 1).Show();
                }
            }
        }
    }

    void ReadOffTrackHits(EvioDataEvent event, boolean print) {
        if (event.hasBank("BSTRec::Hits")) {
            EvioDataBank bankSVTHit = (EvioDataBank) event.getBank("BSTRec::Hits");
            int nRows = bankSVTHit.rows();
            EvioDataBank bankSVTDgtz = null;
            boolean dgtzBank;
            if (event.hasBank("BST::dgtz")) {
                bankSVTDgtz = (EvioDataBank) event.getBank("BST::dgtz");
                dgtzBank = true;
            } else {
                dgtzBank = false;
                System.out.println("No dgtz bank in event");
            }
            int hitId = -1;
            for (int row = 0; row < nRows; ++row) {
                // hitId=row+1;
                // hitId=bankSVTHit.getInt("id",row);
                hitId = bankSVTHit.getInt("ID", row);
                if (IsTrackHit(hitId)) continue;
                AddOffTrackHit();
                svtOfftrackHits.get(nOfftrackHits - 1).id = hitId;
                svtOfftrackHits.get(nOfftrackHits - 1).sector = bankSVTHit.getInt("sector", row);
                svtOfftrackHits.get(nOfftrackHits - 1).layer = bankSVTHit.getInt("layer", row);
                svtOfftrackHits.get(nOfftrackHits - 1).strip = bankSVTHit.getInt("strip", row);
                svtOfftrackHits.get(nOfftrackHits - 1).fitResidual = bankSVTHit.getDouble("fitResidual", row);
                svtOfftrackHits.get(nOfftrackHits - 1).trkingStat = bankSVTHit.getInt("trkingStat", row);
                svtOfftrackHits.get(nOfftrackHits - 1).clusterId = bankSVTHit.getInt("clusterID", row);
//                if(dgtzBank) {
//                    svtOfftrackHits.get(nOfftrackHits -1).adc =bankSVTDgtz.getInt("ADC",row);
//                    svtOfftrackHits.get(nOfftrackHits -1).bco =bankSVTDgtz.getInt("bco",row);
//                    svtOfftrackHits.get(nOfftrackHits -1).hitN =bankSVTDgtz.getInt("hitn",row);
//                }
                for (int clusterRow = 0; clusterRow < svtOfftrackClusters.size(); ++clusterRow) {
                    if (svtOfftrackHits.get(nOfftrackHits - 1).clusterId == svtOfftrackClusters.get(clusterRow).id)
                        svtOfftrackHits.get(nOfftrackHits - 1).crossId = svtOfftrackClusters.get(clusterRow).crossId;
                }
                if (print) {
                    System.out.println(Constants.RED + "Off track" + Constants.RESET);
                    svtOfftrackHits.get(nOfftrackHits - 1).Show();
                }
            }
        }
    }

    void ReadMCHits(EvioDataEvent event, boolean print) {
        if (event.hasBank("BST::true")) {
            EvioDataBank bankSVTMcHit = (EvioDataBank) event.getBank("BST::true");
            int nRows = bankSVTMcHit.rows();
            for (int row = 0; row < nRows; ++row) {
                AddMcHit();
                svtMcHits.get(nMcHits - 1).id = bankSVTMcHit.getInt("hitn", row);
                svtMcHits.get(nMcHits - 1).pId = bankSVTMcHit.getInt("pid", row);
                svtMcHits.get(nMcHits - 1).mPId = bankSVTMcHit.getInt("mpid", row);
                svtMcHits.get(nMcHits - 1).tId = bankSVTMcHit.getInt("tid", row);
                svtMcHits.get(nMcHits - 1).mTId = bankSVTMcHit.getInt("mtid", row);
                svtMcHits.get(nMcHits - 1).oTId = bankSVTMcHit.getInt("otid", row);
                svtMcHits.get(nMcHits - 1).trackE = bankSVTMcHit.getDouble("trackE", row);
                svtMcHits.get(nMcHits - 1).totEDep = bankSVTMcHit.getDouble("totEdep", row);
                svtMcHits.get(nMcHits - 1).avgX = bankSVTMcHit.getDouble("avgX", row);
                svtMcHits.get(nMcHits - 1).avgY = bankSVTMcHit.getDouble("avgY", row);
                svtMcHits.get(nMcHits - 1).avgZ = bankSVTMcHit.getDouble("avgZ", row);
                svtMcHits.get(nMcHits - 1).avgLX = bankSVTMcHit.getDouble("avgLx", row);
                svtMcHits.get(nMcHits - 1).avgLY = bankSVTMcHit.getDouble("avgLy", row);
                svtMcHits.get(nMcHits - 1).avgLZ = bankSVTMcHit.getDouble("avgLz", row);
                double pX = 0.001 * bankSVTMcHit.getDouble("px", row);
                double pY = 0.001 * bankSVTMcHit.getDouble("py", row);
                double pZ = 0.001 * bankSVTMcHit.getDouble("pz", row);
                svtMcHits.get(nMcHits - 1).pX = pX;
                svtMcHits.get(nMcHits - 1).pY = pY;
                svtMcHits.get(nMcHits - 1).pZ = pZ;
                svtMcHits.get(nMcHits - 1).pT = Math.sqrt(pX * pX + pY * pY);
                svtMcHits.get(nMcHits - 1).p = Math.sqrt(pX * pX + pY * pY + pZ * pZ);
                svtMcHits.get(nMcHits - 1).vX = bankSVTMcHit.getDouble("vx", row);
                svtMcHits.get(nMcHits - 1).vY = bankSVTMcHit.getDouble("vy", row);
                svtMcHits.get(nMcHits - 1).vZ = bankSVTMcHit.getDouble("vz", row);
                svtMcHits.get(nMcHits - 1).mVX = bankSVTMcHit.getDouble("mvx", row);
                svtMcHits.get(nMcHits - 1).mVY = bankSVTMcHit.getDouble("mvy", row);
                svtMcHits.get(nMcHits - 1).mVZ = bankSVTMcHit.getDouble("mvz", row);
                svtMcHits.get(nMcHits - 1).avgT = bankSVTMcHit.getDouble("avgT", row);
            }
        }
    }

    void ReadGenParts(EvioDataEvent event, boolean print) {
        if (event.hasBank("GenPart::true")) {
            EvioDataBank bankGenPart = (EvioDataBank) event.getBank("GenPart::true");
            int nRows = bankGenPart.rows();
            AddGenPart();
            for (int row = 0; row < nRows; ++row) {
                genParts.get(nGenParts - 1).id = row + 1;
                genParts.get(nGenParts - 1).pId = bankGenPart.getInt("pid", row);
                double pX = 0.001 * bankGenPart.getDouble("px", row);
                double pY = 0.001 * bankGenPart.getDouble("py", row);
                double pZ = 0.001 * bankGenPart.getDouble("pz", row);
                double p = Math.sqrt(pX * pX + pY * pY + pZ * pZ);
                double thetaGen = Math.acos(pZ / p);
                double phiGen = Math.atan2(pY, pX);
//                double thetaGen = Math.acos(pZ/p)*180/Math.PI;
                genParts.get(nGenParts - 1).pX = pX;
                genParts.get(nGenParts - 1).pY = pY;
                genParts.get(nGenParts - 1).pZ = pZ;
                genParts.get(nGenParts - 1).pT = Math.sqrt(pX * pX + pY * pY);
                genParts.get(nGenParts - 1).p = p;
                genParts.get(nGenParts - 1).phi = phiGen;
                genParts.get(nGenParts - 1).theta = thetaGen;
                genParts.get(nGenParts - 1).vX = bankGenPart.getDouble("vx", row);
                genParts.get(nGenParts - 1).vY = bankGenPart.getDouble("vy", row);
                genParts.get(nGenParts - 1).vZ = bankGenPart.getDouble("vz", row);
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
        String[] trackColumnNames = {"TkID", "sl_yx", "int_yx", "sl_yz", "int_yz", "theta", "phi", "chi2CheckBox", "ndf",
                "Cr1_ID", "Cr2_ID", "Cr3_ID", "Cr4_ID", "Cr5_ID", "Cr6_ID", "Cr7_ID", "Cr8_ID", "n_cr", "n_cl", "n_h"};
        int n_vars = trackColumnNames.length;
        data = new Object[nTracks][n_vars];
        for (int i = 0; i < nTracks; ++i) {
            svtCosmicTracks.get(i).svtTrajectory.Show();
            data[i][0] = svtCosmicTracks.get(i).id;
            data[i][1] = nf.format(svtCosmicTracks.get(i).trklineYxSlope);
            data[i][2] = nf.format(svtCosmicTracks.get(i).trklineYxInterc);
            data[i][3] = nf.format(svtCosmicTracks.get(i).trklineYzSlope);
            data[i][4] = nf.format(svtCosmicTracks.get(i).trklineYzInterc);
            data[i][5] = nf0.format(svtCosmicTracks.get(i).theta);
            data[i][6] = nf0.format(svtCosmicTracks.get(i).phi);
            data[i][7] = nf0.format(svtCosmicTracks.get(i).kfChi2);
            data[i][8] = svtCosmicTracks.get(i).kfNdf;
            data[i][9] = svtCosmicTracks.get(i).crossId[0];
            data[i][10] = svtCosmicTracks.get(i).crossId[1];
            data[i][11] = svtCosmicTracks.get(i).crossId[2];
            data[i][12] = svtCosmicTracks.get(i).crossId[3];
            data[i][13] = svtCosmicTracks.get(i).crossId[4];
            data[i][14] = svtCosmicTracks.get(i).crossId[5];
            data[i][15] = svtCosmicTracks.get(i).crossId[6];
            data[i][16] = svtCosmicTracks.get(i).crossId[7];
            data[i][17] = nTrackCrosses;
            data[i][18] = nTrackClusters;
            data[i][19] = nTrackHits;
        }
        TextTable tt = new TextTable(trackColumnNames, data);
        tt.printTable();
        String[] crossesColumnNames = {"CrID", "sector", "region", "x", "y", "z", "errx", "erry", "errz", "ux", "uy", "uz", "Cl1ID", "Cl2ID", "TkID", "n_cl", "n_h"};
        n_vars = crossesColumnNames.length;
        data = new Object[nTrackCrosses][n_vars];
        int m = 0;
        for (int i = 0; i < nTracks; ++i) {
            for (int j = 0; j < TrackCrosses(i).size(); ++j) {
// 	if(j%2==0) { 
// 	}
// 	else { 
// 	  data[m][0]=RED+TrackCrosses(i).get(j).id+RESET;
// 	}
                data[m][0] = TrackCrosses(i).get(j).id;
                data[m][1] = TrackCrosses(i).get(j).sector;
                data[m][2] = TrackCrosses(i).get(j).region;
                data[m][3] = nf0.format(TrackCrosses(i).get(j).x);
                data[m][4] = nf0.format(TrackCrosses(i).get(j).y);
                data[m][5] = nf0.format(TrackCrosses(i).get(j).z);
                data[m][6] = nf.format(TrackCrosses(i).get(j).errX);
                data[m][7] = nf.format(TrackCrosses(i).get(j).errY);
                data[m][8] = nf.format(TrackCrosses(i).get(j).errZ);
                data[m][9] = nf.format(TrackCrosses(i).get(j).ux);
                data[m][10] = nf.format(TrackCrosses(i).get(j).uy);
                data[m][11] = nf.format(TrackCrosses(i).get(j).uz);
                data[m][12] = TrackCrosses(i).get(j).clusterId[0];
                data[m][13] = TrackCrosses(i).get(j).clusterId[1];
                data[m][14] = TrackCrosses(i).get(j).trackId;
                data[m][15] = TrackCrosses(i).get(j).nTrackClusters;
                data[m][16] = TrackCrosses(i).get(j).nTrackHits;
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

        String[] clustersColumnNames = {"ClID", "sector", "layer", "size", "eTot", "eSeed", "seed", "centroid", "centroidResidual", "seedResidual", "Hit1", "Hit2", "Hit3", "Hit4", "Hit5", "CrID", "TkID", "TrjID"};
        n_vars = clustersColumnNames.length;
        data = new Object[nTrackClusters][n_vars];
        m = 0;
        for (int i = 0; i < nTracks; ++i) {
            for (int j = 0; j < TrackCrosses(i).size(); ++j) {
                for (int k = 0; k < TrackClusters(i, j).size(); ++k) {
                    data[m][0] = TrackClusters(i, j).get(k).id;
                    data[m][1] = TrackClusters(i, j).get(k).sector;
                    data[m][2] = TrackClusters(i, j).get(k).layer;
                    data[m][3] = TrackClusters(i, j).get(k).size;
                    data[m][4] = nf0.format(TrackClusters(i, j).get(k).eTot);
                    data[m][5] = nf0.format(TrackClusters(i, j).get(k).seedE);
                    data[m][6] = TrackClusters(i, j).get(k).seedStrip;
                    data[m][7] = nf.format(TrackClusters(i, j).get(k).centroid);
                    data[m][8] = TrackClusters(i, j).get(k).hit1Id;
                    data[m][9] = TrackClusters(i, j).get(k).hit2Id;
                    data[m][10] = TrackClusters(i, j).get(k).hit3Id;
                    data[m][11] = TrackClusters(i, j).get(k).hit4Id;
                    data[m][12] = TrackClusters(i, j).get(k).hit5Id;
                    data[m][13] = TrackClusters(i, j).get(k).crossId;
                    data[m][14] = TrackClusters(i, j).get(k).trackId;
                    data[m][15] = TrackClusters(i, j).get(k).trjId;
                    m++;
                }
            }
        }
        TextTable ttcl = new TextTable(clustersColumnNames, data);
        ttcl.printTable();

        String[] hitsColumnNames = {"HitID", "sector", "layer", "strip", "residual", "tkStat", "adc", "bco", "hitN", "ClID", "CrID", "TkID"};
        n_vars = hitsColumnNames.length;
        data = new Object[nTrackHits][n_vars];
        m = 0;
        for (int i = 0; i < nTracks; ++i) {
            for (int j = 0; j < TrackCrosses(i).size(); ++j) {
                for (int k = 0; k < TrackClusters(i, j).size(); ++k) {
                    for (int l = 0; l < TrackHits(i, j, k).size(); ++l) {
                        data[m][0] = TrackHits(i, j, k).get(l).id;
                        data[m][1] = TrackHits(i, j, k).get(l).sector;
                        data[m][2] = TrackHits(i, j, k).get(l).layer;
                        data[m][3] = TrackHits(i, j, k).get(l).strip;
                        data[m][4] = nf.format(TrackHits(i, j, k).get(l).fitResidual);
                        data[m][5] = TrackHits(i, j, k).get(l).trkingStat;
                        data[m][6] = TrackHits(i, j, k).get(l).adc;
                        data[m][7] = TrackHits(i, j, k).get(l).bco;
                        data[m][8] = TrackHits(i, j, k).get(l).hitN;
                        data[m][9] = TrackHits(i, j, k).get(l).clusterId;
                        data[m][10] = TrackHits(i, j, k).get(l).crossId;
                        data[m][11] = TrackHits(i, j, k).get(l).trackId;
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
        String[] offtrackCrossesColumnNames = {"CrID", "sector", "region", "x", "y", "z", "errx", "erry", "errz", "ux", "uy", "uz", "Cl1ID", "Cl2ID", "TkID", "n_cl", "n_h"};
        int n_vars = offtrackCrossesColumnNames.length;
        data = new Object[svtOfftrackCrosses.size()][n_vars];
        for (int j = 0; j < svtOfftrackCrosses.size(); ++j) {
            data[j][0] = svtOfftrackCrosses.get(j).id;
            data[j][1] = svtOfftrackCrosses.get(j).sector;
            data[j][2] = svtOfftrackCrosses.get(j).region;
            data[j][3] = nf0.format(svtOfftrackCrosses.get(j).x);
            data[j][4] = nf0.format(svtOfftrackCrosses.get(j).y);
            data[j][5] = nf0.format(svtOfftrackCrosses.get(j).z);
            data[j][6] = nf.format(svtOfftrackCrosses.get(j).errX);
            data[j][7] = nf.format(svtOfftrackCrosses.get(j).errY);
            data[j][8] = nf.format(svtOfftrackCrosses.get(j).errZ);
            data[j][9] = nf.format(svtOfftrackCrosses.get(j).ux);
            data[j][10] = nf.format(svtOfftrackCrosses.get(j).uy);
            data[j][11] = nf.format(svtOfftrackCrosses.get(j).uz);
            data[j][12] = svtOfftrackCrosses.get(j).clusterId[0];
            data[j][13] = svtOfftrackCrosses.get(j).clusterId[1];
            data[j][14] = svtOfftrackCrosses.get(j).trackId;
            data[j][15] = svtOfftrackCrosses.get(j).nOfftrackClusters;
            data[j][16] = svtOfftrackCrosses.get(j).nOfftrackHits;
        }
        TextTable ttcr = new TextTable(offtrackCrossesColumnNames, data);
        ttcr.printTable();

        String[] offtrackClustersColumnNames = {"ClID", "sector", "layer", "size", "eTot", "eSeed", "seed", "centroid", "Hit1", "Hit2", "Hit3", "Hit4", "Hit5", "CrID", "TkID"};
        n_vars = offtrackClustersColumnNames.length;
        data = new Object[svtOfftrackClusters.size()][n_vars];
        for (int k = 0; k < svtOfftrackClusters.size(); ++k) {
            data[k][0] = svtOfftrackClusters.get(k).id;
            data[k][1] = svtOfftrackClusters.get(k).sector;
            data[k][2] = svtOfftrackClusters.get(k).layer;
            data[k][3] = svtOfftrackClusters.get(k).size;
            data[k][4] = nf0.format(svtOfftrackClusters.get(k).eTot);
            data[k][5] = nf0.format(svtOfftrackClusters.get(k).seedE);
            data[k][6] = svtOfftrackClusters.get(k).seedStrip;
            data[k][7] = nf.format(svtOfftrackClusters.get(k).centroid);
            data[k][8] = svtOfftrackClusters.get(k).hit1Id;
            data[k][9] = svtOfftrackClusters.get(k).hit2Id;
            data[k][10] = svtOfftrackClusters.get(k).hit3Id;
            data[k][11] = svtOfftrackClusters.get(k).hit4Id;
            data[k][12] = svtOfftrackClusters.get(k).hit5Id;
            data[k][13] = svtOfftrackClusters.get(k).crossId;
            data[k][14] = svtOfftrackClusters.get(k).trackId;
        }
        TextTable ttcl = new TextTable(offtrackClustersColumnNames, data);
        ttcl.printTable();

        String[] offtrackHitsColumnNames = {"HitID", "sector", "layer", "strip", "residual", "tkStat", "adc", "bco", "hitN", "ClID", "CrID", "TkID"};
        n_vars = offtrackHitsColumnNames.length;
        data = new Object[svtOfftrackHits.size()][n_vars];
        for (int l = 0; l < svtOfftrackHits.size(); ++l) {
            data[l][0] = svtOfftrackHits.get(l).id;
            data[l][1] = svtOfftrackHits.get(l).sector;
            data[l][2] = svtOfftrackHits.get(l).layer;
            data[l][3] = svtOfftrackHits.get(l).strip;
            data[l][4] = nf.format(svtOfftrackHits.get(l).fitResidual);
            data[l][5] = svtOfftrackHits.get(l).trkingStat;
            data[l][6] = svtOfftrackHits.get(l).adc;
            data[l][7] = svtOfftrackHits.get(l).bco;
            data[l][8] = svtOfftrackHits.get(l).hitN;
            data[l][9] = svtOfftrackHits.get(l).clusterId;
            data[l][10] = svtOfftrackHits.get(l).crossId;
            data[l][11] = svtOfftrackHits.get(l).trackId;
        }
        TextTable tth = new TextTable(offtrackHitsColumnNames, data);
        tth.printTable();

    }
}
