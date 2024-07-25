package com.prix.homepage.livesearch.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prix.homepage.constants.PrixDataWriter;
import com.prix.homepage.livesearch.dao.DataMapper;
import com.prix.homepage.livesearch.dao.SearchLogMapper;
import com.prix.homepage.livesearch.dao.UserSettingMapper;
import com.prix.homepage.livesearch.pojo.Data;
import com.prix.homepage.livesearch.pojo.Modification;
import com.prix.homepage.livesearch.pojo.ProcessDto;
import com.prix.homepage.livesearch.service.ModificationService;
import com.prix.homepage.user.dao.DatabaseMapper;
import com.prix.homepage.user.pojo.Database;
import com.prix.homepage.user.pojo.Enzyme;
import com.prix.homepage.user.service.EnzymeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

/**
 * dbond에서 입력 받은 form을 바탕으로 db에 저장, file write하는 작업 수행
 */
@Service
@AllArgsConstructor
public class DbondProcessService {

	private final PrixDataWriter prixDataWriter;

	private final DataMapper dataMapper;
	private final EnzymeService enzymeService;
	private final ModificationService modificationService;
	private final UserSettingMapper userSettingMapper;
	private final SearchLogMapper searchLogMapper;
	private final DatabaseMapper databaseMapper;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ProcessDto process(String id,
			HttpServletRequest request, Map<String, String> paramsMap, MultipartFile[] multipartFiles) throws IOException {

		// initialization
		Integer idInt = Integer.parseInt(id);
		String user = "";
		String title = request.getParameter("title");
		String dataFormat = "";
		String instrument = "";
		String msResolution = "";
		String msmsResolution = "";
		String database = "";
		String decoy = "";
		String fastaDecoy = "";
		String enzyme = "";
		String missedCleavage = "";
		String minNumEnzTerm = "";
		String pTolerance = "";
		String pUnit = "";
		String fTolerance = "";
		String minMM = "";
		String maxMM = "";
		String minIE = "";
		String maxIE = "";
		String modMap = "";
		String multiStage = "";
		String cysteinAlkylation = "";
		String alkylationName = "";
		String alkylationMass = "";
		String engine = request.getParameter("engine");

		String msFile = "";
		String dbFile = "";
		String decoyFile = "";
		int msIndex = -10;
		int dbIndex = -1;
		int decoyIndex = -1;
		if (request.getParameter("mi") != null)
			msIndex = Integer.parseInt(request.getParameter("mi"));
		if (request.getParameter("di") != null)
			dbIndex = Integer.parseInt(request.getParameter("di"));

		String logPath = request.getParameter("log");
		String xmlPath = request.getParameter("xml");
		String msPath = request.getParameter("ms");
		String dbPath = request.getParameter("db");
		String decoyPath = request.getParameter("dec");
		String multiPath = request.getParameter("mul");

		String line = "";
		int rate = 0;
		boolean finished = false;
		boolean failed = false;
		String output = "";

		if (request.getParameter("execute") == null) {
			// final String dir = "/home/PRIX/data/";원래 이거임
			final String dir = "C:/Users/KYH/Desktop/";// 임시
			final String dbDir = "/usr/local/server/apache-tomcat-8.0.14/webapps/ROOT/config/";

			LocalDate date = LocalDate.now();
			String tempdate = String.valueOf(date);
			logger.info(tempdate);
			logPath = dir + "modi_output_" + id + "_" + tempdate + ".log";
			xmlPath = dir + "modi_input_" + id + "_" + tempdate + ".xml";
			msPath = dir + "ms_" + id + "_" + tempdate;
			dbPath = dir + "db_" + id + "_" + tempdate + ".fasta";
			decoyPath = dir + "decoy_" + id + "_" + tempdate + ".fasta";

			// form으로 제출된 param과 file을 paramsMap과 multipartFiles를 결합하여
			// 반복문을 돌려 params를 초기화하거나
			// msindex, dbindex 의 경우 각 path에서 파일을 읽어온다 존재하지 않으면 파일 작성
			// file 타입의 ms_file(ms/ms data), fasta(local db) 정보를 db에 저장, 로그 작성
			List<Object> combinedList = new ArrayList<>();
			for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
				combinedList.add(entry);
			}
			combinedList.addAll(Arrays.asList(multipartFiles));
			// 컬렉션을 반복하면서 params냐 file이냐에 따라 다른 작업 수행
			for (Object obj : combinedList) {
				String name = "";
				if (obj instanceof Map.Entry<?, ?>) {
					// Map.Entry<String, String> 타입의 요소를 처리
					Map.Entry<?, ?> entry = (Map.Entry<?, ?>) obj;
					name = (String) entry.getKey();
					String value = (String) entry.getValue();
					File file = new File("");

					switch (name) {
						case "user":
							user = value;
							break;
						case "title":
							title = value;
							break;
						case "ms_format":
							dataFormat = value;
							msPath += "." + dataFormat;
							break;
						case "ms_instrument":
							instrument = value;
							break;
						case "ms_resolution":
							msResolution = value;
							break;
						case "msms_resolution":
							msmsResolution = value;
							break;
						case "database":
							database = value;
							break;
						case "decoy":
							decoy = value;
							break;
						case "fasta_decoy":
							fastaDecoy = value;
							break;
						case "enzyme":
							enzyme = value;
							break;
						case "missed_cleavage":
							missedCleavage = value;
							break;
						case "ntt":
							minNumEnzTerm = value;
							break;
						case "pept_tolerance":
							pTolerance = value;
							break;
						case "unit":
							pUnit = value;
							break;
						case "frag_tolerance":
							fTolerance = value;
							break;
						case "min_isotope":
							minIE = value;
							break;
						case "max_isotope":
							maxIE = value;
							break;
						case "min_modified_mass":
							minMM = value;
							break;
						case "max_modified_mass":
							maxMM = value;
							break;
						case "modmap":
							modMap = value;
							break;
						case "multistage":
							multiStage = value;
							break;
						case "cystein_alkylation":
							cysteinAlkylation = value;
							break;
						case "alkylation_name":
							alkylationName = value;
							break;
						case "alkylation_mass":
							alkylationMass = value;
							break;
						case "engine":
							engine = value;
							break;
						case "msfile":
							msFile = value;
							break;
						case "dbfile":
							dbFile = value;
							break;
						case "mstype":
							dataFormat = value;
							msPath += "." + dataFormat;
							break;
						case "msindex":
							msIndex = Integer.parseInt(value);
							// write ms file
							file = new File(msPath);
							// 1. select name, content from px_data where id=" "msIndex"
							if (!file.exists()) {
								try {
									Data resultData = dataMapper.getNameContentById(msIndex);
									if (resultData != null) {
										try (InputStream is = new ByteArrayInputStream(resultData.getContent());
												FileOutputStream fos = new FileOutputStream(file);
												OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8")) {
											logger.warn("msindex write done");

											while (is.available() > 0) {
												writer.write(is.read());
											}
										} catch (IOException e) {
											logger.warn("error in writing file msIndex:{}", e.getMessage());
										}
									}
								} catch (NumberFormatException e) {
									logger.warn("error in writing file msIndex:{}", e.getMessage());
								}
							}
							break;
						case "dbindex":
							// 2. select name, content from px_data where id= "dbIndex"
							dbIndex = Integer.parseInt(value);

							logger.warn("dbindex start");

							// write db file
							file = new File(dbPath);
							if (!file.exists()) {
								try {
									Data resultData = dataMapper.getNameContentById(dbIndex);
									if (resultData != null) {
										try (InputStream is = new ByteArrayInputStream(resultData.getContent());
												FileOutputStream fos = new FileOutputStream(file);
												OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8")) {
											while (is.available() > 0) {
												writer.write(is.read());
											}
											logger.warn("dbindex done");

										} catch (IOException e) {
											logger.warn("error in writing file dbIndex:{}", e.getMessage());
										}
									}
								} catch (NumberFormatException e) {
									logger.warn("error in writing file dbIndex:{}", e.getMessage());
								}
							}
							break;
					}
				} else if (obj instanceof MultipartFile) {
					// MultipartFile 타입의 요소를 처리
					MultipartFile file = (MultipartFile) obj;
					name = file.getName();

					// 필드 이름에 따라 파일 처리 로직
					switch (name) {
						case "ms_file":
							msFile = file.getOriginalFilename();
							;
							if (file.getSize() > 209715200 * 2.5) {
								failed = true;
								output = "MS file size should not exceed 500MB.";
								break;
							}
							if (msFile.length() > 0) {
								try {
									msIndex = prixDataWriter.write(dataFormat, msFile.replace('\\', '/'), file.getInputStream());
									try (FileOutputStream fos = new FileOutputStream(msPath);
											OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
											InputStream is = file.getInputStream()) {
										while (is.available() > 0) {
											writer.write(is.read());
										}
									}
									logger.warn("msfile done");

								} catch (Exception e) {
									logger.warn("error in writing ms_file:{}", e.getMessage());
								}
							}
							break;
						case "fasta":
							// fasta에 해당하는 파일 처리
							dbFile = file.getOriginalFilename();

							if (file.getSize() > 52428800) {
								failed = true;
								output = "Database file size should not exceed 50MB.";
								break;
							}
							if (dbFile.length() > 0) {
								try {
									dbIndex = prixDataWriter.write("fasta", dbFile.replace('\\', '/'), file.getInputStream());
									try (FileOutputStream fos = new FileOutputStream(dbPath);
											OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
											InputStream is = file.getInputStream()) {
										while (is.available() > 0) {
											writer.write(is.read());
										}
										logger.warn("fasta done");

									}
								} catch (Exception e) {
									logger.warn("error in writing fasta:{}", e.getMessage());
								}
							}
							break;
						case "user_decoy":
							decoyFile = file.getOriginalFilename();
							if (decoyFile.length() > 0) {

							}
						default:
							logger.warn("Unknown file parameter: " + name);
							break;
					}
				}
			} // for (Object obj : combinedList) end

			// form으로 제출된 정보로 값 할당, 파일 작성, db 저장 및 로그 작성 과정이 정상적으로 완료되면 xml을 작섣한다
			if (!failed) {

				PrintStream ps = new PrintStream(new FileOutputStream(xmlPath), false, "UTF-8");
				ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				if (title == null || title.length() == 0)
					title = "prix" + msIndex;
				ps.println("<search user=\"" + user + "\" title = \"" + title + "\">");
				ps.println("\t<dataset name=\"" + msFile + "\" format=\"" + dataFormat + "\" instrument=\"" + instrument
						+ "\" local_path=\"" + msPath + "\" db_index=\"" + msIndex + "\"/>");
				if (dbFile.length() > 0)
					ps.println(
							"\t<database name=\"" + dbFile + "\" local_path=\"" + dbPath + "\" db_index=\"" + dbIndex + "\"/>");
				else {
					String dbName = "";
					Integer intDatabase = null;
					if (!database.trim().equals(""))
						intDatabase = Integer.parseInt(database);
					Database databaseResult = databaseMapper.selectById(intDatabase);
					if (databaseResult != null) {
						dbIndex = databaseResult.getDataId();
						dbName = databaseResult.getFile();
					}
					ps.println("\t<database name=\"" + dbName + "\" local_path=\"" + dbDir + dbName + "\" db_index=\"" + dbIndex
							+ "\"/>");
				}

				Integer enzymeInt = null;
				Enzyme queryResultEnzyme = null;
				if (!enzyme.trim().equals("")) {
					enzymeInt = Integer.parseInt(enzyme);
					logger.info("enzyme int : " + enzymeInt);
					queryResultEnzyme = enzymeService.selectById(enzymeInt);
				}
				if (queryResultEnzyme != null) {
					if (queryResultEnzyme.getNtCleave().length() == 0) {
						ps.println("\t<enzyme name=\"" + queryResultEnzyme.getName() + "\" cut=\"" + queryResultEnzyme.getCtCleave()
								+ "\" sence=\"C\"/>");
					} else {
						ps.println("\t<enzyme name=\"" + queryResultEnzyme.getName() + "\" cut=\"" + queryResultEnzyme.getNtCleave()
								+ "\" sence=\"N\"/>");
					}
				}

				ps.println("\t<parameters>");
				ps.println("\t\t<enzyme_constraint max_miss_cleavages=\"" + missedCleavage + "\" min_number_termini=\""
						+ minNumEnzTerm + "\"/>");
				ps.println("\t\t<peptide_mass_tol value=\"" + pTolerance + "\" unit=\"" + pUnit + "\"/>");
				ps.println("\t\t<fragment_ion_tol value=\"" + fTolerance + "\" unit=\"Da\"/>");
				ps.println("\t\t<modified_mass_range min_value=\"" + minMM + "\" max_value=\"" + maxMM + "\"/>");
				ps.println("\t</parameters>");
				if (decoyFile.length() > 0)
					ps.println("\t<decoy_search name=\"" + decoyFile + "\" local_path=\"" + decoyPath + "\"/>");
				else if (decoy.length() > 0)
					ps.println("\t<decoy_search checked=\"1\"/>");
				if (engine.compareTo("dbond") != 0)
					ps.println("\t<instrument_resolution ms=\"" + msResolution + "\" msms=\"" + msmsResolution + "\"/>");
				if (modMap.length() > 0)
					ps.println("\t<mod_map checked=\"1\"/>");
				if (multiStage.length() > 0) {
					ps.println("\t<multistages_search checked=\"1\" program=\"\"/>");
					multiPath = msPath + ".fasta";
				}
				if (cysteinAlkylation.length() > 0) {
					if (cysteinAlkylation.compareTo("Direct input") == 0) {
						ps.println("\t<cys_alkylated name=\"" + alkylationName + "\" massdiff=\"" + alkylationMass + "\"/>");
					} else {
						Double queryResultMassDiff = modificationService.selectMassDiffByName(cysteinAlkylation);
						if (queryResultMassDiff != null) {
							ps.println(
									"\t<cys_alkylated name=\"" + cysteinAlkylation + "\" massdiff=\"" + queryResultMassDiff + "\"/>");
						}
					}
				}
				ps.println("\t<modifications>");
				ps.println("\t\t<fixed>");

				Integer isDbondEngine = engine.compareTo("dbond") == 0 ? 1 : 0;
				List<Modification> queryResultFixedMod = modificationService.selectFixedModInUserMod(idInt, isDbondEngine);
				for (Modification tmp : queryResultFixedMod) {
					ps.println("\t\t\t<mod name=\"" + tmp.getName() + "\" site=\"" + tmp.getResidue() + "\" position=\""
							+ tmp.getPosition() + "\" massdiff=\"" + tmp.getMassDiff() + "\"/>");
				}
				ps.println("\t\t</fixed>");
				ps.println("\t\t<variable>");
				List<Modification> queryResultVardMod = modificationService.selectVarModInUserMod(idInt, isDbondEngine);
				for (Modification tmp : queryResultVardMod) {
					ps.println("\t\t\t<mod name=\"" + tmp.getName() + "\" site=\"" + tmp.getResidue() + "\" position=\""
							+ tmp.getPosition() + "\" massdiff=\"" + tmp.getMassDiff() + "\"/>");
				}

				ps.println("\t\t</variable>");
				ps.println("\t</modifications>");
				ps.println("</search>");
				ps.flush();
				ps.close();

				// record user setting
				boolean exist = false;
				if (userSettingMapper.existsByUserId(idInt) != null) {
					exist = true;
				}
				if (missedCleavage.trim() != "") {
					Integer missedCleavageInt = Integer.parseInt(missedCleavage);
					Float pTolFloat = null;
					Float fTolFloat = null;
					if (pTolerance.trim() != null && fTolerance.trim() != null) {
						pTolFloat = Float.parseFloat(pTolerance);
						fTolFloat = Float.parseFloat(fTolerance);

						if (engine.compareTo("dbond") == 0) {
							if (exist) {
								userSettingMapper.updateByUserIdDbond(idInt, enzymeInt, missedCleavageInt, pTolFloat, pUnit, fTolFloat,
										engine, dataFormat, instrument);
							} else {
								try {
									userSettingMapper.insertDbond(idInt, enzymeInt, missedCleavageInt, pTolFloat, pUnit, fTolFloat,
											engine, dataFormat, instrument);
								} catch (Exception e) {
									logger.warn("error inserting usersetting : {}", e.getMessage());
								}
							}
						} else {
							Integer minNumEnzTermInt = Integer.parseInt(minNumEnzTerm);
							Float minMMFloat = Float.parseFloat(minMM);
							Float maxMMFloat = Float.parseFloat(maxMM);
							if (exist) {
								userSettingMapper.updateByUserId(idInt, enzymeInt, missedCleavageInt, minNumEnzTermInt, pTolFloat,
										pUnit,
										fTolFloat, minMMFloat, maxMMFloat, engine, dataFormat, instrument, msResolution, msmsResolution);
							} else {
								userSettingMapper.insert(idInt, enzymeInt, missedCleavageInt, minNumEnzTermInt, pTolFloat, pUnit,
										fTolFloat,
										minMMFloat, maxMMFloat, engine, dataFormat, instrument, msResolution, msmsResolution);
							}
						}

						Runtime runtime = Runtime.getRuntime();
						// 이게 원본임 아마 linux환경인듯 2024
						// String[] command = {"/bin/bash", "-c", String.format("%s%s %s > %s", "java
						// -Xmx2000M -cp
						// /usr/local/server/apache-tomcat-8.0.14/webapps/ROOT/WEB-INF/lib/engine.jar:/usr/local/server/apache-tomcat-8.0.14/webapps/ROOT/WEB-INF/lib/jdom.jar:/usr/local/server/apache-tomcat-8.0.14/webapps/ROOT/WEB-INF/lib/jrap_StAX_v5.2.jar:/usr/local/server/apache-tomcat-8.0.14/webapps/ROOT/WEB-INF/lib/xercesImpl.jar
						// prix.Prix_", engine, xmlPath, logPath) };
						String[] command = { "cmd.exe", "/c", "java -cp C:/Users/KYH/Desktop/Server.jar PrixUser", engine, xmlPath,
								logPath };
						Process process = runtime.exec(command);
					}
				}
			}
		} else {

			int prixIndex = -1;

			// check modeye process
			FileInputStream fis = new FileInputStream(logPath);
			StringWriter writer = new StringWriter();
			StringWriter allWriter = new StringWriter();
			while (fis.available() > 0) {
				char c = (char) fis.read();
				allWriter.append(c);
				if (c == '\n') {
					if (writer.toString().length() > 0) {
						line = writer.toString();
						writer = new StringWriter();
					}
				} else
					writer.append(c);
			}

			if (writer.toString().length() > 0)
				line = writer.toString();

			fis.close();
			output = allWriter.toString();

			if (line.indexOf("Error") >= 0 || line.indexOf("Exception") >= 0) {
				failed = true;
			} else if (line.startsWith("Elapsed Time")) {
				finished = true;

				// insert prix file
				String prixPath = msPath.substring(0, msPath.length() - 3) + "prix";
				File file = new File(prixPath);
				try {
					fis = new FileInputStream(file);
					prixDataWriter.replace(prixIndex, fis);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}

				// record history
				searchLogMapper.insert(idInt, title.replace("'", "\\\'"), msIndex, dbIndex, prixIndex, engine);
				// delete files
			} else if (line.startsWith("Processing")) {
				int split = line.indexOf("/");
				int numerator = Integer.parseInt(line.substring(11, split));
				int denominator = Integer.parseInt(line.substring(split + 1, line.indexOf(" ", split)));
				rate = 100 * numerator / denominator;
			}

			if (finished) {
				// multistage work
				if (multiPath != null && multiPath.length() > 0 && multiPath.compareTo("null") != 0) {

					Integer databaseId = databaseMapper.selectIdByDataId(dbIndex);
					if (databaseId == null) {
						try {
							FileInputStream is = new FileInputStream(multiPath);
							prixDataWriter.replace(dbIndex, is);
							is.close();
						} catch (Exception e) {
							logger.error(e.getMessage());
						}

					}
				}
				// response.sendRedirect("result.jsp?file=" + jobCode);
				ProcessDto processDto = ProcessDto.builder()
						.finished(finished).failed(failed)
						.logPath(logPath).xmlPath(xmlPath).msPath(msPath).dbPath(dbPath).decoyPath(decoyPath)
						.title(title).msIndex(msIndex).dbIndex(dbIndex).multiPath(multiPath).engine(engine)
						.output(output)
						.rate(rate)
						.build();
				return processDto;
			}
		}
		// 별 문제 없으면 livesearch/process를 반환(컨트롤러에서 리턴할 값) process가 modplus외에도 사용될 경우 수정
		// 필요할 수 있음
		ProcessDto processDto = ProcessDto.builder()
				.finished(finished).failed(failed)
				.logPath(logPath).xmlPath(xmlPath).msPath(msPath).dbPath(dbPath).decoyPath(decoyPath)
				.title(title).msIndex(msIndex).dbIndex(dbIndex).multiPath(multiPath).engine(engine)
				.output(output)
				.rate(rate)
				.returnAddr("livesearch/process").build();

		return processDto;
	}
}