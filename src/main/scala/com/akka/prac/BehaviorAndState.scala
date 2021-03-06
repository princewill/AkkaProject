package com.akka.prac

import akka.actor.{Actor, ActorSystem, Props}


object BehaviorAndState extends App {
  val actorSystem = ActorSystem("BehaviorAndState")

  // creating an actor inside the actor system
  val actor = actorSystem.actorOf(Props(classOf[SummingActor], 10), "summingactor")

  while (true) {
    Thread.sleep(3000)
    actor ! 1
  }
  // print actor path
  println(actor.path)
}


class SummingActor(initialSum: Int) extends Actor {
  // state inside the actor
  var sum = 0

  // behaviour which is applied on the state
  override def receive: Receive = {
    // receives message an integer
    case x: Int =>
      sum = initialSum + sum + x
      println(s"my state as sum is $sum")

    // receives default message
    case _ =>
      println("I don't know what are you talking about")
  }

}
