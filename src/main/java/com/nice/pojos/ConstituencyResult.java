package com.nice.pojos;

import java.util.List;

public class ConstituencyResult {

  private final String winnerName;
  private final List<CandidateVotes> candidateList;


  public ConstituencyResult(String winnerName, List<CandidateVotes> candidateList) {
    super();
    this.winnerName = winnerName;
    this.candidateList = candidateList;
  }

  public String getWinnerName() {
    return winnerName;
  }

  public List<CandidateVotes> getCandidateList() {
    return candidateList;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ConstituencyResult [winnerName=");
    builder.append(winnerName);
    builder.append(", candidateList=");
    builder.append(candidateList);
    builder.append("]");
    return builder.toString();
  }

}
