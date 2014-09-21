      package hello

      import java.util
      import java.util.Calendar

      import IDCard.{registerIDCard, IDCardResponse, IDCardRequest}
      import com.fasterxml.jackson.databind.{ObjectMapper, ObjectWriter}
      import org.springframework.http.HttpStatus
      import org.springframework.web.bind.annotation._

      @RestController
     @ControllerAdvice
    class DigitalWalletController {
      val users = new java.util.HashMap[Long, UserResponse]
        val idCards = new java.util.HashMap[Long, IDCardResponse]
        val userCards = new java.util.HashMap[Long, Long]
       val registeredUser=new RegisteredUser()

        //Create User
      @RequestMapping(value = Array("/Users"),
        method = Array(RequestMethod.POST))
       @ResponseStatus( HttpStatus.CREATED )
       def registerUser(@RequestBody request: UserRequest): String={
          val responseMap=new util.HashMap[String,Object]()
          val userId=System.currentTimeMillis
          val createdAt = Calendar.getInstance().getTime()
       val response=new UserResponse(request.emailAddress,request.password,request.name,createdAt,userId)
        //val registeredUser=new RegisteredUser()
        val userIdReturn: Long=registeredUser.register(response,users)
        val responseReturn: UserResponse=users.get(userIdReturn)
        responseMap.put("userId",responseReturn.userId.toString)
        responseMap.put("email",responseReturn.emailAddress)
        responseMap.put("password",responseReturn.password)
        responseMap.put("createdAt",responseReturn.createdAt.toString)
       //  val json: String="{\n"+"\"user_id\" : \""+responseReturn.userId+"\",\n\"email\":\""+responseReturn.emailAddress+"\",\"password\":\""+responseReturn.password+"\",\"createdAt\":"
        val jsonConverter: ObjectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter()
        @ResponseBody
        val json: String = jsonConverter.writeValueAsString(responseMap)
        print("JSON: "+json)
        json
      }

        //Get User
        @RequestMapping(value = Array("/Users/{userId}"),method = Array(RequestMethod.GET))
        @ResponseStatus( HttpStatus.OK )
        def viewUser(@PathVariable("userId") userId: Long): String ={
          val responseMap=new util.HashMap[String,Object]()
          val responseReturn: UserResponse=users.get(userId)
          responseMap.put("userId",responseReturn.userId.toString)
          responseMap.put("email",responseReturn.emailAddress)
          responseMap.put("password",responseReturn.password)
          responseMap.put("createdAt",responseReturn.createdAt.toString)
          val jsonConverter: ObjectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter()
          @ResponseBody
          val json: String = jsonConverter.writeValueAsString(responseMap)
          print("JSON in viewUser: "+json)
          json
        }

        //Update User
        @RequestMapping(value = Array("/Users/{userId}"),method = Array(RequestMethod.PUT))
        @ResponseStatus( HttpStatus.ACCEPTED )
        def updateUser(@PathVariable("userId") userId: Long,@RequestBody request: UserRequest): String ={
          val responseMap=new util.HashMap[String,Object]()
          val oldResponse: UserResponse=users.get(userId)
          oldResponse.emailAddress=request.emailAddress
          oldResponse.password=request.password
          oldResponse.updatedAt=Calendar.getInstance().getTime()
          val userIdReturn: Long=registeredUser.register(oldResponse,users)
          val responseReturn: UserResponse=users.get(userIdReturn)
          println("UpdatedAt: "+responseReturn.updatedAt)
          responseMap.put("userId",responseReturn.userId.toString)
          responseMap.put("email",responseReturn.emailAddress)
          responseMap.put("password",responseReturn.password)
          responseMap.put("createdAt",responseReturn.createdAt.toString)
          responseMap.put("updatedAt",responseReturn.updatedAt.toString)
          //  val json: String="{\n"+"\"user_id\" : \""+responseReturn.userId+"\",\n\"email\":\""+responseReturn.emailAddress+"\",\"password\":\""+responseReturn.password+"\",\"createdAt\":"
          val jsonConverter: ObjectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter()
          @ResponseBody
          val json: String = jsonConverter.writeValueAsString(responseMap)
          print("JSON: "+json)
          json
        }

        //*************ID Card*******************************************
        @RequestMapping(value = Array("/Users/{userId}/idcards"),method = Array(RequestMethod.POST))
        @ResponseStatus( HttpStatus.CREATED )
        def createCard(@PathVariable("userId") userId: Long,@RequestBody request: IDCardRequest): String = {
          val cardId: Long = System.currentTimeMillis
          val response = new IDCardResponse()
          response.cardId = cardId
          response.cardName = request.cardName
          response.cardNumber = request.cardNumber
          response.expirationDate = request.expirationDate
          val cardIdReturned: Long = new registerIDCard().register(response, userId, idCards, userCards)

          val json = null
          json
        }

    }