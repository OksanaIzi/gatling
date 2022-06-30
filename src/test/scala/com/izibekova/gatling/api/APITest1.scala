import io.gatling.core.Predef._
import io.gatling.http.Predef._

class APITest1 extends Simulation{

  val httpProtocol = http
    .baseUrl("https://reqres.in/api/users")

  val scn = scenario("Get Api request demo")
    .exec(
      http("Get Single User")
        .get("/2")
        .check(status.is(200))
        .check(jsonPath("$.data.first_name").is("Janet"))
    )
    .pause(1)

  setUp(
    scn.inject(rampUsers(10).during(5))
      .protocols(httpProtocol)
  )
}