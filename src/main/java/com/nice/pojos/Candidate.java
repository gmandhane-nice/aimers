package com.nice.pojos;

import java.util.Objects;

public class Candidate {
  String constituencyName;
  String candidateName;

  public Candidate(String constituencyName, String candidateName) {
    this.constituencyName = constituencyName;
    this.candidateName = candidateName;
  }

  public String getCandidateName() {
    return candidateName;
  }

  public String getConstituencyName() {
    return constituencyName;
  }

  /**
   * Static factory method to create candidate object
   *
   * @param constituencyName name of constituency
   * @param candidateName name of candidate
   * @return {@Code Candidate}
   */
  public static Candidate createCandidate(String constituencyName, String candidateName) {
    return new Candidate(constituencyName, candidateName);
  }

  @Override
  public String toString() {
    return "Candidate{" +
        "constituencyName='" + constituencyName + '\'' +
        ", candidateName='" + candidateName + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Candidate candidate = (Candidate) o;
    return Objects.equals(candidateName, candidate.candidateName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(candidateName);
  }
}
