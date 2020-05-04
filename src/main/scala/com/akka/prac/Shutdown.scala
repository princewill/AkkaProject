package com.akka.prac

import akka.actor.{Actor, ActorSystem, PoisonPill, Props}

object Shutdown extends App {
  val actorSystem = ActorSystem("shut-down")

  val actor1 = actorSystem.actorOf(Props[ShutdownActor], name = "shutdownActor1")
  actor1 ! "hello"
  actor1 ! PoisonPill
  actor1 ! "Are you there?"

  val actor2 = actorSystem.actorOf(Props[ShutdownActor], "shutdownActor2")
  actor2 ! "hello"
  actor2 ! Stop
  actor2 ! "Are you there?"


}

case object Stop

class ShutdownActor extends Actor {

  override def receive: Receive = {
    case msg:String => println(s"$msg")
    case Stop => context.stop(self)
  }
}
