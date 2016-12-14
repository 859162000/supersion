package com.icfcc.batch.validator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import org.apache.log4j.Category;

import com.icfcc.batch.BatchContext;
import com.icfcc.batch.BatchException;
import com.icfcc.batch.Constants;
import com.icfcc.batch.config.BatchConfig;
import com.icfcc.batch.core.BatchTask;
import com.icfcc.batch.core.Message;
import com.icfcc.batch.core.MessageDescriptor;
import com.icfcc.batch.core.Segment;
import com.icfcc.batch.process.MsgHandler;
import com.icfcc.batch.process.reader.LineReader;
import com.icfcc.batch.util.LogConfiger;

public class PreMsgChecker extends Observable implements MsgHandler {
	Category _log;

	public PreMsgChecker() {
		this._log = LogConfiger.getLoger();
	}

	public List handlerMsg(String msgFile, String badFile,String passFile, int returnWay) throws BatchException {
		List resultDates=new ArrayList();
		String message="";
		String[][] result = null;
		try{
			this._log.info("开始准备共享数据");
			Message msg = (Message) BatchContext.getInstance().getProperites(
					"com.icfcc.batch.core.Message");

			if (msg == null) {
				message="无法获取当前处理的报文对象;";
				throw new BatchException(11111, "无法获取当前处理的报文对象");
			}
			String version = msg.getVersion();
			int msgtype = BatchConfig.getInstance().getTasktype();
			String key = String.valueOf(msgtype) + "." + version;
			MessageDescriptor descriptor = MessageDescriptor.getInstance(key);
			if (descriptor == null) {
				message="无法获取当前处理的报文对象的报文描述对象;";
				throw new BatchException(11112, "无法获取当前处理的报文对象的报文描述对象");
			}

			MsgValidator msgValidator = descriptor.getMsgValidator();

			if (msgValidator == null) {
				message="无法获取当前处理的报文对象的报文检查对象;";
				throw new BatchException(11113, "无法获取当前处理的报文对象的报文检查对象");
			}
			RecordValidator recordValidator = msgValidator.getRecordValidator();

			if (recordValidator == null) {
				message="无法获取当前处理的报文对象的报文检查对象;";
				throw new BatchException(11114, "无法获取当前处理的报文对象的记录检查对象");
			}

			CheckResult checkResult = new CheckResult();
			checkResult.setMessageDescriptor(descriptor);
			checkResult.resetStatus();
			BatchContext.getInstance().addPropetiry("message_process_result",
					checkResult);
			Segment headerSegment = msg.getHeadSegment();
			if (headerSegment == null) {
				message="无法获取当前报文对象的报文头;";
				throw new BatchException(11112, "无法获取当前报文对象的报文头");
			}
			recordValidator.reset();
			String fileName = new File(msg.getFilePath()).getName();
			Map sharedDataMap = new HashMap();
			sharedDataMap.put("fileName", fileName);
			sharedDataMap.put("dataYearMonth", fileName.substring(14, 20));
			SegmentValidator segmentChecker = (SegmentValidator) recordValidator
					.getSegmentCheckMap().get(
							headerSegment.getSegmentDesc().getSegmentType());
			if (segmentChecker != null) {
				List sharedData = segmentChecker.getSharedDataList();
				for (Iterator it = sharedData.iterator(); it.hasNext();) {
					SharedData data = (SharedData) it.next();
					String dataSource = data.getSourceData();
					StringTokenizer token = new StringTokenizer(dataSource, "{}");
					while (token.hasMoreTokens()) {
						String splitString = token.nextToken();

						if (splitString.startsWith("$")) {
							splitString = headerSegment.getFieldValue(splitString
									.substring(1));
						}
						String dateName = data.getName();
						sharedDataMap.put(dateName, splitString);
					}
				}

				recordValidator.setSharedData(sharedDataMap);
			}

			String headerResult = segmentChecker.validate(headerSegment, 2);
			try {
				int expctedNumber = new Integer(headerSegment.getFieldValue(
						"TotalRecords").trim()).intValue();
				int lineNumber = getFileLines(msg.getFilePath());
				System.out.println(" reallines:" + lineNumber);
				if (lineNumber != expctedNumber + 2){
					headerResult = headerResult + "［报文头记录数］与实际记录数不符合";
					message="［报文头记录数］与实际记录数不符合;";
				}
			} catch (NumberFormatException e) {
				message="［报文头记录数］与实际记录数不符合;";
				headerResult = headerResult + "报文头［帐户记录数总数］必须为整数";
			}

			BatchContext.getInstance().addPropetiry("header_checker_result",
					headerResult);

			if ((headerResult != null) && (headerResult.length() > 0)) {
				throw new BatchException(1230, headerResult);
			}

			recordValidator.setExptecedCount(Integer.parseInt(headerSegment
					.getFieldValue("TotalRecords").trim()));

			BatchTask task = (BatchTask) BatchContext.getInstance().getProperites(
					Constants.CURRENT_TASK);

			String oldFullPath = LogConfiger
					.formatFilePath(msgFile);
			String oldPath = oldFullPath.substring(0,
					oldFullPath.lastIndexOf("/") + 1);

			recordValidator.setSharedData(sharedDataMap);

			File f1 = new File(oldPath);
			if (f1.isDirectory()) {
				String[] s = f1.list();

				for (int i = 0; i < s.length; ++i) {
					if (s[i].indexOf("log." + msg.getMsgID()) != -1) {
						new File(oldPath + s[i]).delete();
					}

					if (s[i].indexOf("txt." + msg.getMsgID()) != -1) {
						new File(oldPath + s[i]).delete();
					}

				}

			}

			byte[] headbytes = (byte[]) BatchContext.getInstance().getProperites(
					Constants.DECRYPT_HEADBYTES);

			boolean bPrecheck = false;
			if ((headbytes != null) && (headbytes[0] == 49)) {
				bPrecheck = true;
			}

			int checkMode = BatchConfig.getInstance().getCheckMode();
			boolean checkFormat = true;
			switch (checkMode) {
			case 3:
				checkFormat = false;
				break;
			case 2:
				checkFormat = !(bPrecheck);
				break;
			default:
				checkFormat = true;
			}
			BatchContext.getInstance().addPropetiry("check_format_opition",
					"" + checkFormat);
			result = msgValidator.validate(msgFile, badFile, passFile, returnWay);
			resultDates.add(message);
			resultDates.add(result);
			return resultDates;
		}catch(Exception e){
			resultDates.add(message);
			resultDates.add(result);
			return resultDates;
		}
		
	}

	private int getFileLines(String fileName) {
		int lineNumber = -1;
		try {
			LineReader reader = new LineReader(fileName);
			return reader.getLineNumber();
		} catch (Exception ex) {
			return lineNumber;
		}
	}

	public void addObserver(Observer observer) {
		super.addObserver(observer);
	}
}
