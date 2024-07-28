package com.prix.homepage.user.controller;

import com.prix.homepage.user.service.EnzymeService;
import com.prix.homepage.user.service.ModificationLogService;
import com.prix.homepage.user.service.PrixDataWriter;
import com.prix.homepage.user.service.SoftwareLogService;
import com.prix.homepage.user.service.ModificationUserService;
import com.prix.homepage.user.service.ClassificationService;
import com.prix.homepage.user.service.DatabaseService;
import com.prix.homepage.user.service.SoftwareMsgService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.attoparser.dom.Document;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@AllArgsConstructor
public class ManageController {
    public class Modification {
        private String modName;
        private String fullName;
        private String classi;
        private String md;
        private String amd;
        private String residue;
        private String position;
    
        public Modification(String modName, String fullName, String classi, String md, String amd, String residue, String position) {
            this.modName = modName;
            this.fullName = fullName;
            this.classi = classi;
            this.md = md;
            this.amd = amd;
            this.residue = residue;
            this.position = position;
        }
    
        // Getters
        public String getModName() {
            return modName;
        }

        public String getFullName() {
            return fullName;
        }

        public String getClassi() {
            return classi;
        }

        public String getMd() {
            return md;
        }

        public String getAmd() {
            return amd;
        }

        public String getResidue() {
            return residue;
        }

        public String getPosition() {
            return position;
        }
    }

    private final EnzymeService enzymeService;
    private final DatabaseService databaseService;
    private final SoftwareMsgService softwareMsgService;
    private final ModificationLogService modificationLogService;
    private final ClassificationService classificationService;
    private final ModificationUserService modificationService;
    private final SoftwareLogService softwareLogService;

    //Manage요청 받을시 (configuration에서 edit, unlink 버튼)
    @PostMapping("/admin/manage")
    public String manage(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");
        if (userId == null) {
            return "redirect:/admin/adlogin";
        }

        try {
            if (request.getParameter("delete_enzyme") != null) {
                enzymeService.deleteEnzyme(Integer.parseInt(request.getParameter("enzyme_id")), userId);
            } else if (request.getParameter("add_enzyme") != null) {
                String name = request.getParameter("nenzyme_name");
                String cut = request.getParameter("nenzyme_nt_cut");
                String term = request.getParameter("nenzyme_ct_cut");
                enzymeService.insertEnzyme(userId, name, cut, term);
            } else if(request.getParameter("modify_enzyme") != null){
                Integer enzymeId = Integer.parseInt(request.getParameter("enzyme_id"));
                String name = request.getParameter("enzyme_name");
                String cut = request.getParameter("enzyme_nt_cut");
                String term = request.getParameter("enzyme_ct_cut");
                enzymeService.updateEnzyme(enzymeId, userId, name, cut, term);
            } else if (request.getParameter("delete_db") != null) {
                databaseService.deleteDatabase(Integer.parseInt(request.getParameter("db_index")));
            } else if (request.getParameter("db_name") != null) {
                String name = request.getParameter("db_name");
                String dbId = request.getParameter("db_index");

                if (dbId != null) {
                    Integer id = Integer.parseInt(dbId);
                    databaseService.updateDatabase(id, name);
                }
            /* update messages for "email message to recipient" */
            } else if (request.getParameter("modify_swmsg") != null) {
                softwareMsgService.updateSoftwareMsg("mode", request.getParameter("modeMessage"));
                softwareMsgService.updateSoftwareMsg("dbond", request.getParameter("dbondMessage"));
                softwareMsgService.updateSoftwareMsg("nextsearch", request.getParameter("nxtsrchMessage"));
                softwareMsgService.updateSoftwareMsg("signature", request.getParameter("signatureMessage"));
                
            } 
        } catch (Exception e) {
            /* flash error in case of exception */
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing your request.");
            return "redirect:/admin/configuration";
        }
        
        return "redirect:/admin/configuration";
    }


    //Manage요청 받을시 (configuration에서 edit, unlink 버튼)
    @PostMapping("/admin/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");
        if (userId == null) {
            return "redirect:/admin/adlogin";
        }
        if (file.isEmpty()) {
            /* check if file is empty */
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/admin/configuration";
        }

