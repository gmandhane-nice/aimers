package com.nice.avishkar;

public class Candidate {
  String constituencyName;
  String candidateName;

  public Candidate(String constituencyName, String candidateName) {
    this.constituencyName = constituencyName;
    this.candidateName = candidateName;
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
}
