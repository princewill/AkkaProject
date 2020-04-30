package com.akka.prac

import akka.actor.{Actor, ActorSystem, Props}
import akka.dispatch.ControlMessage

case object MyControlMessage extends ControlMessage

class Logger extends Actor {
  def receive(): Receive = {
    case MyControlMessage => println("Oh, I have to process Control message first")
    case x => println(x.toString)
  }

}

object ControlAwareMailbox extends App {
  val actorSystem = ActorSystem("ControlAwareMailbox")
  val actor = actorSystem.actorOf(Props[Logger].withDispatcher("control-aware-dispatcher"))
  actor ! "hello"
  actor ! "how are"
  actor ! "you?"
  actor ! MyControlMessage
}
