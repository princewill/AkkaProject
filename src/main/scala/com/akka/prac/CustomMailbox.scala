package com.akka.prac

import java.util.concurrent.ConcurrentLinkedQueue

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.dispatch.{Envelope, MailboxType, MessageQueue, ProducesMessageQueue}
import com.typesafe.config.Config

object CustomMailbox {
  val actorSystem = ActorSystem("CustomMailbox")
  val actor: ActorRef = actorSystem.actorOf(Props[MySpecialActor].withDispatcher("custom-dispatcher"))
  val actor1: ActorRef = actorSystem.actorOf(Props[MyActor],name = "xyz")
  val actor2: ActorRef = actorSystem.actorOf(Props[MyActor],name = "MyActor")
  actor1 !  ("hello", actor)
  actor2 !  ("hello", actor)
}

class MyMessageQueue extends MessageQueue {

  private final val queue = new ConcurrentLinkedQueue[Envelope]()

  def enqueue(receiver: ActorRef, handle: Envelope): Unit = {
    if(handle.sender.path.name == "MyActor") {
      handle.sender !  "Hey dude, How are you?, I Know your name,processing your request"
      queue.offer(handle)
    }
    else handle.sender ! "I don't talk to strangers, I can't process your request"
  }

  def dequeue(): Envelope = queue.poll

  def numberOfMessages: Int = queue.size

  def hasMessages: Boolean = !queue.isEmpty

  def cleanUp(owner: ActorRef, deadLetters: MessageQueue) {
    while (hasMessages) {
      deadLetters.enqueue(owner, dequeue())
    }
  }

}

class MyUnboundedMailbox extends MailboxType with ProducesMessageQueue[MyMessageQueue] {

  def this(settings: ActorSystem.Settings, config: Config) = this()

  // The create method is called to create the MessageQueue
  final override def create(owner: Option[ActorRef], system: Option[ActorSystem]): MessageQueue = new MyMessageQueue()
}

class MySpecialActor extends Actor {

  override def receive: Receive = {
    case msg: String => println(s"$msg")
  }
}

class MyActor extends Actor {

  override def receive: Receive = {
    case (msg: String, actorRef: ActorRef) => actorRef ! msg
    case msg: String => println(s"$msg")
  }
}
