package IDCard

import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by dhavalkolapkar on 20/09/14.
 */
class IDCardResponse(
//@JsonProperty("cardId") val cardId: Long,
//@JsonProperty("cardName") var cardName: String,
//@JsonProperty("cardNumber") var cardNumber: String,
//@JsonProperty("expirationDate") val expirationDate: Date
//) {
  //def this(cardId :Long,cardName:String,cardNumber:String) {
   // this(cardId,cardName,cardNumber,expirationDate)
  //}

                      ){
  @JsonProperty("cardId") var cardId: Long=0
  @JsonProperty("cardName") var cardName: String=null
  @JsonProperty("cardNumber") var cardNumber: String=null
 @JsonProperty("expirationDate") var expirationDate: Date=null

}
