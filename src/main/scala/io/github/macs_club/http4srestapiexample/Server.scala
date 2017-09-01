package io.github.macs_club.http4srestapiexample

import java.util.concurrent.{ExecutorService, Executors}
import scala.util.Properties.envOrNone
import scalaz.concurrent.Task
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder

import io.github.macs_club.http4srestapiexample.domain.Note
import io.github.macs_club.http4srestapiexample.service.NoteService
import io.github.macs_club.http4srestapiexample.repository.Repository
import io.github.macs_club.http4srestapiexample.repository.impl.H2NoteRepository

object Main extends ServerApp {

  val port        : Int                       = envOrNone("HTTP_PORT") map (_.toInt) getOrElse 8080
  val ip          : String                    = "0.0.0.0"
  val pool        : ExecutorService           = Executors.newCachedThreadPool()
  val repository  : Repository[Note, Task]    = new H2NoteRepository()
  val noteService : NoteService               = new NoteService(repository)

  repository.init.unsafePerformSync

  override def server(args: List[String]): Task[Server] =
    BlazeBuilder
      .bindHttp(port, ip)
      .mountService(noteService.service)
      .withServiceExecutor(pool)
      .start
}
