package com.akka.prac

import akka.actor.{Actor, ActorSystem, Props}
import akka.actor.ActorSystem.Settings
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}
import com.typesafe.config.Config

object PriorityMailBox extends App{

  val actorSystem = ActorSystem("PriorityMailBox")
  val myPriorityActor = actorSystem.actorOf(Props[MyPriorityActor].withDispatcher("prio-dispatcher"))

  myPriorityActor ! 6.0
  myPriorityActor ! 1
  myPriorityActor ! 5.0
  myPriorityActor ! 3
  myPriorityActor ! "Hello"
  myPriorityActor ! 5
  myPriorityActor ! "I am priority actor"
  myPriorityActor ! "I process string messages first,then integer, long and others"

}



class MyPriorityActor extends Actor {

  override def receive: Receive = {
    case x: Int => println(x)
    case x: String => println(x)
    case x: Long => println(x)
    case x => println(x)
  }

}

class MyPriorityActorMailBox(settings: Settings, config: Config) extends UnboundedPriorityMailbox(
  // Create a new PriorityGenerator,lower prio means more important

  PriorityGenerator {
    // Int Messages
    case x: Int => 1
    // String Messages
    case x: String => 0
    // Long messages
    case x: Long => 2
    // other messages
    case _ => 3
  }

)
