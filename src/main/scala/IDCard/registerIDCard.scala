package IDCard

import java.util.HashMap
/**
 * Created by dhavalkolapkar on 20/09/14.
 */
class registerIDCard () {
  def register(response: IDCardResponse, userId: Long, idCards: HashMap[Long, IDCardResponse], userCards: HashMap[Long, Long]): Long= {
    idCards.put(response.cardId, response)
    userCards.put(userId, response.cardId)
    response.cardId
  }
}
