package org.jlab.svt.validation;

import java.io.*;
import java.util.List;

public class SVTValidation {

    int eventNr;
    long numEvents; // events to process
    boolean print; // dump event
    boolean debug; // for debugging
    boolean skip; // skip events based on cuts
    boolean saveHisto; // save histo file
    boolean launchBrowser; // open TBrowser after processing
    long skipEvents; // events to skip
    long printEventNr; // display progress
    boolean readHisto;
    final static boolean cosmicRun=true;
    String histoFileName;
    SVTHistos svtHistos;
    public static List<String> inputFiles;

    public SVTValidation(){

        debug=false;
        print=false;
        skip=false;
        saveHisto=true;
        launchBrowser=true;
        readHisto=false;
        skipEvents=0;
        numEvents=(long) 1e9;
        printEventNr=20000;
        eventNr=0;
        histoFileName="svtValidation.evio";
        svtHistos=new SVTHistos();
        svtHistos.book();
        try {
            File dir=new File(".");
            File fin=null;
            fin=new File(dir.getCanonicalPath() + File.separator + "inputfiles.txt");
            readFile(fin);
//            System.out.println(inputFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFile(File fin) throws IOException {
        FileInputStream fis=new FileInputStream(fin);
        BufferedReader br=new BufferedReader(new InputStreamReader(fis));

        String line=null;
        while ((line=br.readLine()) !=null) {
//            if(!line.startsWith("//")) inputFiles.add(line);
//                System.out.println(line);
        }

        br.close();
    }
}
