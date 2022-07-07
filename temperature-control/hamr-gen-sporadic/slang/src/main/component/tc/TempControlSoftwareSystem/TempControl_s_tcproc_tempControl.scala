// #Sireum #Logika

package tc.TempControlSoftwareSystem

import org.sireum._
import tc.CoolingFan.FanCmd
import tc._
import tc.TempSensor._

// This file will not be overwritten so is safe to edit
object TempControl_s_tcproc_tempControl {

  // BEGIN STATE VARS
  var currentSetPoint: TempControlSoftwareSystem.SetPoint_i = TempControlSoftwareSystem.SetPoint_i.example()

  var currentFanState: CoolingFan.FanCmd.Type = CoolingFan.FanCmd.byOrdinal(0).get

  var latestTemp: TempSensor.Temperature_i = TempSensor.Temperature_i.example()
  // END STATE VARS

  def initialise(api: TempControl_s_Initialization_Api): Unit = {
    Contract(
      Requires(
        // BEGIN INITIALIZES REQUIRES
        // assume AADL_Requirement
        //   All outgoing event data ports must be empty
        api.fanCmd.isEmpty
        // END INITIALIZES REQUIRES
      ),
      Modifies(
        // BEGIN INITIALIZES MODIFIES
        currentSetPoint,
        currentFanState,
        latestTemp
        // END INITIALIZES MODIFIES
      ),
      Ensures(
        // BEGIN INITIALIZES ENSURES
        // guarantee defautSetPoint
        currentSetPoint.low.degrees == 70.0f && currentSetPoint.high.degrees == 80.0f,
        // guarantee defaultFanStates
        currentFanState == CoolingFan.FanCmd.Off,
        // guarantee defaultLatestTemp
        latestTemp.degrees == 72.0f
        // END INITIALIZES ENSURES
      )
    )

    currentSetPoint = SetPoint_i(low = Temperature_i(70.0f), high = Temperature_i(80.0f))

    currentFanState = CoolingFan.FanCmd.Off
    latestTemp = Temperature_i(72f)

    // initialize output data ports
    //  (no output data ports to initialize)
  }

  def handle_fanAck(api: TempControl_s_Operational_Api, value : CoolingFan.FanAck.Type): Unit = {
    Contract(
      Requires(
        (latestTemp.degrees < currentSetPoint.low.degrees) ->: (currentFanState == CoolingFan.FanCmd.Off),
        (latestTemp.degrees > currentSetPoint.high.degrees) ->: (currentFanState == CoolingFan.FanCmd.On),

        // BEGIN_COMPUTE_REQUIRES_fanAck
        // assume AADL_Requirement
        //   All outgoing event data ports must be empty
        api.fanCmd.isEmpty,
        // assume HAMR-Guarantee
        //   passed in payload must be the same as the spec var's value
        //   NOTE: this assumes the user never changes the param name"
        api.fanAck == value
        // END_COMPUTE REQUIRES_fanAck
      ),
      Modifies(
        // BEGIN_COMPUTE_MODIFIES_fanAck
        currentSetPoint,
        currentFanState,
        latestTemp
        // END_COMPUTE MODIFIES_fanAck
      ),
      Ensures(
        // BEGIN_COMPUTE_ENSURES_fanAck
        // guarantee TC_Req_01
        //   If the current temperature is less than the set point, then the fan state shall be Off.
        (latestTemp.degrees < currentSetPoint.low.degrees) ->: (currentFanState == CoolingFan.FanCmd.Off),
        // guarantee TC_Req_02
        //   If the current temperature is greater than the set point,
        //   then the fan state shall be On.
        (latestTemp.degrees > currentSetPoint.high.degrees) ->: (currentFanState == CoolingFan.FanCmd.On),
        // guarantee TC_Req_03
        //   If the current temperature is greater than or equal to the
        //   current low set point and less than or equal to the current high set point,
        //   then the current fan state is maintained.
        (latestTemp.degrees >= currentSetPoint.low.degrees & latestTemp.degrees <= currentSetPoint.high.degrees) ->: (currentFanState == In(currentFanState)),
        // guarantee mustSendFanCmd
        //   If the local record of the fan state was updated,
        //   then send a fan command event with this updated value.
        (In(currentFanState) != currentFanState) ->: (api.fanCmd.nonEmpty && api.fanCmd.get == currentFanState) && (currentFanState == In(currentFanState)) ->: api.fanCmd.isEmpty,
        // guarantees setPointNotModified
        currentSetPoint == In(currentSetPoint),
        // guarantees lastTempNotModified
        latestTemp == In(latestTemp),
        // guarantees currentFanState
        currentFanState == In(currentFanState),
        // guarantees noEventsSent
        api.fanCmd.isEmpty
        // END_COMPUTE ENSURES_fanAck
      )
    )
    api.logInfo("received fanAck")
    if (value == CoolingFan.FanAck.Error) {
      // In a more complete implementation, we would implement some sort
      // of mitigation or recovery action at this point.
      // For now, we just log that fan is telling us that it did not
      // respond as expected to the last sent command.
      api.logError("Actuation failed!")
    } else {
      // Log actuation succeeded
      api.logInfo("Actuation worked.")
    }
  }

