package org.jlab.svt.validation;

public class SVTHit {
    int id;
    int sector;
    int layer;
    int strip;
    double fitResidual;
    int trkingStat;
    int clusterId;
    int adc;
    int bco;
    int hitN;
//    int clusterId;
    int crossId;
    int trjId;
    int trackId;

    SVTHit() {
        id =-1;
        sector =-1;
        layer =-1;
        strip =-1;
        fitResidual =-1;
        trkingStat =-1;
        adc =-1;
        bco =-1;
        hitN =-1;
        clusterId =-1;
        crossId =-1;
        trjId =-1;
        trackId =-1;
    }

    SVTHit GetBySensor(int layer, int sector) {
        if(this.layer ==layer&& this.sector ==sector) return this;
        else return null;
    }

    void Show() {
        System.out.println("Hit " + id + " " + sector + " " + layer + " " + strip + " " + fitResidual + " " +
                trkingStat + " " + adc + " " + bco + " " + hitN + " clId " + clusterId + " crId " +
                crossId + " trId " + trackId);
    }
}
