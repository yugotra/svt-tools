/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.svt.calib;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jlab.clas.detector.ConstantsTablePanel;
import org.jlab.clas.detector.DetectorDescriptor;
import org.jlab.clas.detector.IConstantsTableListener;
import org.jlab.clas12.calib.CalibrationPane;
import org.jlab.clas12.calib.DetectorShape2D;
import org.jlab.clas12.calib.DetectorShapeView2D;
import org.jlab.clas12.calib.IDetectorListener;
import org.jlab.svt.utils.CalibrationData;
import org.jlab.svt.utils.CalibrationDataChip;
import org.jlab.svt.utils.CalibrationStore;
import org.root.fitter.DataFitter;
import org.root.func.F1D;
import org.root.histogram.GraphErrors;
import org.root.histogram.H1D;
import org.root.histogram.PaveText;
import org.root.pad.EmbeddedCanvas;

/**
 *
 * @author gavalian
 */
public class CalibrationSVT implements IDetectorListener,
        IConstantsTableListener,ActionListener{

    
    private EmbeddedCanvas   canvas = new EmbeddedCanvas();    
    private EmbeddedCanvas   canvas1 = new EmbeddedCanvas();
    private CalibrationPane  calibPane = new CalibrationPane();
    //CalibrationDataChip         calibStore = new CalibrationDataChip();
    CalibrationStore              calibStore = new CalibrationStore();
    
    private ConstantsTablePanel   constantsTablePanel = null;
    private JButton buttonSVT;
        
    public CalibrationSVT(){
        this.initDetector();
        this.init();
        
        //this.calibStore.analyze();
    }
    
    public void init(){
        this.calibPane.getCanvasPane().add(canvas);

        JButton button = new JButton("SVT");
        button.addActionListener(this);
        this.calibPane.getBottonPane().add(button);
        JButton buttonR1 = new JButton("Region 1");
        buttonR1.addActionListener(this);
        this.calibPane.getBottonPane().add(buttonR1);
        JButton buttonR2 = new JButton("Region 2");
        buttonR2.addActionListener(this);
        this.calibPane.getBottonPane().add(buttonR2);
        JButton buttonR3 = new JButton("Region 3");
        buttonR3.addActionListener(this);
        this.calibPane.getBottonPane().add(buttonR3);
        JButton buttonR4 = new JButton("Region 4");
        buttonR4.addActionListener(this);
        this.calibPane.getBottonPane().add(buttonR4);
        this.buttonSVT = new JButton("SVT");
        this.buttonSVT.addActionListener(this);
        //this.constantsTablePanel = new ConstantsTablePanel();
        //this.constantsTablePanel.addListener(this);        
        //this.calibPane.getTablePane().add(this.constantsTablePanel);
    }
    
    public void initDetector(){
        
        DetectorShapeView2D view = GeometrySVT.getDetectorView();
        view.addDetectorListener(this);
        this.calibPane.getDetectorView().addDetectorLayer(view);
//        this.calibPane.getDetectorView().add(this.buttonSVT);
//        this.calibPane.getDetectorView().add(this.canvas1);
    }
    
    public void detectorSelected(DetectorDescriptor dd) {
        int sector = dd.getSector();
        int layer  = dd.getLayer();
        CalibrationDataChip chip = this.calibStore.getChips().get(sector, layer, 0);
        
        System.out.println("SELECTED : " + dd.toString());
        if(chip!=null){
            this.canvas.divide(3, 2);
            this.canvas.cd(0);
            GraphErrors graphEnc = chip.getEncGraph();
            graphEnc.setTitle(CalibrationData.histoTitle);
            graphEnc.setXTitle("Channel");
            graphEnc.setYTitle("ENC, e");
            graphEnc.setFillColor(4);
            graphEnc.setMarkerSize(6);
//            graphEnc.show();
            this.canvas.getPad().setAxisRange(-1.0, 129, 0.0, 2500.0);
            this.canvas.draw(graphEnc);
            PaveText infoEnc = new PaveText(0.05,0.95);
            infoEnc.setFontSize(14);
            infoEnc.addText("mean: " + String.format("%2.0f mV/fC,",graphEnc.getMean()) + " RMS: " + String.format("%2.0f",graphEnc.getRMS()));
//            this.canvas.draw(info);

            this.canvas.cd(3);
            GraphErrors graph = chip.getGraph();
            graph.setTitle(CalibrationData.histoTitle);
            graph.setXTitle("Channel");
            graph.setYTitle("Gain, mV/fC");
            graph.setFillColor(4);
            graph.setMarkerSize(6);
//            graph.show();
            this.canvas.getPad().setAxisRange(-1.0, 129, 0.0, 110.0);
            this.canvas.draw(graph);
            PaveText info = new PaveText(0.05,0.95);
            info.setFontSize(14);
            info.addText("mean: " + String.format("%2.0f mV/fC,",graph.getMean()) + " RMS: " + String.format("%2.0f",graph.getRMS()));
//            this.canvas.draw(info);

            this.canvas.cd(1);
            this.canvas.draw(chip.getHistoGain());
            chip.getHistoGain().setLineWidth(2);
            chip.getHistoGain().setFillColor(3);
            chip.getHistoGain().setTitle(CalibrationData.histoTitle);
            chip.getHistoGain().setXTitle("Gain, mV/fC");
            chip.getHistoGain().setYTitle("Entries");
            final int GAIN_LOW=60;
            final int GAIN_HIGH=100;
            F1D fgain = new F1D("gaus",GAIN_LOW,GAIN_HIGH);
            fgain.setLineColor(2);
            fgain.setLineWidth(2);
            fgain.setParameter(1,85);
            fgain.setParameter(2,2);
            chip.getHistoGain().fit(fgain);
            this.canvas.draw(fgain,"same");

            this.canvas.cd(4);
            chip.getHistoThreshold().setLineWidth(2);
            chip.getHistoThreshold().setFillColor(3);
            this.canvas.draw(chip.getHistoThreshold());
            chip.getHistoThreshold().setTitle(CalibrationData.histoTitle);
            chip.getHistoThreshold().setXTitle("Threshold, e");
            chip.getHistoThreshold().setYTitle("Entries");
            final int Threshold_LOW=22000;
            final int Threshold_HIGH=27000;
            F1D fthreshold = new F1D("gaus",Threshold_LOW,Threshold_HIGH);
            fthreshold.setLineColor(2);
            fthreshold.setLineWidth(2);
            fthreshold.setParameter(1,24500);
            fthreshold.setParameter(2,600);
            chip.getHistoThreshold().fit(fthreshold);
            this.canvas.draw(fthreshold,"same");

            this.canvas.cd(2);
            GraphErrors graphOffset = chip.getOffsetGraph();
            graphOffset.setTitle(CalibrationData.histoTitle);
            graphOffset.setXTitle("Channel");
            graphOffset.setYTitle("Offset, mV");
            graphOffset.setFillColor(4);
            graphOffset.setMarkerSize(6);
//            graphOffset.show();
            this.canvas.getPad().setAxisRange(-1.0, 129, -100.0, 100.0);
            this.canvas.draw(graphOffset);
            PaveText infoOffset = new PaveText(0.05,0.95);
            infoOffset.setFontSize(14);
            infoOffset.addText("mean: " + String.format("%2.0f mV,",graphOffset.getMean()) + " RMS: " + String.format("%2.0f",graphOffset.getRMS()));
//            this.canvas.draw(infoOffset);

            this.canvas.cd(5);
            GraphErrors graphVt50 = chip.getVt50Graph();
            graphVt50.setTitle(CalibrationData.histoTitle);
            graphVt50.setXTitle("Channel");
            graphVt50.setYTitle("Vt_50, mV");
            graphVt50.setFillColor(4);
            graphVt50.setMarkerSize(6);
            //graphVt50.show();
            this.canvas.getPad().setAxisRange(-1.0, 129, 0.0, 400.0);
            this.canvas.draw(graphVt50);
            PaveText infoVt50 = new PaveText(0.05,0.95);
            infoVt50.setFontSize(14);
            infoVt50.addText("mean: " + String.format("%2.0f mV,",graphVt50.getMean()) + " RMS: " + String.format("%2.0f",graphVt50.getRMS()));
//            this.canvas.draw(infoVt50);

//            chip.getCollection().show();
            try {
                this.constantsTablePanel.setTable(chip.getConstantsTable());

            } catch (Exception e){
                System.out.println("-----> error accured while siwching to view : SECTOR = " 
                        + sector + "  LAYER " + layer);
            }
        }
        this.calibPane.repaint();
        //this.canvas.draw(this.calibStore.getGraph());
    }

    public void readData(String file){
//        this.calibStore.readData("/Volumes/data/work/pscan/test");
//        this.calibStore.readData("/Volumes/data/work/pscan/20151123_1551");
        this.calibStore.readData("/Volumes/data/work/pscan/20151209_1231");
        this.calibStore.analyze();
        System.out.println("analyze complete");
        List<CalibrationDataChip> chips = this.calibStore.getChips().getList();
        if(chips.size()>0){
            this.constantsTablePanel = new ConstantsTablePanel(chips.get(0).getConstantsTable());
            this.constantsTablePanel.addListener(this);        
            this.calibPane.getTablePane().add(this.constantsTablePanel);
        }
    }
    
    public void update(DetectorShape2D dsd) {
        int sector = dsd.getDescriptor().getSector();
        int layer  = dsd.getDescriptor().getLayer();
        CalibrationDataChip chip = this.calibStore.getChips().get(sector, layer, 0);
        if(chip==null){
            dsd.setColor(180, 180, 180);
        } else {
            if(layer%2==0){
                dsd.setColor(180, 255,180);
            } else {
                dsd.setColor(180, 180,255);
            }
        }
    }

    public void entrySelected(int i, int i1, int i2) {
        System.out.println("CALLBACK selecting the data " + i + " " + i1 + " " + i2);
        
        CalibrationDataChip dataChip = this.calibStore.getChips().get(i, i1, 0);
        
        if(dataChip!=null){
            CalibrationData  data = dataChip.getData(i, i1, i2);
            if(data!=null){
                this.canvas.divide(2,2);
                for(int loop = 0; loop < 3; loop++){
                    this.canvas.cd(loop);
                    data.getGraph(loop).setFillColor(2);
                    data.getGraph(loop).setLineColor(2);
                    data.getGraph(loop).setMarkerSize(6);
                    this.canvas.draw(data.getGraph(loop));
                    data.getFunc(loop).setLineWidth(2);
                    data.getFunc(loop).setLineColor(3);
                    data.getFunc(loop).setLineStyle(2);
                    
                    this.canvas.draw(data.getFunc(loop),"same");
                }
                this.canvas.cd(3);
                data.getResGraph().setFillColor(7);
                data.getResGraph().setLineWidth(2);
                data.getResGraph().setMarkerStyle(4);
                
                this.canvas.draw(data.getResGraph());
                data.getResFunc().setLineColor(1);
                data.getResFunc().setLineStyle(3);
                data.getResFunc().setLineWidth(2);
                
                
                this.canvas.draw(data.getResFunc(),"same");
            } else {
                System.out.println("Oooooops this is null");
            }
        } else {
            System.out.println("----> error finding chip");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().compareTo("SVT")==0){
            H1D svtGainHisto  = new H1D("hSvtGain","",100,0.0,110.0);
            svtGainHisto.setTitle("SVT Mean Chip Gain");
            svtGainHisto.setXTitle("Gain, mV/fC");
            svtGainHisto.setYTitle("Entries");
            svtGainHisto.setFillColor(4);

            H1D svtEncHisto  = new H1D("hSvtEnc","",100,0.0,3000.0);
            svtEncHisto.setTitle("SVT Mean Chip ENC");
            svtEncHisto.setXTitle("ENC, e");
            svtEncHisto.setYTitle("Entries");
            svtEncHisto.setFillColor(3);

            H1D svtChanGainHisto  = new H1D("hSvtChanGain","",100,0.0,110.0);
            svtChanGainHisto.setTitle("SVT Channel Gain");
            svtChanGainHisto.setXTitle("Gain, mV/fC");
            svtChanGainHisto.setYTitle("Entries");
            svtChanGainHisto.setFillColor(4);
            final int GAIN_LOW=70;
            final int GAIN_HIGH=95;
            F1D fgain = new F1D("gaus",GAIN_LOW,GAIN_HIGH);
            fgain.setLineColor(2);
            fgain.setLineWidth(2);
            fgain.setParameter(1,85);
            fgain.setParameter(2,2.5);

            H1D svtChanEncHisto  = new H1D("hSvtChanEnc","",100,0.0,2000.0);
            svtChanEncHisto.setTitle("SVT Channel ENC");
            svtChanEncHisto.setXTitle("ENC, e");
            svtChanEncHisto.setYTitle("Entries");
            svtChanEncHisto.setFillColor(3);
            final int ENC_LOW=1400;
            final int ENC_HIGH=1700;
            F1D fenc = new F1D("gaus",ENC_LOW,ENC_HIGH);
            fenc.setLineColor(2);
            fenc.setLineWidth(2);
            fenc.setParameter(1,1600);
            fenc.setParameter(2,40);
            fenc.setParLimits(1, 1500.0, 1700.0);

//            GraphErrors graphMean = new GraphErrors();
//            graphMean.setTitle("Gain Mean");
//            GraphErrors graphRms  = new GraphErrors();
//            graphRms.setTitle("rms");
            int counter = 0;
            List<CalibrationDataChip>  chipList = this.calibStore.getChips().getList();
            for(CalibrationDataChip chip : chipList){ 
                counter++;
                double[] dataGain=chip.chipDataGain.getDataY().getArray();
                for(double d:dataGain) svtChanGainHisto.fill(d);
                double[] dataEnc=chip.chipDataEnc.getDataY().getArray();
                for(double d:dataEnc) svtChanEncHisto.fill(d);
                svtGainHisto.fill(chip.chipDataGainProj.getMean());
                svtEncHisto.fill(chip.chipDataEnc.getDataY().getMean());
//                chip.chipDataGainProj.getDataY(i);
//                int xa = chip.detectorDescriptor.getSector() *100 +  chip.detectorDescriptor.getLayer();
//                graphMean.add(xa, chip.chipDataGainProj.getMean());
//                graphRms.add(xa, chip.chipDataGainProj.getRMS());
            }
            
            this.canvas.divide(2, 2);
            this.canvas.cd(0);
            this.canvas.draw(svtEncHisto);
            //this.canvas.getPad().setAxisRange(0, counter, 0,1.4);
//            this.canvas.draw(graphMean);
            this.canvas.cd(1);
            this.canvas.draw(svtChanEncHisto);
            svtChanEncHisto.fit(fenc);
            this.canvas.draw(fenc,"same");
            //this.canvas.getPad().setAxisRange(0, counter, 0,0.08);
//            this.canvas.draw(graphRms);
            this.canvas.cd(2);
            this.canvas.draw(svtGainHisto);
            this.canvas.cd(3);
            this.canvas.draw(svtChanGainHisto);
            svtChanGainHisto.fit(fgain);
            this.canvas.draw(fgain,"same");
        }
    }
    
    public JPanel getView(){ return this.calibPane;}
    
    public static void main(String[] args){
        
        DataFitter.FITPRINTOUT = false;
        JFrame frame = new JFrame();
        frame.setSize(1200, 700);
        CalibrationSVT calib = new CalibrationSVT();
        frame.add(calib.getView());
        frame.pack();
        frame.setVisible(true);
        calib.readData("");
    }
}
