package com.nice.util;

import com.nice.pojos.Candidate;
import com.nice.pojos.Voter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElectionHelper {

  private ElectionHelper() {
    // avoid instantiation
  }

  public static final Function<String, Candidate> STR_TO_CANDIDATE_FUNCTION = line -> {
    String[] p = line.split(",");
    return Candidate.createCandidate(p[0],p[1]);
  };

  public static List<String> getDistinctConstinuencyNames(List<Candidate> candidateList) {
    return candidateList.stream()
        .map(Candidate::getConstituencyName)
        .distinct()
        .collect(Collectors.toList());
  }

  public static Voter buildVoter(String line) {
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
