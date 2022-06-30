package com.izibekova.gatling.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FeederDemo extends Simulation {

  val httpProtocol = http.baseUrl("http://computer-database.gatling.io");

  val feeder = csv("data/data1.csv").circular

  val scn = scenario("Feeder Demo")
    .repeat(1){
      feed(feeder)
        .exec { session =>
          println("Name: " + session("name").as[String])
          println("Job: " + session("job").as[String])
          println("Page: " + session("page").as[String])
          session
    }
        .pause(1)
        .exec(http("Go to #{page}")
        .get("/computers"))
    }

  setUp(scn.inject(atOnceUsers(3))).protocols(httpProtocol)

}
