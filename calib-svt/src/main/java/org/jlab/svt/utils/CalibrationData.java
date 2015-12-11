/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.svt.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jlab.clas.detector.DetectorDescriptor;

import org.root.base.IDataSet;
import org.root.func.F1D;
import org.root.histogram.GraphErrors;

/**
 *
 * @author gavalian
 */
public class CalibrationData {
    
    DetectorDescriptor desc = new DetectorDescriptor();
    
    private List<GraphErrors>  graphs     = new ArrayList<GraphErrors>();
    private List<Integer>      amplitudes = new ArrayList<Integer>();
    private List<F1D>          functions  = new ArrayList<F1D>();
    private GraphErrors        resGraph   = null;
    private F1D                resFunc    = new F1D("p1");
    public static final double MVDAC = 3.5;
    public static final double MVFC = 25.0;
    public static final double EFC = 6250.0;
    public static final double MAX_COUNTS = 3058.0;
    public static final String histoTitle="BCO 128 ns, BLR on, low gain, 125 ns";


    public CalibrationData(int sector, int layer, int component){
        this.desc.setSectorLayerComponent(sector, layer, component);
    }
    
    public DetectorDescriptor getDescriptor(){ return this.desc;}
    
    public void addGraph(int amplitude, int offset, double[] points){
        double[] xp = new double[points.length];
        double[] yp = new double[points.length]; // normalized occupancy
        for(int loop = 0; loop < points.length; loop++){
            xp[loop] = offset + loop;
            yp[loop] = points[loop]/MAX_COUNTS;
        }

        GraphErrors  graph = new GraphErrors(xp,yp);
        graph.setTitle(histoTitle);
        graph.setXTitle("DAC threshold");
        graph.setYTitle("Occupancy");
        this.graphs.add(graph);
        final int shift=7;
        F1D f1 = new F1D("erf",offset+shift,points.length+offset);
//        F1D f1 = new F1D("erf",offset,points.length+offset);
        this.functions.add(f1);
        this.amplitudes.add(amplitude);
    }
    
    public void analyze(){
        for(int loop = 0; loop < this.graphs.size(); loop++){
            F1D func = this.functions.get(loop);
            double [] dataY=this.graphs.get(loop).getDataY().getArray();
            double [] dataX=this.graphs.get(loop).getDataX().getArray();
            double delta=2;
            int index=0;
            for (int i = 0; i < dataX.length; i++) {
                double currentDelta=Math.abs(dataY[i]-0.5);
                if (currentDelta<delta) {
                    delta=currentDelta;
                    index=i;
                }
            }
            func.setParameter(2, dataX[index]);
            double spread=5;
            func.setParLimits(2, dataX[index]-spread, dataX[index]+spread);
//            switch (loop) {
//                case 0:
//                    func.setParameter(2, 75.0);
//                    func.setParLimits(2, 55.0, 85.0);
//                    break;
//                case 1:
//                    func.setParameter(2, 100.0);
//                    func.setParLimits(2, 80.0, 115.0);
//                    break;
//                case 2:
//                    func.setParameter(2, 125.0);
//                    func.setParLimits(2, 110.0, 135.0);
//                    break;
//            }
            func.setParameter(0, 0.0);
            func.setParameter(1, 0.5);
            func.setParameter(3, 7.0);
            
            func.setParLimits(0, -0.2, 0.2);
            func.setParLimits(1, 0.4, 0.6);
//            func.setParLimits(1, 0.49, 0.51);
            func.setParLimits(3, 1.0, 10.0);
            this.graphs.get(loop).fit(this.functions.get(loop));
        }
        
        double[] resX = new double[this.graphs.size()];
        double[] resY = new double[this.graphs.size()];
        
        for(int loop = 0; loop < this.graphs.size(); loop++){
//            resX[loop] = this.graphs.get(loop).getDataX().getValue(0);
            resX[loop] = this.amplitudes.get(loop)/MVFC;
            resY[loop] = this.functions.get(loop).getParameter(2)*MVDAC;
        }
        
        this.resFunc.setRange(2.0,6.0);

        this.resGraph = new GraphErrors();
        this.resGraph.setTitle(histoTitle);
        this.resGraph.setXTitle("Input charge, fC");
        this.resGraph.setYTitle("Output mean, mV");
        for(int loop = 0; loop < 3; loop++){
//            resGraph.add(this.graphs.get(loop).getDataX().getValue(0),
            resGraph.add(this.amplitudes.get(loop)/MVFC,
                    this.functions.get(loop).getParameter(2)*MVDAC);
//            System.out.println("->"+this.graphs.get(loop).getDataX().getValue(0)+" "+
//                    this.functions.get(loop).getParameter(3));
                    /*
                    , 0.0, 
                    Math.abs(this.functions.get(loop).parameter(2).error()));
                            */
        }
        this.resGraph.fit(resFunc);        
    }

    public GraphErrors  getGraph(int index){ return this.graphs.get(index);}
    public Integer      getAmplitude(int index){ return this.amplitudes.get(index);}
    public F1D          getFunc(int index){ return this.functions.get(index);}
    public GraphErrors  getResGraph(){ return this.resGraph;}
    public F1D          getResFunc(){ return this.resFunc;}
}
