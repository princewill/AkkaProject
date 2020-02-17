package com.akka.prac

import java.util.concurrent.ConcurrentLinkedQueue

import akka.actor.ActorRef
import akka.dispatch.{Envelope, MessageQueue}

object CustomMailbox {

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
