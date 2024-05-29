package com.prix.homepage.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.prix.homepage.livesearch.dao.DataMapper;
import com.prix.homepage.livesearch.pojo.Data;
import java.nio.charset.StandardCharsets;

@Controller
@AllArgsConstructor
public class DownloadServlet{

	private final DataMapper dataMapper;

	@GetMapping("/downloader")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ProteinSummary summary = new ProteinSummary();

		try {
			//path값이 -1인 에러가 발생 2024.05.29
			Integer pathInt = Integer.parseInt("path");

			Data dataResult = dataMapper.getNameContentById(pathInt);
			String filePath = new String(dataResult.getContent(), StandardCharsets.UTF_8);
			File file = new File(filePath);
			InputStream is = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(is);
			summary.read(reader);

			Integer dbPathInt = Integer.parseInt(summary.getDatabasePath());
			dataResult = dataMapper.getNameContentById(dbPathInt);
			filePath = new String(dataResult.getContent(), StandardCharsets.UTF_8);
			file = new File(filePath);
			is = new FileInputStream(file);
			summary.readProtein(is);

				PrintWriter out = response.getWriter();
				boolean isCSV = (request.getParameter("type").compareTo("csv") == 0);
				boolean isDBond = false;
				if (summary.getEngineName().compareToIgnoreCase("DBOND") == 0)
					isDBond = true;

				String prefix;// = summary.getFileName().substring(summary.getFileName().lastIndexOf('\\'));
				if( summary.getFileName().lastIndexOf('\\') != -1 ){
					prefix = summary.getFileName().substring(summary.getFileName().lastIndexOf('\\')+1, summary.getFileName().lastIndexOf('.'));
				}
				else if( summary.getFileName().lastIndexOf('/') != -1 ){
					prefix = summary.getFileName().substring(summary.getFileName().lastIndexOf('/')+1, summary.getFileName().lastIndexOf('.'));
				}
				else prefix = summary.getFileName().substring(0, summary.getFileName().lastIndexOf('.'));


				if( isCSV ) prefix += (".csv");
				else {
					if( isDBond ) prefix += ".bond.xml";
					else prefix += ".modi.xml";
				}
				
				response.setContentType("application/x-download");
				response.setHeader("Content-Disposition", "attachment; filename=" + prefix);//*/
				response.setCharacterEncoding("UTF-8");

				String minScore = request.getParameter("minscore");
				String targetDecoy = request.getParameter("targetdecoy");
				String minFDR = request.getParameter("minfdr");
				String maxHit = request.getParameter("maxhit");
				if (minScore == null)
					minScore = "0.1";
				if (minFDR == null)
					minFDR = "1";
				if (maxHit == null)
					maxHit = "All";

				if (isCSV){
					if( isDBond ){
						out.println("Index,Spectrum name,Observed M/Z,CS," +
							"CalcMW,DeltaM,Score,ProteinA,BondA,PeptideA,ModificationA,ProteinB,BondB,PeptideB,ModificationB" );
						for (int i = 0; i<summary.getSpectrums().length; i++){
							if( summary.getSpectrum(i).getPeptides().length != 0 )
								write_bond_spectrum_match(out, summary, summary.getSpectrum(i));
						}
					}
					else{
						out.println("Index,Spectrum name,Observed M/Z,CS," +
										"CalcMW,DeltaM,Probability,Protein,Start_site,End_site,Peptide,Modifications" );
						for (int i = 0; i<summary.getSpectrums().length; i++){
							SpectrumInfo spectrum = summary.getSpectrum(i);
							if( spectrum.getPeptides().length != 0 ){
								write_pept_spectrum_match(out, summary, summary.getSpectrum(i));
							}	
						}
					}
				}
				else
				{
					XMLOutputter XMLOut = new XMLOutputter( Format.getPrettyFormat() );
					out.println( "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" );
					out.println( "<msms_search>" );
					out.println( XMLOut.outputString(get_search_summary(summary, isDBond)) );
					if( isDBond ){
						for (int i = 0; i<summary.getSpectrums().length; i++){
							if( summary.getSpectrum(i).getPeptides().length != 0 )
								out.println( XMLOut.outputString(get_bond_spectrum_query_element(summary, summary.getSpectrum(i)) ) );
						}
					}
					else{
						for (int i = 0; i<summary.getSpectrums().length; i++){
							if( summary.getSpectrum(i).getPeptides().length != 0 )
								out.println( XMLOut.outputString(get_pept_spectrum_query_element(summary, summary.getSpectrum(i)) ) );
						}
					}
					out.println( "</msms_search>" );
				}

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	public static Element get_search_summary( ProteinSummary proSummary, boolean isDBbond ){

		Element summary = new Element( "summary" );
		summary.setAttribute( new Attribute( "engine", proSummary.getEngineName() ) );
		summary.setAttribute( new Attribute( "version", proSummary.getVersion() ) );
		summary.setAttribute( new Attribute( "date", proSummary.getDate() ) );
		summary.setAttribute( new Attribute( "user", proSummary.getUserName() ) );
		summary.setAttribute( new Attribute( "title", proSummary.getTitle() ) );

		Element dataset = new Element( "dataset" );
		dataset.setAttribute( new Attribute( "path", proSummary.getFileName()) );
		dataset.setAttribute( new Attribute( "format", proSummary.getFileType() ) );
		dataset.setAttribute( new Attribute( "instrument", proSummary.getInstrumentName() ) );
		summary.addContent( dataset );

		Element database = new Element( "database" );
		database.setAttribute(new Attribute( "path", proSummary.getDatabaseName() ));
		summary.addContent( database );

		Element enzyme = new Element( "enzyme" );
		enzyme.setAttribute(new Attribute( "name", proSummary.getEnzymeName() ));
	//	enzyme.setAttribute(new Attribute( "cleave", Constants.userDBEnzyme.getCleave() ));
	//	enzyme.setAttribute(new Attribute( "term", Constants.userDBEnzyme.getCleavageSide()+"term" ));
		summary.addContent( enzyme );

		Element parameters = new Element( "parameters" );
		Element enzyme_constraint = new Element( "enzyme_constraint" );
		enzyme_constraint.setAttribute( new Attribute( "max_miss_cleavages", String.valueOf(proSummary.getMaxMissedCleavages()) ) );
	//	enzyme_constraint.setAttribute( new Attribute( "min_number_termini", String.valueOf(proSummary.getMinTerminiNumber()) ) );
		parameters.addContent( enzyme_constraint );

		Element peptide_mass_tol = new Element( "peptide_mass_tol" );
		peptide_mass_tol.setAttribute( new Attribute( "value", String.format("%.4f", proSummary.getPeptideTolerance()) ) );
	//	if( proSummary.getPTUnit().compareToIgnoreCase("ppm")==0 )
		peptide_mass_tol.setAttribute( new Attribute( "unit", (proSummary.getPTUnit().compareToIgnoreCase("ppm")==0)? "ppm":"Da" ) );
		parameters.addContent( peptide_mass_tol );

		Element fragment_ion_tol = new Element( "fragment_ion_tol" );
		fragment_ion_tol.setAttribute( new Attribute( "value", String.format("%.4f", proSummary.getFragmentTolerance()) ) );
		fragment_ion_tol.setAttribute( new Attribute( "unit", "Da" ) );
		parameters.addContent( fragment_ion_tol );

		if( !isDBbond ){
			Element modified_mass_range = new Element( "modified_mass_range" );
			modified_mass_range.setAttribute( new Attribute( "min_value", String.format("%.1f", proSummary.getProteinMassMin()) ) );
			modified_mass_range.setAttribute( new Attribute( "max_value", String.format("%.1f", proSummary.getProteinMassMax()) ) );
			parameters.addContent( modified_mass_range );
		}

		summary.addContent( parameters );

		Element modifications = new Element( "modifications" );
		Element variable = new Element( "variable" );
		if( proSummary.getModifications().length > 0 ){
			Modification[] var = proSummary.getModifications();
			variable.setAttribute( new Attribute( "num", String.valueOf(var.length) ) );
			for( Modification p : var ){
				Element mod = new Element( "mod" );
				mod.setAttribute( new Attribute( "name", p.getType() ) );
				mod.setAttribute( new Attribute( "position", p.getPosition() ) );
				mod.setAttribute( new Attribute( "massdiff", String.format("%.4f", p.getMass()) ) );
				variable.addContent( mod );
			}
		}
		modifications.addContent( variable );

		Element fixed = new Element( "fixed" );
		if( proSummary.getFixedModifications().length > 0 ){
			Modification[] fix = proSummary.getFixedModifications();
			fixed.setAttribute( new Attribute( "num", String.valueOf(fix.length) ) );
			for( Modification p : fix ){
				Element mod = new Element( "mod" );
				mod.setAttribute( new Attribute( "name", p.getType() ) );
				mod.setAttribute( new Attribute( "position", p.getPosition() ) );
				mod.setAttribute( new Attribute( "massdiff", String.format("%.4f", p.getMass()) ) );
				fixed.addContent( mod );
			}
		}
		modifications.addContent( fixed );
		summary.addContent( modifications );

		return summary;
	}

	public static Element get_pept_spectrum_query_element( ProteinSummary summary, SpectrumInfo spectrum ){

		Element spectrum_guery = new Element( "query" );
		spectrum_guery.setAttribute(new Attribute( "id", String.valueOf( spectrum.getId() ) ));
		spectrum_guery.setAttribute(new Attribute( "spectrum", spectrum.getTitle() ));
		spectrum_guery.setAttribute(new Attribute( "observed_mz", String.format("%.4f", spectrum.getWeight()) ));
		spectrum_guery.setAttribute(new Attribute( "cs", String.valueOf(spectrum.getCharge()) ));
		spectrum_guery.setAttribute(new Attribute( "position", String.valueOf(spectrum.getSpectrumPosition()) ));

		PeptideInfo[] pool = spectrum.getPeptides();
		int rank = 1;
		for( PeptideInfo peptide : pool ){
			spectrum_guery.addContent( get_peptide_hit_element(summary, rank++, peptide) );
		}
		return spectrum_guery;
	}//*/
	public static Element get_bond_spectrum_query_element( ProteinSummary summary, SpectrumInfo spectrum ){

		Element spectrum_guery = new Element( "query" );
		spectrum_guery.setAttribute(new Attribute( "id", String.valueOf( spectrum.getId() ) ));
		spectrum_guery.setAttribute(new Attribute( "spectrum", spectrum.getTitle() ));
		spectrum_guery.setAttribute(new Attribute( "observed_mz", String.format("%.4f", spectrum.getWeight()) ));
		spectrum_guery.setAttribute(new Attribute( "cs", String.valueOf(spectrum.getCharge()) ));
		spectrum_guery.setAttribute(new Attribute( "position", String.valueOf(spectrum.getSpectrumPosition()) ));

		PeptideInfo[] pool = spectrum.getPeptides();
		int rank = 1;
		for( int i=0; i<pool.length; i+=2 ){
			spectrum_guery.addContent( get_complex_hit_element( summary, rank, pool[i], pool[i+1] ) );
		}
		return spectrum_guery;
	}//*/

	public static Element get_peptide_hit_element( ProteinSummary summary, int rank, PeptideInfo peptide ){
		Element peptide_hit = new Element( "peptide_hit" );
		peptide_hit.setAttribute(new Attribute( "rank", String.valueOf(rank) ));
		peptide_hit.setAttribute(new Attribute( "sequence", peptide.getName() ));
		peptide_hit.setAttribute(new Attribute( "prev_next_res", String.valueOf(peptide.getNTerm(0))+ String.valueOf(peptide.getCTerm(0))));
	//	peptide_hit.setAttribute(new Attribute( "next_aa", String.valueOf(peptide.getCTerm(0)) ));
		peptide_hit.setAttribute(new Attribute( "calculated_MW", String.format("%.4f", peptide.getMassCalc()) ));

		Element delta_mass = new Element( "delta_mass" );
		delta_mass.setAttribute(new Attribute( "value", String.format("%.4f", peptide.getMassDelta()) ));
		peptide_hit.addContent( delta_mass );

		Element score = new Element( "score" );
		score.setAttribute(new Attribute( "value", String.format("%.4f", peptide.getScore()) ));
		peptide_hit.addContent( score );

		if( peptide.getModIndices().length > 0 ){
			Element pep_modifications = new Element( "pep_modifications" );
			int[] site = peptide.getModIndices();
			int[] mod = peptide.getModOffsets();
			for(int i=0; i<site.length; i++){
				Element modified = new Element( "modified" );
				modified.setAttribute(new Attribute( "site", String.valueOf( site[i] ) ));
				modified.setAttribute(new Attribute( "mod", String.valueOf( mod[i]+1 ) ));
				pep_modifications.addContent( modified );
			}
			peptide_hit.addContent( pep_modifications );
		}

		Element protein = new Element( "protein" );
		protein.setAttribute(new Attribute( "name", summary.getProtein(peptide.getProteinIndex(0)).getName()) );
		protein.setAttribute(new Attribute( "index", String.valueOf(peptide.getProteinIndex(0)) ));
		protein.setAttribute(new Attribute( "pep_start", String.valueOf(peptide.getStartPosition(0)) ));
		peptide_hit.addContent( protein );

		if( peptide.getProteinCount() > 1 ){
			Element alternative_proteins = new Element( "alternative_proteins" );
			alternative_proteins.setAttribute(new Attribute( "num", String.valueOf(peptide.getProteinCount()-1) ));
			for( int pr=1; pr<peptide.getProteinCount(); pr++){
				Element entry = new Element( "entry" );
				entry.setAttribute(new Attribute( "name", summary.getProtein(peptide.getProteinIndex(pr)).getName() ));
				entry.setAttribute(new Attribute( "index", String.valueOf(peptide.getProteinIndex(pr)) ));
				entry.setAttribute(new Attribute( "pep_start", String.valueOf(peptide.getStartPosition(pr)) ));
				alternative_proteins.addContent( entry );
			}
			peptide_hit.addContent( alternative_proteins );
		}
		return peptide_hit;
	}

	public static Element get_complex_hit_element( ProteinSummary summary, int rank, PeptideInfo alpha, PeptideInfo beta ){
		Element complex_hit = new Element( "complex_hit" );
		complex_hit.setAttribute(new Attribute( "rank", String.valueOf(rank) ));
		complex_hit.setAttribute(new Attribute( "sequence", alpha.getName()+"-"+beta.getName() ));
		complex_hit.setAttribute(new Attribute( "calculated_MW", String.format("%.4f", alpha.getMassCalc() ) ));

		Element delta_mass = new Element( "delta_mass" );
		delta_mass.setAttribute(new Attribute( "value", String.format("%.4f", alpha.getMassDelta()) ));
		complex_hit.addContent( delta_mass );

		Element score = new Element( "score" );
		score.setAttribute(new Attribute( "value", String.format("%.4f", alpha.getScore()) ));
		complex_hit.addContent( score );

		complex_hit.addContent( get_comonent_hit_element( summary, "alpha", alpha) );
		complex_hit.addContent( get_comonent_hit_element( summary, "beta", beta) );

		return complex_hit;
	}

	public static Element get_comonent_hit_element( ProteinSummary summary, String rank, PeptideInfo alpha){
		Element peptide_hit = new Element( "peptide_hit" );
		peptide_hit.setAttribute(new Attribute( "id", rank ));
		peptide_hit.setAttribute(new Attribute( "sequence", alpha.getName() ));
		peptide_hit.setAttribute(new Attribute( "prev_next_res", String.valueOf(alpha.getNTerm(0))+ String.valueOf(alpha.getCTerm(0)) ));

		int bondSite = 0;
		Element pep_modifications = new Element( "pep_modifications" );
		Element modified = new Element( "modified" );
		int[] site = alpha.getModIndices();
		int[] mod = alpha.getModOffsets();
		for(int k=0 ; k<site.length ; k++){
			if( mod[k]==0 ){//summary.getModifications()[mod[k]].equals("*") ){
				peptide_hit.setAttribute(new Attribute( "bond", String.valueOf(site[k]) ));
				bondSite = site[k]-1;
			}
			else {
				modified.setAttribute(new Attribute( "site", String.valueOf( site[k] ) ));
				modified.setAttribute(new Attribute( "mod", String.valueOf( mod[k]+1 ) ));
			}
		}
		if( alpha.getModIndices().length > 1 ){
			pep_modifications.addContent( modified );
			peptide_hit.addContent( pep_modifications );
		}

		Element protein = new Element( "protein" );
		protein.setAttribute(new Attribute( "name", summary.getProtein(alpha.getProteinIndex(0)).getName()) );
		protein.setAttribute(new Attribute( "index", String.valueOf(alpha.getProteinIndex(0)) ));
		protein.setAttribute(new Attribute( "pep_start", String.valueOf(alpha.getStartPosition(0)) ));
		protein.setAttribute(new Attribute( "bond", String.valueOf(alpha.getStartPosition(0)+bondSite) ));
		peptide_hit.addContent( protein );

		return peptide_hit;
	}

	public static void write_pept_spectrum_match( PrintWriter out, ProteinSummary summary, SpectrumInfo spectrum){
		
		PeptideInfo peptide = spectrum.getPeptides()[0];
		StringBuffer modified= new StringBuffer();
		if( peptide.getModIndices().length > 0 ){
			int[] site = peptide.getModIndices();
			int[] mod = peptide.getModOffsets();
			for(int k=0; k<site.length; k++ ){
				modified.append(String.format("%s(%c%d) ", summary.getModifications()[mod[k]].getType(), peptide.getName().charAt(site[k]-1), site[k]));	
			}
		}
		
		for(int j=0;j<peptide.getProteinCount();j++){
			out.printf("%s,%s,%.4f,%d,", spectrum.getId(),spectrum.getTitle(),spectrum.getWeight(),spectrum.getCharge() );
			out.printf("%.4f,%.4f,%.4f,%s,%d,%d,%c.%s.%c,%s\r\n", 
					peptide.getMassCalc(),peptide.getMassDelta(), peptide.getScore(), summary.getProtein(peptide.getProteinIndex(j)).getName(), peptide.getStartPosition(j), peptide.getEndPosition(j),
					peptide.getNTerm(j), peptide.getName(), peptide.getCTerm(j), modified.toString());
		}
	}//*/
	
	public static void write_bond_spectrum_match( PrintWriter out, ProteinSummary summary, SpectrumInfo spectrum ){

		out.printf("%s,%s,%.4f,%d,", spectrum.getId(),spectrum.getTitle(),spectrum.getWeight(),spectrum.getCharge() );
		
		PeptideInfo peptide = spectrum.getPeptides()[0];
		out.printf("%.4f,%.4f,%.4f,", peptide.getMassCalc(),peptide.getMassDelta(), peptide.getScore());
	
		StringBuffer curatedPept = new StringBuffer( peptide.getNTerm(0)+"."+peptide.getName()+"."+peptide.getCTerm(0) );
		int bondSite = 0;
		int[] site = peptide.getModIndices();
		int[] mod = peptide.getModOffsets();
		String modified= "";
		for(int k=0 ; k<site.length ; k++){
			if( mod[k]==0 ){
				bondSite = site[k]-1;
				curatedPept.insert(bondSite+3, '*');
			}
			else {
				modified = String.format("%s(%c%d) ", summary.getModifications()[mod[k]].getType(), peptide.getName().charAt(site[k]-1), site[k]);
			}
		}
	/*	if( peptide.getModIndices().length > 1 ){
			curatedPept.append(" ");
			curatedPept.append(modified);
		}//*/
		
		out.printf("%s,%d,%s,%s,", summary.getProtein(peptide.getProteinIndex(0)).getName(), peptide.getStartPosition(0)+bondSite,
				curatedPept.toString(), modified);
		
		
		peptide = spectrum.getPeptides()[1];
	
		curatedPept = new StringBuffer( peptide.getNTerm(0)+"."+peptide.getName()+"."+peptide.getCTerm(0) );
		bondSite = 0;
		site = peptide.getModIndices();
		mod = peptide.getModOffsets();
		modified= "";
		for(int k=0 ; k<site.length ; k++){
			if( mod[k]==0 ){
				bondSite = site[k]-1;
				curatedPept.insert(bondSite+3, '*');
			}
			else {
				modified = String.format("%s(%c%d) ", summary.getModifications()[mod[k]].getType(), peptide.getName().charAt(site[k]-1), site[k]);
			}
		}
	/*	if( peptide.getModIndices().length > 1 ){
			curatedPept.append(" ");
			curatedPept.append(modified);
		}//*/
		
		out.printf("%s,%d,%s,%s\r\n", summary.getProtein(peptide.getProteinIndex(0)).getName(), peptide.getStartPosition(0)+bondSite,
				curatedPept.toString(), modified);
		
	}//*/
}












