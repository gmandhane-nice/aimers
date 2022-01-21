package com.nice.avishkar;

import com.nice.pojos.Candidate;
import com.nice.pojos.CandidateVotes;
import com.nice.pojos.ConstituencyResult;
import com.nice.pojos.Voter;
import com.nice.util.ElectionHelper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.stream.Collectors;

public class Solution {

  private static final Logger logger = LogManager.getLogger(Solution.class);
  // in memory cache
  final Map<String, List<Voter>> constituenciesToVoterList = new HashMap<>();

  public ElectionResult execute(Path candidateFile, Path votingFile)  {
    ElectionResult resultData = new ElectionResult(new HashMap<>());

    List<Candidate> candidateList = ElectionHelper.getCandidatesList(candidateFile);
    if (candidateList.isEmpty()) {
      logger.warn("No candidates found to contest the elections ");
      return resultData; // empty result
    }

    buildConstituencyToVotersMap(votingFile);

    List<String> constituencies = ElectionHelper.getDistinctConstinuencyNames(candidateList);
    Set<String> voterIds = new HashSet<>();
    for (String constituencyName : constituencies) {
      List<Voter> voterByConstituency = constituenciesToVoterList.get(constituencyName);

      Map<String, Integer> candidateVoteCount = new HashMap<>();
      for (Voter voter : voterByConstituency) {
        boolean hasVoted = voterIds.contains(voter.getVoterId());
        if (hasVoted) {
          logger.warn("Disqualified voter with id: {}", () -> voter.getVoterId());
          continue;
        }

        voterIds.add(voter.getVoterId());
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

      performCandidateSorting(candidateVotes);

      String winner = findWinner(candidateVotes);
      ConstituencyResult constituencyResult = new ConstituencyResult(winner, candidateVotes);

      resultData.addConstituencyResult(constituencyName, constituencyResult);
    }

    return resultData;
  }

  private void performCandidateSorting(List<CandidateVotes> candidateVotes) {
    candidateVotes.sort((p1, p2) -> {

      if (p1.getCandidateName().equals("NOTA")) {
        return 1;
      } else if (p2.getCandidateName().equals("NOTA")) {
        return -1;
      }
      long voteCount = p2.getVotes() - p1.getVotes();
      if (voteCount != 0) {
        return (int) voteCount;
      }
      return p1.getCandidateName().compareTo(p2.getCandidateName());
    });
  }

  private void buildConstituencyToVotersMap(Path votingFile) {
    try(BufferedReader brForVoter = new BufferedReader(new InputStreamReader(new FileInputStream(
            votingFile.toFile())))) {
      brForVoter
          .lines()
          .skip(1)
          .forEach(this::addVoterToConstituency);
    } catch (IOException io) {
      throw new RuntimeException("Error occurred during processing the files");
    }
  }

  private void addVoterToConstituency(String a) {
    Voter voter = ElectionHelper.buildVoter(a);
    String constituencyName = voter.getConstituencyName();
    List<Voter> voterByConstituency = constituenciesToVoterList.get(constituencyName);
    if (voterByConstituency == null) {
      voterByConstituency = new ArrayList<>();
    }
    voterByConstituency.add(voter);
    constituenciesToVoterList.put(constituencyName, voterByConstituency);
  }

  private static String findWinner(List<CandidateVotes> candidateVotes) {

    String winner = "NO_WINNER";
    if (candidateVotes.get(0).getVotes() != candidateVotes.get(1)
        .getVotes()) {
      winner = candidateVotes.get(0).getCandidateName();
    }

    return winner;
  }



}