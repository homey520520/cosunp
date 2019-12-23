package com.cosun.cosunp.tool;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author:homey Wong
 * @date:2019/6/4 0004 上午 10:14
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class WordToPDF {

    public static void WordToPDF(MultipartFile mf, String pdfName, String descDir) throws IOException {
        File inputFile = new File(descDir + "linshi/" + mf.getOriginalFilename());
        FileUtils.copyInputStreamToFile(mf.getInputStream(), inputFile);

        File outputFile = new File(pdfName);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().exists();
        }


        //String command = "/opt/openoffice4/program/soffice --headless --accept=\"socket,host=0.0.0.0,port=8100;urp;\" --nofirststartwizard &";
        String command = "/opt/openoffice4/program/soffice --headless --accept=\"socket,host=0.0.0.0,port=8100;urp;\" --nofirststartwizard";
        //String command = "C:/Program Files (x86)/OpenOffice 4/program/soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
        Process p = Runtime.getRuntime().exec(command);

        OpenOfficeConnection connection = new SocketOpenOfficeConnection("0.0.0.0", 8100);
        //OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        connection.connect();

        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);
        connection.disconnect();
        p.destroy();
    }


    public static void WordToPDFOrder(String excelName, String pdfName, String descDir) throws IOException {
        File inputFile = new File(descDir + "linshi/" + excelName);
        File outputFile = new File(descDir + "linshi/" + pdfName);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdir();
        }
        //String command = "/opt/openoffice4/program/soffice --headless --accept=\"socket,host=0.0.0.0,port=8100;urp;\" --nofirststartwizard &";
        // String command = "/opt/openoffice4/program/soffice --headless --accept=\"socket,host=0.0.0.0,port=8100;urp;\" --nofirststartwizard";
        // String command = "C:/Program Files (x86)/OpenOffice 4/program/soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
        // Process p = Runtime.getRuntime().exec(command);
        //OpenOfficeConnection connection = new SocketOpenOfficeConnection("0.0.0.0", 8100);
        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        connection.connect();
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);
        connection.disconnect();
        // p.destroy();
    }


    public static InputStream getPdfStream(String fileType, InputStream fileInput) throws Exception {
        String command = "/opt/openoffice4/program/soffice --headless --accept=\"socket,host=0.0.0.0,port=8100;urp;\" --nofirststartwizard";
        //String command = "C:/Program Files (x86)/OpenOffice 4/program/soffice -headless -accept=\"socket,host=0.0.0.0,port=8100;urp;\" -nofirststartwizard";
        Process p = Runtime.getRuntime().exec(command);
        //OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        OpenOfficeConnection connection = new SocketOpenOfficeConnection("0.0.0.0", 8100);
        try {
            connection.connect();
            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
            DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
            DocumentFormat inputFormat = formatReg.getFormatByFileExtension(fileType);
            DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
            ByteArrayOutputStream pdfstream = new ByteArrayOutputStream();
            converter.convert(fileInput, inputFormat, pdfstream, pdfFormat);
            InputStream pdfInput = new BufferedInputStream(new ByteArrayInputStream(pdfstream.toByteArray()));
            pdfstream.flush();
            pdfstream.close();
            p.destroy();
            return pdfInput;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.disconnect();
                    //p.destroy();
                    connection = null;
                }
            } catch (Exception e) {
            }
        }
        return null;
    }


    public static String getDataDir(Class c) {
        File dir = new File(System.getProperty("user.dir"));
        dir = new File(dir, "src");
        dir = new File(dir, "main");
        dir = new File(dir, "resources");

        return dir.toString() + File.separator;
    }


}
