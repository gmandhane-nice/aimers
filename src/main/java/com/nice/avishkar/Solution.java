package com.nice.avishkar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {

	public ElectionResult execute(Path candidateFile, Path votingFile) throws IOException {
		ElectionResult resultData = new ElectionResult();

		InputStream inputFS = new FileInputStream(candidateFile.toFile());
		BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

		List<Candidate> candidateList = br
				.lines()
				.skip(1)
				.map(mapToCandidate)
				.collect(Collectors.toList());
		InputStream inputFSForVoter = new FileInputStream(votingFile.toFile());
		BufferedReader brForVoter = new BufferedReader(new InputStreamReader(inputFSForVoter));

		List<Voter>  voterList = brForVoter
				.lines()
				.skip(1)
				.map(mapToVoter)
				.collect(Collectors.toList());

		br.close();



		return resultData;
	}

	private Function<String, Candidate> mapToCandidate = (line) -> {
		String[] p = line.split(",");
		Candidate item = new Candidate();
		item.setConstituency(p[0]);//<-- this is the first column in the csv file
		item.setName(p[1]);
		return item;
	};

	private Function<String, Voter> mapToVoter = (line) -> {
		String[] p = line.split(",");
		Voter item = new Voter();
		item.setVoterId(p[0]);
		item.setConstituencyName(p[1]);
		item.setPollingStation(p[2]);
		if (p.length == 4 && p[3] != null && p[3].trim().length() > 0) {
			item.setCandidateName(p[3]);
		}
		return item;
	};

}