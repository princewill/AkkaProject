package com.akka.prac

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

class FibunacciActor extends Actor {

  override def receive: Receive = {
    case num: Int => sender ! computeFib(num)
  }

  private def computeFib(num: Int): Int = num match {
    case 1 | 0 => num
    case _ => computeFib(num - 1) + computeFib(num - 2)
  }
}


object FibunacciActor extends App {
  implicit val timeout: Timeout = Timeout(10.seconds)

  val actorSystem = ActorSystem("FibunacciActor")

  val actor = actorSystem.actorOf(Props[FibunacciActor])

  val resultF = (actor ? 10).mapTo[Int]

  println(Await.result(resultF, 10.seconds))
}
