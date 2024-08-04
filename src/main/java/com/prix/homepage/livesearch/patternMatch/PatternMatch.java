package com.prix.homepage.livesearch.patternMatch;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatternMatch {
    private int[] findNumber = new int[2];
    private String dataBase;
    private String formatType;
    private String species;
    private String id;
    private String passwd;
    private String ip;
    private String[] inputPattern;
    private boolean checkSpecies;
    private boolean checkWithoutSq;
    private boolean checkOrder;
    private Make_html make_html = new Make_html();
    private Regex_Match match = new Regex_Match();
    private ResultSet rs;
    private MySQL db;

    public PatternMatch(String formatType, String dataBase, String[] inputPattern, boolean checkSpecies, String species,
            boolean checkWithoutSq, boolean checkOrder) {
        this.formatType = formatType;
        this.dataBase = dataBase;
        this.checkSpecies = checkSpecies;
        this.species = species;
        this.inputPattern = inputPattern;
        this.checkWithoutSq = checkWithoutSq;
        this.checkOrder = checkOrder;
    }

    public void DBParameter(String id, String passwd, String ip) {
        this.id = id;
        this.passwd = passwd;
        this.ip = ip;
    }

    public void MainMethod() throws IOException, SQLException {
        this.db = new MySQL(this.id, this.passwd, this.ip, this.dataBase);
        this.db.Connect();
        if (this.formatType.equals("PROSITE")) {
            this.inputPattern = Regex_Convert.PrositeToPerl(this.inputPattern);
        }

        if (this.checkSpecies) {
            if (this.dataBase.equals("swiss_prot")) {
                this.rs = this.db.Swiss_protSequence(this.species, this.inputPattern, this.checkOrder);
            } else if (this.dataBase.equals("genbank")) {
                this.rs = this.db.GenbankSequence(this.species, this.inputPattern, this.checkOrder);
            }
        } else if (this.dataBase.equals("swiss_prot")) {
            this.rs = this.db.Swiss_protSequence(this.inputPattern, this.checkOrder);
        } else if (this.dataBase.equals("genbank")) {
            this.rs = this.db.GenbankSequence(this.inputPattern, this.checkOrder);
        }

    }

    public String getOneProtein() throws IOException, SQLException {
        if (this.rs.next()) {
            String acNumber = this.rs.getString(1);
            String description = this.rs.getString(2);
            String species = this.rs.getString(3);
            String sequence = this.rs.getString(4);
            if (!this.checkWithoutSq) {
                sequence = this.match.Match(sequence, this.inputPattern);
            } else {
                this.match.Count(sequence, this.inputPattern);
            }

            return this.make_html.getOneProtein(this.dataBase, acNumber, description, species, sequence,
                    this.checkWithoutSq);
        } else {
            return null;
        }
    }

    public String getParameter() throws IOException, SQLException {
        this.findNumber = this.match.getFindNumber();
        this.rs.close();
        this.db.Close();
        return this.make_html.PrintParameter(this.inputPattern, this.findNumber, this.species, this.checkSpecies);
    }
}
