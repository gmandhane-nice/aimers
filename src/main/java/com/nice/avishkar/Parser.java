package com.nice.avishkar;

import com.nice.pojos.Candidate;
import com.nice.pojos.Voter;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface Parser {
  List<Candidate> parseCandidates(Path path);
  Map<String, List<Voter>> parseVoters(Path path);
}
