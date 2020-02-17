package com.akka.prac

import java.util.concurrent.ConcurrentLinkedQueue

import akka.dispatch.{Envelope, MessageQueue}

object CustomMailbox {

}


class MyMessageQueue extends MessageQueue {

  private final val queue = new ConcurrentLinkedQueue[Envelope]()

  

}
