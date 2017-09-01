package io.github.macs_club.http4srestapiexample.repository

import scalaz.Monad
import scala.language.higherKinds

abstract class Repository[A, M[_]: Monad] {
	def init: M[Int]

	def +=(entity: A): M[Int]
	def update(entity: A): M[Int]
	def -=(entity: A): M[Int]
	def list(): M[List[A]]
}
