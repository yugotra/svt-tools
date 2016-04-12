package org.jlab.svt.validation;

import org.jlab.clas12.calib.DetectorModulePane;
import org.root.basic.EmbeddedCanvas;
import org.root.data.DataSetXY;
import org.root.data.DataVector;
import org.root.func.F1D;
import org.root.histogram.GraphErrors;
import org.root.histogram.H1D;

import javax.swing.*;

public class SVTGraph {

    public SVTGraph() {
    }

    public void FillGraphs(SVTHistos svtHistos, DetectorModulePane pane, JFrame frame) {

        EmbeddedCanvas canvasResidual = pane.getCanvas("Residuals");
        canvasResidual.divide(2, 4);
        canvasResidual.setAxisTitleFontSize(16);
        canvasResidual.setAxisFontSize(16);
        canvasResidual.setTitleFontSize(16);
        canvasResidual.setStatBoxFontSize(8);
        F1D fgain;
        for (int i = 0; i < Constants.NLAYERS; i++) {
            int layer = i + 1;
            int sector;
            sector = 1;
            canvasResidual.cd(layer - 1);
//            if (layer == 1 || layer == 2) sector = 6;
//            else if (layer == 3 || layer == 4) sector = 8;
//            else if (layer == 5 || layer == 6) sector = 10;
//            else if (layer == 7 || layer == 8) sector = 13;
//            if (layer == 1 || layer == 2) sector = 4;
//            else if (layer == 3 || layer == 4) sector = 5;
//            else if (layer == 5 || layer == 6) sector = 6;
//            else if (layer == 7 || layer == 8) sector = 7;

            int maxBin = svtHistos.histoSensorMap.get("centroidResidual").h[layer - 1][sector - 1].getMaximumBin();
            double peak = svtHistos.histoSensorMap.get("centroidResidual").h[layer - 1][sector - 1].getDataX(maxBin);
            double mean = svtHistos.histoSensorMap.get("centroidResidual").h[layer - 1][sector - 1].getMean();
            double sigma = svtHistos.histoSensorMap.get("centroidResidual").h[layer - 1][sector - 1].getRMS();
//            fgain = new F1D("gaus", mean - 0.7*sigma, mean + 0.7*sigma);
            fgain = new F1D("gaus", peak - 3 * sigma, peak + 3 * sigma);
            fgain.setLineColor(2);
            fgain.setLineWidth(2);
            double maximum = svtHistos.histoSensorMap.get("centroidResidual").h[layer - 1][sector - 1].getMaximumBin() * 1.1;

            fgain.setParameter(0, maximum);
            fgain.setParameter(1, mean);
//            fgain.setParameter(2, sigma * 0.3);
            fgain.setParameter(2, sigma * 1.0);
//            fgain.setParLimits(2,sigma*0.8,sigma*1.2);
//            fgain.setParLimits(0,0.9*maximum,1.1*maximum);
//            if(layer!=7)
//            svtHistos.histoSensorMap.get("centroidResidual").h[layer - 1][sector - 1].fit(fgain, "REM");
            canvasResidual.draw(svtHistos.histoSensorMap.get("centroidResidual").h[layer - 1][sector - 1], "S");
            canvasResidual.draw(fgain, "sameS");
        }
//        frame.add(pane);
//        frame.pack();
//        frame.setVisible(true);
    }

