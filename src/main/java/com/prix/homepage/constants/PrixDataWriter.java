package com.prix.homepage.constants;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prix.homepage.livesearch.dao.DataMapper;

/**
 * px_data에 저장하고 log를 작성하는 작업 수행
 */
@Service
public class PrixDataWriter {
	// static String logdir = "E:\\PRIX\\logs\\db_error_"; 이게 원래 진짜임 2024.05.27
	String logdir = "C:/Users/KYH/Desktop/dbond/log/db_error_/";


		private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DataMapper dataMapper;

	public int write(String type, String name, InputStream is)
			throws java.sql.SQLException, java.io.FileNotFoundException, java.io.UnsupportedEncodingException {
		int index = -1;
		String result = "OK";
		byte[] content = null;
		try {
			logger.info("name", name);
			logger.info("name.replace:",name.replace("'", "\\\'") );

			content = is.readAllBytes();
			dataMapper.insert(type, name.replace("'", "\\\'"), content);

			index = dataMapper.getMaxId();
		} catch (Exception e) {
			result = e.toString();
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		String date = "" + (cal.get(Calendar.YEAR));
		if (cal.get(Calendar.MONTH) < 10)
			date += "0";
		date += cal.get(Calendar.MONTH)+1;
		date += cal.get(Calendar.DAY_OF_MONTH);
		PrintStream ps = new PrintStream(new FileOutputStream(logdir + date + ".log", true), false, "UTF-8");
		String sql = "INSERT INTO px_data (type, name, content) values ('" + type + "', '" + name.replace("'", "\\\'")
				+ "', content";
		ps.println("[" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND)
				+ "] " + sql);
		ps.println(result);
		ps.close();

		return index;
	}

	public void replace(int id, InputStream is)
			throws java.sql.SQLException, java.io.FileNotFoundException, java.io.UnsupportedEncodingException {
		String result = "OK";
		try {
			byte[] content = is.readAllBytes();
			dataMapper.updateContent(id, content);
		} catch (Exception e) {
			result = e.toString();
		}

		Calendar cal = Calendar.getInstance();
		String date = "" + (cal.get(Calendar.YEAR));
		if (cal.get(Calendar.MONTH) < 10)
			date += "0";
		date += cal.get(Calendar.MONTH)+1;
		date += cal.get(Calendar.DAY_OF_MONTH);
		// PrintStream ps = new PrintStream(new FileOutputStream("E:\\PRIX\\logs\\db_error_" + date + ".log", true), false,
		// 		"UTF-8");
				PrintStream ps = new PrintStream(new FileOutputStream(logdir + date + ".log", true), false,
				"UTF-8");
		String sql = "UPDATE px_data set content=? where id=" + id;

		ps.println("[" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND)
				+ "] " + sql);
		ps.println(result);
		ps.close();
	}
}
