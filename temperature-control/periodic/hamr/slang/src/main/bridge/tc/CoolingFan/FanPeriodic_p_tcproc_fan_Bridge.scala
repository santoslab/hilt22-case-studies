// #Sireum

package tc.CoolingFan

import org.sireum._
import art._
import tc._
import tc.CoolingFan.{FanPeriodic_p_tcproc_fan => component}

// This file was auto-generated.  Do not edit

@datatype class FanPeriodic_p_tcproc_fan_Bridge(
  val id: Art.BridgeId,
  val name: String,
  val dispatchProtocol: DispatchPropertyProtocol,
  val dispatchTriggers: Option[ISZ[Art.PortId]],

  fanCmd: Port[CoolingFan.FanCmd.Type],
  fanAck: Port[CoolingFan.FanAck.Type]
  ) extends Bridge {

  val ports : Bridge.Ports = Bridge.Ports(
    all = ISZ(fanCmd,
              fanAck),

    dataIns = ISZ(fanCmd),

    dataOuts = ISZ(fanAck),

    eventIns = ISZ(),

    eventOuts = ISZ()
  )

  val initialization_api : FanPeriodic_p_Initialization_Api = {
    val api = FanPeriodic_p_Initialization_Api(
      id,
      fanCmd.id,
      fanAck.id
    )
    FanPeriodic_p_tcproc_fan_Bridge.c_initialization_api = Some(api)
    api
  }

  val operational_api : FanPeriodic_p_Operational_Api = {
    val api = FanPeriodic_p_Operational_Api(
      id,
      fanCmd.id,
      fanAck.id
    )
    FanPeriodic_p_tcproc_fan_Bridge.c_operational_api = Some(api)
    api
  }

  val entryPoints : Bridge.EntryPoints =
    FanPeriodic_p_tcproc_fan_Bridge.EntryPoints(
      id,

      fanCmd.id,
      fanAck.id,

      dispatchTriggers,

      initialization_api,
      operational_api)
}

object FanPeriodic_p_tcproc_fan_Bridge {

  var c_initialization_api: Option[FanPeriodic_p_Initialization_Api] = None()
  var c_operational_api: Option[FanPeriodic_p_Operational_Api] = None()

  @datatype class EntryPoints(
    FanPeriodic_p_tcproc_fan_BridgeId : Art.BridgeId,

    fanCmd_Id : Art.PortId,
    fanAck_Id : Art.PortId,

    dispatchTriggers : Option[ISZ[Art.PortId]],

    initialization_api: FanPeriodic_p_Initialization_Api,
    operational_api: FanPeriodic_p_Operational_Api) extends Bridge.EntryPoints {

    val dataInPortIds: ISZ[Art.PortId] = ISZ(fanCmd_Id)

    val eventInPortIds: ISZ[Art.PortId] = ISZ()

    val dataOutPortIds: ISZ[Art.PortId] = ISZ(fanAck_Id)

    val eventOutPortIds: ISZ[Art.PortId] = ISZ()

    def compute(): Unit = {
      Art.receiveInput(eventInPortIds, dataInPortIds)

      // implement the following in 'component':  def timeTriggered(api: FanPeriodic_p_Operational_Api): Unit = {}
      component.timeTriggered(operational_api)

      Art.sendOutput(eventOutPortIds, dataOutPortIds)
    }

    override
    def testCompute(): Unit = {
      Art.receiveInput(eventInPortIds, dataInPortIds)

      // implement the following in 'component':  def timeTriggered(api: FanPeriodic_p_Operational_Api): Unit = {}
      component.timeTriggered(operational_api)

      Art.releaseOutput(eventOutPortIds, dataOutPortIds)
    }

    override
    def testInitialise(): Unit = {
      component.initialise(initialization_api)
      Art.releaseOutput(eventOutPortIds, dataOutPortIds)
    }

    def activate(): Unit = {
      // implement the following method in 'component':  def activate(api: FanPeriodic_p_Operational_Api): Unit = {}
      component.activate(operational_api)
    }

    def deactivate(): Unit = {
      // implement the following method in 'component':  def deactivate(api: FanPeriodic_p_Operational_Api): Unit = {}
      component.deactivate(operational_api)
    }

    def finalise(): Unit = {
      // implement the following method in 'component':  def finalise(api: FanPeriodic_p_Operational_Api): Unit = {}
      component.finalise(operational_api)
    }

    def initialise(): Unit = {
      // implement the following method in 'component':  def initialise(api: FanPeriodic_p_Initialization_Api): Unit = {}
      component.initialise(initialization_api)
      Art.sendOutput(eventOutPortIds, dataOutPortIds)
    }

    def recover(): Unit = {
      // implement the following method in 'component':  def recover(api: FanPeriodic_p_Operational_Api): Unit = {}
      component.recover(operational_api)
    }
  }
}