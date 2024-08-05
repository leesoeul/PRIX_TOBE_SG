package com.prix.homepage.livesearch.patternMatch;

import java.io.*;
import java.sql.SQLException;

public class Make_html {

	// �ϳ��� protein�� ���� ����� html ���Ŀ� �°� string���� ���� ����
	public String getOneProtein(String dataBase, String acNumber, String description, String species, String sequence,
			boolean withoutSq)
			throws IOException, SQLException {
		StringBuffer temppp = new StringBuffer();

		// swiss prot�� genbank�� ����ϴ� ������ �ణ �ٸ���
		if (dataBase.equals("swiss_prot")) {
			temppp.append("<p><b>AC# : <a href=http://www.uniprot.org/uniprot/");
			temppp.append(acNumber);
			temppp.append(" target=blank>");
			temppp.append(acNumber);
			temppp.append("</a></b><br>\n");
			temppp.append("<b>Description : </b>");
		} else if (dataBase.equals("genbank")) {
			temppp.append(
					"<p><b>GI# : <a href=http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?cmd=Retrieve&db=protein&list_uids=");
			temppp.append(acNumber);
			temppp.append("&dopt=GenPept target=_blank>");
			temppp.append(acNumber);
			temppp.append("</a></b><br>\n");
			temppp.append("<b>Definition : </b>");
		}
		// �Ʒ� �κ��� db�� ������� ����
		temppp.append(description);
		temppp.append("<br>");
		temppp.append("<b>Species : </b>");
		temppp.append(species);
		temppp.append("<br>");

		if (!withoutSq) { // sequence�� ���
			temppp.append("<b>Sequence : </b> \n");
			temppp.append("<pre>\n   ");
			temppp.append(Make_Space(sequence));
			temppp.append("\n</pre> \n \n ");
		}
		return temppp.toString();
	}

	// ã�� ����, ã�� �������� ������ html ������ string���� ���� ����
	public String PrintParameter(String[] inputPattern, int[] theNumberOfFind, String species, boolean checkSpecies)
			throws IOException {
		int many = inputPattern.length;
		StringBuffer temppp = new StringBuffer();
		temppp.append("<b>*Search Pattern   : ");

		for (int i = 0; i < many; i++) {
			temppp.append(inputPattern[i]);
			if (i != many - 1)
				temppp.append(" AND ");
		}

		temppp.append("\n<br>*Search Protein   : ");
		temppp.append(theNumberOfFind[0]);
		temppp.append("\n<br>*Find Pattern   : ");
		temppp.append(theNumberOfFind[1]);
		temppp.append("\n<br>*Search Species   : ");

		if (checkSpecies)
			temppp.append(species);
		temppp.append("</b> <br> \n ");

		return temppp.toString();
	}

	// sequence�� ���� ���� 10�� ���� ������ �����, ���ٿ� 100���� ����
	private String Make_Space(String sequence) {
		int length = sequence.length(), count = 0;
		StringBuffer spaceInsert = new StringBuffer();

		for (int i = 0; i < length; i++) {
			if (sequence.charAt(i) == '<') { // �±װ� ������ �����ϸ�
				while (sequence.charAt(i) != '>') { // �ݴ� �κб��� �̵�
					spaceInsert.append(sequence.substring(i, i + 1));
					i++;
				}
				spaceInsert.append(">"); // �ݴ� ��ũ�� ���ش�
			} else {
				spaceInsert.append(sequence.substring(i, i + 1));
				count++;
				if (count % 100 == 0) // 100�� ����� �Ǹ�
					spaceInsert.append("\n   "); // ���� �ٲٰ�
				else if (count % 10 == 0) // 10�� ����� �Ǹ�
					spaceInsert.append(" "); // ������ ����
			}
		}
		return spaceInsert.toString();
	}
}