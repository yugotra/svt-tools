package org.jlab.svt.validation;

public class SVTHit {
    int id_;
    int sector_;
    int layer_;
    int strip_;
    double fit_residual_;
    int trking_stat_;
    int cluster_id_;
    int adc_;
    int bco_;
    int hit_n_;
    int cluster_id;
    int cross_id_;
    int trj_id_;
    int track_id_;

    SVTHit() {
        id_=-1;
        sector_=-1;
        layer_=-1;
        strip_=-1;
        fit_residual_=-1;
        trking_stat_=-1;
        adc_=-1;
        bco_=-1;
        hit_n_=-1;
        cluster_id_=-1;
        cross_id_=-1;
        trj_id_=-1;
        track_id_=-1;
    }

    SVTHit GetBySensor(int layer, int sector) {
        if(layer_==layer&&sector_==sector) return this;
        else return null;
    }

    void Show() {
        System.out.println("Hit " + id_ + " " + sector_ + " " + layer_ + " " + strip_ + " " + fit_residual_ + " " +
                trking_stat_ + " " + adc_ + " " + bco_ + " " + hit_n_ + " clId " + cluster_id_ + " crId " +
                cross_id_ + " trId " + track_id_);
    }
}
