package VO

import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.NotEmpty

import scala.beans.BeanProperty


/**
 * Created by Dhaval Kolapkar on 9/30/14.
 */
class WebLogin {

  @BeanProperty
  var login_id:String =_

  @NotNull
  @NotEmpty
  @BeanProperty
  var url:String =_

  @NotNull
  @NotEmpty
  @BeanProperty
  var login:String =_

  @NotNull
  @NotEmpty
  @BeanProperty
  var password:String =_

}
