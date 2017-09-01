package io.github.macs_club.http4srestapiexample.repository.impl

import scalaz._, Scalaz._
import doobie.imports._
import doobie.h2.imports._
import scalaz.concurrent.Task

import io.github.macs_club.http4srestapiexample.domain.Note
import io.github.macs_club.http4srestapiexample.repository.Repository

class H2NoteRepository extends Repository[Note, Task]{

	val xa = H2Transactor[Task]("jdbc:h2:mem:http4srestapiexample;DB_CLOSE_DELAY=-1", "h2username", "h2password")

	def init = {
		val query = sql"""
	     CREATE TABLE IF NOT EXISTS note (
	       title VARCHAR(20) NOT NULL UNIQUE,
				 body  VARCHAR(20)
	     )
	    """.update.run
		xa >>= (query.transact(_))
	}

	def +=(note: Note) = {
		val query = sql"""
				INSERT INTO note (title, body)
				VALUES (${note.title},${note.body})
			""".update.run
		xa >>= (query.transact(_))
	}

	def update(note: Note) = {
		val query = sql"""
				UPDATE note
				SET body = ${note.body}
				WHERE title = ${note.title}
			""".update.run
		xa >>= (query.transact(_))
	}

	def -=(note: Note) = {
		val query = sql"""
				DELETE FROM note
				WHERE title = ${note.title}
			""".update.run
		xa >>= (query.transact(_))
	}

	def list() = {
		val query = sql"""
				SELECT title, body
				FROM note
			""".query[Note].process.list
		xa >>= (query.transact(_))
	}
}
