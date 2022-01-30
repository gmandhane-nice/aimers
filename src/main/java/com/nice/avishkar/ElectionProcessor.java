package com.nice.avishkar;

import com.nice.pojos.Candidate;
import com.nice.pojos.CandidateVotes;
import com.nice.pojos.ConstituencyResult;
import com.nice.pojos.Voter;
import com.nice.util.ElectionHelper;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElectionProcessor {

  private static final Logger logger = LogManager.getLogger(ElectionProcessor.class);

  private final SortingStrategy strategy;
  private final WinnerDeductionStrategy winnerDeductionStrategy;
  private final Parser parser;

  public ElectionProcessor(SortingStrategy strategy,
      WinnerDeductionStrategy winnerDeductionStrategy, Parser parser) {
    this.strategy = strategy;
    this.winnerDeductionStrategy = winnerDeductionStrategy;
    this.parser = parser;
  }

  public void sortCandidates(List<CandidateVotes> candidateVotes) {
    strategy.sortCandidates(candidateVotes);
  }

  public String findWinner(List<CandidateVotes> candidateVotes) {
    return winnerDeductionStrategy.findWinner(candidateVotes);
  }

  public List<Candidate> getCandidates(Path path) {
    List<Candidate> candidates = parser.parseCandidates(path);
    if (candidates.isEmpty()) {
      throw new RuntimeException("No candidates present to contest election");
    }
    return ElectionRules.sanitiseCandidates(candidates);
  }

  public Map<String, List<Voter>> getVotersByConstituency(Path path) {
    return parser.parseVoters(path);
  }

  public ElectionResult computeElectionResult(
      Map<String, List<Voter>> constituenciesToVoterList, List<Candidate> candidateList) {
    ElectionResult resultData = new ElectionResult(new HashMap<>());
    Set<String> constituencies = constituenciesToVoterList.keySet();
    Set<String> voterIds = new HashSet<>();
    for (String constituencyName : constituencies) {
      List<Voter> voterByConstituency = constituenciesToVoterList.get(constituencyName);
      List<CandidateVotes> candidateVotes = getCandidateVotesByConstituency(voterIds,
          voterByConstituency);
      this.sortCandidates(candidateVotes);
      String winner = this.findWinner(candidateVotes);
      ConstituencyResult constituencyResult = new ConstituencyResult(winner, candidateVotes);
      resultData.addConstituencyResult(constituencyName, constituencyResult);
    }
    return resultData;
  }

  private List<CandidateVotes> getCandidateVotesByConstituency(Set<String> voterIds,
      List<Voter> voterByConstituency) {
    Map<String, Integer> candidateVoteCount = new HashMap<>();
    for (Voter voter : voterByConstituency) {
      boolean hasVoted = voterIds.contains(voter.getVoterId());
      if (hasVoted) {
        logger.warn("Disqualified voter with id: {}", voter::getVoterId);
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
    return candidateVoteCount.entrySet()
        .stream()
        .map(a -> new CandidateVotes(a.getKey(), a.getValue()))
        .collect(Collectors.toList());
  }
}
