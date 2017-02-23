package travel.messages;

import java.util.List;

/**
 * Created by BARCO on 23-Feb-17.
 */
public class ValidationResponse {
  private String status;
  private List errorMessageList;

  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public List getErrorMessageList() {
    return this.errorMessageList;
  }
  public void setErrorMessageList(List errorMessageList) {
    this.errorMessageList = errorMessageList;
  }
}
