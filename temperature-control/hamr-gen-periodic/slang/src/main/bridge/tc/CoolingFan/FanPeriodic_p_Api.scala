// #Sireum

package tc.CoolingFan

import org.sireum._
import art._
import tc._

@sig trait FanPeriodic_p_Api {
  def id: Art.BridgeId
  def fanCmd_Id : Art.PortId
  def fanAck_Id : Art.PortId

  def put_fanAck(value : CoolingFan.FanAck.Type) : Unit = {
    Art.putValue(fanAck_Id, CoolingFan.FanAck_Payload(value))
  }

  def logInfo(msg: String): Unit = {
    Art.logInfo(id, msg)
  }

  def logDebug(msg: String): Unit = {
    Art.logDebug(id, msg)
  }

  def logError(msg: String): Unit = {
    Art.logError(id, msg)
  }
}

@datatype class FanPeriodic_p_Initialization_Api (
  val id: Art.BridgeId,
  val fanCmd_Id : Art.PortId,
  val fanAck_Id : Art.PortId) extends FanPeriodic_p_Api

@datatype class FanPeriodic_p_Operational_Api (
  val id: Art.BridgeId,
  val fanCmd_Id : Art.PortId,
  val fanAck_Id : Art.PortId) extends FanPeriodic_p_Api {

  def get_fanCmd() : Option[CoolingFan.FanCmd.Type] = {
    val value : Option[CoolingFan.FanCmd.Type] = Art.getValue(fanCmd_Id) match {
      case Some(CoolingFan.FanCmd_Payload(v)) => Some(v)
      case Some(v) =>
        Art.logError(id, s"Unexpected payload on port fanCmd.  Expecting 'CoolingFan.FanCmd_Payload' but received ${v}")
        None[CoolingFan.FanCmd.Type]()
      case _ => None[CoolingFan.FanCmd.Type]()
    }
    return value
  }
}
