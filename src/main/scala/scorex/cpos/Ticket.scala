package scorex.cpos

import java.security.MessageDigest

object HashImpl {
  val DigestSize = 32

  def hash(input: Array[Byte]): Array[Byte] =
    MessageDigest
      .getInstance("SHA-256")
      .digest(input)
      .ensuring(_.length == DigestSize)
}

sealed trait Ticket {

  import HashImpl.hash

  val puz: Array[Byte]
  val account: Account
  val right: Long

  val byteNum: Byte

  def score(): Long = {
    val multiplier = hash(account.publicKey ++ puz)(byteNum)
    if (multiplier >= 64) 0 else multiplier * account.balance
  }
}


case class Ticket1(override val puz: Array[Byte],
                   override val account: Account,
                   override val right: Long) extends Ticket {
  override val byteNum: Byte = 1
}

case class Ticket2(override val puz: Array[Byte],
                   override val account: Account,
                   override val right: Long) extends Ticket {
  override val byteNum: Byte = 2
}

case class Ticket3(override val puz: Array[Byte],
                   override val account: Account,
                   override val right: Long) extends Ticket {
  override val byteNum: Byte = 3
}