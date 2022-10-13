package tc.TempSensor

import org.sireum._
import art.{ArtNative_Ext, Empty}
import tc._

// This file was auto-generated.  Do not edit
abstract class TempSensorPeriodic_p_tcproc_tempSensor_TestApi extends BridgeTestSuite[TempSensorPeriodic_p_tcproc_tempSensor_Bridge](Arch.TempControlSoftwareSystem_p_Instance_tcproc_tempSensor) {

  /** helper function to check TempSensorPeriodic_p_tcproc_tempSensor's
   * output ports.  Use named arguments to check subsets of the output ports.
   * @param currentTemp method that will be called with the value of the outgoing data
   *        port 'currentTemp'.
   */
  def check_concrete_output(currentTemp: TempSensor.Temperature_i => B = currentTempParam => {T}): Unit = {
    var testFailures: ISZ[ST] = ISZ()

    val currentTempValue: TempSensor.Temperature_i = get_currentTemp().get
    if(!currentTemp(currentTempValue)) {
      testFailures = testFailures :+ st"'currentTemp' did not match expected: value of the outgoing data port is ${currentTempValue}"
    }

    assert(testFailures.isEmpty, st"${(testFailures, "\n")}".render)
  }


  // getter for out DataPort
  def get_currentTemp(): Option[TempSensor.Temperature_i] = {
    val value: Option[TempSensor.Temperature_i] = get_currentTemp_payload() match {
      case Some(TempSensor.Temperature_i_Payload(v)) => Some(v)
      case Some(v) => fail(s"Unexpected payload on port currentTemp.  Expecting 'TempSensor.Temperature_i_Payload' but received ${v}")
      case _ => None[TempSensor.Temperature_i]()
    }
    return value
  }

  // payload getter for out DataPort
  def get_currentTemp_payload(): Option[TempSensor.Temperature_i_Payload] = {
    return ArtNative_Ext.observeOutPortValue(bridge.initialization_api.currentTemp_Id).asInstanceOf[Option[TempSensor.Temperature_i_Payload]]
  }

}
