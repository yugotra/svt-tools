/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.svt.utils;

import java.io.File;
import java.util.List;
import org.jlab.clas.detector.DetectorCollection;
import org.jlab.clas.tools.benchmark.BenchmarkTimer;

import org.jlab.clas.tools.utils.FileUtils;
import org.jlab.io.decode.AbsDetectorTranslationTable;
import org.root.fitter.DataFitter;
/**
 *
 * @author gavalian
 */
public class CalibrationStore {
    DetectorCollection<CalibrationDataChip>  calibrationStore = new DetectorCollection<CalibrationDataChip>();
    AbsDetectorTranslationTable                 translationTable = new AbsDetectorTranslationTable();
    
    public CalibrationStore(){
        translationTable.readFile("/Volumes/data/work/coatjava/etc/bankdefs/translation/SVT.table");
        System.out.println(this.translationTable.toString());
        
    }
    
    
    public DetectorCollection<CalibrationDataChip> getChips(){ return this.calibrationStore;}
    
    
    public void readData(String directory){
        List<String> dirFiles = FileUtils.getFilesInDir(directory);

        System.out.println("-----> found files : " + dirFiles.size());
        int icounter = 0;
        for(String f : dirFiles){
//            if(f.contains("scan_")){
            if(f.contains("svt")){
                icounter++;
                if(icounter<80){ // TEMPORARY debugging
//                if(icounter<8000){ // TEMPORARY debugging
                    System.out.println("----> name : " + f);
                    CalibrationDataChip store = new CalibrationDataChip(this.translationTable);                
                    store.readData(f);
                    this.calibrationStore.add(store.detectorDescriptor, store);
                }
            }
        }
        
//        this.calibrationStore.show();
        
    }
    
    public void analyze(){
        BenchmarkTimer analysisTimer = new BenchmarkTimer("FULL-ANALYSIS");

        
        List<CalibrationDataChip>  Stores = this.calibrationStore.getList();
        for(CalibrationDataChip store : Stores){
            analysisTimer.resume();
            store.analyze();
            analysisTimer.pause();
        }

        System.out.println(analysisTimer);
    }
    public static void main(String[] args){
//        DataFitter.FITPRINTOUT = false;
//        CalibrationStore sectors = new CalibrationStore();
        //CalibrationDataChip  chip = new CalibrationDataChip();
        //chip.readData("/Volumes/data/work/pscan/101/2/scan_u1_s03c1");
        //chip.collection.show();
//        sectors.readData("/Volumes/data/work/pscan/test");
//        sectors.readData("/Volumes/data/work/pscan/20151123_1551");
        //sectors.analyze();
    }
}
