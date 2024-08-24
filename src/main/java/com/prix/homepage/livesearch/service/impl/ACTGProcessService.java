package com.prix.homepage.livesearch.service.impl;

import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Map;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prix.homepage.configuration.GlobalProperties;
import com.prix.homepage.livesearch.dao.SearchLogMapper;
import com.prix.homepage.livesearch.pojo.ACTGProcessDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

/**
 * actg에서 입력 받은 form을 바탕으로 db에 저장, file write하는 작업 수행
 */
@Service
@AllArgsConstructor
public class ACTGProcessService {

  private final SearchLogMapper searchLogMapper;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final GlobalProperties globalProperties;

  public ACTGProcessDto process(String id,
      HttpServletRequest request, Map<String, String> paramsMap, MultipartFile[] multipartFiles) throws IOException {

    String user = "";
    String title = request.getParameter("title");
    // Environment
    String method = "";
    String peptideFile = "";
    String IL = "";

    // Protein DB
    String proteinDB = "";
    String SAV = "";

    // Variant Splice Graph DB
    String variantSpliceGraphDB = "";
    String mutation = "";
    String mutationFile = "";
    String exonSkipping = "";
    String altAD = "";
    String intron = "";

    // Six-frame translation
    String referenceGenome = "";

    Enumeration params = request.getAttributeNames();
    while (params.hasMoreElements()) {
      String name = (String) params.nextElement();
    }

    String processName = request.getParameter("process");

    String line = "";
    String rate = "0";
    boolean finished = false;
    boolean failed = false;
    String output = "";

    //결과 zip 파일 및 로그 저장 경로
    final String logDir = globalProperties.getActgLogDir();
    //DB 주소
    final String dbDir = globalProperties.getActgDbDir();

    String processPath = logDir + processName;

    if (request.getParameter("execute") == null) {

      Instant instant = Instant.now();
      long timestamp = instant.toEpochMilli(); // 밀리초 단위의 타임스탬프
      String key = id + "_" + timestamp;
      processPath = "process_" + key + ".proc";
      processName = processPath;
      String xmlPath = "param_" + key + ".xml";
      String proteinDBPath = "";
      String peptideFilePath = "";
      String variantSpliceGraphDBPath = "";
      String mutationFilePath = "";
      String referenceGenomePath = "";
      String outputPath = logDir;

      // form으로 제출된 param과 file을 paramsMap과 multipartFiles를 결합하여
      // 반복문을 돌려 params를 초기화하거나
      // peptideFile, mutationFile 의 경우 각 path에서 파일을 읽어온다 존재하지 않으면 파일 작성
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

          switch (name) {
            case "user":
              user = value;
              break;
            case "title":
              title = value;
              break;
            case "method":
              method = value;
              break;
            case "IL":
              IL = value;
              break;
            case "proteinDB":
              proteinDB = value;
              proteinDBPath = dbDir + proteinDB;
              break;
            case "SAV":
              SAV = value;
              break;
            case "variantSpliceGraphDB":
              variantSpliceGraphDB = value;
              variantSpliceGraphDBPath = dbDir + variantSpliceGraphDB;
              break;
            case "mutation":
              mutation = value;
              break;
            case "exonSkipping":
              exonSkipping = value;
              break;
            case "altAD":
              altAD = value;
              break;
            case "intron":
              intron = value;
              break;
            case "referenceGenome":
              referenceGenome = value;
              referenceGenomePath = dbDir + referenceGenome;
              break;
          }
        } // paramMap
        else if (obj instanceof MultipartFile) {
          // MultipartFile 타입의 요소를 처리
          MultipartFile file = (MultipartFile) obj;
          name = file.getName();

          // 필드 이름에 따라 파일 처리 로직
          switch (name) {
            case "peptideFile":
              peptideFile = "peptide_" + key + ".txt";
              peptideFilePath = logDir + peptideFile;

              if (file.getSize() > 1024 * 100) {
                failed = true;
                output = "The size of peptide list should not exceed 1KB.";
                break;
              }
              if (file.getOriginalFilename().length() > 0) {
                try {
                  try (FileOutputStream fos = new FileOutputStream(peptideFilePath);
                      OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
                      InputStream is = file.getInputStream()) {
                    while (is.available() > 0) {
                      writer.write(is.read());
                    }
                  }
                  logger.info("peptideFile in actg process service done");

                } catch (Exception e) {
                  logger.warn("error in writing peptideFile:{}", e.getMessage());
                }
              }
              break;
            case "mutationFile":
              mutationFile = "mutation_" + key + ".txt";
              mutationFilePath = logDir + mutationFile;

              if (file.getSize() > 20971520) {
                failed = true;
                output = "The size of VCF should not exceed 20MB.";
                break;
              }
              if (file.getOriginalFilename().length() > 0) {
                try {
                  try (FileOutputStream fos = new FileOutputStream(mutationFilePath);
                      OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
                      InputStream is = file.getInputStream()) {
                    while (is.available() > 0) {
                      writer.write(is.read());
                    }
                  }
                  logger.info("mutationFile in actg process service done");

                } catch (Exception e) {
                  logger.warn("error in writing mutationFile:{}", e.getMessage());
                }
              }
              break;
            default:
              logger.warn("Unknown file parameter: " + name);
              break;
          }
        }
        // XML maker
        try {
          // template.xml이라는 파일 필요 2024
          FileReader FR = new FileReader(dbDir + "template.xml");
          BufferedReader BR = new BufferedReader(FR);

          FileWriter FW = new FileWriter(logDir + xmlPath);
          BufferedWriter BW = new BufferedWriter(FW);

          String line_ = null;

          while ((line_ = BR.readLine()) != null) {
            if (line_.contains("[METHOD]")) {
              line_ = line_.replace("[METHOD]", method);
            } else if (line_.contains("[IL]")) {
              line_ = line_.replace("[IL]", IL);
            } else if (line_.contains("[PEPTIDE_FILE]")) {
              line_ = line_.replace("[PEPTIDE_FILE]", peptideFilePath);
            } else if (line_.contains("[PROTEIN_DB]")) {
              line_ = line_.replace("[PROTEIN_DB]", proteinDBPath);
            } else if (line_.contains("[SAV]")) {
              line_ = line_.replace("[SAV]", SAV);
            } else if (line_.contains("[VARIANT_SPLICE_GRAPH_DB]")) {
              line_ = line_.replace("[VARIANT_SPLICE_GRAPH_DB]", variantSpliceGraphDBPath);
            } else if (line_.contains("[ALT_AD]")) {
              line_ = line_.replace("[ALT_AD]", altAD);
            } else if (line_.contains("[EXON_SKIPPING]")) {
              line_ = line_.replace("[EXON_SKIPPING]", exonSkipping);
            } else if (line_.contains("[INTRON]")) {
              line_ = line_.replace("[INTRON]", intron);
            } else if (line_.contains("[MUTATION]")) {
              line_ = line_.replace("[MUTATION]", mutation);
            } else if (line_.contains("[MUTATION_FILE]")) {
              line_ = line_.replace("[MUTATION_FILE]", mutationFilePath);
            } else if (line_.contains("[REFERENCE_GENOME]")) {
              line_ = line_.replace("[REFERENCE_GENOME]", referenceGenomePath);
            } else if (line_.contains("[OUTPUT]")) {
              line_ = line_.replace("[OUTPUT]", outputPath);
            }

            BW.append(line_);
            BW.newLine();
          }

          BR.close();
          FR.close();
          BW.close();
          FW.close();
        } catch (IOException e) {
          output = e + "\n";
        }
      } // for (Object obj : combinedList)

