package org.alexeyn

import java.io.File

import com.typesafe.config.{Config, ConfigFactory, ConfigParseOptions}
import com.typesafe.scalalogging.StrictLogging

case class Server(host: String, port: Int)

object AppConfig extends StrictLogging {
  private val parseOptions = ConfigParseOptions.defaults().setAllowMissing(false)

  def load: (Server, Config) = {
    val path = sys.env.getOrElse("APP_CONFIG_PATH", "src/main/resources/application.conf")
    val config = ConfigFactory.parseFile(new File(path), parseOptions).resolve()
    val host = if (config.hasPath("server.host")) config.getString("server.host") else "localhost"
    val port = if (config.hasPath("server.port")) config.getInt("server.port") else 8080
    logger.debug("cfg: {}", config)
    (Server(host, port), config)
  }
}
