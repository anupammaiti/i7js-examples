/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
    Authors: iText Software.

    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */
package com.itextpdf.samples.pdfhtml;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.html2pdf.css.apply.impl.DefaultCssApplierFactory;
import com.itextpdf.samples.pdfhtml.colorblindness.ColorBlindnessCssApplierFactory;
import com.itextpdf.samples.pdfhtml.colorblindness.ColorBlindnessTransforms;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Example class for converting a html file to a pdfdocument
 */
public class PdfHtmlRunner {


    public static final String sourceFolder = "src/test/resources/pdfHTML/";
    public static final String destinationFolder = "target/output/pdfHTML/";
    public static final String[] files = {"rainbow", "qrcode"};

    public static void main(String[] args) throws IOException, InterruptedException {
        for (String name : files) {
            String htmlSource = sourceFolder + name + "/" + name + ".html";
            String resourceFolder = sourceFolder + name + "/";
            String pdfDest = destinationFolder + name + ".pdf";
            String pdfAsPrintDest = destinationFolder + name + "_print.pdf";
            String pdfColourBLind = destinationFolder+name+ "_colourblind.pdf";
            String pdfQrCode = destinationFolder+name+ "_qrcode.pdf";
            File file = new File(pdfDest);

            System.out.println("Parsing: " + htmlSource);
            file.getParentFile().mkdirs();
            new PdfHtmlRunner().parseSimple(htmlSource, pdfDest, resourceFolder);
            new PdfHtmlRunner().parseAsPrint(htmlSource, pdfAsPrintDest, resourceFolder);
            new PdfHtmlRunner().parseColourBlind(htmlSource,pdfColourBLind,resourceFolder);
            new PdfHtmlRunner().parseQrCode(htmlSource,pdfQrCode,resourceFolder);
        }
    }

    public void parseSimple(String htmlSource, String pdfDest, String resoureLoc) throws IOException, InterruptedException {
        File pdf = new File(pdfDest);
        pdf.getParentFile().mkdirs();

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(resoureLoc);
        try (FileInputStream fileInputStream = new FileInputStream(htmlSource);
             FileOutputStream fileOutputStream = new FileOutputStream(pdfDest)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, converterProperties);
        }
    }


    public void parseAsPrint(String htmlSource, String pdfDest, String resoureLoc) throws IOException, InterruptedException {
        File pdf = new File(pdfDest);
        pdf.getParentFile().mkdirs();

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(resoureLoc);
        converterProperties.setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT));
        try (FileInputStream fileInputStream = new FileInputStream(htmlSource);
             FileOutputStream fileOutputStream = new FileOutputStream(pdfDest)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, converterProperties);
        }
    }

    public void parseColourBlind(String htmlSource, String pdfDest, String resoureLoc) throws IOException, InterruptedException {
        File pdf = new File(pdfDest);
        pdf.getParentFile().mkdirs();
        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(resoureLoc);
        DefaultCssApplierFactory cssApplierFactory = new ColorBlindnessCssApplierFactory(ColorBlindnessTransforms.DEUTERANOMALY);
        converterProperties.setCssApplierFactory(cssApplierFactory);
        try (FileInputStream fileInputStream = new FileInputStream(htmlSource); FileOutputStream fileOutputStream = new FileOutputStream(pdfDest)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, converterProperties);
        }
    }

    public void parseQrCode(String htmlSource, String pdfDest, String resoureLoc) throws IOException, InterruptedException {
        File pdf = new File(pdfDest);
        pdf.getParentFile().mkdirs();
        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(resoureLoc);

        DefaultTagWorkerFactory tagWorkerFactory = new QRCodeTagWorkerFactory();
        converterProperties.setTagWorkerFactory(tagWorkerFactory);


        DefaultCssApplierFactory cssApplierFactory = new QRCodeTagCssApplierFactory();
        converterProperties.setCssApplierFactory(cssApplierFactory);

        try (FileInputStream fileInputStream = new FileInputStream(htmlSource);
             FileOutputStream fileOutputStream = new FileOutputStream(pdfDest)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, converterProperties);
        }
    }

}


