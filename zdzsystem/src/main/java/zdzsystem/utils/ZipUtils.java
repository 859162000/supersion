package zdzsystem.utils;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author veggieg
 * @since 2016-09-23 17:10
 */
public class ZipUtils {
    public static int BUFFER_SIZE = 2048;

    /**
     * @param zipfile
     * @param destDir
     * @return
     * @throws IOException
     */
    public static List<String> unZip(File zipfile, String destDir) throws IOException {
        if (StringUtils.isBlank(destDir)) {
            destDir = zipfile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        ZipArchiveInputStream is = null;
        List<String> fileNames = new ArrayList<String>();
        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipfile), BUFFER_SIZE));
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null) {
                fileNames.add(entry.getName());
                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());
                    directory.mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(new FileOutputStream(new File(destDir, entry.getName())), BUFFER_SIZE);
                        IOUtils.copy(is, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
        } finally {
            IOUtils.closeQuietly(is);
        }

        return fileNames;
    }

    /**
     * @param dir
     * @param zip
     */
    public static void zip(String dir, String zip) throws IOException {
        List<String> paths = getFiles(dir);
        compressFilesZip(paths.toArray(new String[paths.size()]), zip, dir);
    }

    /**
     * 递归获取目录下所有文件、文件夹
     *
     * @param dir
     * @return
     */
    public static List<String> getFiles(String dir) {
        List<String> lstFiles = new ArrayList<String>();
        File file = new File(dir);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                lstFiles.add(f.getAbsolutePath());
                lstFiles.addAll(getFiles(f.getAbsolutePath()));
            } else {
                String str = f.getAbsolutePath();
                lstFiles.add(str);
            }
        }

        return lstFiles;
    }

    private static void compressFilesZip(String[] files, String zipFilePath, String dir) throws IOException {
        if (files == null || files.length <= 0) {
            return;
        }
        ZipArchiveOutputStream os = null;
        try {
            File zipFile = new File(zipFilePath);
            os = new ZipArchiveOutputStream(zipFile);
            os.setUseZip64(Zip64Mode.AsNeeded);

            if (dir.endsWith(File.separator)) {// @see getFilePathName
                dir = dir.substring(0, dir.lastIndexOf(File.separator));
            }

            // 将每个文件用ZipArchiveEntry封装, 再用ZipArchiveOutputStream写到压缩文件中
            for (String strfile : files) {
                File file = new File(strfile);

                String name = getFilePathName(dir, strfile);

                ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, name);
                os.putArchiveEntry(zipArchiveEntry);
                if (file.isDirectory()) {
                    continue;
                }
                InputStream is = null;
                try {
                    is = new BufferedInputStream(new FileInputStream(file));
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        //把缓冲区的字节写入到ZipArchiveEntry
                        os.write(buffer, 0, len);
                    }
                    os.closeArchiveEntry();
                } finally {
                    IOUtils.closeQuietly(is);
                }
            }

            os.finish();
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    private static String getFilePathName(String dir, String path) {
        String p = path.replace(dir + File.separator, "");
        p = p.replace("\\", "/");
        return p;
    }
}