      if (!failed) {
        System.out.println("Reached cmd part");

        Runtime runtime = Runtime.getRuntime();

        // 원본 코드
        // String[] command = { "/bin/bash", "-c",
        // "java -Xmx10G -jar
        // /usr/local/server/apache-tomcat-8.0.14/webapps/ROOT/ACTG/ACTG_Search.jar " +
        // logDir
        // + xmlPath + " " + logDir + processPath };


        //아래 ACTG_Search.jar 실행 명령중에 -Xss2M은 원본 코드에 없습니다. 공간 부족으로 Stackoverflow가 발생하여 방지하고자 크기를 늘려 실행합니다.
        String jarDir = globalProperties.getActgJarDir();
        String jarPath = jarDir + "ACTG_Search.jar";
        String[] command = { "cmd.exe", "/c",
            "java -Xss2M -Xmx10G -jar " + jarPath + " " + logDir + xmlPath + " " + logDir + processPath };
        Process process = runtime.exec(command);


        //아래는 ACTG_Search.jar의 디버깅 코드입니다

        /* BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println("Stdout: " + s);
        }

        while ((s = stdError.readLine()) != null) {
            System.out.println("Stderr: " + s);
        } */
      }
    } // if (request.getParameter("execute") == null)
    else // process에서 redirect 받으면서 새로고침 반복해서 일어나는데 이때 이 작업 수행
    {
      System.out.println("Reached else statement");
      FileInputStream fis = new FileInputStream(processPath);
      StringWriter writer = new StringWriter();
      StringWriter allWriter = new StringWriter();
      while (fis.available() > 0) {
        char c = (char) fis.read();
        if (c == '\n') {
          line = writer.toString();

          if (line.indexOf("ERROR") >= 0 || line.indexOf("Exception") >= 0) {
            System.out.println("failed");
            failed = true;
          } else if (line.startsWith("Elapsed Time")) {
            System.out.println("finished");
            finished = true;
          }

          if (line.contains(logDir)) {
            line = line.replace(logDir, "");
          }
          if (line.contains(dbDir)) {
            line = line.replace(dbDir, "");
          }

          if (line.contains("%")) {
            rate = line;
          } else {
            allWriter.append(line + "\n");
          }
          writer = new StringWriter();
        } else {
          writer.append(c);
        }

      }

      if (writer.toString().length() > 0)
        line = writer.toString();

      fis.close();
      output = allWriter.toString();

      if (finished) {// 모든 작업이 정상 종료
        System.out.println("reached finished");
        String prixIndex = processPath.replace("process_" + id + "_", "");
        prixIndex = prixIndex.replace(logDir, "");
        prixIndex = prixIndex.replace(".proc", "");
        System.out.println(prixIndex);
        // date는 mybatis mapper xml에서 처리
        searchLogMapper.insert(
            Integer.parseInt(id), title.replace("'", "\\'"), 0, 0, prixIndex, "ACTG");

        ACTGProcessDto processDto = ACTGProcessDto.builder().failed(failed).finished(finished).output(output)
            .processName(processName).prixIndex(prixIndex).rate((rate)).title(title).build();
        logger.warn("processName2in:~~~~~~~~~~~{}", processName);

        return processDto;
      }

    }
    ACTGProcessDto processDto = ACTGProcessDto.builder().failed(failed).finished(finished).output(output)
        .processName(processName).prixIndex("").rate((rate)).title(title).build();
    logger.warn("processName2out:~~~~~~~~~~~{}", processName);

    return processDto;
  }
}
