package com.izibekova.gatling.api
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class PostPutDeleteApiDemo extends Simulation{

  val httpProtocol = http
    .baseUrl("https://reqres.in/api")

  val createUser = scenario("Create User")
    .exec(
      http("Create user request")
        .post("/users")
        .header("content-type", "application/json")
        .asJson
        .body(RawFileBody("data/user.json"))
//        .body(StringBody(
//          """
//            |{
//            |    "name": "morpheus",
//            |    "job": "leader"
//            |}
//            |""".stripMargin))
        .check(
          status is 201,
          jsonPath("$.name") is "morpheus")
    )
    .pause(1)


    val updateUserScn = scenario("Update User")
    .exec(
      http("update user")
        .put("/users/2")
        .body(RawFileBody("data/user.json")).asJson
        .check(
          status is 200,
          jsonPath("$.name") is "morpheus"
        )
    )
      .pause(1)

    val deleteUserScn = scenario("Delete User")
      .exec(
        http("delete user req")
          .delete("/user/2")
          .check(status is 204)
      )
      .pause(1)

  setUp(
    createUser.inject(rampUsers(10).during(5)),
    updateUserScn.inject(rampUsers(5).during(3)),
    deleteUserScn.inject(rampUsers(3).during(2))
      .protocols(httpProtocol)
  )
}