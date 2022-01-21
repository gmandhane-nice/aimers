package com.nice.avishkar;

import com.nice.pojos.Candidate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElectionRules {

  private static final Logger logger = LogManager.getLogger(ElectionRules.class);
  public static List<Candidate> sanitiseCandidates(List<Candidate> candidateList) {

    Set<String> candidates = new HashSet<>();
    List<Candidate> sanitisedCandidateList = new ArrayList<>();
    for (Candidate candidate: candidateList) {
      String candidateName = candidate.getCandidateName();
      boolean candidateAlreadyHasConstituency = candidates.contains(candidateName);
      if (candidateAlreadyHasConstituency) {
        logger.warn("Candidate with name {} is contesting from multiple constituencies", candidateName);
        sanitisedCandidateList.remove(candidate);
        continue;
      }
      candidates.add(candidate.getCandidateName());
      sanitisedCandidateList.add(candidate);
    }
    return sanitisedCandidateList;
  }
}
