package tc.TempControlSoftwareSystem

import org.sireum._
import art.{ArtNative_Ext, Empty}
import tc._

// This file was auto-generated.  Do not edit
abstract class OperatorInterfacePeriodic_p_tcproc_operatorInterface_TestApi extends BridgeTestSuite[OperatorInterfacePeriodic_p_tcproc_operatorInterface_Bridge](Arch.TempControlSoftwareSystem_p_Instance_tcproc_operatorInterface) {

  /** helper function to set the values of all input ports.
   * @param currentTemp payload for data port currentTemp
   */
  def put_concrete_inputs(currentTemp : TempSensor.Temperature_i): Unit = {
    put_currentTemp(currentTemp)
  }


  /** helper function to check OperatorInterfacePeriodic_p_tcproc_operatorInterface's
   * output ports.  Use named arguments to check subsets of the output ports.
   * @param setPoint method that will be called with the value of the outgoing data
   *        port 'setPoint'.
   */
  def check_concrete_output(setPoint: TempControlSoftwareSystem.SetPoint_i => B = setPointParam => {T}): Unit = {
    var testFailures: ISZ[ST] = ISZ()

    val setPointValue: TempControlSoftwareSystem.SetPoint_i = get_setPoint().get
    if(!setPoint(setPointValue)) {
      testFailures = testFailures :+ st"'setPoint' did not match expected: value of the outgoing data port is ${setPointValue}"
    }

    assert(testFailures.isEmpty, st"${(testFailures, "\n")}".render)
  }


  // setter for in DataPort
  def put_currentTemp(value : TempSensor.Temperature_i): Unit = {
    ArtNative_Ext.insertInPortValue(bridge.operational_api.currentTemp_Id, TempSensor.Temperature_i_Payload(value))
  }

  // getter for out DataPort
  def get_setPoint(): Option[TempControlSoftwareSystem.SetPoint_i] = {
    val value: Option[TempControlSoftwareSystem.SetPoint_i] = get_setPoint_payload() match {
      case Some(TempControlSoftwareSystem.SetPoint_i_Payload(v)) => Some(v)
      case Some(v) => fail(s"Unexpected payload on port setPoint.  Expecting 'TempControlSoftwareSystem.SetPoint_i_Payload' but received ${v}")
      case _ => None[TempControlSoftwareSystem.SetPoint_i]()
    }
    return value
  }

  // payload getter for out DataPort
  def get_setPoint_payload(): Option[TempControlSoftwareSystem.SetPoint_i_Payload] = {
    return ArtNative_Ext.observeOutPortValue(bridge.initialization_api.setPoint_Id).asInstanceOf[Option[TempControlSoftwareSystem.SetPoint_i_Payload]]
  }

}
