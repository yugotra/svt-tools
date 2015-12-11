/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.svt.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Vector;

import org.jlab.clas.detector.ConstantsTable;
import org.jlab.clas.detector.DetectorCollection;
import org.jlab.clas.detector.DetectorDescriptor;
import org.jlab.clas.detector.DetectorType;
import org.jlab.clas.tools.benchmark.BenchmarkTimer;
import org.jlab.io.decode.AbsDetectorTranslationTable;
import org.root.histogram.GraphErrors;
import org.root.histogram.H1D;

/**
 *
 * @author gavalian
 */
public class CalibrationDataChip {
    
    DetectorCollection<CalibrationData>  collection = new DetectorCollection<CalibrationData>();
    ConstantsTable                       dataTable  = new ConstantsTable(DetectorType.BST,
            new String[]{"ENC1, e","ENC2, e", "ENC3, e","Gain, mV/fC"} );
    
    public DetectorDescriptor    detectorDescriptor = new DetectorDescriptor(DetectorType.BST);
    public GraphErrors           chipDataOffset = new GraphErrors();
    public GraphErrors           chipDataVt50 = new GraphErrors();
    public GraphErrors           chipDataGain = new GraphErrors();
    public GraphErrors           chipDataEnc = new GraphErrors();
    public H1D                   chipDataGainProj  = new H1D("h1","",100,0.0,110.0);
    public H1D                   chipDataThresholdProj  = new H1D("ht","",100,17000.0,33000.0);
    public AbsDetectorTranslationTable   trTable = null;

    int    CHIP = 0;
    int    CHAN = 0;
    
    public CalibrationDataChip(){
        //this.detectorDescriptor.setSectorLayerComponent(sector, layer, 0);
        chipDataOffset.setTitle(CalibrationData.histoTitle);
        chipDataOffset.setXTitle("Channel");
        chipDataOffset.setYTitle("Offset, mV");
        chipDataVt50.setTitle(CalibrationData.histoTitle);
        chipDataVt50.setXTitle("Channel");
        chipDataVt50.setYTitle("Vt_50, mV");
        chipDataGain.setTitle(CalibrationData.histoTitle);
        chipDataGain.setXTitle("Channel");
        chipDataGain.setYTitle("Gain, mV/fC");
        chipDataEnc.setTitle(CalibrationData.histoTitle);
        chipDataEnc.setXTitle("Channel");
        chipDataEnc.setYTitle("ENC, e");
        chipDataGainProj.setTitle(CalibrationData.histoTitle);
        chipDataGainProj.setXTitle("Gain, mV/fC");
        chipDataGainProj.setYTitle("Entries");
        chipDataThresholdProj.setTitle(CalibrationData.histoTitle);
        chipDataThresholdProj.setXTitle("Threshold, e");
        chipDataThresholdProj.setYTitle("Entries");
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
//        NumberFormat nf = NumberFormat.getInstance();
//        nf.setMaximumFractionDigits(1);
        DecimalFormat nf = new DecimalFormat("####");
        nf.setRoundingMode(RoundingMode.CEILING);
        Vector<String> a = new Vector<String>();
//        a.add("a");
        int numColumns=dataTable.getColumnCount();
        for (int i = 0; i <numColumns; i++) {
        a.add(dataTable.getColumnName(i));
        }
        a.set(3,"Channel");
        dataTable.setColumnIdentifiers(a);
        for(CalibrationData data : dataList){
            timer.resume();
            data.analyze();
            Double enc1=CalibrationData.MVDAC*CalibrationData.EFC*data.getFunc(0).getParameter(3)/data.getResFunc().getParameter(1);
            Double enc2=CalibrationData.MVDAC*CalibrationData.EFC*data.getFunc(1).getParameter(3)/data.getResFunc().getParameter(1);
            Double enc3=CalibrationData.MVDAC*CalibrationData.EFC*data.getFunc(2).getParameter(3)/data.getResFunc().getParameter(1);

            this.dataTable.addEntry(data.getDescriptor().getSector(),
                    data.getDescriptor().getLayer(),data.getDescriptor().getComponent());
            this.dataTable.getEntry(
                    data.getDescriptor().getSector(),
                    data.getDescriptor().getLayer(),
//                    data.getDescriptor().getComponent()).setData(0, Double.parseDouble(nf.format(data.getFunc(0).getParameter(2)*CalibrationData.MVDAC)));
                    data.getDescriptor().getComponent()).setData(0, Double.parseDouble(nf.format(enc1)));
            this.dataTable.getEntry(
                    data.getDescriptor().getSector(), 
                    data.getDescriptor().getLayer(),
//                    data.getDescriptor().getComponent()).setData(1, Double.parseDouble(nf.format(data.getFunc(1).getParameter(2)*CalibrationData.MVDAC)));
                    data.getDescriptor().getComponent()).setData(1, Double.parseDouble(nf.format(enc2)));
            this.dataTable.getEntry(
                    data.getDescriptor().getSector(), 
                    data.getDescriptor().getLayer(),
//                    data.getDescriptor().getComponent()).setData(2, Double.parseDouble(nf.format(data.getFunc(2).getParameter(2)*CalibrationData.MVDAC)));
                    data.getDescriptor().getComponent()).setData(2, Double.parseDouble(nf.format(enc3)));
            this.dataTable.getEntry(
                    data.getDescriptor().getSector(), 
                    data.getDescriptor().getLayer(),
                    data.getDescriptor().getComponent()).setData(3, Double.parseDouble(nf.format(data.getResFunc().getParameter(1))));
            timer.pause();
        }
        
//        System.out.println(timer);
        int counter = 1;
        for(CalibrationData data : dataList){
            this.chipDataOffset.add(counter, data.getResFunc().getParameter(0));
            this.chipDataVt50.add(counter, data.getFunc(1).getParameter(2)*CalibrationData.MVDAC);
            this.chipDataGain.add(counter, data.getResFunc().getParameter(1));
            this.chipDataEnc.add(counter, CalibrationData.MVDAC*CalibrationData.EFC*data.getFunc(1).getParameter(3)/data.getResFunc().getParameter(1));
            this.chipDataGainProj.fill(data.getResFunc().getParameter(1));
            this.chipDataThresholdProj.fill(data.getFunc(1).getParameter(2)*CalibrationData.MVDAC*CalibrationData.EFC/data.getResFunc().getParameter(1));
            counter++;
        }
    }
    
