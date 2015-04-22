package VO

import javax.validation.constraints.NotNull

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.NotEmpty

import scala.beans.BeanProperty

/**
 * Created by Dhaval Kolapkar on 9/25/14.
 */
class User {

  @BeanProperty
  var id:String=_

  @NotNull
  @NotEmpty
  @BeanProperty
  var email:String=_

  @NotNull
  @NotEmpty
  @BeanProperty
  var password:String=_

  @BeanProperty
  var created_at:String=_

  @JsonIgnore
  @BeanProperty
  var idCards:Array[IDCard]=_

  @JsonIgnore
  @BeanProperty
  var webLogins:Array[WebLogin]=_

  @JsonIgnore
  @BeanProperty
  var bankAccounts:Array[BankAccount]=_

}
