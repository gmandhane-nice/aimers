package com.nice.avishkar;

import com.nice.pojos.CandidateVotes;
import java.util.List;

public class DefaultSortingStrategy implements SortingStrategy {


  @Override
  public void sortCandidates(List<CandidateVotes> candidateVotes) {
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
}
