/*package report.service.imps;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import framework.helper.SmallTools;
import framework.show.ShowContext;

import report.helper.GetFilePath;

import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import autoETLsystem.service.interfaces.IActivityNodeForJavaExtend;

public class ClearFreeFile implements IActivityNodeForJavaExtend {

	private String path;
	private List<File> files = new ArrayList<File>();
	private Integer dateInterval = 2;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public void findFilesModifyAtYesterday() {
		File file = new File(path);
		isDirectory(file);
		iterator(file);
	}

	private void isDirectory(File file) {
		if (!file.isDirectory()) {
			throw new RuntimeException("Path[" + path + "] is not a directory");
		}
	}

	private void iterator(File file) {
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (File f : listFiles) {
				iterator(f);
			}
		} else if (checkInterval(file)) {
			files.add(file);
		}
	}

	private boolean checkInterval(File file) {
		Calendar last = Calendar.getInstance();
		last.setTime(new Date(file.lastModified()));
		cleanTime(last);

		Calendar today = Calendar.getInstance();
		addInterval(today);

		return last.compareTo(today) == 1;
	}

	private void cleanTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}

	private void addInterval(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)
				- dateInterval);
		cleanTime(calendar);
	}

	private String lastModified(long time) {
		return dateFormat.format(new Date(time));
	}

	// 设置间隔日期
	public void setDateInterval(Integer dateInterval) {
		this.dateInterval = dateInterval;
	}

	// 设置文件夹路径
	public void setPath(String path) {
		this.path = path;
	}

	// 获取所有结果
	public List<File> result() {
		return files;
	}

	public String Execute(
			List<AutoETL_WorkflowParamMV> activeNodeETLWorkflowParamMVList,
			String strFixParameter) throws Exception {
		ClearFreeFile modify = new ClearFreeFile();
		List<File> fileLists = new ArrayList<File>();
		for (Map.Entry<String, String> entry : ShowContext.getInstance()
				.getShowEntityMap().get("downloadResourcePathTime").entrySet()) {
			String day = entry.getKey();
			String path = entry.getValue();
			if (path.equals("tmpFilePath")) {
				modify.setPath(new GetFilePath().getTmpFilePath());
				modify.setDateInterval(Integer.parseInt(day));
				modify.findFilesModifyAtYesterday();
			} else if (path.equals("downloadResourcePath")) {
				modify.setPath(new GetFilePath().getDownloadResourcePath());
				modify.setDateInterval(Integer.parseInt(day));
				modify.findFilesModifyAtYesterday();
			} else {
				modify.setPath(path);
				modify.setDateInterval(Integer.parseInt(day));
				modify.findFilesModifyAtYesterday();
			}
			List<File> files = modify.result();
			fileLists.addAll(files);
		}

		SmallTools.delFileList(fileLists);
		return "执行成功，共清空文件：" + fileLists.size() + "个";
	}
}
*/