    public void FillTrackEfficiency(SVTHistos svtHistos, DetectorModulePane pane, JFrame frame) {
        EmbeddedCanvas canvasEfficiency = pane.getCanvas("TrkEff");
        canvasEfficiency.divide(2, 2);
        canvasEfficiency.setAxisFontSize(20);
        canvasEfficiency.setAxisTitleFontSize(20);
        canvasEfficiency.setTitleFontSize(20);
        canvasEfficiency.setStatBoxFontSize(12);
        GraphErrors pTResPt = svtHistos.histoMap.get("pTResPt").h2.getProfileX();
        pTResPt.setTitle("pT resolution, %");
        pTResPt.setXTitle("pT, GeV");
        pTResPt.setYTitle("pT resolution, %");
        pTResPt.setMarkerColor(2);
        pTResPt.setMarkerSize(10);
        pTResPt.setMarkerStyle(2);
        canvasEfficiency.draw(pTResPt);
        canvasEfficiency.cd(1);
        GraphErrors pResP = svtHistos.histoMap.get("pResP").h2.getProfileX();
        pResP.setTitle("Momentum resolution, %");
        pResP.setXTitle("p, GeV");
        pResP.setYTitle("Momentum resolution, %");
        pResP.setMarkerColor(2);
        pResP.setMarkerSize(10);
        pResP.setMarkerStyle(2);
        canvasEfficiency.draw(pResP);

        H1D hEffPt = H1D.divide(svtHistos.histoMap.get("pTEff").h, svtHistos.histoMap.get("gen_pT").h);
        hEffPt.normalize(0.01);
        H1D hEffP = H1D.divide(svtHistos.histoMap.get("pEff").h, svtHistos.histoMap.get("gen_p").h);
        hEffP.normalize(0.01);

        DataSetXY dataEffPt = hEffPt.getDataSet();
        DataVector xV = dataEffPt.getDataX();
        double[] x = xV.getArray();
        DataVector yV = dataEffPt.getDataY();
        double[] y = yV.getArray();
        GraphErrors gEffPt = new GraphErrors("eff pT", x, y);
        gEffPt.setTitle("Track finding efficiency, %");
        gEffPt.setXTitle("pT, GeV");
        gEffPt.setYTitle("Track finding efficiency, %");
        gEffPt.setMarkerColor(2);
        gEffPt.setMarkerSize(10);
        gEffPt.setMarkerStyle(2);

        DataSetXY dataEffP = hEffP.getDataSet();
        DataVector xVp = dataEffP.getDataX();
        double[] xp = xVp.getArray();
        DataVector yVp = dataEffP.getDataY();
        double[] yp = yVp.getArray();
        GraphErrors gEffP = new GraphErrors("eff pT", xp, yp);
        gEffP.setTitle("Track finding efficiency, %");
        gEffP.setXTitle("p, GeV");
        gEffP.setYTitle("Track finding efficiency, %");
        gEffP.setMarkerColor(2);
        gEffP.setMarkerSize(10);
        gEffP.setMarkerStyle(2);

        canvasEfficiency.cd(2);
        canvasEfficiency.draw(gEffPt);

        canvasEfficiency.cd(3);
        canvasEfficiency.draw(gEffP);
    }

    public void FillHitEfficiency(SVTHistos svtHistos, DetectorModulePane pane, JFrame frame) {
        EmbeddedCanvas canvasEfficiency = pane.getCanvas("HitEff");
        canvasEfficiency.setAxisFontSize(20);
        canvasEfficiency.setAxisTitleFontSize(20);
        canvasEfficiency.setTitleFontSize(20);
        canvasEfficiency.setStatBoxFontSize(20);
        for (int i = 0; i < Constants.NLAYERS; i++) {
            double h = svtHistos.histoMap.get("event_trackHitsPerLayer_svt").h.getBinContent(i);
            double d = svtHistos.histoMap.get("event_dgtzPerLayer_svt").h.getBinContent(i);
//            System.out.println(i+" "+d+" "+h+" "+h/d);
        }
        H1D hEffLr = H1D.divide(svtHistos.histoMap.get("event_trackHitsPerLayer_svt").h, svtHistos.histoMap.get("event_dgtzPerLayer_svt").h);
        hEffLr.normalize(0.01);
        hEffLr.setName("trackHitEff_layer_svt");
        hEffLr.setXTitle("Layer");
        hEffLr.setYTitle("Hit Efficiency, %");
        hEffLr.setFillColor(24);
        canvasEfficiency.draw(hEffLr, "S");
    }

