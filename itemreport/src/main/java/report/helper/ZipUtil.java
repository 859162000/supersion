package report.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil {

	public static void zip(File inputFile, String zipFileName) {
		FileOutputStream out = null;
		ZipOutputStream zOut = null;
		try {
			//创建文件输出对象out,提示:注意中文支持
			out = new FileOutputStream(new String(zipFileName.getBytes("UTF-8")));
			//將文件輸出ZIP输出流接起来
			zOut = new ZipOutputStream(out);
			zip(zOut, inputFile, "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != zOut)
					zOut.close();
				if (null != out)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void zip(ZipOutputStream zOut, File file, String base) {
		try {
			// 如果文件句柄是目录
			if (file.isDirectory()) {
				base = (base.length() == 0 ? "" : base + "/");
				// 建立ZIP条目
				if (base != "")
					zOut.putNextEntry(new ZipEntry(base));
				//获取目录下的文件
				File[] listFiles = file.listFiles();
				// 遍历目录下文件
				for (int i = 0; i < listFiles.length; i++) {
					// 递归进入本方法
					zip(zOut, listFiles[i], base + listFiles[i].getName());
				}
			}
			// 如果文件句柄是文件
			else {
				if (base == "") {
					base = file.getName();
				}
				// 填入文件句柄
				zOut.putNextEntry(new ZipEntry(base));
				// 开始压缩
				// 从文件入流读,写入ZIP 出流
				writeFile(zOut, file);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeFile(ZipOutputStream zOut, File file) throws IOException {
		BufferedInputStream bi = new BufferedInputStream(new FileInputStream(file));
		//开始写入新的ZIP文件条目并将流定位到条目数据的开始处   
		byte[] buffer = new byte[1024];
		int readCount = bi.read(buffer);
		while (readCount != -1) {
			zOut.write(buffer, 0, readCount);
			readCount = bi.read(buffer);
		}
		//注，在使用缓冲流写压缩文件时，一个条件完后一定要刷新一把，不然可能有的内容就会存入到后面条目中去了   
		zOut.flush();
		//文件读完后关闭   
		bi.close();
	}


	public static void unZip(File zip, String outputDirectory) {
		int readedBytes;
		FileOutputStream fileOutStream = null;
		byte[] buff = new byte[1024];
		InputStream inputStream = null;
		ZipFile zipFile = null;
		try {
			createDirectory(outputDirectory, "");
			zipFile = new ZipFile(zip);
			for (Enumeration<ZipEntry> entries = zipFile.getEntries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String path = outputDirectory + "/" + entry.getName().replaceAll("\\\\", "/"); 
				if (entry.isDirectory()) {
					// 目录
					File decompressDirFile = new File(path);
					if (!decompressDirFile.exists()) {
						decompressDirFile.mkdirs();
					}
				} else {
					// 文件  
					String fileDir = path.substring(0, path.lastIndexOf("/"));  
	                File fileDirFile = new File(fileDir);  
	                if (!fileDirFile.exists()) {  
	                    fileDirFile.mkdirs();  
	                }  
					File f = new File(path);
					inputStream = zipFile.getInputStream(entry);
					fileOutStream = new FileOutputStream(f);
					while ((readedBytes = inputStream.read(buff)) > 0) {
						fileOutStream.write(buff, 0, readedBytes);
					}
					fileOutStream.close();
					inputStream.close();
				}
			}
			zipFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (fileOutStream != null)
					fileOutStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if (subDirectory == "" && fl.exists() != true)
				fl.mkdir();
			else if (subDirectory != "") {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (subFile.exists() == false)
						subFile.mkdir();
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
