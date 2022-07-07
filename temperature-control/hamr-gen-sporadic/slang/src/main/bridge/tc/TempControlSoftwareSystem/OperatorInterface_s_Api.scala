// #Sireum

package tc.TempControlSoftwareSystem

import org.sireum._
import art._
import tc._

@sig trait OperatorInterface_s_Api {
  def id: Art.BridgeId
  def currentTemp_Id : Art.PortId
  def setPoint_Id : Art.PortId
  def tempChanged_Id : Art.PortId

  def put_setPoint(value : TempControlSoftwareSystem.SetPoint_i) : Unit = {
    Art.putValue(setPoint_Id, TempControlSoftwareSystem.SetPoint_i_Payload(value))
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

@datatype class OperatorInterface_s_Initialization_Api (
  val id: Art.BridgeId,
  val currentTemp_Id : Art.PortId,
  val setPoint_Id : Art.PortId,
  val tempChanged_Id : Art.PortId) extends OperatorInterface_s_Api

@datatype class OperatorInterface_s_Operational_Api (
  val id: Art.BridgeId,
  val currentTemp_Id : Art.PortId,
  val setPoint_Id : Art.PortId,
  val tempChanged_Id : Art.PortId) extends OperatorInterface_s_Api {

  def get_currentTemp() : Option[TempSensor.Temperature_i] = {
    val value : Option[TempSensor.Temperature_i] = Art.getValue(currentTemp_Id) match {
      case Some(TempSensor.Temperature_i_Payload(v)) => Some(v)
      case Some(v) =>
        Art.logError(id, s"Unexpected payload on port currentTemp.  Expecting 'TempSensor.Temperature_i_Payload' but received ${v}")
        None[TempSensor.Temperature_i]()
      case _ => None[TempSensor.Temperature_i]()
    }
    return value
  }

  def get_tempChanged() : Option[art.Empty] = {
    val value : Option[art.Empty] = Art.getValue(tempChanged_Id) match {
      case Some(Empty()) => Some(Empty())
      case Some(v) =>
        Art.logError(id, s"Unexpected payload on port tempChanged.  Expecting 'Empty' but received ${v}")
        None[art.Empty]()
      case _ => None[art.Empty]()
    }
    return value
  }
}
