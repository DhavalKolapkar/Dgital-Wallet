package hello

import java.util.HashMap

/**
 * Created by dhavalkolapkar on 20/09/14.
 */
class RegisteredUser() {
  def register(response: UserResponse,users: HashMap[Long, UserResponse]): Long={
  users.put(response.userId, response)
  response.userId
  }
}
