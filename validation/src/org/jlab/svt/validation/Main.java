package org.jlab.svt.validation;

import org.jlab.evio.clas12.EvioDataChain;
import org.jlab.evio.clas12.EvioDataEvent;
import org.root.group.TBrowser;

public class Main {

    public static void main(String[] args) {

        SVTValidation svtValidation = new SVTValidation();
        long printEvent;
        if(svtValidation.debug_) printEvent=1;
        else printEvent=svtValidation.print_event_nr_;

        if(svtValidation.read_histo_) {
            if(args.length==0) {
                System.out.println("Enter the histogram file name to read");
                return;
            }
            String histoFilename = args[0];
            System.out.println(histoFilename);
            svtValidation.svthistos_.rootdir_.readFile(histoFilename);
            TBrowser brs = new TBrowser(svtValidation.svthistos_.rootdir_);
            return;
        }

        String[] inputFiles = {
//                "/Volumes/data/work/clasio/svtskim.mc.0.evio",
                "/Volumes/data/work/coatjava/rec_output.mc.pv.0.evio",
//                "/Volumes/data/work/coatjava/rec_output.mc.0.evio",
//                "/Volumes/data/work/coatjava/rec_output.mc.1.evio",
//                 "/Volumes/data/work/coatjava/rec_output.257.17.0.0.evio",
//                 "/Volumes/data/work/coatjava/rec_output.257.17.0.1.evio",
//                 "/Volumes/data/work/coatjava/rec_output.257.17.0.2.evio",
//                 "/Volumes/data/work/coatjava/rec_output.257.17.0.3.evio",
//                 "/Volumes/data/work/coatjava/rec_output.257.17.1.0.evio",
//                 "/Volumes/data/work/coatjava/rec_output.257.17.1.1.evio",
//                 "/Volumes/data/work/coatjava/re®c_output.257.17.1.2.evio"
        };

        EvioDataChain reader = new EvioDataChain();
        for(String inputFile : inputFiles) {
//        for(String inputFile : svtValidation.input_files_) {
            reader.addFile(inputFile);
        }
        reader.open();

        while(reader.hasEvent()&&svtValidation.event_nr_<=svtValidation.num_events_) {
            EvioDataEvent event = reader.getNextEvent();
            if(svtValidation.event_nr_<=svtValidation.skip_events_) {
                svtValidation.event_nr_++;
                continue;
            }
            if(svtValidation.event_nr_%printEvent==0) System.out.println(Constants.GREEN+"Event "+svtValidation.event_nr_+Constants.RESET);
            SVTEvent svtevt = new SVTEvent();
            if(!svtValidation.skip_ || (!svtevt.SkipEvent(event,svtValidation.print_))) {
                if(svtValidation.debug_) System.out.println(Constants.GREEN+"pass skip"+Constants.RESET);
                svtevt.SetEventNumber(svtValidation.event_nr_);
                svtevt.ReadTracks(event,svtValidation.print_);
                if(svtValidation.print_) svtevt.ShowTracks();
                svtevt.ReadOffTrack(event,svtValidation.print_);
                if(svtValidation.print_) svtevt.ShowOffTrack();
                svtevt.FillTrackHistos(svtValidation.svthistos_);
                svtevt.FillOffTrackHistos(svtValidation.svthistos_);
                svtevt.FillEventHistos(event,svtValidation.svthistos_);
            }
            // else {
            // if(svtValidation.debug_==true) System.out.println(Constants.RED+"skipping event"+Constants.RESET);
            // }
            svtValidation.event_nr_++;
        }

//         SVTGraphs svtgraphs = new SVTGraphs(svtValidation.svthistos_);
//         svtgraphs.book();
        if(svtValidation.save_histo_) {
            svtValidation.svthistos_.rootdir_.write(svtValidation.histoFileName);
            System.out.println("Histogram file: "+svtValidation.histoFileName);
        }
        System.out.println(Constants.GREEN+"Events processed: "+(svtValidation.event_nr_-1)+Constants.RESET);
        TBrowser br;
        if(svtValidation.launch_browser_) br = new TBrowser(svtValidation.svthistos_.rootdir_);
    }
}
