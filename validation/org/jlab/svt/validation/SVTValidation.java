package org.jlab.svt.validation;

import java.io.*;
import java.util.List;

public class SVTValidation {

    int event_nr_;
    long num_events_; // events to process
    boolean print_; // dump event
    boolean debug_; // for debugging
    boolean skip_; // skip events based on cuts
    boolean save_histo_; // save histo file
    boolean launch_browser_; // open TBrowser after processing
    long skip_events_; // events to skip
    long print_event_nr_; // display progress
    boolean read_histo_;
    String histoFileName;
    SVTHistos svthistos_;
    public static List<String> input_files_;

    public SVTValidation(){

        debug_=false;
        print_=false;
        skip_=false;
        save_histo_=true;
        launch_browser_=true;
        read_histo_=false;
        skip_events_=0;
        num_events_= (long) 1e9;
        print_event_nr_=20000;
        event_nr_=0;
        histoFileName="svtValidation.evio";
        svthistos_ = new SVTHistos();
        svthistos_.book();
        try {
            File dir = new File(".");
            File fin = null;
            fin = new File(dir.getCanonicalPath() + File.separator + "inputfiles.txt");
            readFile(fin);
//            System.out.println(input_files_);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFile(File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while ((line = br.readLine()) != null) {
//            if(!line.startsWith("//")) input_files_.add(line);
//                System.out.println(line);
        }

        br.close();
    }
}
