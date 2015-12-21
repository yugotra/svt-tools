package org.jlab.svt.validation;

public class SVTDgtz {
    int id;
    int sector;
    int layer;
    int strip;
    int adc;
    int bco;
    int hitN;
    int clusterId;
    int trackId;

    SVTDgtz() {
        id =-1;
        sector =-1;
        layer =-1;
        strip =-1;
        adc =-1;
        bco =-1;
        hitN =-1;
        clusterId =-1;
        trackId =-1;
    }

    SVTDgtz GetBySensor(int layer, int sector) {
        if(this.layer ==layer&& this.sector ==sector) return this;
        else return null;
    }

    void Show() {
        System.out.println("dgtz " + id + " " + layer + " " + sector + " " + strip + " " + adc + " " + bco +
                " " + hitN + " " + clusterId + " " + trackId);
    }
}
