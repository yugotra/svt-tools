/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.svt.utils;

import java.util.ArrayList;
import java.util.List;
import org.jlab.clas.detector.DetectorDescriptor;

import org.root.func.F1D;
import org.root.histogram.GraphErrors;

/**
 *
 * @author gavalian
 */
public class CalibrationData {
    
    DetectorDescriptor desc = new DetectorDescriptor();
    
    private List<GraphErrors>  graphs    = new ArrayList<GraphErrors>();
    private List<F1D>          functions = new ArrayList<F1D>();
    private GraphErrors        resGraph  = null;
    private F1D                resFunc   = new F1D("p1");
    
    public CalibrationData(int sector, int layer, int component){
        this.desc.setSectorLayerComponent(sector, layer, component);
    }
    
    public DetectorDescriptor getDescriptor(){ return this.desc;}
    
    public void addGraph(int offset, double[] points){
        double[] xp = new double[points.length];
        for(int loop = 0; loop < points.length; loop++){
            xp[loop] = offset + loop;
        }
        
        GraphErrors  graph = new GraphErrors(xp,points);
        
        this.graphs.add(graph);
        F1D f1 = new F1D("erf",offset,points.length+offset);
        this.functions.add(f1);
    }
    
    public void analyze(){
        for(int loop = 0; loop < this.graphs.size(); loop++){
            F1D func = this.functions.get(loop);
            func.setParameter(0, 1500);
            func.setParameter(1, 1500.0);
            func.setParameter(2, 70.0);
            func.setParameter(3, 0.1);
            
            func.setParLimits(2, 0.0, 150.0);
            func.setParLimits(3, 0.0001, 1.2);
            this.graphs.get(loop).fit(this.functions.get(loop));
        }
        
        double[] resX = new double[this.graphs.size()];
        double[] resY = new double[this.graphs.size()];
        
        for(int loop = 0; loop < this.graphs.size(); loop++){
            resX[loop] = this.graphs.get(loop).getDataX().getValue(0);
            resY[loop] = this.functions.get(loop).getParameter(2);
        }
        
        this.resFunc.setRange(15,85);

        this.resGraph = new GraphErrors();        
        for(int loop = 0; loop < 3; loop++){
            resGraph.add(this.graphs.get(loop).getDataX().getValue(0), 
                    this.functions.get(loop).getParameter(2));;
                    /*
                    , 0.0, 
                    Math.abs(this.functions.get(loop).parameter(2).error()));
                            */
        }
        this.resGraph.fit(resFunc);        
    }
    
    public GraphErrors  getGraph(int index){ return this.graphs.get(index);}
    public F1D          getFunc(int index){ return this.functions.get(index);}
    public GraphErrors  getResGraph(){ return this.resGraph;}
    public F1D          getResFunc(){ return this.resFunc;}
}
