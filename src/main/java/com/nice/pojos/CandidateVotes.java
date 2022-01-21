package com.nice.pojos;

import java.util.Objects;

public class CandidateVotes {

  private final String candidateName;
  private final long votes;


  public CandidateVotes(String candidateName, long votes) {
    super();
    this.candidateName = candidateName;
    this.votes = votes;
  }

  public String getCandidateName() {
    return candidateName;
  }

  public long getVotes() {
    return votes;
  }

  @Override
  public int hashCode() {
    return Objects.hash(candidateName);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CandidateVotes other = (CandidateVotes) obj;
    return Objects.equals(candidateName, other.candidateName);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CandidateVotes [candidateName=");
    builder.append(candidateName);
    builder.append(", votes=");
    builder.append(votes);
    builder.append("]");
    return builder.toString();
  }

}
