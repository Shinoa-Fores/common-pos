package cpos.model

import cpos.util.HashImpl.hash

sealed trait Ticket {

  val puz: Array[Byte]
  val account: Account

  val byteNum: Byte

  lazy val score: Long = {
    val multiplier = hash(account.publicKey ++ puz)(byteNum)
    if (multiplier >= 64) 0 else multiplier * account.balance
  }
}


case class Ticket1(override val puz: Array[Byte],
                   override val account: Account) extends Ticket {
  override val byteNum: Byte = 1
}

case class Ticket2(override val puz: Array[Byte],
                   override val account: Account) extends Ticket {
  override val byteNum: Byte = 2
}

case class Ticket3(override val puz: Array[Byte],
                   override val account: Account) extends Ticket {
  override val byteNum: Byte = 3
}