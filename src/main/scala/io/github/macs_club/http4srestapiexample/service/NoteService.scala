package io.github.macs_club.http4srestapiexample.service

import scalaz._, Scalaz._
import io.circe._
import org.http4s._
import org.http4s.circe._
import io.circe.generic.auto._
import org.http4s.dsl._
import scalaz.concurrent.Task

import io.github.macs_club.http4srestapiexample.domain.Note
import io.github.macs_club.http4srestapiexample.repository.Repository
import io.github.macs_club.http4srestapiexample.repository.impl.H2NoteRepository

object NoteService {
	implicit def circeJsonDecoder[A](implicit decoder: Decoder[A]): EntityDecoder[A] = org.http4s.circe.jsonOf[A]
  implicit def circeJsonEncoder[A](implicit encoder: Encoder[A]): EntityEncoder[A] = org.http4s.circe.jsonEncoderOf[A]

	val repository: Task[Repository[Note, Task]] = H2NoteRepository()

	val service = HttpService {
		case GET -> Root => repository >>= (_.list) >>= (Ok(_))
		case req @ POST -> Root => 	req.decode[Note]{ data =>
			Ok(repository >>= (_ += data))
		}
		case req @ PUT -> Root => req.decode[Note]{ data =>
			Ok(repository >>= (_ update data))
		}
		case DELETE -> Root / name => Ok(repository >>= (_ -= Note(name)))
	}
}
