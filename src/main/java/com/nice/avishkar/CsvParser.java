package com.nice.avishkar;

import com.nice.pojos.Candidate;
import com.nice.pojos.Voter;
import com.nice.util.ElectionHelper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvParser implements Parser {

  private final Path path;

  public CsvParser(Path path) {
    this.path = path;
  }

  @Override
  public List<Candidate> parseCandidates(Path path) {
    List<Candidate> candidateList;
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(
        this.path.toFile())))) {
      candidateList = br
          .lines()
          .skip(1)
          .map(ElectionHelper.STR_TO_CANDIDATE_FUNCTION)
          .collect(Collectors.toList());
    } catch (IOException io) {
      throw new RuntimeException("Error occurred during processing the files");
    }
    return candidateList;
  }

  @Override
  public Map<String, List<Voter>> parseVoters(Path path) {
    Map<String, List<Voter>> constituenciesToVoterList = new HashMap<>();
    try (BufferedReader brForVoter = new BufferedReader(new InputStreamReader(new FileInputStream(
        path.toFile())))) {
      brForVoter
          .lines()
          .skip(1)
          .forEach(a -> addVoterToConstituency(a, constituenciesToVoterList));
    } catch (IOException io) {
      throw new RuntimeException("Error occurred during processing the files");
    }
    return constituenciesToVoterList;
  }

  private void addVoterToConstituency(String a,
      Map<String, List<Voter>> constituenciesToVoterList) {
    Voter voter = ElectionHelper.buildVoter(a);
    String constituencyName = voter.getConstituencyName();
    List<Voter> voterByConstituency = constituenciesToVoterList.get(constituencyName);
    if (voterByConstituency == null) {
      voterByConstituency = new ArrayList<>();
    }
    voterByConstituency.add(voter);
    constituenciesToVoterList.put(constituencyName, voterByConstituency);
  }
}
