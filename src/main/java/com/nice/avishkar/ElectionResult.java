package com.nice.avishkar;

import com.nice.pojos.ConstituencyResult;
import java.util.Map;

public class ElectionResult {

  private final Map<String, ConstituencyResult> resultData;

  public ElectionResult(Map<String, ConstituencyResult> resultData) {
    super();
    this.resultData = resultData;
  }

  public Map<String, ConstituencyResult> getResultData() {
    return resultData;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ElectionResult [resultData=");
    builder.append(resultData);
    builder.append("]");
    return builder.toString();
  }

  public void addConstituencyResult(String constituencyName, ConstituencyResult result) {
    resultData.put(constituencyName, result);
  }
}
