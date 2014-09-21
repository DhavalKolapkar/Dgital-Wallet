package hello


import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty
/**
 * Created by dhavalkolapkar on 20/09/14.
 */
class UserResponse(@JsonProperty("emailAddress") var emailAddress:String,
  @JsonProperty("password")var password:String,@JsonProperty("name")var name:String,
  @JsonProperty("createdAt")var createdAt:Date, @JsonProperty("updatedAt") var updatedAt:Date,@JsonProperty("userId")var userId:Long) {

  def this(emailAddress:String,password:String,name:String,createdAt:Date,userId:Long) {
    this(emailAddress,password,name,createdAt,null,userId)
     }

}
