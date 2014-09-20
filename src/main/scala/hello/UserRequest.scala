package hello

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by dhavalkolapkar on 19/09/14.
 */
class UserRequest {

  @JsonProperty("emailAddress") val emailAddress: String=null
  @JsonProperty("password")val password: String=null
  @JsonProperty("name") val name: String=null

}
