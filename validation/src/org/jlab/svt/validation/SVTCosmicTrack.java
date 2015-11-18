package org.jlab.svt.validation;

import org.jlab.evio.clas12.EvioDataBank;
import org.jlab.evio.clas12.EvioDataEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

class SVTCosmicTrack {

    int id_;
    double trkline_yx_slope_;
    double trkline_yx_interc_;
    double trkline_yz_slope_;
    double trkline_yz_interc_;
    double theta_;
    double phi_;
    double kf_chi2_;
    int kf_ndf_;
    int[] cross_id_;
    int n_track_hits_;
    int n_track_clusters_;
    int n_track_crosses_;
    ArrayList<SVTCross> svt_track_crosses_;
    SVTTrajectory svt_trajectory_;

    public SVTCosmicTrack() {
        id_=-1;
        trkline_yx_slope_=-1;
        trkline_yx_interc_=-1;
        trkline_yz_slope_=-1;
        trkline_yz_interc_=-1;
        theta_=-1;
        phi_=-1;
        kf_chi2_=-1;
        kf_ndf_=-1;
        cross_id_ = new int[Constants.NLAYERS];
        for(int i=0;i<Constants.NLAYERS;++i) cross_id_[i]=-1;
        svt_track_crosses_ = new ArrayList<>();
        n_track_hits_=0;
        n_track_clusters_=0;
        n_track_crosses_=0;
    }

    void ReadTrackCrosses(EvioDataEvent event, int cross_id, boolean print) {
        AddTrackCross();
        n_track_crosses_=svt_track_crosses_.size();

        if(event.hasBank("BSTRec::Crosses")){
            int traj_row=0;
            EvioDataBank bank_SVTCross = (EvioDataBank) event.getBank("BSTRec::Crosses");
            int nrows=bank_SVTCross.rows();
            int cross_row=-1;
            int sector=-1;
            int layer=-1;
            for(int row = 0; row < nrows; row++){
                if(cross_id==bank_SVTCross.getInt("ID",row)) cross_row=row;
            }
            svt_track_crosses_.get(n_track_crosses_-1).id_=cross_id;
            svt_track_crosses_.get(n_track_crosses_-1).sector_=bank_SVTCross.getInt("sector",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).region_=bank_SVTCross.getInt("region",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).x_=bank_SVTCross.getDouble("x",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).err_x_=bank_SVTCross.getDouble("err_x",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).ux_=bank_SVTCross.getDouble("ux",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).y_=bank_SVTCross.getDouble("y",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).err_y_=bank_SVTCross.getDouble("err_y",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).uy_=bank_SVTCross.getDouble("uy",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).z_=bank_SVTCross.getDouble("z",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).err_z_=bank_SVTCross.getDouble("err_z",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).uz_=bank_SVTCross.getDouble("uz",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).cluster_id_[0]=bank_SVTCross.getInt("Cluster1_ID",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).cluster_id_[1]=bank_SVTCross.getInt("Cluster2_ID",cross_row);
            svt_track_crosses_.get(n_track_crosses_-1).track_id_=id_;
            for(int i=0;i<2;++i) {
                svt_track_crosses_.get(n_track_crosses_ - 1).ReadTrackClusters(event, svt_track_crosses_.get(n_track_crosses_ - 1).cluster_id_[i], svt_trajectory_, print);
                sector = svt_track_crosses_.get(n_track_crosses_ - 1).svt_track_clusters_.get(i).sector_;
                layer = svt_track_crosses_.get(n_track_crosses_ - 1).svt_track_clusters_.get(i).layer_;
//                System.out.println("cr " + i + " " + layer + " " + sector + " " + id_ + " " + svt_trajectory_.GetIdByModule(id_, layer, sector));


                if(svt_trajectory_.GetIdByModule(id_,layer,sector)!=-1) {
                svt_track_crosses_.get(n_track_crosses_ - 1).trj_id_[i] = svt_trajectory_.GetIdByModule(id_, layer, sector);
                }
                else svt_track_crosses_.get(n_track_crosses_ - 1).trj_id_[i] = 0;
// !!!!!!!!!!!!! temporary to kludge the bug with track id in traj bank


            }




                        n_track_clusters_+=svt_track_crosses_.get(n_track_crosses_-1).n_track_clusters_;
            n_track_hits_+=svt_track_crosses_.get(n_track_crosses_-1).n_track_hits_;
            if(print) svt_track_crosses_.get(n_track_crosses_-1).Show();
        }
    }

    void ReadTrackTrajectory(EvioDataEvent event, int track_id, boolean print) {
        if(event.hasBank("BSTRec::Trajectory")){
            svt_trajectory_ = new SVTTrajectory();
            int traj_row=0;
            EvioDataBank bank_SVTTrajectory = (EvioDataBank) event.getBank("BSTRec::Trajectory");
            int nrows=bank_SVTTrajectory.rows();
            for(int row = 0; row < nrows; row++){
                if(track_id!=bank_SVTTrajectory.getInt("ID",row)||bank_SVTTrajectory.getInt("LayerTrackIntersPlane",row)==0) continue;
//                int id = svt_trajectory_.id_.get(traj_row);//=row+1;
//                svt_trajectory_.id_.set(traj_row,row+1);
                svt_trajectory_.id_.add(traj_row,row+1);
                svt_trajectory_.sector_.add(traj_row,bank_SVTTrajectory.getInt("SectorTrackIntersPlane",row));
                svt_trajectory_.layer_.add(traj_row,bank_SVTTrajectory.getInt("LayerTrackIntersPlane",row));
                svt_trajectory_.x_.add(traj_row,bank_SVTTrajectory.getDouble("XtrackIntersPlane",row));
                svt_trajectory_.y_.add(traj_row,bank_SVTTrajectory.getDouble("YtrackIntersPlane",row));
                svt_trajectory_.z_.add(traj_row,bank_SVTTrajectory.getDouble("ZtrackIntersPlane",row));
                svt_trajectory_.phi_.add(traj_row,bank_SVTTrajectory.getDouble("PhiTrackIntersPlane",row));
                svt_trajectory_.theta_.add(traj_row,bank_SVTTrajectory.getDouble("ThetaTrackIntersPlane",row));
                svt_trajectory_.angle_3d_.add(traj_row,bank_SVTTrajectory.getDouble("trkToMPlnAngl",row));
                svt_trajectory_.centroid_.add(traj_row,bank_SVTTrajectory.getDouble("CalcCentroidStrip",row));
                traj_row++;
            }
            svt_trajectory_.n_layers_=traj_row;
            svt_trajectory_.track_id_=track_id;
            if(print) svt_trajectory_.Show();
        }
    }

    public void AddTrackCross() {
        SVTCross svtCross = new SVTCross();
        svt_track_crosses_.add(svtCross);
    }

    void Show() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        System.out.print("Track " + id_ + " " + nf.format(trkline_yx_slope_) + " " + nf.format(trkline_yx_interc_) + " " + nf.format(trkline_yz_slope_) + " " +
                nf.format(trkline_yz_interc_) + " " + nf.format(theta_) + " " + nf.format(phi_) + " " + nf.format(kf_chi2_) + " " + kf_ndf_ + " crId ");
        for(int i=0;i<Constants.NLAYERS;++i) System.out.print(cross_id_[i] + " ");
        System.out.println( " ncr " + n_track_crosses_ + " ncl " + n_track_clusters_ + " nh " + n_track_hits_);
    }
}
