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
        for(int ring = 0; ring < 4; ring++){
            for(int sector = 0; sector < sectors[ring]; sector++){
                double rotation = sector*360.0/sectors[ring];
                for(int layer = 0; layer < 2; layer++){
                    DetectorShape2D  shape_CH0 = new DetectorShape2D(
                            DetectorType.BST,sector+1,(layer+1),1);
                    shape_CH0.createBarXY(30.0, 8.0);
                    shape_CH0.getShapePath().translateXYZ(15,0.0,0.0);
                    shape_CH0.getShapePath().translateXYZ(0, distances[ring] + 10*layer, 0.0);
                    if(layer%2==0){
                        shape_CH0.setColor(180, 180, 255);
                    } else {
                        shape_CH0.setColor(180, 255, 180);
                    }
                    shape_CH0.getShapePath().rotateZ(Math.toRadians(rotation));

                    DetectorShape2D  shape_CH1 = new DetectorShape2D(
                    DetectorType.BST,sector+1,(layer+1),2);
                    shape_CH1.createBarXY(30.0, 8.0);
                    shape_CH1.getShapePath().translateXYZ(-15,0.0,0.0);
                    shape_CH1.getShapePath().translateXYZ(0, distances[ring] + 10*layer, 0.0);
                    if(layer%2==0){
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
