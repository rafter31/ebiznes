package service.auth

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import model.User

/**
  * The default env.
  */
trait DefaultEnv extends Env {
  type I = User
  type A = JWTAuthenticator
}