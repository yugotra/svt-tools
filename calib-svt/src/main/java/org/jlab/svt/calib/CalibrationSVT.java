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
import org.jlab.clas.detector.DetectorType;
import org.jlab.clas.detector.IConstantsTableListener;
import org.jlab.clas12.calib.CalibrationPane;
import org.jlab.clas12.calib.DetectorShape2D;
import org.jlab.clas12.calib.DetectorShapeView2D;
import org.jlab.clas12.calib.IDetectorListener;
import org.jlab.svt.utils.CalibrationData;
import org.jlab.svt.utils.CalibrationDataChip;
import org.jlab.svt.utils.CalibrationStore;
import org.root.fitter.DataFitter;
import org.root.histogram.GraphErrors;
import org.root.histogram.H1D;
import org.root.pad.EmbeddedCanvas;

/**
 *
 * @author gavalian
 */
public class CalibrationSVT implements IDetectorListener,
        IConstantsTableListener,ActionListener{

    
    private EmbeddedCanvas   canvas = new EmbeddedCanvas();    
    private CalibrationPane  calibPane = new CalibrationPane();
    //CalibrationDataChip         calibStore = new CalibrationDataChip();
    CalibrationStore              calibStore = new CalibrationStore();
    
    private ConstantsTablePanel   constantsTablePanel = null;
        
    public CalibrationSVT(){
        this.initDetector();
        this.init();
        
        //this.calibStore.analyze();
    }
    
    public void init(){
        this.calibPane.getCanvasPane().add(canvas);
        
        JButton button = new JButton("Show");
        button.addActionListener(this);
        this.calibPane.getBottonPane().add(button);
        //this.constantsTablePanel = new ConstantsTablePanel();
        //this.constantsTablePanel.addListener(this);        
        //this.calibPane.getTablePane().add(this.constantsTablePanel);
    }
    
    public void initDetector(){
        
        DetectorShapeView2D view = GeometrySVT.getDetectorView();
        view.addDetectorListener(this);
        this.calibPane.getDetectorView().addDetectorLayer(view);
    }
    
    public void detectorSelected(DetectorDescriptor dd) {
        int sector = dd.getSector();
        int layer  = dd.getLayer();
        CalibrationDataChip chip = this.calibStore.getChips().get(sector, layer, 0);
        
        System.out.println("SELECTED : " + dd.toString());
        if(chip!=null){
            GraphErrors graph = chip.getGraph();
            graph.setFillColor(4);
            graph.setMarkerSize(6);
            graph.show();
            this.canvas.divide(1, 2);
            this.canvas.cd(0);
            this.canvas.getPad().setAxisRange(-1.0, 129, 0.0, 1.4);
            this.canvas.draw(graph);
            
            chip.getHisto().setLineWidth(2);
            chip.getHisto().setFillColor(3);
            this.canvas.cd(1);
            this.canvas.draw(chip.getHisto());
            //chip.getCollection().show();
            try {
                this.constantsTablePanel.setTable(chip.getConstantsTable());

            } catch (Exception e){
                System.out.println("-----> error accured while siwching to view : SECTOR = " 
                        + sector + "  LAYAR " + layer);
            }
        }
        this.calibPane.repaint();
        //this.canvas.draw(this.calibStore.getGraph());
    }

    public void readData(String file){
        this.calibStore.readData("/Users/gavalian/Work/Software/Release-8.0/SVT/calib-svt/../101/2");
        this.calibStore.analyze();
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
        if(e.getActionCommand().compareTo("Show")==0){
            GraphErrors graphMean = new GraphErrors();
            GraphErrors graphRms  = new GraphErrors();
            int counter = 0;
            List<CalibrationDataChip>  chipList = this.calibStore.getChips().getList();
            for(CalibrationDataChip chip : chipList){ 
                counter++;
                int xa = chip.detectorDescriptor.getSector() *100 +  chip.detectorDescriptor.getLayer();
                graphMean.add(xa, chip.chipDataSlopesProj.getMean());
                graphRms.add(xa, chip.chipDataSlopesProj.getRMS());
            }
            
            this.canvas.divide(2, 1);
            this.canvas.cd(0);
            //this.canvas.getPad().setAxisRange(0, counter, 0,1.4);
            this.canvas.draw(graphMean);
            this.canvas.cd(1);
            //this.canvas.getPad().setAxisRange(0, counter, 0,0.08);
            this.canvas.draw(graphRms);
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
