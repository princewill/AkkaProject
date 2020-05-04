package com.akka.prac

import akka.actor.{Actor, ActorSystem, Props}

object BecomeUnBecome extends App{

  val actorSystem = ActorSystem("BecomeUnBecome")
  val becomeUnBecome = actorSystem.actorOf(Props[BecomeUnBecomeActor])

  becomeUnBecome ! true
  becomeUnBecome ! "Hello how are you?"
  becomeUnBecome ! false
  becomeUnBecome ! 1100
  becomeUnBecome ! true
  becomeUnBecome ! "What do u do?"
}

class BecomeUnBecomeActor extends Actor{

  override def receive: Receive = {
    case true => context.become(isStateTrue)
    case false => context.become(isStateFalse)
    case _ => println("Don't know what you are saying dawg")
  }

  def isStateTrue: Receive = {
    case msg: String => println(s"$msg")
    case false => context.become(isStateFalse)
  }

  def isStateFalse: Receive = {
    case msg: Int => println(s"$msg")
    case true => context.become(isStateTrue)
  }

}
