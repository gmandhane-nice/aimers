package com.nice.pojos;

public class Voter {

  String voterId;
  String constituencyName;
  String pollingStation;
  String candidateName;

  public String getVoterId() {
    return voterId;
  }

  public void setVoterId(String voterId) {
    this.voterId = voterId;
  }

  public String getConstituencyName() {
    return constituencyName;
  }

  public void setConstituencyName(String constituencyName) {
    this.constituencyName = constituencyName;
  }

  public void setPollingStation(String pollingStation) {
    this.pollingStation = pollingStation;
  }

  public String getCandidateName() {
    return candidateName;
  }

  public void setCandidateName(String candidateName) {
    this.candidateName = candidateName;
  }

  @Override
  public String toString() {
    return "Voter{" +
        "voterId='" + voterId + '\'' +
        ", constituencyName='" + constituencyName + '\'' +
        ", pollingStation='" + pollingStation + '\'' +
        ", candidateName='" + candidateName + '\'' +
        '}';
  }
}