  def handle_setPoint(api: TempControl_s_Operational_Api, value : TempControlSoftwareSystem.SetPoint_i): Unit = {
    Contract(
      Requires(
        // BEGIN_COMPUTE_REQUIRES_setPoint
        // assume AADL_Requirement
        //   All outgoing event data ports must be empty
        api.fanCmd.isEmpty,
        // assume HAMR-Guarantee
        //   passed in payload must be the same as the spec var's value
        //   NOTE: this assumes the user never changes the param name"
        api.setPoint == value
        // END_COMPUTE REQUIRES_setPoint
      ),
      Modifies(
        // BEGIN_COMPUTE_MODIFIES_setPoint
        currentSetPoint,
        currentFanState,
        latestTemp
        // END_COMPUTE MODIFIES_setPoint
      ),
      Ensures(
        // BEGIN_COMPUTE_ENSURES_setPoint
        // guarantee TC_Req_01
        //   If the current temperature is less than the set point, then the fan state shall be Off.
        (latestTemp.degrees < currentSetPoint.low.degrees) ->: (currentFanState == CoolingFan.FanCmd.Off),
        // guarantee TC_Req_02
        //   If the current temperature is greater than the set point,
        //   then the fan state shall be On.
        (latestTemp.degrees > currentSetPoint.high.degrees) ->: (currentFanState == CoolingFan.FanCmd.On),
        // guarantee TC_Req_03
        //   If the current temperature is greater than or equal to the
        //   current low set point and less than or equal to the current high set point,
        //   then the current fan state is maintained.
        (latestTemp.degrees >= currentSetPoint.low.degrees & latestTemp.degrees <= currentSetPoint.high.degrees) ->: (currentFanState == In(currentFanState)),
        // guarantee mustSendFanCmd
        //   If the local record of the fan state was updated,
        //   then send a fan command event with this updated value.
        (In(currentFanState) != currentFanState) ->: (api.fanCmd.nonEmpty && api.fanCmd.get == currentFanState) && (currentFanState == In(currentFanState)) ->: api.fanCmd.isEmpty,
        // guarantees setPointChanged
        currentSetPoint == api.setPoint,
        // guarantees latestTempNotModified
        latestTemp == In(latestTemp)
        // END_COMPUTE ENSURES_setPoint
      )
    )
    // log to indicate that that a setPoint event was received/handled
    // on the setPoint in event data port
    // api.logInfo(s"received setPoint $value")  // remove for now because Logika cannot handle string interpolation
    api.logInfo("received setPoint")
    // assign the setPoint record (containing both low and high set points)
    // to a component local variable "setPoint" that holds the current set point values
    currentSetPoint = value

    // compute command to send to fan
    perform_fan_control(api)
  }

