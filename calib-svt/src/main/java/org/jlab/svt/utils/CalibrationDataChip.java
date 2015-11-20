/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.svt.utils;

import java.util.List;
import org.jlab.clas.detector.ConstantsTable;
import org.jlab.clas.detector.DetectorCollection;
import org.jlab.clas.detector.DetectorDescriptor;
import org.jlab.clas.detector.DetectorType;
import org.jlab.clas.tools.benchmark.BenchmarkTimer;
import org.jlab.io.decode.AbsDetectorTranslationTable;
import org.root.histogram.GraphErrors;
import org.root.histogram.H1D;
import org.root.pad.TGCanvas;

/**
 *
 * @author gavalian
 */
public class CalibrationDataChip {
    
    DetectorCollection<CalibrationData>  collection = new DetectorCollection<CalibrationData>();
    ConstantsTable                       dataTable  = new ConstantsTable(DetectorType.BST,
            new String[]{"Pulse 1","Pulse 2", "Pulse 3","Slope"} );
    
    public DetectorDescriptor    detectorDescriptor = new DetectorDescriptor(DetectorType.BST);
    public GraphErrors           chipDataSlopes     = new GraphErrors();
    public H1D                  chipDataSlopesProj  = new H1D("h1","",100,0.0,1.4);
    public AbsDetectorTranslationTable   trTable = null;
    
    int    CHIP = 0;
    int    CHAN = 0;
    
    public CalibrationDataChip(){
        //this.detectorDescriptor.setSectorLayerComponent(sector, layer, 0);
    }
    
    public CalibrationDataChip(AbsDetectorTranslationTable t){
        //this.detectorDescriptor.setSectorLayerComponent(sector, layer, 0);
        this.trTable = t;
    }
    
    public void addData(CalibrationData data){
        this.collection.add(data.getDescriptor(), data);
    }
    
    public CalibrationData getData(int sector, int layer, int component){
        return this.collection.get(sector, layer, component);
    }
    
    public DetectorCollection<CalibrationData> getCollection(){ return this.collection;}

    public void analyze(){
        BenchmarkTimer  timer = new BenchmarkTimer("FIT-ANALYSIS");

        List<CalibrationData> dataList = this.collection.getList();
        for(CalibrationData data : dataList){
            timer.resume();
            data.analyze();
            
            this.dataTable.addEntry(data.getDescriptor().getSector(), 
                    data.getDescriptor().getLayer(),data.getDescriptor().getComponent());
            
            this.dataTable.getEntry(
                    data.getDescriptor().getSector(), 
                    data.getDescriptor().getLayer(),
                    data.getDescriptor().getComponent()).setData(
                            0, data.getFunc(0).getParameter(2));
            this.dataTable.getEntry(
                    data.getDescriptor().getSector(), 
                    data.getDescriptor().getLayer(),
                    data.getDescriptor().getComponent()).setData(1, data.getFunc(1).getParameter(2));
            this.dataTable.getEntry(
                    data.getDescriptor().getSector(), 
                    data.getDescriptor().getLayer(),
                    data.getDescriptor().getComponent()).setData(2, data.getFunc(2).getParameter(2));
            this.dataTable.getEntry(
                    data.getDescriptor().getSector(), 
                    data.getDescriptor().getLayer(),
                    data.getDescriptor().getComponent()).setData(3, data.getResFunc().getParameter(1));
            timer.pause();
        }
        
        System.out.println(timer);
        int counter = 1;
        for(CalibrationData data : dataList){
            this.chipDataSlopes.add(counter, data.getResFunc().getParameter(1));
            this.chipDataSlopesProj.fill(data.getResFunc().getParameter(1));
            counter++;
        }
    }
    
    public GraphErrors  getGraph(){return this.chipDataSlopes;}
    public H1D          getHisto(){return this.chipDataSlopesProj;}
    
    public void updateDescriptor(String str){
        String[] tokens = new String[4];
        int index = str.length() - 15;
        tokens[0] = str.substring(index, index+1);
        tokens[1] = str.substring(index+8, index+9);
        tokens[2] = str.substring(index+11, index+13);
        tokens[3] = str.substring(index+14, index+15);
        
        this.CHIP = Integer.parseInt(tokens[1]);
        this.CHAN = Integer.parseInt(tokens[3]);
        
        this.detectorDescriptor.setCrateSlotChannel(
                Integer.parseInt(tokens[0].trim()),Integer.parseInt(tokens[2].trim()),
                Integer.parseInt(tokens[1])*10 + Integer.parseInt(tokens[3]));                
    }
    
    public ConstantsTable getConstantsTable(){
        return this.dataTable;
    }
    
    public void readData(String file){
        CalibrationFile cf = new CalibrationFile();
        this.updateDescriptor(file);
        System.out.println(" FILE : " + file);
        System.out.println("\t\t ->  " + this.detectorDescriptor);
        
        cf.read(file);
        for(CalibrationData data : cf.getDataList()){
            //System.out.println("adding data");
            int crate  = this.detectorDescriptor.getCrate();
            int slot   = this.detectorDescriptor.getSlot();
            int sector = this.trTable.getSector(crate, slot , 0);
            data.getDescriptor().setSectorLayerComponent(sector,
                    this.CHIP*10+this.CHAN, data.getDescriptor().getComponent());
            this.detectorDescriptor.setSectorLayerComponent(sector, this.CHIP*10+this.CHAN, 0);
            this.collection.add(data.getDescriptor(), data);
        }
    }
    
    public static void main(String[] args){
        AbsDetectorTranslationTable  tr = new AbsDetectorTranslationTable();
        tr.readFile("/Users/gavalian/Work/Software/Release-8.0/COATJAVA/coatjava/etc/bankdefs/translation/SVT.table");
        
        CalibrationDataChip store = new CalibrationDataChip(tr);
        store.readData("/Users/gavalian/Work/Software/Release-8.0/SVT/calib-svt/../101/2/scan_u2_s15c1");
        store.collection.show();
        /*
        TGCanvas  canvas = new TGCanvas("c1","SVT",800,900,2,2);
        store.getCollection().show();
        
        store.getCollection().get(1, 1, 10).analyze();
        
        for(int pad = 0; pad < 3; pad++){
            canvas.cd(pad);
            canvas.draw(store.getCollection().get(1, 1, 10).getGraph(pad));
            canvas.draw(store.getCollection().get(1, 1, 10).getFunc(pad),"same");
            
        }
        canvas.cd(3);
        canvas.draw(store.getCollection().get(1,1,10).getResGraph());
        canvas.draw(store.getCollection().get(1,1,10).getResFunc(),"same");
        */      
    }
}
