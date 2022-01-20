package com.nice.util;

import com.nice.pojos.Candidate;
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
}
