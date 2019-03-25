package sources

import akka.stream.{Attributes, Outlet, SourceShape}
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}

abstract class DummySourceShape extends  GraphStage[SourceShape[String]] {
    val out: Outlet[String] = Outlet("FbSource")


    override val shape: SourceShape[String] = SourceShape(out)

    def generateData(): String = ???

    override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
      new GraphStageLogic(shape) {

        setHandler(out, new OutHandler {
          override def onPull(): Unit = {
            push(out, generateData())
          }
        })

      }
  }
