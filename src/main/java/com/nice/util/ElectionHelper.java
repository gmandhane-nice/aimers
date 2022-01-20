package com.nice.util;

import com.nice.pojos.Candidate;
import java.util.function.Function;

public class ElectionHelper {

  private ElectionHelper() {
    // avoid instantiation
  }

  public static final Function<String, Candidate> STR_TO_CANDIDATE_FUNCTION = line -> {
    String[] p = line.split(",");
    return Candidate.createCandidate(p[0],p[1]);
  };

}
