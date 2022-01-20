package com.nice.avishkar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {

	public ElectionResult execute(Path candidateFile, Path votingFile) throws IOException {
		ElectionResult resultData = new ElectionResult(new HashMap<>());

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

		List<String> constituencies = candidateList.stream()
						.map(Candidate::getConstituency)
								.distinct()
										.collect(Collectors.toList());

		Map<String, Map<String,Integer>> result = new HashMap<>();

		//List<String> voterId =
		for (String consti: constituencies) {
			List<Voter> voterByConstituency =
			voterList.stream()
					.filter(a -> a.constituencyName.equals(consti))
					.collect(Collectors.toList());


			Map<String, Integer> candidateVoteCount = new HashMap<>();
			for(Voter voter: voterByConstituency) {
				String candidateName = voter.getCandidateName();
				Integer voteCount = candidateVoteCount.get(candidateName);
				if (voteCount == null) {
					candidateVoteCount.put(candidateName, 1);
				} else {
					candidateVoteCount.put(candidateName, ++voteCount);
				}
			}

			List<CandidateVotes> candidateVotes =
					candidateVoteCount.entrySet()
									.stream()
											.map(a -> new CandidateVotes(a.getKey(), a.getValue()))
													.collect(Collectors.toList());

//			List<CandidateVotes> sortedVotesList = candidateVotes.stream()
//							.sorted(Comparator.comparing(CandidateVotes::getCandidateName)
//											.thenComparing(CandidateVotes:: getVotes))
//									.collect(Collectors.toList());
			Comparator<CandidateVotes> compareByCandidateName = Comparator.comparing( CandidateVotes::getCandidateName );

			Comparator<CandidateVotes> compareByVotes = Comparator.comparing( CandidateVotes::getVotes ).reversed();

			//Compare by first name and then last name (multiple fields)
			Comparator<CandidateVotes> compareByNameAndVotes = compareByVotes.thenComparing(compareByCandidateName);

//Use Comparator
			Collections.sort(candidateVotes, compareByNameAndVotes);

			//Collections.sort(candidateVotes);

			result.put(consti, candidateVoteCount);
			String winner = findWinner(candidateVotes);
			ConstituencyResult constituencyResult = new ConstituencyResult(winner, candidateVotes);

			resultData.addConstituencyResult(consti, constituencyResult);
		}

		br.close();
		brForVoter.close();
		return resultData;
	}

	private String findWinner(List<CandidateVotes> candidateVotes) {

//		String winner = candidateVotes
//				.stream()
//				.max(Comparator.comparing(CandidateVotes::getVotes))
//				.orElseGet(() -> new CandidateVotes("NO_WINNER",0))
//				.getCandidateName();

		List<CandidateVotes> candidateVotesWithoutNOTA = candidateVotes
															.stream()
															.filter( c -> !c.getCandidateName().equalsIgnoreCase("NOTA"))
															.collect(Collectors.toList());

		String winner = "NO_WINNER";
		if(candidateVotesWithoutNOTA.get(0).getVotes() != candidateVotesWithoutNOTA.get(1).getVotes())
			winner = candidateVotesWithoutNOTA.get(0).getCandidateName();

		return winner;
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