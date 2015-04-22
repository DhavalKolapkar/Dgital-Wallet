package VO

import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.NotEmpty

import scala.beans.BeanProperty


/**
 * Created by Dhaval Kolapkar on 9/30/14.
 */
class BankAccount {

  @BeanProperty
  var ba_id:String =_

  @BeanProperty
  var account_name:String =_

  @NotNull
  @NotEmpty
  @BeanProperty
  var routing_number:String =_

  @NotNull
  @NotEmpty
  @BeanProperty
  var account_number:String =_

}
