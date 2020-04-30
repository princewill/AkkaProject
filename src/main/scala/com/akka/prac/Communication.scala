package com.akka.prac

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.util.Random._

class RandomNumberGeneratorActor extends Actor {
  import Messages._

  override def receive: PartialFunction[Any, Unit] = {
    case GiveMeRandomNumber => sender ! Done(getRandomNumber)
  }

  private def getRandomNumber: Int = {
    println("received message for random number")
    nextInt()
  }
}

class QueryActor extends Actor {
  import Messages._

  override def receive: PartialFunction[Any, Unit] = {
    case Start(actorRef) =>
      println("send me the next random number")
      actorRef ! GiveMeRandomNumber
    case Done(randNum) =>
      println(s"received random number: $randNum")
  }
}

object Messages {
  case class Done(randomNumber: Int)
  case object GiveMeRandomNumber
  case class Start(actorRef: ActorRef)
}


object Communication {
  import Messages._

  val actorSystem: ActorSystem = ActorSystem("communication")

  val randNumGenActor: ActorRef = actorSystem.actorOf(Props[RandomNumberGeneratorActor], name = "RandomNumberGeneratorActor")

  val queryActor: ActorRef = actorSystem.actorOf(Props[QueryActor], name = "QueryActor")

  queryActor ! Start(randNumGenActor)
}


