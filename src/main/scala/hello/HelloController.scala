package hello

import java.net.URI
import java.text.SimpleDateFormat
import java.util.{Date, Calendar}
import javax.validation.Valid

import Exception.{UserNotFoundException, InvalidRequestException}
import VO.{BankAccount, IDCard, User, WebLogin}
import com.justinsb.etcd.EtcdClient
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._

import scala.util.control.Breaks

/**
 * This config class will trigger Spring @annotation scanning and auto configure Spring context.
 *
 * @author Dhaval
 * @since 1.0
 */

@RestController
class HelloController {

  var users: Map[String, User] = Map()

  val client = new EtcdClient(URI.create("http://54.67.54.197:4001"))
  val key = "010011625/counter"
  //var value : Int = 0
  //var result = this.client.set(key, value.toString)

  @RequestMapping(value=Array("/api/v1/counter") ,method=Array(RequestMethod.GET))
  @ResponseBody
  def seeCount(): String = {

   var result = this.client.get(key)
    var value = result.node.value.toInt + 1
    result = this.client.set(key, value.toString)
    result = this.client.get(key)
    result.node.value
  }

  //statusCheck
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value=Array("api/v1/status/"))
  def getStatus(): String = {
    "Alive"
  }

  //User start
  @RequestMapping(value = Array("/api/v1/users"), method = Array(RequestMethod.POST))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def registerUser(@Valid @RequestBody user: User,binding: BindingResult): User = {
    if(binding.hasErrors()) {
      throw new InvalidRequestException("Invalid User login input",binding)
    }
    var int_user_id: Long = System.currentTimeMillis
    var user_id: String = "u-" + int_user_id
    user.setId(user_id)
    //set date
    var today:Date = Calendar.getInstance().getTime()
    var formatter:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var createdAt: String= formatter.format(today)
    user.setCreated_at(createdAt)
    users += (user_id -> user)
    user
  }

  @RequestMapping(value = Array("/api/v1/users/{user_id}"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def viewUser(@PathVariable("user_id") user_id: String): User = {
    users(user_id)
  }

  @RequestMapping(value = Array("/api/v1/users/{user_id}"), method = Array(RequestMethod.PUT))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def updateUser(@PathVariable("user_id") user_id: String, @RequestBody updated_user: User,binding: BindingResult): User = {
    if(binding.hasErrors()) {
      throw new InvalidRequestException("Invalid User login input",binding)
    }
    var prevUser: User = users(user_id)
    prevUser.setEmail(updated_user.getEmail)
    prevUser.setPassword(updated_user.getPassword)
    //set updated Date
    var today:Date = Calendar.getInstance().getTime()
    var formatter:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val updatedAt:String=formatter.format(today)
    users += (user_id -> prevUser)
    users(user_id)
  }
  //User end
  //IDcards start
  @RequestMapping(value = Array("/api/v1/users/{user_id}/idcards"), method = Array(RequestMethod.POST))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def cardCreate(@PathVariable user_id: String, @RequestBody @Valid id_cards: IDCard,binding: BindingResult ): IDCard = {
    if(binding.hasErrors()) {
      throw new InvalidRequestException("Invalid card login input",binding)
    }
    var user: User = users(user_id)
    if (user != null) {
      var idCards: Array[IDCard] = user.getIdCards
      val card_id=System.currentTimeMillis
      if (idCards == null) {
        id_cards.setCard_id("c-" + card_id.toString)
        idCards = Array[IDCard](id_cards)
      }
      else {
        id_cards.setCard_id("c-" + card_id.toString)
        idCards = idCards :+ id_cards
      }
      user.setIdCards(idCards)
    }
    id_cards
  }


  @RequestMapping(value = Array("/api/v1/users/{user_id}/idcards"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def viewCards(@PathVariable("user_id") user_id: String): Array[IDCard] = {
    if(users.contains(user_id)) {
      var user: User = users(user_id)
      var id_Cards: Array[IDCard] = null
      if (user != null)
        id_Cards = user.getIdCards
      id_Cards
    }else{
      throw new UserNotFoundException()
    }
  }

  @RequestMapping(value = Array("/api/v1/users/{user_id}/idcards/{id_card}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def cardDelete(@PathVariable("user_id") user_id: String,@PathVariable("id_card") id_card: String) {
    var user: User = users(user_id)
    var id_Cards: Array[IDCard] = null
    if (user != null) {
      id_Cards = user.getIdCards
      var breaks = new Breaks
      breaks.breakable {
        for (i <- 1 to id_Cards.length - 1) {
          if (id_Cards(i).getCard_id.equals(id_card)) {
            val temp = id_Cards.toBuffer
            temp.remove(i)
            id_Cards = temp.toArray
            breaks.break()
          }

        }
      }
      user.setIdCards(id_Cards)
    }
  }

  //IDCard end
  //WebLogin start
  @RequestMapping(value = Array("/api/v1/users/{user_id}/weblogins"), method = Array(RequestMethod.POST))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def createUserWebLogin(  @Valid @RequestBody weblogins: WebLogin, @PathVariable user_id: String): WebLogin = {
    var user: User = users(user_id)
    val loginId = System.currentTimeMillis
    if (user != null) {
      var web_logins: Array[WebLogin] = user.getWebLogins

      if (web_logins == null) {
        weblogins.setLogin_id("l-" + loginId.toString)
        web_logins = Array[WebLogin](weblogins)
      }
      else {
        weblogins.setLogin_id("l-" + loginId.toString)
        web_logins = web_logins :+ weblogins
      }
      user.setWebLogins(web_logins)
    }
    weblogins
  }

  @RequestMapping(value = Array("/api/v1/users/{user_id}/weblogins"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def getUserWebLogin(@PathVariable("user_id") user_id: String): Array[WebLogin] = {
    var user: User = users(user_id)
    var web_logins: Array[WebLogin] = null
    if (user != null)
      web_logins = user.getWebLogins
    web_logins
  }

  @RequestMapping(value = Array("/api/v1/users/{user_id}/weblogins/{login_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteUserWebLogin(@PathVariable("login_id") login_id: String, @PathVariable("user_id") user_id: String) {
    var user: User = users(user_id)
    var web_logins: Array[WebLogin] = null
    if (user != null) {
      web_logins = user.getWebLogins
      var breaks = new Breaks
      breaks.breakable {
        for (i <- 1 to web_logins.length - 1) {
          if (web_logins(i).getLogin_id.equals(login_id)) {
            val buff = web_logins.toBuffer
            buff.remove(i)
            web_logins = buff.toArray
            breaks.break()

          }

        }
      }
      user.setWebLogins(web_logins)
    }
  }
  //WebLogin end
  //BankAccount Start
  @Valid
  @RequestMapping(value = Array("/api/v1/users/{user_id}/bankaccounts"), headers = Array({"content-type=application/json"}), method = Array(RequestMethod.POST))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def createUserBankAccount(@RequestBody bankaccounts: BankAccount, @PathVariable user_id: String,binding:BindingResult): BankAccount = {
    if(binding.hasErrors()) {
      throw new InvalidRequestException("Invalid Bank Account login input",binding)
    }
    var user: User = users(user_id)
    val baId = System.currentTimeMillis

    if (user != null) {
      var bankAccounts: Array[BankAccount] = user.getBankAccounts

      if (bankAccounts == null) {
        bankaccounts.setBa_id("b-" + baId.toString)
        bankAccounts = Array[BankAccount](bankaccounts)
      }
      else {
        bankaccounts.setBa_id("b-" + baId.toString)
        bankAccounts = bankAccounts :+ bankaccounts
      }

      user.setBankAccounts(bankAccounts)
    }
    bankaccounts
  }

  @RequestMapping(value = Array("/api/v1/users/{user_id}/bankaccounts"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def getUserBankAccounts(@PathVariable("user_id") user_id: String): Array[BankAccount] = {
    var user: User = users(user_id)
    var bankaccounts: Array[BankAccount] = null
    if (user != null)
      bankaccounts = user.getBankAccounts
    bankaccounts
  }

  @RequestMapping(value = Array("/api/v1/users/{user_id}/bankaccounts/{ba_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteUserBankAccount(@PathVariable("ba_id") ba_id: String, @PathVariable("user_id") user_id: String)  {
    var user: User = users(user_id)
    var ba_ids: Array[BankAccount] = null
    if (user != null) {
      ba_ids = user.getBankAccounts
      var breaks = new Breaks
      breaks.breakable {
        for (i <- 1 to ba_ids.length - 1) {
          if (ba_ids(i).getBa_id.equals(ba_id)) {
            val buff = ba_ids.toBuffer
            buff.remove(i)
            ba_ids = buff.toArray
            breaks.break()
            //var list: List[IDCard] =idCards.toList
            //list.drop(i)
            // idCards = list.toArray
          }

        }
      }
      user.setBankAccounts(ba_ids)

    }
  }

}
//BankAccount end
