package org.jlab.svt.validation;

import org.jlab.clas.detector.DetectorDescriptor;
import org.jlab.clas12.calib.DetectorModulePane;
import org.jlab.clas12.calib.DetectorShape2D;
import org.jlab.clas12.calib.DetectorShapeView2D;
import org.jlab.clas12.calib.IDetectorListener;
//import org.jlab.data.io.hipo.HipoDataSource;
import org.jlab.data.io.hipo.HipoDataSource;
import org.jlab.evio.clas12.EvioDataEvent;
import org.jlab.svt.calib.GeometrySVT;
import org.root.group.TBrowser;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SVTValidation extends JPanel implements IDetectorListener, ItemListener, ActionListener, PropertyChangeListener {

    int eventNr;
    static boolean print = false; // dump event
    static boolean debug = false; // for debugging
    static boolean useCuts = false; // use cuts
    static long skipEvents = 0; // events to skip
    static long numEvents = 30000;//(long) 1e9; // events to process
    static long printEventNr = 20000; // display progress
    public enum DetectorType {SVT, BMT}
    public DetectorType selectedDetector = DetectorType.SVT;
    //    public int numCanvasRows = 1;
//    public int numCanvasColumns = 1;
    public int sector = 1;
    public int layer = 1;
    public String regionHistoType = "adc";
    public String sensorHistoType = "adc";
    public String sectorHistoType = "adc";
    public String histoType = "Track";
    String histoFileName;
    SVTHistos svtHistos;
    SVTGraph svtGraph;
    protected static List<String> inputFiles;

    private DetectorModulePane detectorModulePane;
    private JFrame frame = new JFrame();
    //    public String[] buttonNames = {"L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8"};
    public String[] buttonNames = {"L1", "L2", "L3", "L4"};
    //    public String[] layers = {"L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8"};
    private JComboBox combo;
    private JComboBox comboRegionHistoType;
    private JComboBox comboSensorHistoType;
    private JComboBox comboSectorHistoType;
    //    private JComboBox comboLayer;
//    private JComboBox comboSector;
    private JComboBox combod;
    //    private JComboBox comboCanvasRows;
//    private JComboBox comboCanvasColumns;
    private JCheckBox skipCheckBox;
    private JCheckBox printCheckBox;
    private JCheckBox debugCheckBox;
    private JCheckBox cosmicsCheckBox;
    private JLabel nEventsLabel;
    private JLabel nSkipEventsLabel;
    private JLabel displayEventLabel;
    private JLabel regionLabel;
    private JLabel sensorLabel;
    private JLabel sectorLabel;
    private JLabel groupLabel;
    private static String nEventsString = "Events: ";
    private static String nSkipEventsString = "Skip: ";
    private static String displayEventString = "Display: ";
    private JFormattedTextField nEventsField;
    private JFormattedTextField nSkipEventsField;
    private JFormattedTextField displayEventField;
    public ConfigMenu configMenu;
    private FileChooser fileChooser;

    public SVTValidation() {

        this.init();
        this.initDetector();

        Constants constants = new Constants();
        eventNr = 1;
//        histoFileName="svtValidation_rec_helic_5T.1.0.evio";
//        histoFileName="svtValidation_rec_helic_5T_svt.v0.2.0.evio";
        inputFiles = new ArrayList<String>();
//        inputFiles.add("/Volumes/data/work/coatjava/rec_svt_muminus_5T.4.0.hipo");
        histoFileName = Constants.histoFileName;

        File dir = new File(Constants.inputFileDir);
        try {
            File fin = new File(dir.getCanonicalPath() + File.separator + Constants.inputFileName);
            readInputFileList(fin);
        } catch (IOException e) {
            e.printStackTrace();
        }

        svtHistos = new SVTHistos();
        svtHistos.book();

        fileChooser = new FileChooser();
        configMenu = new ConfigMenu();
        configMenu.pane = new JTextPane();
        configMenu.pane.setPreferredSize(new Dimension(250, 250));
        configMenu.pane.setBorder(new BevelBorder(BevelBorder.LOWERED));

        svtGraph = new SVTGraph();
        frame.setJMenuBar(configMenu.menuBar);
        frame.add(detectorModulePane);
        //JFrame frame1 = new JFrame("Config Menu");
        //frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame1.setJMenuBar(configMenu.menuBar);
        //frame1.getContentPane().add(configMenu.pane, BorderLayout.CENTER);
        //frame1.pack();
        //frame1.setVisible(true);
        frame.pack();
        frame.setVisible(true);

//        try {
//            File dir=new File(".");
//            File fin=null;
//            fin=new File(dir.getCanonicalPath() + File.separator + "inputfiles.txt");
//            readFile(fin);
//            System.out.println(inputFiles);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public DetectorModulePane getDetectorModulePane() {
        return this.detectorModulePane;
    }

//    private static void readFile(File fin) throws IOException {
//        FileInputStream fis=new FileInputStream(fin);
//        BufferedReader br=new BufferedReader(new InputStreamReader(fis));

//        String line=null;
//        while ((line=br.readLine()) !=null) {
//            if(!line.startsWith("//")) inputFiles.add(line);
//                System.out.println(line);
//        }

//        br.close();
//    }

    public void initDetector() {

        DetectorShapeView2D view = GeometrySVT.getDetectorView();
        view.addDetectorListener(this);
        this.detectorModulePane.getControlPanel().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;

//        configMenu.getSingleTrackCheckBox().addItemListener(this);
//        configMenu.getType1CheckBox().addItemListener(this);
//        configMenu.getChi2CheckBox().addItemListener(this);
//        configMenu.getAllCrossesCheckBox().addItemListener(this);

        JButton buttonOpenDataFile = new JButton("Open");
        buttonOpenDataFile.addActionListener(this);
        this.detectorModulePane.getControlPanel().add(buttonOpenDataFile, c);
        c.gridx++;

        JButton buttonProcessData = new JButton("Process");
        buttonProcessData.addActionListener(this);
        this.detectorModulePane.getControlPanel().add(buttonProcessData, c);
        c.gridx++;

        JButton buttonSaveHistos = new JButton("Save");
        buttonSaveHistos.addActionListener(this);
        this.detectorModulePane.getControlPanel().add(buttonSaveHistos, c);
        c.gridy++;
        c.gridx = 0;

        skipCheckBox = new JCheckBox("Use cuts");
        skipCheckBox.setMnemonic(KeyEvent.VK_C);
        skipCheckBox.setSelected(false);
        skipCheckBox.addItemListener(this);
        this.detectorModulePane.getControlPanel().add(skipCheckBox, c);
        c.gridx++;

        cosmicsCheckBox = new JCheckBox("Cosmics");
        cosmicsCheckBox.setMnemonic(KeyEvent.VK_C);
        cosmicsCheckBox.setSelected(false);
        cosmicsCheckBox.addItemListener(this);
        this.detectorModulePane.getControlPanel().add(cosmicsCheckBox, c);
        c.gridx++;

        printCheckBox = new JCheckBox("Print");
        printCheckBox.setMnemonic(KeyEvent.VK_C);
        printCheckBox.setSelected(false);
        printCheckBox.addItemListener(this);
        this.detectorModulePane.getControlPanel().add(printCheckBox, c);
        c.gridx++;

        debugCheckBox = new JCheckBox("Debug");
        debugCheckBox.setMnemonic(KeyEvent.VK_C);
        debugCheckBox.setSelected(false);
        debugCheckBox.addItemListener(this);
        this.detectorModulePane.getControlPanel().add(debugCheckBox, c);
        c.gridy++;
        c.gridx = 0;

        nEventsLabel = new JLabel(nEventsString);
        nSkipEventsLabel = new JLabel(nSkipEventsString);
        displayEventLabel = new JLabel(displayEventString);
        regionLabel = new JLabel("Region");
        sectorLabel = new JLabel("Sector");
        sensorLabel = new JLabel("Sensor");
        groupLabel = new JLabel("Group");

        nEventsField = new JFormattedTextField();
        nEventsField.setValue(Long.valueOf(numEvents));
        nEventsField.setColumns(12);
        nEventsField.setEditable(true);
        this.detectorModulePane.getControlPanel().add(nEventsLabel, c);
        c.gridx++;
        this.detectorModulePane.getControlPanel().add(nEventsField, c);
        nEventsField.addPropertyChangeListener("value", this);
        nEventsLabel.setLabelFor(nEventsField);
        c.gridx++;

        nSkipEventsField = new JFormattedTextField();
        nSkipEventsField.setValue(Long.valueOf(skipEvents));
        nSkipEventsField.setColumns(10);
        nSkipEventsField.setEditable(true);
        this.detectorModulePane.getControlPanel().add(nSkipEventsLabel, c);
        c.gridx++;
        this.detectorModulePane.getControlPanel().add(nSkipEventsField, c);
        nSkipEventsField.addPropertyChangeListener("value", this);
        nSkipEventsLabel.setLabelFor(nSkipEventsField);
        c.gridx++;

        displayEventField = new JFormattedTextField();
        displayEventField.setValue(Long.valueOf(printEventNr));
        displayEventField.setColumns(9);
        displayEventField.setEditable(true);
        this.detectorModulePane.getControlPanel().add(displayEventLabel, c);
        c.gridx++;
        this.detectorModulePane.getControlPanel().add(displayEventField, c);
        displayEventField.addPropertyChangeListener("value", this);
        displayEventLabel.setLabelFor(displayEventField);
        c.gridy++;
        c.gridx = 0;


        JButton buttonBr = new JButton("Browser");
        buttonBr.addActionListener(this);
        this.detectorModulePane.getControlPanel().add(buttonBr, c);
        c.gridy++;

        this.detectorModulePane.getControlPanel().add(groupLabel, c);
        c.gridx++;

        combo = new JComboBox();
        this.detectorModulePane.getControlPanel().add(combo, c);
        for (int iCtr = 0; iCtr < Constants.sList.length; iCtr++)
            combo.addItem(Constants.sList[iCtr]);
        combo.setEditable(true);
        combo.addItemListener(this);
        c.gridx++;

        combod = new JComboBox();
        this.detectorModulePane.getControlPanel().add(combod, c);
        for (int iCtr = 0; iCtr < Constants.detectorTypeList.length; iCtr++)
            combod.addItem(Constants.detectorTypeList[iCtr]);
        combod.setEditable(true);
        combod.addItemListener(this);
        c.gridy++;
        c.gridx = 0;
/*
        comboCanvasRows = new JComboBox();
        this.detectorModulePane.getControlPanel().add(comboCanvasRows, c);
        for (int iCtr = 0; iCtr < Constants.canvasRowsList.length; iCtr++)
            comboCanvasRows.addItem(Constants.canvasRowsList[iCtr]);
        comboCanvasRows.setEditable(true);
        comboCanvasRows.addItemListener(this);
        c.gridx++;

        comboCanvasColumns = new JComboBox();
        this.detectorModulePane.getControlPanel().add(comboCanvasColumns, c);
        for (int iCtr = 0; iCtr < Constants.canvasColumnsList.length; iCtr++)
            comboCanvasColumns.addItem(Constants.canvasColumnsList[iCtr]);
        comboCanvasColumns.setEditable(true);
        comboCanvasColumns.addItemListener(this);
        c.gridy++;
        c.gridx = 0;

        JButton buttonHistos = new JButton("Histos");
        buttonHistos.addActionListener(this);
        this.detectorModulePane.getControlPanel().add(buttonHistos, c);
        */
        c.gridy++;
        c.gridx = 0;

        this.detectorModulePane.getControlPanel().add(regionLabel, c);
        c.gridx++;

        comboRegionHistoType = new JComboBox();
        this.detectorModulePane.getControlPanel().add(comboRegionHistoType, c);
        for (int iCtr = 0; iCtr < Constants.regionHistoList.length; iCtr++)
            comboRegionHistoType.addItem(Constants.regionHistoList[iCtr]);
        comboRegionHistoType.setEditable(true);
        comboRegionHistoType.addItemListener(this);
        c.gridy++;
        c.gridx = 0;

        this.detectorModulePane.getControlPanel().add(sensorLabel, c);
        c.gridx++;

        comboSensorHistoType = new JComboBox();
        this.detectorModulePane.getControlPanel().add(comboSensorHistoType, c);
        for (int iCtr = 0; iCtr < Constants.sensorHistoList.length; iCtr++)
            comboSensorHistoType.addItem(Constants.sensorHistoList[iCtr]);
        comboSensorHistoType.setEditable(true);
        comboSensorHistoType.addItemListener(this);
        c.gridy++;
        c.gridx = 0;
/*
        comboLayer = new JComboBox();
        this.detectorModulePane.getControlPanel().add(comboLayer, c);
        for (int iCtr = 0; iCtr < Constants.NLAYERS; iCtr++)
            comboLayer.addItem("L" + (iCtr + 1));
        comboLayer.setEditable(true);
        comboLayer.addItemListener(this);
        c.gridx++;

        comboSector = new JComboBox();
        this.detectorModulePane.getControlPanel().add(comboSector, c);
        for (int iCtr = 0; iCtr < Constants.NR4SECTORS; iCtr++)
            comboSector.addItem("S" + (iCtr + 1));
        comboSector.setEditable(true);
        comboSector.addItemListener(this);
        c.gridx++;
*/

        this.detectorModulePane.getControlPanel().add(sectorLabel, c);
        c.gridx++;

        comboSectorHistoType = new JComboBox();
        this.detectorModulePane.getControlPanel().add(comboSectorHistoType, c);
        for (int iCtr = 0; iCtr < Constants.sectorHistoList.length; iCtr++)
            comboSectorHistoType.addItem(Constants.sectorHistoList[iCtr]);
        comboSectorHistoType.setEditable(true);
        comboSectorHistoType.addItemListener(this);
        c.gridy++;
        c.gridx = 0;

//        this.detectorModulePane.getDetectorView().setLayout(new FlowLayout());
        this.detectorModulePane.getDetectorView().addDetectorLayer(view);

        //String[] buttonText = new String[]{""};
//        for (String bn : this.buttonNames) {
//            c.gridx++;
//            JButton button = new JButton(bn);
//            button.addActionListener(this);
//            this.detectorModulePane.getControlPanel().add(button, c);
//        }
        /*
        JButton buttonL2 = new JButton(this.buttonNames[1]);
        buttonL2.addActionListener(this);
        this.detectorModulePane.getControlPanel().add(buttonL2,c);
*/
    }

    public void init() {

//        frame.setSize(2000, 1200);
        detectorModulePane = new DetectorModulePane(4000, 2000, 1);
        detectorModulePane.addCanvas("Group");
        detectorModulePane.getCanvas("Group").setSize(2000, 2000);
        detectorModulePane.addCanvas("Region");
        detectorModulePane.addCanvas("Layer");
        detectorModulePane.addCanvas("Sensor");
        detectorModulePane.addCanvas("Sector");
        detectorModulePane.addCanvas("Residuals");
        detectorModulePane.addCanvas("TrkEff");
        detectorModulePane.addCanvas("HitEff");
        detectorModulePane.addCanvas("Resolution");
        detectorModulePane.addCanvas("pTResolution");
    }

    public void processData() {

        long printEvent;
        if (debug) printEvent = 1;
        else printEvent = printEventNr;

        for (String inputFile : inputFiles) {
            if (inputFile == null) break;

            HipoDataSource reader = new HipoDataSource();
            reader.open(inputFile);
            int nevents = reader.getSize();

//        EvioDataChain reader = new EvioDataChain();
//        for (String inputFile : inputFiles) {
//        for(String inputFile : svtValidation.inputFiles) {
//            reader.addFile(inputFile);
//        }
//        reader.open();

            EvioDataEvent evt = (EvioDataEvent) reader.getNextEvent();
            if (evt.hasBank("GenPart::true"))
                Constants.mcRun = true;
            else Constants.mcRun = false;
            System.out.println(Constants.mcRun ? "MC run" : "Data run");

        while (reader.hasEvent() && eventNr <= numEvents) {
                EvioDataEvent event = (EvioDataEvent) reader.getNextEvent();

                if (eventNr <= skipEvents) {
                    eventNr++;
                    continue;
                }
                if (eventNr % printEvent == 0 || debug)
                    System.out.println(Constants.GREEN + "Event " + eventNr + Constants.RESET);
                SVTEvent svtevt = new SVTEvent();
                if (!useCuts || (!svtevt.SkipEvent(event, print))) {
                    if (debug) System.out.println(Constants.GREEN + "pass cuts" + Constants.RESET);
//                System.out.println(Constants.BLUE+"Event "+eventNr +Constants.RESET);
                    svtevt.SetEventNumber(eventNr);
                    svtevt.ReadTracks(event, print);
                    if (print) svtevt.ShowTracks();
                    svtevt.ReadOffTrack(event, print);
                    if (print) svtevt.ShowOffTrack();
                    if (Constants.mcRun) {
                        svtevt.ReadMCHits(event, print);
                        svtevt.ReadGenParts(event, print);
                        svtevt.FillMcHitHistos(svtHistos);
                        svtevt.FillGenPartHistos(svtHistos);
                    }
                    svtevt.FillTrackHistos(svtHistos);
                    svtevt.FillOffTrackHistos(svtHistos);
                    svtevt.FillEventHistos(event, svtHistos);
                }
                // else {
                // if(debug) System.out.println(Constants.RED+"skipping event"+Constants.RESET);
                // }
                eventNr++;
            }
        }
    }

    public void makeGraphs() {

        svtGraph.FillGraphs(svtHistos, getDetectorModulePane(), getFrame());
        svtGraph.FillTrackEfficiency(svtHistos, getDetectorModulePane(), getFrame());
        svtGraph.FillHitEfficiency(svtHistos, getDetectorModulePane(), getFrame());
        svtGraph.FillResolution(svtHistos, getDetectorModulePane(), getFrame());
        svtGraph.PlotHistos(this, getDetectorModulePane(), getFrame());

    }

    public void itemStateChanged(ItemEvent event) {
        if (event.getSource() == combo
                && event.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Change:"
                    + combo.getSelectedItem());
            histoType = combo.getSelectedItem().toString();
            svtGraph.PlotHistos(this, this.getDetectorModulePane(), this.getFrame());
        } else if (event.getSource() == combod
                && event.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Change:"
                    + combod.getSelectedItem());
            if (combod.getSelectedItem().equals("SVT")) selectedDetector = DetectorType.SVT;
            else if (combod.getSelectedItem().equals("BMT")) selectedDetector = DetectorType.BMT;
        } else if (event.getSource() == skipCheckBox) {
            System.out.println("Change:"
                    + skipCheckBox.getActionCommand());
            if (event.getStateChange() == ItemEvent.SELECTED) useCuts = true;
            else if (event.getStateChange() == ItemEvent.DESELECTED) useCuts = false;
        } else if (event.getSource() == printCheckBox) {
            System.out.println("Change:"
                    + printCheckBox.getActionCommand());
            if (event.getStateChange() == ItemEvent.SELECTED) print = true;
            else if (event.getStateChange() == ItemEvent.DESELECTED) print = false;
        } else if (event.getSource() == debugCheckBox) {
            System.out.println("Change:"
                    + debugCheckBox.getActionCommand());
            if (event.getStateChange() == ItemEvent.SELECTED) debug = true;
            else if (event.getStateChange() == ItemEvent.DESELECTED) debug = false;
        } else if (event.getSource() == cosmicsCheckBox) {
            System.out.println("Change:"
                    + cosmicsCheckBox.getActionCommand());
            if (event.getStateChange() == ItemEvent.SELECTED) Constants.cosmicRun = true;
            else if (event.getStateChange() == ItemEvent.DESELECTED) Constants.cosmicRun = false;
//        } else if (event.getSource() == configMenu.getSingleTrackCheckBox()) {
//            System.out.println("Change:"
//                    + configMenu.getSingleTrackCheckBox().getActionCommand());
//            if (event.getStateChange() == ItemEvent.SELECTED) singleTrackCut = true;
//            else if (event.getStateChange() == ItemEvent.DESELECTED) singleTrackCut = false;
        } else if (event.getSource() == comboSectorHistoType
                && event.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Change:"
                    + comboSectorHistoType.getSelectedItem());
            sectorHistoType = comboSectorHistoType.getSelectedItem().toString();
            svtGraph.PlotSectorHistos(this, this.getDetectorModulePane(), this.getFrame());
        } else if (event.getSource() == comboRegionHistoType
                && event.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Change:"
                    + comboRegionHistoType.getSelectedItem());
            regionHistoType = comboRegionHistoType.getSelectedItem().toString();
            svtGraph.PlotRegionHistos(this, this.getDetectorModulePane(), this.getFrame());
        } else if (event.getSource() == comboSensorHistoType
                && event.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Change:"
                    + comboSensorHistoType.getSelectedItem());
            sensorHistoType = comboSensorHistoType.getSelectedItem().toString();
            svtGraph.PlotSensorHistos(this, this.getDetectorModulePane(), this.getFrame());
        }
        /*
        else if (event.getSource() == comboCanvasRows
                && event.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Change:"
                    + comboCanvasRows.getSelectedItem());
            numCanvasRows = Integer.parseInt(comboCanvasRows.getSelectedItem().toString());
        }
            else if (event.getSource() == comboSector
                && event.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Change:"
                    + comboSector.getSelectedItem());
            sector = Integer.parseInt(comboSector.getSelectedItem().toString().substring(1));
            if (sector <= Constants.SECTORSPERLAYER[layer - 1])
                svtGraph.PlotSectorHistos(this, this.getDetectorModulePane(), this.getFrame());
        } else if (event.getSource() == comboLayer
                && event.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Change:"
                    + comboLayer.getSelectedItem());
            layer = Integer.parseInt(comboLayer.getSelectedItem().toString().substring(1));
            if (sector <= Constants.SECTORSPERLAYER[layer - 1])
                svtGraph.PlotSectorHistos(this, this.getDetectorModulePane(), this.getFrame());
        } else if (event.getSource() == comboCanvasColumns
                && event.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Change:"
                    + comboCanvasColumns.getSelectedItem());
            numCanvasColumns = Integer.parseInt(comboCanvasColumns.getSelectedItem().toString());
        }
*/
    }

    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == nEventsField) {
            numEvents = ((Number) nEventsField.getValue()).longValue();
            System.out.println("Events to process: " + numEvents);
        } else if (source == nSkipEventsField) {
            skipEvents = ((Number) nSkipEventsField.getValue()).longValue();
            System.out.println("Skip events: " + skipEvents);
        } else if (source == displayEventField) {
            printEventNr = ((Number) displayEventField.getValue()).longValue();
            System.out.println("Display event: " + printEventNr);
        }
    }

    public void detectorSelected(DetectorDescriptor dd) {
        sector = dd.getSector();
        layer = dd.getLayer() / 10;
        int chipid = dd.getLayer() - 10 * layer;
        System.out.println("SELECTED : " + dd.toString());
        System.out.println("Sector: " + sector + " Layer: " + layer + " Chip: " + chipid);
        svtGraph.PlotSectorHistos(this, this.getDetectorModulePane(), this.getFrame());
    }

    public void update(DetectorShape2D dsd) {
        int sector = dsd.getDescriptor().getSector();
        int layer = dsd.getDescriptor().getLayer();
//        if(chip==null){
//            dsd.setColor(180, 180, 180);
//        } else {
//            if(layer%2==0){
//                dsd.setColor(180, 255,180);
//            } else {
//                dsd.setColor(180, 180,255);
//            }
//        }
    }

    public void actionPerformed(ActionEvent e) {
//        int regionLayerFlag = -1;
        for (int i = 0; i < this.buttonNames.length; ++i) {
            if (e.getActionCommand().compareTo(this.buttonNames[i]) == 0)
                System.out.println("SELECTED : ");
        }
        if (e.getActionCommand().compareTo("Browser") == 0) {
            TBrowser tBrowser = new TBrowser(svtHistos.rootDir);
        } else if (e.getActionCommand().compareTo("Group") == 0) {
            svtGraph.PlotHistos(this, this.getDetectorModulePane(), this.getFrame());
        } else if (e.getActionCommand().compareTo("Open") == 0) {
            inputFiles.clear();
            fileChooser.createAndShowGUI();
        } else if (e.getActionCommand().compareTo("Process") == 0) {
            inputFiles.add(fileChooser.dataFileName);
            System.out.println(Constants.GREEN + "Processing file: " + inputFiles.get(0) + Constants.RESET);
            processData();
            makeGraphs();
            System.out.println(Constants.GREEN + "Events processed: " + (eventNr - 1 - skipEvents) + Constants.RESET);
        } else if (e.getActionCommand().compareTo("Save") == 0) {
        //    svtHistos.rootDir.write(histoFileName);
            //svtHistos.rootDir.writeHipo("testwrite.hipo");
        svtHistos.rootDir.writeHipo("testwrite.hipo");
            System.out.println("Histogram file: " + histoFileName);
        } else if (e.getActionCommand().compareTo("Sector") == 0) {
            svtGraph.PlotSectorHistos(this, this.getDetectorModulePane(), this.getFrame());
        }
    }

    private static void readInputFileList(File fin) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fin));

        String line = null;
        while ((line = br.readLine()) != null) {
            if(line.charAt(0) != '*') {
                System.out.println(line);
                inputFiles.add(line);
            }
        }

        br.close();
    }

}
