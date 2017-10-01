package controllers.auth

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{LogoutEvent, Silhouette}
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import service.auth.DefaultEnv

import scala.concurrent.Future

class UserController @Inject() (
                                 val messagesApi: MessagesApi,
                                 silhouette: Silhouette[DefaultEnv],
                                 socialProviderRegistry: SocialProviderRegistry)
  extends Controller with I18nSupport {

  /**
    * Returns the user.
    *
    * @return The result to display.
    */
  def user = silhouette.SecuredAction.async { implicit request =>
    Future.successful(Ok(Json.toJson(request.identity)))
  }

  /**
    * Manages the sign out action.
    */
  def signOut = silhouette.SecuredAction.async { implicit request =>
    silhouette.env.eventBus.publish(LogoutEvent(request.identity, request))
    silhouette.env.authenticatorService.discard(request.authenticator, Ok)
  }

  def echo = Action { request =>
    Ok("Got request [" + request + "]")
  }

}
