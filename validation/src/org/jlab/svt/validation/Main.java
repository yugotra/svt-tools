package org.jlab.svt.validation;

import org.jlab.evio.clas12.EvioDataChain;
import org.jlab.evio.clas12.EvioDataEvent;
import org.root.group.TBrowser;

public class Main {

    public static void main(String[] args) {

        SVTValidation svtValidation = new SVTValidation();




//        if (!svtValidation.debug) {
//            System.out.println(
//                    StringUtils.getStringBlue(
//                            StringTable.getTitleString("COAT/JAVA PLATFORM for CLAS12  version 1.0", 1)));

//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//
//                public void run() {
//                    TableRender.createAndShowGUI();
//                }
//            });
//        return;




//        }




    long printEvent;
        if(svtValidation.debug) printEvent=1;
        else printEvent=svtValidation.printEventNr;

        if(svtValidation.readHisto) {
            if(args.length==0) {
                System.out.println("Enter the histogram file name to read");
                return;
            }
            String histoFilename = args[0];
            System.out.println(histoFilename);
            svtValidation.svtHistos.rootDir.readFile(histoFilename);
            TBrowser browser = new TBrowser(svtValidation.svtHistos.rootDir);
            return;
        }

        String[] inputFiles = {
//                "/Volumes/data/work/clasio/svtskim.mc.0.evio",
//                "/Volumes/data/work/coatjava/rec_output.mc.pv.0.evio",
                "/Volumes/data/work/coatjava/rec_output.mc.helic.0.evio",
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
//        for(String inputFile : svtValidation.inputFiles) {
            reader.addFile(inputFile);
        }
        reader.open();

        while(reader.hasEvent()&&svtValidation.eventNr <=svtValidation.numEvents) {
            EvioDataEvent event = reader.getNextEvent();
            if(svtValidation.eventNr <=svtValidation.skipEvents) {
                svtValidation.eventNr++;
                continue;
            }
            if(svtValidation.eventNr %printEvent==0) System.out.println(Constants.GREEN+"Event "+svtValidation.eventNr +Constants.RESET);
            SVTEvent svtevt = new SVTEvent();
            if(!svtValidation.skip || (!svtevt.SkipEvent(event,svtValidation.print))) {
                if(svtValidation.debug) System.out.println(Constants.GREEN+"pass skip"+Constants.RESET);
                svtevt.SetEventNumber(svtValidation.eventNr);
                svtevt.ReadTracks(event,svtValidation.print);
                if(svtValidation.print) svtevt.ShowTracks();
                svtevt.ReadOffTrack(event,svtValidation.print);
                if(svtValidation.print) svtevt.ShowOffTrack();
                svtevt.FillTrackHistos(svtValidation.svtHistos);
                svtevt.FillOffTrackHistos(svtValidation.svtHistos);
                svtevt.FillEventHistos(event,svtValidation.svtHistos);
            }
            // else {
            // if(svtValidation.debug==true) System.out.println(Constants.RED+"skipping event"+Constants.RESET);
            // }
            svtValidation.eventNr++;
        }

//         SVTGraphs svtGraphs = new SVTGraphs(svtValidation.svtHistos);
//         svtGraphs.book();
        if(svtValidation.saveHisto) {
            svtValidation.svtHistos.rootDir.write(svtValidation.histoFileName);
            System.out.println("Histogram file: "+svtValidation.histoFileName);
        }
        System.out.println(Constants.GREEN+"Events processed: "+(svtValidation.eventNr -1)+Constants.RESET);
        TBrowser br;
        if(svtValidation.launchBrowser) br = new TBrowser(svtValidation.svtHistos.rootDir);
    }
}