    public void FillResolution(SVTHistos svtHistos, DetectorModulePane pane, JFrame frame) {
        EmbeddedCanvas canvasResolution = pane.getCanvas("Resolution");
        canvasResolution.divide(3, 3);
        canvasResolution.setAxisFontSize(20);
        canvasResolution.setAxisTitleFontSize(20);
        canvasResolution.setTitleFontSize(20);
        canvasResolution.setStatBoxFontSize(12);

        double[] xPtRes = {0.5, 0.7, 0.9, 1.1, 1.3, 1.5, 1.7, 1.9, 2.1};
        double[] yPtRes = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] errxPtRes = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] erryPtRes = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (int i = 1; i < 10; i++) {
            canvasResolution.cd(i - 1);
            String hName = "deltaPt" + i;
//            String hName = "deltaP" + i;
            double mean = svtHistos.histoMap.get(hName).h.getMean();
            double sigma = svtHistos.histoMap.get(hName).h.getRMS();
            int maxBin = svtHistos.histoMap.get(hName).h.getMaximumBin();
            double peak = svtHistos.histoMap.get(hName).h.getDataX(maxBin);
//            F1D fGaus = new F1D("gaus", mean - sigma*1.7, mean + sigma*2.2);
            F1D fGaus = new F1D("gaus", peak - sigma * 2.2, peak + sigma * 2.2);
            fGaus.setParameter(0, svtHistos.histoMap.get(hName).h.getMaximumBin() * 1.1);
//            fGaus.setParameter(1, mean+mean*0.02);
//            fGaus.setParameter(1, mean-mean*0.1);
            fGaus.setParameter(1, peak);
//            fGaus.setParameter(2, sigma * 0.3);
            if (i < 7) fGaus.setParameter(2, sigma * 0.25); // 0.25
            else if (i == 7 || i == 8) fGaus.setParameter(2, sigma * 0.8);
            else fGaus.setParameter(2, sigma * 0.78);
//            if(i==3) fGaus.setParameter(2, sigma * 0.8);
            fGaus.setLineColor(2);
            fGaus.setLineWidth(2);
//            yPtRes[i-1] = 100*svtHistos.histoMap.get(hName).h.getRMS()/xPtRes[i-1];
            svtHistos.histoMap.get(hName).h.fit(fGaus, "REMQ");
            yPtRes[i - 1] = 100 * Math.abs(fGaus.getParameter(2)) / xPtRes[i - 1];
//            System.out.println(i + " par0: " + fGaus.getParameter(0) + " par1: " + fGaus.getParameter(1) + " par2: " + fGaus.getParameter(2) + " " + xPtRes[i - 1]);
//            System.out.println(i + " par0: " + fGaus.parameter(0).error() + " par1: " + fGaus.parameter(1).error() + " par2: " + fGaus.parameter(2).error() + " " + xPtRes[i - 1]);
            erryPtRes[i - 1] = 100 * Math.abs(fGaus.parameter(2).error()) / xPtRes[i - 1];
            canvasResolution.draw(svtHistos.histoMap.get(hName).h, "S");
            canvasResolution.draw(fGaus, "sameS");

        }

        EmbeddedCanvas canvasPtResolution = pane.getCanvas("pTResolution");
        canvasPtResolution.setAxisFontSize(20);
        canvasPtResolution.setAxisTitleFontSize(20);
        canvasPtResolution.setTitleFontSize(20);
        canvasPtResolution.setStatBoxFontSize(12);
        GraphErrors gResPt = new GraphErrors("pT resolution, %", xPtRes, yPtRes, errxPtRes, erryPtRes);
//        GraphErrors gResPt = new GraphErrors("momentum resolution, %",xPtRes,yPtRes,errxPtRes,erryPtRes);
        gResPt.setTitle("pT resolution, %");
        gResPt.setXTitle("pT, GeV");
        gResPt.setYTitle("pT resolution, %");
