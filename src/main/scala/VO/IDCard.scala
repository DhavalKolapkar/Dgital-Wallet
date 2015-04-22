package VO

import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.NotEmpty

import scala.beans.BeanProperty


/**
 * Created by Dhaval Kolapkar on 9/25/14.
 */
class IDCard {

  @BeanProperty
  var card_id:String=_

  @NotNull
  @NotEmpty
  @BeanProperty
  var card_name:String=_

  @NotNull
  @NotEmpty
  @BeanProperty
  var card_number:String=_

  @BeanProperty
  var expiration_date:String=_


}
