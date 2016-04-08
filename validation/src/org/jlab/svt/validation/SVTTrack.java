package org.jlab.svt.validation;

import org.jlab.evio.clas12.EvioDataBank;
import org.jlab.evio.clas12.EvioDataEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SVTTrack {

    int id;
    int fittingMethod;
    double cX;
    double cY;
    double cZ;
    double cUX;
    double cUY;
    double cUZ;
    double pathLength;
    int q;
    double p;
    double pT;
    double phi0;
    double tanDip;
    double z0;
    double d0;
    double covD02;
    double covD0Phi0;
    double covD0Rho;
    double covPhi02;
    double covPhi0Rho;
    double covRho2;
    double covZ02;
    double covTanDip2;
    double cirFitChi2Ndf;
    double linFitChi2Ndf;
    int[] crossId;

    int nTrackHits;
    int nTrackClusters;
    int nTrackCrosses;
    ArrayList<SVTCross> svtTrackCrosses;
    SVTTrajectory svtTrajectory;

    double trklineYxSlope;
    double trklineYxInterc;
    double trklineYzSlope;
    double trklineYzInterc;
    double theta;
    double phi;
    double kfChi2;
    int kfNdf;

    public SVTTrack() {
        id = -1;
        fittingMethod = -1;
        cX = -1;
        cY = -1;
        cZ = -1;
        cUX = -1;
        cUY = -1;
        cUZ = -1;
        int q = -1;
        pathLength = -1;
        p = -1;
        pT = -1;
        phi0 = -1;
        tanDip = -1;
        z0 = -1;
        d0 = -1;
        covD02 = -1;
        covD0Phi0 = -1;
        covD0Rho = -1;
        covPhi02 = -1;
        covPhi0Rho = -1;
        covRho2 = -1;
        covZ02 = -1;
        covTanDip2 = -1;
        cirFitChi2Ndf = -1;
        linFitChi2Ndf = -1;
        crossId = new int[Constants.NREGIONS];
        for(int i=0;i<Constants.NREGIONS;++i) crossId[i] = -1;

        svtTrackCrosses = new ArrayList<>();
        nTrackHits = 0;
        nTrackClusters = 0;
        nTrackCrosses = 0;

        trklineYxSlope = -1;
        trklineYxInterc = -1;
        trklineYzSlope = -1;
        trklineYzInterc = -1;
        theta = -1;
        phi = -1;
        kfChi2 = -1;
        kfNdf = -1;
    }

    void ReadTrackCrosses(EvioDataEvent event, int crossId, boolean print) {
        AddTrackCross();
        nTrackCrosses = svtTrackCrosses.size();

        if(event.hasBank("BSTRec::Crosses")){
            int trajRow=0;
            EvioDataBank bank_SVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
            int nrows=bank_SVTCross.rows();
            int crossRow=-1;
            int sector=-1;
            int layer=-1;
            for(int row = 0; row < nrows; row++){
                if(crossId==bank_SVTCross.getInt("ID",row)) crossRow=row;
            }
            svtTrackCrosses.get(nTrackCrosses-1).id =crossId;
            svtTrackCrosses.get(nTrackCrosses-1).sector =bank_SVTCross.getInt("sector",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).region =bank_SVTCross.getInt("region",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).x =bank_SVTCross.getDouble("x",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).errX =bank_SVTCross.getDouble("err_x",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).ux =bank_SVTCross.getDouble("ux",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).y =bank_SVTCross.getDouble("y",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).errY =bank_SVTCross.getDouble("err_y",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).uy =bank_SVTCross.getDouble("uy",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).z =bank_SVTCross.getDouble("z",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).errZ =bank_SVTCross.getDouble("err_z",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).uz =bank_SVTCross.getDouble("uz",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).clusterId[0]=bank_SVTCross.getInt("Cluster1_ID",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).clusterId[1]=bank_SVTCross.getInt("Cluster2_ID",crossRow);
            svtTrackCrosses.get(nTrackCrosses-1).trackId = id;
            for(int i=0;i<2;++i) {
                svtTrackCrosses.get(nTrackCrosses-1).ReadTrackClusters(event, svtTrackCrosses.get(nTrackCrosses-1).clusterId[i], svtTrajectory, print);
                sector = svtTrackCrosses.get(nTrackCrosses-1).svtTrackClusters.get(i).sector;
                layer = svtTrackCrosses.get(nTrackCrosses-1).svtTrackClusters.get(i).layer;
//                System.out.println("cr " + i + " " + layer + " " + sector + " " + id + " " + svtTrajectory.GetIdByModule(id, layer, sector));


                if(svtTrajectory.GetIdByModule(id,layer,sector)!=-1) {
                    svtTrackCrosses.get(nTrackCrosses-1).trjId[i] = svtTrajectory.GetIdByModule(id, layer, sector);
                }
                else svtTrackCrosses.get(nTrackCrosses-1).trjId[i] = 0;
// !!!!!!!!!!!!! temporary to kludge the bug with track id in traj bank


            }




            nTrackClusters += svtTrackCrosses.get(nTrackCrosses-1).nTrackClusters;
            nTrackHits += svtTrackCrosses.get(nTrackCrosses-1).nTrackHits;
            if(print) svtTrackCrosses.get(nTrackCrosses-1).Show();
        }
    }

    void ReadTrackTrajectory(EvioDataEvent event, int trackId, boolean print) {
        if(event.hasBank("BSTRec::Trajectory") || event.hasBank("CVTRec::Trajectory")){
            svtTrajectory = new SVTTrajectory();
            int trajRow=0;
            EvioDataBank bank_SVTTrajectory;
            if(event.hasBank("BSTRec::Trajectory")) bank_SVTTrajectory = (EvioDataBank) event.getBank("BSTRec::Trajectory");
            else bank_SVTTrajectory = (EvioDataBank) event.getBank("CVTRec::Trajectory");
            int nrows=bank_SVTTrajectory.rows();
            for(int row = 0; row < nrows; row++){
                if(trackId!=bank_SVTTrajectory.getInt("ID",row)||bank_SVTTrajectory.getInt("LayerTrackIntersPlane",row)==0) continue;
//                int id = svtTrajectory.id.get(trajRow);//=row+1;
//                svtTrajectory.id.set(trajRow,row+1);
                svtTrajectory.id.add(trajRow,row+1);
                svtTrajectory.sector.add(trajRow,bank_SVTTrajectory.getInt("SectorTrackIntersPlane",row));
                svtTrajectory.layer.add(trajRow,bank_SVTTrajectory.getInt("LayerTrackIntersPlane",row));
                svtTrajectory.x.add(trajRow,bank_SVTTrajectory.getDouble("XtrackIntersPlane",row));
                svtTrajectory.y.add(trajRow,bank_SVTTrajectory.getDouble("YtrackIntersPlane",row));
                svtTrajectory.z.add(trajRow,bank_SVTTrajectory.getDouble("ZtrackIntersPlane",row));
                double phi = bank_SVTTrajectory.getDouble("PhiTrackIntersPlane",row);
                double theta = bank_SVTTrajectory.getDouble("ThetaTrackIntersPlane",row);
                double angle3D = bank_SVTTrajectory.getDouble("trkToMPlnAngl",row);
                if(Constants.mcRun) {
                    phi*=180/Math.PI;
                    theta*=180/Math.PI;
                    angle3D*=180/Math.PI;
                }
                svtTrajectory.phi.add(trajRow,phi);
                svtTrajectory.theta.add(trajRow,theta);
                svtTrajectory.angle3D.add(trajRow,angle3D);
                svtTrajectory.centroid.add(trajRow,bank_SVTTrajectory.getDouble("CalcCentroidStrip",row));
                trajRow++;
            }
            svtTrajectory.nLayers =trajRow;
            svtTrajectory.trackId =trackId;
            if(print) svtTrajectory.Show();
        }
    }

    public void AddTrackCross() {
        SVTCross svtCross = new SVTCross();
        svtTrackCrosses.add(svtCross);
    }

    void Show() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        System.out.print("Track " + id + " " + nf.format(trklineYxSlope) + " " + nf.format(trklineYxInterc) + " " + nf.format(trklineYzSlope) + " " +
                nf.format(trklineYzInterc) + " " + nf.format(theta) + " " + nf.format(phi) + " " + nf.format(kfChi2) + " " + kfNdf + " crId ");
        if(Constants.cosmicRun) for(int i=0;i<Constants.NLAYERS;++i) System.out.print(crossId[i] + " ");
        else  for(int i=0;i<Constants.NREGIONS;++i) System.out.print(crossId[i] + " ");
        System.out.println( " ncr " + nTrackCrosses + " ncl " + nTrackClusters + " nh " + nTrackHits);
    }
}
