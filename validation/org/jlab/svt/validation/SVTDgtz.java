package org.jlab.svt.validation;

public class SVTDgtz {
    int id_;
    int sector_;
    int layer_;
    int strip_;
    int adc_;
    int bco_;
    int hit_n_;
    int cluster_id_;
    int track_id_;

    SVTDgtz() {
        id_=-1;
        sector_=-1;
        layer_=-1;
        strip_=-1;
        adc_=-1;
        bco_=-1;
        hit_n_=-1;
        cluster_id_=-1;
        track_id_=-1;
    }

    SVTDgtz GetBySensor(int layer, int sector) {
        if(layer_==layer&&sector_==sector) return this;
        else return null;
    }

    void Show() {
        System.out.println("dgtz " + id_ + " " + layer_ + " " + sector_ + " " + strip_ + " " + adc_ + " " + bco_ +
                " " + hit_n_ + " " + cluster_id_ + " " + track_id_);
    }
}
