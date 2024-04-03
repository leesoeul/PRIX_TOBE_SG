<jsp:include page="inc/misc.jsp" flush="true" />
<!-- header ³¡-->

<table width="990" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="10" height="100%" rowspan="2" align="right" valign="top" style="padding-right:15px">
		&nbsp;
	</td>

    <td>	
		<table border="0" cellspacing="0" cellpadding="0" id="TitTable">
		  <tr>
			<td align="left"><font class="drakBR" size="3">HELP</td>
			<td align="right" valign="bottom" style="padding-right:5px;">&nbsp;</td>
		  </tr>
		</table>
		<table width="700" border="0" align="left" cellpadding="1">
			<tr>
			  <td align="left"><img src="images/p_st1.GIF" width="4" height="9"/><font class="drakBR"> Search Parameters<br/><br/>
			  </td>
			</tr>
			<tr>
			  <td align="left"><li><font class="drakBR"><a name="NAME">User Name</a></font><br/>&nbsp;Put your name or id.<br/><br/></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="TIT">Search Title</a></font><br/>&nbsp;Put title for the search. It will be printed at the top of result page. If the title is left blank, it will be named automatically.<br/><br></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="MSMS">MS/MS Data</a></font><br/>&nbsp;Select the spectra file to be searched. The size of upload file is allowed <font color="red">up to 500MB</font>.<br/><br/></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="DATA_FORMAT">Data Format</a></font><br/>&nbsp;Supported MS/MS formats are<br><br>
			  &nbsp;&nbsp;<a class="under" href="#DTA">Sequest (*.dta)</a><br>
			  &nbsp;&nbsp;<a class="under" href="#PKL">Micromass (*.pkl)</a><br>
			  &nbsp;&nbsp;<a class="under" href="#MGF">Mascot Generic Format (*.mgf)</a>
			  <table id="gray">
				<tr>
					<td colspan="3"><br></td>
				</tr>
				<tr>
					
					<th class="grayTH"><font color="red"><a class="under" name="DTA">Sequest (*.dta)</a></th>
					<th class="grayTH" width="10">&nbsp;</th>
					<th class="grayTH"><font color="red">Examle</th>
				</tr>
				<tr>
					
					<td valign="top">
					    M+H&nbsp;&nbsp;&nbsp;CHARGE (precursor ion 1)<br>
						m/z&nbsp;&nbsp;&nbsp;intensity (fragment ion) [1..*]<br>
						<br>
						M+H&nbsp;&nbsp;&nbsp;CHARGE (precursor ion 2)<br>
						m/z&nbsp;&nbsp;&nbsp;intensity (fragment ion) [1..*]<br><br>
						The first line represents M+H and charge state of precursor ion from an MS scan.
						From the second line, m/z and intensity values of fragment ions are recorded.
						For multiple MS/MS scans, at least one blank line must be between each MS/MS scan.
					</td>
					<td width="10">&nbsp;</td>
					<td valign="center">
					    1511.44&nbsp;&nbsp;&nbsp;2<br>
						339.2520&nbsp;&nbsp;&nbsp;191<br>343.2500&nbsp;&nbsp;&nbsp;1<br>344.6134&nbsp;&nbsp;&nbsp;138<br>345.3719&nbsp;&nbsp;&nbsp;15<br>346.2221&nbsp;&nbsp;&nbsp;9<br>
						<br>
						2568.16&nbsp;&nbsp;&nbsp;3<br>
						730.2723&nbsp;&nbsp;&nbsp;85<br>731.3793&nbsp;&nbsp;&nbsp;83<br>742.3622&nbsp;&nbsp;&nbsp;62<br>748.2934&nbsp;&nbsp;&nbsp;159<br>749.3618&nbsp;&nbsp;&nbsp;56<br>760.2417&nbsp;&nbsp;&nbsp;257<br>
					</td>
				</tr>
				
				<tr>
					
					<th class="grayTH"><font color="red"><a class="under" name="PKL">Micromass (*.pkl)</a></th>
					<th class="grayTH" width="10">&nbsp;</th>
					<th class="grayTH"><font color="red">Examle</th>
				</tr>
				<tr>
					
					<td valign="top">
					    M/Z&nbsp;&nbsp;&nbsp;INTENSITY&nbsp;&nbsp;&nbsp;CHARGE (precursor ion 1)<br>
						m/z&nbsp;&nbsp;&nbsp;intensity (fragment ion) [1..*]<br>
						<br>
						M/Z&nbsp;&nbsp;&nbsp;INTENSITY&nbsp;&nbsp;&nbsp;CHARGE (precursor ion 2)<br>
						m/z&nbsp;&nbsp;&nbsp;intensity (fragment ion) [1..*]<br><br>
						The first line represents m/z, intetnsity and charge state of precursor ion from an MS scan.
						From the second line, m/z and intensity values of fragment ions are recorded.						
					</td>
					<td width="10">&nbsp;</td>
					<td valign="center">
					    756.22&nbsp;&nbsp;&nbsp;32457&nbsp;&nbsp;&nbsp;2<br>
						339.2520&nbsp;&nbsp;&nbsp;191<br>343.2500&nbsp;&nbsp;&nbsp;1<br>344.6134&nbsp;&nbsp;&nbsp;138<br>345.3719&nbsp;&nbsp;&nbsp;15<br>346.2221&nbsp;&nbsp;&nbsp;9<br>
						<br>
						856.72&nbsp;&nbsp;&nbsp;125341&nbsp;&nbsp;&nbsp;3<br>
						730.2723&nbsp;&nbsp;&nbsp;85<br>731.3793&nbsp;&nbsp;&nbsp;83<br>742.3622&nbsp;&nbsp;&nbsp;62<br>748.2934&nbsp;&nbsp;&nbsp;159<br>749.3618&nbsp;&nbsp;&nbsp;56<br>760.2417&nbsp;&nbsp;&nbsp;257<br>
					</td>
				</tr>
				<tr>
					
					<th class="grayTH"><font color="red"><a class="under" name="MGF">Mascot Generic Format (*.mgf)</a></th>
					<th class="grayTH" width="10">&nbsp;</th>
					<th class="grayTH"><font color="red">Examle</th>
				</tr>
				<tr>
					
					<td valign="top">BEGIN IONS<br>
						TITLE=Spectrum 1 name<br>
						PEPMASS=precursor ion M/Z<br>
						CHARGE=Charge<br>
						m/z&nbsp;&nbsp;&nbsp;intensity (fragment ion) [1..*]<br>
						END IONS<br><br>

						BEGIN IONS<br>
						TITLE=Spectrum 2 name<br>
						PEPMASS=precursor ion M/Z<br>
						CHARGE=Charge<br>
						m/z&nbsp;&nbsp;&nbsp;intensity (fragment ion) [1..*]<br>
						END IONS<br><br>
						An MS/MS scan delimited by a pair of statements: BEGIN IONS and END IONS.
					</td>
					<td width="10">&nbsp;</td>
					<td valign="center">BEGIN IONS<br>
						TITLE=my_sample_1.2<br>
						PEPMASS=756.22<br>
						CHARGE=2+<br>
						339.2520&nbsp;&nbsp;&nbsp;191<br>343.2500&nbsp;&nbsp;&nbsp;1<br>344.6134&nbsp;&nbsp;&nbsp;138<br>345.3719&nbsp;&nbsp;&nbsp;15<br>346.2221&nbsp;&nbsp;&nbsp;9<br>
						END IONS<br><br>
						BEGIN IONS<br>
						TITLE=my_sample_2.3<br>
						PEPMASS=856.72<br>
						CHARGE=3+<br>
						730.2723&nbsp;&nbsp;&nbsp;85<br>731.3793&nbsp;&nbsp;&nbsp;83<br>742.3622&nbsp;&nbsp;&nbsp;62<br>748.2934&nbsp;&nbsp;&nbsp;159<br>749.3618&nbsp;&nbsp;&nbsp;56<br>760.2417&nbsp;&nbsp;&nbsp;257<br>
						END IONS<br>
					</td>
				</tr>
			  </table>			  			  
			 <br/></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="INST">Instrument</a></font><br/>&nbsp;Select instrument type used for MS/MS.<br/><br/></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="DB">Database</a></font><br/>&nbsp;Select a protein sequence database to be searched or upload the FASTA file including the protein sequences. The size of upload file is allowed <font color="red">up to 100MB</font>.<br/><br/></td>
			</tr>
			
			<tr>
			  <td align="left"><li><font class="drakBR"><a name="TARDEC">Target-Dccoy Search</a></font><br/>&nbsp;Check for target-decoy search. Then, the ms/ms search will be conducted against the normal proteins (selected or uploaded) database <font color="red">appended with their reverse sequences</font>. This target-decoy search assumes that the distribution of incorrect peptide-spectrum matches (PSMs) from the target database is similar to that of PSMs from the decoy database. With this assumption, an optimized score cut-off for the corresponding <font color="red">false discovery rate (FDR)</font> is calculated. FDR was calculated as FDR = D/T, where D and T are the numbers of the decoy hits and the target hits above the given score threshold, respectively. T is reported to search summary.<br/><br/></td>
			</tr>
			
			<tr>
			  <td align="left"><li><font class="drakBR"><a name="ENZYME">Enzyme</a></font><br/>&nbsp;Select enzyme used for protein digestion. Users can also add new enzyme rules to their own list, and can use them (Login required).<br/><br/>
			 
			  <font color="red">Predefined Enzymes</font>
			  <table style="BORDER-BOTTOM: #d9dddc 2px solid; BORDER-TOP: #d9dddc 2px solid;">				
				<tr>
					<th class="grayTH">Name</th>
					<th class="grayTH">&nbsp;Cleavage&nbsp;&nbsp;&nbsp;&nbsp;</th>
					<th class="grayTH">&nbsp;Term&nbsp;&nbsp;&nbsp;&nbsp;</th>
				</tr>
				<tr>				
					<td>&nbsp;&nbsp;Trypsin</td>
					<td align="center">KR</td>
					<td align="center">Cterm</td>
				</tr>
				<tr>				
					<td>&nbsp;&nbsp;Arg-C</td>
					<td align="center">R</td>
					<td align="center">Cterm</td>
				</tr>
				<tr>					
					<td>&nbsp;&nbsp;Lys-C</td>
					<td align="center">K</td>
					<td align="center">Cterm</td>
				</tr>
				<tr>					
					<td>&nbsp;&nbsp;Glu-C</td>
					<td align="center">E</td>
					<td align="center">Cterm</td>
				</tr>
				<tr>					
					<td>&nbsp;&nbsp;Chymotrypsin</td>
					<td align="center">FYWL</td>
					<td align="center">Cterm</td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;PepsinA</td>
					<td align="center">FL</td>
					<td align="center">Cterm</td>
				</tr>
			  </table>			  
			  <br/></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="MISSCLEAVE">Max No. of Missed Cleavages</a></font><br/>&nbsp;Select the maximum number of missed cleavages permitted in a peptide. If this is set to 2, a peptide can have zero, one or two cleavage residues at other positions than the last position of the peptide in case that cleavage Term of used enzyme is Cterm.<br/><br/></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="NTT">Min No. of Enzyme Termini</a></font><br/>&nbsp;Select minimum number of termini fragmented by the selected enzyme rule. When Trypsin is selected, set 2 to only consider fully tryptic peptides and set 1 to include partially tryptic peptides.</font><br/><br/></td>
			</tr>
			<tr>
			  <td align="left"><li><font class="drakBR"><a name="PEPTTOL">Peptide Tolerance</a></font><br/>&nbsp;Specify the error tolerance on experimental peptide mass values. Peptides would be found within the specified tolerance range to either side of the theoretical mass. The peptide mass tolerance is specified in dalton or in ppm.<br/><br/></td>
			</tr>
			<tr>
			  <td align="left"><li><font class="drakBR"><a name="ISOERR">#<sup>13</sup>C (Isotope error)</a></font><br/>&nbsp;Specify the isotope error of precursors. Sometimes, the <sup>13</sup>C peak is selected for fragmentation rather than the <sup>12</sup>C even though you are using a high accuracy-instrument. In extreme cases, the <sup>13</sup>C<sub>2</sub> peak may be selected. This option is effective if you are using a high-accuracy instrument (that is you're using ppm-tolerance for Peptide Tolerance). If you're using a low-accuracy instrument, use quite wide dalton-tolerance (e.g. 3 Da) for Peptide Tolerance  <br/><br/></td>
			</tr>
			<tr>
			  <td align="left"><li><font class="drakBR"><a name="FRAGTOL">Fragment Ion Tolarance</a></font><br/>&nbsp;Specify the error tolerance on experimental fragment ion mass values. Experimental fragment ions would be found within the specified tolerance range to either side of the theoretical position. The fragment ion mass tolerance is specified in dalton.<br/><br/></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="MODRANGE">Modified Mass</a></font><br/>&nbsp;Specify minimum and maximum masses of peptides to be modified.<br/><br/></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="FIXMOD">Fixed Modifications</a></font><br/>&nbsp;Fixed modifications are applied to every amino acid specified. For example, if Carbamidomethyl (+57 Da) is fixed to 'Cys', the mass of all the 'Cys' in a peptide is changed to 160 Da. In a result page, fixed modified amino acids are not explicitly labeled as modified. Fixed modifications are restricted to amino acids, and single fixed modification per amino acid is allowed. Variable modifications are not allowed on fixed modified amino acids. PRIX ms/ms search provides the modification list from <a href="http://www.unimod.org" target="_blank" class="under">Unimod (www.unimod.org)</a>. Additionally, users can add new modifications to their own list, and use them (Login required).<br/><br/></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="VARMOD">Variable Modifications</a></font><br/>&nbsp;Variable modifications may or may not be present in a peptide, and thus, would enlarge search space exponentially by enumeration of all possible modifications. <font color="red">MODPlus has no limitation on the number of modifications to be selected, and hundreds of modifications can be taken as input. DBond allows up to 20 variable modifications to be selected.</font> PRIX ms/ms search provides the modification list from <a href="http://www.unimod.org" target="_blank" class="under">Unimod (www.unimod.org)</a>. Default list consists of modifications from Post-, Co-, and Pre-translational, Chemical derivative, Artefact, Multiple, and Other classifications (All: default list + modifications from N-linked, O-linked, and Other glycosylation, Synth. pep. protect. gp., Isotopic label, Non-standard residue, and AA substitution classifications). Additionally, users can add new modifications to their own list, and can use them (Login required).<br/><br/></td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="MODMAP">MODmap</a></font><br/>&nbsp;Blind search predicts new delta masses, from MS/MS spectra, other than modifications in your search list. Predicted delta masses could be modifications present in the sample. User can re-submit ms/ms search with abundant ones of predicted delta masses. Notably, if you used all the modifications in search, they may be novel modification candidates.<br/><br/>
			  <font color="red">PTM Frequency Matrix</font>
			  <table style="BORDER-BOTTOM: #d9dddc 2px solid; BORDER-TOP: #d9dddc 2px solid;">
					<tr>
						<th class="grayTH"><font size="2">Delta&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">Nterm&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">A&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">C&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">D&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">E&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">F&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">G&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">H&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">I&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">K&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">L&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">M&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">N&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">P&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">Q&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">R&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">S&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">T&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">V&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">W&nbsp;&nbsp;</font></th>
						<th class="grayTH"><font size="2">Y&nbsp;&nbsp;</font></th>
					</tr>
						<%
							int row = 12;
							int[][] matrix = {								
								{ -28,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	8,	0,	0,	0,	0,	0},
								{ -17, 22,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	4,	0,	20,	0,	0,	0,	0,	0,	0},
								{  -1,	2,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0},
								{	1,	5,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0},
								{	4,	0,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0, 14,	0},
								{  12, 27,	0,	0,	3,	0,	0,	4,	0,	0,	2,	0,	0,	0,	12,	0,	0,	3,	2,	0,	8,	0},
								{  16,	0,	0, 13,	0,	0,	0,	0,	1,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
								{  30,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	7,	0,	0,	0,	0},
								{  43,	21,	4,	0,	2,	2,	0,	3,	0,	0,	2,	0,	0,	0,	0,	3,	0,	0,	4,	5,	0,	0},
								{  73,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0,	4,	0,	0,	0,	0},
								{  96,	7,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	4,	3,	0,	0,	0},
								{ 166,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	16,	0}
							};
						
							for(int i=0; i<row; i++)
							{
						%>
								<tr>
							<% for(int j=0; j<22; j++)
								{
							%>
									<td align="center">
								<%	if( j == 0 )
									{
								%>
									<b><%=matrix[i][j]%></b>
								<%
									}
									else if( matrix[i][j] > 2 )
									{
								%>
										<font color="red"><b><%=matrix[i][j]%></b></font>
								<%	
									}
									else
									{
								%>		
										<%=matrix[i][j]%>
								<% 
									}
								%>
									</td>
							<%
								}
							%>
								</tr>
						<%
							}
						%>
				</table><br>
				A MODmap result is shown as PTM Frequency Matrix. Each entry of the matrix represents the number of the corresponding modifications predicted in a sample. For example, in above matrix, the mass shift of +12 Da on Nterm was observed 27 times during the ms/ms search. Cells with abundant number (shown in <font color="red"><b>Bold Red</b></font>) can be defined as candidate modifications allowing 'delta' for 'AA'.<br/><br/>
			  </td>
			</tr>

			<tr>
			  <td align="left">&nbsp;</td>
			</tr>
			<tr>
			  <td align="left"><img src="images/p_st1.GIF" width="4" height="9"/><font class="drakBR"> MISC<br/><br/>
			  </td>
			</tr>

			<tr>
			  <td align="left"><li><font class="drakBR"><a name="BONDION">DBond Viewer Fragment Ion Annotation</a></font><br/>&nbsp;'P*', one strand (top) of a dipeptide; 'p*', the other strand (bottom) of a dipeptide; 'capital letters', fragment ions from peptide P*; 'small letters', fragment ions from peptide p*; 'C-34', dehydroalanine ion; 'C+32', persulfide ion (formed by S-S or C-S bond cleavage reactions); 'AA*' represents immonium ion of the amino acid AA. Matched peaks are shown in red. The ion types of matched peaks were written in red for b- and y-ions, blue for ions from C-S and S-S bonds, and green for bond-specific ions.<br/>			  
			  </td>
			</tr>
			
		</table>	
	</td>
  </tr>

</table>

<jsp:include page="inc/footer.jsp" flush="true" />