    public GraphErrors getGraph(){return this.chipDataGain;}
    public GraphErrors getEncGraph(){return this.chipDataEnc;}
    public GraphErrors  getOffsetGraph(){return this.chipDataOffset;}
    public GraphErrors  getVt50Graph(){return this.chipDataVt50;}
    public H1D getHistoGain(){return this.chipDataGainProj;}
    public H1D          getHistoThreshold(){return this.chipDataThresholdProj;}

    public void updateDescriptor(String str){
        String[] tokens = new String[4];
//        int index = str.length() - 15;
//        tokens[0] = str.substring(index, index+1); // crate
//        tokens[1] = str.substring(index+8, index+9); // chip
//        tokens[2] = str.substring(index+11, index+13); // slot
//        tokens[3] = str.substring(index+14, index+15); // vscm channel
        int index = str.length() - 11;
        tokens[0] = str.substring(index, index+1); // crate
        tokens[1] = str.substring(index+3, index+5); // slot
        tokens[2] = str.substring(index+7, index+8); // vscm channel
        tokens[3] = str.substring(index+10, index+11); // chip

//        this.CHIP = Integer.parseInt(tokens[1]);
//        this.CHAN = Integer.parseInt(tokens[3]);
        this.CHIP = Integer.parseInt(tokens[3]);
        this.CHAN = Integer.parseInt(tokens[2]);

        this.detectorDescriptor.setCrateSlotChannel(
//                Integer.parseInt(tokens[0].trim()),Integer.parseInt(tokens[2].trim()),
//                Integer.parseInt(tokens[1])*10 + Integer.parseInt(tokens[3]));
                Integer.parseInt(tokens[0].trim()),Integer.parseInt(tokens[1].trim()),
//                Integer.parseInt(tokens[3])*10 + Integer.parseInt(tokens[2]));
                this.CHIP*10 + this.CHAN);
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
        int crate  = this.detectorDescriptor.getCrate();
        int slot   = this.detectorDescriptor.getSlot();
        int sector = this.trTable.getSector(crate, slot , 0);
        this.detectorDescriptor.setSectorLayerComponent(sector, this.CHIP*10+this.CHAN, 0);
        System.out.println("crate "+crate+" slot "+slot+" sector "+sector+" chip "+this.CHIP+" chan "+this.CHAN);
        for(CalibrationData data : cf.getDataList()){
            //System.out.println("adding data");
            data.getDescriptor().setType(DetectorType.BST);
            data.getDescriptor().setSectorLayerComponent(sector,
                    this.CHIP*10+this.CHAN, data.getDescriptor().getComponent());
            this.collection.add(data.getDescriptor(), data);
            System.out.println(data.desc);
        }
    }
    
    public static void main(String[] args){
        AbsDetectorTranslationTable  tr = new AbsDetectorTranslationTable();
        tr.readFile("/Volumes/data/work/coatjava/etc/bankdefs/translation/SVT.table");
        
        CalibrationDataChip store = new CalibrationDataChip(tr);
//        store.readData("/Volumes/data/work/pscan/101/2/scan_u2_s15c1");
        store.readData("/Volumes/data/work/pscan/test/svt2_s03_c1_u1");
//        store.readData("/Volumes/data/work/pscan/20151123_1551/svt7_s03_c2_u4");
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
