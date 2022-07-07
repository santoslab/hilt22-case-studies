// #Sireum #Logika

package tc.TempSensor

import org.sireum._
import tc._

// This file will not be overwritten so is safe to edit
object TempSensor_s_tcproc_tempSensor {

  def initialise(api: TempSensor_s_Initialization_Api): Unit = {
    Contract(
      Requires(
        // BEGIN INITIALIZES REQUIRES
        // assume AADL_Requirement
        //   All outgoing event data ports must be empty
        api.tempChanged.isEmpty
        // END INITIALIZES REQUIRES
      ),
      Ensures(
        // BEGIN INITIALIZES ENSURES
        // guarantee initializes
        api.currentTemp.degrees == 72.0f,
        // guarantee g1
        //   Testing MustSend with event port, initializes so no event expected
        !(api.tempChanged.nonEmpty),
        // guarantee g2
        //   Testing NoSend with event port, initializes so no event expected
        api.tempChanged.isEmpty
        // END INITIALIZES ENSURES
      )
    )
    api.put_currentTemp(Temperature_i(72.0f))
  }

  def timeTriggered(api: TempSensor_s_Operational_Api): Unit = {
    // read temperature from HARDWARE temperature sensor,
    // via interface realized via Slang Extension "TempSensorNative"
    var temp = TempSensorDevice.currentTempGet()
    // set the out data port currentTemp to hold the read temperature
    //  BUG - unauthorized mod of temperature data
    // val adjDegrees = temp.degrees + 13.0f // adjust temperature upward
    // temp = Temperature_i(adjDegrees)
    api.put_currentTemp(temp)
    // put an event on tempChanged out event port to
    // notify subscribers (e.g., tempControl thermostat) that the
    // temperature has changed
    api.put_tempChanged()

    api.logInfo(s"Sensed: ${temp}")
  }

  def activate(api: TempSensor_s_Operational_Api): Unit = { }

  def deactivate(api: TempSensor_s_Operational_Api): Unit = { }

  def finalise(api: TempSensor_s_Operational_Api): Unit = { }

  def recover(api: TempSensor_s_Operational_Api): Unit = { }
}

@ext("TempSensorDevice_Ext_Sim") object TempSensorDevice {
  def currentTempGet(): Temperature_i = Contract.Only(
    Ensures(Res[Temperature_i].degrees >= -50.0f & Res[Temperature_i].degrees <= 150.0f)
  )
}
