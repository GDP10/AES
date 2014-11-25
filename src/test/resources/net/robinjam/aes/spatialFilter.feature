Feature: Filter NOTAM by distance from a point
         As a flight planner
         I want to receive NOTAMs relating to a geographical location
         So that I can filter out events that are not relevant to my flight plan

   Scenario: Subscribe to a time period and recieve NOTAMS registered for that period
              Given a broker is running
              When I start a consumer with a latitude of "0.5", a longitude of "0.5" and a range of "0.1"
              And I start a producer with xml "/delta.xml"
              Then the consumer receives events with location "ABCD"
              And the consumer does not receive an event with location "EFGH"