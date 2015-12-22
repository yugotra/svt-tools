/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.svt.calib;

import java.util.List;
import org.jlab.clas.detector.DetectorType;
import org.jlab.clas12.calib.DetectorShape2D;
import org.jlab.clas12.calib.DetectorShapeView2D;
import org.jlab.clasrec.utils.CLASGeometryLoader;

/**
 *
 * @author gavalian
 */
public class GeometrySVT {
    public static DetectorShapeView2D getDetectorView(){
        DetectorShapeView2D view = new DetectorShapeView2D("BST");
        List<DetectorShape2D> shapes = CLASGeometryLoader.getDetectorShape2D(DetectorType.BST);        
        for(DetectorShape2D shape : shapes) view.addShape(shape);
        return view;
    }
}
