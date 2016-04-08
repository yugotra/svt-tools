package org.jlab.svt.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final int NREGIONS = 4;
    public static final int NLAYERS = 8;
    public static final int NSECTORS = 24;
    public static final int NR1SECTORS = 10;
    public static final int NR2SECTORS = 14;
    public static final int NR3SECTORS = 18;
    public static final int NR4SECTORS = 24;
    public static final int SECTORSPERLAYER[] = new int[] {10,10,14,14,18,18,24,24};
    public static final int SECTORSPERREGION[] = new int[] {10,14,18,24};
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    protected static boolean cosmicRun = true;
    protected static boolean mcRun = true;
    protected static String inputFileDir = ".";
    protected static String inputFileName = "input_files.txt";
    protected static String histoFileName = "svtValidation_rec_svt_test.evio";


    protected static final String[] sList =
            {
                    "Track",
                    "CosmicTrack",
                    "Trajectory",
                    "Cross",
                    "Cluster",
                    "Hit",
                    "Event"
            };

    protected static final String[] detectorTypeList =
            {
                    "SVT",
                    "BMT"
            };

    public static final ArrayList<String> trackHistoList = new ArrayList<String>(
            Arrays.asList("p", "pT", "phi0", "z0", "d0", "tanDip", "pathLength", "fittingMethod", "cirFitChi2Ndf", "linFitChi2Ndf"));

    public static final ArrayList<String> cosmicTrackHistoList = new ArrayList<String>(
            Arrays.asList("normChi2", "phi", "theta", "trackMultiplicity"));

    public static final ArrayList<String> trajectoryHistoList = new ArrayList<String>(
            Arrays.asList("localPhi", "localTheta", "localTrackAngle", "localX", "localY", "localZ"));

    public static final ArrayList<String> crossHistoList = new ArrayList<String>(
            Arrays.asList("crossErrX", "crossErrY", "crossErrZ", "crossMultiplicity", "crossX", "crossY", "crossZ"));

    public static final ArrayList<String> clusterHistoList = new ArrayList<String>(
            Arrays.asList("centroid",
                    "centroidResidual",
                    "seedResidual",
                    "clusterCharge",
                    "clusterMultiplicity",
                    "correctedClusterCharge",
                    "seedCharge",
                    "seedStrip",
                    "stripMultiplicity"));

    public static final ArrayList<String> hitHistoList = new ArrayList<String>(
            Arrays.asList("hitAdc", "hitBco", "hitMultiplicity", "hitStrip"));

    public static final ArrayList<String> eventHistoList = new ArrayList<String>(
            Arrays.asList("event_centroid", "event_centroidResidual", "event_seedResidual", "event_clusterCharge", "event_clusterMultiplicity", "event_seedCharge", "event_seedStrip", "event_stripMultiplicity"));

    public static Map<String,ArrayList<String>> histoTypeMap = new HashMap<String,ArrayList<String>>();

//    public static final String[] canvasRowsList =
//            {
//                    "1", "2", "3", "4", "5", "6"
//            };
//    public static final String[] canvasColumnsList =
//            {
//                    "1", "2", "3", "4", "5", "6"
//            };

    protected static final String[] regionHistoList =
            {
                    "adc",
                    "clusterCharge",
                    "strip",
                    "stripMultiplicity",
                    "centroidResidual",
                    "localPhi",
                    "localTheta",
                    "localTrackAngle",
                    "seedCharge",
                    "correctedClusterCharge",
                    "fitResidual",
                    "seedResidual",
                    "crossX",
                    "crossY",
                    "crossZ",
                    "crossErrX",
                    "crossErrY",
                    "crossErrZ"
            };

    protected static final String[] sensorHistoList =
            {
                    "adc",
                    "clusterCharge",
                    "strip",
                    "stripMultiplicity",
                    "centroidResidual",
                    "localPhi",
                    "localTheta",
                    "localTrackAngle",
                    "seedCharge",
                    "correctedClusterCharge",
                    "fitResidual",
                    "seedResidual",
            };

    protected static final String[] sectorHistoList =
            {
                    "adc",
                    "clusterCharge",
                    "correctedClusterCharge",
                    "fitResidual",
                    "centroidResidual",
                    "seedResidual",
                    "localPhi",
                    "localTheta",
                    "localTrackAngle",
                    "seedCharge",
                    "strip",
                    "stripMultiplicity"
            };

    public Constants() {

        histoTypeMap.put("Track",trackHistoList);
        histoTypeMap.put("CosmicTrack",cosmicTrackHistoList);
        histoTypeMap.put("Trajectory",trajectoryHistoList);
        histoTypeMap.put("Cross",crossHistoList);
        histoTypeMap.put("Cluster",clusterHistoList);
        histoTypeMap.put("Hit",hitHistoList);
        histoTypeMap.put("Event",eventHistoList);

    }

}