        try {
            String root = "src/main/config/";
            if (request.getParameter("add_db") != null){
                String dbFile = file.getOriginalFilename();
                if(dbFile.length() > 0){
                    dbFile.replace('\\', '/');
                    
                    String dbPath = dbFile.substring(dbFile.lastIndexOf('/') + 1, dbFile.length());
                    int index = PrixDataWriter.write("faste", dbFile, file.getInputStream());

                    String dbName = request.getParameter("db_name");
                    if (dbName == null || dbName.length() == 0){
                        int last = dbPath.lastIndexOf('.');
                        if(last < 0){
                            dbName = dbPath;
                        } else{
                            dbName = dbPath.substring(0, last);
                        }
                    }
                    String path = root + dbPath;
                    FileOutputStream fos = new FileOutputStream(path);
                    OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
                    InputStream is = file.getInputStream();
                    while (is.available() > 0){
                        writer.write(is.read());
                    }
                    writer.close();
                    fos.close();
                    is.close();

                    databaseService.insertDatabase(dbName, dbPath, index);
                }
                

            } else if (request.getParameter("ptm_add") != null){
                String modPath = file.getOriginalFilename();
                if (modPath.length() > 0){
                    modPath = modPath.replace('\\', '/');
                    try{
                        String modFile = modPath.substring(modPath.lastIndexOf('/') + 1, modPath.length());
                        String path = root + modFile;
                        FileOutputStream fos = new FileOutputStream(path);
                        OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
                        InputStream is = file.getInputStream();
                        while (is.available() > 0){
                            writer.write(is.read());
                        }
                        writer.close();
                        fos.close();
                        is.close();

                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = docFactory.newDocumentBuilder();
                        org.w3c.dom.Document doc = builder.parse(file.getInputStream());

                        HashMap<String, Integer> classMap = new HashMap<String, Integer>();
                        NodeList list = doc.getElementsByTagName("classificationRow");
                        for(int i = 0; i < list.getLength(); i++){
                            Node node = list.item(i);
                            NamedNodeMap map = node.getAttributes();
							Node attr = map.getNamedItem("classification");
							String nodeName = attr.getNodeValue();

                            //adding to db 
                            Integer rs = classificationService.selectByClass(nodeName);
							if (rs != null)
								classMap.put(nodeName, rs);
							else {
                                rs = classificationService.selectMax();
								int index = -1;
								if (rs != null) {
									index = rs;
                                    classificationService.insertNew(nodeName);
								}
								classMap.put(nodeName, index);
							}
                        }

                        list = doc.getElementsByTagName("PTM");
                        int size = list.getLength();
                        String[] sqls = new String[size];
                        List<Modification> modifications = new ArrayList<>();
                        for(int i = 0; i < size; i++){
                            String modName = "", fullName = "", classi = "", md = "", amd = "", residue = "", position = "";
                            Node node = list.item(i);
                            NodeList children = node.getChildNodes();
                            for (int j = 0; j < children.getLength(); j++){
                                Node child = children.item(j);
								String nodeName = child.getNodeName();
								if (nodeName.equals("name"))
									modName = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("fullName"))
									fullName = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("classification"))
									classi = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("massDifference"))
									md = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("avgMassDifference"))
									amd = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("residue"))
									residue = child.getFirstChild().getNodeValue();
								else if (nodeName.equals("position"))
									position = child.getFirstChild().getNodeValue();
                            }
                            modifications.add(new Modification(modName, fullName, classi, md, amd, residue, position));
                        }
                        if (sqls.length > 0){
                            String modDate = request.getParameter("ptm_date");
                            String modVersion = request.getParameter("ptm_version");
                            if (modDate == null || modDate.length() == 0)
								modDate = "now()";
							else
								modDate = "'" + modDate + "'";
							if (modVersion == null || modVersion.length() == 0)
								modVersion = "0.0";
                            modificationLogService.insertModLog(modDate, modVersion, modFile.replace("'", "\\\'"));
							
							int min = -1;
                            Integer rs = modificationService.selectMin();
							if (rs != null)
								min = rs - 1;
                            modificationService.updateMod(min);
							for (Modification mod : modifications)
							{
                                Integer classId = classMap.get(mod.getClassi());
                                modificationService.insertModification(mod.getModName(), mod.getFullName(), classId, mod.getMd(), mod.getAmd(), mod.getResidue(), mod.getPosition());
							}
                        }  
                    }
                    catch (Exception e)
					{
						e.printStackTrace();
					}
                }

            } else if (request.getParameter("sftw_add") != null){
                String sftw_root = "src/main/software_archive/";
                String sftwVersion = request.getParameter("sftw_version");
                String sftwDate = request.getParameter("sftw_date");
                String sftwName = request.getParameter("sftw_name");

                String modPath = file.getOriginalFilename();
                if(modPath.length() > 0){
                    modPath = modPath.replace('\\', '/');
                    File protDir = new File(sftw_root + "release/");
                    for(File fileX: protDir.listFiles()){
                        if(fileX.getName().startsWith(sftwName.toLowerCase())){
                            fileX.renameTo(new File(sftw_root + "deprecated/" + new Date().getTime() + "_" + fileX.getName()));
                            break;
                        }
                    }
                    try{
                        String sftwFile = sftwName.toLowerCase() + "_v" + sftwVersion + ".zip";
						String path = sftw_root + "release/" + sftwFile;
						FileOutputStream fos = new FileOutputStream(path);
						InputStream is = file.getInputStream();
						byte[] b = new byte[4096];
						int size = 0;
						 while( (size = is.read(b)) > 0 ){
							fos.write(b,0,size);
						}
						fos.close();
						is.close();
                        softwareLogService.insertSoftLog(sftwName, sftwDate, sftwVersion, modPath.substring(modPath.lastIndexOf('/') + 1).replace("'", "\\\'"));
                    }
                    catch (Exception e)
					{
						e.printStackTrace();
					}
                }
            }
            
        } catch (Exception e) {
            /* return error if exception found */
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing your request.");
            return "redirect:/admin/configuration";
        }
        
        return "redirect:/admin/configuration";
    }
}
