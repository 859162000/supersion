package framework.services.interfaces;

import java.io.InputStream;

public interface IFileHandler {
	//完成未完成的方法
	DownloadResult GetStreamResultFromPath(String path, Integer bufferSize) throws Exception;
	DownloadResult GetStreamResultFromRealPath(String path, String strFileName,Integer bufferSize) throws Exception;
	DownloadResult GetStreamResultFromBytes(byte[] byteData, String fileName, Integer bufferSize) throws Exception;
	DownloadResult GetStreamResultFromInputStream(InputStream inputStream, String fileName, Integer bufferSize) throws Exception;
	DownloadResult GetStreamResultFromPath(String[] path, String fileName, Integer bufferSize) throws Exception; // 打包下载，未完成
}