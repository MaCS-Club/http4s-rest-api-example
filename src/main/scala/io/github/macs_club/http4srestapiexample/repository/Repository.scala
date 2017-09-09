package io.github.macs_club.http4srestapiexample.repository

import scalaz.Monad
import scala.language.higherKinds

abstract class Repository[A, M[_]: Monad] {
	def +=(entity: A): M[A]
	def update(entity: A): M[A]
	def -=(entity: A): M[Unit]
	def list: M[List[A]]
}
