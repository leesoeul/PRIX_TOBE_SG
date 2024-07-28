package com.prix.homepage.livesearch.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.prix.homepage.constants.Modification;
import com.prix.homepage.constants.PeptideLine;
import com.prix.homepage.constants.ProteinInfo;
import com.prix.homepage.constants.ProteinSummary;
import com.prix.homepage.livesearch.dao.DataMapper;
import com.prix.homepage.livesearch.dao.SearchLogMapper;
import com.prix.homepage.livesearch.pojo.ResultDto;
import com.prix.homepage.user.pojo.Account;
import com.prix.homepage.user.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResultService {

	AccountService accountService;
	SearchLogMapper searchLogMapper;
	DataMapper dataMapper;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResultDto result(String id,
			HttpServletRequest request, HttpSession session) throws FileNotFoundException {

		if(id == null) id = "4";
		Integer idInt = Integer.parseInt(id);

		String fileName = request.getParameter("file");
		if (fileName == null)
		//fileName = "D:\\Work\\PRIX\\test.prix";
		fileName = "45";

		ProteinSummary summary = new ProteinSummary();

		String databasePath = "";

		// verification
		Integer level = (Integer) session.getAttribute("level");
		if (level == null) {
			Account account = accountService.getAccount(idInt);
			if (account != null) {
				level = account.getLevel();
				session.setAttribute("level", level);
			}
		}
		if (level == null)
			level = 0;
		Integer userId = searchLogMapper.findUserId(idInt, fileName);

		//이후 result.html에서 사용할 것들
		// read prix result file
		byte[] pwd = dataMapper.findContentById(idInt);
		logger.info("pwd:{}", pwd);
		String pwdStr = new String(pwd, StandardCharsets.UTF_8);
		if (pwd != null && pwd.length > 0) {
			File file = new File(pwdStr);
			InputStream is = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(is);
			summary.read(reader);
		}

		// read search database file
		if (summary != null && summary.getDatabasePath() != null) {
			Integer summaryId = Integer.parseInt(summary.getDatabasePath());
			pwd = dataMapper.findContentById(summaryId);
			pwdStr = new String(pwd, StandardCharsets.UTF_8);
			if (pwd != null && pwd.length > 0) {
				File file = new File(pwdStr);
				InputStream is = new FileInputStream(file);
				summary.readProtein(is);
				databasePath = pwdStr;
			}
		}

		boolean isDBond = false;
		if (summary.getEngineName() != null && summary.getEngineName().compareToIgnoreCase("DBOND") == 0)
			isDBond = true;

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
		if (targetDecoy == null && summary.isTargetDecoyed())
			targetDecoy = "O";

		int maxProteins = 0;
		if (maxHit.compareTo("All") != 0)
			maxProteins = Integer.parseInt(maxHit);
		double minPeptideScore = Double.parseDouble(minScore);
		boolean useTargetDecoy = (targetDecoy != null);
		double minFDRate = Double.parseDouble(minFDR) / 100;

		Modification[] mods = summary.getModifications();
		ProteinInfo[] proteins = summary.getProteins();

		if (useTargetDecoy) {

			double[] scores = summary.getDecoyScores();
			int fdr = Integer.parseInt(minFDR) - 1;
			if (fdr >= scores.length)
				fdr = scores.length - 1;
			minPeptideScore = scores[fdr];

			minScore = Double.toString(minPeptideScore);
		}
		if (proteins != null) {
			for (int i = 0; i < proteins.length; i++) {
				if (proteins[i] != null) {
					proteins[i].makePeptideLines(summary, mods, minPeptideScore, isDBond, i, true);
				}
			}
		}

		// sort here
		String sort = request.getParameter("sort");
		if (sort == null)
			sort = "pm";
		Comparator<ProteinInfo> by = new PepMatchComparator();
		if (sort.compareTo("pi") == 0)
			by = new IDComparator();
		else if (sort.compareTo("sc") == 0)
			by = new SeqCovComparator();
		else if (sort.compareTo("psm") == 0)
			by = new PSMMatchComparator();
		if (proteins != null)
			Arrays.sort(proteins, by);

		ResultDto resultDto = ResultDto.builder()
				.summary(summary)
				.mods(mods)
				.fileName(fileName)
				.isDBond(isDBond)
				.useTargetDecoy(useTargetDecoy)
				.minScore(minScore)
				.maxHit(maxHit)
				.minFDR(minFDR)
				.sort(sort)
				.userId(userId)
				.max(summary.getProteinMassMax())
				.proteins(proteins)
				.maxProteins(maxProteins)
				.level(level)
				.build();

		return resultDto;
	}// result end
}

