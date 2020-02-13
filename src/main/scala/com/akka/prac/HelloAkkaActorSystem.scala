package com.akka.prac

import akka.actor.ActorSystem


object HelloAkkaActorSystem extends App {
  val actorSystem = ActorSystem("HelloAkka")
  println(actorSystem)
}