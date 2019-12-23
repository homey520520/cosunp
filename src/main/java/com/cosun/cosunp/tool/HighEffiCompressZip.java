package com.cosun.cosunp.tool;

import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.parallel.InputStreamSupplier;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author:homey Wong
 * @date:2019/6/13 上午 10:01
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class HighEffiCompressZip {

    private String rootPaths;


    ParallelScatterZipCreator scatterZipCreator = new ParallelScatterZipCreator();
    ScatterZipOutputStream dirs = ScatterZipOutputStream
            .fileBased(File.createTempFile("whatever-preffix", ".whatever"));


    public HighEffiCompressZip(String rootPaths) throws IOException {
        this.rootPaths = rootPaths;
    }

    public HighEffiCompressZip() throws IOException {
    }


    public void addEntry(final ZipArchiveEntry zipArchiveEntry, final InputStreamSupplier streamSupplier)
            throws IOException {
        if (zipArchiveEntry.isDirectory() && !zipArchiveEntry.isUnixSymlink()) {
            dirs.addArchiveEntry(ZipArchiveEntryRequest.createZipArchiveEntryRequest(zipArchiveEntry, streamSupplier));
        } else {
            scatterZipCreator.addArchiveEntry(zipArchiveEntry, streamSupplier);
        }
    }


    public void writeTo(final ZipArchiveOutputStream zipArchiveOutputStream)
            throws IOException, ExecutionException, InterruptedException {
        dirs.writeTo(zipArchiveOutputStream);
        dirs.close();
        scatterZipCreator.writeTo(zipArchiveOutputStream);
    }


    public String getRootPaths() {
        return rootPaths;
    }

    public void setRootPaths(String rootPath) {
        this.rootPaths = rootPaths;
    }


}