// comparators
class PepMatchComparator implements Comparator<ProteinInfo> {
	public int compare(ProteinInfo l, ProteinInfo r) {
		int diff;
		if(l == null && r == null) return 0;
		else if (l == null)
			return 1;
		else if (r == null)
			return -1;
		else
		{
			diff = r.getNumberOfMatchedPeptides() - l.getNumberOfMatchedPeptides();
			if (diff == 0)
				diff = r.getPeptideLines().length - l.getPeptideLines().length;
			//return diff;
		}
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
	}
}

class PSMMatchComparator implements Comparator<ProteinInfo> {
	public int compare(ProteinInfo l, ProteinInfo r) {
		int diff;
		if(l == null && r == null) return 0;
		else if (l == null)
			return 1;
		else if (r == null)
			return -1;
		else
		{
			diff = r.getPeptideLines().length - l.getPeptideLines().length;
			if (diff == 0)
				diff = r.getNumberOfMatchedPeptides() - l.getNumberOfMatchedPeptides();
			//return diff;
		}
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
	}
}

class SeqCovComparator implements Comparator<ProteinInfo> {
	public int compare(ProteinInfo l, ProteinInfo r) {
		int diff;
		if(l == null && r == null) return 0;
		else if (l == null)
			return 1;
		else if (r == null)
			return -1;
		else
		{
			boolean[] hits = l.getCoverageCode();
			int lc = 0, rc = 0;
			for (int i = 0; i < hits.length; i++)
				if (hits[i])
					lc++;
			int ll = hits.length;
			hits = r.getCoverageCode();
			for (int i = 0; i < hits.length; i++)
				if (hits[i])
					rc++;
			int rl = hits.length;
			diff = rc * ll - lc * rl;
			if (diff == 0)
				diff = r.getNumberOfMatchedPeptides() - l.getNumberOfMatchedPeptides();
			//return diff;
		}
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
	}
}

class IDComparator implements Comparator<ProteinInfo> {
	public int compare(ProteinInfo l, ProteinInfo r) {
		int diff;
		if(l == null && r == null) return 0;
		else if (r == null)
			return 1;
		else if (l == null)
			return -1;
		else
		{
			diff = l.getName().compareTo(r.getName());
			if (diff == 0)
				diff = r.getNumberOfMatchedPeptides() - l.getNumberOfMatchedPeptides();
			//return diff;
		}
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
	}
}

class PeptideComparator implements Comparator<PeptideLine> {
	public int compare(PeptideLine l, PeptideLine r) {
		int diff = l.getStart() - r.getStart();
		if (diff == 0)
		{
			//diff = (int)((l.getScore() - r.getScore()) * 10000);
			diff = l.getEnd() - r.getEnd();
			if (diff == 0)
				diff = l.getIndex() - r.getIndex();
		}
		//return diff;
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
		
	}
}

class PeptideDecoyComparator implements Comparator<PeptideLine> {
	public int compare(PeptideLine l, PeptideLine r) {
//		return (int)((r.getScore() - l.getScore()) * 10000);
		int diff = (int)((r.getScore() - l.getScore()) * 10000);
		if(diff > 0) return 1;
		else if(diff < 0) return -1;
		else return 0;
	}
}