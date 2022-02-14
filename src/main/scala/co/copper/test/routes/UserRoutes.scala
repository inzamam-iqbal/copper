package co.copper.test.routes

import co.copper.test.services.UserService
import com.sbuslab.http.RestRoutes
import com.sbuslab.sbus.Context
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Autowired
class UserRoutes(
  userService: UserService
) extends RestRoutes {
  def anonymousRoutes(implicit context: Context) =
    pathEnd {
      post {
        complete {
          userService.saveUsers
        }
      } ~
        get {
          complete {
            userService.getUsers
          }
        }
    }
}