package com.nice.avishkar;

import com.nice.pojos.Candidate;
import com.nice.pojos.Voter;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Solution {

  private static final Logger logger = LogManager.getLogger(Solution.class);

  public ElectionResult execute(Path candidateFilePath, Path votingFilePath) {
    logger.info("Started execution");
    ElectionProcessor processor = new ElectionProcessor(new DefaultSortingStrategy(),
        new DefaultWinnerComputationStrategy(), new CsvParser(candidateFilePath));

    List<Candidate> candidateList = processor.getCandidates(candidateFilePath);
    Map<String, List<Voter>> constituenciesToVoterList = processor.getVotersByConstituency(
        votingFilePath);

    return processor.computeElectionResult(constituenciesToVoterList, candidateList);
  }
}