//        gResPt.setTitle("momentum resolution, %");
//        gResPt.setXTitle("p, GeV");
//        gResPt.setYTitle("momentum resolution, %");
        gResPt.setMarkerColor(2);
        gResPt.setMarkerSize(10);
        gResPt.setMarkerStyle(2);
        canvasPtResolution.cd(0);
        canvasPtResolution.draw(gResPt);
    }

    public void PlotHistos(SVTValidation svtValidation, DetectorModulePane pane, JFrame frame) {
        EmbeddedCanvas canvas = pane.getCanvas("Group");
        int size = Constants.histoTypeMap.get(svtValidation.histoType).size();
        if (size % 2 != 0) size += 1;
        int rows = size / 2;
        int columns = 2;
//        canvas.divide(svtValidation.numCanvasColumns, svtValidation.numCanvasRows);
        canvas.divide(columns, rows);
        canvas.setAxisFontSize(20);
        canvas.setAxisTitleFontSize(20);
        canvas.setTitleFontSize(20);
        canvas.setStatBoxFontSize(8);

        String suffix = "";
        for (int i = 0; i < Constants.sList.length; i++) {
            if (i > 2) {
                if (svtValidation.selectedDetector == SVTValidation.DetectorType.SVT) suffix = "_svt";
                else suffix = "_bmt";
            }
            if (svtValidation.histoType.equals(Constants.sList[i])) break;
        }
        int idx = 0;
        for (String temp : Constants.histoTypeMap.get(svtValidation.histoType)) {
            canvas.cd(idx);
//            System.out.println(size+" "+temp+suffix);
            String hName = temp + suffix;
            canvas.draw(svtValidation.svtHistos.histoMap.get(hName).h, "S");
            idx++;
        }
    }

    public void PlotSensorHistos(SVTValidation svtValidation, DetectorModulePane pane, JFrame frame) {
        EmbeddedCanvas canvas = pane.getCanvas("Sensor");
        canvas.divide(2, 4);
//        canvas.divide(svtValidation.numCanvasColumns, svtValidation.numCanvasRows);
        canvas.setAxisFontSize(10);
        canvas.setAxisTitleFontSize(10);
        canvas.setTitleFontSize(10);
        canvas.setStatBoxFontSize(7);

        for (int i = 0; i < Constants.NLAYERS; i++) {
            int layer = i + 1;
            int sector;
            sector = 1;
            canvas.cd(layer - 1);
            if (layer == 1 || layer == 2) sector = 6;
            else if (layer == 3 || layer == 4) sector = 8;
            else if (layer == 5 || layer == 6) sector = 10;
            else if (layer == 7 || layer == 8) sector = 13;
//            if (layer == 1 || layer == 2) sector = 4;
//            else if (layer == 3 || layer == 4) sector = 5;
//            else if (layer == 5 || layer == 6) sector = 6;
//            else if (layer == 7 || layer == 8) sector = 7;

            canvas.draw(svtValidation.svtHistos.histoSensorMap.get(svtValidation.sensorHistoType).h[layer - 1][sector - 1], "S");
        }
    }

    public void PlotRegionHistos(SVTValidation svtValidation, DetectorModulePane pane, JFrame frame) {
        EmbeddedCanvas canvas = pane.getCanvas("Region");
        canvas.divide(2, 2);
        canvas.setAxisFontSize(15);
        canvas.setAxisTitleFontSize(15);
        canvas.setTitleFontSize(15);
        canvas.setStatBoxFontSize(10);

        for (int i = 0; i < Constants.NREGIONS; i++) {
            int region = i + 1;
            canvas.cd(region - 1);
            canvas.draw(svtValidation.svtHistos.histoRegionMap.get(svtValidation.regionHistoType).h[region - 1], "S");
        }
    }

    public void PlotSectorHistos(SVTValidation svtValidation, DetectorModulePane pane, JFrame frame) {
        EmbeddedCanvas canvas = pane.getCanvas("Sector");
        canvas.setAxisFontSize(20);
        canvas.setAxisTitleFontSize(20);
        canvas.setTitleFontSize(20);
        canvas.setStatBoxFontSize(20);
        canvas.draw(svtValidation.svtHistos.histoSensorMap.get(svtValidation.sectorHistoType).h[svtValidation.layer - 1][svtValidation.sector - 1], "S");

    }
}
