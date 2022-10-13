// #Sireum

package tc.TempSensor

import org.sireum._
import art._
import tc._
import tc.TempSensor.{TempSensorPeriodic_p_tcproc_tempSensor => component}

// This file was auto-generated.  Do not edit

@datatype class TempSensorPeriodic_p_tcproc_tempSensor_Bridge(
  val id: Art.BridgeId,
  val name: String,
  val dispatchProtocol: DispatchPropertyProtocol,
  val dispatchTriggers: Option[ISZ[Art.PortId]],

  currentTemp: Port[TempSensor.Temperature_i]
  ) extends Bridge {

  val ports : Bridge.Ports = Bridge.Ports(
    all = ISZ(currentTemp),

    dataIns = ISZ(),

    dataOuts = ISZ(currentTemp),

    eventIns = ISZ(),

    eventOuts = ISZ()
  )

  val initialization_api : TempSensorPeriodic_p_Initialization_Api = {
    val api = TempSensorPeriodic_p_Initialization_Api(
      id,
      currentTemp.id
    )
    TempSensorPeriodic_p_tcproc_tempSensor_Bridge.c_initialization_api = Some(api)
    api
  }

  val operational_api : TempSensorPeriodic_p_Operational_Api = {
    val api = TempSensorPeriodic_p_Operational_Api(
      id,
      currentTemp.id
    )
    TempSensorPeriodic_p_tcproc_tempSensor_Bridge.c_operational_api = Some(api)
    api
  }

  val entryPoints : Bridge.EntryPoints =
    TempSensorPeriodic_p_tcproc_tempSensor_Bridge.EntryPoints(
      id,

      currentTemp.id,

      dispatchTriggers,

      initialization_api,
      operational_api)
}

object TempSensorPeriodic_p_tcproc_tempSensor_Bridge {

  var c_initialization_api: Option[TempSensorPeriodic_p_Initialization_Api] = None()
  var c_operational_api: Option[TempSensorPeriodic_p_Operational_Api] = None()

  @datatype class EntryPoints(
    TempSensorPeriodic_p_tcproc_tempSensor_BridgeId : Art.BridgeId,

    currentTemp_Id : Art.PortId,

    dispatchTriggers : Option[ISZ[Art.PortId]],

    initialization_api: TempSensorPeriodic_p_Initialization_Api,
    operational_api: TempSensorPeriodic_p_Operational_Api) extends Bridge.EntryPoints {

    val dataInPortIds: ISZ[Art.PortId] = ISZ()

    val eventInPortIds: ISZ[Art.PortId] = ISZ()

    val dataOutPortIds: ISZ[Art.PortId] = ISZ(currentTemp_Id)

    val eventOutPortIds: ISZ[Art.PortId] = ISZ()

    def compute(): Unit = {
      Art.receiveInput(eventInPortIds, dataInPortIds)

      // implement the following in 'component':  def timeTriggered(api: TempSensorPeriodic_p_Operational_Api): Unit = {}
      component.timeTriggered(operational_api)

      Art.sendOutput(eventOutPortIds, dataOutPortIds)
    }

    override
    def testCompute(): Unit = {
      Art.receiveInput(eventInPortIds, dataInPortIds)

      // implement the following in 'component':  def timeTriggered(api: TempSensorPeriodic_p_Operational_Api): Unit = {}
      component.timeTriggered(operational_api)

      Art.releaseOutput(eventOutPortIds, dataOutPortIds)
    }

    override
    def testInitialise(): Unit = {
      component.initialise(initialization_api)
      Art.releaseOutput(eventOutPortIds, dataOutPortIds)
    }

    def activate(): Unit = {
      // implement the following method in 'component':  def activate(api: TempSensorPeriodic_p_Operational_Api): Unit = {}
      component.activate(operational_api)
    }

    def deactivate(): Unit = {
      // implement the following method in 'component':  def deactivate(api: TempSensorPeriodic_p_Operational_Api): Unit = {}
      component.deactivate(operational_api)
    }

    def finalise(): Unit = {
      // implement the following method in 'component':  def finalise(api: TempSensorPeriodic_p_Operational_Api): Unit = {}
      component.finalise(operational_api)
    }

    def initialise(): Unit = {
      // implement the following method in 'component':  def initialise(api: TempSensorPeriodic_p_Initialization_Api): Unit = {}
      component.initialise(initialization_api)
      Art.sendOutput(eventOutPortIds, dataOutPortIds)
    }

    def recover(): Unit = {
      // implement the following method in 'component':  def recover(api: TempSensorPeriodic_p_Operational_Api): Unit = {}
      component.recover(operational_api)
    }
  }
}