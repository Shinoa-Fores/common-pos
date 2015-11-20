package scorex.simulation

import akka.actor.{Props, Actor, ActorSystem}
import scorex.actors.{MinerSpec, Miner}
import scorex.simulation.SimulatorSpec.NewTick
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

trait Event


class Simulator extends Actor {

  import SimulatorSpec._

  var time = 0

  val MaxDelta = 10

  val MinersCount = 100

  val packetsLossPercentage = 1

  //todo: pass delta?
  val miners = 1.to(100).toSeq.map(_ => context.system.actorOf(Props[Miner]))

  override def receive = {
    case NewTick =>
      time = time + 1
      miners.foreach(ref => ref ! MinerSpec.TimerUpdate(time))
  }
}

object SimulatorSpec {
  case object NewTick
}


object SimulatorLauncher {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem()

    val simulator = system.actorOf(Props[Simulator])
    system.scheduler.schedule(0.seconds, 50.millis)(simulator ! NewTick)
  }
}
