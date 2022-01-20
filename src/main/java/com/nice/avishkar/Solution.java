package com.nice.avishkar;

import com.nice.pojos.Candidate;
import com.nice.pojos.CandidateVotes;
import com.nice.pojos.ConstituencyResult;
import com.nice.pojos.Voter;
import com.nice.util.ElectionHelper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Solution {

  // in memory cache
  Map<String, List<Voter>> constituenciesToVoterList = new HashMap<>();

  public ElectionResult execute(Path candidateFile, Path votingFile) throws IOException {
    ElectionResult resultData = new ElectionResult(new HashMap<>());

    // construct candidateList
    InputStream inputFS = new FileInputStream(candidateFile.toFile());
    BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
    List<Candidate> candidateList = br
        .lines()
        .skip(1)
        .map(ElectionHelper.STR_TO_CANDIDATE_FUNCTION)
        .collect(Collectors.toList());
    br.close();

    // construct in memory cache to hold voters constituency wise
    InputStream inputFSForVoter = new FileInputStream(votingFile.toFile());
    BufferedReader brForVoter = new BufferedReader(new InputStreamReader(inputFSForVoter));
    brForVoter
        .lines()
        .skip(1)
        .forEach(this::addVoterToConstituency);
    brForVoter.close();
    
    List<String> constituencies = ElectionHelper.getDistinctConstinuencyNames(candidateList);

    for (String constituencyName : constituencies) {
      List<Voter> voterByConstituency = constituenciesToVoterList.get(constituencyName);

      Map<String, Integer> candidateVoteCount = new HashMap<>();
      for (Voter voter : voterByConstituency) {
        String candidateName = voter.getCandidateName();
        Integer voteCount = candidateVoteCount.get(candidateName);
        if (voteCount == null) {
          candidateVoteCount.put(candidateName, 1);
        } else {
          candidateVoteCount.put(candidateName, ++voteCount);
        }
      }
      Integer notaCount = candidateVoteCount.remove("NOTA");

      List<CandidateVotes> candidateVotes =
          candidateVoteCount.entrySet()
              .stream()
              .map(a -> new CandidateVotes(a.getKey(), a.getValue()))
              .collect(Collectors.toList());

      Comparator<CandidateVotes> compareByCandidateName = Comparator.comparing(
          CandidateVotes::getCandidateName);
      Comparator<CandidateVotes> compareByVotes = Comparator.comparing(CandidateVotes::getVotes
          ).reversed();
      Comparator<CandidateVotes> compareByNameAndVotes = compareByVotes.thenComparing(
          compareByCandidateName);
      candidateVotes.sort(compareByNameAndVotes);

      String winner = findWinner(candidateVotes);
      if (Optional.ofNullable(notaCount).orElse(-1) != -1) {
        candidateVotes.add(new CandidateVotes("NOTA", notaCount));
      }

      ConstituencyResult constituencyResult = new ConstituencyResult(winner, candidateVotes);

      resultData.addConstituencyResult(constituencyName, constituencyResult);
    }

    return resultData;
  }

  private void addVoterToConstituency(String a) {
    Voter voter = buildVoter(a);
    String constituencyName = voter.getConstituencyName();
    List<Voter> voterByConstituency = constituenciesToVoterList.get(constituencyName);
    if (voterByConstituency == null) {
      voterByConstituency = new ArrayList<>();
    }
    voterByConstituency.add(voter);
    constituenciesToVoterList.put(constituencyName, voterByConstituency);
  }

  private String findWinner(List<CandidateVotes> candidateVotes) {

    String winner = "NO_WINNER";
    if (candidateVotes.get(0).getVotes() != candidateVotes.get(1)
        .getVotes()) {
      winner = candidateVotes.get(0).getCandidateName();
    }

    return winner;
  }

  private Voter buildVoter(String line) {
    String[] p = line.split(",");
    Voter item = new Voter();
    item.setVoterId(p[0]);
    item.setConstituencyName(p[1]);
    item.setPollingStation(p[2]);
    if (p.length == 4 && p[3] != null && p[3].trim().length() > 0) {
      item.setCandidateName(p[3]);
    }
    return item;
  }

}