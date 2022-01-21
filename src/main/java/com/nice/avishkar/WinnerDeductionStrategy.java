package com.nice.avishkar;

import com.nice.pojos.CandidateVotes;
import java.util.List;

public interface WinnerDeductionStrategy {
  public String findWinner(List<CandidateVotes> candidateVotes);
}
