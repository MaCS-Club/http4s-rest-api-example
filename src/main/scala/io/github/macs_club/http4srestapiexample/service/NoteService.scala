package io.github.macs_club.http4srestapiexample.service

import scalaz._, Scalaz._
import io.circe._
import org.http4s._
import org.http4s.circe._
import io.circe.generic.auto._
import org.http4s.server._
import org.http4s.dsl._
import scala.language.higherKinds
import scalaz.concurrent.Task

import io.github.macs_club.http4srestapiexample.domain._
import io.github.macs_club.http4srestapiexample.repository._

class NoteService(repository: Repository[Note,Task]) {
	implicit def circeJsonDecoder[A](implicit decoder: Decoder[A]): EntityDecoder[A] = org.http4s.circe.jsonOf[A]
  implicit def circeJsonEncoder[A](implicit encoder: Encoder[A]): EntityEncoder[A] = org.http4s.circe.jsonEncoderOf[A]

	val service = HttpService {
		case GET -> Root => repository.list >>= (Ok(_))
		case req @ POST -> Root => 	req.decode[Note]{ data =>
			Ok(repository += data)
		}
		case req @ PUT -> Root => req.decode[Note]{ data =>
			Ok(repository.update(data))
		}
		case DELETE -> Root / name => Ok(repository -= Note(name))
	}
}
