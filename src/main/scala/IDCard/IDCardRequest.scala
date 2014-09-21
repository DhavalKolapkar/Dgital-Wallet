package IDCard

import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by dhavalkolapkar on 20/09/14.
 */
class IDCardRequest {
  @JsonProperty("cardName") val cardName: String=null
  @JsonProperty("cardNumber") val cardNumber: String=null
  @JsonProperty("expirationDate") var expirationDate: Date=new Date()
}
