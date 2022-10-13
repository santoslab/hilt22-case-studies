package tc.CoolingFan

import org.sireum._
import art.{ArtNative_Ext, Empty}
import tc._

// This file was auto-generated.  Do not edit
abstract class FanPeriodic_p_tcproc_fan_TestApi extends BridgeTestSuite[FanPeriodic_p_tcproc_fan_Bridge](Arch.TempControlSoftwareSystem_p_Instance_tcproc_fan) {

  /** helper function to set the values of all input ports.
   * @param fanCmd payload for data port fanCmd
   */
  def put_concrete_inputs(fanCmd : CoolingFan.FanCmd.Type): Unit = {
    put_fanCmd(fanCmd)
  }


  /** helper function to check FanPeriodic_p_tcproc_fan's
   * output ports.  Use named arguments to check subsets of the output ports.
   * @param fanAck method that will be called with the value of the outgoing data
   *        port 'fanAck'.
   */
  def check_concrete_output(fanAck: CoolingFan.FanAck.Type => B = fanAckParam => {T}): Unit = {
    var testFailures: ISZ[ST] = ISZ()

    val fanAckValue: CoolingFan.FanAck.Type = get_fanAck().get
    if(!fanAck(fanAckValue)) {
      testFailures = testFailures :+ st"'fanAck' did not match expected: value of the outgoing data port is ${fanAckValue}"
    }

    assert(testFailures.isEmpty, st"${(testFailures, "\n")}".render)
  }


  // setter for in DataPort
  def put_fanCmd(value : CoolingFan.FanCmd.Type): Unit = {
    ArtNative_Ext.insertInPortValue(bridge.operational_api.fanCmd_Id, CoolingFan.FanCmd_Payload(value))
  }

  // getter for out DataPort
  def get_fanAck(): Option[CoolingFan.FanAck.Type] = {
    val value: Option[CoolingFan.FanAck.Type] = get_fanAck_payload() match {
      case Some(CoolingFan.FanAck_Payload(v)) => Some(v)
      case Some(v) => fail(s"Unexpected payload on port fanAck.  Expecting 'CoolingFan.FanAck_Payload' but received ${v}")
      case _ => None[CoolingFan.FanAck.Type]()
    }
    return value
  }

  // payload getter for out DataPort
  def get_fanAck_payload(): Option[CoolingFan.FanAck_Payload] = {
    return ArtNative_Ext.observeOutPortValue(bridge.initialization_api.fanAck_Id).asInstanceOf[Option[CoolingFan.FanAck_Payload]]
  }

}
