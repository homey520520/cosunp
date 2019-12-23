package com.cosun.cosunp.tool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/21 0021 上午 10:48
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class WordToHtml {

    private static Logger logger = LogManager.getLogger(WordToHtml.class);


    public static void word2007ToHtml(String centerPath, String allPath, MultipartFile file, Integer deptId) throws Exception {
        int index = file.getOriginalFilename().lastIndexOf(".");
        String nameOnly = file.getOriginalFilename().substring(0, index);
        String imagePathStr = centerPath + "image/";
        OutputStreamWriter outputStreamWriter = null;
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(allPath));
            XHTMLOptions options = XHTMLOptions.create();
            options.setExtractor(new FileImageExtractor(new File(imagePathStr)));
            options.URIResolver(new BasicURIResolver("ftp://admin:FL33771@192.168.0.152/" + deptId + "/image"));
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(centerPath + nameOnly + ".html"), "utf-8");
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, outputStreamWriter, options);
            outputStreamWriter.close();
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
        }
    }


    public static void DocToHtml(String centerPath, String allPath, MultipartFile file, Integer deptId) throws Exception {
        ByteArrayOutputStream out = null;
        try {
            int index = file.getOriginalFilename().lastIndexOf(".");
            String nameOnly = file.getOriginalFilename().substring(0, index);
            String outPutFile = centerPath + nameOnly + ".html";
            HWPFDocument wordDocument;
            try {
                InputStream in = new FileInputStream(allPath);
                wordDocument = new HWPFDocument(in);
                DocumentBuilderFactory domBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder domBuilder = domBuilderFactory.newDocumentBuilder();
                Document dom = domBuilder.newDocument();
                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(dom);
                wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                    public String savePicture(byte[] content,
                                              PictureType pictureType, String suggestedName,
                                              float widthInches, float heightInches) {
                        return "ftp://admin:FL33771@192.168.0.152/" + deptId + "/" + suggestedName.substring(1, suggestedName.length() - 1);
                    }
                });
                wordToHtmlConverter.processDocument(wordDocument);
                List<?> pics = wordDocument.getPicturesTable().getAllPictures();
                if (pics != null) {
                    for (int i = 0; i < pics.size(); i++) {
                        Picture pic = (Picture) pics.get(i);
                        try {
                            pic.writeImageContent(new FileOutputStream(centerPath + pic.suggestFullFileName()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Document htmlDocument = wordToHtmlConverter.getDocument();
                DOMSource domSource = new DOMSource(htmlDocument);
                out = new ByteArrayOutputStream();
                StreamResult streamResult = new StreamResult(out);
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer serializer = tf.newTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");

                serializer.transform(domSource, streamResult);
                writeFile(new String(out.toByteArray()), outPutFile);
                out.close();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (TransformerConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (TransformerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } catch (Exception e) {
            out.close();
            throw e;
        }
    }


    public static void writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            } catch (IOException ie) {
            }
        }
    }

}
