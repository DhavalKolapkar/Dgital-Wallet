package Exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by dhavalkolapkar on 21/09/14.
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such User")
class UserNotFoundException() extends RuntimeException{

}
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No card for the user")
class CardNotFoundException() extends RuntimeException{

}

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No web login for the user")
class WebLoginNotFoundException() extends RuntimeException{

}


@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No bank account for the user")
class NoBankAccFoundException() extends RuntimeException{

}

