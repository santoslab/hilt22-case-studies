package tc.TempControlSoftwareSystem

import org.sireum._
import art.{ArtNative_Ext, Empty}
import tc._

// This file was auto-generated.  Do not edit
abstract class TempControlPeriodic_p_tcproc_tempControl_TestApi extends BridgeTestSuite[TempControlPeriodic_p_tcproc_tempControl_Bridge](Arch.TempControlSoftwareSystem_p_Instance_tcproc_tempControl) {

  /** helper function to set the values of all input ports.
   * @param currentTemp payload for data port currentTemp
   * @param fanAck payload for data port fanAck
   * @param setPoint payload for data port setPoint
   */
  def put_concrete_inputs(currentTemp : TempSensor.Temperature_i,
                          fanAck : CoolingFan.FanAck.Type,
                          setPoint : TempControlSoftwareSystem.SetPoint_i): Unit = {
    put_currentTemp(currentTemp)
    put_fanAck(fanAck)
    put_setPoint(setPoint)
  }


  /** helper function to check TempControlPeriodic_p_tcproc_tempControl's
   * output ports.  Use named arguments to check subsets of the output ports.
   * @param fanCmd method that will be called with the value of the outgoing data
   *        port 'fanCmd'.
   */
  def check_concrete_output(fanCmd: CoolingFan.FanCmd.Type => B = fanCmdParam => {T}): Unit = {
    var testFailures: ISZ[ST] = ISZ()

    val fanCmdValue: CoolingFan.FanCmd.Type = get_fanCmd().get
    if(!fanCmd(fanCmdValue)) {
      testFailures = testFailures :+ st"'fanCmd' did not match expected: value of the outgoing data port is ${fanCmdValue}"
    }

    assert(testFailures.isEmpty, st"${(testFailures, "\n")}".render)
  }


  // setter for in DataPort
  def put_currentTemp(value : TempSensor.Temperature_i): Unit = {
    ArtNative_Ext.insertInPortValue(bridge.operational_api.currentTemp_Id, TempSensor.Temperature_i_Payload(value))
  }

  // setter for in DataPort
  def put_fanAck(value : CoolingFan.FanAck.Type): Unit = {
    ArtNative_Ext.insertInPortValue(bridge.operational_api.fanAck_Id, CoolingFan.FanAck_Payload(value))
  }

  // setter for in DataPort
  def put_setPoint(value : TempControlSoftwareSystem.SetPoint_i): Unit = {
    ArtNative_Ext.insertInPortValue(bridge.operational_api.setPoint_Id, TempControlSoftwareSystem.SetPoint_i_Payload(value))
  }

  // getter for out DataPort
  def get_fanCmd(): Option[CoolingFan.FanCmd.Type] = {
    val value: Option[CoolingFan.FanCmd.Type] = get_fanCmd_payload() match {
      case Some(CoolingFan.FanCmd_Payload(v)) => Some(v)
      case Some(v) => fail(s"Unexpected payload on port fanCmd.  Expecting 'CoolingFan.FanCmd_Payload' but received ${v}")
      case _ => None[CoolingFan.FanCmd.Type]()
    }
    return value
  }

  // payload getter for out DataPort
  def get_fanCmd_payload(): Option[CoolingFan.FanCmd_Payload] = {
    return ArtNative_Ext.observeOutPortValue(bridge.initialization_api.fanCmd_Id).asInstanceOf[Option[CoolingFan.FanCmd_Payload]]
  }

}