  def handle_tempChanged(api: TempControl_s_Operational_Api): Unit = {
    Contract(
      Requires(
        // BEGIN_COMPUTE_REQUIRES_tempChanged
        // assume AADL_Requirement
        //   All outgoing event data ports must be empty
        api.fanCmd.isEmpty
        // END_COMPUTE REQUIRES_tempChanged
      ),
      Modifies(
        // BEGIN_COMPUTE_MODIFIES_tempChanged
        currentSetPoint,
        currentFanState,
        latestTemp
        // END_COMPUTE MODIFIES_tempChanged
      ),
      Ensures(
        // BEGIN_COMPUTE_ENSURES_tempChanged
        // guarantee TC_Req_01
        //   If the current temperature is less than the set point, then the fan state shall be Off.
        (latestTemp.degrees < currentSetPoint.low.degrees) ->: (currentFanState == CoolingFan.FanCmd.Off),
        // guarantee TC_Req_02
        //   If the current temperature is greater than the set point,
        //   then the fan state shall be On.
        (latestTemp.degrees > currentSetPoint.high.degrees) ->: (currentFanState == CoolingFan.FanCmd.On),
        // guarantee TC_Req_03
        //   If the current temperature is greater than or equal to the
        //   current low set point and less than or equal to the current high set point,
        //   then the current fan state is maintained.
        (latestTemp.degrees >= currentSetPoint.low.degrees & latestTemp.degrees <= currentSetPoint.high.degrees) ->: (currentFanState == In(currentFanState)),
        // guarantee mustSendFanCmd
        //   If the local record of the fan state was updated,
        //   then send a fan command event with this updated value.
        (In(currentFanState) != currentFanState) ->: (api.fanCmd.nonEmpty && api.fanCmd.get == currentFanState) && (currentFanState == In(currentFanState)) ->: api.fanCmd.isEmpty,
        // guarantees tempChanged
        latestTemp == api.currentTemp,
        // guarantees setPointNotModified
        currentSetPoint == In(currentSetPoint)
        // END_COMPUTE ENSURES_tempChanged
      )
    )
    // log to indicate that that a tempChanged event was received/handled
    api.logInfo("received tempChanged")

    // get current temp from currentTemp in data port
    latestTemp = api.get_currentTemp().get // since this is a data port, the .get always succeeds

    // compute command to send to fan
    perform_fan_control(api)
  }


  //--------------------------------------------
  //
  //  p e r f o r m _ f a n _ c o n t r o l
  //
  //  Helper function to control the fan after
  //    change to current temperature, or
  //    change to set point.
  //
  //--------------------------------------------

  def perform_fan_control(api: TempControl_s_Operational_Api) : Unit = {
    Contract(
      // For now we need to manually specify that we assume that output event ports are empty at start of method.
      // This is because if we do not call "put value" on them, we need to be able to conclude that the ports are still empty
      // in the post-condition.
      Requires(api.fanCmd.isEmpty),
      Modifies(api,
        // ghost variables for ports are modified
        currentFanState),
      Ensures(
        api.currentTemp == In(api).currentTemp,

        (api.setPoint.low == In(api).setPoint.low) && (api.setPoint.high == In(api).setPoint.high),
        api.setPoint == In(api).setPoint,

        api.fanAck == In(api).fanAck,
        // post-conditions - control logic
        (latestTemp.degrees < currentSetPoint.low.degrees) ->:
          (currentFanState == FanCmd.Off),
        (latestTemp.degrees > currentSetPoint.high.degrees) ->:
          (currentFanState == FanCmd.On),
        // The following clause combines the control law logic with the logic for decided if we need to send a fan command
        //  (i.e., did our desired state for fan change).   It might be cleaner (better compositional reasoning) to
        // separate these.
        (latestTemp.degrees >= currentSetPoint.low.degrees & latestTemp.degrees <= currentSetPoint.high.degrees)
          ->: (currentFanState == In(currentFanState) & api.fanCmd == None[FanCmd.Type]()),
        // post-condition - communication logic
        (currentFanState != In(currentFanState)) ->: (api.fanCmd == Some(currentFanState)),
        (currentFanState == In(currentFanState)) ->: (api.fanCmd.isEmpty)
      )
    )
    val oldFanState = currentFanState
    if (latestTemp.degrees < currentSetPoint.low.degrees) {
      // if current temp is below low set point,
      currentFanState = CoolingFan.FanCmd.Off
      api.logInfo("Set fan command: Off")
    } else if (latestTemp.degrees > currentSetPoint.high.degrees) {
      // if current temp exceeds high set point,
      currentFanState = CoolingFan.FanCmd.On
      api.logInfo("Set fan command: On")
    } else {
      api.logInfo("Fan state unchanged")
      // DEMO: Uncommenting this line will lead Logika to find a post-condition violation
      //   i.e., a fan command is being sent when it shouldn't be sent
      // api.put_fanCmd(currentFanState)
    }
    if (currentFanState != oldFanState) {
      // if we change the desired fanState, send new command to fan to change its state
      api.put_fanCmd(currentFanState)
    }
  }
  def activate(api: TempControl_s_Operational_Api): Unit = { }

  def deactivate(api: TempControl_s_Operational_Api): Unit = { }

  def finalise(api: TempControl_s_Operational_Api): Unit = { }

  def recover(api: TempControl_s_Operational_Api): Unit = { }
}
