package com.cosun.cosunp.tool;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.parallel.InputStreamSupplier;
import org.apache.commons.io.input.NullInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;

/**
 * @author:homey Wong
 * @date:2019/6/13 0013 上午 10:02
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class HighEffiCompressZipTest {

    public static void main(String[] arg) throws Exception {
    }

    class CustomInputStreamSupplier implements InputStreamSupplier {
        private File currentFile;


        public CustomInputStreamSupplier(File currentFile) {
            this.currentFile = currentFile;
        }


        @Override
        public InputStream get() {
            try {
                return currentFile.isDirectory() ? new NullInputStream(0) : new FileInputStream(currentFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private void  addEntry(String entryName, File currentFile, HighEffiCompressZip scatterSample) throws IOException {
        ZipArchiveEntry archiveEntry = new ZipArchiveEntry(entryName);
        archiveEntry.setMethod(ZipEntry.DEFLATED);
        final InputStreamSupplier supp = new CustomInputStreamSupplier(currentFile);
        scatterSample.addEntry(archiveEntry, supp);
    }


    private void compressCurrentDirectory(List<String> dir, HighEffiCompressZip scatterSample) throws IOException {
        if (dir == null) {
            throw new IOException("");
        }
        String relativePath = "";
        for (String file : dir) {
            File f = new File(file);
            if (!f.isDirectory()){
                relativePath = f.getParent().replace(scatterSample.getRootPaths(), "");
                addEntry(relativePath + File.separator + f.getName(), f, scatterSample);
            }
        }
    }


    public void createZipFile(final List<String> rootPaths, final File result) throws Exception {
        File dstFolder = new File(result.getParent());
        if (!dstFolder.isDirectory()) {
            dstFolder.mkdirs();
        }

        final HighEffiCompressZip scatterSample = new HighEffiCompressZip("zip");
        compressCurrentDirectory(rootPaths, scatterSample);
        final ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(result);
        scatterSample.writeTo(zipArchiveOutputStream);
        zipArchiveOutputStream.close();
    }

}
