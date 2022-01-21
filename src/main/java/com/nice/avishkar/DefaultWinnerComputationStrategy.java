package com.nice.avishkar;

import com.nice.pojos.CandidateVotes;
import java.util.List;

public class DefaultWinnerComputationStrategy implements WinnerDeductionStrategy {


  @Override
  public String findWinner(List<CandidateVotes> candidateVotes) {
    String winner = "NO_WINNER";
    if (candidateVotes.get(0).getVotes() != candidateVotes.get(1)
        .getVotes()) {
      winner = candidateVotes.get(0).getCandidateName();
    }

    return winner;
  }
}
