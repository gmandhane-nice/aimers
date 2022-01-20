package com.nice.avishkar;

public class Voter {

  String voterId;
  String constituencyName;
  String PollingStation;
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

  public String getPollingStation() {
    return PollingStation;
  }

  public void setPollingStation(String pollingStation) {
    PollingStation = pollingStation;
  }

  public String getCandidateName() {
    return candidateName;
  }

  public void setCandidateName(String candidateName) {
    this.candidateName = candidateName;
  }
}
