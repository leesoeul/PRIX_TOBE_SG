package com.prix.homepage.user.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class PrixDataWriter {
	static String logdir = "E:\\PRIX\\logs\\db_error_";
	
	static public int write(String type, String name, InputStream is) throws java.sql.SQLException, java.io.FileNotFoundException, java.io.UnsupportedEncodingException {
		Connection conn = PrixConnector.getConnection();
		if (conn == null)
			return -1;

		int index = -1;
		String sql = "INSERT INTO px_data (type, name, content) values ('" + type + "', '" + name.replace("'", "\\\'") + "', ?)";
		String result = "OK";
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBinaryStream(1, is);
			//ps.setCharacterStream(1, new InputStreamReader(is, "UTF-8"));
			ps.executeUpdate();
			conn.commit();
			ps.close();

			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("SELECT max(id) FROM px_data;");
			if (rs.next())
				index = rs.getInt(1);
			rs.close();
			state.close();
		} catch (Exception e) {
			result = e.toString();
		} finally {
			conn.close();
		}

		Calendar cal = Calendar.getInstance();
		String date = "" + (cal.get(Calendar.YEAR));
		if (cal.get(Calendar.MONTH) < 10)
			date += "0";
		date += cal.get(Calendar.MONTH);
		date += cal.get(Calendar.DAY_OF_MONTH);
		PrintStream ps = new PrintStream(new FileOutputStream(logdir + date + ".log", true), false, "UTF-8");
		ps.println("[" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + "] " + sql);
		ps.println(result);
		ps.close();

		return index;
	}

	static public void replace(int id, InputStream is) throws java.sql.SQLException, java.io.FileNotFoundException, java.io.UnsupportedEncodingException {
		Connection conn = PrixConnector.getConnection();
		if (conn == null)
			return;

		int index = -1;
		String sql = "UPDATE px_data set content=? where id=" + id;
		String result = "OK";
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBinaryStream(1, is);
			//ps.setCharacterStream(1, new InputStreamReader(is, "UTF-8"));
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			result = e.toString();
		} finally {
			conn.close();
		}

		Calendar cal = Calendar.getInstance();
		String date = "" + (cal.get(Calendar.YEAR));
		if (cal.get(Calendar.MONTH) < 10)
			date += "0";
		date += cal.get(Calendar.MONTH);
		date += cal.get(Calendar.DAY_OF_MONTH);
		PrintStream ps = new PrintStream(new FileOutputStream("E:\\PRIX\\logs\\db_error_" + date + ".log", true), false, "UTF-8");
		ps.println("[" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + "] " + sql);
		ps.println(result);
		ps.close();
	}
}
