package com.prix.homepage.constants;

import java.util.Comparator;

public class PeptideComparator implements Comparator<PeptideLine> {

  @Override
	public int compare(PeptideLine l, PeptideLine r) {
		int diff = l.getStart() - r.getStart();
		if (diff == 0) {
			// diff = (int)((l.getScore() - r.getScore()) * 10000);
			diff = l.getEnd() - r.getEnd();
			if (diff == 0)
				diff = l.getIndex() - r.getIndex();
		}
		// return diff;
		if (diff > 0)
			return 1;
		else if (diff < 0)
			return -1;
		else
			return 0;

	}
}
