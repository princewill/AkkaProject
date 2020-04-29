package com.akka.prac

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

class FibonacciActor extends Actor {

  override def receive: Receive = {
    case num: Int => sender ! computeFib(num)
  }

  private def computeFib(num: Int): Int = num match {
    case 1 | 0 => num
    case _ => computeFib(num - 1) + computeFib(num - 2)
  }
}


object FibonacciActor extends App {
  implicit val timeout: Timeout = Timeout(10.seconds)

  val actorSystem = ActorSystem("FibonacciActor")

  val actor = actorSystem.actorOf(Props[FibonacciActor])

  val resultF = (actor ? 10).mapTo[Int]

  println(Await.result(resultF, 10.seconds))
}
