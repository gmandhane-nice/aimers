package com.nice.avishkar;

import com.nice.pojos.Candidate;
import com.nice.pojos.CandidateVotes;
import java.util.List;

public interface SortingStrategy {
  void sortCandidates(List<CandidateVotes> candidateVotes);
}
