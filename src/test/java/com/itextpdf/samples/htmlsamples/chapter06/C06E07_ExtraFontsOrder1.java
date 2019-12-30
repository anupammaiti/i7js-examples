/*
 * Copyright 2016-2020, iText Group NV.
 * This example was created by Bruno Lowagie.
 * It was written in the context of the following book:
 * https://leanpub.com/itext7_pdfHTML
 * Go to http://developers.itextpdf.com for more info.
 */
package com.itextpdf.samples.htmlsamples.chapter06;

import java.io.File;
import java.io.IOException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.licensekey.LicenseKey;

public class C06E07_ExtraFontsOrder1 {

	/** An array with the paths to extra fonts. */
	public static final String[] FONTS = {
			"./src/test/resources/htmlsamples/fonts/noto/NotoSans-Regular.ttf",
			"./src/test/resources/htmlsamples/fonts/cardo/Cardo-Regular.ttf"
	};

	/**
	 * The path to the resulting PDF file.
	 */
	public static final String DEST = "./target/htmlsamples/ch06/fonts_noto.pdf";

	/**
	 * The path to the source HTML file.
	 */
	public static final String SRC = "./src/test/resources/htmlsamples/html/hello.html";

	/**
	 * The main method of this example.
	 *
	 * @param args no arguments are needed to run this example.
	 * @throws IOException signals that an I/O exception has occurred.
	 */
    public static void main(String[] args) throws IOException {
        LicenseKey.loadLicenseFile(System.getenv("ITEXT7_LICENSEKEY") + "/itextkey-html2pdf_typography.xml");
    	File file = new File(DEST);
    	file.getParentFile().mkdirs();

        C06E07_ExtraFontsOrder1 app = new C06E07_ExtraFontsOrder1();
        app.createPdf(SRC, FONTS, DEST);
    }

    /**
     * Creates the PDF file.
     *
     * @param src the path to the source HTML file
     * @param fonts an array containing paths to extra fonts
     * @param dest the path to the resulting PDF
     * @throws IOException signals that an I/O exception has occurred.
     */
    public void createPdf(String src, String[] fonts, String dest) throws IOException {
    	ConverterProperties properties = new ConverterProperties();
    	FontProvider fontProvider = new DefaultFontProvider(false, false, false);
    	for (String font : fonts) {
    		FontProgram fontProgram = FontProgramFactory.createFont(font);
    		fontProvider.addFont(fontProgram);
    	}
    	properties.setFontProvider(fontProvider);
		HtmlConverter.convertToPdf(new File(src), new File(dest), properties);
    }
}
