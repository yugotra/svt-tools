/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.svt.calib;

import org.jlab.clas.detector.DetectorType;
import org.jlab.clas12.calib.DetectorShape2D;
import org.jlab.clas12.calib.DetectorShapeView2D;

/**
 *
 * @author gavalian
 */
public class GeometrySVT {
    public static DetectorShapeView2D getDetectorView(){
        DetectorShapeView2D view = new DetectorShapeView2D("BST");
        int[] sectors = new int[]{10,14,18,24};
        double[] distances = new double[]{120.0,160.0,200.0,240.0};
        for(int region = 0; region < 4; region++){
            for(int sector = 0; sector < sectors[region]; sector++){
                double rotation = sector*360.0/sectors[region];
                for(int side = 0; side < 2; side++){
                    int layer = 2*(region+1)-(side==0 ? 1 : 0);
                    int chip = (layer == 0 ? 2 : 1);
                    DetectorShape2D  shape_CH0 = new DetectorShape2D(
                            DetectorType.BST,sector+1,layer,chip);
//                            DetectorType.BST,sector+1,(side+1),2);
//                            DetectorType.BST,sector,(region+1)*10+(side+1),0);
                    shape_CH0.createBarXY(30.0, 8.0);
                    shape_CH0.getShapePath().translateXYZ(15,0.0,0.0);
                    shape_CH0.getShapePath().translateXYZ(0, distances[region] + 10*side, 0.0);
                    if(side%2==0){
                        shape_CH0.setColor(180, 180, 255);
                    } else {
                        shape_CH0.setColor(180, 255, 180);
                    }
                    shape_CH0.getShapePath().rotateZ(Math.toRadians(rotation));
                    chip = (layer == 0 ? 1 : 2);
                    DetectorShape2D  shape_CH1 = new DetectorShape2D(
                    DetectorType.BST,sector+1,layer,chip);
//                    DetectorType.BST,sector+1,(side+1),1);
                    shape_CH1.createBarXY(30.0, 8.0);
                    shape_CH1.getShapePath().translateXYZ(-15,0.0,0.0);
                    shape_CH1.getShapePath().translateXYZ(0, distances[region] + 10*side, 0.0);
                    if(side%2==0){
                        shape_CH1.setColor(180, 180, 255);
                    } else {
                        shape_CH1.setColor(180, 255, 180);
                    }
                    shape_CH1.getShapePath().rotateZ(Math.toRadians(rotation));

                    view.addShape(shape_CH0);
                    view.addShape(shape_CH1);
                }
            }
        }
        return view;
    }
}
